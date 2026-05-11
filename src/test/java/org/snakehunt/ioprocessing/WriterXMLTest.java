package org.snakehunt.ioprocessing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;
import org.junit.jupiter.api.*;

import org.snakehunt.model.*;

/**
 * In the WriterXMLTest class, the methods of the WriterXML class are tested. For
 * this purpose, four separate classes were created, each covering a use case.
 * Additionally, the case where the passed model is empty is included, as this
 * should also be covered in the main program. In each of the larger test
 * classes of this class, a @BeforeAll is used first to initialize certain
 * states.
 * 
 * @author Philip Redecker
 */
class WriterXMLTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_test_for_writing_in_an_empty_model {

		@DisplayName("Simple test for the situation where the passed model is empty.")
		@Test
		void testModelEmpty() {
			assertThrows(Exception.class,
					() -> new WriterXML(new ProblemModel())
							.writeInFile("res/das_modell_ist_incomplete.xml"),
					() -> "\nThe passed model does not exist, but no exception is generated.");
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_writing_in_a_solution_file {
		private static IModel exampleModel;
		private static WriterXML writerXML;
		private static List<Element> listForTests;

		/*
		 * Here, a model is first filled 'by hand' so that it can then be tested whether
		 * the methods of the WriterXML class transfer the correct data into an XML
		 * document. It should be noted that the specified snakes are not actual
		 * solutions to this jungle. Everything is inserted here only as an example.
		 * For the smaller methods, descriptions in the form of comments have been
		 * omitted, and variable and method names have been chosen as clearly as
		 * possible.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Create a jungle example
			Jungle exampleJungle = new Jungle(2, 3, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a" + (i + j + j));
					exampleJungle.getFields()[i][j].setPoints(1);
					exampleJungle.getFields()[i][j].setUsage(1);
					exampleJungle.getFields()[i][j].setRow(i);
					exampleJungle.getFields()[i][j].setColumn(j);
					exampleJungle.getFields()[i][j].setId("F" + (exampleJungle.getColumns() * i + j));
				}
			}

			// Create a neighborhood example
			INeighborhood exampleNeighborhood = new DistanceNeighborhood(2);

			// Create a list of possible snake types
			SnakeType[] exampleTypes = { new SnakeType("A0", exampleNeighborhood, "abcdefghij", 1, 1),
					new SnakeType("A1", exampleNeighborhood, "hjgzhi", 21, 31) };

			// Create a sample snake with two sample elements
			SnakeElement firstElement = new SnakeElement(exampleJungle.getFields()[0][0]);
			SnakeElement secondElement = new SnakeElement(exampleJungle.getFields()[0][1]);
			List<SnakeElement> elements = new ArrayList<SnakeElement>();
			elements.add(firstElement);
			elements.add(secondElement);

			// Create a sample solution and add the data from above to it
			Solution exampleSolution = new Solution();
			exampleSolution.addSnake(new Snake(exampleTypes[0], elements));
			Double[] exampleTime = { 1.0, 2.0 };

			// Add all created data to the example model
			exampleModel = new ProblemModel(exampleJungle, exampleSolution, exampleTime);
			exampleModel.addSnakeType(new SnakeType("A0", exampleNeighborhood, "abcdefghij", 1, 1));
			exampleModel.addSnakeType(new SnakeType("A1", exampleNeighborhood, "hjgzhi", 21, 31));

			// Now write this data to a document
			writerXML = new WriterXML(exampleModel);
			writerXML.writeInFile("res/test_fuer_schreiben_in_solutionsdatei.xml");
			listForTests = writerXML.getDocument().getRootElement().getChildren();
		}

		@DisplayName("Simple test for the root element.")
		@Test
		void testRoot() {
			assertEquals("SnakeHunt", writerXML.getDocument().getRootElement().getName(),
					"\nThe read root element does not correspond to the content of the model.");
		}

		@DisplayName("Simple test for the 'Time' element.")
		@Test
		void testTime() {
			for (Element element : listForTests) {
				if (element.getName().equals("Time")) {
					assertEquals(1.0, Double.parseDouble(element.getChildText("Limit")),
							"\nThe read value for the 'Time' attribute (Limit) does not match the one from the model.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Elapsed")),
							"\nThe read value for the 'Time' attribute (Elapsed) does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'Jungle' element.")
		@Test
		void testJungle() {
			for (Element element : listForTests) {
				if (element.getName().equals("Jungle")) {

					// Check the type of jungle in the model
					assertEquals(2, Integer.parseInt(element.getAttributeValue("rows")),
							"\nThe read value for the 'rows' attribute does not match the one from the model.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("cols")),
							"\nThe read value for the 'cols' attribute does not match the one from the model.");
					assertEquals("asdertg", element.getAttributeValue("signs"),
							"\nThe read value for the 'signs' attribute does not match the one from the model.");

					// Check the field at position [0][0] of the jungle in the model
					assertEquals("F0", element.getChildren().get(0).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][0] does not match the one from the model.");
					assertEquals("a0", element.getChildren().get(0).getText(),
							"\nThe read value for the 'character' attribute at position [0][0] does not match the one from the model.");

					// Check the field at position [0][1] of the jungle in the model
					assertEquals("F1", element.getChildren().get(1).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][1] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(1).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][1] does not match the one from the model.");
					assertEquals("a2", element.getChildren().get(1).getText(),
							"\nThe read value for the 'character' attribute at position [0][1] does not match the one from the model.");

					// Check the field at position [0][2] of the jungle in the model
					assertEquals("F2", element.getChildren().get(2).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][2] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(2).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][2] does not match the one from the model.");
					assertEquals("2", element.getChildren().get(2).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][2] does not match the one from the model.");
					assertEquals("a4", element.getChildren().get(2).getText(),
							"\nThe read value for the 'character' attribute at position [0][2] does not match the one from the model.");

					// Check the field at position [1][0] of the jungle in the model
					assertEquals("F3", element.getChildren().get(3).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(3).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][0] does not match the one from the model.");
					assertEquals("a1", element.getChildren().get(3).getText(),
							"\nThe read value for the 'character' attribute at position [1][0] does not match the one from the model.");

					// Check the field at position [1][1] of the jungle in the model
					assertEquals("F4", element.getChildren().get(4).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][1] does not match the one from the model.");
					assertEquals("a3", element.getChildren().get(4).getText(),
							"\nThe read value for the 'character' attribute at position [1][1] does not match the one from the model.");

					// Check the field at position [1][2] of the jungle in the model
					assertEquals("F5", element.getChildren().get(5).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][2] does not match the one from the model.");
					assertEquals("2", element.getChildren().get(5).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][2] does not match the one from the model.");
					assertEquals("a5", element.getChildren().get(5).getText(),
							"\nThe read value for the 'character' attribute at position [1][2] does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'SnakeType' element.")
		@Test
		void testSnakeTypes() {
			for (Element element : listForTests) {
				if (element.getName().equals("SnakeType")) {

					// Check first snake type of the model
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(0).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");

					// Check second snake type of the model
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(1).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'Snakes' element.")
		@Test
		void testSnakes() {
			for (Element element : listForTests) {
				if (element.getName().equals("Snakes")) {

					// Check the found snakes of the jungle in the model
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("type"),
							"\nThe read value for the 'type' attribute does not match the one from the model.");
					assertEquals("F0", element.getChildren().get(0).getChildren().get(0).getAttributeValue("field"),
							"\nA read value for the 'field' attribute of this snake does not match the one from the model.");
					assertEquals("F1", element.getChildren().get(0).getChildren().get(1).getAttributeValue("field"),
							"\nA read value for the 'field' attribute of this snake does not match the one from the model.");
				}
			}
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_writing_in_a_problem_file {
		private static IModel exampleModel;
		private static WriterXML writerXML;
		private static List<Element> listForTests;

		/*
		 * Here, a model is first filled 'by hand' so that it can then be tested whether
		 * the methods of the WriterXML class transfer the correct data into an XML
		 * document. It should be noted that the specified snakes are not actual
		 * solutions to this jungle. Everything is inserted here only as an example.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Create a jungle example
			Jungle exampleJungle = new Jungle(2, 3, "asdertg", 1);
			for (int i = 0; i < exampleJungle.getRows(); i++) {
				for (int j = 0; j < exampleJungle.getColumns(); j++) {
					exampleJungle.getFields()[i][j].setCharacter("a" + (i + j + j));
					exampleJungle.getFields()[i][j].setPoints(1);
					exampleJungle.getFields()[i][j].setUsage(1);
					exampleJungle.getFields()[i][j].setRow(i);
					exampleJungle.getFields()[i][j].setColumn(j);
					exampleJungle.getFields()[i][j].setId("F" + (i + j + j));
				}
			}

			// Create a neighborhood example
			INeighborhood exampleNeighborhood = new DistanceNeighborhood(2);

			// Create sample time
			Double[] exampleTime = { 1.0, 2.0 };

			// Add all created data to the example model
			exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.addSnakeType(new SnakeType("A0", exampleNeighborhood, "abcdefghij", 1, 1));
			exampleModel.addSnakeType(new SnakeType("A1", exampleNeighborhood, "hjgzhi", 21, 31));

			// Now write this data to a document
			writerXML = new WriterXML(exampleModel);
			writerXML.writeInFile("res/test_fuer_schreiben_in_solutionsdatei.xml");
			listForTests = writerXML.getDocument().getRootElement().getChildren();
		}

		@DisplayName("Simple test for the root element.")
		@Test
		void testRoot() {
			assertEquals("SnakeHunt", writerXML.getDocument().getRootElement().getName(),
					"\nThe read root element does not correspond to the content of the model.");
		}

		@DisplayName("Simple test for the 'Time' element.")
		@Test
		void testTime() {
			for (Element element : listForTests) {
				if (element.getName().equals("Time")) {
					assertEquals(1.0, Double.parseDouble(element.getChildText("Limit")),
							"\nThe read value for the 'Time' attribute (Limit) does not match the one from the model.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Elapsed")),
							"\nThe read value for the 'Time' attribute (Elapsed) does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'Jungle' element.")
		@Test
		void testJungle() {
			for (Element element : listForTests) {
				if (element.getName().equals("Jungle")) {

					// Check the type of jungle in the model
					assertEquals(2, Integer.parseInt(element.getAttributeValue("rows")),
							"\nThe read value for the 'rows' attribute does not match the one from the model.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("cols")),
							"\nThe read value for the 'cols' attribute does not match the one from the model.");
					assertEquals("asdertg", element.getAttributeValue("signs"),
							"\nThe read value for the 'signs' attribute does not match the one from the model.");

					// Check the field at position [0][0] of the jungle in the model
					assertEquals("F0", element.getChildren().get(0).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(0).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][0] does not match the one from the model.");
					assertEquals("a0", element.getChildren().get(0).getText(),
							"\nThe read value for the 'character' attribute at position [0][0] does not match the one from the model.");

					// Check the field at position [0][1] of the jungle in the model
					assertEquals("F2", element.getChildren().get(1).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][1] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(1).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(1).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][1] does not match the one from the model.");
					assertEquals("a2", element.getChildren().get(1).getText(),
							"\nThe read value for the 'character' attribute at position [0][1] does not match the one from the model.");

					// Check the field at position [0][2] of the jungle in the model
					assertEquals("F4", element.getChildren().get(2).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [0][2] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(2).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [0][2] does not match the one from the model.");
					assertEquals("2", element.getChildren().get(2).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [0][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [0][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(2).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [0][2] does not match the one from the model.");
					assertEquals("a4", element.getChildren().get(2).getText(),
							"\nThe read value for the 'character' attribute at position [0][2] does not match the one from the model.");

					// Check the field at position [1][0] of the jungle in the model
					assertEquals("F1", element.getChildren().get(3).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][0] does not match the one from the model.");
					assertEquals("0", element.getChildren().get(3).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][0] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(3).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][0] does not match the one from the model.");
					assertEquals("a1", element.getChildren().get(3).getText(),
							"\nThe read value for the 'character' attribute at position [1][0] does not match the one from the model.");

					// Check the field at position [1][1] of the jungle in the model
					assertEquals("F3", element.getChildren().get(4).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][1] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(4).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][1] does not match the one from the model.");
					assertEquals("a3", element.getChildren().get(4).getText(),
							"\nThe read value for the 'character' attribute at position [1][1] does not match the one from the model.");

					// Check the field at position [1][2] of the jungle in the model
					assertEquals("F5", element.getChildren().get(5).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("row"),
							"\nThe read value for the 'row' attribute at position [1][2] does not match the one from the model.");
					assertEquals("2", element.getChildren().get(5).getAttributeValue("col"),
							"\nThe read value for the 'col' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("usability"),
							"\nThe read value for the 'usability' attribute at position [1][2] does not match the one from the model.");
					assertEquals("1", element.getChildren().get(5).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute at position [1][2] does not match the one from the model.");
					assertEquals("a5", element.getChildren().get(5).getText(),
							"\nThe read value for the 'character' attribute at position [1][2] does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'SnakeType' element.")
		@Test
		void testSnakeTypes() {
			for (Element element : listForTests) {
				if (element.getName().equals("SnakeType")) {

					// Check first snake type of the model
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(0).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");

					// Check second snake type of the model
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(1).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");
				}
			}
		}
	}

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_tests_for_writing_in_an_incomplete_file {
		private static IModel exampleModel;
		private static WriterXML writerXML;
		private static List<Element> listForTests;

		/*
		 * Here, a model is first filled 'by hand' so that it can then be tested whether
		 * the methods of the WriterXML class transfer the correct data into an XML
		 * document. It should be noted that the specified snakes are not actual
		 * solutions to this jungle. Everything is inserted here only as an example.
		 */

		@BeforeAll
		static void setUpBeforeClass() throws Exception {
			// Create a jungle example
			Jungle exampleJungle = new Jungle(2, 3, "asdertg", 1);

			// Create a neighborhood example
			INeighborhood exampleNeighborhood = new DistanceNeighborhood(2);

			// Create sample time
			Double[] exampleTime = { 1.0, 2.0 };

			// Add all created data to the example model
			exampleModel = new ProblemModel(exampleJungle, null, exampleTime);
			exampleModel.addSnakeType(new SnakeType("A0", exampleNeighborhood, "abcdefghij", 1, 1));
			exampleModel.addSnakeType(new SnakeType("A1", exampleNeighborhood, "hjgzhi", 21, 31));

			// Now write this data to a document
			writerXML = new WriterXML(exampleModel);
			writerXML.writeInFile("res/test_fuer_schreiben_in_solutionsdatei.xml");
			listForTests = writerXML.getDocument().getRootElement().getChildren();
		}

		@DisplayName("Simple test for the root element.")
		@Test
		void testRoot() {
			assertEquals("SnakeHunt", writerXML.getDocument().getRootElement().getName(),
					"\nThe read root element does not correspond to the content of the model.");
		}

		@DisplayName("Simple test for the 'Time' element.")
		@Test
		void testTime() {
			for (Element element : listForTests) {
				if (element.getName().equals("Time")) {

					// Check the time in the model
					assertEquals(1.0, Double.parseDouble(element.getChildText("Limit")),
							"\nThe read value for the 'Time' attribute (Limit) does not match the one from the model.");
					assertEquals(2.0, Double.parseDouble(element.getChildText("Elapsed")),
							"\nThe read value for the 'Time' attribute (Elapsed) does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'Jungle' element.")
		@Test
		void testJungle() {
			for (Element element : listForTests) {
				if (element.getName().equals("Jungle")) {

					// Check the type of jungle in the model
					assertEquals(2, Integer.parseInt(element.getAttributeValue("rows")),
							"\nThe read value for the 'rows' attribute does not match the one from the model.");
					assertEquals(3, Integer.parseInt(element.getAttributeValue("cols")),
							"\nThe read value for the 'cols' attribute does not match the one from the model.");
					assertEquals("asdertg", element.getAttributeValue("signs"),
							"\nThe read value for the 'signs' attribute does not match the one from the model.");
				}
			}
		}

		@DisplayName("Simple test for the 'SnakeType' element.")
		@Test
		void testSnakeTypes() {
			for (Element element : listForTests) {
				if (element.getName().equals("SnakeType")) {

					// Check first snake type of the model
					assertEquals("A0", element.getChildren().get(0).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("1", element.getChildren().get(0).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("abcdefghij",
							element.getChildren().get(0).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(0).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(0).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");

					// Check second snake type of the model
					assertEquals("A1", element.getChildren().get(1).getAttributeValue("id"),
							"\nThe read value for the 'id' attribute does not match the one from the model.");
					assertEquals("21", element.getChildren().get(1).getAttributeValue("points"),
							"\nThe read value for the 'points' attribute does not match the one from the model.");
					assertEquals("31", element.getChildren().get(1).getAttributeValue("amount"),
							"\nThe read value for the 'amount' attribute does not match the one from the model.");
					assertEquals("hjgzhi", element.getChildren().get(1).getChildren("Word").get(0).getText(),
							"\nThe read character string of this neighborhood structure does not match the one from the model.");
					assertEquals("Distance",
							element.getChildren().get(1).getChildren("NeighborhoodStructure").get(0)
									.getAttributeValue("type"),
							"\nThe read type of this neighborhood structure does not match the one from the model.");
					assertEquals("2",
							element.getChildren().get(1).getChildren("Parameter").get(0).getAttributeValue("value"),
							"\nThe read parameter of this neighborhood structure does not match the one from the model.");
				}
			}
		}
	}
}

