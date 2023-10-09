package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Klasse, die die Speicherung einer Loesung im Modell erlaubt. Eine
 * Loesung, die von der Schlangensuche gefunden oder eine Loesung, die
 * eingelesen wurde, kann hier abgelegt werden. Es werden einige Methoden
 * angeboten, die die Datenverarbeitungs- und Algorithmusklassen unterstuetzen.
 * 
 * @author Philip Redecker
 *
 */
public class Solution {
	private List<Snake> schlangen;

	/**
	 * Ein parametrisierter Konstruktor in dem direkt eine Liste von Schlangen,
	 * gemaess der in diesem Paket definierten Datenstruktur Schlange, uebergeben
	 * wird. Eine Instanz einer Loesungsklasse hat in diesem Fall also eine Liste
	 * mit (Loesungs-) Schlangen.
	 * 
	 * @param schlangen Eine Liste mit Schlangen, gemaess der in diesem Paket
	 *                  definierten Datenstruktur Schlange.
	 */
	public Solution(List<Snake> schlangen) {
		super();
		this.schlangen = schlangen;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen. Hier
	 * wird direkt eine leere Liste mit Schlangen erzeugt, damit der Loesung
	 * jederzeit Schlangen hinzugefuegt werden koennen, sollte dies gewuenscht sein.
	 */
	public Solution() {
		super();
		this.schlangen = new ArrayList<Snake>();
	}

	/**
	 * Eine Methode, die die verschiedenen Schlagenarten zaehlt und ausgibt, die
	 * tatsaechlich in einer Loesung vorhanden sind. Diese Zahl stimmt nach den
	 * Vorgaben nicht zwingend mit der Zahl der im Modell enthaltenen Schlangenarten
	 * ueberein. Dies wird insbesondere in den Datenverarbeitungsklassen und beim
	 * Testen dieser Klasse genutzt.
	 * 
	 * @return Die Anzahl der verschiedenen Schlangenarten in der Loesung.
	 */
	public int getAnzahlSchlangenarten() {
		List<SnakeType> verschiedeneArten = new ArrayList<SnakeType>();
		for (Snake schlange : schlangen) {
			if (verschiedeneArten.contains(schlange.getArt()) == false) {
				verschiedeneArten.add(schlange.getArt());
			}
		}
		return verschiedeneArten.size();
	}

	/**
	 * Diese Methode ermoeglicht es eine Schlange in der Loesung anhand ihrer der ID
	 * ihrer Schlangenart zu finden und zurueckzugeben.
	 * 
	 * @param id Ein String, der die ID der Schlangenart enthaelt, nach der gesucht
	 *           wird.
	 * @return <code>null</code>, wenn keine Schlange gefunden wurde, ansonsten wird die
	 *         Schlange zurueckgegeben.
	 */
	public Snake getSchlangeMitSchlangenartID(String id) {
		for (Snake schlange : schlangen) {
			if (schlange.getArt().getId().equals(id)) {
				return schlange;
			}
		}
		return null;
	}

	/**
	 * Eine Methode die der Loesung eine Schlange hinzufuegen kann.
	 * 
	 * @param inSchlange Eine Schlange, gemaess der in diesem Paket definierten
	 *                   Datenstruktur Schlange.
	 */
	public void addSchlange(Snake inSchlange) {
		this.schlangen.add(inSchlange);
	}

	/**
	 * Eine Methode, die die letzte Schlange in der Loesung entfernt.
	 */
	public void entferneLetzteSchlange() {
		if (this.schlangen != null) {
			if (this.schlangen.size() > 0) {
				this.schlangen.remove(schlangen.size() - 1);
			}
		}
	}

	/**
	 * Es werden die Schlangen der Loesung zurueckgegeben.
	 * 
	 * @return Eine Liste von Schlangen gemaess der in diesem Paket definierten
	 *         Datenstruktur Schlangen.
	 */
	public List<Snake> getSchlangen() {
		return schlangen;
	}

	/**
	 * Durch diese Methode ist das Setzen der Schlangen der Loesung auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param schlangen Eine Liste mit Schlangen, die der Loesung uebergeben werden
	 *                  soll.
	 */
	public void setSchlangen(List<Snake> schlangen) {
		this.schlangen = schlangen;
	}
}