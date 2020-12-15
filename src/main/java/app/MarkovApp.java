package app;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import markov.MarkovChain;

/**
 * Applicatoin permettant de simuler une chaine de Markov
 * @author Nicolas
 */
public class MarkovApp {
	
	public static void main(String[] args) throws Exception {
        String sentences = readFileAsString("./target/VictorHugo_LesMiserables-II-Cosette.txt");
        System.out.println(sentences);
        
        MarkovChain markov = new MarkovChain(3);
        markov.learn(sentences);
        
        System.out.println("Generation :\n");
        System.out.println(markov.generateMarkov(6));
        System.out.println(markov.generateMarkov(9));
        System.out.println(markov.generateMarkov(12));
	}

    public static String readFileAsString(String fileName) throws Exception { 
        String data = ""; 
        Path path = Paths.get(fileName); 
        data = new String(Files.readAllBytes(path)); // Je suis en java 8 :(
        return data; 
    } 
    
}
