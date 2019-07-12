package reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Stack;

import controller.converters.CaseConverter;
import controller.converters.StackMaker;
import controller.converters.ValueSelector;
import controller.informations.CaseInformation;
import controller.informations.CuttingInformation;
import controller.informations.VectorInformation;
import controller.model.Case;
import controller.model.ColumnVector;

/**
 * A CaseReader feladata a reader.input mappábából a megadott input fájl tartalmát, vagyis a tanító halmazt
 * megfelelő módon beolvasni. Az input lehet normál vagy vektoros formában adott.
 * Bizonyos, a döntési fa előállítását segítő információk is beállításra kerülnek.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class CaseReader {
	public static boolean inputIsInVectorFormat = false;
	
	/**
	 * Vektoros formájú input beolvasása.
	 * Közben bizonyos, a döntési fa előállításához szükséges információk beállítódnak.
	 * 
	 * @param source		a vektoros formájú tanító halmaz
	 * @return				tanító példák listába rendezve
	 * @throws IOException
	 */
	private static ArrayList<Case> readInVectorFormat(BufferedReader source) throws IOException {
		VectorInformation.vectorList = new ArrayList<>();
		String line = source.readLine();
		String[] possibleValues = line.split(";");
		ValueSelector.valuesOfTheAttributes = new LinkedHashMap<>();
		for(int i=0; i<CaseInformation.numberOfAttributes + 1; i++) {
			String[] valueNames = possibleValues[i].split(",");
			if(i < CaseInformation.numberOfAttributes) {
				ValueSelector.valuesOfTheAttributes.put(CaseInformation.attributeNames.get(i), valueNames);
			}
			else {
				ValueSelector.valuesOfTheAttributes.put(CaseInformation.className, valueNames);
			}
			for(int j=0; j<valueNames.length; j++) {
				VectorInformation.vectorList.add(new ColumnVector(valueNames[j]));
			}
		}
		while ((line = source.readLine()) != null) {
			String[] currentCase = line.split(";");
			int vectorIndex = 0;
			for(int i=0; i<currentCase.length; i++) {
				String[] values = currentCase[i].split(",");
				for(int j=0; j<values.length; j++) {
					VectorInformation.vectorList.get(vectorIndex++).putValue(Double.parseDouble(values[j]));
				}
			}
		}
		CaseConverter.convertCasesToVectors();
		return ValueSelector.selectValueForAttributes();
	}
	
	/**
	 * Normál formájú input beolvasása.
	 * Közben bizonyos, a döntési fa előállításához szükséges információk beállítódnak.
	 * 
	 * @param source		a normál formájú tanító halmaz 
	 * @return				tanító példák listába rendezve
	 * @throws IOException
	 */
	private static ArrayList<Case> readInNonVectorFormat(BufferedReader source) throws IOException {
		String line;
		ArrayList<Case> caseList = new ArrayList<Case>();
		VectorInformation.vectorList = new ArrayList<>();
		VectorInformation.classifications = new HashSet<>();
		int numberOfClassifications = 0;
		while ((line = source.readLine()) != null) {
			String[] currentCase = line.split(";");
			caseList.add(new Case(currentCase));
			for(int i=0; i<currentCase.length; i++) {
				boolean isNewVector = true;
				for(int j=0; j<VectorInformation.vectorList.size(); j++) {
					if(VectorInformation.vectorList.get(j).getName().equals(currentCase[i])) {
						isNewVector = false;
						break;
					}
				}
				if(isNewVector) {
					VectorInformation.vectorList.add(new ColumnVector(currentCase[i]));
					if(i == currentCase.length - 1) {
						if(!VectorInformation.classifications.contains(currentCase[i])) {
							VectorInformation.classifications.add(currentCase[i]);
							numberOfClassifications++;
						}
					}
				}
			}
		}
		if(numberOfClassifications > 2) {
			VectorInformation.moreThanTwoClassifications = true;
			VectorInformation.negatedClassifications = new HashSet<>();
			for(String classification : VectorInformation.classifications) {
				VectorInformation.vectorList.add(new ColumnVector("¬" + classification));
				VectorInformation.negatedClassifications.add("¬" + classification);
			}
		}
		CuttingInformation.caseList = caseList;
		CaseConverter.convertCasesToVectors();
		if(numberOfClassifications > 2) {
			VectorInformation.saveVectorList();
		}
		return caseList;
	}
	
	/**
	 * Az input beolvasása.
	 * Az input formája alapján a megfelelő beolvasó függvény hívódik meg.
	 * Előáll az esetek listája, ami az osztályozás alapú feldolgozás után egy verembe kerül.
	 * Közben a döntési fa előállításához szükséges információk beállítódnak.
	 * 
	 * @return	osztályozás alapján feldolgozott esetek listáját tartalmazó verem
	 */
	public static Stack<ArrayList<Case>> reading() {
		Stack<ArrayList<Case>> stack = null;
		ArrayList<Case> caseList = null;
		try {
			BufferedReader source = new BufferedReader(new FileReader(new File("src\\reader\\input\\source.txt")));
			//BufferedReader source = new BufferedReader(new FileReader(new File("src\\reader\\input\\sourceVectorFormat.txt")));
			String line = source.readLine();
			CaseInformation.saveAttributeNames(line.split(";"));
			CaseInformation.setAttributeNames();
			caseList = inputIsInVectorFormat ? readInVectorFormat(source) : readInNonVectorFormat(source);
			CuttingInformation.defaultNumberOfCases = caseList.size();
			stack = StackMaker.createStack(caseList);
			source.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stack;
	}
	
}
