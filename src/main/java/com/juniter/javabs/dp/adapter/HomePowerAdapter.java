package com.juniter.javabs.dp.adapter;

/**
 * 家用电源适配器，实现了电脑标准电源接口，通过接口的powerService()方法
 * 
 * 用家用电源来为电脑供电
 * 
 * @author Juniter
 *
 */
public class HomePowerAdapter implements PCPowerStandard {

	private HomePower homePower;

	public HomePowerAdapter(HomePower homePower) {
		this.homePower = homePower;
	}

	@Override
	public void powerService() {
		this.homePower.homePowerService();
		// do power transform
		System.out.println("Transform HomePower To Computer Standard Power!");
	}

}
