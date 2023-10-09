package de.fuhagen.course01584.ss23.model;

/**
 * Eine Klasse, die den Dschungel des Modelles modelliert und dessen
 * Gesetzmaessigkeiten beruecksichtigt. Es ist moeglich Instanzen der
 * Dschungelklasse zu erstellen und verschiedene Operationen auszufuehren.
 * Insbesondere werden auch einige Methoden bereitgestellt, die das Suchen nach
 * Schlangen unterstuetzen sollen.
 * 
 * @author Philip Redecker
 *
 */
public class Jungle {
	private int rows;
	private int columns;
	private Field[][] fields;
	private String signs;

	/**
	 * Ein Konstruktor fuer die Dschungelklasse dem zwei Zahlen fuer die Anzahl der
	 * Zeilen und Spalten, ein String fuer die Menge der verwendeten Zeichen und
	 * auch ein 2 dimensionales Array fuer die Felder uebergeben wird. Es wird also
	 * bei Instanziierung direkt ein vollstaendiger Dschungel erzeugt.
	 * 
	 * @param zeilen       Eine Zahl, die der Anzahl der gewuenschten Zeilen
	 *                     entspricht.
	 * @param spalten      Eine Zahl, die der Anzahl der gewuenschten Spalten
	 *                     entspricht.
	 * @param felder       Ein 2 dimensionales Array von Feldern. Dies sind die
	 *                     Dschungelfelder.
	 * @param zeichenmenge Ein String, der die verwendeten Zeichen des Dschungels
	 *                     enthaelt.
	 * @throws IllegalArgumentException Falls ungueltige Argumente fuer Zeilen und
	 *                                  Spalten uebergeben werden, wird eine
	 *                                  Ausnahme geworfen.
	 */
	public Jungle(int zeilen, int spalten, Field[][] felder, String zeichenmenge) throws IllegalArgumentException {
		super();
		if (zeilen < 0 || spalten < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' und 'spalten' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE - 1 < zeilen || Integer.MAX_VALUE - 1 < spalten) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' und 'spalten' nicht groesser als der groesste Integerwert sein");
		}
		this.rows = zeilen;
		this.columns = spalten;
		this.fields = felder;
		this.signs = zeichenmenge;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor fuer die Klasse Dschungel. Es
	 * werden zwei Zahlen fuer die Anzahl der Zeilen und Spalten, ein String fuer
	 * die Menge der verwendeten Zeichen und eine Zahl fuer die Erzeugung von leeren
	 * Feldern uebergeben. Es wird kein 2 dimensionales Array uebergeben, sondern
	 * hier wird ein solches direkt im Konstruktor erzeugt. Der uebergebene
	 * Parameter <code>parameter</code> dient hierbei dazu die einzelnen Felder zu
	 * instanziieren (siehe Konstruktoren fuer Klasse Feld).
	 * 
	 * @param zeilen       Eine Zahl, die der Anzahl der gewuenschten Zeilen
	 *                     entspricht.
	 * @param spalten      Eine Zahl, die der Anzahl der gewuenschten Spalten
	 *                     entspricht.
	 * @param zeichenmenge Ein String, der die verwendeten Zeichen des Dschungels
	 *                     enthaelt.
	 * @param parameter    Eine Zahl, die an den Konstruktor der einzelnen Felder
	 *                     weitergegeben wird. Sie bestimmt die Verwendbarkeit und
	 *                     Punkte der einzelnen Felder (siehe Konstruktoren fuer die
	 *                     Klasse Feld).
	 * @throws IllegalArgumentException Falls ungueltige Argumente fuer Zeilen und
	 *                                  Spalten uebergeben werden, wird eine
	 *                                  Ausnahme geworfen.
	 */
	public Jungle(int zeilen, int spalten, String zeichenmenge, int parameter) throws IllegalArgumentException {
		super();
		if (zeilen < 0 || spalten < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' und 'spalten' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE - 1 < zeilen || Integer.MAX_VALUE - 1 < spalten) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' duerfen die Attribute 'zeilen' und 'spalten' nicht groesser als der groesste Integerwert sein");
		}
		this.rows = zeilen;
		this.columns = spalten;
		this.signs = zeichenmenge;
		this.fields = new Field[zeilen][spalten];
		for (int i = 0; i < zeilen; i++) {
			for (int j = 0; j < spalten; j++) {
				fields[i][j] = new Field(i, j, parameter);
				int idNummer = spalten * i + j;
				fields[i][j].setId("F" + idNummer);
			}
		}
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public Jungle() {
		super();
	}

	/**
	 * Eine Methode, die die Anzahl der Felder ausgibt, die ein Zeichen haben. Also
	 * die Zahl der nicht leeren Felder. Die Methode dient der Unterstuetzung der
	 * Schlangesuche und wird zusaetzlich zum Testen der Dschungelklasse verwendet.
	 * 
	 * @return Die Anzahl, der mit einem Zeichen belegten Felder.
	 */
	public int numberOfTakenFields() {
		int anzahl = 0;
		if (fields == null) {
			return anzahl;
		}
		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[0].length; j++) {
				if (fields[i][j].getCharacter() != null) {
					anzahl++;
				}
			}
		}
		return anzahl;
	}

	/**
	 * Eine einfache Methode, die die gesamte Anzahl der Felder ausgibt. Es wird
	 * hierbei einfach die Laenge des Dschungels mal die Breite des Dschungels
	 * gerechnet. Diese Methode dient nur dem einfachen Zugriff auf die Anzahl der
	 * Felder.
	 * 
	 * @return Die Anzahl der Felder im Dschungel insgesamt.
	 */
	public int numberOfFields() {
		return fields.length * fields[0].length;
	}

	@Override
	public String toString() {
		String output = "";
		if (numberOfTakenFields() == 0) {
			return output;
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (fields[i][j].getCharacter() == null) {
					output += " (Ø," + fields[i][j].getUsage() + "," + fields[i][j].getPoints() + ")";
				} else {
					output += " (" + fields[i][j].getCharacter() + "," + fields[i][j].getUsage() + ","
							+ fields[i][j].getPoints() + ")";
				}
			}
			output += "\n\n";
		}
		return output;
	}

	/**
	 * Es wird die Anzahl der Zeilen des Dschungels zurueckgegeben. Dies wird vor
	 * allem zum Testen oder beziehungsweise und fuer die spaetere
	 * Programmerweiterung bereitgestellt.
	 * 
	 * @return Die Anzahl der Zeilen des Dschungels.
	 */
	public int getRows() {
		return rows;
	}

	/**
	 * Durch diese Methode ist das Setzen der Zeilen des Dschungel auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param zeilen Die Anzahl der Zeilen, die dem Dschungel uebergeben werden
	 *               soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Zeilen
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setRows(int zeilen) throws IllegalArgumentException {
		if (zeilen < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' darf das Attribut 'zeilen' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE < zeilen) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' darf das Attribut 'zeilen' nicht groesser als der groesste Integerwert sein");
		}
		this.rows = zeilen;
	}

	/**
	 * Es wird die Anzahl der Spalten des Dschungels zurueckgegeben. Dies wird vor
	 * allem zum Testen oder beziehungsweise und fuer die spaetere
	 * Programmerweiterung bereitgestellt.
	 * 
	 * @return Die Anzahl der Spalten des Dschungels.
	 */
	public int getColumns() {
		return columns;
	}

	/**
	 * Durch diese Methode ist das Setzen der Spalten des Dschungel auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param spalten Die Anzahl der Spalten, die dem Dschungel uebergeben werden
	 *                soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer Spalten
	 *                                  uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setColumns(int spalten) throws IllegalArgumentException {
		if (spalten < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' darf das Attribut 'spalten' keine negativen Werte annehmen.");
		}
		if (Integer.MAX_VALUE - 1 < spalten) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Dschungel' darf das Attribut 'spalten' nicht groesser als der groesste Integerwert sein");
		}
		this.columns = spalten;
	}

	/**
	 * Mit dieser Methode wird es moeglich ein einzelnes Feld zu setzen. Da Felder
	 * Informationen darueber enthalten, wo genau sie im Dschungel sind (siehe
	 * Klasse Feld), ist nicht mehr als eine einfache Zuweiseung noetig.
	 * 
	 * @param inFeld Das Feld, das im Dschungel platziert werden soll.
	 */
	public void setField(Field inFeld) {
		this.fields[inFeld.getRow()][inFeld.getColumn()] = inFeld;
	}

	/**
	 * Es die Felder des Dschungels in Form der internen Datenstruktur
	 * zurueckgegeben (2D Array aus Feldern) zurueckgegeben. Es wird an mehreren
	 * Progammstellen nach Felder im Dschungel gesucht und diese Methode hilft
	 * dabei. Sie wird aber auch zum Testen oder beziehungsweise und fuer die
	 * spaetere Programmerweiterung genutzt.
	 * 
	 * @return Die Felder des Dschungel (2D Array aus Feldern).
	 */
	public Field[][] getFields() {
		return fields;
	}

	/**
	 * Durch diese Methode ist das Setzen der Felder des Dschungel auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param felder Ein 2D Array aus Feldern, das dem Dschungel uebergeben wird.
	 */
	public void setFields(Field[][] felder) {
		this.fields = felder;
	}

	/**
	 * Es wird die Zeichenmenge des Dschungels zurueckgegeben. Viele andere
	 * Programmteile muessen die Zeichenmenge des Dschungels kennen (beispielsweise
	 * der Dschungelgenerator), deshalb ist sie ueber diese Methode abrufbar. Sie
	 * wird aber auch zum Testen oder beziehungsweise und fuer die spaetere
	 * Programmerweiterung bereitgestellt.
	 * 
	 * @return Die Zeichenmenge des Dschungels.
	 */
	public String getSigns() {
		return signs;
	}

	/**
	 * Durch diese Methode ist das Setzen der Zeichenmenge des Dschungel auch nach
	 * Instanziierung moeglich. Dies dient vor allem dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param zeichenmenge Die Zeichenmenge, die dem Dschungel uebergeben werden
	 *                     soll.
	 */
	public void setSigns(String zeichenmenge) {
		this.signs = zeichenmenge;
	}
}