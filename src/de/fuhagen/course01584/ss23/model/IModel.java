package de.fuhagen.course01584.ss23.model;

import java.util.List;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Datenmodell und
 * den anderen Komponenten des Programmes. Dadurch ist es moeglich in Zukunft
 * das Programm durch ein anderes Modell zu erweitern oder das Modell
 * anzupassen, sollte dies gewuenscht sein. Es werden generell viele Getter und
 * Setter angeboten aber abgesehen davon gibt es auch einige Hilfsfunktionen,
 * die die Schlangesuche unterstuetzen.
 * 
 * @author Philip Redecker
 *
 */
public interface IModel {

	/**
	 * Es wird die Zeit des Modelles zurueckgegeben. Hierbei wird entweder eine Zahl
	 * fuer die Zeitvorgabe oder aber zwei Zahlen fuer die Zeitvorgabe und die
	 * Zeitabgabe zurueckgegeben.
	 * 
	 * @return Ein Array, in dem entweder ein Eintrag oder zwei Eintraege fuer die
	 *         Zeitvorgabe beziehungsweise Zeitabgabe stehen.
	 */
	Double[] getTime();

	/**
	 * Durch diese Methode ist das Setzen der Zeit des Modelles auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Einlesen von Daten aber
	 * unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param time Ein Array aus Double, in dem entweder ein oder zwei Eintraege
	 *             stehen. Ein Eintrag fuer die Zeitvorgabe und einer fuer die
	 *             Zeitabgabe.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Zeit
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	void setTime(Double[] time) throws IllegalArgumentException;

	/**
	 * Es wird die Zeiteinheit der Zeitangaben des Modelles zurueckgegeben.
	 * 
	 * @return Ein String, der die Zeiteinheit enthaelt.
	 */
	String getUnitOfTime();

	/**
	 * Durch diese Methode ist das Setzen der Zeiteinheit des Modelles auch nach
	 * Instanziierung moeglich. Es ist darauf zu achten, dass eine gueltige
	 * Zeiteinheit uebergeben wird. Gueltig sind <code>ms</code> (Milisekunden),
	 * <code>s</code>(Sekunden), <code>min</code> (Minuten), <code>h</code>(Stunden)
	 * und <code>d</code>(Tage). Diese Methode dient vor allem dem Einlesen von
	 * Daten aber unter anderem auch dem Testen oder beziehungsweise und der
	 * spaeteren Programmerweiterung.
	 * 
	 * @param unitOfTime Ein String, der die Zeiteinheit enthaelt, die dem Modell
	 *                    uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer
	 *                                  Zeiteinheit uebergeben wird, wird eine
	 *                                  Ausnahme geworfen.
	 */
	void setUnitOfTime(String unitOfTime) throws IllegalArgumentException;

	/**
	 * Es wird der Dschungel des Modelles mit Zeilen, Spalten, Zeichenmenge und
	 * Feldern zurueckgegeben.
	 * 
	 * @return Der Dschungel des Modelles.
	 */
	Jungle getJungle();

	/**
	 * Durch diese Methode ist das Setzen des Dschungels des Modelles auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param jungle Ein Dschungel gemaess der in diesem Paket definierten
	 *                  Datenstruktur Dschungel.
	 */
	void setJungle(Jungle jungle);

	/**
	 * Es werden die Schlangenarten des Modelles zurueckgegeben.
	 * 
	 * @return Eine Liste von Schlangenarten gemaess der in diesem Paket definierten
	 *         Datenstruktur Schlangenart.
	 */
	List<SnakeType> getSnakeTypes();

	/**
	 * Durch diese Methode ist das Setzen der Schlangenarten des Modelles auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param snakeTypes Eine Liste mit Schlangenarten, die dem Dschungel
	 *                       uebergeben werden soll.
	 */
	void setSnakeTypes(List<SnakeType> snakeTypes);

	/**
	 * Eine Methode, die es ermoeglicht dem Modell eine einzelne Schlangenart,
	 * gemaess der in diesem Paket definierten Datenstruktur Schlangenart,
	 * hinzuzufuegen.
	 * 
	 * @param inType Die Schlangenart, die hinzugefuegt werden soll.
	 */
	void addSnakeType(SnakeType inType);

	/**
	 * Es wird der Loesung des Modelles mit den dazugehoerigen Schlangenarten
	 * zurueckgegeben.
	 * 
	 * @return Die Loesung des Modelles, gemaess der in diesem Paket definierten
	 *         Datenstruktur Loesung.
	 */
	Solution getSolution();

	/**
	 * Durch diese Methode ist das Setzen der Loesung des Modelles auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param solution Eine Loesung gemaess der in diesem Paket definierten
	 *                Datenstruktur Loesung.
	 */
	void setSolution(Solution solution);

	/**
	 * Mit dieser Hilfsmethode wird die im Modell stehende Zeit in Nanosekunden
	 * umgerechnet, da die Zeitmessung in diesem Programm in Nanosekunden erfolgt.
	 * Dazu wird die Zeiteinheit mit einbezogen.
	 * 
	 * @param modelSpecification Die Zeitvorgabe aus dem Modell.
	 * @return Eine Zahl, die der Zeitvorgabe in Nanosekunden entspricht.
	 * @throws IllegalArgumentException Wurden im Modell falsche Angaben zur
	 *                                  Zeiteinheit gemacht oder wurden falsche
	 *                                  Parameter uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	Double calculateTimeToNanoseconds() throws IllegalArgumentException;

	/**
	 * Mit dieser Hilfsmethode wird die gemessene Zeit bei der Schlangensuche in die
	 * urspruengliche Zeiteinheit des Modelles umgerechnet. Schliesslich kann dann
	 * die genutzte Zeit ins Modell geschrieben werden.
	 * 
	 * @param usedTimeInNanoseconds Die gemessene Zeit aus der Schlangensuche.
	 * @return Eine Zahl, die der gemessenen Zeit in der Zeiteinheit des Modelles
	 *         entspricht.
	 * @throws IllegalArgumentException Wurden im Modell falsche Angaben zur
	 *                                  Zeiteinheit gemacht oder wurden falsche
	 *                                  Parameter uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	Double calculateTimeInUnitGivenByModel(Long usedTimeInNanoseconds) throws IllegalArgumentException;

}