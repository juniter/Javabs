package com.juniter.javabs.dp.adapter;

/**
 * 标准家庭用电
 * 
 * @author Juniter
 *
 */
public class HomePower implements HomePowerStandard {

	@Override
	public void homePowerService() {
		System.out.println("Home Power Standard! 220 Volet!");
	}

}
