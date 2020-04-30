package com.bachka.pokerManager.utils.pokerday;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.bachka.pokerManager.utils.pokerRound.IPokerRound;

public class PokerNight implements IPokerNight {

	private HashMap<Integer, IPokerRound> _allRounds = new HashMap<Integer, IPokerRound>();
	HashMap<String, Double> total = new HashMap<String, Double>();
	
	public HashMap<String, Double> getTotal() {
		return total;
	}

	@Override
	public IPokerNight addRound(IPokerRound newRound, IPokerRound... values) {
		
		_allRounds.put(newRound.getRoundNumber(), newRound);
		
		for (IPokerRound pr : values)
		{
			_allRounds.put(pr.getRoundNumber(), pr);
		}
		return this;
	}
	
	@Override
	public void calcTotalChipsPerPerson() {
		
		_allRounds.forEach((k, round) ->{
			round.getPlayerListProfit().forEach((key, value) -> {
				if(total.get(key) != null)
				{
					double temp = total.get(key);
					total.put(key, value + temp);
				}
				else
				{
					total.put(key, value);
				}
			});			
		});
	
	}
	
	public List<String> schuldenRechnen()
    {
    	List<String> ausgleich = new ArrayList<String>();
    	
    	calcTotalChipsPerPerson();
		
		Map<String, Double> loserList = total.entrySet().stream().filter(map -> map.getValue() < 0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		Map<String, Double> winnerList = total.entrySet().stream().filter(map -> map.getValue() > 0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		loserList.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
		System.out.println("Winner*********************************************************");
		winnerList.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
		System.out.println("**************Schulden rechnen**************");
		while (loserList.size() > 0 && winnerList.size() > 0) {
			Entry<String, Double> bayan = maxUsingCollectionsMaxAndLambda(winnerList);
			Entry<String, Double> yaduu = minUsingCollectionsMaxAndLambda(loserList);
			boolean rausgehen= false;
			if (bayan.getValue() < 0.01) {
				winnerList.remove(bayan.getKey());
				System.out.println("ERROR bayan");
				rausgehen = true;
			}
			if(yaduu.getValue() > -0.01)
			{
				loserList.remove(yaduu.getKey());
				System.out.println("ERROR yaduu");
				rausgehen = true;			
			}
			if(rausgehen)
			{
				continue;
			}
			
			if (bayan.getValue() > Math.abs(yaduu.getValue())) {
				double schulden = bayan.getValue() + yaduu.getValue();
				winnerList.replace(bayan.getKey(), schulden);
				loserList.remove(yaduu.getKey());
				System.out.println("LOG: " + yaduu.getKey() + " erlost " + yaduu.getValue());
				System.out.println("LOG: " + bayan.getKey() + " hat noch " + schulden + " zunehmen");

				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + Math.abs(yaduu.getValue()));

			} else if (bayan.getValue() == Math.abs(yaduu.getValue())) {
				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + Math.abs(yaduu.getValue()));

				winnerList.remove(bayan.getKey());
				loserList.remove(yaduu.getKey());
			} else if (bayan.getValue() < Math.abs(yaduu.getValue())) {
				double schulden = bayan.getValue() + yaduu.getValue();
				System.out.println(bayan.getKey() + " " + yaduu.getKey() + " " + bayan.getValue() + " " + yaduu.getValue() + " " + schulden);
				loserList.replace(yaduu.getKey(), schulden);
				winnerList.remove(bayan.getKey());
				System.out.println("LOG: " + bayan.getKey() + " erlost");
				System.out.println("LOG: " + yaduu.getKey() + " hat noch " + schulden + " zuzahlen");
				
				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + bayan.getValue());
			}
			
		}
		ausgleich.forEach((v)-> System.out.println(v));
		return ausgleich; 
    }
    
	public Entry<String, Double> maxUsingCollectionsMaxAndLambda(Map<String, Double> map) {
		Optional<Entry<String, Double>> maxEntry = map.entrySet().stream()
				.max(Comparator.comparing(Map.Entry::getValue));
		return maxEntry.get();
	}

	public Entry<String, Double> minUsingCollectionsMaxAndLambda(Map<String, Double> map) {
		Optional<Entry<String, Double>> minEntry = map.entrySet().stream()
				.min(Comparator.comparing(Map.Entry::getValue));
		return minEntry.get();
	}
}
