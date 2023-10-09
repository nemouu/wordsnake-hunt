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

	private static INeighborhood nachbarS;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		nachbarS = new DistanceNeighborhood(5);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher positiver Test bezueglich Attribut 'punkte' mit zu grossen Wert.")
		@Test
		void testePunkteGross() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", nachbarS, "", punkte, 1),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test bezueglich Attribut 'punkte' mit zu kleinem Wert.")
		@Test
		void testePunkteKlein() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", nachbarS, "", punkte, 1),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setPunkte mit zu grossen Wert.")
		@Test
		void testeSetPunkteGross() {
			int punkte = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", nachbarS, "", 0, 1).setPunkte(punkte),
					() -> "Fuer den" + "zu grossen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher negativer Test von setPunkte mit zu kleinem Wert.")
		@Test
		void testeSetPunkteKlein() {
			int punkte = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", nachbarS, "", 0, 1).setPunkte(punkte),
					() -> "Fuer den" + "zu kleinen Wert von 'punkte '" + punkte + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit zu grossen Wert.")
		@Test
		void testeAnzahlGross() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", nachbarS, "", 1, anzahl),
					() -> "Fuer den zu grossen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit zu kleinem Wert.")
		@Test
		void testeAnzahlKlein() {
			int anzahl = Integer.MIN_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", nachbarS, "", 1, anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test bezueglich Attribut 'anzahl' mit Wert 0.")
		@Test
		void testeAnzahlNull() {
			int anzahl = 0;
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("", nachbarS, "", 1, anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit zu grossen Wert.")
		@Test
		void testeSetAnzahlGross() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", nachbarS, "", 0, 1).setAnzahl(anzahl),
					() -> "Fuer den zu grossen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit zu kleinem Wert.")
		@Test
		void testeSetAnzahlKlein() {
			int anzahl = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", nachbarS, "", 0, 1).setAnzahl(anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher positiver Test von setAnzahl mit Wert 0.")
		@Test
		void testeSetAnzahlNull() {
			int anzahl = -1;
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("", nachbarS, "", 0, 1).setAnzahl(anzahl),
					() -> "Fuer den zu kleinen Wert von 'anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Test toString Methode, wenn moeglichst viel leer ist")
		@Test
		void testeToStringLeer() {
			assertThrows(NullPointerException.class, () -> new SnakeType(null, null, null, 0, 1),
					"\nEs wird nicht der leere String zurueckgegeben, obwohl das Modell leer ist.");
		}

		@DisplayName("Test toString Methode mit normalem Inhalt")
		@Test
		void testeToStringNormal() {
			SnakeType art = new SnakeType("A0", nachbarS, "FERNUNI", 2, 3);
			assertEquals("ID=A0, Nachbarschaftsstruktur=Distanz, Zeichenkette=FERNUNI, Punkte=2, Anzahl=3",
					art.toString(), "\nEs wird nicht der richtige String zurueckgegeben.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parametrisierte_Tests {
		static Random zufallszahlenGenerator;

		@BeforeAll
		static void initAll() {
			zufallszahlenGenerator = new Random(1);
		}

		static IntStream erzeugeNegativeParameterwerte() {
			return IntStream.iterate(zufallszahlenGenerator.nextInt(-20, -1), i -> i - 10).limit(20);
		}

		static IntStream erzeugePositiveParameterwerte() {
			return IntStream.iterate(zufallszahlenGenerator.nextInt(1, 20), i -> i + 10).limit(20);
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getPunkte.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetPunktePositiv(int punkte) {
			SnakeType art = new SnakeType("a", nachbarS, "a", punkte, 1);
			assertEquals(art.getPunkte(), punkte, "\nDer Wert fuer Punkte '" + art.getPunkte()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer Konstruktor und getPunkte.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorUndGetPunkteNegativ(int punkte) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", nachbarS, "a", punkte, 1),
					"\nEs wurde keine Ausnahme ausgeloest, obwohl der Wert fuer Punkte negativ ist.");
		}

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getAnzahl.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetAnzahlPositiv(int anzahl) {
			SnakeType art = new SnakeType("a", nachbarS, "a", 1, anzahl);
			assertEquals(art.getAnzahl(), anzahl, "\nDer Wert fuer Anzahl '" + art.getAnzahl()
					+ "' entspricht nicht dem vorgegebenen Wert '" + anzahl + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer Konstruktor und getAnzahl.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorUndGetAnzahlNegativ(int anzahl) {
			assertThrows(IllegalArgumentException.class, () -> new SnakeType("a", nachbarS, "a", 1, anzahl),
					"\nFuer den (negativen) Wert fuer Anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter positiver Test fuer setPunkte und getPunkte.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeSetPunktePositiv(int punkte) {
			SnakeType art = new SnakeType("a", nachbarS, "a", 1, 1);
			art.setPunkte(punkte);
			assertEquals(art.getPunkte(), punkte, "\nDer Wert fuer Punkte '" + art.getPunkte()
					+ "' entspricht nicht dem vorgegebenen Wert '" + punkte + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setPunkte und getPunkte.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetPunkteNegativ(int punkte) {
			SnakeType art = new SnakeType("a", nachbarS, "a", 1, 1);
			assertThrows(IllegalArgumentException.class, () -> art.setPunkte(punkte),
					"\nEs wurde keine Ausnahme ausgeloest, obwohl der Wert fuer Punkte negativ ist.");
		}

		@DisplayName("Parameterisierter positiver Test fuer setAnzahl und getAnzahl.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeSetAnzahlPositiv(int anzahl) {
			SnakeType art = new SnakeType("a", nachbarS, "a", 1, 1);
			art.setAnzahl(anzahl);
			assertEquals(art.getAnzahl(), anzahl, "\nDer Wert fuer Anzahl '" + art.getAnzahl()
					+ "' entspricht nicht dem vorgegebenen Wert '" + anzahl + "'.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setAnzahl.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetAnzahlNegativ(int anzahl) {
			assertThrows(IllegalArgumentException.class,
					() -> new SnakeType("a", nachbarS, "a", 1, 1).setAnzahl(anzahl),
					"\nFuer den (negativen) Wert fuer Anzahl '" + anzahl + "' wird keine Ausnahme erzeugt.");
		}
	}
}
