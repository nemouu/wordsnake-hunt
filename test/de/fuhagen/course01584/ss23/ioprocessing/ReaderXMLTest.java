package de.fuhagen.course01584.ss23.ioprocessing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

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
					new ReaderXML().readFile("res/diese_xml_existiert_nicht.xml");
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
			xmlLeser.readFile("res/sj_p1_loesung.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.001205252 };
			assertArrayEquals(zeitInDatei, xmlLeser.getTransferredModel().getTime(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getRows(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getTransferredModel().getJungle().getColumns(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getTransferredModel().getJungle().getSigns(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel' und dessen Attribut 'felder'.")
		@Test
		void testeDschungelFelder() {

			// Ueberpruefe das Feld an Position[0][0]
			assertEquals("F0", xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getId(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getRow(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getColumn(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getUsage(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getPoints(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("F", xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getCharacter(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][1]
			assertEquals("F1", xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getId(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getRow(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getColumn(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getUsage(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getPoints(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("E", xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getCharacter(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][2]
			assertEquals("F2", xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getId(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getRow(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getColumn(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getUsage(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getPoints(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("R", xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getCharacter(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][3]
			assertEquals("F3", xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getId(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getRow(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getColumn(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getUsage(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getPoints(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getCharacter(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][0]
			assertEquals("F4", xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getId(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getRow(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getColumn(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getUsage(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getPoints(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("X", xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getCharacter(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][1]
			assertEquals("F5", xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getId(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getRow(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getColumn(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getUsage(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getPoints(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("I", xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getCharacter(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][2]
			assertEquals("F6", xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getId(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getRow(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getColumn(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getUsage(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getPoints(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getCharacter(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][3]
			assertEquals("F7", xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getId(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getRow(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getColumn(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getUsage(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getPoints(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("U", xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getCharacter(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getStruktur().getType(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getAnzahl(),
					"\nDie eingelesene Anzahl entspricht nicht der Anzahl aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangen'.")
		@Test
		void testeSchlangen() {
			assertEquals("F0",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(0).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F1",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(1).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F2",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(2).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F3",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(3).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F7",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(4).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F6",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(5).getField()
							.getId(),
					"\nDie ID der eingelesenen Schlange entspricht nicht der in der einzulesenden Datei.");
			assertEquals("F5",
					xmlLeser.getTransferredModel().getSolution().getSchlangen().get(0).getElements().get(6).getField()
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
			xmlLeser.readFile("res/sj_p1_probleminstanz.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.0 };
			assertArrayEquals(zeitInDatei, xmlLeser.getTransferredModel().getTime(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getRows(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getTransferredModel().getJungle().getColumns(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getTransferredModel().getJungle().getSigns(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel' und dessen Attribut 'felder'.")
		@Test
		void testeDschungelFelder() {

			// Ueberpruefe das Feld an Position[0][0]
			assertEquals("F0", xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getId(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getRow(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getColumn(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getUsage(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getPoints(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("F", xmlLeser.getTransferredModel().getJungle().getFields()[0][0].getCharacter(),
					"\nEin Feld an der Stelle [0][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][1]
			assertEquals("F1", xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getId(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getRow(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getColumn(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getUsage(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getPoints(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("E", xmlLeser.getTransferredModel().getJungle().getFields()[0][1].getCharacter(),
					"\nEin Feld an der Stelle [0][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][2]
			assertEquals("F2", xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getId(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getRow(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getColumn(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getUsage(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getPoints(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("R", xmlLeser.getTransferredModel().getJungle().getFields()[0][2].getCharacter(),
					"\nEin Feld an der Stelle [0][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[0][3]
			assertEquals("F3", xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getId(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getRow(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getColumn(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getUsage(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getPoints(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getTransferredModel().getJungle().getFields()[0][3].getCharacter(),
					"\nEin Feld an der Stelle [0][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][0]
			assertEquals("F4", xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getId(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getRow(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(0, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getColumn(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getUsage(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getPoints(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("X", xmlLeser.getTransferredModel().getJungle().getFields()[1][0].getCharacter(),
					"\nEin Feld an der Stelle [1][0] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][1]
			assertEquals("F5", xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getId(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getRow(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getColumn(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getUsage(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getPoints(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("I", xmlLeser.getTransferredModel().getJungle().getFields()[1][1].getCharacter(),
					"\nEin Feld an der Stelle [1][1] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][2]
			assertEquals("F6", xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getId(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getRow(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getColumn(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getUsage(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getPoints(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("N", xmlLeser.getTransferredModel().getJungle().getFields()[1][2].getCharacter(),
					"\nEin Feld an der Stelle [1][2] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");

			// Ueberpruefe das Feld an Position[1][3]
			assertEquals("F7", xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getId(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getRow(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(3, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getColumn(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getUsage(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getPoints(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
			assertEquals("U", xmlLeser.getTransferredModel().getJungle().getFields()[1][3].getCharacter(),
					"\nEin Feld an der Stelle [1][3] des Dschungels entspricht nicht dem entsprechenden Feld aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getStruktur().getType(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getAnzahl(),
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
			xmlLeser.readFile("res/sj_p1_unvollstaendig.xml");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'zeit'.")
		@Test
		void testeZeit() {
			Double[] zeitInDatei = { 60.0, 0.0 };
			assertArrayEquals(zeitInDatei, xmlLeser.getTransferredModel().getTime(),
					"\nDie eingelesene Zeit entspricht nicht der Zeit aus der XML-Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'dschungel'.")
		@Test
		void testeDschungel() {
			assertEquals(2, xmlLeser.getTransferredModel().getJungle().getRows(),
					"\nDie eingelesene Zahl fuer die Spalten des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals(4, xmlLeser.getTransferredModel().getJungle().getColumns(),
					"\nDie eingelesene Zahl fuer die Zeilen des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					xmlLeser.getTransferredModel().getJungle().getSigns(),
					"\nDie eingelesene Zeichenmenge des Dschungels entspricht nicht dem Wert aus der einzulesenden Datei.");
		}

		@DisplayName("Einfacher Test fuer das Attribut 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			assertEquals("A0", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nDie eingelesene ID der Schlange entspricht nicht der ID aus der einzulesenden Datei.");
			assertEquals("Distanz", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getStruktur().getType(),
					"\nDie eingelesene Schlangenart entspricht nicht der Schlangenart aus der einzulesenden Datei.");
			assertEquals("FERNUNI", xmlLeser.getTransferredModel().getSnakeTypes().get(0).getZeichenkette(),
					"\nDie eingelesende Zeichenkette entspricht nicht der Zeichenkette aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getPunkte(),
					"\nDie eingelesenen Punkte entsprechen nicht den Punkten aus der einzulesenden Datei.");
			assertEquals(1, xmlLeser.getTransferredModel().getSnakeTypes().get(0).getAnzahl(),
					"\nDie eingelesene Anzahl entspricht nicht der Anzahl aus der einzulesenden Datei.");
		}
	}
}
