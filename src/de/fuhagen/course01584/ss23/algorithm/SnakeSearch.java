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
	private IModel modell;
	private Solution aktLoesung;
	private Solution loesung;
	private SolutionEvaluator bewerter;
	private Long aktZeit;
	private Double modellZeit;
	private ISnakeSearchUtil funktionen;

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
		if (modell.getDschungel().anzahlBelegterFelder() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Dem Konstruktor von SchlangenSuche muss ein Modell uebergeben werden, dass"
							+ " Felder im Dschungel hat.");
		}
		this.modell = modell;
		this.aktLoesung = new Solution();
		this.loesung = new Solution();
		this.bewerter = new SolutionEvaluator();
		this.funktionen = new SnakeSearchUtil(modell);
		this.modellZeit = modell.berechneZeitInNanosekunden(modell.getZeit()[0]);
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SnakeSearch() {
		super();
	}

	@Override
	public void sucheSchlangen() {
		this.aktZeit = System.nanoTime();
		sucheSchlange();
	}

	private void sucheSchlange() {
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
		if (bewerter.bewerteLoesung(aktLoesung) > bewerter.bewerteLoesung(loesung)) {
			loesung = new Solution();
			for (Snake schlange : aktLoesung.getSchlangen()) {
				Snake neueSchlange = new Snake(schlange.getArt());
				for (SnakeElement glied : schlange.getGlieder()) {
					neueSchlange.addGlied(glied);
				}
				loesung.addSchlange(neueSchlange);
			}
		}

		/*
		 * Es wird zuerst ueber die Schlangenarten iteriert, um die Priorisierung ueber
		 * Schlangenarten zu vereinfachen.
		 */
		List<SnakeType> schlangenarten = funktionen.erzeugeZulaessigeSchlangenarten();
		for (SnakeType schlangenart : schlangenarten) {
			List<Field> startfelder = funktionen.erzeugeZulaessigeStartfelder(schlangenart);
			for (Field startfeld : startfelder) {
				if (System.nanoTime() - aktZeit > modellZeit) {
					return;
				}
				// Es wird eine neue Schlange erstellt mit entsprechendem Schlangenkopf.
				startfeld.setVerwendbarkeit(startfeld.getVerwendbarkeit() - 1);
				Snake neueSchlange = new Snake(schlangenart);
				SnakeElement schlangenkopf = new SnakeElement(startfeld);
				neueSchlange.addGlied(schlangenkopf);
				aktLoesung.addSchlange(neueSchlange);

				// Es wird versucht die weiteren Schlangenglieder zu suchen.
				sucheSchlangenglied(schlangenkopf, neueSchlange);

				/*
				 * Die Schlange wird und der Schlangenkopf werden wieder entfernt und die
				 * genutzten Felder werden wieder freigegeben.
				 */
				startfeld.setVerwendbarkeit(startfeld.getVerwendbarkeit() + 1);
				neueSchlange.entferneLetztesGlied();
				aktLoesung.entferneLetzteSchlange();
			}
		}
	}

	private void sucheSchlangenglied(SnakeElement vorherigesGlied, Snake aktSchlange) {
		/*
		 * Eine Methode, die von der Methode 'sucheSchlange' und von sich selbst
		 * aufgerufen wird. Es wird immer das naechste Glied der aktuellen Schlange
		 * gesucht. So lange bis das entweder nicht mehr moeglich ist, weil kein
		 * passendes Folgeglied gefunden wird oder bis eine Loesung gespeichert oder bis
		 * eine neue Kombination probiert wird. Ist eine Schlange vollstaendig, wird
		 * hier in die Methode 'sucheSchlange' gesprungen, um nach der naechsten
		 * Schlange zu suchen.
		 */
		if (vorherigesGlied.getFeld().getZeichen().equals(aktSchlange.letztesGlied())
				&& aktSchlange.getGlieder().size() == aktSchlange.getArt().getZeichenkette().length()) {
			sucheSchlange();
			return;
		}
		/*
		 * Es werden zulaessige Nachbarn aufgelistet und nach und nach ueber diese
		 * iteriert.
		 */
		List<Field> nachbarn = funktionen.erzeugeZulaessigeNachbarn(vorherigesGlied, aktSchlange);
		for (Field nachbar : nachbarn) {
			if (System.nanoTime() - aktZeit > modellZeit) {
				return;
			}

			// Fuege der aktuellen Schlange den naechsten Nachbar hinzu.
			nachbar.setVerwendbarkeit(nachbar.getVerwendbarkeit() - 1);
			SnakeElement neuesGlied = new SnakeElement(nachbar);
			aktSchlange.addGlied(neuesGlied);

			// Suche nach dem naechsten Schlangenglied.
			sucheSchlangenglied(neuesGlied, aktSchlange);

			/*
			 * Entferne die einzelnen Schlangenglieder wieder und gebe die genutzten Felder
			 * wieder frei.
			 */
			nachbar.setVerwendbarkeit(nachbar.getVerwendbarkeit() + 1);
			aktSchlange.entferneLetztesGlied();
		}
	}

	@Override
	public IModel getModell() {
		return modell;
	}

	@Override
	public void setModell(IModel modell) throws IllegalArgumentException {
		if (modell.getDschungel().anzahlBelegterFelder() == 0) {
			System.out.println("Das Modell, dass der Schlangensuche uebergeben werden soll, hat einen Dschungel ohne "
					+ "Felder. Es\nmuss ein Modell uebergeben werden, dass "
					+ "Feldern besitzt. Sind keine Felder vorhanden, so koennen\ndiese mit dem "
					+ "Dschungelgenerator erstellt werden.");
			System.out.println();
			throw new IllegalArgumentException(
					"Der SchlangenSuche muss ein Modell uebergeben werden, dass" + " Felder im Dschungel hat.");
		}
		this.modell = modell;
	}

	@Override
	public Solution getLoesung() {
		return loesung;
	}

	@Override
	public Long getAktZeit() {
		return aktZeit;
	}

	@Override
	public ISnakeSearchUtil getFunktionen() {
		return funktionen;
	}

	@Override
	public void setFunktionen(ISnakeSearchUtil funktionen) {
		this.funktionen = funktionen;
	}
}