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
	private String unitOfTime;
	private Double[] time;
	private Jungle jungle;
	private List<SnakeType> snakeTypes;
	private Solution solution;

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
		this.jungle = dschungel;
		this.snakeTypes = schlangenarten;
		this.solution = loesung;
		this.time = zeit;
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
		this.jungle = dschungel;
		this.snakeTypes = new ArrayList<SnakeType>();
		this.solution = loesung;
		this.time = zeit;
		if (unitOfTime == null) {
			unitOfTime = "s";
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
		this.snakeTypes = new ArrayList<SnakeType>();
	}

	@Override
	public Double calculateTimeToNanoseconds(Double vorgabeAusModell) throws IllegalArgumentException {
		switch (getUnitOfTime()) {
		case "ms": {
			return getTime()[0] * 1000000;
		}
		case "s": {
			return getTime()[0] * 1000000000;
		}
		case "min": {
			return getTime()[0] * 6.0 * (Math.pow(10, 10));
		}
		case "h": {
			return getTime()[0] * 3.6 * (Math.pow(10, 12));
		}
		case "d": {
			return getTime()[0] * 8.64 * (Math.pow(10, 13));
		}
		default:
			throw new IllegalArgumentException("Die Zeitangabe des Modelles kann nicht umgerechnet werden.");
		}
	}

	@Override
	public Double calculateTimeInUnitGivenByModel(Long genutzteZeitInNanosekunden) throws IllegalArgumentException {
		switch (getUnitOfTime()) {
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
		for (SnakeType schlangenart : snakeTypes) {
			schlangenartenToString += " (" + schlangenart.toString() + ")\n";
		}
		try {
			if (jungle.numberOfTakenFields() == 0) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, "
						+ jungle.getColumns() + " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "' \naber keine Felder und es kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden. Die Felder koennen mit dem Befehl 'e' erzeugt werden. Die Loesung hierzu ist "
						+ "nicht \nvorhanden und kann mit dem Befehl 'l' gesucht werden.";
			} else if (solution == null && jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, "
						+ jungle.getColumns() + " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ jungle.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung enthalten. Dabei ist jedoch zu beachten, dass"
						+ " der eingelesene\nDschungel leere Felder enthaelt. Es wurden " + jungle.numberOfFields()
						+ " Felder erwartet aber nur " + jungle.numberOfTakenFields()
						+ " wurden eingelesen. Die uebrigen\n"
						+ "Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel erzeugt werden.";
			} else if (solution == null || solution.getSchlangen().size() == 0) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, "
						+ jungle.getColumns() + " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + jungle.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + schlangenartenToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung vorhanden. Es kann mit dem Befehl 'l' nach einer"
						+ " Loesung\ngesucht werden.";
			} else if (jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, "
						+ jungle.getColumns() + " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ jungle.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n"
						+ schlangenartenToString
						+ "\ngesucht werden. Zu jeder gefundenen Schlange werden einige Informationen ausgegeben. "
						+ "Dann wird die Reihenfolge der\nGlieder der jeweiligen Schlange in dem Format '(Zeichen, Zeile, Spalte)' "
						+ "angegeben und schließlich wird ein\nDschungel ausgegeben, der die Schlange hervorhebt. Hierbei "
						+ "werden die nicht genutzten Felder mit ( ) gekennzeichnet.\nEs ist hierbei zu beachten, dass "
						+ " der eingelesene Dschungel leere Felder enthaelt. Es wurden " + jungle.numberOfFields()
						+ " Felder erwartet aber\nnur " + jungle.numberOfTakenFields()
						+ " wurden eingelesen. Die uebrigen Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel "
						+ "erzeugt\nwerden.\n\n" + toStringSolution();
			} else {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, "
						+ jungle.getColumns() + " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + jungle.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + schlangenartenToString
						+ "\ngesucht werden. Zu jeder gefundenen Schlange werden einige Informationen ausgegeben. "
						+ "Dann wird die Reihenfolge der\nGlieder der jeweiligen Schlange in dem Format '(Zeichen, Zeile, Spalte)' "
						+ "angegeben und schließlich wird ein\nDschungel ausgegeben, der die Schlange hervorhebt. Hierbei "
						+ "werden die nicht genutzten Felder mit ( ) gekennzeichnet.\n\n" + toStringSolution();
			}
		} catch (Exception e) {
			return "Beim Ausgeben des Modelles in einer Textausgabe ist ein Fehler aufgetreten.";
		}
	}

	private String toStringSolution() {
		String output = "";
		int zeilenumbruch = 1;
		for (int i = 0; i < solution.getSchlangen().size(); i++) {
			List<Field> loesungsFelder = new ArrayList<Field>();
			output += " (" + (i + 1) + ") Information: (ID=" + solution.getSchlangen().get(i).getType().getId()
					+ ", Zeichenkette=" + solution.getSchlangen().get(i).getType().getZeichenkette()
					+ ", Nachbarschaftsstruktur=" + solution.getSchlangen().get(i).getType().getStruktur().toString()
					+ ")\n\n     Verlauf: ";
			for (int l = 0; l < solution.getSchlangen().get(i).getElements().size(); l++) {
				loesungsFelder.add(solution.getSchlangen().get(i).getElements().get(l).getField());
				output += "(" + solution.getSchlangen().get(i).getElements().get(l).getField().getCharacter() + ", "
						+ solution.getSchlangen().get(i).getElements().get(l).getField().getRow() + ", "
						+ solution.getSchlangen().get(i).getElements().get(l).getField().getColumn() + ")";
				if (l < solution.getSchlangen().get(i).getElements().size() - 1) {
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
			for (int j = 0; j < jungle.getRows(); j++) {
				if (j > 0) {
					output += "                ";
				}
				for (int k = 0; k < jungle.getColumns(); k++) {
					if (loesungsFelder.contains(jungle.getFields()[j][k])) {
						output += " (" + jungle.getFields()[j][k].getCharacter() + ")";
					} else {
						output += " ( )";
					}
				}
				if (j != jungle.getRows() - 1) {
					output += "\n\n";
				}
			}
			output += "\n\n\n";
		}
		return output;
	}

	@Override
	public Double[] getTime() {
		return time;
	}

	@Override
	public void setTime(Double[] zeit) throws IllegalArgumentException {
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
		this.time = zeit;
	}

	@Override
	public String getUnitOfTime() {
		return unitOfTime;
	}

	@Override
	public void setUnitOfTime(String zeiteinheit) throws IllegalArgumentException {
		if (zeiteinheit.equals("ms") || zeiteinheit.equals("s") || zeiteinheit.equals("min") || zeiteinheit.equals("h")
				|| zeiteinheit.equals("d")) {
			this.unitOfTime = zeiteinheit;
		} else {
			throw new IllegalArgumentException(
					"Es wurde eine ungueltige Zeiteinheit uebergeben. Gueltige Zeiteinheiten sind "
							+ "'ms' (Millisekunden), 's' (Sekunden), 'min' (Minuten), 'h' (Stunden) und 'd' (Tage).");
		}
	}

	@Override
	public Jungle getJungle() {
		return jungle;
	}

	@Override
	public void setJungle(Jungle dschungel) {
		this.jungle = dschungel;
	}

	@Override
	public List<SnakeType> getSnakeTypes() {
		return snakeTypes;
	}

	@Override
	public void setSnakeTypes(List<SnakeType> schlangenarten) {
		this.snakeTypes = schlangenarten;
	}

	@Override
	public void addSnakeType(SnakeType inArt) {
		this.snakeTypes.add(inArt);
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public void setSolution(Solution loesung) {
		this.solution = loesung;

	}
}