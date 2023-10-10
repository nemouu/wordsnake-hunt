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
	class Simple_Tests {
		@DisplayName("Einfacher Test fuer getSchlangeMitID, Teil 1")
		@Test
		void testGetSnakeWithIDOne() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSnake(schlange1);
			bspLoesung.addSnake(schlange2);
			assertEquals(bspLoesung.getSnakeWithSnakeTypeID("A0").getType().toString(),
					"ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=1, Anzahl=1",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer getSchlangeMitID, Teil 2")
		@Test
		void testGetSnakeWithIDTwo() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSnake(schlange1);
			bspLoesung.addSnake(schlange2);
			assertEquals(bspLoesung.getSnakeWithSnakeTypeID("A1").getType().toString(),
					"ID=A1, Nachbarschaftsstruktur=Distanz, Zeichenkette=JUNITTEST, Punkte=2, Anzahl=4",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 1")
		@Test
		void testAmountSnakeTypesOne() {
			Solution bspLoesung = new Solution();
			assertEquals(0, bspLoesung.getNumberOfDifferentSnakeTypes(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 2")
		@Test
		void testAmountSnakeTypesTwo() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSnake(schlange1);
			bspLoesung.addSnake(schlange2);
			assertEquals(2, bspLoesung.getNumberOfDifferentSnakeTypes(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer addSchlange.")
		@Test
		void testAddSnake() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			bspLoesung.addSnake(schlange1);
			assertEquals("A0", bspLoesung.getSnakes().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", bspLoesung.getSnakes().get(0).getType().getStructure().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("FERNUNI", bspLoesung.getSnakes().get(0).getType().getSigns(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, bspLoesung.getSnakes().get(0).getType().getPoints(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(3, bspLoesung.getSnakes().get(0).getType().getAmount(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 1.")
		@Test
		void testRemoveLastSnakeOne() {
			Solution bspLoesung = new Solution();
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Snake schlange2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			bspLoesung.addSnake(schlange2);
			bspLoesung.addSnake(schlange1);
			bspLoesung.removeLastSnake();
			assertEquals("A1", bspLoesung.getSnakes().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", bspLoesung.getSnakes().get(0).getType().getStructure().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("JUNITTEST", bspLoesung.getSnakes().get(0).getType().getSigns(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, bspLoesung.getSnakes().get(0).getType().getPoints(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(4, bspLoesung.getSnakes().get(0).getType().getAmount(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 2.")
		@Test
		void testRemoveLastSnakeTwo() {
			Solution bspLoesung = new Solution();
			bspLoesung.removeLastSnake();
			assertEquals(0, bspLoesung.getSnakes().size(),
					"\nDie Schlangenliste hat nicht die Groesse 0, obwohl keine Schlangen enthalten sind.");
		}
	}
}