package de.fuhagen.course01584.ss23.algorithm;

import de.fuhagen.course01584.ss23.model.*;

/**
 * In dieser Klasse werden Konstruktoren und Methoden bereitgestellt, um die
 * Bewertung von gegebenen Loesungen der Schlangenjagd zu ermoeglichen. Hierbei
 * werden die Punkte aller Schlangenarten und die der Felder, die die
 * zugehoerigen Schlangen belegen, zusammen gezaehlt. Der Nutzer kann sich diese
 * Ergebnis zurueckgeben oder ausdrucken lassen.
 * 
 * @author Philip Redecker
 *
 */
public class SolutionEvaluator {
	private int evaluation = 0;

	/**
	 * Es wird eine neue Instanz der Klasse LoesungsBewerter erstellt.
	 */
	public SolutionEvaluator() {
		super();
	}

	/**
	 * Eine Methode, um eine uebergebene Loesung zu bewerten. Es kann eine beliebige
	 * Loesung uebergeben werden. Ist diese Loesung leer, so wird eine
	 * <code>0</code> zurueckgegeben.
	 * 
	 * @param solution Die Loesung deren Bewertung berechnet werden soll.
	 * @return Die Punkte der zuvor uebergebenen Loesung.
	 */
	public int evaluateSolution(Solution solution) {
		int points = 0;
		for (Snake snake : solution.getSnakes()) {
			points += snake.getType().getPoints();
			for (SnakeElement glied : snake.getElements()) {
				points += glied.getField().getPoints();
			}
		}
		evaluation = points;
		return points;
	}

	/**
	 * Es wird die Bewertung der Loesung auf die Konsole ausgegeben.
	 */
	public void printOutEvaluation() {
		System.out.println("Die uebergebene Loesung hat " + evaluation + " Punkte.");
	}

	/**
	 * Es wird hierdurch die Zugriff auf die Bewertung der letzten Loesung, die
	 * berechnet wurde, ermoeglicht. Dies ist vor allem fuer zukuenftige
	 * Programmerweiterung und beziehungsweise oder Tests gedacht.
	 * 
	 * @return Der Wert der Variable <code>bewertung</code>.
	 */
	public int getEvaluation() {
		return evaluation;
	}
}