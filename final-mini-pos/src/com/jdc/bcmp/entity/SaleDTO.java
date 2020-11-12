package com.jdc.bcmp.entity;

import java.util.ArrayList;
import java.util.List;

public class SaleDTO {

	private Invoice invoice;
	private List<SaleOrder> orders;

	public SaleDTO() {
		invoice = new Invoice();
		orders = new ArrayList<>();
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public List<SaleOrder> getOrders() {
		return orders;
	}

	public void setOrders(List<SaleOrder> orders) {
		this.orders = orders;
	}

}
