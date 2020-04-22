import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

class Test {

	@org.junit.jupiter.api.Test
	void test() {

		HashMap<String, Double> gesamt = new HashMap<String, Double>();
		gesamt.put("amgaa", -5.0);
		gesamt.put("chingee", 15.0);
		gesamt.put("tuumk", -10.0);
//		gesamt.put("tsoom", -12.59169991);
//		gesamt.put("tamiraa", -17.0719438);
//		gesamt.put("bachka", 33.87890733);
//		gesamt.put("chinguun", -20.0);

		List<String> ausgleich = new ArrayList<String>();
		
		Map<String, Double> looserList = gesamt.entrySet().stream().filter(map -> map.getValue() < 0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		Map<String, Double> winerList = gesamt.entrySet().stream().filter(map -> map.getValue() > 0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		
		looserList.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
		System.out.println("Winer*********************************************************");
		winerList.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
		System.out.println("**************Schulden rechnen**************");
		while (looserList.size() > 0 && winerList.size() > 0) {
			Entry<String, Double> bayan = maxUsingCollectionsMaxAndLambda(winerList);
			Entry<String, Double> yaduu = minUsingCollectionsMaxAndLambda(looserList);
			boolean rausgehen= false;
			if (bayan.getValue() < 0.01) {
				winerList.remove(bayan.getKey());
				System.out.println("ERROR bayan");
				rausgehen = true;
			}
			if(yaduu.getValue() > -0.01)
			{
				looserList.remove(yaduu.getKey());
				System.out.println("ERROR yaduu");
				rausgehen = true;			
			}
			if(rausgehen)
			{
				continue;
			}
			
			if (bayan.getValue() > Math.abs(yaduu.getValue())) {
				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + Math.abs(yaduu.getValue()));
				
				double schulden = bayan.getValue() + yaduu.getValue();
				winerList.replace(bayan.getKey(), schulden);
				looserList.remove(yaduu.getKey());
				System.out.println("LOG: " + yaduu.getKey() + " erlost " + yaduu.getValue());
				System.out.println("LOG: " + bayan.getKey() + " hat noch " + schulden + " zunehmen");


			} else if (bayan.getValue() == Math.abs(yaduu.getValue())) {
				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + Math.abs(yaduu.getValue()));

				winerList.remove(bayan.getKey());
				looserList.remove(yaduu.getKey());
			} else if (bayan.getValue() < Math.abs(yaduu.getValue())) {
				ausgleich.add(yaduu.getKey() + " -> " + bayan.getKey() + " : " + bayan.getValue());
				
				double schulden = bayan.getValue() + yaduu.getValue();
				System.out.println(bayan.getKey() + " " + yaduu.getKey() + " " + bayan.getValue() + " " + yaduu.getValue() + " " + schulden);
				looserList.replace(yaduu.getKey(), schulden);
				winerList.remove(bayan.getKey());
				System.out.println("LOG: " + bayan.getKey() + " erlost");
				System.out.println("LOG: " + yaduu.getKey() + " hat noch " + schulden + " zuzahlen");
				
			}
			System.out.println("WinnerList size : " + winerList.size());
			System.out.println("Looser size : " + looserList.size());

		}
		ausgleich.forEach((v)-> System.out.println(v));
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

	private static Map<String, Double> sortByValue(Map<String, Double> unsortMap, final boolean order) {
		List<Entry<String, Double>> list = new LinkedList<>(unsortMap.entrySet());

		// Sorting the list based on values
		list.sort((o1, o2) -> order
				? o1.getValue().compareTo(o2.getValue()) == 0 ? o1.getKey().compareTo(o2.getKey())
						: o1.getValue().compareTo(o2.getValue())
				: o2.getValue().compareTo(o1.getValue()) == 0 ? o2.getKey().compareTo(o1.getKey())
						: o2.getValue().compareTo(o1.getValue()));
		return list.stream().collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}

}
