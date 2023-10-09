package de.fuhagen.course01584.ss23.ioprocessing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import de.fuhagen.course01584.ss23.ioprocessing.IReader;
import de.fuhagen.course01584.ss23.ioprocessing.ReaderXML;

/**
 * In der Klasse LeserXMLTest werden die Methoden der Klasse LeserXML getestet.
 * Dazu wurden vier seperate Klassen erstellt, die jeweils einen Anwendungsfall
 * abdecken. Hinzu kommt der Fall, dass unter dem angegebenen Dateipfad keine
 * Datei zu finden ist, denn auch dies sollte im Hauptprogramm entsprechend
 * abgedeckt werden. In jeder der groesseren Testklassen dieser Klasse wird
 * zunaechst ein @BeforeAll genutzt, um gewisse Zustaende zu initialisieren. Bei
 * den kleineren Methoden wurden Beschreibungen in Form von Kommentaren
 * weggelassen und die Variablen- und Methodennamen moeglichst eindeutig
 * gewaehlt.
 * 
 * @author Philip Redecker
 */
class ReaderXMLTest {

	private static IReader xmlLeser;

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Lesen_einer_nicht_vorhandenen_Datei {
		@DisplayName("Einfacher Test fuer die Situation in der eine angegebene Datei nicht vorhanden ist.")
		@Test
		void testeDateiVorhanden() {
			assertDoesNotThrow(() -> {
				try {
					new ReaderXML().leseDatei("res/diese_xml_existiert_nicht.xml");
				} catch (Exception e) {
					System.out.println("Fehler beim Einlesen!");
				}
			}, "\nDie angegebene Datei existiert nicht oder sie existiert nicht unter dem angegebenen "
					+ "Verzeichnis aber es wird keine Ausnahme erzeugt.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Lesen_einer_Loesungsdatei {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Lese die entsprechende Datei ein
			xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p1_loesung.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.001205252 };
			assertArrayEquals(zeitInDatei, xmlLeser.getUebergebenesModell().getZeit(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getZeilen(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getUebergebenesModell().getDschungel().getSpalten(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getUebergebenesModell().getDschungel().getZeichenmenge(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel' und dessen Attribut 'felder'.")
		@Test
		void testeDschungelFelder() {

			// Ueberpruefe das Feld an Position[0][0]
			assertEquals("F0", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getId(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getZeile(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getSpalte(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getPunkte(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("F", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getZeichen(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][1]
			assertEquals("F1", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getId(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getZeile(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getSpalte(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getPunkte(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("E", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getZeichen(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][2]
			assertEquals("F2", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getId(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getZeile(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getSpalte(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getPunkte(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("R", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getZeichen(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][3]
			assertEquals("F3", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getId(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getZeile(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getSpalte(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getPunkte(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getZeichen(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][0]
			assertEquals("F4", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getId(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getZeile(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getSpalte(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getPunkte(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("X", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getZeichen(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][1]
			assertEquals("F5", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getId(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getZeile(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getSpalte(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getPunkte(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("I", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getZeichen(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][2]
			assertEquals("F6", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getId(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getZeile(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getSpalte(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getPunkte(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getZeichen(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][3]
			assertEquals("F7", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getId(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getZeile(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getSpalte(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getPunkte(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("U", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getZeichen(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getStruktur().getArt(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getAnzahl(),
					"\nDie eingelesene Anzahl entspricht nicht der Anzahl aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangen'.")
		@Test
		void testeSchlangen() {
			assertEquals("F0",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(0).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F1",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(1).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F2",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(2).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F3",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(3).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F7",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(4).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F6",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(5).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F5",
					xmlLeser.getUebergebenesModell().getLoesung().getSchlangen().get(0).getGlieder().get(6).getFeld()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Lesen_einer_Probleminstanzdatei {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Lese die entsprechende Datei ein
			xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p1_probleminstanz.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.0 };
			assertArrayEquals(zeitInDatei, xmlLeser.getUebergebenesModell().getZeit(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getZeilen(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getUebergebenesModell().getDschungel().getSpalten(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getUebergebenesModell().getDschungel().getZeichenmenge(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel' und dessen Attribut 'felder'.")
		@Test
		void testeDschungelFelder() {

			// Ueberpruefe das Feld an Position[0][0]
			assertEquals("F0", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getId(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getZeile(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getSpalte(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getPunkte(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("F", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][0].getZeichen(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][1]
			assertEquals("F1", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getId(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getZeile(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getSpalte(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getPunkte(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("E", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][1].getZeichen(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][2]
			assertEquals("F2", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getId(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getZeile(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getSpalte(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getPunkte(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("R", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][2].getZeichen(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][3]
			assertEquals("F3", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getId(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getZeile(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getSpalte(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getPunkte(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[0][3].getZeichen(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][0]
			assertEquals("F4", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getId(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getZeile(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getSpalte(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getPunkte(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("X", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][0].getZeichen(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][1]
			assertEquals("F5", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getId(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getZeile(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getSpalte(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getPunkte(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("I", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][1].getZeichen(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][2]
			assertEquals("F6", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getId(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getZeile(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getSpalte(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getPunkte(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][2].getZeichen(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][3]
			assertEquals("F7", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getId(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getZeile(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getSpalte(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getVerwendbarkeit(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getPunkte(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("U", xmlLeser.getUebergebenesModell().getDschungel().getFelder()[1][3].getZeichen(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getStruktur().getArt(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getAnzahl(),
					"\nDie eingelesene Anzahl entspricht nicht der Anzahl aus der einzulesenden Datei.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Lesen_einer_unvollstaendigen_Datei {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Lese die entsprechende Datei ein
			xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p1_unvollstaendig.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.0 };
			assertArrayEquals(zeitInDatei, xmlLeser.getUebergebenesModell().getZeit(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getUebergebenesModell().getDschungel().getZeilen(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getUebergebenesModell().getDschungel().getSpalten(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getUebergebenesModell().getDschungel().getZeichenmenge(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getStruktur().getArt(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getUebergebenesModell().getSchlangenarten().get(0).getAnzahl(),
					"\nDie eingelesene Anzahl entspricht nicht der Anzahl aus der einzulesenden Datei.");
		}
	}
}
