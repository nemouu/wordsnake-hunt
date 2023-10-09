package de.fuhagen.course01584.ss23.model;

import java.util.*;

/**
 * Eine Implementierung der Schnittstelle IModell, um die Daten, die bei der
 * Schlangensuche gebraucht werden aufnehmen und bearbeiten zu koennen. Es
 * werden die Methoden aus der Schnittstelle implementiert und zusaetzlich
 * einige private Methoden zum Ausgeben des Modelles auf die Konsole
 * bereitgestellt.
 * 
 * @author Philip Redecker
 *
 */
public class ProblemModel implements IModel {
	private String zeiteinheit;
	private Double[] zeit;
	private Jungle dschungel;
	private List<SnakeType> schlangenarten;
	private Solution loesung;

	/**
	 * Ein parametrisierter Konstruktor, der eine Instanz des Modelles erstellen
	 * kann. Hierbei werden Parameter so uebergeben, dass direkt eine eventuell
	 * nicht leere Liste von Schlangenarten im Modell enthalten ist. Diese wird hier
	 * naemlich als Konstruktor uebergeben.
	 * 
	 * @param dschungel      Ein Dschungel gemaess der in diesem Paket definierten
	 *                       Datenstruktur Dschungel.
	 * @param schlangenarten Eine Liste von Schlangenarten gemaess der in diesem
	 *                       Paket definierten Datenstruktur Schlangenart.
	 * @param loesung        Eine Loesung gemaess der in diesem Paket definierten
	 *                       Datenstruktur Loesung.
	 * @param zeit           Eine Array von Doubles fuer die Zeitvorgabe und
	 *                       beziehungsweise oder die Zeitabgabe.
	 * @throws IllegalArgumentException Wird fuer das Attribut Zeit ein ungueltiges
	 *                                  Argument uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public ProblemModel(Jungle dschungel, List<SnakeType> schlangenarten, Solution loesung, Double[] zeit)
			throws IllegalArgumentException {
		super();
		if (zeit.length == 1) {
			if (zeit[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (zeit.length == 2) {
			if (zeit[0] == 0.0 || zeit[1] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0 || zeit[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292 || zeit[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.dschungel = dschungel;
		this.schlangenarten = schlangenarten;
		this.loesung = loesung;
		this.zeit = zeit;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor, der es erlaubt ein Modell zu
	 * instanziieren ohne eine Liste von Schlangenarten zu uebergeben. Hierbei wird
	 * dann im Konstruktor eine leere Liste mit Schlangenarten erzeugt, damit dort
	 * spaeter Schlangenarten hinzugefuegt werden koennen.
	 * 
	 * @param dschungel Ein Dschungel gemaess der in diesem Paket definierten
	 *                  Datenstruktur Dschungel.
	 * @param loesung   Eine Loesung gemaess der in diesem Paket definierten
	 *                  Datenstruktur Loesung.
	 * @param zeit      Eine Array von Doubles fuer die Zeitvorgabe und
	 *                  beziehungsweise oder die Zeitabgabe.
	 * @throws IllegalArgumentException Wird fuer das Attribut Zeit ein ungueltiges
	 *                                  Argument uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public ProblemModel(Jungle dschungel, Solution loesung, Double[] zeit) throws IllegalArgumentException {
		super();
		if (zeit.length == 1) {
			if (zeit[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (zeit.length == 2) {
			if (zeit[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0 || zeit[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292 || zeit[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.dschungel = dschungel;
		this.schlangenarten = new ArrayList<SnakeType>();
		this.loesung = loesung;
		this.zeit = zeit;
		if (zeiteinheit == null) {
			zeiteinheit = "s";
		}
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen. Es
	 * wird zusaetzlich eine leere Liste von Schlangenarten instanziiert, so dass
	 * diese befuellt werden kann.
	 */
	public ProblemModel() {
		super();
		this.schlangenarten = new ArrayList<SnakeType>();
	}

	@Override
	public Double berechneZeitInNanosekunden(Double vorgabeAusModell) throws IllegalArgumentException {
		switch (getZeiteinheit()) {
		case "ms": {
			return getZeit()[0] * 1000000;
		}
		case "s": {
			return getZeit()[0] * 1000000000;
		}
		case "min": {
			return getZeit()[0] * 6.0 * (Math.pow(10, 10));
		}
		case "h": {
			return getZeit()[0] * 3.6 * (Math.pow(10, 12));
		}
		case "d": {
			return getZeit()[0] * 8.64 * (Math.pow(10, 13));
		}
		default:
			throw new IllegalArgumentException("Die Zeitangabe des Modelles kann nicht umgerechnet werden.");
		}
	}

	@Override
	public Double berechneZeitInModellEinheit(Long genutzteZeitInNanosekunden) throws IllegalArgumentException {
		switch (getZeiteinheit()) {
		case "ms": {
			return (double) (genutzteZeitInNanosekunden / 1000000.0);
		}
		case "s": {
			return (double) (genutzteZeitInNanosekunden / 1000000000.0);
		}
		case "min": {
			return (double) (genutzteZeitInNanosekunden / (6.0 * (Math.pow(10, 10))));
		}
		case "h": {
			return (double) (genutzteZeitInNanosekunden / (3.6 * (Math.pow(10, 12))));
		}
		case "d": {
			return (double) (genutzteZeitInNanosekunden / (8.64 * (Math.pow(10, 13))));
		}
		default:
			throw new IllegalArgumentException("Die Zeitangabe des Modelles kann nicht umgerechnet werden.");
		}
	}

	@Override
	public String toString() {
		String schlangenartenToString = "";
		for (SnakeType schlangenart : schlangenarten) {
			schlangenartenToString += " (" + schlangenart.toString() + ")\n";
		}
		try {
			if (dschungel.anzahlBelegterFelder() == 0) {
				return "Der Dschungel dieses Problemes hat " + dschungel.getZeilen() + " Zeilen, "
						+ dschungel.getSpalten() + " Spalten und die Zeichenmenge '" + dschungel.getZeichenmenge()
						+ "' \naber keine Felder und es kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden. Die Felder koennen mit dem Befehl 'e' erzeugt werden. Die Loesung hierzu ist "
						+ "nicht \nvorhanden und kann mit dem Befehl 'l' gesucht werden.";
			} else if (loesung == null && dschungel.anzahlBelegterFelder() < dschungel.anzahlFelder()) {
				return "Der Dschungel dieses Problemes hat " + dschungel.getZeilen() + " Zeilen, "
						+ dschungel.getSpalten() + " Spalten und die Zeichenmenge '" + dschungel.getZeichenmenge()
						+ "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ dschungel.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung enthalten. Dabei ist jedoch zu beachten, dass"
						+ " der eingelesene\nDschungel leere Felder enthaelt. Es wurden " + dschungel.anzahlFelder()
						+ " Felder erwartet aber nur " + dschungel.anzahlBelegterFelder()
						+ " wurden eingelesen. Die uebrigen\n"
						+ "Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel erzeugt werden.";
			} else if (loesung == null || loesung.getSchlangen().size() == 0) {
				return "Der Dschungel dieses Problemes hat " + dschungel.getZeilen() + " Zeilen, "
						+ dschungel.getSpalten() + " Spalten und die Zeichenmenge '" + dschungel.getZeichenmenge()
						+ "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + dschungel.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + schlangenartenToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung vorhanden. Es kann mit dem Befehl 'l' nach einer"
						+ " Loesung\ngesucht werden.";
			} else if (dschungel.anzahlBelegterFelder() < dschungel.anzahlFelder()) {
				return "Der Dschungel dieses Problemes hat " + dschungel.getZeilen() + " Zeilen, "
						+ dschungel.getSpalten() + " Spalten und die Zeichenmenge '" + dschungel.getZeichenmenge()
						+ "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ dschungel.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden. Zu jeder gefundenen Schlange werden einige Informationen ausgegeben. "
						+ "Dann wird die Reihenfolge der\nGlieder der jeweiligen Schlange in dem Format '(Zeichen, Zeile, Spalte)' "
						+ "angegeben und schließlich wird ein\nDschungel ausgegeben, der die Schlange hervorhebt. Hierbei "
						+ "werden die nicht genutzten Felder mit ( ) gekennzeichnet.\nEs ist hierbei zu beachten, dass "
						+ " der eingelesene Dschungel leere Felder enthaelt. Es wurden " + dschungel.anzahlFelder()
						+ " Felder erwartet aber\nnur " + dschungel.anzahlBelegterFelder()
						+ " wurden eingelesen. Die uebrigen Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel "
						+ "erzeugt\nwerden.\n\n" + toStringLoesung();
			} else {
				return "Der Dschungel dieses Problemes hat " + dschungel.getZeilen() + " Zeilen, "
						+ dschungel.getSpalten() + " Spalten und die Zeichenmenge '" + dschungel.getZeichenmenge()
						+ "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + dschungel.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + schlangenartenToString
						+ "\ngesucht werden. Zu jeder gefundenen Schlange werden einige Informationen ausgegeben. "
						+ "Dann wird die Reihenfolge der\nGlieder der jeweiligen Schlange in dem Format '(Zeichen, Zeile, Spalte)' "
						+ "angegeben und schließlich wird ein\nDschungel ausgegeben, der die Schlange hervorhebt. Hierbei "
						+ "werden die nicht genutzten Felder mit ( ) gekennzeichnet.\n\n" + toStringLoesung();
			}
		} catch (Exception e) {
			return "Beim Ausgeben des Modelles in einer Textausgabe ist ein Fehler aufgetreten.";
		}
	}

	private String toStringLoesung() {
		String output = "";
		int zeilenumbruch = 1;
		for (int i = 0; i < loesung.getSchlangen().size(); i++) {
			List<Field> loesungsFelder = new ArrayList<Field>();
			output += " (" + (i + 1) + ") Information: (ID=" + loesung.getSchlangen().get(i).getArt().getId()
					+ ", Zeichenkette=" + loesung.getSchlangen().get(i).getArt().getZeichenkette()
					+ ", Nachbarschaftsstruktur=" + loesung.getSchlangen().get(i).getArt().getStruktur().toString()
					+ ")\n\n     Verlauf: ";
			for (int l = 0; l < loesung.getSchlangen().get(i).getGlieder().size(); l++) {
				loesungsFelder.add(loesung.getSchlangen().get(i).getGlieder().get(l).getFeld());
				output += "(" + loesung.getSchlangen().get(i).getGlieder().get(l).getFeld().getZeichen() + ", "
						+ loesung.getSchlangen().get(i).getGlieder().get(l).getFeld().getZeile() + ", "
						+ loesung.getSchlangen().get(i).getGlieder().get(l).getFeld().getSpalte() + ")";
				if (l < loesung.getSchlangen().get(i).getGlieder().size() - 1) {
					output += " -> ";
				}
				if (l == 13) {
					output += "\n              ";
					zeilenumbruch++;
				} else if (l > zeilenumbruch * 13) {
					output += "\n              ";
					zeilenumbruch++;
				}
			}
			output += " \n\n     Dschungel: ";
			for (int j = 0; j < dschungel.getZeilen(); j++) {
				if (j > 0) {
					output += "                ";
				}
				for (int k = 0; k < dschungel.getSpalten(); k++) {
					if (loesungsFelder.contains(dschungel.getFelder()[j][k])) {
						output += " (" + dschungel.getFelder()[j][k].getZeichen() + ")";
					} else {
						output += " ( )";
					}
				}
				if (j != dschungel.getZeilen() - 1) {
					output += "\n\n";
				}
			}
			output += "\n\n\n";
		}
		return output;
	}

	@Override
	public Double[] getZeit() {
		return zeit;
	}

	@Override
	public void setZeit(Double[] zeit) throws IllegalArgumentException {
		if (zeit.length == 1) {
			if (zeit[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (zeit.length == 2) {
			if (zeit[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (zeit[0] < 0.0 || zeit[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (zeit[0] > Double.MAX_VALUE - 2E292 || zeit[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.zeit = zeit;
	}

	@Override
	public String getZeiteinheit() {
		return zeiteinheit;
	}

	@Override
	public void setZeiteinheit(String zeiteinheit) throws IllegalArgumentException {
		if (zeiteinheit.equals("ms") || zeiteinheit.equals("s") || zeiteinheit.equals("min") || zeiteinheit.equals("h")
				|| zeiteinheit.equals("d")) {
			this.zeiteinheit = zeiteinheit;
		} else {
			throw new IllegalArgumentException(
					"Es wurde eine ungueltige Zeiteinheit uebergeben. Gueltige Zeiteinheiten sind "
							+ "'ms' (Millisekunden), 's' (Sekunden), 'min' (Minuten), 'h' (Stunden) und 'd' (Tage).");
		}
	}

	@Override
	public Jungle getDschungel() {
		return dschungel;
	}

	@Override
	public void setDschungel(Jungle dschungel) {
		this.dschungel = dschungel;
	}

	@Override
	public List<SnakeType> getSchlangenarten() {
		return schlangenarten;
	}

	@Override
	public void setSchlangenarten(List<SnakeType> schlangenarten) {
		this.schlangenarten = schlangenarten;
	}

	@Override
	public void addSchlangenart(SnakeType inArt) {
		this.schlangenarten.add(inArt);
	}

	@Override
	public Solution getLoesung() {
		return loesung;
	}

	@Override
	public void setLoesung(Solution loesung) {
		this.loesung = loesung;

	}
}