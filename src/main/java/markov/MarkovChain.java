package markov;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * MarkovChain est la classe simulant une chaine de markov
 * @author Nicolas
 */
public class MarkovChain {
	
	/** Markov chain ngram parameter (contiguous sequence of words) */
	private int ngram;
	/** Seed generator for randow  */
	private Random rgen = new Random();
	/** Seed generator for randow  */
    private MarkovData data = null;

	public MarkovChain (int ngram){
		this.ngram = ngram;
		this.data = new MarkovData();
	}

	public void learn(String text) {
		data.read(text);
		
		// Learn until last ngram words
        int maxwords = data.keyWordSize() - ngram - 1;
        String keyString = null;
        int end;
        
        // Search next tuple of ngram words
        for (int j = 0; j < maxwords; j++) {
            keyString = "";
            end = j + ngram;
            for(int k = j; k < end; k++) {
                keyString = keyString + data.getKeyWord(k) + " ";
            }
			keyString = keyString.trim(); // get rid of trailing spaces
			
			String wordToLearn = data.getKeyWord(end);
			
			boolean isNewKey = data.renforceWord(keyString, wordToLearn);
			if(!isNewKey) {
				if(end <= maxwords)	{
					data.learnWord(keyString, wordToLearn);
				}
			}
        }
	}

    public String generateMarkov(int numWords) {
        // Build a random string using the above Markov chain table
        String buffer = "";
        String newword = "";
        String keyString = "";
        
        // Initialize random number generator
        List<String> lastwords = new ArrayList<String>();
        int possible = data.keyWordSize() - ngram;
		int startnum = rgen.nextInt(possible);
		
		// Get the random start word chain of ngram words
        for (int i = startnum, j = 0; i < startnum+ngram; i++,j++){ 	
        	newword = data.getKeyWord(i);
			lastwords.add(j, newword);
            buffer += newword + " ";
        }

        // Loop until numWords was generated
        for(int i = ngram; i < numWords; i++){
            keyString = "";
            
            // Generate the 'key string' 
            for (int j = 0; j < ngram; j++) {
                keyString = keyString + lastwords.get(j) + " ";
            }
            // Get rid of trailing spaces
			keyString = keyString.trim();		
			
			// If ngram in list then add next word
			if(data.containsKeyWord(keyString))	{
		        List<String> possiblenext = new ArrayList<String>();
                possiblenext = Arrays.asList(data.getLearnedWord(keyString).split(" "));
                int c = possiblenext.size();	// Must be at least 1
               	int r = rgen.nextInt(c);
                
                String nextword = possiblenext.get(r);
                buffer += nextword +" ";
                for (int j = 0; j < ngram-1; j++) {
                	// shift words to the left
                    lastwords.set(j, lastwords.get(j+1));	
                }
                // Add the next word to end
                lastwords.set(ngram-1, nextword);			
            }
        }

         return buffer.trim();
    }

	public void setRgen(Random rgen) {
		this.rgen = rgen;
	}    
}
