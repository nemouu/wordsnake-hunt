package de.fuhagen.course01584.ss23.algorithm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.fuhagen.course01584.ss23.algorithm.SnakeSearch;
import de.fuhagen.course01584.ss23.ioprocessing.IReader;
import de.fuhagen.course01584.ss23.ioprocessing.ReaderXML;
import de.fuhagen.course01584.ss23.model.*;

/**
 * In der Klasse SchlangenSucheTest werden die Methoden der Klasse
 * SchlangenSuche getestet. Hierbei werden erst ein paar einfache Tests
 * durchgefuehrt, die dazu dienen sollen ein paar wichtige Randsituationen
 * abzudecken. Insbesondere das Fehlen eines Dschungels im Modell wird
 * untersucht. Fuer die Fehlerfindung werden entweder die gegebenen Dateien
 * <code>sj_pX_probleminstanz.xml</code> genutzt oder es wird ein Dschungel
 * manuell erstellt. Hinweis: Alle Klassen des Algorithmuspaketes werden auch
 * noch einmal durch die Tests der Hauptkomponente getestet.
 * 
 * @author Philip Redecker
 */
class SnakeSearchTest {

	@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
	@Nested
	class Einfache_Tests {
		@DisplayName("Einfacher Test mit selbst erstellter Probleminstanz.")
		@Test
		void testeSchlangenSucheMitEigenerProbleminstanz() {
			Double[] zeit = { 30.0 };
			String zeiteinheit = "s";
			Jungle bspDschungel = new Jungle(4, 5, "ABCDEFG", 1);
			bspDschungel.setFeld(new Field("F0", 0, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F1", 0, 1, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F2", 0, 2, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F3", 0, 3, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F4", 0, 4, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F5", 1, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F6", 1, 1, 1, 1, "J"));
			bspDschungel.setFeld(new Field("F7", 1, 2, 1, 1, "U"));
			bspDschungel.setFeld(new Field("F8", 1, 3, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F9", 1, 4, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F10", 2, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F11", 2, 1, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F12", 2, 2, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F13", 2, 3, 1, 1, "N"));
			bspDschungel.setFeld(new Field("F14", 2, 4, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F15", 3, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F16", 3, 1, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F17", 3, 2, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F18", 3, 3, 1, 1, "T"));
			bspDschungel.setFeld(new Field("F19", 3, 4, 1, 1, "I"));
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "JUNIT", 1, 1);
			IModel modell = new ProblemModel();
			modell.setZeit(zeit);
			modell.setZeiteinheit(zeiteinheit);
			modell.setDschungel(bspDschungel);
			modell.addSchlangenart(art);
			SnakeSearch suche = new SnakeSearch(modell);
			suche.sucheSchlangen();
			for (int i = 0; i < 5; i++) {
				assertEquals("JUNIT".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(0).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit selbst erstellter Probleminstanz und zwei Loesungsschlangen.")
		@Test
		void testeSchlangenSucheMitEigenerProbleminstanzZweiSchlangen() {
			Double[] zeit = { 30.0 };
			String zeiteinheit = "s";
			Jungle bspDschungel = new Jungle(4, 5, "ABCDEFG", 1);
			bspDschungel.setFeld(new Field("F0", 0, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F1", 0, 1, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F2", 0, 2, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F3", 0, 3, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F4", 0, 4, 1, 1, "N"));
			bspDschungel.setFeld(new Field("F5", 1, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F6", 1, 1, 1, 1, "J"));
			bspDschungel.setFeld(new Field("F7", 1, 2, 1, 1, "U"));
			bspDschungel.setFeld(new Field("F8", 1, 3, 1, 1, "E"));
			bspDschungel.setFeld(new Field("F9", 1, 4, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F10", 2, 0, 1, 1, "H"));
			bspDschungel.setFeld(new Field("F11", 2, 1, 1, 1, "A"));
			bspDschungel.setFeld(new Field("F12", 2, 2, 1, 1, "G"));
			bspDschungel.setFeld(new Field("F13", 2, 3, 1, 1, "N"));
			bspDschungel.setFeld(new Field("F14", 2, 4, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F15", 3, 0, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F16", 3, 1, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F17", 3, 2, 1, 1, "O"));
			bspDschungel.setFeld(new Field("F18", 3, 3, 1, 1, "T"));
			bspDschungel.setFeld(new Field("F19", 3, 4, 1, 1, "I"));
			SnakeType art1 = new SnakeType("A0", new DistanceNeighborhood(1), "JUNIT", 1, 1);
			SnakeType art2 = new SnakeType("A1", new DistanceNeighborhood(1), "HAGEN", 1, 1);
			IModel modell = new ProblemModel();
			modell.setZeit(zeit);
			modell.setZeiteinheit(zeiteinheit);
			modell.setDschungel(bspDschungel);
			modell.addSchlangenart(art1);
			modell.addSchlangenart(art2);
			SnakeSearch suche = new SnakeSearch(modell);
			suche.sucheSchlangen();
			for (int i = 0; i < 5; i++) {
				assertEquals("JUNIT".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(0).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("HAGEN".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(1).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit eingelesener Probleminstanz.")
		@Test
		void testeSchlangenSucheMitEingelesenerProbleminstanz() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p1_probleminstanz.xml");
			IModel modell = xmlLeser.getUebergebenesModell();
			SnakeSearch suche = new SnakeSearch(modell);
			SnakeType art = new SnakeType("A0", new DistanceNeighborhood(1), "FERNUNI", 1, 1);
			SnakeElement glied1 = new SnakeElement(modell.getDschungel().getFelder()[0][0]);
			SnakeElement glied2 = new SnakeElement(modell.getDschungel().getFelder()[0][1]);
			SnakeElement glied3 = new SnakeElement(modell.getDschungel().getFelder()[0][2]);
			SnakeElement glied4 = new SnakeElement(modell.getDschungel().getFelder()[0][3]);
			SnakeElement glied5 = new SnakeElement(modell.getDschungel().getFelder()[1][3]);
			SnakeElement glied6 = new SnakeElement(modell.getDschungel().getFelder()[1][2]);
			SnakeElement glied7 = new SnakeElement(modell.getDschungel().getFelder()[1][1]);
			Snake schlange1 = new Snake(art);
			schlange1.addGlied(glied1);
			schlange1.addGlied(glied2);
			schlange1.addGlied(glied3);
			schlange1.addGlied(glied4);
			schlange1.addGlied(glied5);
			schlange1.addGlied(glied6);
			schlange1.addGlied(glied7);
			suche.sucheSchlangen();
			for (int i = 0; i < 7; i++) {
				assertEquals(schlange1.getGlieder().get(i).getFeld().getZeichen(),
						suche.getLoesung().getSchlangen().get(0).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder an der Stelle " + i + " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit eingelesener Probleminstanz mit mehreren Loesungsschlangen.")
		@Test
		void testeSchlangenSucheMitEingelesenerProbleminstanzMehrerenSchlangen() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p2_probleminstanz.xml");
			IModel modell = xmlLeser.getUebergebenesModell();
			SnakeSearch suche = new SnakeSearch(modell);
			suche.sucheSchlangen();
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESES".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(0).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der ersten Schlange an der Stelle " + i + " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESEN".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(1).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der zweiten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 6; i++) {
				assertEquals("DIESER".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(2).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der dritten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 5; i++) {
				assertEquals("DIESE".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(3).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der vierten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 4; i++) {
				assertEquals("DIES".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(4).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der fuenften Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
			for (int i = 0; i < 3; i++) {
				assertEquals("DIE".substring(i, i + 1),
						suche.getLoesung().getSchlangen().get(5).getGlieder().get(i).getFeld().getZeichen(),
						"\nDie Zeichen der Felder der sechsten Schlange an der Stelle " + i
								+ " stimmen nicht ueberein.");
			}
		}

		@DisplayName("Einfacher Test mit leerer Probleminstanz.")
		@Test
		void testeSchlangenSucheMitLeererProbleminstanz() {
			IModel modell = new ProblemModel();
			Jungle bspDschungel = new Jungle(3, 3, "ABC", 1);
			modell.setDschungel(bspDschungel);
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(modell),
					"\nDas Uebergeben eines unvollstaendigen Modelles loest keine Ausnahme aus.");
		}

		@DisplayName("Einfacher Test mit leerer eingelesener Probleminstanz.")
		@Test
		void testeSchlangenSucheMitEingelesenerLeererProbleminstanz() throws Exception {
			IReader xmlLeser = new ReaderXML();
			xmlLeser.leseDatei("res/sj_p1_unvollstaendig.xml");
			assertThrows(IllegalArgumentException.class, () -> new SnakeSearch(xmlLeser.getUebergebenesModell()),
					"\nDas Uebergeben eines unvollstaendigen Modelles loest keine Ausnahme aus.");
		}
	}
}
