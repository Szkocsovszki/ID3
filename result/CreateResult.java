package result;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import controller.model.DecisionTree;

/**
 * Itt történik az előállt döntési fa vagy fák megfelelő formátumban való fájlba kiírása.
 * Az eredmények a result.results mappában lesznek megtalálhatóak.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class CreateResult {
	/**
	 * Döntési fa csúcsainak új sorba írása az eredmény fájlon belül.
	 * 
	 * @param result		az eredmény fájl
	 * @param nodes			a döntési fa csúcsai
	 * @throws IOException
	 */
	private static void writeWithNewLines(BufferedWriter result, String[] nodes) throws IOException {
		for(String node : nodes) {
	        result.write(node);
	        result.newLine();
	    }
	}
	
	/**
	 * Ahány döntési fa állt elő, annyi eredmény fájl készítése.
	 * A result.results mappa korábbi tartalma törlődni fog.
	 * 
	 * @param results	az előállt döntési fák
	 */
	public static void createResult(ArrayList<DecisionTree> results) {
		try {
			for(File file : new File("src\\result\\results").listFiles()) { 
			    if (!file.isDirectory()) {
			        file.delete();
			    }
			}
			if(results.size() == 1) {
				BufferedWriter result = new BufferedWriter(new FileWriter("src\\result\\results\\result.txt"));
			    writeWithNewLines(result, results.get(0).toString().split(";"));
				result.close();
			} else {
				for(int i=0; i<results.size(); i++) {
					BufferedWriter result = new BufferedWriter(new FileWriter("src\\result\\results\\result" + (i+1) + ".txt"));
					writeWithNewLines(result, results.get(i).toString().split(";"));
					result.close();
				}
			}
			System.out.println("Results created in the result/results directory.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
