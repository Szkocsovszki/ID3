package controller.informations;

import java.util.ArrayList;

import controller.model.Case;

/**
 * A CaseInformation a beolvasott esetekről biztosít különböző információkat.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class CaseInformation {
	public static int numberOfCases = 0;
	public static ArrayList<String> attributeNames = null;
	private static ArrayList<String> savedAttributeNames = null;
	public static int numberOfAttributes = 0;
	public static String className;
	public static String positiveClassification = "";
	public static String negativeClassification = "";
	
	/**
	 * Beolvasott attribútumnevek, illetve az osztályozás oszlop nevének lementése.
	 * Erre azért van szükség, mert az attribútumnevek felhasználás után törlésre kerülnek.
	 * Több döntési fa előállításakor lesz szükség a visszaállításra.
	 * 
	 * @param attributeNames	a beolvasott attribútumnevek
	 */
	public static void saveAttributeNames(String[] attributeNames) {
		CaseInformation.savedAttributeNames = new ArrayList<>();
		numberOfAttributes = attributeNames.length - 1;
		for(int i=0; i<numberOfAttributes; i++) {
			CaseInformation.savedAttributeNames.add(attributeNames[i]);
		}
		className = attributeNames[attributeNames.length - 1];
	}
	
	/**
	 * Lehetséges osztálycímkék beállítása.
	 * Egy döntési fában legfeljebb két osztályozás lehet: a pozitív és a negatív osztályozás.
	 * 
	 * @param caseList	az eseteket tartalmazó lista, melynek minden eleme rendelkezik egy osztályozással
	 */
	public static void setPossibleClassifications(ArrayList<Case> caseList) {
		ArrayList<String> classificationList = new ArrayList<>();
		for(int i=0; i<caseList.size(); i++) {
			String classification = caseList.get(i).getClassification(); 
			if(!classificationList.contains(classification)) {
				classificationList.add(classification);
			}
		}
		if(classificationList.size() == 2) {
			positiveClassification = classificationList.get(0);
			negativeClassification = classificationList.get(1);
		}
	}
	
	/**
	 * Az attribútumnevek beállítása a lementett attribútumnevek alapján.
	 */
	public static void setAttributeNames() {
		attributeNames = new ArrayList<>();
		for(int i=0; i<savedAttributeNames.size(); i++) {
			attributeNames.add(savedAttributeNames.get(i));
		}
	}
	
	/**
	 * Átadott attribútumnév törlése az attribútumlistából.
	 * 
	 * @param attribute	a törlendő attribútumnév
	 */
	public static void deleteAttribute(String attribute) {
		attributeNames.remove(attribute);
	}
	
}
