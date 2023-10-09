package de.fuhagen.course01584.ss23.algorithm;

import de.fuhagen.course01584.ss23.model.*;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Schlangensuche,
 * Hauptkomponente und Datenmodell. Dadurch ist es moeglich in Zukunft die
 * Schlangensuche zu erweitern, anzupassen oder zu ersetzen durch eine andere
 * Implementierung. Die Schnittstelle wird so eingesetzt, dass die konkrete
 * Implementierung der Klasse SchlangeSuche austauschbar bleibt.
 * 
 * @author Philip Redecker
 *
 */
public interface ISnakeSearch {

	/**
	 * Eine Methode zum Suchen von Schlangen in einem gegebenen Dschungel. Es soll
	 * so lange nach Schlangen gesucht werden bis der Dschungel komplett durchsucht
	 * worden ist oder bis ein vorgegebenes Zeitlimit erreicht worden ist. Die
	 * Loesung wird in einer private Variablen <code>Loesung</code> gespeichert.
	 */
	void sucheSchlangen();

	/**
	 * Es wird der Wert der Variablen <code>funktionen</code> zurueckgegeben.
	 * Hierbei handelt es sich um eine weitere Schnittstelle, weswegen in dieser
	 * Klasse Getter und Setter angeboten werden. Dies ist fuer zukuenftige
	 * Programmerweiterung und beziehungsweise oder Tests gedacht.
	 * 
	 * @return Der Wert der Variablen <code>funktionen</code>.
	 */
	ISnakeSearchUtil getFunktionen();

	/**
	 * Es kann der Wert der Variablen <code>funktionen</code> gesetzt. Hierbei
	 * handelt es sich um eine weitere Schnittstelle, weswegen in dieser Klasse
	 * Getter und Setter angeboten werden. Dies ist fuer zukuenftige
	 * Programmerweiterung und beziehungsweise oder Tests gedacht.
	 * 
	 * @param funktionen Eine Instanz einer Klasse, die das Interface
	 *                   <code>ISchlangenSucheUtil</code> implementiert.
	 */
	void setFunktionen(ISnakeSearchUtil funktionen);

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell in der Schlangesuche
	 * befindet. Dies ist vor allem fuer kuenftige Aenderungen und auch fuer damit
	 * einhergehende Tests gedacht.
	 * 
	 * @return Der Wert der Variablen <code>modell</code>.
	 */
	IModel getModell();

	/**
	 * Es kann das Modell der Schlangesuche gesetzt werden. So ist es zum Beispiel
	 * moeglich ein Modell zu uebergeben, auch wenn zunaechst der parameterlose
	 * Konstruktor genutzt wurde. Es ist auch im Allgemeinen moeglich das Modell
	 * nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param modell Das Modell, das uebergeben werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	void setModell(IModel modell) throws IllegalArgumentException;

	/**
	 * Es wird die Loesung zurueckgegeben, die die Schlangensuche gefunden hat.
	 * Hierbei ist zu beachten, dass der Aufruf dieser Methode erst sinnvoll ist,
	 * nachdem die Schlangensuche ueberhaupt nach einer Loesung gesucht hat. Wird
	 * dies Methode vorher aufgerufen, wird moeglicherweise eine unvollstaendige
	 * Loesung zurueckgegeben.
	 * 
	 * @return Der Wert der Variable <code>loesung</code>.
	 */
	Solution getLoesung();

	/**
	 * Es wird der Zeitpunkt zurueckgegeben, an dem die Schlangensuche gestartet
	 * wurde. Dies ist hauptsaechlich fuer die Nutzung dieser Klasse in der
	 * Hauptkomponente dieses Programmes noetig.
	 * 
	 * @return Der Wert der Variable <code>aktZeit</code>.
	 */
	Long getAktZeit();

}