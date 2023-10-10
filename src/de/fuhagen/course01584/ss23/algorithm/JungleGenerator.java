package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.model.*;

/**
 * In dieser Klasse werden Konstruktoren und Methoden bereitgestellt, um die
 * Erzeugung einfacher Probleminstanzen fuer die Schlangenjagd zu ermoeglichen.
 * Dies bedeutet, dass sich Schlangen bei der Erzeugung solcher Probleminstanzen
 * nicht ueberschneiden koennen sollen, jedes Feld also maximal ein Mal
 * verwendet werden darf. Zur Erzeugung werden die Daten zu Zeilen, Spalten und
 * der Zeichenmenge des Dschungels und die Daten zu zu verteilenden
 * Schlangenarten genutzt.
 * 
 * @author Philip Redecker
 *
 */
public class JungleGenerator {
	private IModel model;
	private int minimumSnakes;
	private SnakeType currentType;
	private Jungle jungle;
	private Jungle newJungle;
	private Long currentTime;
	private List<SnakeType> snakeTypes;
	private boolean failed;
	private boolean full;

	/**
	 * Erstellt bei Uebergabe eines geeigneten Modelles eine Probleminstanz auf
	 * Basis der Daten des Modelles. Fehlen die Daten oder Teile der Daten, so wird
	 * eine Fehlermeldung ausgegeben.
	 * 
	 * @param model Das Modell auf Basis dessen der Dschungelgenerator den
	 *               Dschungel generiert. Es ist darauf zu achten ein Modell zu
	 *               uebergeben, dass genug Daten hat, so, dass ein Dschungel
	 *               erzeugt werden kann. Ist dies nicht der Fall wird eine
	 *               Fehlermeldung ausgegeben.
	 *
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn dem
	 *                                  Konstruktor ein unpassendes Modell
	 *                                  uebergeben wird.
	 */
	public JungleGenerator(IModel model) throws IllegalArgumentException {
		super();
		this.snakeTypes = model.getSnakeTypes();
		if (model.getJungle().getRows() * model.getJungle().getColumns() < minimumNumberUsedFields()) {
			System.out
					.println("Das Modell, dass dem Dschungelgenerator uebergeben werden soll enthaelt mehr\nSchlangen "
							+ "als in den Dschungel passen. Es kann kein Dschungel generiert werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem Konstruktor des Dschungelgenerators kann kein Modell uebergeben werden, dass"
							+ " mehr Schlangen hat, als in den Dschungel passen.");
		}
		this.model = model;
		this.minimumSnakes = minimumNumberUsedFields();
		this.jungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
				model.getJungle().getSigns(), 1);
		this.newJungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
				model.getJungle().getSigns(), 1);
		this.currentTime = System.nanoTime();
		this.failed = false;
		this.full = false;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public JungleGenerator() {
		super();
	}

	/**
	 * Die hauptsaechliche Methode dieser Klasse. Es werden insgesamt drei andere
	 * private Methoden dieser Klasse genutzt, um basierend auf den Daten des
	 * Modelles einen Dschungel zu erzeugen. Es werden nach und nach alle Schlangen
	 * durchgegangen und verteilt. Hierbei wird immer die private Methode
	 * <code>platziereSchlangenglied</code> genutzt, um fuer die in dieser Methode
	 * platzierten Schlangen, die Glieder der Schlangen zu platzieren. Passt eine
	 * Schlange nicht mehr in den Dschungel, wird sie Stueck fuer Stueck wieder
	 * entfernt und es wird versucht die Schlangen anders zu positionieren. Es wird
	 * an dieser Stelle beruecksichtigt, dass der Dschungelgenerator bei besonders
	 * unguenstiger Verteilung der Schlangen nicht schnell genug terminiert.
	 * Passiert dies, so wird unter den gleichen Vorgaben ein neuer Versuch
	 * gestartet.
	 * 
	 * @throws Exception Schlaegt der Dschungelgenerator zwei mal in Folge fehl,
	 *                   wird eine Ausnahme erzeugt.
	 */
	public void generateJungle() throws Exception {
		placeSnake();
		fillRestOfJungle(newJungle);
		if (failed) {
			System.out.println("Der Generator ist fehlgeschlagen. Moeglicherweise sind die Schlangen unguenstig\n"
					+ "verteilt worden. Versuche noch einmal...");
			System.out.println();
			this.snakeTypes = model.getSnakeTypes();
			this.minimumSnakes = minimumNumberUsedFields();
			this.jungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
					model.getJungle().getSigns(), 1);
			this.newJungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
					model.getJungle().getSigns(), 1);
			this.currentTime = System.nanoTime();
			this.failed = false;
			placeSnake();
			fillRestOfJungle(newJungle);
			if (failed) {
				System.out.println(
						"Der Generator ist wieder fehlgeschlagen. Moeglicherweise sind die Schlangen wieder unguenstig\n"
								+ "verteilt worden. Ein Neustart des Programmes kann das Problem beheben. Sollte es wieder zu\n"
								+ "einem Fehler kommen, liegt moeglicherweise ein anderes Problem vor.");
				System.out.println();
				throw new Exception();
			}
		}
	}

	private void placeSnake() {
		/*
		 * Hier wird geprueft, ob alle Schlangen verteilt worden sein. Ist dies der
		 * Fall, so werden sie einem neuenDschungel hinzugefuegt. Fuer die Generierung
		 * eines Dschungel wurde ein Zeitlimit von 10 Sekunden gesetzt.
		 */
		if (jungle.numberOfTakenFields() == minimumSnakes && numberOfTypesTotal() == 0 && full == false) {
			for (int i = 0; i < jungle.getFields().length; i++) {
				for (int j = 0; j < jungle.getFields()[0].length; j++) {
					newJungle.getFields()[i][j] = new Field(jungle.getFields()[i][j].getId(), i, j, 1, 1,
							jungle.getFields()[i][j].getCharacter());
				}
			}
			this.full = true;
			return;
		}
		if (System.nanoTime() - currentTime > 10000000000.0 || full == true) {
			return;
		}

		// Es werden alle Schlangenarten, deren Anzahl noch groesser 0 ist aufgelistet.
		List<SnakeType> validSnakeTypes = createValidSnakeTypes();
		for (SnakeType snakeType : validSnakeTypes) {
			snakeType.setAmount(snakeType.getAmount() - 1);
			SnakeType previousSnakeType = currentType;
			currentType = snakeType;

			/*
			 * Es werden alle Startfelder fuer die aktuelle Schlangenart aufgelistet und
			 * fuer jedes dieser Startfelder wird nun versucht eine Schlange der aktuellen
			 * Schlangenart zu platzieren. Ist das Platzieren fehlgeschlangen, wird die
			 * Anzahl der Art wieder erhoeht, damit das Platzieren nochmal mit einem anderen
			 * Startfeld versucht werden kann.
			 */
			List<Field> validStartingFields = createValidStartingFields();
			for (Field startingField : validStartingFields) {
				jungle.getFields()[startingField.getRow()][startingField.getColumn()]
						.setCharacter(currentType.getSigns().substring(0, 1));
				placeSnakeElement(startingField, 1);
				jungle.getFields()[startingField.getRow()][startingField.getColumn()].setCharacter(null);
			}
			snakeType.setAmount(snakeType.getAmount() + 1);
			currentType = previousSnakeType;
		}
	}

	private void placeSnakeElement(Field previousField, int elementIndex) {
		/*
		 * Ist die Schlange vollstaendig verteilt, wird versucht die naechste Schlange
		 * zu verteilen.
		 */
		if (elementIndex == currentType.getSigns().length()) {
			placeSnake();
			return;
		}

		/*
		 * Es werden die Nachbarn des letztes Schlangengliedes aufgelistet und es wird
		 * nach und nach versucht die Schlange zu verteilen. Gelingt dies nicht werden
		 * die Felder im Dschungel wieder freigegeben und es wird in 'platziereSchlange'
		 * zurueckgekehrt.
		 */
		List<Field> validNeighborFields = createValidNeighbors(previousField);
		for (Field neighborField : validNeighborFields) {
			jungle.getFields()[neighborField.getRow()][neighborField.getColumn()]
					.setCharacter(currentType.getSigns().substring(elementIndex, elementIndex + 1));
			placeSnakeElement(neighborField, elementIndex + 1);
			if (System.nanoTime() - currentTime > 10000000000.0 || full == true) {
				if (full == false) {
					this.failed = true;
					return;
				} else {
					return;
				}
			}
			jungle.getFields()[neighborField.getRow()][neighborField.getColumn()].setCharacter(null);
		}
	}

	/**
	 * Die leeren Felder eines Dschungel werden mit Zeichen ausgefuellt, die
	 * zufaellig aus der Zeichenmenge des Dschungels ausgewaehlt werden.
	 * 
	 * @param jungle Der Dschungel, in dem die leeren Felder ausgefuellt werden
	 *                  sollen.
	 */
	private void fillRestOfJungle(Jungle jungle) {
		for (int i = 0; i < jungle.getRows(); i++) {
			for (int j = 0; j < jungle.getColumns(); j++) {
				if (jungle.getFields()[i][j].getCharacter() == null) {
					if (jungle.getSigns().equals("")) {
					} else {
						int rand = new Random().nextInt(0, jungle.getSigns().length());
						jungle.getFields()[i][j].setCharacter(jungle.getSigns().substring(rand, rand + 1));
					}
				}
			}
		}
	}

	private List<SnakeType> createValidSnakeTypes() {
		/*
		 * Es werden alle Schlangenarten des Modelles zurueckgegeben, deren Anzahl
		 * groesser als 0 ist, von denen also noch mindestens eine verteilt werden muss.
		 * Schliesslich werden die Schlangenarten durchgemischt, um eine zufaellige
		 * Verteilung zu garantieren.
		 */
		List<SnakeType> validSnakeTypes = new ArrayList<SnakeType>();
		for (SnakeType snakeType : model.getSnakeTypes()) {
			if (snakeType.getAmount() > 0) {
				validSnakeTypes.add(snakeType);
			}
		}
		Collections.shuffle(validSnakeTypes);
		return validSnakeTypes;
	}

	private List<Field> createValidNeighbors(Field previousField) {
		/*
		 * Es werden alle Nachbarn des letzten Feldes bestimmt, wobei die Anordnung der
		 * Nachbarn im Dschungel immer auf der Nachbarschaftsstruktur der aktuellen
		 * Schlangenart basiert. Schliesslich werden die Felder durchgemischt, um eine
		 * zufaellige Verteilung zu garantieren.
		 */
		List<Field> validNeighborFields = new ArrayList<Field>();
		for (Field neighborField : currentType.getStructure().getNeighbors(jungle, previousField)) {
			if (neighborField.getCharacter() == null) {
				validNeighborFields.add(neighborField);
			}
		}
		Collections.shuffle(validNeighborFields);
		return validNeighborFields;
	}

	private List<Field> createValidStartingFields() {
		/*
		 * Es werden alle moeglichen Startfelder, also alle Felder, die noch kein
		 * Zeichen haben, aufgelistet und durchgemischt, um eine zufaellige Verteilung
		 * zu garantieren.
		 */
		List<Field> validStartingFields = new ArrayList<Field>();
		for (int i = 0; i < jungle.getFields().length; i++) {
			for (int j = 0; j < jungle.getFields()[0].length; j++) {
				if (jungle.getFields()[i][j].getCharacter() == null) {
					validStartingFields.add(jungle.getFields()[i][j]);
				}
			}
		}
		Collections.shuffle(validStartingFields);
		return validStartingFields;
	}

	private int minimumNumberUsedFields() {
		int amount = 0;
		for (SnakeType snakeType : snakeTypes) {
			amount += snakeType.getAmount() * snakeType.getSigns().length();
		}
		return amount;
	}

	private int numberOfTypesTotal() {
		int amount = 0;
		for (SnakeType snakeType : snakeTypes) {
			amount += snakeType.getAmount();
		}
		return amount;
	}

	/**
	 * Es wird der aktuelle Wert der privaten Variable <code>fehlgeschlagen</code>
	 * zurueckgegeben. Es ist hierdurch moeglich Auskunft darueber zu geben, ob ein
	 * Dschungel innerhalb einer bestimmten Zeit erzeugt werden konnte. Ausserdem
	 * kann damit direkt ein zweiter Versuch gestartet werden, denn oftmals liegt
	 * eine Zeitueberschreitung nur an einer ungluecklichen Verteilung der Schlangen
	 * im Dschungel.
	 * 
	 * @return Wert der Variablen <code>fehlgeschlagen</code>.
	 */
	public boolean getFailed() {
		return failed;
	}

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell im Dschungelgenerator
	 * befindet. Dies ist vor allem fuer kuenftige Aenderungen und auch fuer damit
	 * einhergehende Tests gedacht.
	 * 
	 * @return Wert der Variablen <code>modell</code>.
	 */
	public IModel getModel() {
		return model;
	}

	/**
	 * Es kann das Modell des Dschungelgenerators gesetzt werden. So ist es zum
	 * Beispiel moeglich ein Modell zu uebergeben, auch wenn zunaechst der
	 * parameterlose Konstruktor genutzt wurde. Es ist auch im Allgemeinen moeglich
	 * das Modell nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param model Das Modell, das uebergeben werden soll.
	 * @throws IllegalArgumentException Eine Ausnahme wird geworfen, wenn ein
	 *                                  unpassendes Modell uebergeben wird.
	 */
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getJungle().getRows() * model.getJungle().getColumns() < minimumNumberUsedFields()) {
			System.out
					.println("Das Modell, dass dem Dschungelgenerator uebergeben werden soll enthaelt mehr\nSchlangen "
							+ "als in den Dschungel passen. Es kann kein Dschungel generiert werden.");
			System.out.println();
			throw new IllegalArgumentException("Dem Dschungelgenerator kann kein Modell uebergeben werden, dass"
					+ " mehr Schlangen hat, als in den Dschungel passen.");
		}
		this.model = model;
	}

	/**
	 * Es wird nur der Dschungel des Modelles zurueckgegeben, das sich aktuell im
	 * Dschungelgenerator befindet. Dies ist fuer zukuenftige Programmerweiterung
	 * und beziehungsweise oder Tests gedacht, denn so ist der Zugriff auf diesen
	 * Teil des Programmes gewaehrleistet.
	 * 
	 * @return Der Wert der Variablen <code>dschungel</code>.
	 */
	public Jungle getJungle() {
		return jungle;
	}

	/**
	 * Es kann hierdurch nur der Dschungel geaendert werden, sollte dies gewollt
	 * sein. Dies ist fuer zukuenftige Programmerweiterung und beziehungsweise oder
	 * Tests gedacht
	 * 
	 * @param jungle Der Dschungel, der dem Modell des Dschungelgenerators
	 *                  uebergeben werden soll.
	 */
	public void setJungle(Jungle jungle) {
		this.jungle = jungle;
	}

	/**
	 * Es wird die Anzahl der Felder uebergeben, die die Schlangen in dem Dschungel
	 * ueberdecken werden, wenn sie verteilt sind. Es werden hierbei die Laenge und
	 * Anzahl der einzelnen Schlangenarten mit einbezogen.
	 * 
	 * @return Die Anzahl der Felder, die die Schlangenarten des Modelles nutzen
	 *         werden.
	 */
	public int getMinimumSnakes() {
		return minimumSnakes;
	}

	/**
	 * Es wird der neue Dschungel zurueckgegeben. Dadurch ist ein Zugriff auf den
	 * Dschungel moeglich, den der Dschungelgenerator erzeugt hat. Es ist zu
	 * beachten, dass der neue Dschungel unvollstaendig ist, wenn er zurueckgeben
	 * wird bevor er befuellt worden ist.
	 * 
	 * @return Der Wert der Variable <code>neuerDschungel</code>.
	 */
	public Jungle getNewJungle() {
		return newJungle;
	}

	/**
	 * Es wird eine Liste mit den Schlangenarten zurueckgegeben, die im Modell des
	 * Dschungelgenerator stehen. Dies ist fuer zukuenftige Programmerweiterung und
	 * beziehungsweise oder Tests gedacht.
	 * 
	 * @return Der Wert der Variable <code>schlangenarten</code>.
	 */
	public List<SnakeType> getSnakeTypes() {
		return snakeTypes;
	}

	/**
	 * Es ist moeglich die im Modell des Dschungelgenerators vorhandenen
	 * Schlangenarten zu aendern und beziehungsweise oder zu erweitern. Dies ist
	 * fuer zukuenftige Programmerweiterung und beziehungsweise oder Tests gedacht.
	 * 
	 * @param snakeTypes Die Liste mit Schlangenarten, die dem Modell des
	 *                       Dschungelgenerators uebergeben werden soll.
	 */
	public void setSnakeTypes(List<SnakeType> snakeTypes) {
		this.snakeTypes = snakeTypes;
	}
}