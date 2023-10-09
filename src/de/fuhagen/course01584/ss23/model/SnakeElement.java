package de.fuhagen.course01584.ss23.model;

/**
 * Eine Klasse, die Schlangenglieder modelliert. Jede Schlange besteht immer aus
 * einem oder mehreren Schlangengliedern. Schlangenglieder sind also die
 * Bausteine der Schlangen. Jedem Schlangenglied ist ein Feld im Dschungel
 * zugeordnet, das das richtige Zeichen enthaelt. Das richtige Zeichen wird
 * hierbei von der Zeichenkette der Schlangeart der Schlange vorgegeben zu der
 * die Schlangenglieder gehoeren.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeElement {
	private Field field;

	/**
	 * Ein parametrisierter Konstruktor fuer die Klasse Schlangenglied. Bei der
	 * Instanziierung kann also direkt auf das Feld verwiesen werden, das laut
	 * Schlangenart zu diesem Schlangenglied gehoert.
	 * 
	 * @param feld Das Feld, das zu diesem Schlangenglied gehoert.
	 */
	public SnakeElement(Field feld) {
		super();
		this.field = feld;

	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SnakeElement() {
		super();
	}

	/**
	 * Diese Methode gibt das Feld zurueck, das zu diesem Schlangenglied gehoert.
	 * 
	 * @return Das Feld, das zu diesem Schlangenglied gehoert.
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Durch diese Methode ist das Setzen des Feldes des Schlangengliedes auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param feld Das Feld, das dem Schlangenglied uebergeben werden soll.
	 */
	public void setField(Field feld) {
		this.field = feld;
	}
}