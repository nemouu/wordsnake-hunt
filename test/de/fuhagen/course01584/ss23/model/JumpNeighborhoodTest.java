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
 * In der Klasse SprungNachbarschaftTest werden die Methoden der Klasse
 * SprungNachbarschaft getestet. Hierbei werden erst ein paar einfache Tests
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
class JumpNeighborhoodTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher positiver Test fuer setParameters.")
		@Test
		void testeSetParameters() {
			int param1 = 5;
			int param2 = 6;
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param1);
			paramList.add(param2);
			INeighborhood sprungNachb = new JumpNeighborhood(paramList);
			int param3 = 7;
			int param4 = 8;
			List<Integer> paramList2 = new ArrayList<Integer>();
			paramList2.add(param3);
			paramList2.add(param4);
			sprungNachb.setParameters(paramList2);
			assertEquals(paramList2, sprungNachb.getParameters(),
					"\nDie Parameter '" + sprungNachb.getParameters().get(0) + "' und '"
							+ sprungNachb.getParameters().get(1) + "' entsprechen nicht dem vorgegebenen Werten '"
							+ param3 + "' und '" + param4 + "'.");
		}

		@DisplayName("Einfacher Test fuer setParameters mit zu grossen Werten in der Liste")
		@Test
		void testeSetParametersGrosseEintraege() {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(2);
			paramList.add(4);
			int param1 = Integer.MAX_VALUE;
			int param2 = Integer.MAX_VALUE;
			List<Integer> paramListGross = new ArrayList<Integer>();
			paramListGross.add(param1);
			paramListGross.add(param2);
			assertThrows(IllegalArgumentException.class,
					() -> new JumpNeighborhood(paramList).setParameters(paramListGross),
					() -> "\nEiner oder mehrere der Parameterwerte '" + param1 + "' und '" + param2
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie zu gross ist/sind.");
		}

		@DisplayName("Einfacher Test fuer setParameters mit zu kleinen Werten in der Liste")
		@Test
		void testeSetParametersKleineEintraege() {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(2);
			paramList.add(4);
			int param1 = Integer.MIN_VALUE;
			int param2 = Integer.MIN_VALUE;
			List<Integer> paramListGross = new ArrayList<Integer>();
			paramListGross.add(param1);
			paramListGross.add(param2);
			assertThrows(IllegalArgumentException.class,
					() -> new JumpNeighborhood(paramList).setParameters(paramListGross),
					() -> "\nEiner oder mehrere der Parameterwerte '" + param1 + "' und '" + param2
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie zu klein ist/sind.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests_fuer_die_Methode_getNachbarn_mit_Parametern_1_und_2 {
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(1);
			paramList.add(2);
			bspNachb = new JumpNeighborhood(paramList);

			// Es wird hier der erzeugte Dschungel auf die Konsole ausgegeben, damit
			// zusaetzlich auch durch Nachschauen getestet werden kann
			System.out.println();
			System.out.println(
					"Es wird der Dschungel ausgegeben, damit zusaetzlich eine Pruefung von Hand moeglich ist:");
			System.out.println();
			System.out.println(bspDschungel.toString());
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der Mitte.")
		@Test
		void testeNachbarnMitParameterEinsMitte() {
			Field zuTestendesFeld = bspDschungel.getFields()[3][3];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[1][2]);
			nachbarFelder.add(bspDschungel.getFields()[2][1]);
			nachbarFelder.add(bspDschungel.getFields()[1][4]);
			nachbarFelder.add(bspDschungel.getFields()[2][5]);
			nachbarFelder.add(bspDschungel.getFields()[4][1]);
			nachbarFelder.add(bspDschungel.getFields()[5][2]);
			nachbarFelder.add(bspDschungel.getFields()[5][4]);
			nachbarFelder.add(bspDschungel.getFields()[4][5]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der linken oberen Ecke.")
		@Test
		void testeNachbarnMitParameterEinsEckeLinksOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[1][2]);
			nachbarFelder.add(bspDschungel.getFields()[2][1]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der rechten oberen Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeRechtsOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[1][5]);
			nachbarFelder.add(bspDschungel.getFields()[2][6]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der linken unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeLinksUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][1]);
			nachbarFelder.add(bspDschungel.getFields()[4][2]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 1 und 2 in der rechten unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsRechtsUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][6]);
			nachbarFelder.add(bspDschungel.getFields()[4][5]);
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
	class Einfache_Tests_fuer_die_Methode_getNachbarn_mit_Parametern_3_und_0 {
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(0);
			paramList.add(3);
			bspNachb = new JumpNeighborhood(paramList);
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der Mitte.")
		@Test
		void testeNachbarnMitParameterEinsMitte() {
			Field zuTestendesFeld = bspDschungel.getFields()[3][3];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][0]);
			nachbarFelder.add(bspDschungel.getFields()[0][3]);
			nachbarFelder.add(bspDschungel.getFields()[3][6]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der linken oberen Ecke.")
		@Test
		void testeNachbarnMitParameterEinsEckeLinksOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[3][0]);
			nachbarFelder.add(bspDschungel.getFields()[0][3]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der rechten oberen Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeRechtsOben() {
			Field zuTestendesFeld = bspDschungel.getFields()[0][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[0][4]);
			nachbarFelder.add(bspDschungel.getFields()[3][7]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der linken unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsEckeLinksUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][0];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[2][0]);
			nachbarFelder.add(bspDschungel.getFields()[5][3]);
			assertEquals(nachbarFelder.size(), bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size(),
					"\nDie erhaltene Liste hat die Groesse "
							+ bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).size()
							+ " aber es wurde eine Liste mit Groesse " + nachbarFelder.size() + " erwartet.");
			assertTrue(nachbarFelder.containsAll(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld)),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
			assertTrue(bspNachb.getNeighbors(bspDschungel, zuTestendesFeld).containsAll(nachbarFelder),
					"\nDie erhaltene Liste enthaelt nicht alle oder keine Elemente der erwarteten Liste.");
		}

		@DisplayName("Test fuer getNachbarn mit den Parametern 0 und 3 in der rechten unteren Ecke.")

		@Test
		void testeNachbarnMitParameterEinsRechtsUnten() {
			Field zuTestendesFeld = bspDschungel.getFields()[5][7];
			HashSet<Field> nachbarFelder = new HashSet<Field>();
			nachbarFelder.add(bspDschungel.getFields()[2][7]);
			nachbarFelder.add(bspDschungel.getFields()[5][4]);
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
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			INeighborhood sprungNachb = new JumpNeighborhood(paramList);
			assertIterableEquals(sprungNachb.getParameters(), paramList,
					"\nDie Parameter '" + sprungNachb.getParameters().get(0) + "' und '"
							+ sprungNachb.getParameters().get(1) + "' entsprechen nicht dem vorgegebenen Werten '"
							+ param + "' und " + (param + 1) + "'.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit negativem Parameter.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeKonstruktorNegativerParameter(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param - 1);
			assertThrows(IllegalArgumentException.class, () -> new JumpNeighborhood(paramList),
					() -> "\nEiner oder mehrere der Parameterwerte '" + param + "' und '" + (param - 1)
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie negativ ist/sind.");
		}

		@DisplayName("Parameterisierter Test fuer Konstruktor mit zu langer Eingabe.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeKonstruktorParameterlisteZuLang(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			paramList.add(param + 2);
			assertThrows(IllegalArgumentException.class, () -> new JumpNeighborhood(paramList),
					() -> "\nDie Eingabeliste hat die Groesse '" + paramList.size() + "' aber erzeugt keine Ausnahme.");
		}

		@DisplayName("Parameterisierter negativer Test fuer setParameters.")
		@ParameterizedTest
		@MethodSource("erzeugeNegativeParameterwerte")
		void testeSetParametersNegativ(int param) {
			List<Integer> paramListInit = new ArrayList<Integer>();
			paramListInit.add(11);
			paramListInit.add(13);
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param - 1);
			assertThrows(IllegalArgumentException.class,
					() -> new JumpNeighborhood(paramListInit).setParameters(paramList),
					() -> "\nEiner oder mehrere der Parameterwerte '" + param + "' und '" + (param - 1)
							+ "' erzeugt/erzeugen keine Ausnahme, obwohl er/sie negativ ist/sind.");
		}

		@DisplayName("Parameterisierter Test fuer setParameters mit zu grosser Liste.")
		@ParameterizedTest
		@MethodSource("erzeugePositiveParameterwerte")
		void testeSetParametersGrosseListe(int param) {
			List<Integer> paramList = new ArrayList<Integer>();
			paramList.add(param);
			paramList.add(param + 1);
			List<Integer> paramListGross = new ArrayList<Integer>();
			paramListGross.add(param + 2);
			paramListGross.add(param + 3);
			paramListGross.add(param + 4);
			assertThrows(IllegalArgumentException.class,
					() -> new JumpNeighborhood(paramList).setParameters(paramListGross),
					() -> "\nFuer die zu grosse Liste wird keine Ausnahme erzeugt.");
		}
	}
}
