package controller.builders.treebuilder;

import java.util.ArrayList;
import java.util.Stack;

import controller.builders.treebuilder.rules.FurtherDividerRule;
import controller.informations.CaseInformation;
import controller.informations.VectorInformation;
import controller.model.Case;
import controller.model.DecisionTree;
import controller.model.DecisionTree.Edge;
import controller.model.DecisionTree.Index;
import controller.model.DecisionTree.Node;
import controller.model.TreeElement;
import result.CreateResult;

/**
 * A TreeBuilder építi fel a döntési fát vagy fákat az ID3 szabályainak felhasználásával.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class TreeBuilder {	
	public TreeBuilder(Stack<ArrayList<Case>> stack) {
		ArrayList<Case> caseList;
		ArrayList<DecisionTree> results = new ArrayList<>();
		while(!stack.isEmpty()) {
			caseList = stack.pop();
			CaseInformation.setPossibleClassifications(caseList);
			results.add(buildTree(caseList));
			if(VectorInformation.moreThanTwoClassifications) {
				CaseInformation.setAttributeNames();
				VectorInformation.restoreVectorList();
			}
		}
		CreateResult.createResult(results);
	}

	/**
	 * A döntési fát felépítő algoritmus.
	 * 
	 * @param caseList	esetlista, ami alapján fel kell építeni a döntési fát
	 * @return			a felépített döntési fa
	 */
	private DecisionTree buildTree(ArrayList<Case> caseList) {
		Stack<TreeElement> treeElementStack = FurtherDividerRule.divider(caseList);
		Stack<Index> indexStack = new Stack<>();
		Stack<Node> nodeStack = new Stack<>();
		Stack<Node> preparedNodeStack = new Stack<>();
		Stack<Node> finishedNodeStack = new Stack<>();
		Node node;
		while(!treeElementStack.isEmpty()) {
			TreeElement element = new TreeElement(treeElementStack.pop());
			switch(element.getType()) {
				case "index":
					indexStack.push(new Index(element.getValue()));
					break;
				case "leaf":
					ArrayList<Index> indexes = new ArrayList<>();
					while(!indexStack.isEmpty()) {
						indexes.add(indexStack.pop());
					}
					nodeStack.push(new Node(element.getValue(), indexes));
					break;
				case "edge":
					node = new Node(nodeStack.pop());
					node.setAncestorEdge(new Edge(element.getValue()));
					preparedNodeStack.push(node);
					break;
				case "node":
					node = new Node(element.getValue());
					ArrayList<Edge> edges = new ArrayList<>();
					while(!preparedNodeStack.isEmpty()) {
						Node preparedNode = new Node(preparedNodeStack.pop());
						preparedNode.setAncestor(new Node(element.getValue()));
						finishedNodeStack.push(preparedNode);
						edges.add(new Edge(preparedNode.getAncestorEdge()));	
					}
					node.setDescendantEdges(edges);
					if(treeElementStack.isEmpty()) {
						finishedNodeStack.push(node);
					} else {
						nodeStack.push(node);
					}
					break;
			}
		}
		return new DecisionTree(finishedNodeStack);
	}
	
}
