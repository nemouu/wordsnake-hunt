package org.snakehunt.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.snakehunt.model.*;

/**
 * The methods of the SnakeSearchUtil class are tested in the
 * SnakeSearchUtilTest class. Here, a few simple tests are first carried out,
 * and for this purpose, some jungles or problem instances are created
 * manually. The sorting is addressed and also checked here. Note: All classes
 * in the algorithm package are also tested again by the tests of the main
 * component.
 * 
 * @author Philip Redecker
 */
class SnakeSearchUtilTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for createValidStartingFields with a snake that has elements in the jungle.")
		@Test
		void testCreateValidStartingFields() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[2][3].setCharacter("F");
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][1].setCharacter("F");
			List<Field> startingFields = new ArrayList<Field>();
			startingFields.add(exampleJungle.getFields()[2][3]);
			startingFields.add(exampleJungle.getFields()[3][4]);
			startingFields.add(exampleJungle.getFields()[2][1]);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(
					startingFields.size() == util.createValidStartingFields(snake.getType()).size()
							&& startingFields.containsAll(util.createValidStartingFields(snake.getType()))
							&& util.createValidStartingFields(snake.getType()).containsAll(startingFields),
					"\nThe list of starting fields does not match the expected list.");
		}

		@DisplayName("Simple test for createValidStartingFields with a snake that has no elements in the jungle.")
		@Test
		void testCreateValidStartingFieldsEmpty() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(3, 4, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			List<Field> startingFields = new ArrayList<Field>();
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertIterableEquals(startingFields, util.createValidStartingFields(snake.getType()),
					"\nThe list of starting fields was not empty, but an empty list was expected because no fields with a permissible character exist in the jungle.");
		}

		@DisplayName("Simple test for createValidStartingFields with a snake that has elements in the jungle, but some of them have a usability of 0.")
		@Test
		void testCreateValidStartingFieldsUsage() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[2][3].setCharacter("F");
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][1].setCharacter("F");
			exampleJungle.getFields()[2][1].setUsage(0);
			exampleJungle.getFields()[2][3].setUsage(0);
			List<Field> startingFields = new ArrayList<Field>();
			startingFields.add(exampleJungle.getFields()[3][4]);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(
					startingFields.size() == util.createValidStartingFields(snake.getType()).size()
							&& startingFields.containsAll(util.createValidStartingFields(snake.getType()))
							&& util.createValidStartingFields(snake.getType()).containsAll(startingFields),
					"\nThe list of starting fields does not match the expected list. Fields with usability 0 were probably included.");
		}

		@DisplayName("Simple test for createValidNeighbors with a snake that has elements in the jungle.")
		@Test
		void testCreateValidNeighborFields() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][4].setCharacter("E");
			exampleJungle.getFields()[2][3].setCharacter("E");
			SnakeElement previousElement = new SnakeElement(exampleJungle.getFields()[3][4]);
			snake.addElement(previousElement);
			List<Field> neighborFields = new ArrayList<Field>();
			neighborFields.add(exampleJungle.getFields()[2][4]);
			neighborFields.add(exampleJungle.getFields()[2][3]);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(
					neighborFields.size() == util.createValidNeighbors(previousElement, snake).size()
							&& neighborFields.containsAll(util.createValidNeighbors(previousElement, snake))
							&& util.createValidNeighbors(previousElement, snake).containsAll(neighborFields),
					"\nThe list with neighboring fields does not match the expected list.");
		}

		@DisplayName("Simple test for createValidNeighbors with a snake that has no further elements in the jungle.")
		@Test
		void testCreateValidNeighborFieldsEmpty() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[3][4].setCharacter("F");
			SnakeElement previousElement = new SnakeElement(exampleJungle.getFields()[3][4]);
			snake.addElement(previousElement);
			List<Field> neighborFields = new ArrayList<Field>();
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertIterableEquals(neighborFields, util.createValidNeighbors(previousElement, snake),
					"\nThe list with starting fields was not empty, but an empty list was expected because no fields with a permissible character exist in the jungle.");
		}

		@DisplayName("Simple test for createValidNeighbors with a snake that has further elements in the jungle, but some of them have a usability of 0.")
		@Test
		void testCreateValidNeighborFieldsUsage() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][4].setCharacter("E");
			exampleJungle.getFields()[2][3].setCharacter("E");
			SnakeElement previousElement = new SnakeElement(exampleJungle.getFields()[3][4]);
			snake.addElement(previousElement);
			List<Field> neighborFields = new ArrayList<Field>();
			neighborFields.add(exampleJungle.getFields()[2][4]);
			exampleJungle.getFields()[2][3].setUsage(0);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(
					neighborFields.size() == util.createValidNeighbors(previousElement, snake).size()
							&& neighborFields.containsAll(util.createValidNeighbors(previousElement, snake))
							&& util.createValidNeighbors(previousElement, snake).containsAll(neighborFields),
					"\nThe list with neighboring fields does not match the expected list.");
		}

		@DisplayName("Simple test for createValidSnakeTypes.")
		@Test
		void testCreateValidSnakeTypes() {
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			SnakeType type1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType type2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType type3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType type4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType type5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType type6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			model.addSnakeType(type1);
			model.addSnakeType(type2);
			model.addSnakeType(type3);
			model.addSnakeType(type4);
			model.addSnakeType(type5);
			model.addSnakeType(type6);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(
					model.getSnakeTypes().size() == util.createValidSnakeTypes().size()
							&& model.getSnakeTypes().containsAll(util.createValidSnakeTypes())
							&& util.createValidSnakeTypes().containsAll(model.getSnakeTypes()),
					"\nThe list with snake types does not match the expected list.");
		}

		@DisplayName("Simple test for createValidSnakeTypes with empty snake types.")
		@Test
		void testCreateValidSnakeTypesEmpty() {
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			List<SnakeType> types = new ArrayList<SnakeType>();
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertIterableEquals(types, util.createValidSnakeTypes(),
					"\nThe list with snake types was not empty, but an empty list was expected because no snake types exist in the model.");
		}

		@DisplayName("Simple test for createValidSnakeTypes with sorting.")
		@Test
		void testCreateValidSnakeTypesSort() {
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			SnakeType type1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType type2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType type3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType type4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType type5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType type6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			model.addSnakeType(type1);
			model.addSnakeType(type2);
			model.addSnakeType(type3);
			model.addSnakeType(type4);
			model.addSnakeType(type5);
			model.addSnakeType(type6);
			List<SnakeType> types = new ArrayList<SnakeType>();
			types.add(type2);
			types.add(type3);
			types.add(type4);
			types.add(type5);
			types.add(type1);
			types.add(type6);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(types.size() == util.createValidSnakeTypes().size(),
					"\nThe list of obtained snake types does not have the correct size.");
			assertIterableEquals(types, util.createValidSnakeTypes(),
					"\nThe list of obtained snake types does not have the correct sorting.");
		}

		@DisplayName("Simple test for createValidNeighbors with a snake that has elements in the jungle, sorting is observed.")
		@Test
		void testCreateValidNeighborFieldsSort() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][4].setCharacter("E");
			exampleJungle.getFields()[2][3].setCharacter("E");
			exampleJungle.getFields()[2][3].setPoints(7);
			SnakeElement previousElement = new SnakeElement(exampleJungle.getFields()[3][4]);
			snake.addElement(previousElement);
			List<Field> neighborFields = new ArrayList<Field>();
			neighborFields.add(exampleJungle.getFields()[2][3]);
			neighborFields.add(exampleJungle.getFields()[2][4]);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(neighborFields.size() == util.createValidNeighbors(previousElement, snake).size(),
					"\nThe size of the list with neighboring fields does not match the size of the expected list.");
			assertIterableEquals(neighborFields, util.createValidNeighbors(previousElement, snake),
					"\nThe list with neighboring fields does not have the expected sorting.");
		}

		@DisplayName("Simple test for createValidStartingFields with a snake that has elements in the jungle, sorting is observed.")
		@Test
		void testCreateValidStartingFieldsSort() {
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[2][3].setCharacter("F");
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][1].setCharacter("F");
			List<Field> startingFields = new ArrayList<Field>();
			startingFields.add(exampleJungle.getFields()[2][1]);
			startingFields.add(exampleJungle.getFields()[2][3]);
			startingFields.add(exampleJungle.getFields()[3][4]);
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(startingFields.size() == util.createValidStartingFields(snake.getType()).size(),
					"\nThe list of starting fields does not match the expected list.");
			assertIterableEquals(startingFields, util.createValidStartingFields(snake.getType()),
					"\nThe list with starting fields does not have the correct sorting.");
		}

		@DisplayName("Simple test for createValidStartingFields with a snake that has high points. Elements in the jungle are present, sorting is observed.")
		@Test
		void testCreateValidStartingFieldsSortHighPoints() {
			SnakeType type1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 100, 3);
			SnakeType type2 = new SnakeType("A1", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType type3 = new SnakeType("A2", new DistanceNeighborhood(1), "JUNIT", 2, 3);
			Jungle exampleJungle = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a");
				}
			}
			exampleJungle.getFields()[2][3].setCharacter("F");
			exampleJungle.getFields()[3][4].setCharacter("F");
			exampleJungle.getFields()[2][0].setCharacter("F");
			List<Field> startingFields = new ArrayList<Field>();
			startingFields.add(exampleJungle.getFields()[3][4]);
			startingFields.add(exampleJungle.getFields()[2][0]);
			startingFields.add(exampleJungle.getFields()[2][3]);
			IModel model = new ProblemModel();
			model.addSnakeType(type1);
			model.addSnakeType(type2);
			model.addSnakeType(type3);
			model.setJungle(exampleJungle);
			SnakeSearchUtil util = new SnakeSearchUtil(model);
			assertTrue(startingFields.size() == util.createValidStartingFields(type1).size(),
					"\nThe list of starting fields does not match the expected list.");
			assertIterableEquals(startingFields, util.createValidStartingFields(type1),
					"\nThe list with starting fields does not have the correct sorting.");
		}
	}
}