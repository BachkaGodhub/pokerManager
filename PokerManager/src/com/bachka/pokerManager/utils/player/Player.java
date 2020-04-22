package com.bachka.pokerManager.utils.player;

public class Player implements IPlayer {

	private String name;
	private double euroTotalBuyIn;
	private int chips;
	private double profit;
	private int countGame;
	private int id;

	public Player(String name, double euro, int chips, double profit, int count) {
		setName(name);
		setEuroTotalBuyIn(euro);;
		setChips(chips);
		setProfit(profit);
		setCountGame(count);
	}
	
	public Player(String name, double euro, int chips) {
		this(name, euro, chips, 0.0, 0);
	}

	public Player(String _name) {
		this(_name, 0.0, 0, 0.0, 0);
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public double getEuroTotalBuyIn() {
		return euroTotalBuyIn;
	}

	@Override
	public void setEuroTotalBuyIn(double d) {
		this.euroTotalBuyIn= d;
	}

	@Override
	public int getChips() {
		return chips;
	}

	@Override
	public void setChips(int chips) {
		this.chips = chips;
	}

	@Override
	public double getProfit() {
		return profit;
	}

	@Override
	public void setProfit(double profit) {
		this.profit = profit;
	}

	public Player buyIn(double euro) {
		euroTotalBuyIn = euroTotalBuyIn + euro;
		return this;
	}

	public double calcEuroStand(double chipCost) {
		return chips * chipCost;
	}

	public void calcProfit(double chipCost) {
		profit = Math.round((calcEuroStand(chipCost) - euroTotalBuyIn) * 100) / 100.00;
	}

	public int getCountGame() {
		return countGame;
	}

	public void setCountGame(int countGame) {
		this.countGame = countGame;
	}
	
	public int getId()
	{
		return id;
	}

	public void setId(Integer aValue) {
		this.id = aValue;
	}
}
