package org.snakehunt.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * In the SnakeTest class, the methods of the Snake class are tested.
 * A few simple tests are performed to cover some important edge cases.
 * To provide a sample snake for all testing methods, it is initialized in a @BeforeAll method.
 * 
 * @author Philip Redecker
 */
class SnakeTest {
	private static Snake exampleSnake;

	@BeforeAll
	static void setUp() {
		SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
		SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
		SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
		SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
		SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
		SnakeElement element6 = new SnakeElement(new Field(1, 5, 1));
		List<SnakeElement> elements = new ArrayList<SnakeElement>();
		elements.add(element1);
		elements.add(element2);
		elements.add(element3);
		elements.add(element4);
		elements.add(element5);
		elements.add(element6);
		exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Simple test of characterOfNextElement, part 1.")
		@Test
		void testCharacterOfNextElementOne() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("O", exampleSnake.characterOfNextElement(),
					"\nThe returned next element does not match the actual next element.");
		}

		@DisplayName("Simple test of characterOfNextElement, part 2.")
		@Test
		void testCharacterOfNextElementTwo() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("L", exampleSnake.characterOfNextElement(),
					"\nThe returned next element does not match the actual next element.");
		}

		@DisplayName("Simple test of characterOfNextElement, part 3.")
		@Test
		void testCharacterOfNextElementThree() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			elements.add(element5);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("S", exampleSnake.characterOfNextElement(),
					"\nThe returned next element does not match the actual next element.");
		}

		@DisplayName("Simple test of characterOfNextElement, part 4.")
		@Test
		void testCharacterOfNextElementFour() {
			SnakeElement element1 = new SnakeElement(new Field(0, 1, 2));
			SnakeElement element2 = new SnakeElement(new Field(0, 3, 1));
			SnakeElement element3 = new SnakeElement(new Field(0, 4, 1));
			SnakeElement element4 = new SnakeElement(new Field(1, 3, 1));
			SnakeElement element5 = new SnakeElement(new Field(0, 6, 1));
			SnakeElement element6 = new SnakeElement(new Field(1, 5, 1));
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(element1);
			elements.add(element2);
			elements.add(element3);
			elements.add(element4);
			elements.add(element5);
			elements.add(element6);
			Snake exampleSnake = new Snake(new SnakeType("A0", new DistanceNeighborhood(), "HALLOS", 3, 1), elements);
			assertEquals("", exampleSnake.characterOfNextElement(),
					"\nThe returned next element does not match the actual next element.");
		}

		@DisplayName("Simple test of characterOfLastElement")
		@Test
		void testCharacterOfLastElement() {
			assertEquals("S", exampleSnake.characterOfLastElement(),
					"\nThe returned next element does not match the actual next element.");
		}

		@DisplayName("Simple test of removeLastElement, part 1")
		@Test
		void testRemoveLastElementOne() {
			exampleSnake.removeLastElement();
			assertEquals(5, exampleSnake.getElements().size(),
					"\nThe snake does not have the desired number of elements. It should be one element fewer than before "
							+ "(i.e., 5), but " + exampleSnake.getElements().size() + " was returned.");
		}

		@DisplayName("Simple test of removeLastElement, part 2")
		@Test
		void testRemoveLastElementTwo() {
			Snake exampleSnake2 = new Snake();
			exampleSnake2.removeLastElement();
			assertEquals(0, exampleSnake2.getElements().size(),
					"\nThe snake does not have the desired number of elements. It should be one element fewer than before "
							+ "(i.e., 0), but " + exampleSnake2.getElements().size() + " was returned.");
		}
	}
}