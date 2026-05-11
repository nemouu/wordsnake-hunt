package org.snakehunt.ioprocessing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

/**
 * In the ReaderXMLTest class, the methods of the ReaderXML class are tested.
 * For this purpose, four separate classes were created, each covering a use case.
 * Additionally, the case where no file is found at the specified file path is
 * included, as this should also be covered in the main program. In each of the
 * larger test classes of this class, a @BeforeAll is used first to initialize
 * certain states. For the smaller methods, descriptions in the form of comments
 * have been omitted, and variable and method names have been chosen as clearly
 * as possible.
 * 
 * @author Philip Redecker
 */
class ReaderXMLTest {

	private static IReader readerXML;

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_test_for_trying_to_read_in_a_not_existing_file {
		@DisplayName("Simple test for the situation where a specified file does not exist.")
		@Test
		void testFileExists() {
			assertDoesNotThrow(() -> {
				try {
					new ReaderXML().readFile("res/this_xml_does_not_exist.xml");
				} catch (Exception e) {
					System.out.println("Error while reading!");
				}
			}, "\nThe specified file does not exist or does not exist in the specified "
					+ "directory, but no exception is generated.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_reading_in_a_solution_from_a_file {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Read in the corresponding file
			readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p1_solution.xml");
		}

		@DisplayName("Simple test for the 'time' attribute.")
		@Test
		void testTime() {
			Double[] timeInFile = { 60.0, 0.001205252 };
			assertArrayEquals(timeInFile, readerXML.getTransferredModel().getTime(),
					"\nThe read time does not correspond to the time from the XML file.");
		}

		@DisplayName("Simple test for the 'jungle' attribute.")
		@Test
		void testJungle() {
			assertEquals(2, readerXML.getTransferredModel().getJungle().getRows(),
					"\nThe read number for the rows of the jungle does not correspond to the value from the file to be read.");
			assertEquals(4, readerXML.getTransferredModel().getJungle().getColumns(),
					"\nThe read number for the columns of the jungle does not correspond to the value from the file to be read.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					readerXML.getTransferredModel().getJungle().getSigns(),
					"\nThe read character set of the jungle does not correspond to the value from the file to be read.");
		}

		@DisplayName("Simple test for the 'jungle' attribute and its 'fields' attribute.")
		@Test
		void testJungleFields() {

			// Check the field at position [0][0]
			assertEquals("F0", readerXML.getTransferredModel().getJungle().getFields()[0][0].getId(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][0].getRow(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][0].getColumn(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][0].getUsage(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][0].getPoints(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("F", readerXML.getTransferredModel().getJungle().getFields()[0][0].getCharacter(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][1]
			assertEquals("F1", readerXML.getTransferredModel().getJungle().getFields()[0][1].getId(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][1].getRow(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getColumn(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getUsage(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getPoints(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("E", readerXML.getTransferredModel().getJungle().getFields()[0][1].getCharacter(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][2]
			assertEquals("F2", readerXML.getTransferredModel().getJungle().getFields()[0][2].getId(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][2].getRow(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(2, readerXML.getTransferredModel().getJungle().getFields()[0][2].getColumn(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][2].getUsage(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][2].getPoints(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("R", readerXML.getTransferredModel().getJungle().getFields()[0][2].getCharacter(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][3]
			assertEquals("F3", readerXML.getTransferredModel().getJungle().getFields()[0][3].getId(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][3].getRow(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(3, readerXML.getTransferredModel().getJungle().getFields()[0][3].getColumn(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][3].getUsage(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][3].getPoints(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("N", readerXML.getTransferredModel().getJungle().getFields()[0][3].getCharacter(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][0]
			assertEquals("F4", readerXML.getTransferredModel().getJungle().getFields()[1][0].getId(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getRow(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[1][0].getColumn(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getUsage(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getPoints(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("X", readerXML.getTransferredModel().getJungle().getFields()[1][0].getCharacter(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][1]
			assertEquals("F5", readerXML.getTransferredModel().getJungle().getFields()[1][1].getId(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getRow(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getColumn(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getUsage(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getPoints(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("I", readerXML.getTransferredModel().getJungle().getFields()[1][1].getCharacter(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][2]
			assertEquals("F6", readerXML.getTransferredModel().getJungle().getFields()[1][2].getId(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getRow(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(2, readerXML.getTransferredModel().getJungle().getFields()[1][2].getColumn(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getUsage(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getPoints(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("N", readerXML.getTransferredModel().getJungle().getFields()[1][2].getCharacter(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][3]
			assertEquals("F7", readerXML.getTransferredModel().getJungle().getFields()[1][3].getId(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getRow(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(3, readerXML.getTransferredModel().getJungle().getFields()[1][3].getColumn(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getUsage(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getPoints(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("U", readerXML.getTransferredModel().getJungle().getFields()[1][3].getCharacter(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
		}

		@DisplayName("Simple test for the 'snakeTypes' attribute.")
		@Test
		void testSnakeTypes() {
			assertEquals("A0", readerXML.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nThe read ID of the snake does not correspond to the ID from the file to be read.");
			assertEquals("Distance", readerXML.getTransferredModel().getSnakeTypes().get(0).getStructure().getType(),
					"\nThe read snake type does not correspond to the snake type from the file to be read.");
			assertEquals("FERNUNI", readerXML.getTransferredModel().getSnakeTypes().get(0).getSigns(),
					"\nThe read character string does not correspond to the character string from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getPoints(),
					"\nThe read points do not correspond to the points from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getAmount(),
					"\nThe read amount does not correspond to the amount from the file to be read.");
		}

		@DisplayName("Simple test for the 'snakes' attribute.")
		@Test
		void testSnakes() {
			assertEquals("F0",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(0).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F1",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(1).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F2",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(2).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F3",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(3).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F7",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(4).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F6",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(5).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
			assertEquals("F5",
					readerXML.getTransferredModel().getSolution().getSnakes().get(0).getElements().get(6).getField()
							.getId(),
					"\nThe ID of the read snake does not correspond to the one in the file to be read.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_reading_in_a_problem_from_a_file {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Read in the corresponding file
			readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p1_problem_instance.xml");
		}

		@DisplayName("Simple test for the 'time' attribute.")
		@Test
		void testTime() {
			Double[] timeInFile = { 60.0, 0.0 };
			assertArrayEquals(timeInFile, readerXML.getTransferredModel().getTime(),
					"\nThe read time does not correspond to the time from the XML file.");
		}

		@DisplayName("Simple test for the 'jungle' attribute.")
		@Test
		void testJungle() {
			assertEquals(2, readerXML.getTransferredModel().getJungle().getRows(),
					"\nThe read number for the rows of the jungle does not correspond to the value from the file to be read.");
			assertEquals(4, readerXML.getTransferredModel().getJungle().getColumns(),
					"\nThe read number for the columns of the jungle does not correspond to the value from the file to be read.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					readerXML.getTransferredModel().getJungle().getSigns(),
					"\nThe read character set of the jungle does not correspond to the value from the file to be read.");
		}

		@DisplayName("Simple test for the 'jungle' attribute and its 'fields' attribute.")
		@Test
		void testJungleFields() {

			// Check the field at position [0][0]
			assertEquals("F0", readerXML.getTransferredModel().getJungle().getFields()[0][0].getId(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][0].getRow(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][0].getColumn(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][0].getUsage(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][0].getPoints(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("F", readerXML.getTransferredModel().getJungle().getFields()[0][0].getCharacter(),
					"\nA field at position [0][0] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][1]
			assertEquals("F1", readerXML.getTransferredModel().getJungle().getFields()[0][1].getId(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][1].getRow(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getColumn(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getUsage(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][1].getPoints(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("E", readerXML.getTransferredModel().getJungle().getFields()[0][1].getCharacter(),
					"\nA field at position [0][1] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][2]
			assertEquals("F2", readerXML.getTransferredModel().getJungle().getFields()[0][2].getId(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][2].getRow(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(2, readerXML.getTransferredModel().getJungle().getFields()[0][2].getColumn(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][2].getUsage(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][2].getPoints(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("R", readerXML.getTransferredModel().getJungle().getFields()[0][2].getCharacter(),
					"\nA field at position [0][2] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [0][3]
			assertEquals("F3", readerXML.getTransferredModel().getJungle().getFields()[0][3].getId(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[0][3].getRow(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(3, readerXML.getTransferredModel().getJungle().getFields()[0][3].getColumn(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][3].getUsage(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[0][3].getPoints(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("N", readerXML.getTransferredModel().getJungle().getFields()[0][3].getCharacter(),
					"\nA field at position [0][3] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][0]
			assertEquals("F4", readerXML.getTransferredModel().getJungle().getFields()[1][0].getId(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getRow(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(0, readerXML.getTransferredModel().getJungle().getFields()[1][0].getColumn(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getUsage(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][0].getPoints(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("X", readerXML.getTransferredModel().getJungle().getFields()[1][0].getCharacter(),
					"\nA field at position [1][0] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][1]
			assertEquals("F5", readerXML.getTransferredModel().getJungle().getFields()[1][1].getId(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getRow(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getColumn(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getUsage(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][1].getPoints(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("I", readerXML.getTransferredModel().getJungle().getFields()[1][1].getCharacter(),
					"\nA field at position [1][1] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][2]
			assertEquals("F6", readerXML.getTransferredModel().getJungle().getFields()[1][2].getId(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getRow(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(2, readerXML.getTransferredModel().getJungle().getFields()[1][2].getColumn(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getUsage(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][2].getPoints(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("N", readerXML.getTransferredModel().getJungle().getFields()[1][2].getCharacter(),
					"\nA field at position [1][2] of the jungle does not correspond to the corresponding field from the file to be read.");

			// Check the field at position [1][3]
			assertEquals("F7", readerXML.getTransferredModel().getJungle().getFields()[1][3].getId(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getRow(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(3, readerXML.getTransferredModel().getJungle().getFields()[1][3].getColumn(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getUsage(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getJungle().getFields()[1][3].getPoints(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
			assertEquals("U", readerXML.getTransferredModel().getJungle().getFields()[1][3].getCharacter(),
					"\nA field at position [1][3] of the jungle does not correspond to the corresponding field from the file to be read.");
		}

		@DisplayName("Simple test for the 'snakeTypes' attribute.")
		@Test
		void testSnakeTypes() {
			assertEquals("A0", readerXML.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nThe read ID of the snake does not correspond to the ID from the file to be read.");
			assertEquals("Distance", readerXML.getTransferredModel().getSnakeTypes().get(0).getStructure().getType(),
					"\nThe read snake type does not correspond to the snake type from the file to be read.");
			assertEquals("FERNUNI", readerXML.getTransferredModel().getSnakeTypes().get(0).getSigns(),
					"\nThe read character string does not correspond to the character string from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getPoints(),
					"\nThe read points do not correspond to the points from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getAmount(),
					"\nThe read amount does not correspond to the amount from the file to be read.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_reading_in_an_incomplete_file {
		@BeforeAll
		static void setUpBeforeClass() throws Exception {

			// Read in the corresponding file
			readerXML = new ReaderXML();
			readerXML.readFile("res/sh_p1_incomplete.xml");
		}

		@DisplayName("Simple test for the 'time' attribute.")
		@Test
		void testTime() {
			Double[] timeInFile = { 60.0, 0.0 };
			assertArrayEquals(timeInFile, readerXML.getTransferredModel().getTime(),
					"\nThe read time does not correspond to the time from the XML file.");
		}

		@DisplayName("Simple test for the 'jungle' attribute.")
		@Test
		void testJungle() {
			assertEquals(2, readerXML.getTransferredModel().getJungle().getRows(),
					"\nThe read number for the rows of the jungle does not correspond to the value from the file to be read.");
			assertEquals(4, readerXML.getTransferredModel().getJungle().getColumns(),
					"\nThe read number for the columns of the jungle does not correspond to the value from the file to be read.");
			assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXYZ",
					readerXML.getTransferredModel().getJungle().getSigns(),
					"\nThe read character set of the jungle does not correspond to the value from the file to be read.");
		}

		@DisplayName("Simple test for the 'snakeTypes' attribute.")
		@Test
		void testSnakeTypes() {
			assertEquals("A0", readerXML.getTransferredModel().getSnakeTypes().get(0).getId(),
					"\nThe read ID of the snake does not correspond to the ID from the file to be read.");
			assertEquals("Distance", readerXML.getTransferredModel().getSnakeTypes().get(0).getStructure().getType(),
					"\nThe read snake type does not correspond to the snake type from the file to be read.");
			assertEquals("FERNUNI", readerXML.getTransferredModel().getSnakeTypes().get(0).getSigns(),
					"\nThe read character string does not correspond to the character string from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getPoints(),
					"\nThe read points do not correspond to the points from the file to be read.");
			assertEquals(1, readerXML.getTransferredModel().getSnakeTypes().get(0).getAmount(),
					"\nThe read amount does not correspond to the amount from the file to be read.");
		}
	}
}

