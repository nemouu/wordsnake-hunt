package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.fuhagen.course01584.ss23.ioprocessing.IReader;
import de.fuhagen.course01584.ss23.ioprocessing.ReaderXML;
import de.fuhagen.course01584.ss23.model.*;

/**
 * In der Klasse SchlangenSucheTest werden die Methoden der Klasse
 * SchlangenSuche getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken. Insbesondere das Fehlen eines Dschungels im Modell wird
 * untersucht. Fuer die Fehlerfindung werden entweder die gegebenen Dateien
 * <code>sj_pX_probleminstanz.xml</code> genutzt oder es wird ein Dschungel
 * manuell erstellt. Hinweis: Alle Klassen des Algorithmuspaketes werden auch
 * noch einmal durch die Tests der Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class SnakeSearchTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test mit selbst erstellter Probleminstanz.")
		@Test
		void testSnakeSearchWithCustomProblem() {
			Double[] time = { 30.0 };
			String unit = "s";
			Jungle exampleJungle = new Jungle(4, 5, "ABCDEFG", 1);
			exampleJungle.setField(new Field("F0", 0, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F1", 0, 1, 1, 1, "O"));
			exampleJungle.setField(new Field("F2", 0, 2, 1, 1, "O"));
			exampleJungle.setField(new Field("F3", 0, 3, 1, 1, "O"));
			exampleJungle.setField(new Field("F4", 0, 4, 1, 1, "O"));
			exampleJungle.setField(new Field("F5", 1, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F6", 1, 1, 1, 1, "J"));
			exampleJungle.setField(new Field("F7", 1, 2, 1, 1, "U"));
			exampleJungle.setField(new Field("F8", 1, 3, 1, 1, "O"));
			exampleJungle.setField(new Field("F9", 1, 4, 1, 1, "O"));
			exampleJungle.setField(new Field("F10", 2, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F11", 2, 1, 1, 1, "O"));
			exampleJungle.setField(new Field("F12", 2, 2, 1, 1, "O"));
			exampleJungle.setField(new Field("F13", 2, 3, 1, 1, "N"));
			exampleJungle.setField(new Field("F14", 2, 4, 1, 1, "O"));
			exampleJungle.setField(new Field("F15", 3, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F16", 3, 1, 1, 1, "O"));
			exampleJungle.setField(new Field("F17", 3, 2, 1, 1, "O"));
			exampleJungle.setField(new Field("F18", 3, 3, 1, 1, "T"));
			exampleJungle.setField(new Field("F19", 3, 4, 1, 1, "I"));
			SnakeType type = new SnakeType("A0", new DistanceNeighborhood(1), "JUNIT", 1, 1);
			IModel model = new ProblemModel();
			model.setTime(time);
			model.setUnitOfTime(unit);
			model.setJungle(exampleJungle);
			model.addSnakeType(type);
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			for (int i = 0; i < 5; i++) {
				assertEquals("JUNIT".substring(i, i + 1),
						search.getSolution().getSnakes().get(0).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit selbst erstellter Probleminstanz und zwei Loesungsschlangen.")
		@Test
		void testSnakeSearchWithCustomProblemAndTwoSnakes() {
			Double[] time = { 30.0 };
			String unit = "s";
			Jungle exampleJungle = new Jungle(4, 5, "ABCDEFG", 1);
			exampleJungle.setField(new Field("F0", 0, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F1", 0, 1, 1, 1, "O"));
			exampleJungle.setField(new Field("F2", 0, 2, 1, 1, "O"));
			exampleJungle.setField(new Field("F3", 0, 3, 1, 1, "O"));
			exampleJungle.setField(new Field("F4", 0, 4, 1, 1, "N"));
			exampleJungle.setField(new Field("F5", 1, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F6", 1, 1, 1, 1, "J"));
			exampleJungle.setField(new Field("F7", 1, 2, 1, 1, "U"));
			exampleJungle.setField(new Field("F8", 1, 3, 1, 1, "E"));
			exampleJungle.setField(new Field("F9", 1, 4, 1, 1, "O"));
			exampleJungle.setField(new Field("F10", 2, 0, 1, 1, "H"));
			exampleJungle.setField(new Field("F11", 2, 1, 1, 1, "A"));
			exampleJungle.setField(new Field("F12", 2, 2, 1, 1, "G"));
			exampleJungle.setField(new Field("F13", 2, 3, 1, 1, "N"));
			exampleJungle.setField(new Field("F14", 2, 4, 1, 1, "O"));
			exampleJungle.setField(new Field("F15", 3, 0, 1, 1, "O"));
			exampleJungle.setField(new Field("F16", 3, 1, 1, 1, "O"));
			exampleJungle.setField(new Field("F17", 3, 2, 1, 1, "O"));
			exampleJungle.setField(new Field("F18", 3, 3, 1, 1, "T"));
			exampleJungle.setField(new Field("F19", 3, 4, 1, 1, "I"));
			SnakeType type1 = new SnakeType("A0", new DistanceNeighborhood(1), "JUNIT", 1, 1);
			SnakeType type2 = new SnakeType("A1", new DistanceNeighborhood(1), "HAGEN", 1, 1);
			IModel model = new ProblemModel();
			model.setTime(time);
			model.setUnitOfTime(unit);
			model.setJungle(exampleJungle);
			model.addSnakeType(type1);
			model.addSnakeType(type2);
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			for (int i = 0; i < 5; i++) {
				assertEquals("JUNIT".substring(i, i + 1),
						search.getSolution().getSnakes().get(0).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("HAGEN".substring(i, i + 1),
						search.getSolution().getSnakes().get(1).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit eingelesener Probleminstanz.")
		@Test
		void testSnakeSearchWithReadInProblem() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p1_probleminstanz.xml");
			IModel model = readerXML.getTransferredModel();
			SnakeSearch search = new SnakeSearch(model);
			SnakeType type = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeElement element1 = new SnakeElement(model.getJungle().getFields()[0][0]);
			SnakeElement element2 = new SnakeElement(model.getJungle().getFields()[0][1]);
			SnakeElement element3 = new SnakeElement(model.getJungle().getFields()[0][2]);
			SnakeElement element4 = new SnakeElement(model.getJungle().getFields()[0][3]);
			SnakeElement element5 = new SnakeElement(model.getJungle().getFields()[1][3]);
			SnakeElement element6 = new SnakeElement(model.getJungle().getFields()[1][2]);
			SnakeElement element7 = new SnakeElement(model.getJungle().getFields()[1][1]);
			Snake snake = new Snake(type);
			snake.addElement(element1);
			snake.addElement(element2);
			snake.addElement(element3);
			snake.addElement(element4);
			snake.addElement(element5);
			snake.addElement(element6);
			snake.addElement(element7);
			search.searchSnakes();
			for (int i = 0; i < 7; i++) {
				assertEquals(snake.getElements().get(i).getField().getCharacter(),
						search.getSolution().getSnakes().get(0).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit eingelesener Probleminstanz mit mehreren Loesungsschlangen.")
		@Test
		void testSnakeSearchWithReadInProblemMultipleSnakes() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p2_probleminstanz.xml");
			IModel model = readerXML.getTransferredModel();
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESES".substring(i, i + 1),
						search.getSolution().getSnakes().get(0).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der ersten Schlange an der Stelle " + i + " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESEN".substring(i, i + 1),
						search.getSolution().getSnakes().get(1).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der zweiten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESER".substring(i, i + 1),
						search.getSolution().getSnakes().get(2).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der dritten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("DIESE".substring(i, i + 1),
						search.getSolution().getSnakes().get(3).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der vierten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 4; i++) {
				assertEquals("DIES".substring(i, i + 1),
						search.getSolution().getSnakes().get(4).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der fuenften Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 3; i++) {
				assertEquals("DIE".substring(i, i + 1),
						search.getSolution().getSnakes().get(5).getElements().get(i).getField().getCharacter(),
						"\nDie Zeichen der Felder der sechsten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit leerer Probleminstanz.")
		@Test
		void testSnakeSearchWithEmptyProblem() {
			IModel model = new ProblemModel();
			Jungle exampleJungle = new Jungle(3, 3, "ABC", 1);
			model.setJungle(exampleJungle);
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(model),
					"\nDas Uebergeben eines unvollstaendigen Modelles loest keine Ausnahme aus.");
		}

		@DisplayName("Einfacher Test mit leerer eingelesener Probleminstanz.")
		@Test
		void testSnakeSearchWithReadInEmptyProblem() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p1_unvollstaendig.xml");
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(readerXML.getTransferredModel()),
					"\nDas Uebergeben eines unvollstaendigen Modelles loest keine Ausnahme aus.");
		}
	}
}
