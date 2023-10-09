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
	private String art = "Stern";
	private List<Integer> parameter = new ArrayList<Integer>();

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
		this.parameter = parameters;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public StarNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNeighbors(Jungle dschungel, Field feld) {
		List<Field> listeMitNachbarn = new ArrayList<Field>();
		int param1 = parameter.get(0);
		int param2 = parameter.get(1);
		Field[][] felder = dschungel.getFields();
		for (int i = 1; i <= param1; i++) {
			if (feld.getRow() - i >= 0) {
				listeMitNachbarn.add(felder[feld.getRow() - i][feld.getColumn()]);
			}
			if (feld.getRow() + i < felder.length) {
				listeMitNachbarn.add(felder[feld.getRow() + i][feld.getColumn()]);
			}
			if (feld.getColumn() - i >= 0) {
				listeMitNachbarn.add(felder[feld.getRow()][feld.getColumn() - i]);
			}
			if (feld.getColumn() + i < felder[0].length) {
				listeMitNachbarn.add(felder[feld.getRow()][feld.getColumn() + i]);
			}
		}
		for (int i = 1; i <= param2; i++) {
			if (feld.getRow() - i >= 0 && feld.getColumn() - i >= 0) {
				listeMitNachbarn.add(felder[feld.getRow() - i][feld.getColumn() - i]);
			}
			if (feld.getRow() - i >= 0 && feld.getColumn() + i < felder[0].length) {
				listeMitNachbarn.add(felder[feld.getRow() - i][feld.getColumn() + i]);
			}
			if (feld.getRow() + i < felder.length && feld.getColumn() - i >= 0) {
				listeMitNachbarn.add(felder[feld.getRow() + i][feld.getColumn() - i]);
			}
			if (feld.getRow() + i < felder.length && feld.getColumn() + i < felder[0].length) {
				listeMitNachbarn.add(felder[feld.getRow() + i][feld.getColumn() + i]);
			}
		}
		return listeMitNachbarn;
	}

	@Override
	public String toString() {
		return art + ", Parameter=(" + parameter.get(0) + ", " + parameter.get(1) + ")";
	}

	@Override
	public String getType() {
		return art;
	}

	@Override
	public List<Integer> getParameters() {
		return parameter;
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
		this.parameter = parameters;
	}
}