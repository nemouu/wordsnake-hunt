package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * In der Klasse LoesungTest werden die Methoden der Klasse Loesung getestet.
 * Hierbei werden erst ein paar einfache Tests durchgefuehrt, die dazu dienen
 * sollen ein paar wichtige Randsituationen abzudecken. Ausserdem wird hier auch
 * die Methode toString mehrere Male getestet. Dann gibt es eine Reihe von
 * parametrisierten Tests, die die Methoden auf eine ganz normale Art und Weise
 * testen.
 * 
 * @author Philip Redecker
 */
class SolutionTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher Test fuer getSchlangeMitID, Teil 1")
		@Test
		void testeGetSchlangeMitIDEins() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSchlange(schlange1);
			bspLoesung.addSchlange(schlange2);
			assertEquals(bspLoesung.getSchlangeMitSchlangenartID("A0").getType().toString(),
					"ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=1, Anzahl=1",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer getSchlangeMitID, Teil 2")
		@Test
		void testeGetSchlangeMitIDZwei() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSchlange(schlange1);
			bspLoesung.addSchlange(schlange2);
			assertEquals(bspLoesung.getSchlangeMitSchlangenartID("A1").getType().toString(),
					"ID=A1, Nachbarschaftsstruktur=Distanz, Zeichenkette=JUNITTEST, Punkte=2, Anzahl=4",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 1")
		@Test
		void testeAnzahlSchlangenartenEins() {
			Solution bspLoesung = new Solution();
			assertEquals(0, bspLoesung.getAnzahlSchlangenarten(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 2")
		@Test
		void testeAnzahlSchlangenartenZwei() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSchlange(schlange1);
			bspLoesung.addSchlange(schlange2);
			assertEquals(2, bspLoesung.getAnzahlSchlangenarten(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer addSchlange.")
		@Test
		void testeAddSchlange() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			bspLoesung.addSchlange(schlange1);
			assertEquals("A0", bspLoesung.getSchlangen().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", bspLoesung.getSchlangen().get(0).getType().getStruktur().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("FERNUNI", bspLoesung.getSchlangen().get(0).getType().getZeichenkette(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, bspLoesung.getSchlangen().get(0).getType().getPunkte(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(3, bspLoesung.getSchlangen().get(0).getType().getAnzahl(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 1.")
		@Test
		void testeEntferneLetzteSchlangeEins() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSchlange(schlange2);
			bspLoesung.addSchlange(schlange1);
			bspLoesung.entferneLetzteSchlange();
			assertEquals("A1", bspLoesung.getSchlangen().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", bspLoesung.getSchlangen().get(0).getType().getStruktur().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("JUNITTEST", bspLoesung.getSchlangen().get(0).getType().getZeichenkette(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, bspLoesung.getSchlangen().get(0).getType().getPunkte(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(4, bspLoesung.getSchlangen().get(0).getType().getAnzahl(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 2.")
		@Test
		void testeEntferneLetzteSchlangeZwei() {
			Solution bspLoesung = new Solution();
			bspLoesung.entferneLetzteSchlange();
			assertEquals(0, bspLoesung.getSchlangen().size(),
					"\nDie Schlangenliste hat nicht die Groesse 0, obwohl keine Schlangen enthalten sind.");
		}
	}
}