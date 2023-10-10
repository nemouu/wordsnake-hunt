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
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(exampleSolution.getSnakeWithSnakeTypeID("A0").getType().toString(),
					"ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=1, Anzahl=1",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer getSchlangeMitID, Teil 2")
		@Test
		void testGetSnakeWithIDTwo() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(exampleSolution.getSnakeWithSnakeTypeID("A1").getType().toString(),
					"ID=A1, Nachbarschaftsstruktur=Distanz, Zeichenkette=JUNITTEST, Punkte=2, Anzahl=4",
					"\nEs wird nicht das erwartete Ergebnis geliefert.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 1")
		@Test
		void testAmountSnakeTypesOne() {
			Solution exampleSolution = new Solution();
			assertEquals(0, exampleSolution.getNumberOfDifferentSnakeTypes(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer anzahlSchlangenarten, Teil 2")
		@Test
		void testAmountSnakeTypesTwo() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(2, exampleSolution.getNumberOfDifferentSnakeTypes(),
					"\nDie errechnete Anzahl von Schlangenarten stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer addSchlange.")
		@Test
		void testAddSnake() {
			Solution exampleSolution = new Solution();
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			exampleSolution.addSnake(snake);
			assertEquals("A0", exampleSolution.getSnakes().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", exampleSolution.getSnakes().get(0).getType().getStructure().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("FERNUNI", exampleSolution.getSnakes().get(0).getType().getSigns(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, exampleSolution.getSnakes().get(0).getType().getPoints(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(3, exampleSolution.getSnakes().get(0).getType().getAmount(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 1.")
		@Test
		void testRemoveLastSnakeOne() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake2);
			exampleSolution.addSnake(snake1);
			exampleSolution.removeLastSnake();
			assertEquals("A1", exampleSolution.getSnakes().get(0).getType().getId(),
					"\nDie erhaltene ID der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("Distanz", exampleSolution.getSnakes().get(0).getType().getStructure().getType(),
					"\nDie erhaltene Nachbarschaftsstruktur stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals("JUNITTEST", exampleSolution.getSnakes().get(0).getType().getSigns(),
					"\nDie erhaltene Zeichenkette der Schlange stimmt nicht mit der tatsaechlichen ueberein.");
			assertEquals(2, exampleSolution.getSnakes().get(0).getType().getPoints(),
					"\nDie errechneten Punkte der Schlange stimmen nicht mit der tatsaechlichen ueberein.");
			assertEquals(4, exampleSolution.getSnakes().get(0).getType().getAmount(),
					"\nDie errechnete Anzahl von Schlangen stimmt nicht mit der tatsaechlichen ueberein.");
		}

		@DisplayName("Einfacher Test fuer entferneLetzteSchlange, Teil 2.")
		@Test
		void testRemoveLastSnakeTwo() {
			Solution exampleSolution = new Solution();
			exampleSolution.removeLastSnake();
			assertEquals(0, exampleSolution.getSnakes().size(),
					"\nDie Schlangenliste hat nicht die Groesse 0, obwohl keine Schlangen enthalten sind.");
		}
	}
}