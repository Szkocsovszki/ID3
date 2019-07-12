package controller.informations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import controller.model.Case;

/**
 * A CuttingInformation a csúcshoz tartozó eseteket tárolja, vagyis az aktuális tanító halmazt.
 * Továbbá megadja egy lekérdezett attribútum lehetséges értékeit.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class CuttingInformation {
	public static int defaultNumberOfCases = 0;
	public static ArrayList<Case> caseList = null;
	
	/**
	 * Attribútum lehetséges értékeit megadó függvény.
	 * 
	 * @param attributeName	az attribútum, melynek lehetséges értékeit szeretnénk megtudni
	 * @return				az attribútum lehetséges értékeinek halmaza
	 */
	public static Set<String> possibleValuesForAttribute(String attributeName) {
		Set<String> set = new HashSet<>();
		for(int i=0; i<caseList.size(); i++) {
			set.add(caseList.get(i).getAttributeValue(attributeName));
		}
		return set;
	}
}
