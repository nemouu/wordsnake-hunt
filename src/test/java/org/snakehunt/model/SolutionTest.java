package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * In the SolutionTest class, the methods of the Solution class are tested.
 * A few simple tests are performed to cover some important edge cases.
 * Additionally, the toString method is tested multiple times.
 * There are also a series of parameterized tests that test the methods in a normal way.
 * 
 * @author Philip Redecker
 */
class SolutionTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test for getSnakeWithSnakeTypeID, part 1")
		@Test
		void testGetSnakeWithIDOne() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(exampleSolution.getSnakeWithSnakeTypeID("A0").getType().toString(),
					"ID=A0, Neighborhood Structure=Distance, String=FERNUNI, Points=1, Amount=1",
					"\nThe expected result was not returned.");
		}

		@DisplayName("Simple test for getSnakeWithSnakeTypeID, part 2")
		@Test
		void testGetSnakeWithIDTwo() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(exampleSolution.getSnakeWithSnakeTypeID("A1").getType().toString(),
					"ID=A1, Neighborhood Structure=Distance, String=JUNITTEST, Points=2, Amount=4",
					"\nThe expected result was not returned.");
		}

		@DisplayName("Simple test for getNumberOfDifferentSnakeTypes, part 1")
		@Test
		void testAmountSnakeTypesOne() {
			Solution exampleSolution = new Solution();
			assertEquals(0, exampleSolution.getNumberOfDifferentSnakeTypes(),
					"\nThe calculated number of snake types does not match the actual number.");
		}

		@DisplayName("Simple test for getNumberOfDifferentSnakeTypes, part 2")
		@Test
		void testAmountSnakeTypesTwo() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake1);
			exampleSolution.addSnake(snake2);
			assertEquals(2, exampleSolution.getNumberOfDifferentSnakeTypes(),
					"\nThe calculated number of snake types does not match the actual number.");
		}

		@DisplayName("Simple test for addSnake.")
		@Test
		void testAddSnake() {
			Solution exampleSolution = new Solution();
			Snake snake = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			exampleSolution.addSnake(snake);
			assertEquals("A0", exampleSolution.getSnakes().get(0).getType().getId(),
					"\nThe obtained snake ID does not match the actual ID.");
			assertEquals("Distance", exampleSolution.getSnakes().get(0).getType().getStructure().getType(),
					"\nThe obtained neighborhood structure does not match the actual structure.");
			assertEquals("FERNUNI", exampleSolution.getSnakes().get(0).getType().getSigns(),
					"\nThe obtained snake string does not match the actual string.");
			assertEquals(2, exampleSolution.getSnakes().get(0).getType().getPoints(),
					"\nThe calculated snake points do not match the actual points.");
			assertEquals(3, exampleSolution.getSnakes().get(0).getType().getAmount(),
					"\nThe calculated number of snakes does not match the actual number.");
		}

		@DisplayName("Simple test for removeLastSnake, part 1.")
		@Test
		void testRemoveLastSnakeOne() {
			Solution exampleSolution = new Solution();
			Snake snake1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Snake snake2 = new Snake(new SnakeType("A1", new DistanceNeighborhood(3), "JUNITTEST", 2, 4));
			exampleSolution.addSnake(snake2);
			exampleSolution.addSnake(snake1);
			exampleSolution.removeLastSnake();
			assertEquals("A1", exampleSolution.getSnakes().get(0).getType().getId(),
					"\nThe obtained snake ID does not match the actual ID.");
			assertEquals("Distance", exampleSolution.getSnakes().get(0).getType().getStructure().getType(),
					"\nThe obtained neighborhood structure does not match the actual structure.");
			assertEquals("JUNITTEST", exampleSolution.getSnakes().get(0).getType().getSigns(),
					"\nThe obtained snake string does not match the actual string.");
			assertEquals(2, exampleSolution.getSnakes().get(0).getType().getPoints(),
					"\nThe calculated snake points do not match the actual points.");
			assertEquals(4, exampleSolution.getSnakes().get(0).getType().getAmount(),
					"\nThe calculated number of snakes does not match the actual number.");
		}

		@DisplayName("Simple test for removeLastSnake, part 2.")
		@Test
		void testRemoveLastSnakeTwo() {
			Solution exampleSolution = new Solution();
			exampleSolution.removeLastSnake();
			assertEquals(0, exampleSolution.getSnakes().size(),
					"\nThe snake list does not have size 0, although no snakes are included.");
		}
	}
}