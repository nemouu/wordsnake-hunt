package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import de.fuhagen.course01584.ss23.algorithm.JungleGenerator;
import de.fuhagen.course01584.ss23.algorithm.SnakeSearch;
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
	class Einfache_Tests {
		@DisplayName("Einfacher Test fuer Dschungelgenerator ohne Schlangen")
		@Test
		void testeDschungelGeneratorZuVieleSchlangeEingelesen() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p11_unvollstaendig.xml");
			assertThrows(IllegalArgumentException.class, () -> new JungleGenerator(xmlLeser.getUebergebenesModell()),
					"\nEs wird keine Ausnahme ausgeloest, obwohl mehr Schlangen im Modell verteilt werden sollten als "
							+ "Platz im Dschungel des Modelles ist.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator ohne Schlangen")
		@Test
		void testeDschungelGenerator() throws Exception {
			Jungle bspDschungel = new Jungle(5, 5, "ABCDEFG", 1);
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generiereDschungel();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNeuerDschungel().getFelder().length; i++) {
				for (int j = 0; j < generator.getNeuerDschungel().getFelder()[0].length; j++) {
					if (!"ABCDEFG".contains(generator.getNeuerDschungel().getFelder()[i][j].getZeichen())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Erster einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNeuerDschungel());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen")
		@Test
		void testeDschungelGeneratorMitSchlangen() throws Exception {
			Jungle bspDschungel = new Jungle(5, 5, "X", 1);
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel modell = new ProblemModel();
			modell.addSchlangenart(art);
			modell.setDschungel(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generiereDschungel();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNeuerDschungel().getFelder().length; i++) {
				for (int j = 0; j < generator.getNeuerDschungel().getFelder()[0].length; j++) {
					if (!"XFERNUNI".contains(generator.getNeuerDschungel().getFelder()[i][j].getZeichen())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Zweiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNeuerDschungel());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 1.")
		@Test
		void testeDschungelGeneratorMitEingelesenenDatenEins() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p2_unvollstaendig.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getUebergebenesModell());
			generator.generiereDschungel();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNeuerDschungel().getFelder().length; i++) {
				for (int j = 0; j < generator.getNeuerDschungel().getFelder()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNeuerDschungel().getFelder()[i][j].getZeichen())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Driiter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNeuerDschungel());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 2.")
		@Test
		void testeDschungelGeneratorMitEingelesenenDatenZwei() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p4_unvollstaendig.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getUebergebenesModell());
			generator.generiereDschungel();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNeuerDschungel().getFelder().length; i++) {
				for (int j = 0; j < generator.getNeuerDschungel().getFelder()[0].length; j++) {
					if (!"ÄABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNeuerDschungel().getFelder()[i][j].getZeichen())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Vierter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNeuerDschungel());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit eingelesenen Daten, Teil 3.")
		@Test
		void testeDschungelGeneratorMitEingelesenenDatenDrei() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p2_loesung.xml");
			JungleGenerator generator = new JungleGenerator(xmlLeser.getUebergebenesModell());
			generator.generiereDschungel();
			boolean enthaeltRichtigeZeichen = true;
			for (int i = 0; i < generator.getNeuerDschungel().getFelder().length; i++) {
				for (int j = 0; j < generator.getNeuerDschungel().getFelder()[0].length; j++) {
					if (!"ABCDEFGHIJKLMNOPQRSTUVWXYZ"
							.contains(generator.getNeuerDschungel().getFelder()[i][j].getZeichen())) {
						enthaeltRichtigeZeichen = false;
					}
				}
			}
			System.out.println("Fuenfter einfacher Test (visuelle Bestaetigung: ");
			System.out.println(generator.getNeuerDschungel());
			System.out.println();
			assertTrue(enthaeltRichtigeZeichen, "\nDer generierte Dschungel enthaelt nicht die richtigen Zeichen.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
		@Test
		void testeDschungelGeneratorMitSchlangenUndSchlangensucheEins() throws Exception {
			Double[] zeit = { 10.0 };
			String einheit = "s";
			Jungle bspDschungel = new Jungle(5, 5, "X", 1);
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			IModel modell = new ProblemModel();
			modell.setZeiteinheit(einheit);
			modell.setZeit(zeit);
			modell.addSchlangenart(art);
			modell.setDschungel(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generiereDschungel();
			modell.setDschungel(generator.getNeuerDschungel());
			SnakeSearch suche = new SnakeSearch(modell);
			suche.sucheSchlangen();
			String loesung = "";
			for (SnakeElement glied : suche.getLoesung().getSchlangen().get(0).getGlieder()) {
				loesung += glied.getFeld().getZeichen();
			}
			assertEquals("FERNUNI", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit Schlangen und Loesung durch SchlangenSuche.")
		@Test
		void testeDschungelGeneratorMitSchlangenUndSchlangensucheZwei() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p4_unvollstaendig.xml");
			IModel modell = xmlLeser.getUebergebenesModell();
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generiereDschungel();
			modell.setDschungel(generator.getNeuerDschungel());
			SnakeSearch suche = new SnakeSearch(modell);
			suche.sucheSchlangen();
			String loesung = "";
			for (SnakeElement glied : suche.getLoesung().getSchlangen().get(0).getGlieder()) {
				loesung += glied.getFeld().getZeichen();
			}
			assertEquals("ÄSKULAPNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			loesung = "";
			for (SnakeElement glied : suche.getLoesung().getSchlangen().get(1).getGlieder()) {
				loesung += glied.getFeld().getZeichen();
			}
			assertEquals("SCHLINGNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
			loesung = "";
			for (SnakeElement glied : suche.getLoesung().getSchlangen().get(2).getGlieder()) {
				loesung += glied.getFeld().getZeichen();
			}
			assertEquals("RINGELNATTER", loesung,
					"\nDie Schlangensuche findet nicht diesselbe Schlange, die der Dschungelgenerator vorher verteilt hatte.");
		}

		@DisplayName("Einfacher Test fuer Dschungelgenerator mit zu leerem Modell.")
		@Test
		void testeDschungelGeneratorMitZuLeeremModell() throws Exception {
			Jungle bspDschungel = new Jungle();
			IModel modell = new ProblemModel();
			modell.setDschungel(bspDschungel);
			JungleGenerator generator = new JungleGenerator(modell);
			generator.generiereDschungel();
			assertEquals("", generator.getNeuerDschungel().toString(),
					"\nEs wurde nicht der leere String erzeugt, obwohl im Dschungel nicht genuegend Informationen vorhanden waren.");
		}
	}
}