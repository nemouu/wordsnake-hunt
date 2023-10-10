package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * In der Klasse SchlangeTest werden die Methoden der Klasse Schlange getestet.
 * Hierbei werden ein paar einfache Tests durchgefuehrt, die dazu dienen sollen
 * ein paar wichtige Randsituationen abzudecken. Um eine Beispielschlange und
 * fuer alle Methoden zum Testen bereitstellen zu koennen wird diese in
 * einer @BeforeAll Methode initialisiert.
 * 
 * @author Philip Redecker
 */
class SnakeTest {
	private static Snake exampleSnake;

	@BeforeAll
	static void setUp() {
		SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
		SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
		SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
		SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
		SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
		SnakeElement element6 = new SnakeElement(new Field(1, 5, 1));
		List<SnakeElement> elements = new ArrayList<SnakeElement>();
		elements.add(element1);
		elements.add(element2);
		elements.add(element3);
		elements.add(element4);
		elements.add(element5);
		elements.add(element6);
		exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test von naechstesGlied, Teil 1.")
		@Test
		void testCharacterOfNextElementOne() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("O", exampleSnake.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 2.")
		@Test
		void testCharacterOfNextElementTwo() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("L", exampleSnake.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 3.")
		@Test
		void testCharacterOfNextElementThree() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			elements.add(element5);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("S", exampleSnake.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 4.")
		@Test
		void testCharacterOfNextElementFour() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
			SnakeElement element6 = new SnakeElement(new Field(1, 5, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			elements.add(element5);
			elements.add(element6);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("", exampleSnake.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von lastGlied")
		@Test
		void testCharacterOfLastElement() {
			assertEquals("S", exampleSnake.characterOfLastElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von entferneLetztesGlied, Teil 1")
		@Test
		void testRemoveLastElementOne() {
			exampleSnake.removeLastElement();
			assertEquals(5, exampleSnake.getElements().size(),
					"\nDie Schlange hat nicht die gewuenschte Anzahl Glieder. Es sollte ein Glied weniger sein also vorher "
							+ "(also 5) aber es wurde " + exampleSnake.getElements().size() + " ausgegeben.");
		}

		@DisplayName("Einfacher Test von entferneLetztesGlied, Teil 2")
		@Test
		void testRemoveLastElementTwo() {
			Snake exampleSnake2 = new Snake();
			exampleSnake2.removeLastElement();
			assertEquals(0, exampleSnake2.getElements().size(),
					"\nDie Schlange hat nicht die gewuenschte Anzahl Glieder. Es sollte ein Glied weniger sein also vorher "
							+ "(also 5) aber es wurde " + exampleSnake.getElements().size() + " ausgegeben.");
		}
	}
}