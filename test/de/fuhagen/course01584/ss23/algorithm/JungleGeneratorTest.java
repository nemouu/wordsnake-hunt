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
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p11_unvollstaendig.xml");
			assertThrows(IllegalArgumentException.class, () -> new JungleGenerator(xmlLeser.getTransferredModel()),
					"\nEs wird keine Ausnahme ausgeloest, obwohl mehr Schlangen im Modell verteilt werden sollten als "
							+ "Platz im Dschungel des Modelles ist.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator ohne Schlangen")
		@Test
		void testJungleGenerator() throws Exception {
			Jungle bspDschungel = new Jungle(5, 5, "ABCDEFG", 1);
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generateJungle();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFG".contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Erster einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen")
		@Test
		void testJungleGeneratorWithSnakes() throws Exception {
			Jungle bspDschungel = new Jungle(5, 5, "X", 1);
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel modell = new ProblemModel();
			modell.addSnakeType(art);
			modell.setJungle(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generateJungle();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"XFERNUNI".contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Zweiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 1.")
		@Test
		void testJungleGeneratorWithReadInDataOne() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p2_unvollstaendig.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getTransferredModel());
			generator.generateJungle();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Driiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 2.")
		@Test
		void testJungleGeneratorWithReadInDataTwo() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p4_unvollstaendig.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getTransferredModel());
			generator.generateJungle();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ÄABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Vierter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 3.")
		@Test
		void testJungleGeneratorWithReadInDataThree() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p2_loesung.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getTransferredModel());
			generator.generateJungle();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNewJungle().getFields().length; i++) {
				for (int j = 0; j < generator.getNewJungle().getFields()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNewJungle().getFields()[i][j].getCharacter())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Fuenfter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNewJungle());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
		@Test
		void testJungleGeneratorWithSnakesAndSnakeSearchOne() throws Exception {
			Double[] zeit = { 10.0 };
			String einheit = "s";
			Jungle bspDschungel = new Jungle(5, 5, "X", 1);
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel modell = new ProblemModel();
			modell.setUnitOfTime(einheit);
			modell.setTime(zeit);
			modell.addSnakeType(art);
			modell.setJungle(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generateJungle();
			modell.setJungle(generator.getNewJungle());
			SnakeSearch suche = new SnakeSearch(modell);
			suche.searchSnakes();
			String loesung = "";
			for (SnakeElement glied : suche.getSolution().getSnakes().get(0).getElements()) {
				loesung += glied.getField().getCharacter();
			}
			assertEquals("FERNUNI", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
		@Test
		void testJungleGeneratorWithSnakesAndSnakeSearchTwo() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.readFile("res/sj_p4_unvollstaendig.xml");
			IModel modell = xmlLeser.getTransferredModel();
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generateJungle();
			modell.setJungle(generator.getNewJungle());
			SnakeSearch suche = new SnakeSearch(modell);
			suche.searchSnakes();
			String loesung = "";
			for (SnakeElement glied : suche.getSolution().getSnakes().get(0).getElements()) {
				loesung += glied.getField().getCharacter();
			}
			assertEquals("ÄSKULAPNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			loesung = "";
			for (SnakeElement glied : suche.getSolution().getSnakes().get(1).getElements()) {
				loesung += glied.getField().getCharacter();
			}
			assertEquals("SCHLINGNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			loesung = "";
			for (SnakeElement glied : suche.getSolution().getSnakes().get(2).getElements()) {
				loesung += glied.getField().getCharacter();
			}
			assertEquals("RINGELNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit zu leerem Modell.")
		@Test
		void testJungleGeneratorWithTooEmptyModel() throws Exception {
			Jungle bspDschungel = new Jungle();
			IModel modell = new ProblemModel();
			modell.setJungle(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generateJungle();
			assertEquals("", generator.getNewJungle().toString(),
					"\nEs wurde nicht der leere String erzeugt, obwohl im Dschungel nicht genuegend Informationen vorhanden waren.");
		}
	}
}