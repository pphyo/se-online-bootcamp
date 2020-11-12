package com.jdc.bcmp.views;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.jdc.bcmp.PosException;
import com.jdc.bcmp.entity.Category;
import com.jdc.bcmp.entity.Invoice;
import com.jdc.bcmp.entity.Product;
import com.jdc.bcmp.entity.SaleDTO;
import com.jdc.bcmp.entity.SaleOrder;
import com.jdc.bcmp.service.CategoryService;
import com.jdc.bcmp.service.ProductService;
import com.jdc.bcmp.service.SaleService;
import com.jdc.bcmp.util.StringUtil;

import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

public class SaleManagement {

	@FXML
	private ComboBox<Category> cbxCategory;
	@FXML
	private TextField txtProduct;
	@FXML
	private TableView<Product> tblProList;
	@FXML
	private Label headerTotal;
	@FXML
	private TableView<SaleOrder> tblCartList;
	@FXML
	private Label lblSubTotal;
	@FXML
	private Label lblTax;
	@FXML
	private Label lblTotal;
	@FXML
	private TableColumn<SaleOrder, Integer> colQty;
	
	private SaleService saleService;
	private ProductService proService;
	private List<SaleOrder> soList;
	private SaleOrder saleOrder;
	private SaleDTO dto;
	
	public void initialize() {
		saleService = SaleService.getInstance();
		proService = ProductService.getInstance();
		soList = new ArrayList<>();
		search();
		
		cbxCategory.getItems().addAll(CategoryService.getInstance().getAll());
		
		headerTotal.textProperty().bind(lblTotal.textProperty());
		
		colQty.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

			@Override
			public String toString(Integer obj) {
				if(null != obj)
					return String.valueOf(obj);
				return null;
			}

			@Override
			public Integer fromString(String str) {
				if(!StringUtil.isEmpty(str))
					return Integer.parseInt(str);
				return null;
			}

			
		}));
		
		colQty.setOnEditCommit(e -> {
			saleOrder = e.getRowValue();
			saleOrder.setQuantity(e.getNewValue());
			saleOrder.setTotal(saleOrder.getQuantity() * saleOrder.getUnitPrice());
			soList = new ArrayList<>(tblCartList.getItems());
			prepareForNext();
			calculate();
		});
		
	}

	public void search() {
		tblProList.getItems().clear();
		List<Product> list = proService.search(cbxCategory.getValue(), txtProduct.getText(), 0);
		tblProList.getItems().addAll(list);
	}
	
	public void addToCart(MouseEvent event) {
		if(event.getClickCount() == 2) {
			Product p = tblProList.getSelectionModel().getSelectedItem();
			
			saleOrder = tblCartList.getItems().stream()
								  .filter(so -> so.getProduct().getId() == p.getId())
								  .findFirst().orElse(null);
			
			if(null == saleOrder) {
				saleOrder = new SaleOrder();
				saleOrder.setProduct(p);
				saleOrder.setQuantity(1);
				saleOrder.setUnitPrice(p.getPrice());
				saleOrder.setTotal(saleOrder.getQuantity() * saleOrder.getUnitPrice());
				
				soList.add(saleOrder);
			} else {
				saleOrder.setQuantity(saleOrder.getQuantity() + 1);
				saleOrder.setTotal(saleOrder.getQuantity() * saleOrder.getUnitPrice());
				soList = new ArrayList<>(tblCartList.getItems());
			}
		}
		
		prepareForNext();
		calculate();
	}

	public void prepareForNext() {
		saleOrder = null;
		tblCartList.getItems().clear();
		tblCartList.getItems().addAll(soList);
	}
	
	public void calculate() {
		int subTotal = tblCartList.getItems().stream().mapToInt(so -> so.getTotal()).sum();
		int tax = subTotal / 100 * 10;
		int total = subTotal + tax;
		lblSubTotal.setText(String.valueOf(subTotal));
		lblTax.setText(String.valueOf(tax));
		lblTotal.setText(String.valueOf(total));
	}
	
	public void clear() {
		soList.clear();
		tblCartList.getItems().clear();
		calculate();
	}
	
	public void paid() {
		try {
			if(tblCartList.getItems().size() == 0) {
				throw new PosException("Please add product to cart!");
			}
			
			if(null == dto) {
				dto = new SaleDTO();
				Invoice invoice = dto.getInvoice();
				invoice.setInvoiceDate(LocalDate.now());
				invoice.setInvoiceTime(LocalTime.now());
				invoice.setSubTotal(Integer.parseInt(lblSubTotal.getText()));
				invoice.setTax(Integer.parseInt(lblTax.getText()));
				invoice.setTotal(Integer.parseInt(lblTotal.getText()));
			}
			
			dto.getOrders().clear();
			dto.getOrders().addAll(tblCartList.getItems());
			
			saleService.save(dto);
			clear();
		} catch (Exception e) {
			Dialog<String> dialog = new Dialog<>();
			dialog.setTitle("Empty Cart");
			dialog.setContentText(e.getMessage());
			dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
			dialog.show();
		}
		
	}
	
}
