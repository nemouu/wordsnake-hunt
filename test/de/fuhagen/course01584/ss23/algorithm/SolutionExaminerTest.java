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
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			fehler.add(ErrorType.ELEMENTS);
			fehler.add(ErrorType.ELEMENTS);
			assertEquals(fehler, jagd.examineSolution("res/sj_p11_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 2.")
		@Test
		void testExamineSolutionExampleTwo() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			fehler.add(ErrorType.USAGE);
			assertEquals(fehler, jagd.examineSolution("res/sj_p12_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 3.")
		@Test
		void testExamineSolutionExampleThree() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			fehler.add(ErrorType.NEIGHBORHOOD);
			assertEquals(fehler, jagd.examineSolution("res/sj_p13_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 4.")
		@Test
		void testExamineSolutionExampleFour() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			fehler.add(ErrorType.ASSIGNMENT);
			assertEquals(fehler, jagd.examineSolution("res/sj_p14_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn keine Fehler in der Loesung sind.")
		@Test
		void testExamineSolutionExampleNoErrors() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			assertEquals(fehler, jagd.examineSolution("res/sj_p11_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist.")
		@Test
		void testExamineSolutionExampleNoSolution() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<ErrorType> fehler = new ArrayList<ErrorType>();
			assertEquals(fehler, jagd.examineSolution("res/sj_p11_probleminstanz.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist mit Ausnahme.")
		@Test
		void testExamineSolutionExampleNoSolutionException() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p11_probleminstanz.xml");
			assertThrows(IllegalArgumentException.class, () -> new SolutionExaminer(xmlLeser.getTransferredModel()),
					"\nEs wird ein Modell ohne Loesung uebergeben aber es wird keine Ausnahme ausgeloest.");
		}
	}
}