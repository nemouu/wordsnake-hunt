package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.fuhagen.course01584.ss23.ioprocessing.IReader;
import de.fuhagen.course01584.ss23.ioprocessing.ReaderXML;
import de.fuhagen.course01584.ss23.main.SnakeHunt;
import de.fuhagen.course01584.ss23.main.SnakeHuntAPI;
import de.fuhagen.course01584.ss23.main.SnakeHuntAPI.ErrorType;

/**
 * In der Klasse LoesungsPrueferTest werden die Methoden der Klasse
 * LoesungsPruefer getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken. Insbesondere das Fehlen einer Loesung im Modell oder auch die
 * Situation, dass keine Fehler gefunden werden, wird untersucht. Fuer die
 * Fehlerfindung werden die gegebenen fehlerhaften Dateien
 * <code>sj_pX_loesung_fehler.xml</code> genutzt. Hinweis: Alle Klassen des
 * Algorithmuspaketes werden auch noch einmal durch die Tests der
 * Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class SolutionExaminerTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 1.")
		@Test
		void testExamineSolutionExampleOne() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ELEMENTS);
			error.add(ErrorType.ELEMENTS);
			assertEquals(error, hunt.examineSolution("res/sj_p11_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 2.")
		@Test
		void testExamineSolutionExampleTwo() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.USAGE);
			assertEquals(error, hunt.examineSolution("res/sj_p12_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 3.")
		@Test
		void testExamineSolutionExampleThree() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.NEIGHBORHOOD);
			assertEquals(error, hunt.examineSolution("res/sj_p13_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 4.")
		@Test
		void testExamineSolutionExampleFour() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			error.add(ErrorType.ASSIGNMENT);
			assertEquals(error, hunt.examineSolution("res/sj_p14_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn keine Fehler in der Loesung sind.")
		@Test
		void testExamineSolutionExampleNoErrors() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertEquals(error, hunt.examineSolution("res/sj_p11_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist.")
		@Test
		void testExamineSolutionExampleNoSolution() {
			SnakeHuntAPI hunt = new SnakeHunt();
			List<ErrorType> error = new ArrayList<ErrorType>();
			assertEquals(error, hunt.examineSolution("res/sj_p11_probleminstanz.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist mit Ausnahme.")
		@Test
		void testExamineSolutionExampleNoSolutionException() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p11_probleminstanz.xml");
			assertThrows(IllegalArgumentException.class, () -> new SolutionExaminer(readerXML.getTransferredModel()),
					"\nEs wird ein Modell ohne Loesung uebergeben aber es wird keine Ausnahme ausgeloest.");
		}
	}
}