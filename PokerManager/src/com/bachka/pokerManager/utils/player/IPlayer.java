package com.bachka.pokerManager.utils.player;

public interface IPlayer {

	public String getName();
	
	void setName(String name);
	
	public double getChips();
	
	public void setChips(double name);
	
	public double getEuroTotalBuyIn();
	
	public void calcProfit(double chipCost);

	void setProfit(double profit);

	double getProfit();

	void setEuroTotalBuyIn(double d);

	public IPlayer buyIn(double euro);

	
}
