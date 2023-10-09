package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.model.*;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Schlangensuche und
 * verschiedenen nuetzlichen Funktionen, die bestimmte Funktionalitaeten
 * anbieten, die die Schlangensuche nutzt. Es wird hier eine Schnittstelle
 * genutzt um die Moeglichkeit offen zu lassen, Teile dieser Funktionen zu
 * ersetzen oder anzupassen.
 * 
 * @author Philip Redecker
 *
 */
public interface ISnakeSearchUtil {

	/**
	 * Eine Methode, die fuer eine gegebene Probleminstanz und einen bestimmten
	 * Moment in der Suche die zulaessigen Schlangenarten bestimmt. Diese werden in
	 * einer Liste hinzugefuegt und diese Liste wird anschliessend einer bestimmten
	 * Heuristik folgend sortiert.
	 * 
	 * @return Eine (sortierte) Liste mit Schlangenarten.
	 */
	List<SnakeType> erzeugeZulaessigeSchlangenarten();

	/**
	 * Eine Methode, die fuer eine gegebene Schlangenart und einen gegebenen
	 * Dschungel die zulaessigen Startfelder bestimmt. Diese werden einer Liste
	 * hinzugefuegt und diese Liste wird anschliessend einer bestimmten Heuristik
	 * folgend sortiert.
	 * 
	 * @param art Die Schlangenart fuer die, die Startfelder bestimmt werden sollen.
	 * @return Eine (sortierte) Liste mit Startfeldern fuer eine Schlangenart.
	 */
	List<Field> erzeugeZulaessigeStartfelder(SnakeType art);

	/**
	 * Eine Methode, die fuer eine gegebene (noch nicht fertige) Schlange und einen
	 * gegebenen Dschungel die zulaessigen Nachbarfelder bestimmt, anhaengig von dem
	 * letzten Schlangenglied, das hinzugefuegt wurde. Die zulaessigen Nachbarfelder
	 * werden einer Liste hinzugefuegt und diese Liste wird anschliessend einer
	 * bestimmten Heuristik folgend sortiert.
	 * 
	 * @param vorherigesGlied Das letzte der aktuellen Schlange hinzugefuegte
	 *                        Schlangenglied.
	 * @param dieseSchlange   Die aktuelle Schlange.
	 * @return Eine (sortierte) Liste mit Nachbarfeldern fuer die aktuelle Schlange
	 *         und das aktuelle Schlangenglied.
	 */
	List<Field> erzeugeZulaessigeNachbarn(SnakeElement vorherigesGlied, Snake dieseSchlange);

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell in der SchlangesucheUtil
	 * befindet. Dies ist vor allem fuer kuenftige Aenderungen und auch fuer damit
	 * einhergehende Tests gedacht.
	 * 
	 * @return Der Wert der Variablen <code>modell</code>.
	 */
	IModel getModell();

	/**
	 * Es kann das Modell der SchlangesucheUtil gesetzt werden. So ist es zum
	 * Beispiel moeglich ein Modell zu uebergeben, auch wenn zunaechst der
	 * parameterlose Konstruktor genutzt wurde. Es ist auch im Allgemeinen moeglich
	 * das Modell nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param modell Das Modell, das uebergeben werden soll.
	 */
	void setModell(IModel modell);

}