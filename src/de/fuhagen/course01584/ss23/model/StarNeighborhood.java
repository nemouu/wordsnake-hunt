package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Implementierung der Schnittstelle INachbarschaft, die eine Nachbarschaft
 * zwischen Feldern beschreibt, die sternförmig im Dschungel angeordnet sind.
 * Hierbei gibt der erste Parameter die maximale Distanz in waage- und
 * senkrechter Richtung vor und der zweite Parameter gibt die maximale Distanz
 * in beiden diagonalen Richtungen vor.
 * 
 * @author Philip Redecker
 *
 */
public class StarNeighborhood implements INeighborhood {
	private String type = "Stern";
	private List<Integer> parameters = new ArrayList<Integer>();

	/**
	 * Ein Konstruktor, dem die in der Klasse genutzten Parameter direkt uebergeben
	 * werden. Bei Instanziierung der Klasse ist also direkt bekannt wie weit und wo
	 * nach Nachbarn gesucht werden soll.
	 * 
	 * @param parameters Eine Liste mit Zahlen, die bestimmen wo die Nachbarfelder
	 *                   eines Feldes liegen.
	 */
	public StarNeighborhood(List<Integer> parameters) {
		super();
		if (parameters.size() != 2) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, die zwei Eintraege hat.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, deren Eintraege positiv sind.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, deren Eintraege nicht groesser als der groesste Integerwert sind.");
		}
		if (parameters.get(0) == 0 && parameters.get(1) == 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann keine"
					+ "Liste uebergeben werden, deren Eintraege beide gleich 0 sind.");
		}
		this.parameters = parameters;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public StarNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle jungle, Field field) {
		List<Field> listOfNeighbors = new ArrayList<Field>();
		int param1 = parameters.get(0);
		int param2 = parameters.get(1);
		Field[][] jungleFields = jungle.getFields();
		for (int i = 1; i <= param1; i++) {
			if (field.getRow() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn()]);
			}
			if (field.getRow() + i < jungleFields.length) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn()]);
			}
			if (field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow()][field.getColumn() - i]);
			}
			if (field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow()][field.getColumn() + i]);
			}
		}
		for (int i = 1; i <= param2; i++) {
			if (field.getRow() - i >= 0 && field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() - i]);
			}
			if (field.getRow() - i >= 0 && field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow() - i][field.getColumn() + i]);
			}
			if (field.getRow() + i < jungleFields.length && field.getColumn() - i >= 0) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() - i]);
			}
			if (field.getRow() + i < jungleFields.length && field.getColumn() + i < jungleFields[0].length) {
				listOfNeighbors.add(jungleFields[field.getRow() + i][field.getColumn() + i]);
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
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, die zwei Eintraege hat.");
		}
		if (parameters.get(0) < 0 || parameters.get(1) < 0) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, derem Eintraege positiv sind.");
		}
		if (parameters.get(0) > Integer.MAX_VALUE - 1 || parameters.get(1) > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException("Fuer die Klasse 'SternNachbarschaft' kann nur eine"
					+ "Liste uebergeben werden, derem Eintraege nicht groesser als der groesste Integerwert sind.");
		}
		this.parameters = parameters;
	}
}