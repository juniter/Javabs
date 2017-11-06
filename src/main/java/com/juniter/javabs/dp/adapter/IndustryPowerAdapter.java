package com.juniter.javabs.dp.adapter;

/**
 * 工业电源适配器，实现了电脑标准电源接口，通过接口的powerService()方法
 * 
 * 用工业电源来为电脑供电
 * 
 * @author Juniter
 *
 */
public class IndustryPowerAdapter implements PCPowerStandard {

	private IndustryPower industryPower;

	public IndustryPowerAdapter(IndustryPower industryPower) {
		this.industryPower = industryPower;
	}

	@Override
	public void powerService() {
		this.industryPower.industryPowerService();
		// do power transform
		System.out.println("Transform IndustryPower To Computer Standard Power!");
	}

}
