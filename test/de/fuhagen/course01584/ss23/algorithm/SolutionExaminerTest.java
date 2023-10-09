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
import de.fuhagen.course01584.ss23.main.SnakeHuntAPI.Fehlertyp;

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
	class Einfache_Tests {
		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 1.")
		@Test
		void testePruefeLoesungBeispielEins() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			fehler.add(Fehlertyp.GLIEDER);
			fehler.add(Fehlertyp.GLIEDER);
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p11_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 2.")
		@Test
		void testePruefeLoesungBeispielZwei() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			fehler.add(Fehlertyp.VERWENDUNG);
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p12_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 3.")
		@Test
		void testePruefeLoesungBeispielDrei() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			fehler.add(Fehlertyp.NACHBARSCHAFT);
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p13_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, Teil 4.")
		@Test
		void testePruefeLoesungBeispielVier() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			fehler.add(Fehlertyp.ZUORDNUNG);
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p14_loesung_fehler.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn keine Fehler in der Loesung sind.")
		@Test
		void testePruefeLoesungBeispielKeineFehler() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p11_loesung.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist.")
		@Test
		void testePruefeLoesungBeispielKeineLoesung() {
			SnakeHuntAPI jagd = new SnakeHunt();
			List<Fehlertyp> fehler = new ArrayList<Fehlertyp>();
			assertEquals(fehler, jagd.pruefeLoesung("res/sj_p11_probleminstanz.xml"),
					"\nDie Loesungpruefung findet nicht die erwarteten Fehler.");
		}

		@DisplayName("Einfacher Test fuer Loesungspruefer, wenn kein Loesung da ist mit Ausnahme.")
		@Test
		void testePruefeLoesungBeispielKeineLoesungAusnahme() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p11_probleminstanz.xml");
			assertThrows(IllegalArgumentException.class, () -> new SolutionExaminer(xmlLeser.getUebergebenesModell()),
					"\nEs wird ein Modell ohne Loesung uebergeben aber es wird keine Ausnahme ausgeloest.");
		}
	}
}