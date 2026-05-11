package org.snakehunt.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.snakehunt.ioprocessing.IReader;
import org.snakehunt.ioprocessing.ReaderXML;
import org.snakehunt.model.*;

/**
 * The methods of the SnakeSearch class are tested in the SnakeSearchTest class.
 * Here, a few simple tests are carried out first, which are intended to cover a
 * few important edge situations. In particular, the absence of a jungle in the
 * model is examined. For bug hunting, either the given files
 * sh_pX_problem_instance.xml are used or a jungle is created manually. Note: All
 * classes in the algorithm package are also tested again by the tests of the
 * main component.
 * 
 * @author Philip Redecker
 */
class SnakeSearchTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test with a custom problem instance.")
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
						"\nThe characters of the fields at position " + i + " do not match.");
			}
		}

		@DisplayName("Simple test with a custom problem instance and two solution snakes.")
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
						"\nThe characters of the fields at position " + i + " do not match.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("HAGEN".substring(i, i + 1),
						search.getSolution().getSnakes().get(1).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields at position " + i + " do not match.");
			}
		}

		@DisplayName("Simple test with a read-in problem instance.")
		@Test
		void testSnakeSearchWithReadInProblem() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p1_problem_instance.xml");
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
						"\nThe characters of the fields at position " + i + " do not match.");
			}
		}

		@DisplayName("Simple test with a read-in problem instance with multiple solution snakes.")
		@Test
		void testSnakeSearchWithReadInProblemMultipleSnakes() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p2_problem_instance.xml");
			IModel model = readerXML.getTransferredModel();
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESES".substring(i, i + 1),
						search.getSolution().getSnakes().get(0).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the first snake at position " + i + " do not match.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESEN".substring(i, i + 1),
						search.getSolution().getSnakes().get(1).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the second snake at position " + i
								+ " do not match.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESER".substring(i, i + 1),
						search.getSolution().getSnakes().get(2).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the third snake at position " + i
								+ " do not match.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("DIESE".substring(i, i + 1),
						search.getSolution().getSnakes().get(3).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the fourth snake at position " + i
								+ " do not match.");
			}
			for (int i = 0; i < 4; i++) {
				assertEquals("DIES".substring(i, i + 1),
						search.getSolution().getSnakes().get(4).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the fifth snake at position " + i
								+ " do not match.");
			}
			for (int i = 0; i < 3; i++) {
				assertEquals("DIE".substring(i, i + 1),
						search.getSolution().getSnakes().get(5).getElements().get(i).getField().getCharacter(),
						"\nThe characters of the fields of the sixth snake at position " + i
								+ " do not match.");
			}
		}

		@DisplayName("Simple test with an empty problem instance.")
		@Test
		void testSnakeSearchWithEmptyProblem() {
			IModel model = new ProblemModel();
			Jungle exampleJungle = new Jungle(3, 3, "ABC", 1);
			model.setJungle(exampleJungle);
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(model),
					"\nPassing an incomplete model does not trigger an exception.");
		}

		@DisplayName("Simple test with an empty read-in problem instance.")
		@Test
		void testSnakeSearchWithReadInEmptyProblem() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p1_incomplete.xml");
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(readerXML.getTransferredModel()),
					"\nPassing an incomplete model does not trigger an exception.");
		}
	}
}

