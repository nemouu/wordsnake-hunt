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
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[2][3].setCharacter("F");
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][1].setCharacter("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFields()[2][3]);
			startfelder.add(bspDschungel.getFields()[3][4]);
			startfelder.add(bspDschungel.getFields()[2][1]);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					startfelder.size() == util.createValidStartingFields(schlange1.getType()).size()
							&& startfelder.containsAll(util.createValidStartingFields(schlange1.getType()))
							&& util.createValidStartingFields(schlange1.getType()).containsAll(startfelder),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die keine Glieder im Dschungel hat.")
		@Test
		void testCreateValidStartingFieldsEmpty() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			List<Field> startfelder = new ArrayList<Field>();
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(startfelder, util.createValidStartingFields(schlange1.getType()),
					"\nDie Liste der Startfelder war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
		@Test
		void testCreateValidStartingFieldsUsage() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[2][3].setCharacter("F");
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][1].setCharacter("F");
			bspDschungel.getFields()[2][1].setUsage(0);
			bspDschungel.getFields()[2][3].setUsage(0);
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFields()[3][4]);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					startfelder.size() == util.createValidStartingFields(schlange1.getType()).size()
							&& startfelder.containsAll(util.createValidStartingFields(schlange1.getType()))
							&& util.createValidStartingFields(schlange1.getType()).containsAll(startfelder),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste. Es wurden vermutlich Felder mitgezaehlt, die Verwendbarkeit 0 haben.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat.")
		@Test
		void testCreateValidNeighborFields() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][4].setCharacter("E");
			bspDschungel.getFields()[2][3].setCharacter("E");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFields()[3][4]);
			schlange1.addElement(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFields()[2][4]);
			nachbarfelder.add(bspDschungel.getFields()[2][3]);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					nachbarfelder.size() == util.createValidNeighbors(vorherigesGlied, schlange1).size()
							&& nachbarfelder.containsAll(util.createValidNeighbors(vorherigesGlied, schlange1))
							&& util.createValidNeighbors(vorherigesGlied, schlange1).containsAll(nachbarfelder),
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die keine weiteren Glieder im Dschungel hat.")
		@Test
		void testCreateValidNeighborFieldsEmpty() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[3][4].setCharacter("F");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFields()[3][4]);
			schlange1.addElement(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(nachbarfelder, util.createValidNeighbors(vorherigesGlied, schlange1),
					"\nDie Liste mit Startfeldern war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die weitere Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
		@Test
		void testCreateValidNeighborFieldsUsage() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][4].setCharacter("E");
			bspDschungel.getFields()[2][3].setCharacter("E");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFields()[3][4]);
			schlange1.addElement(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFields()[2][4]);
			bspDschungel.getFields()[2][3].setUsage(0);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					nachbarfelder.size() == util.createValidNeighbors(vorherigesGlied, schlange1).size()
							&& nachbarfelder.containsAll(util.createValidNeighbors(vorherigesGlied, schlange1))
							&& util.createValidNeighbors(vorherigesGlied, schlange1).containsAll(nachbarfelder),
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten.")
		@Test
		void testCreateValidSnakeTypes() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType art3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType art4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType art6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			modell.addSnakeType(art1);
			modell.addSnakeType(art2);
			modell.addSnakeType(art3);
			modell.addSnakeType(art4);
			modell.addSnakeType(art5);
			modell.addSnakeType(art6);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					modell.getSnakeTypes().size() == util.createValidSnakeTypes().size()
							&& modell.getSnakeTypes().containsAll(util.createValidSnakeTypes())
							&& util.createValidSnakeTypes().containsAll(modell.getSnakeTypes()),
					"\nDie Liste mit Schlangenarten entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit leeren Schlangearten.")
		@Test
		void testCreateValidSnakeTypesEmpty() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			List<SnakeType> arten = new ArrayList<SnakeType>();
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(arten, util.createValidSnakeTypes(),
					"\nDie Liste mit Schlangenarten war nicht leer aber es wurde eine leere Liste erwartet, da im Modell "
							+ "keine Schlangenarten existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit Sortierung.")
		@Test
		void testCreateValidSnakeTypesSort() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType art3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType art4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType art6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			modell.addSnakeType(art2);
			modell.addSnakeType(art1);
			modell.addSnakeType(art3);
			modell.addSnakeType(art4);
			modell.addSnakeType(art5);
			modell.addSnakeType(art6);
			List<SnakeType> arten = new ArrayList<SnakeType>();
			arten.add(art2);
			arten.add(art3);
			arten.add(art4);
			arten.add(art5);
			arten.add(art1);
			arten.add(art6);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(arten.size() == util.createValidSnakeTypes().size(),
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Groesse.");
			assertIterableEquals(arten, util.createValidSnakeTypes(),
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testCreateValidNeighborFieldsSort() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][4].setCharacter("E");
			bspDschungel.getFields()[2][3].setCharacter("E");
			bspDschungel.getFields()[2][3].setPoints(7);
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFields()[3][4]);
			schlange1.addElement(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFields()[2][3]);
			nachbarfelder.add(bspDschungel.getFields()[2][4]);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(nachbarfelder.size() == util.createValidNeighbors(vorherigesGlied, schlange1).size(),
					"\nDie Groesse der Liste mit Nachbarfeldern entspricht nicht der Groesse der erwarteten Liste.");
			assertIterableEquals(nachbarfelder, util.createValidNeighbors(vorherigesGlied, schlange1),
					"\nDie Liste mit Nachbarfeldern hat nicht die erwartete Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testCreateValidStartingFieldsSort() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[2][3].setCharacter("F");
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][1].setCharacter("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFields()[2][1]);
			startfelder.add(bspDschungel.getFields()[2][3]);
			startfelder.add(bspDschungel.getFields()[3][4]);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(startfelder.size() == util.createValidStartingFields(schlange1.getType()).size(),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startfelder, util.createValidStartingFields(schlange1.getType()),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die hohe Punkte hat. Die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testCreateValidStartingFieldsSortHighPoints() {
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 100, 3);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art3 = new SnakeType("A2", new DistanceNeighborhood(1), "JUNIT", 2, 3);
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getRows(); i++) {
				for (int j = 0; j < bspDschungel.getColumns(); j++) {
					bspDschungel.getFields()[i][j].setCharacter("a");
				}
			}
			bspDschungel.getFields()[2][3].setCharacter("F");
			bspDschungel.getFields()[3][4].setCharacter("F");
			bspDschungel.getFields()[2][0].setCharacter("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFields()[3][4]);
			startfelder.add(bspDschungel.getFields()[2][0]);
			startfelder.add(bspDschungel.getFields()[2][3]);
			IModel modell = new ProblemModel();
			modell.addSnakeType(art1);
			modell.addSnakeType(art2);
			modell.addSnakeType(art3);
			modell.setJungle(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(startfelder.size() == util.createValidStartingFields(art1).size(),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startfelder, util.createValidStartingFields(art1),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}
	}
}