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
		SnakeElement glied1 = new SnakeElement(new Field(0, 1, 2));
		SnakeElement glied2 = new SnakeElement(new Field(0, 3, 1));
		SnakeElement glied3 = new SnakeElement(new Field(0, 4, 1));
		SnakeElement glied4 = new SnakeElement(new Field(1, 3, 1));
		SnakeElement glied5 = new SnakeElement(new Field(0, 6, 1));
		SnakeElement glied6 = new SnakeElement(new Field(1, 5, 1));
		List<SnakeElement> glieder = new ArrayList<SnakeElement>();
		glieder.add(glied1);
		glieder.add(glied2);
		glieder.add(glied3);
		glieder.add(glied4);
		glieder.add(glied5);
		glieder.add(glied6);
		exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), glieder);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test von naechstesGlied, Teil 1.")
		@Test
		void testCharacterOfNextElementOne() {
			SnakeElement glied1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement glied2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement glied3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement glied4 = new SnakeElement(new Field(1, 3, 1));
			List<SnakeElement> glieder1 = new ArrayList<SnakeElement>();
			glieder1.add(glied1);
			glieder1.add(glied2);
			glieder1.add(glied3);
			glieder1.add(glied4);
			Snake bspSchlange = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1),
					glieder1);
			assertEquals("O", bspSchlange.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 2.")
		@Test
		void testCharacterOfNextElementTwo() {
			SnakeElement glied1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement glied2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement glied3 = new SnakeElement(new Field(0, 4, 1));
			List<SnakeElement> glieder1 = new ArrayList<SnakeElement>();
			glieder1.add(glied1);
			glieder1.add(glied2);
			glieder1.add(glied3);
			Snake bspSchlange = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1),
					glieder1);
			assertEquals("L", bspSchlange.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 3.")
		@Test
		void testCharacterOfNextElementThree() {
			SnakeElement glied1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement glied2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement glied3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement glied4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement glied5 = new SnakeElement(new Field(0, 6, 1));
			List<SnakeElement> glieder1 = new ArrayList<SnakeElement>();
			glieder1.add(glied1);
			glieder1.add(glied2);
			glieder1.add(glied3);
			glieder1.add(glied4);
			glieder1.add(glied5);
			Snake bspSchlange = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1),
					glieder1);
			assertEquals("S", bspSchlange.characterOfNextElement(),
					"\nDas ausgegebene naechste Glied stimmt nicht mit dem tatsaechlichen naechsten Glied ueberein.");
		}

		@DisplayName("Einfacher Test von naechstesGlied, Teil 4.")
		@Test
		void testCharacterOfNextElementFour() {
			SnakeElement glied1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement glied2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement glied3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement glied4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement glied5 = new SnakeElement(new Field(0, 6, 1));
			SnakeElement glied6 = new SnakeElement(new Field(1, 5, 1));
			List<SnakeElement> glieder = new ArrayList<SnakeElement>();
			glieder.add(glied1);
			glieder.add(glied2);
			glieder.add(glied3);
			glieder.add(glied4);
			glieder.add(glied5);
			glieder.add(glied6);
			Snake bspSchlange = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1),
					glieder);
			assertEquals("", bspSchlange.characterOfNextElement(),
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
			Snake bspSchlange = new Snake();
			bspSchlange.removeLastElement();
			assertEquals(0, bspSchlange.getElements().size(),
					"\nDie Schlange hat nicht die gewuenschte Anzahl Glieder. Es sollte ein Glied weniger sein also vorher "
							+ "(also 5) aber es wurde " + exampleSnake.getElements().size() + " ausgegeben.");
		}
	}
}