package markov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MarkovData conserve l'historique des successions de mots dans une hashmap
 * @author Nicolas
 */
public class MarkovData {
	private Map<String, String> markovTable = new HashMap<String,String>();
	private List<String> textwords = new ArrayList<String>();

	public void read(String text) {
		List<String> newWords = Arrays.asList(text.split(" "));
		this.textwords.addAll(newWords);
	}
	
	public boolean renforceWord(String keyString, String wordToLearn) {
		String value;
		boolean isNewKey = containsKeyWord(keyString);
		
		// if chain in list then add extra word
		if(isNewKey){
			value = getLearnedWord(keyString);
			value += " " + wordToLearn;
			learnWord(keyString, value);
		}
		return isNewKey;
	}

	String getLearnedWord(String keyString) {
		return this.markovTable.get(keyString);
	}

	boolean containsKeyWord(String keyString) {
		return this.markovTable.containsKey(keyString);
	}

	public void learnWord(String keyString, String learnWord) {
		this.markovTable.put(keyString, learnWord);
	}
	
	public String getKeyWord(int k) {
		return this.textwords.get(k);
	}

	public int keyWordSize() {
		return this.textwords.size();
	}
}