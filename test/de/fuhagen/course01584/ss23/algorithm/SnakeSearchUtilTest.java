package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.fuhagen.course01584.ss23.model.*;

/**
 * In der Klasse SchlangenSucheUtilTest werden die Methoden der Klasse
 * SchlangenSucheUtil getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt und dazu werden einige Dschungel beziehungsweise
 * Probleminstanzen manuell erstellt. Hierbei wird auf die Sortierungen
 * eingegangen und auch diese werden hier ueberprueft. Hinweis: Alle Klassen des
 * Algorithmuspaketes werden auch noch einmal durch die Tests der
 * Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class SnakeSearchUtilTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat.")
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
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die keine Glieder im Dschungel hat.")
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
					"\nDie Liste der Startfelder war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
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
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste. Es wurden vermutlich Felder mitgezaehlt, die Verwendbarkeit 0 haben.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat.")
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
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die keine weiteren Glieder im Dschungel hat.")
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
					"\nDie Liste mit Startfeldern war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die weitere Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
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
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten.")
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
					"\nDie Liste mit Schlangenarten entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit leeren Schlangearten.")
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
					"\nDie Liste mit Schlangenarten war nicht leer aber es wurde eine leere Liste erwartet, da im Modell "
							+ "keine Schlangenarten existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit Sortierung.")
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
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Groesse.");
			assertIterableEquals(types, util.createValidSnakeTypes(),
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
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
					"\nDie Groesse der Liste mit Nachbarfeldern entspricht nicht der Groesse der erwarteten Liste.");
			assertIterableEquals(neighborFields, util.createValidNeighbors(previousElement, snake),
					"\nDie Liste mit Nachbarfeldern hat nicht die erwartete Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
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
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startingFields, util.createValidStartingFields(snake.getType()),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die hohe Punkte hat. Die Glieder im Dschungel hat, die Sortierung wird beachtet.")
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
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startingFields, util.createValidStartingFields(type1),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}
	}
}