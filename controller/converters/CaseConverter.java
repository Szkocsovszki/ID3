package controller.converters;

import java.util.ArrayList;
import java.util.Set;

import controller.informations.CaseInformation;
import controller.informations.CuttingInformation;
import controller.informations.VectorInformation;
import controller.model.Case;
import controller.model.ColumnVector;
import reader.CaseReader;

/**
 * Itt készülnek el az oszlopvektorok a tanító példák alapján.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class CaseConverter {
	
	private static final String CLASSIFICATION = "";
	
	/**
	 * A megadott attribútum vagy az osztályozás lehetséges értékeivel vektorok létrehozása.
	 * 
	 * @param name				az attribútum neve vagy az osztályozás oszlop neve
	 * @param possibleValues	az attribútum lehetséges értékei vagy a lehetséges osztálycímkék
	 */
	private static void createColumnVector(String name, Set<String> possibleValues) {
		for(String value : possibleValues) {
			ColumnVector vector = new ColumnVector(value);
			for(Case currentCase : CuttingInformation.caseList) {
				if(name.equals(CLASSIFICATION)) {
					vector.putValue(currentCase.getClassification().equals(value) ? 1.0 : 0.0);
				} else {
					vector.putValue(currentCase.getAttributeValue(name).equals(value) ? 1.0 : 0.0);
				}
			}
			VectorInformation.vectorList.add(vector);
		}
	}
	
	/**
	 * Kettőnél több osztálycímke esetén az osztályozás vektorok negált változatának előállítása.
	 */
	private static void fillNegatedClassificationVectors() {
		for(String name : VectorInformation.classifications) {
			ColumnVector vector = new ColumnVector("¬" + name);
			ArrayList<Double> values = VectorInformation.getVectorFromVectorListByName(name).getValues();
			for(Double value : values) {
				vector.putValue(Math.abs(value - 1));
			}
			VectorInformation.vectorList.add(vector);
		}
	}
	
	/**
	 * Attribútumértékek és osztálycímkék vektorrá alakítása.
	 * Normál formában adott input esetén kell használni.
	 * Kettőnél több osztálycímke esetén meghívódik az osztályozás vektorok negált változatát elkészítő függvény is.
	 */
	private static void convert() {
		VectorInformation.vectorList = new ArrayList<>();
		for(String attributeName : CaseInformation.attributeNames) {
			createColumnVector(attributeName, CuttingInformation.possibleValuesForAttribute(attributeName));
		}
		createColumnVector(CLASSIFICATION, VectorInformation.classifications);
		if(VectorInformation.moreThanTwoClassifications) {
			fillNegatedClassificationVectors();
		}
	}
	

	/**
	 * Ha az input normál formában volt megadva, akkor meghívja az esetek alapján vektorokat készítő függgvényt.
	 * Ha az input alapból vektoros formában volt megadva, a vektorokba lementett értékek alapján elkészíti a vektorokat.
	 */
	public static void convertCasesToVectors() {
		if(!CaseReader.inputIsInVectorFormat) {
			convert();
		}
		for(ColumnVector vector : VectorInformation.vectorList) {
			vector.createColumnVector();
		}
	}
}
