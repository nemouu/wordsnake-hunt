package de.fuhagen.course01584.ss23.main;

import java.util.List;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen dem Programm und
 * dem Nutzer des Programmes. Es soll in einer implementierten Klasse moeglich
 * sein, das Programm durch eine Konsole beziehungsweise ein Terminal zu nutzen
 * und es soll moeglich sein, das Programm im API Modus ueber diese
 * Schnittstelle zu nutzen, indem dieses Programm beispielsweise als
 * <code>.jar</code> Datei eingebunden wird.
 * 
 * @author Philip Redecker
 *
 */
public interface SnakeHuntAPI {
	/**
	 * Liest die vorgegebene Eingabedatei mit einer vollstaendigen Probleminstanz
	 * und startet das Loesungverfahren fuer die Schlangensuche. Die gefundene
	 * Loesung wird zusammen mit der Probleminstanz in der Ausgabedatei gespeichert.
	 * 
	 * @param xmlInputFile Dateipfad zu einer XML-Datei mit der Probleminstanz,
	 *                        die geloest werden soll.
	 * @param xmlOutputFile Dateipfad zu einer XML-Datei fuer die Probleminstanz
	 *                        und die erzeugte Loesung.
	 * @return <code>true</code>, wenn mindestens eine Schlange gefunden wurde,
	 *         ansonsten <code>false</code>. Beim Auftreten eines Fehlers wird
	 *         ebenfalls <code>false</code> zurückgegeben.
	 */
	public boolean solveProblem(String xmlInputFile, String xmlOutputFile);

	/**
	 * Liest die Vorgegebene Eingabedatei mit einer (moeglicherweise
	 * unvollstaendigen) Probleminstanz und erzeugt eine neue Probleminstanz auf
	 * Basis der gegebenen Parameter. Die erzeugte Probleminstanz wird in der
	 * vorgegebenen Ausgabedatei gespeichert.
	 * 
	 * @param xmlInputFile Dateipfad zu einer XML-Datei mit Parametern fuer eine
	 *                        Probleminstanz, die erzeugt werden soll.
	 * @param xmlOutputFile Dateipfad zu einer XML-Datei fuer die erzeugte
	 *                        Probleminstanz.
	 * @return <code>true</code>, bei Erfolg, ansonsten <code>false</code>. Beim
	 *         Auftreten eines Fehlers wird ebenfalls <code>false</code>
	 *         zurückgegeben.
	 */
	public boolean generateProblem(String xmlInputFile, String xmlOutputFile);

	/**
	 * Moegliche Fehlertypen fuer eine Loesung einer Probleminstanz.
	 * <ul>
	 * <li><code>GLIEDER</code>: Eine Schlange besteht nicht aus der richtigen
	 * Anzahl von Schlangengliedern.</li>
	 * <li><code>ZUORDNUNG</code>: Ein Schlangenglied ist einem Feld mit einem
	 * falschen Zeichen zugeordnet.</li>
	 * <li><code>VERWENDUNG</code>: Ein Schlangenglied ist einem bereits maximal
	 * verwendeten Feld zugeordnet.</li>
	 * <li><code>NACHBARSCHAFT</code>: Ein Schlangenglied befindet sich nicht in der
	 * Nachbarschaft des jeweils vorherigen Schlangengliedes.</li>
	 * </ul>
	 */
	public enum ErrorType {
		/**
		 * Eine Schlange besteht nicht aus der richtigen Anzahl von Schlangengliedern.
		 */
		ELEMENTS,
		/**
		 * Ein Schlangenglied ist einem Feld mit falschem Zeichen zugeordnet.
		 */
		ASSIGNMENT,
		/**
		 * Ein Schlangenglied ist einem bereits maximal verwendeten Feld zugeordnet.
		 */
		USAGE,
		/**
		 * Ein Schlangenglied befindet sich nicht in der Nachbarschaft des jeweils
		 * vorherigen Schlangengliedes.
		 */
		NEIGHBORHOOD
	}

	/**
	 * Liest die Probleminstanz und Loesung aus der gegebenen Datei ein und
	 * ueberprueft die Loesung auf Zulaessingkeit. Dabei werden sowohl die Art als
	 * auch die Haeufigkeit der verletzten Bedingungen ermittelt.
	 * 
	 * @param xmlInputFile Dateipfad zu einer XML-Datei mit einer Probleminstanz
	 *                        und der zugehoerigen Loesung.
	 * @return Liste der gefundenen Einzelfehler. Beim Auftreten eines Fehlers wird
	 *         eine leere Liste zurueckgegeben.
	 */
	public List<ErrorType> examineSolution(String xmlInputFile);

	/**
	 * Liest die Probleminstanz und Loesung aus der gegebenen Datei ein und
	 * berechnet die erreichte Punktzahl. Die Berechnung erfolgt unabhaengig von der
	 * Zulaessigkeit der Loesung. Punkte werden fuer jede gefundene Schlange einer
	 * Schlangenart und fuer jedes von einem Schlangenglied verwendete Feld (fuer
	 * jede Verwendung) vergeben.
	 * 
	 * @param xmlInputFile Dateipfad zu einer XML-Datei mit einer Probleminstanz
	 *                        und Loesung.
	 * @return Erreichte Gesamtpunktzahl. Beim Auftreten eines Fehlers wird der Wert
	 *         <code>0</code> zurueckgegeben.
	 */
	public int evaluateSolution(String xmlInputFile);
}
