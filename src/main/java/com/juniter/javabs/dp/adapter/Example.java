package com.juniter.javabs.dp.adapter;

public class Example {
	public static void main(String args[]) {
		//Adapter HomePower
		PC pc = new PC();
		pc.setStandard(new HomePowerAdapter(new HomePower()));
		pc.use();
		
		//Adapter IndustryPower
		pc.setStandard(new IndustryPowerAdapter(new IndustryPower()));
		pc.use();
	}
}
