package de.fuhagen.course01584.ss23.model;

/**
 * Eine Klasse, die die Felder des Dschungels im Modell modelliert. Felder
 * enthalten Informationen ueber ihre Zeile und Spalte, ueber ihre ID, ihre
 * Punkte und Verwendbarkeit und ihr eigenes Zeichen. Es werden verschiedene
 * Konstruktoren und ansonsten nur Getter und Setter fuer die einzelnen
 * Variablen angeboten.
 * 
 * @author Philip Redecker
 *
 */
public class Field {
	private String id;
	private int zeile;
	private int spalte;
	private int verwendbarkeit;
	private int punkte;
	private String zeichen;

	/**
	 * Ein parametrisierter Konstruktor dem direkt alle Attribute als Parameter
	 * uebergeben werden. Ein so instanziiertes Feld koennte zum Beispiel direkt
	 * einem Dschungel hinzugefuegt werden. Der Konstruktor dient aber auch dem
	 * Testen und beziehungsweise oder der spaeteren Programmerweiterung.
	 * 
	 * @param id             Ein String, der die ID des Feldes enthaelt.
	 * @param zeile          Die Zahl der Zeile in der das Feld zu finden sein soll.
	 * @param spalte         Die Zahl der Spalte in der das Feld zu finden sein
	 *                       soll.
	 * @param verwendbarkeit Die Verwendbarkeit, die das Feld haben soll.
	 * @param punkte         Die Punkte, die das Feld haben soll.
	 * @param zeichen        Ein String, der das Zeichen des Feldes enthaelt.
	 * @throws IllegalArgumentException Falls ein ungueltges Argument fuer Zeile,
	 *                                  Spalte, Verwendbarkeit oder Punkte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public Field(String id, int zeile, int spalte, int verwendbarkeit, int punkte, String zeichen)
			throws IllegalArgumentException {
		super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE < zeile || Integer.MAX_VALUE < spalte || Integer.MAX_VALUE < verwendbarkeit) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile', 'spalte' und 'verwendbarkeit' nicht groesser als der groesste Integerwert sein");
		}
		if (punkte > Integer.MAX_VALUE - 1 || punkte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'punkte' nicht zu gross oder zu klein sein.");
		}
		if (verwendbarkeit > Integer.MAX_VALUE - 1 || verwendbarkeit < Integer.MIN_VALUE + 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'verwendbarkeit' nicht zu gross oder zu klein sein.");
		}
		this.id = id;
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = verwendbarkeit;
		this.punkte = punkte;
		this.zeichen = zeichen;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor, dem nur Zahlen fuer Zeile und
	 * Spalte und ein weiterer Parameter fuer Punkte und Verwendbarkeit uebergeben
	 * wird. Dieser Konstruktor dient dazu Felder zu initialisieren. Entweder beim
	 * Einlesen aus einer Datei, hier werden die Daten der Felder dann spaeter
	 * ueberschrieben oder zum Beispiel auch im Dschungelgenerator.
	 * 
	 * @param zeile     Die Zahl der Zeile in der das Feld zu finden sein soll.
	 * @param spalte    Die Zahl der Spalte in der das Feld zu finden sein soll.
	 * @param parameter Die Zahl fuer Punkte und Verwendbarkeit des Feldes.
	 * @throws IllegalArgumentException Falls ein ungueltges Argument fuer Zeile,
	 *                                  Spalte oder Parameter uebergeben wird, wird
	 *                                  eine Ausnahme ausgeloest.
	 */
	public Field(int zeile, int spalte, int parameter) throws IllegalArgumentException {
		super();
		if (zeile < 0 || spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		if (zeile > Integer.MAX_VALUE - 1 || zeile > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' nicht groesser als der groesste Integerwert sein");
		}
		if (parameter < Integer.MIN_VALUE + 1 || parameter > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'verwendbarkeit' und 'punkte' nicht groesser als der groesste "
							+ "Integerwert und nicht kleiner als der kleinste Integerwert sein");
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.verwendbarkeit = parameter;
		this.punkte = parameter;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public Field() {
		super();
	}

	/**
	 * Es wird die ID des Feldes zurueckgegeben.
	 * 
	 * @return Ein String, der die ID des Feldes enthaelt.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Durch diese Methode ist das Setzen der ID des Feldes auch nach Instanziierung
	 * moeglich. Dies dient vor allem dem Testen oder beziehungsweise und der
	 * spaeteren Programmerweiterung.
	 * 
	 * @param id Ein String, der die ID enthaelt, die dem Feld uebergeben werden
	 *           soll.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Es wird die Zeile des Feldes (im Dschungel) zurueckgegeben.
	 * 
	 * @return Die Zahl der Zeile in der das Feld zu finden ist.
	 */
	public int getZeile() {
		return zeile;
	}

	/**
	 * Durch diese Methode ist das Setzen der Zeile des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param zeile Die Zahl der Zeile, die dem Feld uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Zeile
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setZeile(int zeile) throws IllegalArgumentException {
		if (zeile < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse Feld darf das Attribut 'zeile' keine negativen Werte annehmen.");
		}
		if (zeile > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'zeile' nicht groesser als der groesste Integerwert sein");
		}
		this.zeile = zeile;
	}

	/**
	 * Es wird die Spalte des Feldes (im Dschungel) zurueckgegeben.
	 * 
	 * @return Die Zahl der Spalte in der das Feld zu finden ist.
	 */
	public int getSpalte() {
		return spalte;
	}

	/**
	 * Durch diese Methode ist das Setzen der Spalte des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param spalte Die Zahl der Spalte, die dem Feld uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Spalte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setSpalte(int spalte) throws IllegalArgumentException {
		if (spalte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse Feld darf das Attribut 'spalte' keine negativen Werte annehmen.");
		}
		if (spalte > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'spalte' nicht groesser als der groesste Integerwert sein");
		}
		this.spalte = spalte;
	}

	/**
	 * Es wird die (aktuelle) Verwendbarkeit des Feldes zurueckgegeben. Also die
	 * Anzahl der Male, die das Feld (noch) fuer eine Loesung genutzt werden kann.
	 * 
	 * @return Die Verwendbarkeit des Feldes.
	 */
	public int getVerwendbarkeit() {
		return verwendbarkeit;
	}

	/**
	 * Durch diese Methode ist das Setzen der Verwendbarkeit des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param verwendbarkeit Die Verwendbarkeit, die dem Feld uebergeben werden
	 *                       soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer
	 *                                  Verwendbarkeit uebergeben wird, wird eine
	 *                                  Ausnahme geworfen.
	 */
	public void setVerwendbarkeit(int verwendbarkeit) throws IllegalArgumentException {
		if (verwendbarkeit > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'verwendbarkeit' nicht groesser als der groesste Integerwert sein");
		}
		this.verwendbarkeit = verwendbarkeit;
	}

	/**
	 * Es werden die Punkte des Feldes zurueckgegeben. Dies sind die Punkte, die das
	 * Feld bringt, wenn es fuer eine Loesung genutzt wird.
	 * 
	 * @return Die Punkte des Feldes.
	 */
	public int getPunkte() {
		return punkte;
	}

	/**
	 * Durch diese Methode ist das Setzen der Punkte des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param punkte Die Punkte, die dem Feld uebergeben werden sollen.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Punkte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setPunkte(int punkte) throws IllegalArgumentException {
		if (punkte > Integer.MAX_VALUE - 1 || punkte < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'punkte' nicht zu gross oder zu klein sein.");
		}
		this.punkte = punkte;
	}

	/**
	 * Es wird das Zeichen des Feldes zurueckgegeben.
	 * 
	 * @return Ein String, der das Zeichen des Feldes enthaelt.
	 */
	public String getZeichen() {
		return zeichen;
	}

	/**
	 * Durch diese Methode ist das Setzen des Zeichens des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param zeichen Ein String, das das Zeichen enthaelt, das dem Feld uebergeben
	 *                werden soll.
	 */
	public void setZeichen(String zeichen) {
		this.zeichen = zeichen;
	}
}