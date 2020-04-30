package com.bachka.pokerManager.utils.pokerRound;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.bachka.pokerManager.utils.player.IPlayer;

public class PokerRound implements IPokerRound {

	private static final AtomicInteger count = new AtomicInteger(0); 

	private HashMap<String, IPlayer> _players = new HashMap<String, IPlayer>();

	int roundNumber;
		
	public PokerRound(IPlayer player, IPlayer... morePlayers) {
		_players.put(player.getName(), player);

		if (morePlayers != null && morePlayers.length > 0) {
			for (IPlayer p : morePlayers)
				_players.put(p.getName(), p);
		}
	}

	public PokerRound(List<IPlayer> list) {
		for (IPlayer p: list)
		{
			_players.put(p.getName(), p);
		}
		
		roundNumber = count.incrementAndGet();
	}

	public int getRoundNumber() {
		return roundNumber;
	}

	@Override
	public HashMap<String, IPlayer> getPlayers() {
		return _players;
	}

	public void buyIn(IPlayer p, double euro) {
		_players.put(p.getName(), _players.get(p.getName()).buyIn(euro));
	}

	public double getTotalEuro() {
		double totalEuro = 0;

		for (Entry<String, IPlayer> p : _players.entrySet()) {
			totalEuro = totalEuro + p.getValue().getEuroTotalBuyIn();
		}

		return totalEuro;
	}

	public double getTotalChips() {
		double totalChips = 0;

		for (Entry<String, IPlayer> p : _players.entrySet()) {
			totalChips = totalChips + p.getValue().getChips();
		}

		return totalChips;
	}

	public void calcProfit() {
		double chipCost = getTotalEuro()/getTotalChips();
		_players.forEach((k,v)-> _players.get(k).calcProfit(chipCost));
	}
	
	public HashMap<String, Double> getPlayerListProfit() {
		HashMap<String, Double> _playersProfit = new HashMap<String, Double>();
		this.calcProfit();
		for (IPlayer p : getPlayers().values()) {
			_playersProfit.put(p.getName(), p.getProfit());
			System.out.println("PP " + p.getName() + ": " + p.getProfit());
		}

		return _playersProfit;
	}
}
