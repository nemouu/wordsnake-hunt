package de.fuhagen.course01584.ss23.model;

/**
 * Eine Klasse, die Schlangenarten modelliert. Jede Schlange im Modell hat eine
 * Schlangenart und auch im Modell selbst sind bestimmte Schlangenarten
 * vorgegeben. Nach Schlangen dieser Schlangenarten kann dann im Dschungel
 * gesucht werden, sofern dieser vorhanden ist. Neben Gettern und Settern werden
 * auch einige Hilfsmethoden angeboten, die von anderen Klassen genutzt werden.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeType {
	private String id;
	private INeighborhood structure;
	private String signs;
	private int points;
	private int amount;

	/**
	 * Ein parametrisierter Konstruktor, der es ermoeglicht bei Instanziierung
	 * direkt alle moeglichen Informationen an die Instanz dieser Klasse zu
	 * uebergeben. Es werden die Attribute ID, Zeichenkette und Punkte uebergeben
	 * aber auch die Nachbarschaftsstruktur, der Schlangen dieser Schlangenart
	 * folgen muessen. Beim Attribut Anzahl ist zu beachten, dass dieses
	 * hauptsaechlich dem Dschungelgenerator dient, der immer genau so viele
	 * Schlangen im Dschungel verteilen muss. Fuer die Schlangensuche spielt die
	 * Anzahl keine Rolle, da die tatsaechliche Anzahl der gefundenen Schlangen
	 * nicht mit dieser Anzahl hier uebereinstimmen muss.
	 * 
	 * @param id           Ein String, der die ID enthaelt, die diese Schlangenart
	 *                     haben soll.
	 * @param struktur     Die Nachbarschaftsstruktur, der diese Schlangenart folgen
	 *                     soll.
	 * @param zeichenkette Die Zeichen, die Schlangen dieser Schlangenart haben
	 *                     muessen.
	 * @param punkte       Die Punkte, die das Finden von Schlangen dieser
	 *                     Schlangenart erzielt.
	 * @param anzahl       Die Anzahl der Schlangen dieser Art, die im
	 *                     Dschungelgenerator im Dschungel verteilt werden sollen.
	 * @throws IllegalArgumentException Wird ein ungueltiges Argument fuer die
	 *                                  Parameter Anzahl, Punkte und beziehungsweise
	 *                                  oder Zeichenkette verwendet, so wird eine
	 *                                  Ausnahme ausgeloest.
	 */
	public SnakeType(String id, INeighborhood struktur, String zeichenkette, int punkte, int anzahl)
			throws IllegalArgumentException {
		super();
		if (anzahl < 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'anzahl' nicht kleiner als 1 sein.");
		}
		if (anzahl > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'anzahl' nicht zu gross sein.");
		}
		if (punkte < 0 || punkte > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'punkte' nicht zu klein oder zu gross sein.");
		}
		if (zeichenkette.length() < 1) {
			throw new IllegalArgumentException("Die Schlangenart braucht mindestens ein Zeichen, "
					+ "damit ueberhaupt nach ihr gesucht werden kann.");
		}
		this.id = id;
		this.structure = struktur;
		this.signs = zeichenkette;
		this.points = punkte;
		this.amount = anzahl;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SnakeType() {
		super();
	}

	@Override
	public String toString() {
		if (id == null || structure == null || signs == null) {
			return "";
		}
		return "ID=" + id + ", Nachbarschaftsstruktur=" + structure.getType() + ", Zeichenkette=" + signs
				+ ", Punkte=" + points + ", Anzahl=" + amount;
	}

	/**
	 * Diese Methode gibt einen String zurueck, der die ID der Schlangenart
	 * enthaelt.
	 * 
	 * @return Ein String, der die ID der Schlangenart enthaelt.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Durch diese Methode ist das Setzen der ID einer Schlangenart auch nach
	 * Instanziierung moeglich. Diese Methode dient vor allem dem Einlesen von Daten
	 * aber unter anderem auch dem Testen oder beziehungsweise und der spaeteren
	 * Programmerweiterung.
	 * 
	 * @param id Die ID, die dieser Schlangenart uebergeben werden soll.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Diese Methode gibt die Nachbarschaftsstruktur zurueck, der die Schlangenart
	 * unterliegt.
	 * 
	 * @return Die Nachbarschaftsstruktur der Schlangenart.
	 */
	public INeighborhood getStructure() {
		return structure;
	}

	/**
	 * Durch diese Methode ist das Setzen der Nachbarschaftsstruktur einer
	 * Schlangenart auch nach Instanziierung moeglich. Diese Methode dient vor allem
	 * dem Einlesen von Daten aber unter anderem auch dem Testen oder
	 * beziehungsweise und der spaeteren Programmerweiterung.
	 * 
	 * @param struktur Die Nachbarschaftsstruktur, die dieser Schlangenart
	 *                 uebergeben werden soll.
	 */
	public void setStructure(INeighborhood struktur) {
		this.structure = struktur;
	}

	/**
	 * Diese Methode gibt einen String zurueck, der die Zeichenkette der
	 * Schlangenart enthaelt.
	 * 
	 * @return Ein String, der die Zeichenkette der Schlangenart enthaelt.
	 */
	public String getSigns() {
		return signs;
	}

	/**
	 * Durch diese Methode ist das Setzen der Zeichenkette einer Schlangenart auch
	 * nach Instanziierung moeglich. Es ist darauf zu achten, dass eine gueltige
	 * Zeichenkette uebergeben wird. Diese Methode dient vor allem dem Einlesen von
	 * Daten aber unter anderem auch dem Testen oder beziehungsweise und der
	 * spaeteren Programmerweiterung.
	 * 
	 * @param zeichenkette Die Zeichenkette, die dieser Schlangenart uebergeben
	 *                     werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer die
	 *                                  Zeichenkette uebergeben wird, wird eine
	 *                                  Ausnahme geworfen.
	 */
	public void setSigns(String zeichenkette) throws IllegalArgumentException {
		if (zeichenkette.length() < 1) {
			throw new IllegalArgumentException("Die Schlangenart braucht mindestens ein Zeichen, "
					+ "damit ueberhaupt nach ihr gesucht werden kann.");
		}
		this.signs = zeichenkette;
	}

	/**
	 * Diese Methode gibt die Punkte der Schlangenart in Form einer ganzen Zahl
	 * zurueck.
	 * 
	 * @return Die Punkte der Schlangenart, eine ganze Zahl.
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Durch diese Methode ist das Setzen der Punkte einer Schlangenart auch nach
	 * Instanziierung moeglich. Es ist darauf zu achten, dass eine gueltige
	 * Punktzahl uebergeben wird. Diese Methode dient vor allem dem Einlesen von
	 * Daten aber unter anderem auch dem Testen oder beziehungsweise und der
	 * spaeteren Programmerweiterung.
	 * 
	 * @param punkte Die Punkte, die dieser Schlangenart uebergeben werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer die
	 *                                  Punkte uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setPoints(int punkte) throws IllegalArgumentException {
		if (punkte < 0 || punkte > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'punkte' nicht zu klein oder zu gross sein.");
		}
		this.points = punkte;
	}

	/**
	 * Diese Methode gibt die Anzahl der Schlangen dieser Schlangenart in Form einer
	 * ganzen Zahl zurueck.
	 * 
	 * @return Die Anzahl der Schlangen einer Schlangenart.
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Durch diese Methode ist das Setzen der Anzahl der Schlangen einer
	 * Schlangenart auch nach Instanziierung moeglich. Es ist darauf zu achten, dass
	 * eine gueltige Anzahl uebergeben wird. Diese Methode dient vor allem dem
	 * Einlesen von Daten aber unter anderem auch dem Testen oder beziehungsweise
	 * und der spaeteren Programmerweiterung.
	 * 
	 * @param anzahl Die Anzahl der Schlangen dieser Schlangenart, die im Dschungel
	 *               verteilt werden soll.
	 * @throws IllegalArgumentException Falls ein ungueltiges Argument fuer die
	 *                                  Anzahl uebergeben wird, wird eine Ausnahme
	 *                                  geworfen.
	 */
	public void setAmount(int anzahl) throws IllegalArgumentException {
		if (anzahl < 0) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'anzahl' nicht kleiner als 0 sein.");
		}
		if (anzahl > Integer.MAX_VALUE - 1) {
			throw new IllegalArgumentException(
					"Fuer die Klasse 'Schlangenart' darf das Attribut 'anzahl' nicht zu gross sein.");
		}
		this.amount = anzahl;
	}
}