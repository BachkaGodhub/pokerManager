package com.bachka.pokerManager.utils.pokerRound;

import java.util.HashMap;

import com.bachka.pokerManager.utils.player.IPlayer;

public interface IPokerRound {

	public HashMap<String, IPlayer> getPlayers();
	
	public void buyIn(IPlayer p, double euro);
	
	public double getTotalChips();
	
	public double getTotalEuro();
	
	public void calcProfit();
	
	public int getRoundNumber();
	
	public HashMap<String, Double> getPlayerListProfit();
}
