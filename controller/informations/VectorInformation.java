package controller.informations;

import java.util.ArrayList;
import java.util.Set;

import controller.model.ColumnVector;

/**
 * A VectorInformation a beolvasott tanító példák alapján készített vektorokról nyújt információt.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class VectorInformation {
	public static ArrayList<ColumnVector> vectorList = null;
	private static ArrayList<ColumnVector> copyVectorList = null;
	public static boolean moreThanTwoClassifications = false;
	public static Set<String> classifications = null;
	public static Set<String> negatedClassifications = null;
	
	/**
	 * Eredetileg előállított vektorok lementése.
	 * Az attribútumok törlésekor a hozzájuk tartozó vektorok is törlődnek.
	 * Több döntési fa készítése esetén így visszakapható az eredetileg létrejött vektorok listája. 
	 */
	public static void saveVectorList() {
		copyVectorList = new ArrayList<>();
		for(ColumnVector vector : vectorList) {
			copyVectorList.add(new ColumnVector(vector));
		}
	}
	
	/**
	 * Vektorlista visszaállítása az eredetileg létrehozott vektorlistára.
	 */
	public static void restoreVectorList() {
		vectorList = new ArrayList<>();
		for(ColumnVector vector : copyVectorList) {
			vectorList.add(new ColumnVector(vector));
		}
	}
	
	/**
	 * Vektor lekérdezése a neve alapján.
	 * 
	 * @param name	a szükséges vektor neve
	 * @return		az adott névvel rendelkező vektor
	 */
	public static ColumnVector getVectorFromVectorListByName(String name) {
		return getVectorFromVectorListByName(name, vectorList);
	}
	
	/**
	 * Vektor lekérdezése a neve alapján az adott listából.
	 * 
	 * @param name	a keresett vektor neve
	 * @param list	a lista, amiben a vektort keresni kell
	 * @return		a keresett vektor, ha benne van a listában, különben null
	 */
	public static ColumnVector getVectorFromVectorListByName(String name, ArrayList<ColumnVector> list) {
		for(ColumnVector i : list) {
			if(i.getName().equals(name)) {
				return i;
			}
		}
		return null;
	}
}
