package com.juniter.javabs.dp.adapter;

/**
 * 电脑
 * @author Juniter
 *
 */
public class PC {
	//电脑正常工作所需要的电源标准
	private PCPowerStandard standard;
	
	public void use() {
		this.standard.powerService();
		System.out.println("Computer Work Well!");
	}
	
	//根据实际情况注入电源来使得电脑正常工作
	public void setStandard(PCPowerStandard standard) {
		this.standard = standard;
	}
}
