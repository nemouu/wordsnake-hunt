package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.main.SnakeHuntAPI.ErrorType;
import de.fuhagen.course01584.ss23.model.*;

/**
 * In dieser Klasse werden Konstruktoren und Methoden bereitgestellt, um die
 * Pruefung von Loesung zu Probleminstanzen der Schlangenjagd zu ermoeglichen.
 * Hierbei werden. Die einem Modell enthaltene Loesung wird in ausgesuchten
 * Methoden auf Fehler ueberprueft. Die Fehler haben hierbei den Typ der in dem
 * Interface SchlangenjagdAPI definierten Enum <code>Fehlertyp</code>.
 * 
 * @author Philip Redecker
 *
 */
public class SolutionExaminer {
	private IModel model;
	private Jungle jungle;
	private List<Snake> snakes;
	private List<ErrorType> errorList;

	/**
	 * Ein parametrisierter Konstruktor fuer die Klasse LoesungsPruefer, der ein
	 * Modell uebergeben wird. Wird ein (zu) leeres Modell uebergeben, so wird eine
	 * Ausnahme geworfen.
	 * 
	 * @param model Das Modell, dessen Loesung der Loesungspruefer pruefen soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn dem
	 *                                  Konstruktor ein unpassendes Modell
	 *                                  uebergeben wird.
	 */
	public SolutionExaminer(IModel model) throws IllegalArgumentException {
		super();
		if (model.getSolution() == null || model.getSolution().getSnakes().size() == 0) {
			System.out.println("Das Modell, dass dem Loesungspruefer uebergeben werden soll, hat keine Loesung. Um "
					+ "eine Loesung zu\npruefen, muss ein Modell uebergeben werden, dass eine Loesung besitzt. Ist "
					+ "keine Loesung vorhanden,\nso kann diese mit der Schlangensuche gefunden werden. Es wird eine "
					+ "leere Fehlerliste\nzurueckgegeben.");
			System.out.println();
			throw new IllegalArgumentException("Dem Konstruktor von LoesungsPruefer muss ein Modell uebergeben werden, "
					+ "dass eine Loesung enthaelt.");
		}
		this.model = model;
		this.jungle = model.getJungle();
		this.snakes = model.getSolution().getSnakes();
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SolutionExaminer() {
		super();
	}

	/**
	 * Eine oeffentliche Methode, die mehrere private Methoden nutzt, um die Art und
	 * Anzahl der Fehler vom Typ <code>Fehlertyp</code> zu ermitteln. Kommt ein
	 * Fehler mehrmals vor, so ist fuer jeden dieser Fehler ein Element in der
	 * zurueckgegebenen Fehlerliste.
	 * 
	 * @return Die (ungeordnete) Liste mit allen in der Loesung vorkommenen Fehler.
	 */
	public List<ErrorType> examineSolution() {
		errorList = new ArrayList<ErrorType>();
		examineNumber(errorList);
		examineAssignment(errorList);
		examineUsage(errorList);
		examineNeighborhood(errorList);
		return errorList;
	}

	private void examineNumber(List<ErrorType> fehlerliste) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs 'Fehlertyp.GLIEDER'
		 * ueberrpueft. Kommt ein solcher Fehler vor, wird fuer jeden dieser Fehler ein
		 * Element des entsprechenden Fehlertyps der Fehlerliste hinzugefuegt.
		 */
		for (Snake snake : snakes) {
			if (snake.getElements().size() != snake.getType().getSigns().length()) {
				fehlerliste.add(ErrorType.ELEMENTS);
			}
		}
	}

	private void examineAssignment(List<ErrorType> errorList) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.ZUORDNUNG' ueberrpueft. Kommt ein solcher Fehler vor, wird fuer
		 * jeden dieser Fehler ein Element des entsprechenden Fehlertyps der Fehlerliste
		 * hinzugefuegt.
		 */
		for (Snake snake : snakes) {
			if (snake.getElements().size() == snake.getType().getSigns().length()) {
				for (int i = 0; i < snake.getElements().size(); i++) {
					if (snake.getElements().get(i).getField().getCharacter()
							.equals(snake.getType().getSigns().substring(i, i + 1)) == false) {
						errorList.add(ErrorType.ASSIGNMENT);
					}

				}
			}
		}
	}

	private void examineUsage(List<ErrorType> errorList) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.VERWENDUNG' ueberrpueft. Kommt ein solcher Fehler vor, wird fuer
		 * jeden dieser Fehler ein Element des entsprechenden Fehlertyps der Fehlerliste
		 * hinzugefuegt. Es wird ein 2 dimensionales Array mit Zahlen erstellt, um die
		 * Verwendbarkeit der Felder zu speichern. So kann die Loesung auf
		 * Verwendbarkeitsfehler untersucht werden ohne das Modell an sich zu aendern.
		 */
		int[][] usageMatrix = new int[jungle.getRows()][jungle.getColumns()];
		for (int i = 0; i < usageMatrix.length; i++) {
			for (int j = 0; j < usageMatrix[0].length; j++) {
				usageMatrix[i][j] = jungle.getFields()[i][j].getUsage();
			}
		}
		for (Snake snake : snakes) {
			for (SnakeElement element : snake.getElements()) {
				if (usageMatrix[element.getField().getRow()][element.getField().getColumn()] < 1) {
					errorList.add(ErrorType.USAGE);
				}
				usageMatrix[element.getField().getRow()][element.getField()
						.getColumn()] = usageMatrix[element.getField().getRow()][element.getField()
								.getColumn()] - 1;
			}
		}
	}

	private void examineNeighborhood(List<ErrorType> errorList) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.NACHBARSCHAFT' ueberrpueft. Kommt ein solcher Fehler vor, wird
		 * fuer jeden dieser Fehler ein Element des entsprechenden Fehlertyps der
		 * Fehlerliste hinzugefuegt.
		 */
		for (Snake snake : snakes) {
			for (int i = 0; i < snake.getElements().size() - 1; i++) {
				snake.getType().getStructure().getNeighbors(jungle, snake.getElements().get(i).getField());
				if (snake.getType().getStructure().getNeighbors(jungle, snake.getElements().get(i).getField())
						.contains(snake.getElements().get(i + 1).getField()) == false) {
					errorList.add(ErrorType.NEIGHBORHOOD);
				}
			}
		}
	}

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell im LoesungsPruefer
	 * befindet. Dies ist vor allem fuer kuenftige Aenderungen und auch fuer damit
	 * einhergehende Tests gedacht.
	 * 
	 * @return Wert der Variablen <code>modell</code>.
	 */
	public IModel getModel() {
		return model;
	}

	/**
	 * Es kann das Modell des LoesungsPruefers gesetzt werden. So ist es zum
	 * Beispiel moeglich ein Modell zu uebergeben, auch wenn zunaechst der
	 * parameterlose Konstruktor genutzt wurde. Es ist auch im Allgemeinen moeglich
	 * das Modell nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param model Das Modell, das uebergeben werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getSolution() == null || model.getSolution().getSnakes().size() == 0) {
			System.out.println("Das Modell, dass dem Loesungspruefer uebergeben werden soll, hat keine Loesung. Um "
					+ "eine Loesung zu\npruefen, muss ein Modell uebergeben werden, dass eine Loesung besitzt. Ist "
					+ "keine Loesung vorhanden,\nso kann diese mit der Schlangensuche gefunden werden. Es wird eine "
					+ "leere Fehlerliste\nzurueckgegeben.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem LoesungsPruefer muss ein Modell uebergeben werden, dass eine Loesung enthaelt.");
		}
		this.model = model;
	}

	/**
	 * Es wird die aktuelle Fehlerliste des LoesungsPruefers zurueckgegeben. Dies
	 * ist vor allem fuer kuenftige Aenderungen und auch fuer damit einhergehende
	 * Tests gedacht.
	 * 
	 * @return Die (aktuelle) Fehlerliste des LoesungsPruefers.
	 */
	public List<ErrorType> getErrorList() {
		return errorList;
	}

	/**
	 * Es wird die aktuelle Liste mit Schlangen des LoesungsPruefers zurueckgegeben.
	 * Dies ist vor allem fuer kuenftige Aenderungen und auch fuer damit
	 * einhergehende Tests gedacht.
	 * 
	 * @return Die Schlangenliste des LoesungsPruefers.
	 */
	public List<Snake> getSnakes() {
		return snakes;
	}
}