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
	 * @param modell Das Modell, dessen Loesung der Loesungspruefer pruefen soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn dem
	 *                                  Konstruktor ein unpassendes Modell
	 *                                  uebergeben wird.
	 */
	public SolutionExaminer(IModel modell) throws IllegalArgumentException {
		super();
		if (modell.getSolution() == null || modell.getSolution().getSchlangen().size() == 0) {
			System.out.println("Das Modell, dass dem Loesungspruefer uebergeben werden soll, hat keine Loesung. Um "
					+ "eine Loesung zu\npruefen, muss ein Modell uebergeben werden, dass eine Loesung besitzt. Ist "
					+ "keine Loesung vorhanden,\nso kann diese mit der Schlangensuche gefunden werden. Es wird eine "
					+ "leere Fehlerliste\nzurueckgegeben.");
			System.out.println();
			throw new IllegalArgumentException("Dem Konstruktor von LoesungsPruefer muss ein Modell uebergeben werden, "
					+ "dass eine Loesung enthaelt.");
		}
		this.model = modell;
		this.jungle = modell.getJungle();
		this.snakes = modell.getSolution().getSchlangen();
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
		for (Snake schlange : snakes) {
			if (schlange.getElements().size() != schlange.getType().getZeichenkette().length()) {
				fehlerliste.add(ErrorType.GLIEDER);
			}
		}
	}

	private void examineAssignment(List<ErrorType> fehlerliste) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.ZUORDNUNG' ueberrpueft. Kommt ein solcher Fehler vor, wird fuer
		 * jeden dieser Fehler ein Element des entsprechenden Fehlertyps der Fehlerliste
		 * hinzugefuegt.
		 */
		for (Snake schlange : snakes) {
			if (schlange.getElements().size() == schlange.getType().getZeichenkette().length()) {
				for (int i = 0; i < schlange.getElements().size(); i++) {
					if (schlange.getElements().get(i).getField().getCharacter()
							.equals(schlange.getType().getZeichenkette().substring(i, i + 1)) == false) {
						fehlerliste.add(ErrorType.ZUORDNUNG);
					}

				}
			}
		}
	}

	private void examineUsage(List<ErrorType> fehlerliste) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.VERWENDUNG' ueberrpueft. Kommt ein solcher Fehler vor, wird fuer
		 * jeden dieser Fehler ein Element des entsprechenden Fehlertyps der Fehlerliste
		 * hinzugefuegt. Es wird ein 2 dimensionales Array mit Zahlen erstellt, um die
		 * Verwendbarkeit der Felder zu speichern. So kann die Loesung auf
		 * Verwendbarkeitsfehler untersucht werden ohne das Modell an sich zu aendern.
		 */
		int[][] verwendungsArr = new int[jungle.getRows()][jungle.getColumns()];
		for (int i = 0; i < verwendungsArr.length; i++) {
			for (int j = 0; j < verwendungsArr[0].length; j++) {
				verwendungsArr[i][j] = jungle.getFields()[i][j].getUsage();
			}
		}
		for (Snake schlange : snakes) {
			for (SnakeElement schlangenglied : schlange.getElements()) {
				if (verwendungsArr[schlangenglied.getField().getRow()][schlangenglied.getField().getColumn()] < 1) {
					fehlerliste.add(ErrorType.VERWENDUNG);
				}
				verwendungsArr[schlangenglied.getField().getRow()][schlangenglied.getField()
						.getColumn()] = verwendungsArr[schlangenglied.getField().getRow()][schlangenglied.getField()
								.getColumn()] - 1;
			}
		}
	}

	private void examineNeighborhood(List<ErrorType> fehlerliste) {
		/*
		 * Die im Modell enthaltene Loesung wird auf Fehler des Typs
		 * 'Fehlertyp.NACHBARSCHAFT' ueberrpueft. Kommt ein solcher Fehler vor, wird
		 * fuer jeden dieser Fehler ein Element des entsprechenden Fehlertyps der
		 * Fehlerliste hinzugefuegt.
		 */
		for (Snake schlange : snakes) {
			for (int i = 0; i < schlange.getElements().size() - 1; i++) {
				schlange.getType().getStruktur().getNeighbors(jungle, schlange.getElements().get(i).getField());
				if (schlange.getType().getStruktur().getNeighbors(jungle, schlange.getElements().get(i).getField())
						.contains(schlange.getElements().get(i + 1).getField()) == false) {
					fehlerliste.add(ErrorType.NACHBARSCHAFT);
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
	 * @param modell Das Modell, das uebergeben werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	public void setModel(IModel modell) throws IllegalArgumentException {
		if (modell.getSolution() == null || modell.getSolution().getSchlangen().size() == 0) {
			System.out.println("Das Modell, dass dem Loesungspruefer uebergeben werden soll, hat keine Loesung. Um "
					+ "eine Loesung zu\npruefen, muss ein Modell uebergeben werden, dass eine Loesung besitzt. Ist "
					+ "keine Loesung vorhanden,\nso kann diese mit der Schlangensuche gefunden werden. Es wird eine "
					+ "leere Fehlerliste\nzurueckgegeben.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem LoesungsPruefer muss ein Modell uebergeben werden, dass eine Loesung enthaelt.");
		}
		this.model = modell;
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