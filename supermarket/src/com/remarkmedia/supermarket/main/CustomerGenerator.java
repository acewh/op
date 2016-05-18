package com.remarkmedia.supermarket.main;

import java.util.Random;

import org.apache.log4j.Logger;
/**
 * customer generator
 * @description create a customer in 1-3 second 
 * @author ZORO
 * @date 2016-5-17
 */
public class CustomerGenerator implements Runnable{
	private Logger logger = Logger.getLogger(CustomerGenerator.class);
	private final Supermarket supermarket;
	public CustomerGenerator(Supermarket _supermarket){
		this.supermarket = _supermarket;
	}	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Random rand = new Random();
		int customerIndex = 0;
		boolean isRunning = true;
		while(isRunning){
			int sleep = rand.nextInt(3)+1;
			try {
				Thread.sleep(sleep*1000);
				customerIndex++;
				Customer cust = new Customer("Customer"+customerIndex);
				if(cust.buyGood(supermarket)!=null){
					//set the start waiting time 
					cust.setInitTime(System.currentTimeMillis());
					logger.info(cust.getName()+" here coming!!,buys a :"+cust.getGood().getName());
					supermarket.getCustomerQueue().put(cust);
				}else{
					isRunning = false;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
