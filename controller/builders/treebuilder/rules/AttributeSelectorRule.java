package controller.builders.treebuilder.rules;

import java.util.ArrayList;

import controller.informations.CaseInformation;
import controller.informations.CuttingInformation;
import controller.informations.VectorInformation;
import controller.model.ColumnVector;
import controller.operations.VectorOperations;

/**
 * Az ID3 attribútum-választó szabályának megvalósítása.
 * A választott attribútum a legjobban szeparáló attribútum lesz.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class AttributeSelectorRule {
	
	/**
	 * Bizonytalansági mérték
	 * 
	 * @param w_plus
	 * @param xki_minus	
	 * @param w_minus
	 * @param xki_plus
	 * @return			a bizonytalansági mérték
	 */
	private static double vagueness(double w_plus, double xki_minus, double w_minus, double xki_plus) {
		return (xki_plus*xki_minus) / (w_plus*xki_plus + w_minus*xki_minus);
	}
	
	/**
	 * Bizonytalansági mérték kiszámítása a lehetséges attribútumokra
	 * 
	 * @param vaguenessList						az attribútumok bizonytalanságai ebbe a listába fognak kerülni
	 * @param positiveClassificationVector		a pozitív osztályozás vektor
	 * @param negativeClassificationVector		a negatív osztályozás vektor
	 * @param r_plus							az attribútumokhoz tartozó lehetséges értékek vektora skalárisan összeszorozva 
	 * 											a pozitív osztályozás vektorával
	 * @param r_minus							az attribútumokhoz tartozó lehetséges értékek vektora skalárisan összeszorozva 
	 * 											a negatív osztályozás vektorával
	 */
	private static void createVaguenessList(
			ArrayList<Double> vaguenessList, 
			ColumnVector positiveClassificationVector, ColumnVector negativeClassificationVector, 
			ArrayList<ColumnVector> r_plus, ArrayList<ColumnVector> r_minus
	) {
		int size = CuttingInformation.caseList.size();
		double w_plus = (double) positiveClassificationVector.getSum() / size;
		double w_minus = (double) negativeClassificationVector.getSum() / size;
		double xki_plus, xki_minus;
		double attributeVagueness;
		String name;
		for(String attributeName : CaseInformation.attributeNames) {
			attributeVagueness = 0;
			for(String value : CuttingInformation.possibleValuesForAttribute(attributeName)) {
				name = VectorInformation.getVectorFromVectorListByName(value).getName();
				xki_plus = VectorInformation.getVectorFromVectorListByName(CaseInformation.positiveClassification + name, r_plus).getSum() / 
						   positiveClassificationVector.getSum();
				xki_minus = VectorInformation.getVectorFromVectorListByName(CaseInformation.negativeClassification + name, r_minus).getSum() / 
						   negativeClassificationVector.getSum();
				attributeVagueness += vagueness(w_plus, xki_minus, w_minus, xki_plus);
			}
			vaguenessList.add(attributeVagueness);
		}
	}
	
	/**
	 * Legjobban szeparáló attribútum kiválasztása.
	 * A legjobban szeparáló attribútum az lesz, amihez a legkisebb bizonytalansági mérték tartozik.
	 * 
	 * @return	a legjobban szeparáló attribútum
	 */
	public static String selectAttributeForCutting() {
		ColumnVector positiveClassificationVector = VectorInformation.getVectorFromVectorListByName(CaseInformation.positiveClassification);
		ColumnVector negativeClassificationVector = VectorInformation.getVectorFromVectorListByName(CaseInformation.negativeClassification);
		ArrayList<ColumnVector> r_plus = new ArrayList<>();
		ArrayList<ColumnVector> r_minus = new ArrayList<>();
		VectorOperations.scalarMultiplicationWithClassVectors(positiveClassificationVector, negativeClassificationVector, r_plus, r_minus);
		ArrayList<Double> vaguenessList = new ArrayList<>();
		createVaguenessList(vaguenessList, positiveClassificationVector, negativeClassificationVector, r_plus, r_minus);
		double min = vaguenessList.get(0);
		int counter = 0, index = 0;
		for(double i : vaguenessList) {
			if(i < min) {
				min = i;
				index = counter;
			}
			counter++;
		}
		return CaseInformation.attributeNames.get(index);
	}
}
