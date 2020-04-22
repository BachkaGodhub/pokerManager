package com.bachka.pokerManager.utils.pokerRound;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.bachka.pokerManager.utils.player.IPlayer;
import com.bachka.pokerManager.utils.player.Player;

public class PokerRound {

	private HashMap<String, IPlayer> _players = new HashMap<String, IPlayer>();

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
	}

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
}
