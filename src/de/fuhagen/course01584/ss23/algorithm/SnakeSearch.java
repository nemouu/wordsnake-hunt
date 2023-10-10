package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.model.*;

/**
 * Eine Klasse SchlangenSuche, die die Schnittstelle ISchlangenSuche und damit
 * die in ISchlangenSuche angegebenen Methoden implementiert. Es sind hier
 * verschiedene private Methoden zu finden, die die Funktionalitaet
 * ermoeglichen, die durch die Schnittstelle vorgegeben wurde.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeSearch implements ISnakeSearch {
	private IModel model;
	private Solution currSolution;
	private Solution solution;
	private SolutionEvaluator evaluator;
	private Long currTime;
	private Double modelTime;
	private ISnakeSearchUtil functions;

	/**
	 * Ein parametrisierter Konstruktor dem das Modell, in dem nach Schlangen
	 * gesucht werden soll, direkt uebergeben wird. Ist in dem Modell kein Dschungel
	 * vorhanden wird eine Ausnahme geworfen.
	 * 
	 * @param model Das Modell, in dem gesucht werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	public SnakeSearch(IModel model) throws IllegalArgumentException {
		super();
		if (model.getJungle().numberOfTakenFields() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem Konstruktor von SchlangenSuche muss ein Modell uebergeben werden, dass"
							+ " Felder im Dschungel hat.");
		}
		this.model = model;
		this.currSolution = new Solution();
		this.solution = new Solution();
		this.evaluator = new SolutionEvaluator();
		this.functions = new SnakeSearchUtil(model);
		this.modelTime = model.calculateTimeToNanoseconds();
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SnakeSearch() {
		super();
	}

	@Override
	public void searchSnakes() {
		this.currTime = System.nanoTime();
		searchSnake();
	}

	private void searchSnake() {
		/*
		 * Eine Methode, die in die SchlangenSuche einsteigt. Es werden Schlangen nach
		 * und nach aufgebaut und dann gegebenenfalls wieder abgebaut, falls keine neuen
		 * Glieder mehr gefunden werden koennen oder falls eine Loesung gespeichert
		 * wurde oder eine neue Kombination probiert wird. Aus dieser Methode wird die
		 * Methode 'sucheSchlangenglied' aufgerufen. Hier wird die 'loesung'
		 * ueberschrieben, falls die 'aktLoesung' mehr Punkte erzielt. Es ist zu
		 * beachten, dass zusaetzliche Zeitabfragen eingefuegt wurden, denn so
		 * terminiert das Programm schneller, wenn die Zeit abgelaufen ist.
		 */
		if (evaluator.evaluateSolution(currSolution) > evaluator.evaluateSolution(solution)) {
			solution = new Solution();
			for (Snake snake : currSolution.getSnakes()) {
				Snake newSnake = new Snake(snake.getType());
				for (SnakeElement element : snake.getElements()) {
					newSnake.addElement(element);
				}
				solution.addSnake(newSnake);
			}
		}

		/*
		 * Es wird zuerst ueber die Schlangenarten iteriert, um die Priorisierung ueber
		 * Schlangenarten zu vereinfachen.
		 */
		List<SnakeType> snakeTypes = functions.createValidSnakeTypes();
		for (SnakeType snakeType : snakeTypes) {
			List<Field> startingFields = functions.createValidStartingFields(snakeType);
			for (Field startingField : startingFields) {
				if (System.nanoTime() - currTime > modelTime) {
					return;
				}
				// Es wird eine neue Schlange erstellt mit entsprechendem Schlangenkopf.
				startingField.setUsage(startingField.getUsage() - 1);
				Snake newSnake = new Snake(snakeType);
				SnakeElement snakeHead = new SnakeElement(startingField);
				newSnake.addElement(snakeHead);
				currSolution.addSnake(newSnake);

				// Es wird versucht die weiteren Schlangenglieder zu suchen.
				searchSnakeElement(snakeHead, newSnake);

				/*
				 * Die Schlange wird und der Schlangenkopf werden wieder entfernt und die
				 * genutzten Felder werden wieder freigegeben.
				 */
				startingField.setUsage(startingField.getUsage() + 1);
				newSnake.removeLastElement();
				currSolution.removeLastSnake();
			}
		}
	}

	private void searchSnakeElement(SnakeElement previousElement, Snake currentSnake) {
		/*
		 * Eine Methode, die von der Methode 'sucheSchlange' und von sich selbst
		 * aufgerufen wird. Es wird immer das naechste Glied der aktuellen Schlange
		 * gesucht. So lange bis das entweder nicht mehr moeglich ist, weil kein
		 * passendes Folgeglied gefunden wird oder bis eine Loesung gespeichert oder bis
		 * eine neue Kombination probiert wird. Ist eine Schlange vollstaendig, wird
		 * hier in die Methode 'sucheSchlange' gesprungen, um nach der naechsten
		 * Schlange zu suchen.
		 */
		if (previousElement.getField().getCharacter().equals(currentSnake.characterOfLastElement())
				&& currentSnake.getElements().size() == currentSnake.getType().getSigns().length()) {
			searchSnake();
			return;
		}
		/*
		 * Es werden zulaessige Nachbarn aufgelistet und nach und nach ueber diese
		 * iteriert.
		 */
		List<Field> neighborFields = functions.createValidNeighbors(previousElement, currentSnake);
		for (Field neighborField : neighborFields) {
			if (System.nanoTime() - currTime > modelTime) {
				return;
			}

			// Fuege der aktuellen Schlange den naechsten Nachbar hinzu.
			neighborField.setUsage(neighborField.getUsage() - 1);
			SnakeElement newElement = new SnakeElement(neighborField);
			currentSnake.addElement(newElement);

			// Suche nach dem naechsten Schlangenglied.
			searchSnakeElement(newElement, currentSnake);

			/*
			 * Entferne die einzelnen Schlangenglieder wieder und gebe die genutzten Felder
			 * wieder frei.
			 */
			neighborField.setUsage(neighborField.getUsage() + 1);
			currentSnake.removeLastElement();
		}
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getJungle().numberOfTakenFields() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Der SchlangenSuche muss ein Modell uebergeben werden, dass" + " Felder im Dschungel hat.");
		}
		this.model = model;
	}

	@Override
	public Solution getSolution() {
		return solution;
	}

	@Override
	public Long getCurrTime() {
		return currTime;
	}

	@Override
	public ISnakeSearchUtil getFunctions() {
		return functions;
	}

	@Override
	public void setFunctions(ISnakeSearchUtil functions) {
		this.functions = functions;
	}
}