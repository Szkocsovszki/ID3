package controller.converters;

import java.util.ArrayList;
import java.util.HashMap;

import controller.informations.VectorInformation;
import controller.model.Case;

/**
 * Vektoros formában adott input esetén, ha a tanító példák nem egy konkrét attribútum értékkel rendelkeznek, 
 * hanem minden attribútum értéket valamilyen mértékben tartalmaznak, akkor a döntési fa előállításához
 * ki kell választani, hogy a tanító példa melyik lehetséges attribútum értékkel rendelkezzen. 
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class ValueSelector {
	public static HashMap<String, String[]> valuesOfTheAttributes = null;
	
	/**
	 * A legnagyobb mértékben tartalmazott lehetséges érték kiválasztása.
	 * Az érték meghatározása a 0.5 küszöbérték alkalmazásával történik. 
	 * 
	 * @return	az esetek listája, ahol az egyes eseteknek már be van állítva, hogy az attribútum melyik lehetséges értékével rendelkeznek
	 */
	public static ArrayList<Case> selectValueForAttributes() {
		ArrayList<Case> caseList = new ArrayList<>();
		for(int i=0; i<VectorInformation.vectorList.size() - 1; i++) {
			String[] caseValues = new String[valuesOfTheAttributes.size()];
			int actualAttribute = 0;
			for(String attribute : valuesOfTheAttributes.keySet()) {
				int needsFurtherInvestigation = 0;
				String[] values = valuesOfTheAttributes.get(attribute);
				double max = VectorInformation.getVectorFromVectorListByName(values[0]).getVector()[i];
				String maxName = values[0];
				for(String name : values) {
					double value = VectorInformation.getVectorFromVectorListByName(name).getVector()[i];
					if(value >= 0.5) {
						caseValues[actualAttribute++] = name;
						break;
					} else {
						needsFurtherInvestigation++;
						if(value >= max) {
							max = value;
							maxName = name;
						}
						if(needsFurtherInvestigation == values.length) {
							caseValues[actualAttribute++] = maxName;
							break;
						}
					}
				}
			}
			caseList.add(new Case(caseValues));
		}
		return caseList;
	}
}
