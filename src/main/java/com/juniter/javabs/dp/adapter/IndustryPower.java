package com.juniter.javabs.dp.adapter;

/**
 * 标准工业用电
 * 
 * @author Juniter
 *
 */
public class IndustryPower implements IndustryPowerStandard{

	@Override
	public void industryPowerService() {
		System.out.println("Industry Power Standard! 360 Volet!");
	}

}
