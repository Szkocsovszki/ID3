package controller.converters;

import java.util.ArrayList;
import java.util.Stack;

import controller.model.Case;

/**
 * Itt készül el a TreeBuilder számára az esetlistákat tartalmazó verem.
 * Ha az inputban két lehetséges osztályozás volt, akkor csak egy lista kerül a verembe.
 * Kettőnél több osztályozás esetén annyi lista kerül a verembe, ahány lehetséges osztálycímke volt az inputban,
 * továbbá átcímkézésre kerülnek a nem megfelelő osztálycímkék, hogy egy listában csak két lehetséges osztálycímke legyen.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class StackMaker {
	/**
	 * Lehetséges osztálycímkék meghatározása.
	 * 
	 * @param caseList	esetlista
	 * @return			az esetlistában található osztálycímkék halmaza
	 */
	private static ArrayList<String> getPossibleClassifications(ArrayList<Case> caseList) {
		ArrayList<String> classificationList = new ArrayList<>();
		for(int i=0; i<caseList.size(); i++) {
			String classification = caseList.get(i).getClassification(); 
			if(!classificationList.contains(classification)) {
				classificationList.add(classification);
			}
		}
		return classificationList;
	}
	
	/**
	 * Verem előállítása a lehetséges osztálycímkék száma alapján.
	 * 
	 * @param caseList	az input szereplő esetek listája
	 * @return			osztálycímkék száma alapján előállított verem
	 */
	public static Stack<ArrayList<Case>> createStack(ArrayList<Case> caseList) {
		Stack<ArrayList<Case>> stack = new Stack<>();
		ArrayList<String> possibleClassifications = getPossibleClassifications(caseList);
		if(possibleClassifications.size() > 2) {
			for(String classification : possibleClassifications) {
				ArrayList<Case> i = new ArrayList<>();
				for(Case currentCase : caseList) {
					if(currentCase.getClassification().equals(classification)) {
						i.add(new Case(currentCase));
					} else {
						Case newCase = new Case(currentCase);
						newCase.setClassification("¬" + classification);
						i.add(newCase);
					}
				}
				stack.push(i);
			}
		} else {
			stack.push(caseList);
		}
		return stack;
	}
}
