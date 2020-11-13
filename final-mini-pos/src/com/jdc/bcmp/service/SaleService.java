package com.jdc.bcmp.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Invoice;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.entity.SaleDTO;
import com.jdc.bcmp.entity.SaleOrder;
import com.jdc.bcmp.util.DatabaseConnection;

public class SaleService {

	private static SaleService INSTANCE;
	
	private SaleService() {}
	
	public static SaleService getInstance() {
		return null == INSTANCE ? INSTANCE = new SaleService() : INSTANCE;
	}

	public void save(SaleDTO dto) {
		
		String invSql = "insert into invoice (inv_date, inv_time, tax, sub_total, total)"
				+ " values (?, ?, ?, ?, ?)";
		String soSql = "insert into sale_order (quantity, unit_price, total, product_id, product_category_id, invoice_id)"
				+ " values (?, ?, ?, ?, ?, ?)";
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement invInsert = conn.prepareStatement(invSql, Statement.RETURN_GENERATED_KEYS);
				PreparedStatement soInsert =  conn.prepareStatement(soSql)) {
			
			Invoice invoice = dto.getInvoice();
			invInsert.setDate(1, Date.valueOf(invoice.getInvoiceDate()));
			invInsert.setTime(2, Time.valueOf(invoice.getInvoiceTime()));
			invInsert.setInt(3, invoice.getTax());
			invInsert.setInt(4, invoice.getSubTotal());
			invInsert.setInt(5, invoice.getTotal());
			invInsert.executeUpdate();
			
			ResultSet rs = invInsert.getGeneratedKeys();
			
			while(rs.next()) {
				int invoiceId = rs.getInt(1);
				
				for(SaleOrder so : dto.getOrders()) {
					soInsert.setInt(1, so.getQuantity());
					soInsert.setInt(2, so.getUnitPrice());
					soInsert.setInt(3, so.getTotal());
					soInsert.setInt(4, so.getProduct().getId());
					soInsert.setInt(5, so.getProduct().getCategory().getId());
					soInsert.setInt(6, invoiceId);
					
					soInsert.addBatch();
				}
				soInsert.executeBatch();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public List<SaleOrder> search(LocalDate from, LocalDate to, int total) {
		String sql = "select inv.inv_date sale_date, inv.inv_time sale_time, "
				+ "p.name pro_name, so.quantity quantity, so.unit_price unit_price, "
				+ "so.total total from sale_order so join invoice inv on "
				+ "so.invoice_id = inv.id join product p on so.product_id = p.id where 1 = 1";
		
		List<SaleOrder> result = new ArrayList<>();
		List<Object> params = new LinkedList<>();
		StringBuilder sb = new StringBuilder(sql);
		
		if(null != from && from.isBefore(to)) {
			sb.append(" and inv.inv_date >= ?");
			params.add(Date.valueOf(from));
		}
		
		if(null != to && to.isAfter(from) && !to.isAfter(LocalDate.now())) {
			sb.append(" and inv.inv_date <= ?");
			params.add(Date.valueOf(to));
		}
		
		if(total > 0) {
			sb.append(" and so.total >= ?");
			params.add(total);
		}
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sb.toString())) {
			
			for(int i = 0; i < params.size(); i++) {
				stmt.setObject(i + 1, params.get(i));
			}
			
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				SaleOrder so = new SaleOrder();
				Product p = new Product();
				Invoice inv = new Invoice();
				
				p.setName(rs.getString("pro_name"));
				so.setProduct(p);
				
				inv.setInvoiceDate(rs.getDate("sale_date").toLocalDate());
				inv.setInvoiceTime(rs.getTime("sale_time").toLocalTime());
				so.setInvoice(inv);
				
				so.setQuantity(rs.getInt("quantity"));
				so.setUnitPrice(rs.getInt("unit_price"));
				so.setTotal(rs.getInt("total"));
				
				result.add(so);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public Map<String, Map<String, Integer>> findData(LocalDate from, LocalDate to) {
		Map<String, Map<String, Integer>> map = new LinkedHashMap<>();
		
		for(Category c : CategoryService.getInstance().getAll()) {
			map.put(c.getName(), find(c, from, to));
		}
		
		return map;
	}
	
	public Map<String, Integer> find(Category c, LocalDate from, LocalDate to) {
		
		String sql = "select sum(so.total) from sale_order so join invoice inv on"
				+ " so.invoice_id = inv.id join product p on p.id = so.product_id"
				+ " join category c on c.id = p.category_id where c.id = ?"
				+ " and inv.inv_date between ? and ? group by c.id";
		
		Map<String, Integer> map = new LinkedHashMap<>();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			
			while(from.compareTo(to) <= 0) {
				stmt.setInt(1, c.getId());
				stmt.setDate(2, Date.valueOf(from));
				stmt.setDate(3, Date.valueOf(to.plusDays(1)));
				
				ResultSet rs = stmt.executeQuery();
				
				int value = 0;
				
				while(rs.next()) {
					value = rs.getInt(1);
				}
				
				map.put(from.format(df), value);
				from = from.plusDays(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return map;
		
	}
	
	
	
//	public Map<LocalDate, Map<LocalDate, Integer>> findChartData(LocalDate from, LocalDate to) {
//		
//		String sql = "select inv.inv_date, sum(so.total) from sale_order so join invoice inv on so.invoice_id = inv.id where inv.inv_date = ? and inv.inv_date between ? and ?";
//		
//		Map<LocalDate, Map<LocalDate, Integer>> result = new LinkedHashMap<>();
//		
//		to = null == to ? LocalDate.now() : to;
//		from = null == from ? to : from;
//		
//		try(Connection conn = DatabaseConnection.getConnection();
//				PreparedStatement stmt = conn.prepareStatement(sql)) {
//			
//			while(from.compareTo(to) <= 0) {
//				stmt.setDate(1, Date.valueOf(from));
//				stmt.setDate(2, Date.valueOf(from));
//				stmt.setDate(3, Date.valueOf(to));
//				
//				ResultSet rs = stmt.executeQuery();
//				
//				while(rs.next()) {
//					
//					LocalDate key = rs.getDate(1).toLocalDate();
//					int value = rs.getInt(2);
//					
//					Map<LocalDate, Integer> map = result.get(key);
//					
//					if(null == map) {
//						map = new LinkedHashMap<>();
//						result.put(key, map);
//					}
//					
//					map.put(key, value);
//				}
//
//			}
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		while(from.compareTo(to) <= 0) {
//			
//			for(LocalDate key : result.keySet()) {
//				Map<LocalDate, Integer> map = result.get(key);
//				
//				if(!map.keySet().contains(from)) {
//					map.put(from, 0);
//				}
//			}
//			
//			from = from.plusDays(1);
//		}
//		
//		return result;
//	}
	
}
