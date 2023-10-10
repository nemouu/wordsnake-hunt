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
	 * @param jungle     Ein Dschungel gemaess der in diesem Paket definierten
	 *                   Datenstruktur Dschungel.
	 * @param snakeTypes Eine Liste von Schlangenarten gemaess der in diesem Paket
	 *                   definierten Datenstruktur Schlangenart.
	 * @param solution   Eine Loesung gemaess der in diesem Paket definierten
	 *                   Datenstruktur Loesung.
	 * @param time       Eine Array von Doubles fuer die Zeitvorgabe und
	 *                   beziehungsweise oder die Zeitabgabe.
	 * @throws IllegalArgumentException Wird fuer das Attribut Zeit ein ungueltiges
	 *                                  Argument uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public ProblemModel(Jungle jungle, List<SnakeType> snakeTypes, Solution solution, Double[] time)
			throws IllegalArgumentException {
		super();
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0 || time[1] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.jungle = jungle;
		this.snakeTypes = snakeTypes;
		this.solution = solution;
		this.time = time;
	}

	/**
	 * Ein alternativer parametrisierter Konstruktor, der es erlaubt ein Modell zu
	 * instanziieren ohne eine Liste von Schlangenarten zu uebergeben. Hierbei wird
	 * dann im Konstruktor eine leere Liste mit Schlangenarten erzeugt, damit dort
	 * spaeter Schlangenarten hinzugefuegt werden koennen.
	 * 
	 * @param jungle   Ein Dschungel gemaess der in diesem Paket definierten
	 *                 Datenstruktur Dschungel.
	 * @param solution Eine Loesung gemaess der in diesem Paket definierten
	 *                 Datenstruktur Loesung.
	 * @param time     Eine Array von Doubles fuer die Zeitvorgabe und
	 *                 beziehungsweise oder die Zeitabgabe.
	 * @throws IllegalArgumentException Wird fuer das Attribut Zeit ein ungueltiges
	 *                                  Argument uebergeben, wird eine Ausnahme
	 *                                  ausgeloest.
	 */
	public ProblemModel(Jungle jungle, Solution solution, Double[] time) throws IllegalArgumentException {
		super();
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.jungle = jungle;
		this.snakeTypes = new ArrayList<SnakeType>();
		this.solution = solution;
		this.time = time;
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
	public Double calculateTimeToNanoseconds() throws IllegalArgumentException {
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
	public Double calculateTimeInUnitGivenByModel(Long usedTimeInNanoseconds) throws IllegalArgumentException {
		switch (getUnitOfTime()) {
		case "ms": {
			return (double) (usedTimeInNanoseconds / 1000000.0);
		}
		case "s": {
			return (double) (usedTimeInNanoseconds / 1000000000.0);
		}
		case "min": {
			return (double) (usedTimeInNanoseconds / (6.0 * (Math.pow(10, 10))));
		}
		case "h": {
			return (double) (usedTimeInNanoseconds / (3.6 * (Math.pow(10, 12))));
		}
		case "d": {
			return (double) (usedTimeInNanoseconds / (8.64 * (Math.pow(10, 13))));
		}
		default:
			throw new IllegalArgumentException("Die Zeitangabe des Modelles kann nicht umgerechnet werden.");
		}
	}

	@Override
	public String toString() {
		String snakeTypesToString = "";
		for (SnakeType snakeType : snakeTypes) {
			snakeTypesToString += " (" + snakeType.toString() + ")\n";
		}
		try {
			if (jungle.numberOfTakenFields() == 0) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, " + jungle.getColumns()
						+ " Spalten und die Zeichenmenge '" + jungle.getSigns()
						+ "' \naber keine Felder und es kann nach Schlangen der Schlangenart/en \n\n"
						+ snakeTypesToString
						+ "\ngesucht werden. Die Felder koennen mit dem Befehl 'e' erzeugt werden. Die Loesung hierzu ist "
						+ "nicht \nvorhanden und kann mit dem Befehl 'l' gesucht werden.";
			} else if (solution == null && jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, " + jungle.getColumns()
						+ " Spalten und die Zeichenmenge '" + jungle.getSigns() + "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ jungle.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n" + snakeTypesToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung enthalten. Dabei ist jedoch zu beachten, dass"
						+ " der eingelesene\nDschungel leere Felder enthaelt. Es wurden " + jungle.numberOfFields()
						+ " Felder erwartet aber nur " + jungle.numberOfTakenFields()
						+ " wurden eingelesen. Die uebrigen\n"
						+ "Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel erzeugt werden.";
			} else if (solution == null || solution.getSnakes().size() == 0) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, " + jungle.getColumns()
						+ " Spalten und die Zeichenmenge '" + jungle.getSigns() + "'. "
						+ "Die Felder werden\nimmer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + jungle.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + snakeTypesToString
						+ "\ngesucht werden und im Modell ist aktuell keine Loesung vorhanden. Es kann mit dem Befehl 'l' nach einer"
						+ " Loesung\ngesucht werden.";
			} else if (jungle.numberOfTakenFields() < jungle.numberOfFields()) {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, " + jungle.getColumns()
						+ " Spalten und die Zeichenmenge '" + jungle.getSigns() + "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und leere Felder "
						+ " werden durch '(Ø, 0, 0)'\ngekennzeichnet. Die Felder sind wie folgt angeordnet: \n\n\n"
						+ jungle.toString() + "\nEs kann nach Schlangen der Schlangenart/en \n\n" + snakeTypesToString
						+ "\ngesucht werden. Zu jeder gefundenen Schlange werden einige Informationen ausgegeben. "
						+ "Dann wird die Reihenfolge der\nGlieder der jeweiligen Schlange in dem Format '(Zeichen, Zeile, Spalte)' "
						+ "angegeben und schließlich wird ein\nDschungel ausgegeben, der die Schlange hervorhebt. Hierbei "
						+ "werden die nicht genutzten Felder mit ( ) gekennzeichnet.\nEs ist hierbei zu beachten, dass "
						+ " der eingelesene Dschungel leere Felder enthaelt. Es wurden " + jungle.numberOfFields()
						+ " Felder erwartet aber\nnur " + jungle.numberOfTakenFields()
						+ " wurden eingelesen. Die uebrigen Felder sind leer. Mit dem Befehl 'e' kann ein vollstaendiger Dschungel "
						+ "erzeugt\nwerden.\n\n" + toStringSolution();
			} else {
				return "Der Dschungel dieses Problemes hat " + jungle.getRows() + " Zeilen, " + jungle.getColumns()
						+ " Spalten und die Zeichenmenge '" + jungle.getSigns() + "'. "
						+ "Die\nFelder werden immer in dem Format '(Zeichen, Verwendbarkeit, Punkte)' angegeben und"
						+ " sind wie folgt angeordnet: \n\n\n" + jungle.toString() + "\nEs kann nach Schlangen"
						+ " der Schlangenart/en \n\n" + snakeTypesToString
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
		int newLine = 1;
		for (int i = 0; i < solution.getSnakes().size(); i++) {
			List<Field> solutionFields = new ArrayList<Field>();
			output += " (" + (i + 1) + ") Information: (ID=" + solution.getSnakes().get(i).getType().getId()
					+ ", Zeichenkette=" + solution.getSnakes().get(i).getType().getSigns() + ", Nachbarschaftsstruktur="
					+ solution.getSnakes().get(i).getType().getStructure().toString() + ")\n\n     Verlauf: ";
			for (int l = 0; l < solution.getSnakes().get(i).getElements().size(); l++) {
				solutionFields.add(solution.getSnakes().get(i).getElements().get(l).getField());
				output += "(" + solution.getSnakes().get(i).getElements().get(l).getField().getCharacter() + ", "
						+ solution.getSnakes().get(i).getElements().get(l).getField().getRow() + ", "
						+ solution.getSnakes().get(i).getElements().get(l).getField().getColumn() + ")";
				if (l < solution.getSnakes().get(i).getElements().size() - 1) {
					output += " -> ";
				}
				if (l == 13) {
					output += "\n              ";
					newLine++;
				} else if (l > newLine * 13) {
					output += "\n              ";
					newLine++;
				}
			}
			output += " \n\n     Dschungel: ";
			for (int j = 0; j < jungle.getRows(); j++) {
				if (j > 0) {
					output += "                ";
				}
				for (int k = 0; k < jungle.getColumns(); k++) {
					if (solutionFields.contains(jungle.getFields()[j][k])) {
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
	public void setTime(Double[] time) throws IllegalArgumentException {
		if (time.length == 1) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		if (time.length == 2) {
			if (time[0] == 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht gleich 0 sein.");
			}
			if (time[0] < 0.0 || time[1] < 0.0) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht kleiner 0 sein.");
			}
			if (time[0] > Double.MAX_VALUE - 2E292 || time[1] > Double.MAX_VALUE - 2E292) {
				throw new IllegalArgumentException(
						"Das Attribut 'zeit' darf in der Klasse 'ProblemModell' nicht zu gross sein.");
			}
		}
		this.time = time;
	}

	@Override
	public String getUnitOfTime() {
		return unitOfTime;
	}

	@Override
	public void setUnitOfTime(String unitOfTime) throws IllegalArgumentException {
		if (unitOfTime.equals("ms") || unitOfTime.equals("s") || unitOfTime.equals("min") || unitOfTime.equals("h")
				|| unitOfTime.equals("d")) {
			this.unitOfTime = unitOfTime;
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
	public void setJungle(Jungle jungle) {
		this.jungle = jungle;
	}

	@Override
	public List<SnakeType> getSnakeTypes() {
		return snakeTypes;
	}

	@Override
	public void setSnakeTypes(List<SnakeType> snakeTypes) {
		this.snakeTypes = snakeTypes;
	}

	@Override
	public void addSnakeType(SnakeType inTypes) {
		this.snakeTypes.add(inTypes);
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public void setSolution(Solution solution) {
		this.solution = solution;

	}
}