package com.remarkmedia.supermarket.main;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Cashier 
 * @description handle a Customer Request in 5-10 seconds   
 * @author ACE
 * @date 2016-5-16
 */
public class Cashier implements Runnable{
	private Logger logger = Logger.getLogger(Cashier.class);
	private String name;
	private List<Customer> receiveList = new LinkedList<Customer>();//init receive customer list		
	public List<Customer> getReceiveList() {
		return receiveList;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	private final Supermarket supermarket;	
	public Cashier(Supermarket _superSupermarket){
		this.supermarket = _superSupermarket;
	}
	public Cashier(Supermarket _superSupermarket,String _name){
		this.supermarket = _superSupermarket;
		this.name = _name;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub				
		boolean isRunning = true;
		while(isRunning){
			if(supermarket.isSoldOut&&supermarket.getCustomerQueue().size()==0){
				logger.info("all of the goods sold out && all customer request handled!");
				isRunning=false;
			}else{
				Customer customer = supermarket.getCustomerQueue().poll();				
				if(customer!=null){				
					//handler customer		
					handleCustomer(customer);
					logger.info(name+"  handle the "+customer.getName()+"'s request");
				}
			}
		}
	}
	/**
	 * handle a customer request use 5-10 secnonds
	 * @param cust
	 */
	public void handleCustomer(Customer cust){
		Random rand = new Random();
		int seed = rand.nextInt(6)+5;
		try {
			Thread.sleep(seed*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cust.getGood().setSellTime(System.currentTimeMillis());
		cust.setHandleTime(System.currentTimeMillis());				
		synchronized (this) {
			receiveList.add(cust);
		}
	}
}
