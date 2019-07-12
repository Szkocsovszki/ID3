package controller.model;

import java.util.ArrayList;

import controller.informations.CuttingInformation;

/**
 * Az oszlopvektor megadja egy attribútum egy lehetséges értékének előfordulását a tanító példákban.
 * A vektort a neve alapján tudjuk elérni, ami megegyezik az attribútum adott lehetséges értékének elnevezésével.
 * A vektor a döntési fa építése során változni fog attól függően, hogy az aktuális tanító halmaz melyik eseteket tartalmazza.
 * 
 * @author Szkocsovszki Zsolt
 *
 */
public class ColumnVector {
	private String name = "";
	private double[] vector;
	private double[] startVector;
	private double sum = 0.0;
	private boolean[] indexes;
	private ArrayList<Double> values;
	
	public ColumnVector(String name, double[] vector) {
		int size = vector.length;
		this.name = name;
		this.vector = new double[size];
		this.startVector = new double[size];
		this.indexes = new boolean[size];
		for(int i=0; i<size; i++) {
			this.vector[i] = vector[i];
			this.startVector[i] = vector[i];
			this.indexes[i] = true;
			sum += vector[i];
		}
	}
	
	public ColumnVector(String name) {
		this.name = name;
		this.vector = null;
		this.values = new ArrayList<>();
	}
	
	public ColumnVector(ColumnVector copy) {
		int size = copy.vector.length;
		this.name = copy.name;
		this.vector = new double[size];
		this.startVector = new double[size];
		this.indexes = new boolean[size];
		for(int i=0; i<size; i++) {
			this.vector[i] = copy.vector[i];
			this.startVector[i] = vector[i];
			this.indexes[i] = true;
			sum += vector[i];
		}
	}

	public String getName() {
		return name;
	}

	public double[] getVector() {
		return vector;
	}
	
	public double getSum() {
		return sum;
	}
	
	public ArrayList<Double> getValues() {
		return values;
	}
	
	public void putValue(double value) {
		values.add(value);
	}
	
	public void createColumnVector() {
		int size = values.size();
		this.indexes = new boolean[size];
		this.vector = new double[size];
		this.startVector = new double[size];
		for(int i=0; i<size; i++) {
			double value = values.get(i);
			this.indexes[i] = true;
			this.vector[i] = value;
			this.startVector[i] = value;
			sum += value;
		}
	}
	
	public void deleteCases(ArrayList<Integer> indexes) {
		for(int i=0; i<CuttingInformation.defaultNumberOfCases; i++) {
			this.indexes[i] = true;
		}
		int size = CuttingInformation.defaultNumberOfCases - indexes.size();
		this.vector = new double[size];
		for(Integer index : indexes) {
			this.indexes[index - 1] = false;
		}
		int index = 0;
		for(int i=0; i<this.indexes.length; i++) {
			if(this.indexes[i]) {
				this.vector[index++] = this.startVector[i];
			}
		}
	}
	
	public String toString() {
		String vectorString = "(";
		try {
			for(int i=0; i<vector.length; i++) {
				vectorString += vector[i] + (i == vector.length - 1 ? ")" : " ");
			}
		} catch(NullPointerException e) {
			vectorString += ")";
		}
		return name + vectorString;
	}
	
}
