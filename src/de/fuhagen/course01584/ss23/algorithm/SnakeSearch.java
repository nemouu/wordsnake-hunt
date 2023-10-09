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
	 * @param modell Das Modell, in dem gesucht werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	public SnakeSearch(IModel modell) throws IllegalArgumentException {
		super();
		if (modell.getJungle().numberOfTakenFields() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem Konstruktor von SchlangenSuche muss ein Modell uebergeben werden, dass"
							+ " Felder im Dschungel hat.");
		}
		this.model = modell;
		this.currSolution = new Solution();
		this.solution = new Solution();
		this.evaluator = new SolutionEvaluator();
		this.functions = new SnakeSearchUtil(modell);
		this.modelTime = modell.calculateTimeToNanoseconds(modell.getTime()[0]);
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
			for (Snake schlange : currSolution.getSchlangen()) {
				Snake neueSchlange = new Snake(schlange.getType());
				for (SnakeElement glied : schlange.getElements()) {
					neueSchlange.addElement(glied);
				}
				solution.addSchlange(neueSchlange);
			}
		}

		/*
		 * Es wird zuerst ueber die Schlangenarten iteriert, um die Priorisierung ueber
		 * Schlangenarten zu vereinfachen.
		 */
		List<SnakeType> schlangenarten = functions.createValidSnakeTypes();
		for (SnakeType schlangenart : schlangenarten) {
			List<Field> startfelder = functions.createValidStartingFields(schlangenart);
			for (Field startfeld : startfelder) {
				if (System.nanoTime() - currTime > modelTime) {
					return;
				}
				// Es wird eine neue Schlange erstellt mit entsprechendem Schlangenkopf.
				startfeld.setUsage(startfeld.getUsage() - 1);
				Snake neueSchlange = new Snake(schlangenart);
				SnakeElement schlangenkopf = new SnakeElement(startfeld);
				neueSchlange.addElement(schlangenkopf);
				currSolution.addSchlange(neueSchlange);

				// Es wird versucht die weiteren Schlangenglieder zu suchen.
				searchSnakeElement(schlangenkopf, neueSchlange);

				/*
				 * Die Schlange wird und der Schlangenkopf werden wieder entfernt und die
				 * genutzten Felder werden wieder freigegeben.
				 */
				startfeld.setUsage(startfeld.getUsage() + 1);
				neueSchlange.removeLastElement();
				currSolution.entferneLetzteSchlange();
			}
		}
	}

	private void searchSnakeElement(SnakeElement vorherigesGlied, Snake aktSchlange) {
		/*
		 * Eine Methode, die von der Methode 'sucheSchlange' und von sich selbst
		 * aufgerufen wird. Es wird immer das naechste Glied der aktuellen Schlange
		 * gesucht. So lange bis das entweder nicht mehr moeglich ist, weil kein
		 * passendes Folgeglied gefunden wird oder bis eine Loesung gespeichert oder bis
		 * eine neue Kombination probiert wird. Ist eine Schlange vollstaendig, wird
		 * hier in die Methode 'sucheSchlange' gesprungen, um nach der naechsten
		 * Schlange zu suchen.
		 */
		if (vorherigesGlied.getField().getCharacter().equals(aktSchlange.characterOfLastElement())
				&& aktSchlange.getElements().size() == aktSchlange.getType().getZeichenkette().length()) {
			searchSnake();
			return;
		}
		/*
		 * Es werden zulaessige Nachbarn aufgelistet und nach und nach ueber diese
		 * iteriert.
		 */
		List<Field> nachbarn = functions.createValidNeighbors(vorherigesGlied, aktSchlange);
		for (Field nachbar : nachbarn) {
			if (System.nanoTime() - currTime > modelTime) {
				return;
			}

			// Fuege der aktuellen Schlange den naechsten Nachbar hinzu.
			nachbar.setUsage(nachbar.getUsage() - 1);
			SnakeElement neuesGlied = new SnakeElement(nachbar);
			aktSchlange.addElement(neuesGlied);

			// Suche nach dem naechsten Schlangenglied.
			searchSnakeElement(neuesGlied, aktSchlange);

			/*
			 * Entferne die einzelnen Schlangenglieder wieder und gebe die genutzten Felder
			 * wieder frei.
			 */
			nachbar.setUsage(nachbar.getUsage() + 1);
			aktSchlange.removeLastElement();
		}
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel modell) throws IllegalArgumentException {
		if (modell.getJungle().numberOfTakenFields() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Der SchlangenSuche muss ein Modell uebergeben werden, dass" + " Felder im Dschungel hat.");
		}
		this.model = modell;
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
	public void setFunctions(ISnakeSearchUtil funktionen) {
		this.functions = funktionen;
	}
}