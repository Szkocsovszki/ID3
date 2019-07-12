package controller.builders.treebuilder.rules;

import java.util.ArrayList;

import controller.model.Case;

/**
 * Az ID3 osztályozó szabályának megvalósítása.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class ClassifierRule {
	public static String determineTheClassOfTheLeaf(ArrayList<Case> caseList) {		
		return caseList.get(0).getClassification();
	}
}
