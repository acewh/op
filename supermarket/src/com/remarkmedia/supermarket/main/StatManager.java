package com.remarkmedia.supermarket.main;
import org.apache.log4j.Logger;

/**
 * 
 * @description 
 * @author ZORO
 * @date 2016-5-17
 */
public class StatManager implements Runnable{
	private Logger logger = Logger.getLogger(StatManager.class);	
	private final Supermarket supermarket;
	public StatManager(Supermarket _supermarket){
		this.supermarket = _supermarket;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		long beginSaleTime = System.currentTimeMillis();
		boolean isRunning = true;
		while(isRunning){
			try {
				if(supermarket.isSoldOut&&supermarket.getCustomerQueue().size()==0){
					logger.info("all of the goods sold out && all customer request handled!");
					isRunning=false;
				}else{
					//stat every 10 seconds
					Thread.sleep(10000);
					stateInfo();
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//wait to finish
		for(Thread cashier:supermarket.getCashierThreads()){
			try {
				cashier.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long endSaleTime = System.currentTimeMillis();
		logger.info("从开始销售到售罄总共时间:"+(endSaleTime-beginSaleTime)/1000+"秒");
		stateInfo();
	}
	private void stateInfo(){
		long custWaitTime = 0;
		long goodWaitTime = 0;
		int custSize =0;
		int goodSize =0;
		for(Cashier cashier:supermarket.getCashierList()){
			synchronized (cashier) {
				custSize+=cashier.getReceiveList().size();
				goodSize+=cashier.getReceiveList().size();
				logger.info(cashier.getName()+"接待的顾客数量:"+cashier.getReceiveList().size());
				for(Customer cust:cashier.getReceiveList()){
					custWaitTime+=cust.getWaitTime();
					goodWaitTime+=cust.getGood().getSoldTime();
				}
			}
		}
		if(custSize!=0&&goodSize!=0){
			logger.info("每个顾客平均等待时间:"+custWaitTime/custSize/1000+"秒");
			logger.info("每个商品平均售出时间:"+goodWaitTime/goodSize/1000+"秒");
		}
	}
}
