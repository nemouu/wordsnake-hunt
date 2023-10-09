package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Datenmodell und
 * Nachbarschaftsverhaeltnis, das bestimmt wie Schlangenglieder im Dschungel des
 * Modelles angeordnet sind. Dadurch ist es moeglich in Zukunft das Programm
 * durch eine andere Form der Nachbarschaft zu erweitern, sollte dies gewuenscht
 * sein.
 * 
 * @author Philip Redecker
 *
 */
public interface INeighborhood {

	/**
	 * Es wird die Art der Nachbarschaft als String zurueckgegeben. Der hier
	 * zurueckgegebene String beschreibt die Nachbarschaft und sollte idealerweise
	 * auch im Klassennamen der implementierenden Klasse zu finden sein.
	 * 
	 * @return Der Wert einer Variable <code>art</code>, der die Art der
	 *         Nachbarschaft beschreibt.
	 */
	String getType();

	/**
	 * Es werden die Parameter der jeweiligen Implementierung ausgegeben. Je nach
	 * Implementierung kann dies eine Zahl oder mehrere Zahlen sein, deswegen wurde
	 * die Liste als Datenstruktur gewaehlt. Die Parameter der Nachbarschaft haben
	 * Einfluss auf die tatsaechlich verfuegbaren Nachbarn eines Feldes, deshalb
	 * wurde der Zugriff auf diese ermoeglicht.
	 * 
	 * @return Eine Liste mit Parametern der implementierten Nachbarschaftsklasse.
	 */
	List<Integer> getParameters();

	/**
	 * Es ist durch diese Methode moeglich die Parameter der implementierenden
	 * Klasse nachtraeglich zu aendern. Dies ist hauptsaechlich zum Testen der
	 * jeweiligen Klassen gedacht oder aber auch fuer zukuenftige Aenderungen des
	 * Programmes.
	 * 
	 * @param parameters Eine Liste mit einem oder mehreren Parametern, die der
	 *                   implementierenden Klasse uebergeben werden sollen.
	 */
	void setParameters(List<Integer> parameters);

	/**
	 * Diese Methode bestimmt die Nachbarn eines Feldes in einem uebergebenen
	 * Dschungel. Um zu suchen werden im Allgemeinen die Parameter genutzt, die der
	 * implementierenden Klasse vorher uebergeben worden sind.
	 * 
	 * @param dschungel Der Dschungel, in dem nach den Nachbarn gesucht wird.
	 * @param feld      Das Feld, dessen Nachbarn bestimmt werden sollen.
	 * @return Eine Liste mit Nachbarn des eingegebenen Feldes, die im eingegebenen
	 *         Dschungel liegen.
	 */
	List<Field> getNeighbors(Jungle dschungel, Field feld);

}