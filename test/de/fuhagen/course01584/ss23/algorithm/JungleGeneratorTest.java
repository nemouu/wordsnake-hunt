package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import de.fuhagen.course01584.ss23.ioprocessing.IReader;
import de.fuhagen.course01584.ss23.ioprocessing.ReaderXML;
import de.fuhagen.course01584.ss23.model.*;

/**
 * In der Klasse DschungelGeneratorTest werden die oeffentlichen Methoden der
 * Klasse Dschungelgenerator getestet. Aufgrund der Struktur der Methoden dieser
 * Klasse werden nur eine Reihe von einfachen Tests durchgefuehrt. Hierbei wird
 * auch die Fehlererkennung getestet und es ist zu beachten, dass oftmals auch
 * ein Dschungel auf die Konsole ausgegeben wird, um einen zusaetzlichen
 * manuellen Test zu ermoeglichen. Hinweis: Alle Klassen des Algorithmuspaketes
 * werden auch noch einmal durch die Tests der Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class JungleGeneratorTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Simple_Tests {
		@DisplayName("Einfacher Test fuer Dschungelgenerator ohne Schlangen")
		@Test
		void testJungleGeneratorTooManyReadInSnakes() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p11_unvollstaendig.xml");
			assertThrows(IllegalArgumentException.class, () -> new JungleGenerator(readerXML.getTransferredModel()),
					"\nEs wird keine Ausnahme ausgeloest, obwohl mehr Schlangen im Modell verteilt werden sollten als "
							+ "Platz im Dschungel des Modelles ist.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator ohne Schlangen")
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
			System.out.println("Erster einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen")
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
			System.out.println("Zweiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 1.")
		@Test
		void testJungleGeneratorWithReadInDataOne() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p2_unvollstaendig.xml");
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
			System.out.println("Driiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 2.")
		@Test
		void testJungleGeneratorWithReadInDataTwo() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p4_unvollstaendig.xml");
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
			System.out.println("Vierter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 3.")
		@Test
		void testJungleGeneratorWithReadInDataThree() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p2_loesung.xml");
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
			System.out.println("Fuenfter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(containsRightCharacters, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
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
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
		@Test
		void testJungleGeneratorWithSnakesAndSnakeSearchTwo() throws Exception {
			IReader readerXML = new ReaderXML();
			readerXML.readFile("res/sj_p4_unvollstaendig.xml");
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
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(1).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("SCHLINGNATTER", solution,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			solution = "";
			for (SnakeElement element : search.getSolution().getSnakes().get(2).getElements()) {
				solution += element.getField().getCharacter();
			}
			assertEquals("RINGELNATTER", solution,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit zu leerem Modell.")
		@Test
		void testJungleGeneratorWithTooEmptyModel() throws Exception {
			Jungle exampleJungle = new Jungle();
			IModel model = new ProblemModel();
			model.setJungle(exampleJungle);
			JungleGenerator generator = new JungleGenerator(model);
			generator.generateJungle();
			assertEquals("", generator.getNewJungle().toString(),
					"\nEs wurde nicht der leere String erzeugt, obwohl im Dschungel nicht genuegend Informationen vorhanden waren.");
		}
	}
}