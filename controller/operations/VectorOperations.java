package controller.operations;

import java.util.ArrayList;

import controller.informations.CaseInformation;
import controller.informations.CuttingInformation;
import controller.informations.VectorInformation;
import controller.model.Case;
import controller.model.ColumnVector;

/**
 * A fa előállítása során a vektorok kezeléséhez használt speciális műveletek itt vannak megvalósítva.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class VectorOperations {
	/**
	 * Skaláris szorzat.
	 * 
	 * @param v1	első vektor
	 * @param v2	második vektor
	 * @return		a két vektor skaláris szorzatával előállt vektor
	 */
	private static double[] scalarMultiplication(double[] v1, double[] v2) {
		int size = CuttingInformation.caseList.size();
		double[] vector = new double[size];
		for(int i=0; i<size; i++) {
			vector[i] = v1[i] * v2[i];
		}		
		return vector;
	}
	
	/**
	 * Attribútumok lehetséges értékeihez tartozó vektorok skaláris összeszorzása a pozitív illetve negatív osztályozás vektorokkal.
	 * 
	 * @param positiveClassificationVector	a pozitív osztályozás vektor
	 * @param negativeClassificationVector	a negatív osztályozás vektor
	 * @param r_plus						ebbe kerüljenek a pozitív osztályozás vektorral való skaláris szorzat eredményei
	 * @param r_minus						ebbe kerüljenek a negatív osztályozás vektorral való skaláris szorzat eredményei
	 */
	public static void scalarMultiplicationWithClassVectors(
			ColumnVector positiveClassificationVector, ColumnVector negativeClassificationVector, 
			ArrayList<ColumnVector> r_plus, ArrayList<ColumnVector> r_minus
	) {
		double[] vector = new double[CuttingInformation.caseList.size()];
		String name;
		for(String attributeName : CaseInformation.attributeNames) {
			for(String value : CuttingInformation.possibleValuesForAttribute(attributeName)) {
				name = VectorInformation.getVectorFromVectorListByName(value).getName();
				vector = VectorInformation.getVectorFromVectorListByName(value).getVector();
				r_plus.add(
						new ColumnVector(
							CaseInformation.positiveClassification + name, 
							scalarMultiplication(vector, positiveClassificationVector.getVector())
						)
				);
				r_minus.add(
						new ColumnVector(
							CaseInformation.negativeClassification + name, 
							scalarMultiplication(vector, negativeClassificationVector.getVector())
						)
				);
			}
		}
	}
	
	/**
	 * Vektorok módosítása az aktuális ágon releváns esetek alapján. 
	 * 
	 * @param caseList	aktuális esetlista
	 */
	public static void keepIndexes(ArrayList<Case> caseList) {
		ArrayList<Integer> indexes = new ArrayList<>();
		ArrayList<Integer> toDelete = new ArrayList<>();
		for(Case currentCase : caseList) {
			indexes.add(currentCase.getIndex());
		}
		for(int index=1; index<=CuttingInformation.defaultNumberOfCases; index++) {
			if(!indexes.contains(index)) {
				toDelete.add(index);
			}
		}
		if(toDelete.size() > 0) {
			for(ColumnVector vector : VectorInformation.vectorList) {
				vector.deleteCases(toDelete);
			}
		}
	}
}
