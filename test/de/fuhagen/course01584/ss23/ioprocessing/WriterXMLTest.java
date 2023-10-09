package de.fuhagen.course01584.ss23.ioprocessing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.junit.jupiter.api.*;

import de.fuhagen.course01584.ss23.model.*;

/**
 * In der Klasse SchreiberXMLTest werden die Methoden der Klasse SchreiberXML
 * getestet. Dazu wurden vier seperate Klassen erstellt, die jeweils einen
 * Anwendungsfall abdecken. Hinzu kommt der Fall, dass das uebergebene Modell
 * leer ist, denn auch dies sollte im Hauptprogramm entsprechend abgedeckt
 * werden. In jeder der groesseren Testklassen dieser Klasse wird zunaechst
 * ein @BeforeAll genutzt, um gewisse Zustaende zu initialisieren.
 * 
 * @author Philip Redecker
 */
class WriterXMLTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfacher_Test_fuer_das_Schreiben_eines_leeren_Modelles {

		@DisplayName("Einfacher Test fuer die Situation in der eine das uebergebene Modell leer ist.")
		@Test
		void testeModellLeer() {
			assertThrows(Exception.class,
					() -> new WriterXML(new ProblemModel())
							.schreibeInDatei("res/das_modell_ist_unvollstaendig.xml"),
					() -> "\nDas uebergebene Modell existiert nicht aber es wird keine Ausnahme erzeugt.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Schreiben_einer_Loesungsdatei {
		private static IModel bspModell;
		private static WriterXML schreiberXML;
		private static List<Element> listeFuerTest;

		/*
		 * Hier wird zunaechst ein Modell 'von Hand' befuellt, sodass dann getestet
		 * werden kann, ob die Methoden der Klasse SchreiberXML die richtigen Daten in
		 * ein XML Dokument uebertragen. Zu beachten ist, dass die angegebenen Schlangen
		 * keine tatsaechlichen Loesungen zu diesem Dschungel sind. Alles wird hier nur
		 * als Beispiel eingefuegt.Bei den kleineren Methoden wurden Beschreibungen in
		 * Form von Kommentaren weggelassen und die Variablen- und Methodennamen
		 * moeglichst eindeutig gewaehlt.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Erstelle ein Dschungelbeispiel
			Jungle bspDschungel = new Jungle(2, 3, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a" + (i + j + j));
					bspDschungel.getFelder()[i][j].setPunkte(1);
					bspDschungel.getFelder()[i][j].setVerwendbarkeit(1);
					bspDschungel.getFelder()[i][j].setZeile(i);
					bspDschungel.getFelder()[i][j].setSpalte(j);
					bspDschungel.getFelder()[i][j].setId("F" + (bspDschungel.getSpalten() * i + j));
				}
			}

			// Erstellte ein Nachbarschaftsbeispiel
			INeighborhood bspNachbarschaft = new DistanceNeighborhood(2);

			// Erstellte eine Liste von moeglichen Schlangenarten
			SnakeType[] bspArten = { new SnakeType("A0", bspNachbarschaft, "abcdefghij", 1, 1),
					new SnakeType("A1", bspNachbarschaft, "hjgzhi", 21, 31) };

			// Erstelle eine Beispielschlange mit zwei Beispielgliedern
			SnakeElement erstesGlied = new SnakeElement(bspDschungel.getFelder()[0][0]);
			SnakeElement zweitesGlied = new SnakeElement(bspDschungel.getFelder()[0][1]);
			List<SnakeElement> glieder = new ArrayList<SnakeElement>();
			glieder.add(erstesGlied);
			glieder.add(zweitesGlied);

			// Erstelle eine Beispielloesung und fuege ihr die Daten von oben hinzu
			Solution bspLoesung = new Solution();
			bspLoesung.addSchlange(new Snake(bspArten[0], glieder));
			Double[] bspZeit = { 1.0, 2.0 };

			// Fuege dem bspModell alle erstellten Daten hinzu
			bspModell = new ProblemModel(bspDschungel, bspLoesung, bspZeit);
			bspModell.addSchlangenart(new SnakeType("A0", bspNachbarschaft, "abcdefghij", 1, 1));
			bspModell.addSchlangenart(new SnakeType("A1", bspNachbarschaft, "hjgzhi", 21, 31));

			// Schreibe nun diese Daten in ein Document
			schreiberXML = new WriterXML(bspModell);
			schreiberXML.schreibeInDatei("res/test_fuer_schreiben_in_loesungsdatei.xml");
			listeFuerTest = schreiberXML.getDokument().getRootElement().getChildren();
		}

		@DisplayName("Einfacher Test fuer das Wurzelelement.")
		@Test
		void testeWurzel() {
			assertEquals("Schlangenjagd", schreiberXML.getDokument().getRootElement().getName(),
					"\nDas eingelesene Wurzelelement entspricht nicht dem Inhalt des Modelles");
		}

		@DisplayName("Einfacher Test fuer das Element 'zeit'.")
		@Test
		void testeZeit() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Zeit") {
					assertEquals(1.0, Double.parseDouble(element.getChildText("Vorgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Vorgabe) stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Abgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Abgabe) stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'dschungel'.")
		@Test
		void testeDschungel() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Dschungel") {

					// Ueberpruefe die Art des Dschungels im Modell
					assertEquals(2, Integer.parseInt(element.getAttributeValue("zeilen")),
							"\nDer eingelesene Wert fuer das Attribut 'zeilen' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("spalten")),
							"\nDer eingelesene Wert fuer das Attribut 'spalten' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("asdertg", element.getAttributeValue("zeichen"),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][0] des Dschungels im Modell
					assertEquals("F0", element.getChildren().get(0).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a0", element.getChildren().get(0).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][1] des Dschungels im Modell
					assertEquals("F1", element.getChildren().get(1).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(1).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a2", element.getChildren().get(1).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][2] des Dschungels im Modell
					assertEquals("F2", element.getChildren().get(2).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(2).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2", element.getChildren().get(2).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a4", element.getChildren().get(2).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][0] des Dschungels im Modell
					assertEquals("F3", element.getChildren().get(3).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(3).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a1", element.getChildren().get(3).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][1] des Dschungels im Modell
					assertEquals("F4", element.getChildren().get(4).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a3", element.getChildren().get(4).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][2] des Dschungels im Modell
					assertEquals("F5", element.getChildren().get(5).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2", element.getChildren().get(5).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a5", element.getChildren().get(5).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Schlangenart") {

					// Ueberpruefe erste Schlangenart des Modelles
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(0).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe zweite Schlangenart des Modelles
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(1).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'schlangen'.")
		@Test
		void testeSchlangen() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Schlangen") {

					// Ueberpruefe die gefundenen Schlangen des Dschungels im Modell
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("art"),
							"\nDer eingelesene Wert fuer das Attribut 'art' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("F0", element.getChildren().get(0).getChildren().get(0).getAttributeValue("feld"),
							"\nEin eingelesener Wert fuer das Attribut 'feld' dieser Schlange stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("F1", element.getChildren().get(0).getChildren().get(1).getAttributeValue("feld"),
							"\nEin eingelesener Wert fuer das Attribut 'feld' dieser Schlange stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Schreiben_einer_Probleminstanzdatei {
		private static IModel bspModell;
		private static WriterXML schreiberXML;
		private static List<Element> listeFuerTest;

		/*
		 * Hier wird zunaechst ein Modell 'von Hand' befuellt, sodass dann getestet
		 * werden kann, ob die Methoden der Klasse SchreiberXML die richtigen Daten in
		 * ein XML Dokument uebertragen. Zu beachten ist, dass die angegebenen Schlangen
		 * keine tatsaechlichen Loesungen zu diesem Dschungel sind. Alles wird hier nur
		 * als Beispiel eingefuegt.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Erstelle ein Dschungelbeispiel
			Jungle bspDschungel = new Jungle(2, 3, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a" + (i + j + j));
					bspDschungel.getFelder()[i][j].setPunkte(1);
					bspDschungel.getFelder()[i][j].setVerwendbarkeit(1);
					bspDschungel.getFelder()[i][j].setZeile(i);
					bspDschungel.getFelder()[i][j].setSpalte(j);
					bspDschungel.getFelder()[i][j].setId("F" + (i + j + j));
				}
			}

			// Erstellte ein Nachbarschaftsbeispiel
			INeighborhood bspNachbarschaft = new DistanceNeighborhood(2);

			// Erstelle Beispielzeit
			Double[] bspZeit = { 1.0, 2.0 };

			// Fuege dem bspModell alle erstellten Daten hinzu
			bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.addSchlangenart(new SnakeType("A0", bspNachbarschaft, "abcdefghij", 1, 1));
			bspModell.addSchlangenart(new SnakeType("A1", bspNachbarschaft, "hjgzhi", 21, 31));

			// Schreibe nun diese Daten in ein Document
			schreiberXML = new WriterXML(bspModell);
			schreiberXML.schreibeInDatei("res/test_fuer_schreiben_in_loesungsdatei.xml");
			listeFuerTest = schreiberXML.getDokument().getRootElement().getChildren();
		}

		@DisplayName("Einfacher Test fuer das Wurzelelement.")
		@Test
		void testeWurzel() {
			assertEquals("Schlangenjagd", schreiberXML.getDokument().getRootElement().getName(),
					"\nDas eingelesene Wurzelelement entspricht nicht dem Inhalt des Modelles");
		}

		@DisplayName("Einfacher Test fuer das Element 'zeit'.")
		@Test
		void testeZeit() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Zeit") {
					assertEquals(1.0, Double.parseDouble(element.getChildText("Vorgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Vorgabe) stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Abgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Abgabe) stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'dschungel'.")
		@Test
		void testeDschungel() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Dschungel") {

					// Ueberpruefe die Art des Dschungels im Modell
					assertEquals(2, Integer.parseInt(element.getAttributeValue("zeilen")),
							"\nDer eingelesene Wert fuer das Attribut 'zeilen' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("spalten")),
							"\nDer eingelesene Wert fuer das Attribut 'spalten' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("asdertg", element.getAttributeValue("zeichen"),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][0] des Dschungels im Modell
					assertEquals("F0", element.getChildren().get(0).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a0", element.getChildren().get(0).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][0] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][1] des Dschungels im Modell
					assertEquals("F2", element.getChildren().get(1).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(1).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a2", element.getChildren().get(1).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][1] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[0][2] des Dschungels im Modell
					assertEquals("F4", element.getChildren().get(2).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(2).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2", element.getChildren().get(2).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a4", element.getChildren().get(2).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[0][2] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][0] des Dschungels im Modell
					assertEquals("F1", element.getChildren().get(3).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("0", element.getChildren().get(3).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a1", element.getChildren().get(3).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][0] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][1] des Dschungels im Modell
					assertEquals("F3", element.getChildren().get(4).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a3", element.getChildren().get(4).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][1] stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe das Feld an Position[1][2] des Dschungels im Modell
					assertEquals("F5", element.getChildren().get(5).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("zeile"),
							"\nDer eingelesene Wert fuer das Attribut 'zeile' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2", element.getChildren().get(5).getAttributeValue("spalte"),
							"\nDer eingelesene Wert fuer das Attribut 'spalte' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("verwendbarkeit"),
							"\nDer eingelesene Wert fuer das Attribut 'verwendbarkeit' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("a5", element.getChildren().get(5).getText(),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' an der Stelle[1][2] stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Schlangenart") {

					// Ueberpruefe erste Schlangenart des Modelles
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(0).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe zweite Schlangenart des Modelles
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(1).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_das_Schreiben_einer_unvollstaendigen_Datei {
		private static IModel bspModell;
		private static WriterXML schreiberXML;
		private static List<Element> listeFuerTest;

		/*
		 * Hier wird zunaechst ein Modell 'von Hand' befuellt, sodass dann getestet
		 * werden kann, ob die Methoden der Klasse SchreiberXML die richtigen Daten in
		 * ein XML Dokument uebertragen. Zu beachten ist, dass die angegebenen Schlangen
		 * keine tatsaechlichen Loesungen zu diesem Dschungel sind. Alles wird hier nur
		 * als Beispiel eingefuegt.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Erstelle ein Dschungelbeispiel
			Jungle bspDschungel = new Jungle(2, 3, "asdertg", 1);

			// Erstellte ein Nachbarschaftsbeispiel
			INeighborhood bspNachbarschaft = new DistanceNeighborhood(2);

			// Erstelle Beispielzeit
			Double[] bspZeit = { 1.0, 2.0 };

			// Fuege dem bspModell alle erstellten Daten hinzu
			bspModell = new ProblemModel(bspDschungel, null, bspZeit);
			bspModell.addSchlangenart(new SnakeType("A0", bspNachbarschaft, "abcdefghij", 1, 1));
			bspModell.addSchlangenart(new SnakeType("A1", bspNachbarschaft, "hjgzhi", 21, 31));

			// Schreibe nun diese Daten in ein Document
			schreiberXML = new WriterXML(bspModell);
			schreiberXML.schreibeInDatei("res/test_fuer_schreiben_in_loesungsdatei.xml");
			listeFuerTest = schreiberXML.getDokument().getRootElement().getChildren();
		}

		@DisplayName("Einfacher Test fuer das Wurzelelement.")
		@Test
		void testeWurzel() {
			assertEquals("Schlangenjagd", schreiberXML.getDokument().getRootElement().getName(),
					"\nDas eingelesene Wurzelelement entspricht nicht dem Inhalt des Modelles");
		}

		@DisplayName("Einfacher Test fuer das Element 'zeit'.")
		@Test
		void testeZeit() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Zeit") {

					// Ueberpruefe die Zeit im Modell
					assertEquals(1.0, Double.parseDouble(element.getChildText("Vorgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Vorgabe) stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Abgabe")),
							"\nDer eingaelesene Wert fuer das Attribut 'zeit'(Abgabe) stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'dschungel'.")
		@Test
		void testeDschungel() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Dschungel") {

					// Ueberpruefe die Art des Dschungels im Modell
					assertEquals(2, Integer.parseInt(element.getAttributeValue("zeilen")),
							"\nDer eingelesene Wert fuer das Attribut 'zeilen' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("spalten")),
							"\nDer eingelesene Wert fuer das Attribut 'spalten' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("asdertg", element.getAttributeValue("zeichen"),
							"\nDer eingelesene Wert fuer das Attribut 'zeichen' stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}

		@DisplayName("Einfacher Test fuer das Element 'schlangenarten'.")
		@Test
		void testeSchlangenarten() {
			for (Element element : listeFuerTest) {
				if (element.getName() == "Schlangenart") {

					// Ueberpruefe erste Schlangenart des Modelles
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(0).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");

					// Ueberpruefe zweite Schlangenart des Modelles
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nDer eingelesene Wert fuer das Attribut 'id' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("punkte"),
							"\nDer eingelesene Wert fuer das Attribut 'punkte' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("anzahl"),
							"\nDer eingelesene Wert fuer das Attribut 'anzahl' stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Zeichenkette").get(0).getText(),
							"\nDie eingelesene Zeichenkette dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("Distanz",
							element.getChildren().get(1).getChildren("Nachbarschaftsstruktur").get(0)
									.getAttributeValue("typ"),
							"\nDer eingelesene Typ dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("wert"),
							"\nDer eingelesene Parameter dieser Nachbarschaftsstruktur stimmt nicht mit dem aus dem Modell ueberein.");
				}
			}
		}
	}
}
