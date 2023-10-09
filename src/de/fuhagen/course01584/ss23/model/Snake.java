package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Klasse, die Schlangen modelliert. Diese werden zum Beispiel vom
 * Dschungelgenerator im Dschungel versteckt oder von der Schlangensuche im
 * Dschungel gesucht. Neben Gettern und Settern werden auch einige Hilfsmethoden
 * angeboten, die von anderen Klassen genutzt werden.
 * 
 * @author Philip Redecker
 *
 */
public class Snake {
	private SnakeType art;
	private List<SnakeElement> glieder;

	/**
	 * In diesem parametrisierten Konstruktor kann bei Instanziierung dieser Klasse
	 * eine Schlangenart aber auch eine Liste von Schlangengliedern uebergeben
	 * werden. In diesem Fall ist die Schlange also moeglicherweise nach der
	 * Instanziierung vollstaendig.
	 * 
	 * @param art     Die Schlangenart, die die Schlange haben soll.
	 * @param glieder Eine Liste mit Schlangengliedern, gemaess der in diesem Paket
	 *                definierten Datenstruktur Schlangenglied.
	 */
	public Snake(SnakeType art, List<SnakeElement> glieder) {
		super();
		this.art = art;
		this.glieder = glieder;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor. In diesem wird nur eine
	 * Schlangenart uebergeben. Im Konstruktor selbst wird dann eine leere Liste aus
	 * Schlangengliedern, gemaess der in diesem Paket definierten Datenstruktur
	 * Schlangenglied erzeugt, sodass der Schlange jederzeit Schlangenglieder
	 * hinzugefuegt werden koennen.
	 * 
	 * @param art Die Schlangenart, die die Schlange haben soll.
	 */
	public Snake(SnakeType art) {
		super();
		this.art = art;
		this.glieder = new ArrayList<SnakeElement>();
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen. Es
	 * wird im Konstruktor selbst eine leere Liste aus Schlangengliedern, gemaess
	 * der in diesem Paket definierten Datenstruktur Schlangenglied erzeugt, sodass
	 * der Schlange jederzeit Schlangenglieder hinzugefuegt werden koennen.
	 */
	public Snake() {
		super();
		this.glieder = new ArrayList<SnakeElement>();
	}

	/**
	 * Eine Methode, die der Schlange ein Schlangenglied (am Ende der Schlange)
	 * hinzufuegt.
	 * 
	 * @param inGlied Das Schlangenglied, das hinzugefuegt werden soll.
	 */
	public void addGlied(SnakeElement inGlied) {
		this.glieder.add(inGlied);
	}

	/**
	 * Eine Methode, die das letzte Schlangenglied einer Schlange entfernt.
	 */
	public void entferneLetztesGlied() {
		if (glieder != null && glieder.size() > 0) {
			this.glieder.remove(glieder.size() - 1);
		}
	}

	/**
	 * Eine Methode, die einen String zurueckgibt, der das Zeichen des naechsten
	 * Schlangengliedes enthaelt. Hat die Schlange kein naechstes Glied mehr, wird
	 * <code>""</code> zurueckgegeben.
	 * 
	 * @return Ein String, der das Zeichen des naechsten Schlangengliedes enthaelt.
	 *         Wenn es kein naechstes Glied mehr gibt wird <code>null</code>
	 *         zurueckgegeben.
	 */
	public String naechstesGlied() {
		if (glieder.size() < art.getZeichenkette().length()) {
			return art.getZeichenkette().substring(glieder.size(), glieder.size() + 1);
		}
		return "";
	}

	/**
	 * Eine Methode, die einen String zurueckgibt, der das Zeichen des letzten
	 * Schlangengliedes der Schlange enthaelt.
	 * 
	 * @return Ein String, der das Zeichen des letzten Schlangengliedes enthaelt.
	 */
	public String letztesGlied() {
		return art.getZeichenkette().substring(art.getZeichenkette().length() - 1, art.getZeichenkette().length());
	}

	/**
	 * Es wird die Schlangenart der Schlange zurueckgegeben.
	 * 
	 * @return Eine Schlangenart, gemaess der in diesem Paket definierten
	 *         Datenstruktur Schlangenart.
	 */
	public SnakeType getArt() {
		return art;
	}

	/**
	 * Durch diese Methode ist das Setzen der Schlangenart der Schlange auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param art Eine Schlangenart, die der Schlange uebergeben werden soll.
	 */
	public void setArt(SnakeType art) {
		this.art = art;
	}

	/**
	 * Es werden die Schlangenglieder der Schlange zurueckgegeben.
	 * 
	 * @return Eine Liste von Schlangengliedern gemaess der in diesem Paket
	 *         definierten Datenstruktur Schlangenglied.
	 */
	public List<SnakeElement> getGlieder() {
		return glieder;
	}

	/**
	 * Durch diese Methode ist das Setzen der Schlangenglieder der Schlange auch
	 * nach Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von
	 * Daten aber unter anderem auch dem Testen oder beziehungsweise und der
	 * spaeteren Programmerweiterung.
	 * 
	 * @param glieder Eine Liste mit Schlangengliedern, die der Schlange uebergeben
	 *                werden soll.
	 */
	public void setGlieder(List<SnakeElement> glieder) {
		this.glieder = glieder;
	}
}