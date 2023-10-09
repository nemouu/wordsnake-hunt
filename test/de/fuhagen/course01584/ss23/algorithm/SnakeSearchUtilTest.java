package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.fuhagen.course01584.ss23.algorithm.SnakeSearchUtil;
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
	class Einfache_Tests {
		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat.")
		@Test
		void testeErzeugeZulaessigeStartfelder() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[2][3].setZeichen("F");
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][1].setZeichen("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFelder()[2][3]);
			startfelder.add(bspDschungel.getFelder()[3][4]);
			startfelder.add(bspDschungel.getFelder()[2][1]);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					startfelder.size() == util.erzeugeZulaessigeStartfelder(schlange1.getArt()).size()
							&& startfelder.containsAll(util.erzeugeZulaessigeStartfelder(schlange1.getArt()))
							&& util.erzeugeZulaessigeStartfelder(schlange1.getArt()).containsAll(startfelder),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die keine Glieder im Dschungel hat.")
		@Test
		void testeErzeugeZulaessigeStartfelderLeer() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(3, 4, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			List<Field> startfelder = new ArrayList<Field>();
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(startfelder, util.erzeugeZulaessigeStartfelder(schlange1.getArt()),
					"\nDie Liste der Startfelder war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
		@Test
		void testeErzeugeZulaessigeStartfelderVerwendbarkeit() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[2][3].setZeichen("F");
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][1].setZeichen("F");
			bspDschungel.getFelder()[2][1].setVerwendbarkeit(0);
			bspDschungel.getFelder()[2][3].setVerwendbarkeit(0);
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFelder()[3][4]);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					startfelder.size() == util.erzeugeZulaessigeStartfelder(schlange1.getArt()).size()
							&& startfelder.containsAll(util.erzeugeZulaessigeStartfelder(schlange1.getArt()))
							&& util.erzeugeZulaessigeStartfelder(schlange1.getArt()).containsAll(startfelder),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste. Es wurden vermutlich Felder mitgezaehlt, die Verwendbarkeit 0 haben.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat.")
		@Test
		void testeErzeugeZulaessigeNachbarfelder() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][4].setZeichen("E");
			bspDschungel.getFelder()[2][3].setZeichen("E");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFelder()[3][4]);
			schlange1.addGlied(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFelder()[2][4]);
			nachbarfelder.add(bspDschungel.getFelder()[2][3]);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					nachbarfelder.size() == util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1).size()
							&& nachbarfelder.containsAll(util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1))
							&& util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1).containsAll(nachbarfelder),
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die keine weiteren Glieder im Dschungel hat.")
		@Test
		void testeErzeugeZulaessigeNachbarfelderLeer() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[3][4].setZeichen("F");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFelder()[3][4]);
			schlange1.addGlied(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(nachbarfelder, util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1),
					"\nDie Liste mit Startfeldern war nicht leer aber es wurde eine leere Liste erwartet, da im Dschungel "
							+ "keine Felder mit zulaessigem Zeichen existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die weitere Glieder im Dschungel hat aber unter diesen sind welche mit Verwendbarkeit 0.")
		@Test
		void testeErzeugeZulaessigeNachbarfelderVerwendbarkeit() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][4].setZeichen("E");
			bspDschungel.getFelder()[2][3].setZeichen("E");
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFelder()[3][4]);
			schlange1.addGlied(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFelder()[2][4]);
			bspDschungel.getFelder()[2][3].setVerwendbarkeit(0);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					nachbarfelder.size() == util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1).size()
							&& nachbarfelder.containsAll(util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1))
							&& util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1).containsAll(nachbarfelder),
					"\nDie Liste mit Nachbarfeldern entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten.")
		@Test
		void testeErzeugeZulaessigeSchlangenarten() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType art3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType art4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType art6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			modell.addSchlangenart(art1);
			modell.addSchlangenart(art2);
			modell.addSchlangenart(art3);
			modell.addSchlangenart(art4);
			modell.addSchlangenart(art5);
			modell.addSchlangenart(art6);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(
					modell.getSchlangenarten().size() == util.erzeugeZulaessigeSchlangenarten().size()
							&& modell.getSchlangenarten().containsAll(util.erzeugeZulaessigeSchlangenarten())
							&& util.erzeugeZulaessigeSchlangenarten().containsAll(modell.getSchlangenarten()),
					"\nDie Liste mit Schlangenarten entspricht nicht der erwarteten Liste.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit leeren Schlangearten.")
		@Test
		void testeErzeugeZulaessigeSchlangenartenLeer() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			List<SnakeType> arten = new ArrayList<SnakeType>();
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertIterableEquals(arten, util.erzeugeZulaessigeSchlangenarten(),
					"\nDie Liste mit Schlangenarten war nicht leer aber es wurde eine leere Liste erwartet, da im Modell "
							+ "keine Schlangenarten existieren.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeSchlangenarten mit Sortierung.")
		@Test
		void testeErzeugeZulaessigeSchlangenartenSort() {
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "PROPRA", 2, 1);
			SnakeType art3 = new SnakeType("A2", new JumpNeighborhood(), "ANACONDA", 2, 3);
			SnakeType art4 = new SnakeType("A3", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art5 = new SnakeType("A4", new JumpNeighborhood(), "JUNIT", 2, 4);
			SnakeType art6 = new SnakeType("A4", new DistanceNeighborhood(3), "JUNIT", 1, 3);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			modell.addSchlangenart(art2);
			modell.addSchlangenart(art1);
			modell.addSchlangenart(art3);
			modell.addSchlangenart(art4);
			modell.addSchlangenart(art5);
			modell.addSchlangenart(art6);
			List<SnakeType> arten = new ArrayList<SnakeType>();
			arten.add(art2);
			arten.add(art3);
			arten.add(art4);
			arten.add(art5);
			arten.add(art1);
			arten.add(art6);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(arten.size() == util.erzeugeZulaessigeSchlangenarten().size(),
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Groesse.");
			assertIterableEquals(arten, util.erzeugeZulaessigeSchlangenarten(),
					"\nDie Liste der erhaltenen Schlangenarten hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeNachbarfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testeErzeugeZulaessigeNachbarfelderSort() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][4].setZeichen("E");
			bspDschungel.getFelder()[2][3].setZeichen("E");
			bspDschungel.getFelder()[2][3].setPunkte(7);
			SnakeElement vorherigesGlied = new SnakeElement(bspDschungel.getFelder()[3][4]);
			schlange1.addGlied(vorherigesGlied);
			List<Field> nachbarfelder = new ArrayList<Field>();
			nachbarfelder.add(bspDschungel.getFelder()[2][3]);
			nachbarfelder.add(bspDschungel.getFelder()[2][4]);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(nachbarfelder.size() == util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1).size(),
					"\nDie Groesse der Liste mit Nachbarfeldern entspricht nicht der Groesse der erwarteten Liste.");
			assertIterableEquals(nachbarfelder, util.erzeugeZulaessigeNachbarn(vorherigesGlied, schlange1),
					"\nDie Liste mit Nachbarfeldern hat nicht die erwartete Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testeErzeugeZulaessigeStartfelderSort() {
			Snake schlange1 = new Snake(new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 2, 3));
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[2][3].setZeichen("F");
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][1].setZeichen("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFelder()[2][1]);
			startfelder.add(bspDschungel.getFelder()[2][3]);
			startfelder.add(bspDschungel.getFelder()[3][4]);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(startfelder.size() == util.erzeugeZulaessigeStartfelder(schlange1.getArt()).size(),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startfelder, util.erzeugeZulaessigeStartfelder(schlange1.getArt()),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}

		@DisplayName("Einfacher Test fuer erzeugeZulaessigeStartfelder mit Schlange, die hohe Punkte hat. Die Glieder im Dschungel hat, die Sortierung wird beachtet.")
		@Test
		void testeErzeugeZulaessigeStartfelderSortHohePunkte() {
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 100, 3);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "HAGEN", 2, 3);
			SnakeType art3 = new SnakeType("A2", new DistanceNeighborhood(1), "JUNIT", 2, 3);
			Jungle bspDschungel = new Jungle(4, 5, "asdertg", 1);
			for (int i = 0; i < bspDschungel.getZeilen(); i++) {
				for (int j = 0; j < bspDschungel.getSpalten(); j++) {
					bspDschungel.getFelder()[i][j].setZeichen("a");
				}
			}
			bspDschungel.getFelder()[2][3].setZeichen("F");
			bspDschungel.getFelder()[3][4].setZeichen("F");
			bspDschungel.getFelder()[2][0].setZeichen("F");
			List<Field> startfelder = new ArrayList<Field>();
			startfelder.add(bspDschungel.getFelder()[3][4]);
			startfelder.add(bspDschungel.getFelder()[2][0]);
			startfelder.add(bspDschungel.getFelder()[2][3]);
			IModel modell = new ProblemModel();
			modell.addSchlangenart(art1);
			modell.addSchlangenart(art2);
			modell.addSchlangenart(art3);
			modell.setDschungel(bspDschungel);
			SnakeSearchUtil util = new SnakeSearchUtil(modell);
			assertTrue(startfelder.size() == util.erzeugeZulaessigeStartfelder(art1).size(),
					"\nDie Liste der Startfelder entspricht nicht der erwarteten Liste.");
			assertIterableEquals(startfelder, util.erzeugeZulaessigeStartfelder(art1),
					"\nDie Liste mit Startfeldern hat nicht die richtige Sortierung.");
		}
	}
}