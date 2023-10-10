package de.fuhagen.course01584.ss23.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;

import de.fuhagen.course01584.ss23.main.SnakeHuntAPI.ErrorType;

import java.util.*;

/**
 * In der Klasse SchlangenjagdTest werden die Methoden der Klasse Schlangenjagd
 * getestet. Bei den Methoden bei welchen es moeglich ist werden parametrisierte
 * Tests genutzt. Dazu werden die bereitgestellten Dateien genutzt. Abhaengig
 * von der gerade getesteten Methode werden also immer die
 * <code>sj_pX_loesung.xml</code>, die <code>sj_pX_probleminstanz.xml</code>
 * oder die <code>sj_pX_unvollstaendig.xml</code> Dateien genutzt. Sind
 * parametrisierte Tests aufgrund der Methodenstruktur nicht moeglich, werden
 * einfache Tests genutzt.
 * 
 * @author Philip Redecker
 */
class SnakeHuntTest {
	private static SnakeHuntAPI hunt;
	static int count = 1;

	@BeforeAll
	static void createHunt() {
		hunt = new SnakeHunt();
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test fuer loeseProbleminstanz.")
		@Test
		void testSolveProblemSimple() {
			assertTrue(hunt.solveProblem("res/sj_p1_probleminstanz.xml", "res/sj_p1_probleminstanz_einfach_test.xml"),
					"\nDie Probleminstanz p1 konnte nicht geloest werden, obwohl eine Loesung erwartet wurde.");
		}

		@DisplayName("Einfacher Test fuer loeseProbleminstanz mit unvollstaendigem Dschungel.")
		@Test
		void testSolveProblemSimpleIncomplete() {
			assertFalse(hunt.solveProblem("res/sj_p1_unvollstaendig.xml", "res/sj_p1_unvollstaendig_einfach_test.xml"),
					"\nDie Probleminstanz p1 wird irgendwie bearbeitet, obwohl der Dschungel der eingelesenen Datei leer ist.");
		}

		@DisplayName("Einfacher Test fuer loeseProbleminstanz, die Ausnahme wirft.")
		@Test
		void testSolveProblemSimpleException() {
			assertFalse(hunt.solveProblem("res/s_p1_unvollstaendig.xml", "res/sj_p1_unvollstaendig_einfach_test.xml"),
					"\nDie Probleminstanz p1 wird irgendwie bearbeitet, obwohl die einzulesende Datei nicht existiert.");
		}

		@DisplayName("Einfacher Test fuer loeseProbleminstanz, die Ausgabedatei hat das falsche Format.")
		@Test
		void testSolveProblemSimpleOutputNotExistent() {
			assertFalse(hunt.solveProblem("res/sj_p1_unvollstaendig.xml", "res/sj_p1_unvollstaendig_einfach_test.l"),
					"\nDie Probleminstanz p1 wird irgendwie bearbeitet, obwohl die angegebene Ausgabedatei das falsche Format hat.");
		}

		@DisplayName("Einfacher Test fuer erzeugeProbleminstanz.")
		@Test
		void testGenerateProblemSimple() {
			assertTrue(
					hunt.generateProblem("res/sj_p1_unvollstaendig.xml", "res/sj_p1_unvollstaendig_einfach_test.xml"),
					"\nEs wurde kein Dschungel erzeugt, obwohl die eingelesene Datei genug Informationen enthaelt, um einen passenden Dschungel zu erzeugen.");
		}

		@DisplayName("Einfacher Test fuer erzeugeProbleminstanz mit Fehler in der Ausfuehrung.")
		@Test
		void testGenerateProblemError() {
			assertFalse(hunt.generateProblem("res/sj_p1_staendig.xml", "res/sj_p1_unvollstaendig_einfach_test.xml"),
					"\nEs wird ein Dschungel erzeugt, obwohl die eingelesene Datei eigentlich nicht genug Informationen enthaelt.");
		}

		@DisplayName("Einfacher Test fuer loeseProbleminstanz, die Ausgabedatei hat das falsche Format.")
		@Test
		void testGenerateProblemSimpleOuputNotExistent() {
			assertFalse(hunt.generateProblem("res/s_p1_unvollstaendig.xml", "res/sj_p1_unvollstaendig_einfach_test.x"),
					"\nDie Probleminstanz p1 wird irgendwie bearbeitet, obwohl die angegebene Ausgabedatei das falsche Format hat.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung ohne Fehler.")
		@Test
		void testExamineSolutionWithoutErrors() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertIterableEquals(error, hunt.examineSolution("res/sj_p1_loesung.xml"),
					"\nDie Pruefung hat Fehler gefunden, obwohl die gegebene Loesung keine Fehler enthaelt.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung, Teil 1.")
		@Test
		void testExamineSolutionOne() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ELEMENTS);
			error.add(ErrorType.ELEMENTS);
			assertIterableEquals(error, hunt.examineSolution("res/sj_p11_loesung_fehler.xml"),
					"\nDie Pruefung hat keine oder nicht die richtigen Fehler gefunden, obwohl die gegebene Loesung Fehler enthaelt.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung, Teil 2.")
		@Test
		void testExamineSolutionTwo() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.USAGE);
			assertIterableEquals(error, hunt.examineSolution("res/sj_p12_loesung_fehler.xml"),
					"\nDie Pruefung hat keine oder nicht die richtigen Fehler gefunden, obwohl die gegebene Loesung Fehler enthaelt.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung, Teil 3.")
		@Test
		void testExamineSolutionThree() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.NEIGHBORHOOD);
			assertIterableEquals(error, hunt.examineSolution("res/sj_p13_loesung_fehler.xml"),
					"\nDie Pruefung hat keine oder nicht die richtigen Fehler gefunden, obwohl die gegebene Loesung Fehler enthaelt.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung, Teil 4.")
		@Test
		void testExamineSolutionFour() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ASSIGNMENT);
			assertIterableEquals(error, hunt.examineSolution("res/sj_p14_loesung_fehler.xml"),
					"\nDie Pruefung hat keine oder nicht die richtigen Fehler gefunden, obwohl die gegebene Loesung Fehler enthaelt.");
		}

		@DisplayName("Einfacher Test fuer pruefeLoesung mit Loesung, Teil 4.")
		@Test
		void testExamineSolutionWrongInputFile() {
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertIterableEquals(error, hunt.examineSolution("res/sj_p1esung_fehler.xml"),
					"\nDie Pruefung hat Fehler gefunden, obwohl die gegebene Eingabedatei nicht existiert.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Parameterized_Tests {
		@DisplayName("Parameterisierter Test fuer loeseProbleminstanz mit gegebenen Probleminstanzen p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void testSolveProblemWithActualProblemsFromFiles(int number) {
			assertTrue(
					hunt.solveProblem("res/sj_p" + number + "_probleminstanz.xml",
							"res/sj_p" + number + "_probleminstanz_test.xml"),
					"\nDie Probleminstanz p" + number
							+ " konnte nicht geloest werden, obwohl eine Loesung erwartet wurde.");
		}

		@DisplayName("Parameterisierter Test fuer loeseProbleminstanz mit gegebenen unvollstaendigen Probleminstanzen p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15 })
		void testSolveProblemWithoutAJungleInTheModel(int number) {
			assertFalse(
					hunt.solveProblem("res/sj_p" + number + "_unvollstaendig.xml",
							"res/sj_p" + number + "_unvollstaendig_test.xml"),
					"\nDie Probleminstanz p" + number
							+ " wird irgendwie bearbeitet, obwohl der Dschungel der eingelesenen Datei leer ist.");
		}

		@DisplayName("Parameterisierter Test fuer bewerteProbleminstanz mit gegebenen Loesungen p1 - p15.")
		@ParameterizedTest
		@ValueSource(ints = { 8, 36, 81, 41, 69, 635, 198, 305, 362, 206, 450, 246, 967, 8734, 23642 })
		void testEvaluateProblemWithActualSolutionsFromFiles(int result) {
			assertEquals(result, hunt.evaluateSolution("res/sj_p" + count + "_loesung.xml"),
					"\nDie Bewertung der Loesung in der Datei 'res/sj_p" + count
							+ "_loesung.xml' entspricht nicht dem erwarteten Wert " + result + ".");
			count++;
		}
	}
}
