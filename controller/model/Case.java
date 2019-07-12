package controller.model;

import java.util.HashMap;
import java.util.LinkedHashMap;

import controller.informations.CaseInformation;

/**
 * Egy eset egy tanító példának felel meg.
 * Minden eset rendelkezik egy indexszel, attribútértékekkel, és egy osztályozással.
 * Az esetek attribútumértékei tölődhetnek a döntési fa építése során.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class Case {
	private int index;
	private HashMap<String, String> valuesOfTheAttributes;
	private String classification = null;
	
	public Case(String[] attributeValues) {
		this.valuesOfTheAttributes = new LinkedHashMap<>();
		index = ++CaseInformation.numberOfCases;
		for(int i=0; i<CaseInformation.numberOfAttributes; i++) {	
			this.valuesOfTheAttributes.put(CaseInformation.attributeNames.get(i), attributeValues[i]);
		}
		classification = attributeValues[attributeValues.length - 1];
	}
	
	public Case(Case copyCase) {
		index = copyCase.getIndex();
		valuesOfTheAttributes = new HashMap<>();
		for(String attributeName : copyCase.getValuesOfTheAttributes().keySet()) {
			valuesOfTheAttributes.put(attributeName, copyCase.getAttributeValue(attributeName));
		}
		classification = copyCase.getClassification();
	}

	private HashMap<String, String> getValuesOfTheAttributes() {
		return valuesOfTheAttributes;
	}
	
	public int getIndex() {
		return index;
	}
	
	public String getClassification() {
		return classification;
	}

	public String getAttributeValue(String attribute) {
		if(valuesOfTheAttributes.containsKey(attribute)) {
			return valuesOfTheAttributes.get(attribute);
		}
		return "";
	}
	
	public void removeAttribute(String attribute) {
		if(valuesOfTheAttributes.containsKey(attribute)) {
			valuesOfTheAttributes.remove(attribute);
		}
	}
	
	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String toString() {
		return valuesOfTheAttributes.toString() + CaseInformation.className + ": " + classification + "\n";
	}
}
