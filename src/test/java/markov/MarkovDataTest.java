package markov;

import static org.junit.Assert.*;

import org.junit.Test;

public class MarkovDataTest {

    @Test
    public void read_addToListEmpty_listNotEmpty() {
        MarkovData data = new MarkovData();

        assertEquals(0, data.keyWordSize());
        data.read("a b c");
        assertEquals(3, data.keyWordSize());
    }
    
    @Test
    public void read_addWord_wordInList() {
        MarkovData data = new MarkovData();

        data.read("a b c d e f g");
        assertEquals("d", data.getKeyWord(3));
    }

    @Test
    public void renforceWord_newWord_false() {
        MarkovData data = new MarkovData();

        assertEquals(false, data.renforceWord("a", "b"));
    }

    @Test
    public void renforceWord_oldWord_true() {
        MarkovData data = new MarkovData();
        
        data.learnWord("a","b");
        assertEquals(true, data.renforceWord("a", "b"));
    }
    
    @Test
    public void renforceWord_addWord_sizeChangeProperly() {
        MarkovData data = new MarkovData();
        
        String word1 = "bonjour", word2 = "test";
        
        data.learnWord("a","bonjour");
        assertEquals(word1.length(), data.getLearnedWord("a").length());
        data.renforceWord("a","test");
        assertEquals(word1.length()+1+word2.length(), data.getLearnedWord("a").length());
    }

}
