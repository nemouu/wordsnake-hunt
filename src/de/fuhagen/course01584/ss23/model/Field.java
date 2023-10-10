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
	private int row;
	private int column;
	private int usage;
	private int points;
	private String character;

	/**
	 * Ein parametrisierter Konstruktor dem direkt alle Attribute als Parameter
	 * uebergeben werden. Ein so instanziiertes Feld koennte zum Beispiel direkt
	 * einem Dschungel hinzugefuegt werden. Der Konstruktor dient aber auch dem
	 * Testen und beziehungsweise oder der spaeteren Programmerweiterung.
	 * 
	 * @param id             Ein String, der die ID des Feldes enthaelt.
	 * @param row          Die Zahl der Zeile in der das Feld zu finden sein soll.
	 * @param column         Die Zahl der Spalte in der das Feld zu finden sein
	 *                       soll.
	 * @param usage Die Verwendbarkeit, die das Feld haben soll.
	 * @param points         Die Punkte, die das Feld haben soll.
	 * @param character        Ein String, der das Zeichen des Feldes enthaelt.
	 * @throws IllegalArgumentException Falls ein ungueltges Argument fuer Zeile,
	 *                                  Spalte, Verwendbarkeit oder Punkte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public Field(String id, int row, int column, int usage, int points, String character)
			throws IllegalArgumentException {
		super();
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE < row || Integer.MAX_VALUE < column || Integer.MAX_VALUE < usage) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile', 'spalte' und 'verwendbarkeit' nicht groesser als der groesste Integerwert sein");
		}
		if (points > Integer.MAX_VALUE - 1 || points < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'punkte' nicht zu gross oder zu klein sein.");
		}
		if (usage > Integer.MAX_VALUE - 1 || usage < Integer.MIN_VALUE + 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'verwendbarkeit' nicht zu gross oder zu klein sein.");
		}
		this.id = id;
		this.row = row;
		this.column = column;
		this.usage = usage;
		this.points = points;
		this.character = character;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor, dem nur Zahlen fuer Zeile und
	 * Spalte und ein weiterer Parameter fuer Punkte und Verwendbarkeit uebergeben
	 * wird. Dieser Konstruktor dient dazu Felder zu initialisieren. Entweder beim
	 * Einlesen aus einer Datei, hier werden die Daten der Felder dann spaeter
	 * ueberschrieben oder zum Beispiel auch im Dschungelgenerator.
	 * 
	 * @param row     Die Zahl der Zeile in der das Feld zu finden sein soll.
	 * @param column    Die Zahl der Spalte in der das Feld zu finden sein soll.
	 * @param parameter Die Zahl fuer Punkte und Verwendbarkeit des Feldes.
	 * @throws IllegalArgumentException Falls ein ungueltges Argument fuer Zeile,
	 *                                  Spalte oder Parameter uebergeben wird, wird
	 *                                  eine Ausnahme ausgeloest.
	 */
	public Field(int row, int column, int parameter) throws IllegalArgumentException {
		super();
		if (row < 0 || column < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' keine negativen Werte annehmen.");
		}
		if (row > Integer.MAX_VALUE - 1 || row > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'zeile' und 'spalte' nicht groesser als der groesste Integerwert sein");
		}
		if (parameter < Integer.MIN_VALUE + 1 || parameter > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' duerfen die Attribute 'verwendbarkeit' und 'punkte' nicht groesser als der groesste "
							+ "Integerwert und nicht kleiner als der kleinste Integerwert sein");
		}
		this.row = row;
		this.column = column;
		this.usage = parameter;
		this.points = parameter;
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
	public int getRow() {
		return row;
	}

	/**
	 * Durch diese Methode ist das Setzen der Zeile des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param row Die Zahl der Zeile, die dem Feld uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Zeile
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setRow(int row) throws IllegalArgumentException {
		if (row < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse Feld darf das Attribut 'zeile' keine negativen Werte annehmen.");
		}
		if (row > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'zeile' nicht groesser als der groesste Integerwert sein");
		}
		this.row = row;
	}

	/**
	 * Es wird die Spalte des Feldes (im Dschungel) zurueckgegeben.
	 * 
	 * @return Die Zahl der Spalte in der das Feld zu finden ist.
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Durch diese Methode ist das Setzen der Spalte des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param column Die Zahl der Spalte, die dem Feld uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Spalte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setColumn(int column) throws IllegalArgumentException {
		if (column < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse Feld darf das Attribut 'spalte' keine negativen Werte annehmen.");
		}
		if (column > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'spalte' nicht groesser als der groesste Integerwert sein");
		}
		this.column = column;
	}

	/**
	 * Es wird die (aktuelle) Verwendbarkeit des Feldes zurueckgegeben. Also die
	 * Anzahl der Male, die das Feld (noch) fuer eine Loesung genutzt werden kann.
	 * 
	 * @return Die Verwendbarkeit des Feldes.
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * Durch diese Methode ist das Setzen der Verwendbarkeit des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param usage Die Verwendbarkeit, die dem Feld uebergeben werden
	 *                       soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer
	 *                                  Verwendbarkeit uebergeben wird, wird eine
	 *                                  Ausnahme geworfen.
	 */
	public void setUsage(int usage) throws IllegalArgumentException {
		if (usage > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'verwendbarkeit' nicht groesser als der groesste Integerwert sein");
		}
		this.usage = usage;
	}

	/**
	 * Es werden die Punkte des Feldes zurueckgegeben. Dies sind die Punkte, die das
	 * Feld bringt, wenn es fuer eine Loesung genutzt wird.
	 * 
	 * @return Die Punkte des Feldes.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Durch diese Methode ist das Setzen der Punkte des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param points Die Punkte, die dem Feld uebergeben werden sollen.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Punkte
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setPoints(int points) throws IllegalArgumentException {
		if (points > Integer.MAX_VALUE - 1 || points < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Feld' darf das Attribut 'punkte' nicht zu gross oder zu klein sein.");
		}
		this.points = points;
	}

	/**
	 * Es wird das Zeichen des Feldes zurueckgegeben.
	 * 
	 * @return Ein String, der das Zeichen des Feldes enthaelt.
	 */
	public String getCharacter() {
		return character;
	}

	/**
	 * Durch diese Methode ist das Setzen des Zeichens des Feldes auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param character Ein String, das das Zeichen enthaelt, das dem Feld uebergeben
	 *                werden soll.
	 */
	public void setCharacter(String character) {
		this.character = character;
	}
}