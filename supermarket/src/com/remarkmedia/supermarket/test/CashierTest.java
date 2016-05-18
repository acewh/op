package com.remarkmedia.supermarket.test;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.remarkmedia.supermarket.main.Cashier;
import com.remarkmedia.supermarket.main.Customer;
import com.remarkmedia.supermarket.main.Supermarket;

public class CashierTest {
	private static Supermarket supermarket;
	@BeforeClass
	public static void testInitGoods(){
		supermarket = new Supermarket("onepiece");
		supermarket.initGoods("Apple",15);
		supermarket.initGoods("Macbook",15);
		supermarket.initGoods("Cookie",15);
	}
	@Test
	public void testHandleCustRequest(){
		Customer cust = new Customer("testCust");
		cust.buyGood(supermarket);
		Cashier cashier = new Cashier(supermarket,"testCashier");
		cashier.handleCustomer(cust);
		Assert.assertEquals(cashier.getReceiveList().size(),1);
	}
}
