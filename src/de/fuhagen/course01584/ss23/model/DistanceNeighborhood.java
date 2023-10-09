package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Implementierung der Schnittstelle INachbarschaft, die eine Nachbarschaft
 * zwischen direkt aneinander grenzenden Feldern beschreibt. Hierbei werden alle
 * Felder betrachten die ueber, unter, neben und diagonal zum Ausgangsfeld
 * liegen. Wie viele Nachbarn hinzugefuegt werden haengt von dem eingegebenen
 * Parameter, also der Distanz ab.
 * 
 * @author Philip Redecker
 *
 */
public class DistanceNeighborhood implements INeighborhood {
	private String art = "Distanz";
	private List<Integer> parameter = new ArrayList<Integer>();

	/**
	 * Ein Konstruktor, dem der in der Klasse genutzte Parameter direkt uebergeben
	 * wird. Bei Instanziierung der Klasse ist also direkt bekannt wie weit nach
	 * Nachbarn gesucht werden soll.
	 * 
	 * @param parameter Eine Zahl, die bestimmt wo die Nachbarfelder eines Feldes
	 *                  liegen.
	 */
	public DistanceNeighborhood(int parameter) {
		super();
		if (parameter < 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ " Elemente haben, die negativ sind.");
		}
		if (Integer.MAX_VALUE - 1 < parameter) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ "Elemente haben, die groesser als der groesste Integerwert sind.");
		}
		this.parameter.add(parameter);
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public DistanceNeighborhood() {
		super();
	}

	@Override
	public List<Field> getNachbarn(Jungle dschungel, Field feld) {
		/*
		 * In dieser Methode werden die Nachbarn eines Feldes in einem Dschungel
		 * gefunden. Dabei werden alle Felder, die in der Entfernung des Parameters vom
		 * Ursprungsfeld liegen betrachtet. Dabei wird immer geprueft, ob der jeweilige
		 * Nachbarn ueberhaupt noch in den Grenzen des Dschungels liegt. Ausserdem
		 * werden hierbei manche Felder moeglicherweise mehrmals betrachtet, deshalb
		 * wird immer gerpueft, ob sich ein bestimmtes Feld schon in der Liste mit
		 * Nachbarn befindet.
		 */
		List<Field> listeMitNachbarn = new ArrayList<Field>();
		int param = parameter.get(0);
		Field[][] felder = dschungel.getFelder();
		for (int i = 0; i < param + 1; i++) {
			for (int j = 0; j < param + 1; j++) {
				if (i == 0 && j == 0) {
				} else {
					if (feld.getZeile() + i < felder.length && feld.getSpalte() + j < felder[0].length) {
						if (listeMitNachbarn.contains(felder[feld.getZeile() + i][feld.getSpalte() + j]) == false) {
							listeMitNachbarn.add(felder[feld.getZeile() + i][feld.getSpalte() + j]);
						}
					}
					if (feld.getZeile() - i >= 0 && feld.getSpalte() - j >= 0) {
						if (listeMitNachbarn.contains(felder[feld.getZeile() - i][feld.getSpalte() - j]) == false) {
							listeMitNachbarn.add(felder[feld.getZeile() - i][feld.getSpalte() - j]);
						}
					}
					if (feld.getZeile() + i < felder.length && feld.getSpalte() - j >= 0) {
						if (listeMitNachbarn.contains(felder[feld.getZeile() + i][feld.getSpalte() - j]) == false) {
							listeMitNachbarn.add(felder[feld.getZeile() + i][feld.getSpalte() - j]);
						}
					}
					if (feld.getZeile() - i >= 0 && feld.getSpalte() + j < felder[0].length) {
						if (listeMitNachbarn.contains(felder[feld.getZeile() - i][feld.getSpalte() + j]) == false) {
							listeMitNachbarn.add(felder[feld.getZeile() - i][feld.getSpalte() + j]);
						}
					}
				}
			}
		}
		return listeMitNachbarn;
	}

	@Override
	public String toString() {
		return art + ", Parameter=" + parameter.get(0);
	}

	@Override
	public String getArt() {
		return art;
	}

	@Override
	public List<Integer> getParameter() {
		return parameter;
	}

	@Override
	public void setParameter(List<Integer> parameters) {
		if (parameters.size() > 1) {
			throw new IllegalArgumentException("Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters'"
					+ "nicht mehr als ein Element haben");
		}
		if (parameters.get(0) < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ " Elemente haben, die negativ sind.");
		}
		if (Integer.MAX_VALUE - 1 < parameters.get(0)) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ "Elemente haben, die groesser als der groesste Integerwert sind");
		}
		this.parameter = parameters;
	}

	/**
	 * Eine Methode mit der es moeglich ist einen einzelnen Parameter zu setzen.
	 * Dies ist vor allem in dieser Implementierung der Schnittstelle nuetzlich, da
	 * hier eigentlich nur ein Parameter gebraucht wird. Die Methode wird
	 * hauptsaechlich zum Testen der Klasse und der anderen Methoden der Klasse
	 * verwendet.
	 * 
	 * @param parameter Eine Zahl, die als Parameter uebergeben werden soll.
	 */
	public void setParameterEinzeln(int parameter) {
		if (parameter < 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ " Elemente haben, die negativ sind.");
		}
		if (Integer.MAX_VALUE - 1 < parameter) {
			throw new IllegalArgumentException(
					"Fuer die Klasse DistanzNachbarschaft darf das Attribut 'parameters' keine "
							+ "Elemente haben, die groesser als der groesste Integerwert sind");
		}
		this.parameter.set(0, parameter);
	}
}