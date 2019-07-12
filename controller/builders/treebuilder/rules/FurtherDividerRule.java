package controller.builders.treebuilder.rules;

import java.util.ArrayList;
import java.util.Set;
import java.util.Stack;

import controller.informations.CaseInformation;
import controller.informations.CuttingInformation;
import controller.model.Case;
import controller.model.TreeElement;
import controller.operations.VectorOperations;

/**
 * Az ID3 továbbontó szabályának megvalósítása.
 * Itt kerül összeállításra a fa elemeket tartalmazó verem is, ami alapján az építő egység fel tudja majd építeni a döntési fát.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class FurtherDividerRule {
	private static Stack<TreeElement> tree;
	
	/**
	 * Az esetlista alapján fa elemeket tartalmazó verem készítése.
	 * 
	 * @param caseList	esetlista, amit fel kell dolgozni
	 * @return			az esetlista alapján előállított, fa elemeket tartalmazó verem
	 */
	public static Stack<TreeElement> divider(ArrayList<Case> caseList) {
		tree = new Stack<>();
		ArrayList<Case> i = new ArrayList<>();
		Stack<ArrayList<Case>> stack = new Stack<>();
		stack.add(caseList);
		while(!stack.isEmpty()) {
			i = stack.pop();
			if(FinishingRule.isFinished(i)) {
				tree.push(new TreeElement("leaf", ClassifierRule.determineTheClassOfTheLeaf(i)));
				for(int j=0; j<i.size(); j++) {
					tree.push(new TreeElement("index", i.get(j).getIndex() + ""));
				}				
			} else {
				CuttingInformation.caseList = i;
				divide(stack);
			}
		}
		return tree;
	}

	/**
	 * Az esetlista szétbontása a legjobban szeparáló attribútum alapján.
	 * A fa elemeket tartalmazó verembe újabb elemek kerülnek.
	 * Törlődik a felhasznált attribútum.
	 * 
	 * @param stack		verem, amibe a szétbontás eredménye kerüljön
	 */
	private static void divide(Stack<ArrayList<Case>> stack) {
		VectorOperations.keepIndexes(CuttingInformation.caseList);
		String attributeToCut = AttributeSelectorRule.selectAttributeForCutting();
		tree.push(new TreeElement("node", attributeToCut));
		Set<String> set = CuttingInformation.possibleValuesForAttribute(attributeToCut);
		for(String value : set) {
			ArrayList<Case> newCaseList = new ArrayList<>();
			tree.push(new TreeElement("edge", value));
			for(Case currentCase : CuttingInformation.caseList) {
				if(currentCase.getAttributeValue(attributeToCut).equals(value)) {
					currentCase.removeAttribute(attributeToCut);
					newCaseList.add(currentCase);
				}
			}
			stack.push(newCaseList);
		}
		CaseInformation.deleteAttribute(attributeToCut);
	}
}
