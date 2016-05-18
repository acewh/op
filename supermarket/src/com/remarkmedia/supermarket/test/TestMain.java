package com.remarkmedia.supermarket.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.remarkmedia.supermarket.main.CustomerGenerator;
import com.remarkmedia.supermarket.main.StatManager;
import com.remarkmedia.supermarket.main.Supermarket;


public class TestMain {
	public static void main(String[] args) {
		ExecutorService exec = Executors.newCachedThreadPool();
		//init supermarket name
		Supermarket market = new Supermarket("onepiece_market");
		CustomerGenerator custGener = new CustomerGenerator(market);
		StatManager stat = new StatManager(market);
		//start market
		exec.execute(market);
		//start customer generator
		exec.execute(custGener);
		//start stat manager
		exec.execute(stat);
		exec.shutdown();
	}
}
