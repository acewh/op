/**
 * 
 */
package com.remarkmedia.supermarket.test;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.remarkmedia.supermarket.main.Supermarket;


/**
 * @description 
 * @author ZORO
 * @date 2016-5-18
 */
public class SupermarketTest {
	private static Supermarket supermarket;
	@BeforeClass
	public static void testInitGoods(){
		supermarket = new Supermarket("onepiece");
		supermarket.initGoods("Apple",15);
		supermarket.initGoods("Macbook",15);
		supermarket.initGoods("Cookie",15);
	}
	@Test
	public void testGetRandomGood(){
		for(int i =0;i<45;i++){
			supermarket.getRandomGood();
		}
		Assert.assertTrue(supermarket.getRandomGood()==null);			
	}
}
