package controller.model;

/**
 * A fa elemeket a FurtherDividerRule állítja elő, és a TreeBuilder fogja felhasználni a döntési fa létrehozásához.
 * Egy fa elem típusa lehet: node, edge, leaf, index.
 * Egy fa elem értéke lehet: megnevezés (node, egde vagy leaf esetén) vagy érték (index esetén).
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class TreeElement {
	private String type = "";
	private String value = "";
	
	public TreeElement(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	public TreeElement(TreeElement copy) {
		this.type = copy.type;
		this.value = copy.value;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}
	
	public String toString() {
		return type + ": " + value;
	}
	
}
