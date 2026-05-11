package org.snakehunt.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import org.snakehunt.ioprocessing.IReader;
import org.snakehunt.ioprocessing.ReaderXML;
import org.snakehunt.model.*;

/**
 * The public methods of the JungleGenerator class are tested in the
 * JungleGeneratorTest class. Due to the structure of the methods in this
 * class, only a series of simple tests are performed. Error detection is also
 * tested here, and it should be noted that a jungle is often output to the
 * console to allow for an additional manual test. Note: All classes in the
 * algorithm package are also tested again by the tests of the main component.
 * 
 * @author Philip Redecker
 */
class JungleGeneratorTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for jungle generator without snakes")
		@Test
		void testJungleGeneratorTooManyReadInSnakes() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p11_incomplete.xml");
			assertThrows(IllegalArgumentException.class, () -> new JungleGenerator(readerXML.getTransferredModel()),
					"\nNo exception is thrown, even though more snakes should be distributed in the model than there is room in the model's jungle.");
		}

		@DisplayName("Simple test for jungle generator without snakes")
		@Test
		void testJungleGenerator() throws Exception {
			Jungle exampleJungle = new Jungle(5, 5, "ABCDEFG", 1);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			boolean containsRightCharacters = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFG".contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						containsRightCharacters = false;
					}
				}
			}
			System.out.println("First simple test (visual confirmation: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nThe generated jungle does not contain the correct characters.");
		}

		@DisplayName("Simple test for jungle generator with snakes")
		@Test
		void testJungleGeneratorWithSnakes() throws Exception {
			Jungle exampleJungle = new Jungle(5, 5, "X", 1);
			SnakeType type = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel model = new ProblemModel();
			model.addSnakeType(type);
			model.setJungle(exampleJungle);
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			boolean containsRightCharacters = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"XFERNUNI".contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						containsRightCharacters = false;
					}
				}
			}
			System.out.println("Second simple test (visual confirmation: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nThe generated jungle does not contain the correct characters.");
		}

		@DisplayName("Simple test for jungle generator with read-in data, part 1.")
		@Test
		void testJungleGeneratorWithReadInDataOne() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p2_incomplete.xml");
			JungleGenerator generator = new JungleGenerator(readerXML.getTransferredModel());
			generator.generateJungle();
			boolean containsRightCharacters = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						containsRightCharacters = false;
					}
				}
			}
			System.out.println("Third simple test (visual confirmation: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nThe generated jungle does not contain the correct characters.");
		}

		@DisplayName("Simple test for jungle generator with read-in data, part 2.")
		@Test
		void testJungleGeneratorWithReadInDataTwo() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p4_incomplete.xml");
			JungleGenerator generator = new JungleGenerator(readerXML.getTransferredModel());
			generator.generateJungle();
			boolean containsRightCharacters = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ÄABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						containsRightCharacters = false;
					}
				}
			}
			System.out.println("Fourth simple test (visual confirmation: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nThe generated jungle does not contain the correct characters.");
		}

		@DisplayName("Simple test for jungle generator with read-in data, part 3.")
		@Test
		void testJungleGeneratorWithReadInDataThree() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p2_solution.xml");
			JungleGenerator generator = new JungleGenerator(readerXML.getTransferredModel());
			generator.generateJungle();
			boolean containsRightCharacters = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						containsRightCharacters = false;
					}
				}
			}
			System.out.println("Fifth simple test (visual confirmation: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nThe generated jungle does not contain the correct characters.");
		}

		@DisplayName("Simple test for jungle generator with snakes and solution by Snake Search.")
		@Test
		void testJungleGeneratorWithSnakesAndSnakeSearchOne() throws Exception {
			Double[] time = { 10.0 };
			String unit = "s";
			Jungle exampleJungle = new Jungle(5, 5, "X", 1);
			SnakeType type = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel model = new ProblemModel();
			model.setUnitOfTime(unit);
			model.setTime(time);
			model.addSnakeType(type);
			model.setJungle(exampleJungle);
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			model.setJungle(generator.getNewJungle());
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			String solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(0).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("FERNUNI", solution,
					"\nThe snake search does not find the same snake that the jungle generator had previously distributed.");
		}

		@DisplayName("Simple test for jungle generator with snakes and solution by Snake Search.")
		@Test
		void testJungleGeneratorWithSnakesAndSnakeSearchTwo() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p4_incomplete.xml");
			IModel model = readerXML.getTransferredModel();
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			model.setJungle(generator.getNewJungle());
			SnakeSearch search = new SnakeSearch(model);
			search.searchSnakes();
			String solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(0).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("ÄSKULAPNATTER", solution,
					"\nThe snake search does not find the same snake that the jungle generator had previously distributed.");
			solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(1).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("SCHLINGNATTER", solution,
					"\nThe snake search does not find the same snake that the jungle generator had previously distributed.");
			solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(2).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("RINGELNATTER", solution,
					"\nThe snake search does not find the same snake that the jungle generator had previously distributed.");
		}

		@DisplayName("Simple test for jungle generator with a model that is too empty.")
		@Test
		void testJungleGeneratorWithTooEmptyModel() throws Exception {
			Jungle exampleJungle = new Jungle();
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			assertEquals("", generator.getNewJungle().toString(),
					"\nThe empty string was not generated, although there was not enough information in the jungle.");
		}
	}
}
