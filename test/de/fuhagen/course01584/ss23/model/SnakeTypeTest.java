package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse SchlangenartTest werden die Methoden der Klasse Schlangenart
 * getestet. Hierbei werden erst ein paar einfache Tests durchgefuehrt, die dazu
 * dienen sollen ein paar wichtige Randsituationen abzudecken. Ausserdem wird
 * hier auch die Methode toString mehrere Male getestet. Dann gibt es eine Reihe
 * von parametrisierten Tests, die die Methoden auf eine ganz normale Art und
 * Weise testen. Um eine Beispielnachbarschaft fuer alle Methoden zum Testen
 * bereitstellen zu koennen wird diese in einer @BeforeAll Methode
 * initialisiert.
 * 
 * @author Philip Redecker
 */
class SnakeTypeTest {

	private static INeighborhood exampleNeighborhood;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		exampleNeighborhood = new DistanceNeighborhood(5);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte' mit zu grossen Wert.")
		@Test
		void testPointsLarge() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", punkte, 1),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test bezueglich Attribut 'punkte' mit zu kleinem Wert.")
		@Test
		void testPointsSmall() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", punkte, 1),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setPunkte mit zu grossen Wert.")
		@Test
		void testSetPointsLarge() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setPoints(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test von setPunkte mit zu kleinem Wert.")
		@Test
		void testSetPointsSmall() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setPoints(punkte),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit zu grossen Wert.")
		@Test
		void testAmountLarge() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, anzahl),
					() -> "Fuer den zu grossen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit zu kleinem Wert.")
		@Test
		void testAmountSmall() {
			int anzahl = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit Wert 0.")
		@Test
		void testAmountZero() {
			int anzahl = 0;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", exampleNeighborhood, "", 1, anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit zu grossen Wert.")
		@Test
		void testSetAmountLarge() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(anzahl),
					() -> "Fuer den zu grossen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit zu kleinem Wert.")
		@Test
		void testSetAmountSmall() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit Wert 0.")
		@Test
		void testSetAmountZero() {
			int anzahl = -1;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", exampleNeighborhood, "", 0, 1).setAmount(anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Test toString Methode, wenn moeglichst viel leer ist")
		@Test
		void testToStringEmpty() {
			assertThrows(NullPointerException.class, () -> new SnakeType(null, null, null, 0, 1),
					"\nEs wird nicht der leere String zurueckgegeben, obwohl das Modell leer ist.");
		}

		@DisplayName("Test toString Methode mit normalem Inhalt")
		@Test
		void testToStringNormal() {
			SnakeType art = new SnakeType("A0", exampleNeighborhood, "FERNUNI", 2, 3);
			assertEquals("ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=2, Anzahl=3",
					art.toString(), "\nEs wird nicht der richtige String zurueckgegeben.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parameterized_Tests {
		static Random randomNumberGenerator;

		@BeforeAll
		static void initAll() {
			randomNumberGenerator = new Random(1);
		}

		static IntStream generateNegativeParametervalues() {
			return IntStream.iterate(randomNumberGenerator.nextInt(-20, -1), i -> i - 10).limit(20);
		}

		static IntStream generatePositiveParametervalues() {
			return IntStream.iterate(randomNumberGenerator.nextInt(1, 20), i -> i + 10).limit(20);
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getPunkte.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetPointsPositive(int punkte) {
			SnakeType art = new SnakeType("a", exampleNeighborhood, "a", punkte, 1);
			assertEquals(art.getPoints(), punkte, "\nDer Wert fuer Punkte '" + art.getPoints()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer Konstruktor und getPunkte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorAndGetPointsNegative(int punkte) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", exampleNeighborhood, "a", punkte, 1),
					"\nEs wurde keine Ausnahme ausgeloest, obwohl der Wert fuer Punkte negativ ist.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getAnzahl.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testConstructorAndGetAmountPositive(int anzahl) {
			SnakeType art = new SnakeType("a", exampleNeighborhood, "a", 1, anzahl);
			assertEquals(art.getAmount(), anzahl, "\nDer Wert fuer Anzahl '" + art.getAmount()
					+ "' entspricht nicht dem vorgegebenen Wert '" + anzahl + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer Konstruktor und getAnzahl.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testConstructorAndGetAmountNegative(int anzahl) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", exampleNeighborhood, "a", 1, anzahl),
					"\nFuer den (negativen) Wert fuer Anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter positiver Test fuer setPunkte und getPunkte.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetPointsPositive(int punkte) {
			SnakeType art = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			art.setPoints(punkte);
			assertEquals(art.getPoints(), punkte, "\nDer Wert fuer Punkte '" + art.getPoints()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setPunkte und getPunkte.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetPointsNegative(int punkte) {
			SnakeType art = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			assertThrows(IllegalArgumentException.class, () -> art.setPoints(punkte),
					"\nEs wurde keine Ausnahme ausgeloest, obwohl der Wert fuer Punkte negativ ist.");
		}

		@DisplayName("Parameterisierter positiver Test fuer setAnzahl und getAnzahl.")
		@ParameterizedTest
		@MethodSource("generatePositiveParametervalues")
		void testSetAmountPositive(int anzahl) {
			SnakeType art = new SnakeType("a", exampleNeighborhood, "a", 1, 1);
			art.setAmount(anzahl);
			assertEquals(art.getAmount(), anzahl, "\nDer Wert fuer Anzahl '" + art.getAmount()
					+ "' entspricht nicht dem vorgegebenen Wert '" + anzahl + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setAnzahl.")
		@ParameterizedTest
		@MethodSource("generateNegativeParametervalues")
		void testSetAmountNegative(int anzahl) {
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("a", exampleNeighborhood, "a", 1, 1).setAmount(anzahl),
					"\nFuer den (negativen) Wert fuer Anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}
	}
}
