package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import de.fuhagen.course01584.ss23.main.SnakeHunt;
import de.fuhagen.course01584.ss23.main.SnakeHuntAPI;

/**
 * In der Klasse LoesungsBewerterTest werden die Methoden der Klasse
 * LoesungsBewerter getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken, vor allem die Situation, dass ein uebergebenes Modell gar keine
 * Loesung hat. Hinweis: Alle Klassen des Algorithmuspaketes werden auch noch
 * einmal durch die Tests der Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class SolutionEvaluatorTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher Test fuer Loesungsbewerter, Teil 1.")
		@Test
		void testeBewerteLoesungBeispielEins() {
			SnakeHuntAPI jagd = new SnakeHunt();
			assertEquals(8, jagd.bewerteLoesung("res/sj_p1_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungsbewerter, Teil 2.")
		@Test
		void testeBewerteLoesungBeispielZwei() {
			SnakeHuntAPI jagd = new SnakeHunt();
			assertEquals(36, jagd.bewerteLoesung("res/sj_p2_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungsbewerter, Teil 3.")
		@Test
		void testeBewerteLoesungBeispielDrei() {
			SnakeHuntAPI jagd = new SnakeHunt();
			assertEquals(81, jagd.bewerteLoesung("res/sj_p3_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungsbewerter, Teil 4.")
		@Test
		void testeBewerteLoesungBeispielVier() {
			SnakeHuntAPI jagd = new SnakeHunt();
			assertEquals(41, jagd.bewerteLoesung("res/sj_p4_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungsbewerter, wenn keine Loesung vorhanden ist.")
		@Test
		void testeBewerteLoesungBeispielKeineFehler() {
			SnakeHuntAPI jagd = new SnakeHunt();
			assertEquals(0, jagd.bewerteLoesung("res/sj_p4_probleminstanz.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}
	}
}
