package com.remarkmedia.supermarket.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

/**
 * 
 * @description 
 * @author ACE 
 * @date 2016-5-16
 */
public class Supermarket implements Runnable{
	private Logger logger = Logger.getLogger(Supermarket.class);
	private String name;
	public Supermarket(String _name){
		this.name = _name;
	}
	/**
	 * customer waiting queue
	 */
	private LinkedBlockingDeque<Customer> customerQueue = new LinkedBlockingDeque<Customer>();
	/**
	 * supermarket repository
	 */
	private Map<String,LinkedList<Good>> repository = new HashMap<String, LinkedList<Good>>();	
	private List<Cashier> cashierList = new ArrayList<Cashier>();
	private List<Thread> cashierThreads = new ArrayList<Thread>();
	protected volatile boolean isSoldOut = false;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info(name+"    started!!!!");
		logger.info("start to init the Cashiers!!!");
		Cashier cashierA = new Cashier(this,"Cashier_A");
		Cashier cashierB = new Cashier(this,"Cashier_B");
		Cashier cashierC = new Cashier(this,"Cashier_C");
		cashierList.add(cashierA);
		cashierList.add(cashierB);
		cashierList.add(cashierC);
		Thread threadA =new Thread(cashierA);
		Thread threadB =new Thread(cashierB);
		Thread threadC =new Thread(cashierC);
		cashierThreads.add(threadA);
		cashierThreads.add(threadB);
		cashierThreads.add(threadC);
		threadA.start();
		threadB.start();
		threadC.start();
		initGoods("Apple",15);
		initGoods("Macbook",15);
		initGoods("Cookie",15);
		
		
		//generate customers
//		new Thread(new CustomerGenerator(this)).start();
//		long beginSaleTime =System.currentTimeMillis();
//		Random rand = new Random();
//		int customerIndex = 0;
//		try {
//			threadA.join();
//			threadB.join();
//			threadC.join();			
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		long endSaleTime = System.currentTimeMillis();
//		long custWait = (cashierA.getCustWait() + cashierB.getCustWait() + cashierC.getCustWait())/45;				
//		long goodWait = (cashierA.getGoodWait()+cashierB.getGoodWait()+cashierC.getGoodWait())/45;
//		//顾客按购买商品后时间开始计算
//		logger.info("每个顾客平均等待时间:"+custWait/1000+"秒");
//		//商品按入库时间开始计算
//		logger.info("每个商品平均售出时间:"+goodWait/1000+"秒");
//		logger.info("从开始销售到售罄总共时间:"+(endSaleTime-beginSaleTime)/1000+"秒");
//		logger.info("CashierA接待的顾客数量:"+cashierA.getReceiveList().size());
//		logger.info("CashierB接待的顾客数量:"+cashierB.getReceiveList().size());
//		logger.info("CashierC接待的顾客数量:"+cashierC.getReceiveList().size());
	}
	public List<Thread> getCashierThreads() {
		return cashierThreads;
	}
	public List<Cashier> getCashierList() {
		return cashierList;
	}
	/**
	 * initialize 
	 * @param name name of goods
	 * @param count 
	 */
	public void initGoods(String name,long count){
		LinkedList<Good> list = new LinkedList<Good>();
		repository.put(name,list);		
		for(int i=1;i<=count;i++){
			Good good = new Good(name);
			list.add(good);
		}
		logger.info("init "+name+","+count+"success!!!");
	}
	/**
	 * get a good randomly
	 * @return
	 */
	public Good getRandomGood(){
		List<String> remainGoods = new ArrayList<String>();
		for(String key:repository.keySet()){
			List goodList = repository.get(key);
			if(goodList.size()!=0){
				remainGoods.add(key);
			}
		}
		if(remainGoods.size()==0){
			logger.info("all of the goods sold out !!!!!");
			isSoldOut=true;			
			return null;
		}else if(remainGoods.size()==1){
			String goodName = remainGoods.get(0);
			return repository.get(goodName).pollFirst(); 
		}else{
			Random rand = new Random();
			int seed = rand.nextInt(remainGoods.size());
			String goodName = remainGoods.get(seed);
			return repository.get(goodName).pollFirst();
		}
	}
	/**
	 * get the customer queue
	 * @return
	 */
	public LinkedBlockingDeque<Customer> getCustomerQueue() {
		return customerQueue;
	}	
	public Map<String, LinkedList<Good>> getRepository() {
		return repository;
	}
	public void setRepository(Map<String, LinkedList<Good>> repository) {
		this.repository = repository;
	}
}
