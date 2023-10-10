package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Implementierung der Schnittstelle INachbarschaft, die eine Nachbarschaft
 * zwischen Feldern beschreibt, die auch sehr weit voneinander entfernt sein
 * koennen. Hierbei ist wichtig zu beachten, dass es in dieser Implementierung
 * immer nur zwei Parameter geben kann. Es wird dabei immer nur senkrecht und
 * waagerecht gelaufen, das heisst es gibt immer nur <code>4</code> oder
 * <code>8</code> Nachbarn.
 * 
 * @author Philip Redecker
 *
 */
public class JumpNeighborhood implements INeighborhood {
	private String type = "Sprung";
	private List<Integer> parameters = new ArrayList<Integer>();

	/**
	 * Ein Konstruktor, dem die in der Klasse genutzten Parameter direkt uebergeben
	 * werden. Bei Instanziierung der Klasse ist also direkt bekannt wie weit und wo
	 * nach Nachbarn gesucht werden soll.
	 * 
	 * @param parameters Eine Liste mit Zahlen, die bestimmen wo die Nachbarfelder
	 *                   eines Feldes liegen.
	 */
	public JumpNeighborhood(List<Integer> parameters) {
		super();
		if (parameters.size() != 2) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, die zwei Eintraege hat.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, deren Eintraege positiv sind.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, deren Eintraege nicht groesser als der groesste Integerwert sind.");
		}
		if (parameters.get(0) == 0 && parameters.get(1) == 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann keine"
					+ "Liste uebergeben werden, deren Eintraege beide gleich 0 sind.");
		}
		this.parameters = parameters;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public JumpNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle jungle, Field field) {
		/*
		 * In dieser Methode werden die Nachbarn eines Feldes in einem Dschungel
		 * gefunden. Dabei werden alle mit den Parametern moegliche Kombinationen
		 * ausprobiert und es wird immer geprueft, ob der jeweilige Nachbarn ueberhaupt
		 * noch in den Grenzen des Dschungels liegt. Ausserdem wird beruecksichtigt,
		 * dass bei bestimmten Parametern (zum Beispiel 0 und 2) einige Felder mehrmals
		 * in der Liste mit Nachbarn vorkommen koennten. Deshalb wird immer geprueft, ob
		 * sich das Feld schon in der Liste mit Nachbarn befindet.
		 */
		List<Field> listOfNeighbors = new ArrayList<Field>();
		int param1 = parameters.get(0);
		int param2 = parameters.get(1);
		Field[][] jungleFields = jungle.getFields();
		if (field.getRow() + param1 < jungleFields.length && field.getColumn() + param2 < jungleFields[0].length) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() + param1][field.getColumn() + param2]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() + param1][field.getColumn() + param2]);
			}
		}
		if (field.getRow() + param2 < jungleFields.length && field.getColumn() + param1 < jungleFields[0].length) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() + param2][field.getColumn() + param1]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() + param2][field.getColumn() + param1]);
			}
		}
		if (field.getRow() - param1 >= 0 && field.getColumn() - param2 >= 0) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() - param1][field.getColumn() - param2]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() - param1][field.getColumn() - param2]);
			}
		}
		if (field.getRow() - param2 >= 0 && field.getColumn() - param1 >= 0) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() - param2][field.getColumn() - param1]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() - param2][field.getColumn() - param1]);
			}
		}
		if (field.getRow() - param1 >= 0 && field.getColumn() + param2 < jungleFields[0].length) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() - param1][field.getColumn() + param2]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() - param1][field.getColumn() + param2]);
			}
		}
		if (field.getRow() + param1 < jungleFields.length && field.getColumn() - param2 >= 0) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() + param1][field.getColumn() - param2]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() + param1][field.getColumn() - param2]);
			}
		}
		if (field.getRow() - param2 >= 0 && field.getColumn() + param1 < jungleFields[0].length) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() - param2][field.getColumn() + param1]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() - param2][field.getColumn() + param1]);
			}
		}
		if (field.getRow() + param2 < jungleFields.length && field.getColumn() - param1 >= 0) {
			if (listOfNeighbors.contains(jungleFields[field.getRow() + param2][field.getColumn() - param1]) == false) {
				listOfNeighbors.add(jungleFields[field.getRow() + param2][field.getColumn() - param1]);
			}
		}
		return listOfNeighbors;
	}

	@Override
	public String toString() {
		return type + ", Parameter=(" + parameters.get(0) + ", " + parameters.get(1) + ")";
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public List<Integer> getParameters() {
		return parameters;
	}

	@Override
	public void setParameters(List<Integer> parameters) {
		if (parameters.size() != 2) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, die zwei Eintraege hat.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, derem Eintraege positiv sind.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("Fuer die Klasse 'SprungNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, derem Eintraege nicht groesser als der groesste Integerwert sind.");
		}
		this.parameters = parameters;
	}
}