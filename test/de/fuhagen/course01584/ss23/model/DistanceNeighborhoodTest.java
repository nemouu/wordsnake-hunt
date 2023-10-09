package de.fuhagen.course01584.ss23.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * In der Klasse DistanzNachbarschaftTest werden die Methoden der Klasse
 * DistanzNachbarschaft getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken. Dann gibt es eine Reihe von parametrisierten Tests, die die
 * Methoden auf eine ganz normale Art und Weise testen. Die Methode getNachbarn
 * wird in eigenen Testklassen getestet. Sowohl in einer normalen Situation als
 * auch in Randsituationen und auch mit verschiedenen Parametern. Um alles auch
 * nochmal von Hand ueberrpuefen zu koennen wird der fuer den Test erzeugte
 * Dschungel einmal auf die Konsole ausgegeben.
 * 
 * @author Philip Redecker
 */
class DistanceNeighborhoodTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher positiver Test fuer setParameters.")
		@Test
		void testeSetParameters() {
			int param = 5;
			INeighborhood distanzNachb = new DistanceNeighborhood(4);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			distanzNachb.setParameters(paramList);
			assertEquals(5, distanzNachb.getParameters().get(0), "\nDer Parameter '" + distanzNachb.getParameters().get(0)
					+ "' entspricht nicht dem vorgegebenen Wert '" + param + "'.");
		}

		@DisplayName("Einfacher Test fuer setParameters mit zu grossen Werten in der Liste.")
		@Test
		void testeSetParametersGrosseEintraege() {
			int param = Integer.MAX_VALUE;
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameters(paramList),
					() -> "Fuer den zu grossen Parameterwert '" + param + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Einfacher Test fuer setParameter mit zu grossen Werten in der Liste.")
		@Test
		void testeSetParameterGrosseEintraege() {
			int param = Integer.MAX_VALUE;
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameter(param),
					() -> "Fuer den zu grossen Parameterwert '" + param + "' wird keine Ausnahme erzeugt.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_die_Methode_getNachbarn_mit_Parameter_1 {
		static Jungle bspDschungel;
		static Random zufallsZeichen;
		static INeighborhood bspNachb;

		@BeforeAll
		static void initDschungel() {
			zufallsZeichen = new Random(1);
			bspDschungel = new Jungle(6, 8, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("" + (char) (zufallsZeichen.nextInt(26) + 'a'));
				}
			}
			bspNachb = new DistanceNeighborhood(1);

			// Es wird hier der erzeugte Dschungel auf die Konsole ausgegeben, damit
			// zusaetzlich auch durch Nachschauen getestet werden kann
			System.out.println();
			System.out.println(
					"Es wird der Dschungel ausgegeben, damit zusaetzlich eine Pruefung von Hand moeglich ist:");
			System.out.println();
			System.out.println(bspDschungel.toString());
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 1 in der Mitte.")
		@Test
		void testeNachbarnMitParameterEinsMitte() {
			INeighborhood bspNachb = new DistanceNeighborhood(1);
			Field zuTestendesFeld = bspDschungel.getFields()[4][5];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][4]);
			nachbarFelder.add(bspDschungel.getFields()[3][5]);
			nachbarFelder.add(bspDschungel.getFields()[3][6]);
			nachbarFelder.add(bspDschungel.getFields()[4][4]);
			nachbarFelder.add(bspDschungel.getFields()[4][6]);
			nachbarFelder.add(bspDschungel.getFields()[5][4]);
			nachbarFelder.add(bspDschungel.getFields()[5][5]);
			nachbarFelder.add(bspDschungel.getFields()[5][6]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 1 in der linken oberen Ecke.")
		@Test
		void testeNachbarnMitParameterEinsEckeLinksOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[0][1]);
			nachbarFelder.add(bspDschungel.getFields()[1][0]);
			nachbarFelder.add(bspDschungel.getFields()[1][1]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 1 in der rechten oberen Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeRechtsOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[0][6]);
			nachbarFelder.add(bspDschungel.getFields()[1][6]);
			nachbarFelder.add(bspDschungel.getFields()[1][7]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 1 in der linken unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeLinksUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[4][0]);
			nachbarFelder.add(bspDschungel.getFields()[4][1]);
			nachbarFelder.add(bspDschungel.getFields()[5][1]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 1 in der rechten unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsRechtsUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[5][6]);
			nachbarFelder.add(bspDschungel.getFields()[4][6]);
			nachbarFelder.add(bspDschungel.getFields()[4][7]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_die_Methode_getNachbarn_mit_Parameter_2 {
		static Jungle bspDschungel;
		static Random zufallsZeichen;
		static INeighborhood bspNachb;

		@BeforeAll
		static void initDschungel() {
			zufallsZeichen = new Random(1);
			bspDschungel = new Jungle(6, 8, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("" + (char) (zufallsZeichen.nextInt(26) + 'a'));
				}
			}
			bspNachb = new DistanceNeighborhood(2);
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 2 in der Mitte.")
		@Test
		void testeNachbarnMitParameterEinsMitte() {
			Field zuTestendesFeld = bspDschungel.getFields()[3][3];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[1][1]);
			nachbarFelder.add(bspDschungel.getFields()[1][2]);
			nachbarFelder.add(bspDschungel.getFields()[1][3]);
			nachbarFelder.add(bspDschungel.getFields()[1][4]);
			nachbarFelder.add(bspDschungel.getFields()[1][5]);
			nachbarFelder.add(bspDschungel.getFields()[2][1]);
			nachbarFelder.add(bspDschungel.getFields()[2][2]);
			nachbarFelder.add(bspDschungel.getFields()[2][3]);
			nachbarFelder.add(bspDschungel.getFields()[2][4]);
			nachbarFelder.add(bspDschungel.getFields()[2][5]);
			nachbarFelder.add(bspDschungel.getFields()[3][1]);
			nachbarFelder.add(bspDschungel.getFields()[3][2]);
			nachbarFelder.add(bspDschungel.getFields()[3][4]);
			nachbarFelder.add(bspDschungel.getFields()[3][5]);
			nachbarFelder.add(bspDschungel.getFields()[4][1]);
			nachbarFelder.add(bspDschungel.getFields()[4][2]);
			nachbarFelder.add(bspDschungel.getFields()[4][3]);
			nachbarFelder.add(bspDschungel.getFields()[4][4]);
			nachbarFelder.add(bspDschungel.getFields()[4][5]);
			nachbarFelder.add(bspDschungel.getFields()[5][1]);
			nachbarFelder.add(bspDschungel.getFields()[5][2]);
			nachbarFelder.add(bspDschungel.getFields()[5][3]);
			nachbarFelder.add(bspDschungel.getFields()[5][4]);
			nachbarFelder.add(bspDschungel.getFields()[5][5]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 2 in der linken oberen Ecke.")
		@Test
		void testeNachbarnMitParameterEinsEckeLinksOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[0][1]);
			nachbarFelder.add(bspDschungel.getFields()[0][2]);
			nachbarFelder.add(bspDschungel.getFields()[1][0]);
			nachbarFelder.add(bspDschungel.getFields()[1][1]);
			nachbarFelder.add(bspDschungel.getFields()[1][2]);
			nachbarFelder.add(bspDschungel.getFields()[2][0]);
			nachbarFelder.add(bspDschungel.getFields()[2][1]);
			nachbarFelder.add(bspDschungel.getFields()[2][2]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 2 in der rechten oberen Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeRechtsOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[0][5]);
			nachbarFelder.add(bspDschungel.getFields()[0][6]);
			nachbarFelder.add(bspDschungel.getFields()[1][5]);
			nachbarFelder.add(bspDschungel.getFields()[1][6]);
			nachbarFelder.add(bspDschungel.getFields()[1][7]);
			nachbarFelder.add(bspDschungel.getFields()[2][5]);
			nachbarFelder.add(bspDschungel.getFields()[2][6]);
			nachbarFelder.add(bspDschungel.getFields()[2][7]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 2 in der linken unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeLinksUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][0]);
			nachbarFelder.add(bspDschungel.getFields()[3][1]);
			nachbarFelder.add(bspDschungel.getFields()[3][2]);
			nachbarFelder.add(bspDschungel.getFields()[4][0]);
			nachbarFelder.add(bspDschungel.getFields()[4][1]);
			nachbarFelder.add(bspDschungel.getFields()[4][2]);
			nachbarFelder.add(bspDschungel.getFields()[5][1]);
			nachbarFelder.add(bspDschungel.getFields()[5][2]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit Parameter 2 in der rechten unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsRechtsUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][5]);
			nachbarFelder.add(bspDschungel.getFields()[3][6]);
			nachbarFelder.add(bspDschungel.getFields()[3][7]);
			nachbarFelder.add(bspDschungel.getFields()[4][5]);
			nachbarFelder.add(bspDschungel.getFields()[4][6]);
			nachbarFelder.add(bspDschungel.getFields()[4][7]);
			nachbarFelder.add(bspDschungel.getFields()[5][5]);
			nachbarFelder.add(bspDschungel.getFields()[5][6]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
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

		@DisplayName("Parameterisierter positiver Test fuer Konstruktor und getParameters.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorUndGetParametersPositiv(int param) {
			INeighborhood distanzNachb = new DistanceNeighborhood(param);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertIterableEquals(distanzNachb.getParameters(), paramList, "\nDer Parameter '"
					+ distanzNachb.getParameters().get(0) + "' entspricht nicht dem vorgegebenen Wert '" + param + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativem Parameter.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorUndGetSpaltePositiv(int param) {
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(param),
					() -> "Fuer den (negativen) " + "Parameterwert '" + param + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setParameters.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetParametersNegativ(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(1).setParameters(paramList),
					() -> "\nBeim Setzen des (negativen) Parameterwertes '" + param + "' wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter Test fuer setParameters mit zu grosser Liste.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeSetParametersGrosseListe(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 10);
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(4).setParameters(paramList),
					() -> "\nFuer die zu grosse Liste wird keine Ausnahme erzeugt.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setParameter.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetParameterNegativ(int param) {
			assertThrows(IllegalArgumentException.class, () -> new DistanceNeighborhood(1).setParameter(param),
					() -> "\nBeim Setzen des (negativen) Parameterwertes '" + param + "' wird keine Ausnahme erzeugt.");
		}
	}
}
