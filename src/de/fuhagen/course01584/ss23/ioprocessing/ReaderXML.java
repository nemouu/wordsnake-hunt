package de.fuhagen.course01584.ss23.ioprocessing;

import java.io.*;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

import de.fuhagen.course01584.ss23.model.*;

/**
 * Eine Implementierung der Schnittstelle ILeser, die es ermoeglicht Daten aus
 * XML-Dateien einzulesen und diese Daten in das interne Modell des Programmes
 * zu uebertragen. Dazu wird zunaechst ein 'leeres' Modell instanziiert und dann
 * wird dieses nach und nach mit den Daten befuellt, die in der XML-Datei
 * stehen. Wichtig ist hierbei, dass insbesondere nur XML-Datei eingelesen
 * werden koennen, die der Datei <code>schlangenjagd.dtd</code> entsprechen.
 * Entsprechen sie dieser DTD nicht wird eine Fehlermeldung ausgegeben.
 * 
 * 
 * @author Philip Redecker
 *
 */
public class ReaderXML implements IReader {
	private IModel transferredModel;

	/**
	 * Ein parameterloser Konstruktor in dem bei Instanziierung direkt eine Instanz
	 * eines Modelles erstellt wird, sodass bei Aufruf der entsprechenden Methoden
	 * direkt ein internes Modell verfuegbar ist, in das die Daten uebertragen
	 * werden koennen.
	 */
	public ReaderXML() {
		super();
		this.transferredModel = new ProblemModel();
	}

	@Override
	public void readFile(String filePath) throws Exception {
		try {
			SAXBuilder builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
			Document xmlFile = null;
			xmlFile = builder.build(new File(filePath));
			Element root = xmlFile.getRootElement();
			List<Element> items = root.getChildren();
			for (Element item : items) {
				if (item.getName() == "Zeit") {
					transferTimeFromFile(item);
				}
				if (item.getName() == "Dschungel") {
					transferJungleFromFile(item);
				}
				if (item.getName() == "Schlangenarten") {
					transferSnakeTypesFromFile(item);
				}
				if (item.getName() == "Schlangen") {
					transferSnakesFromFile(item);
				}
			}
			if (transferredModel.getTime() == null) {
				Double[] time = { 30.0, 0.0 };
				transferredModel.setTime(time);
				transferredModel.setUnitOfTime("s");
			}
		} catch (JDOMException e) {
			System.out.println("Fehler beim Einlesen. Die unter '" + filePath
					+ "' gefundene Datei hat nicht das richtige\nFormat. Dies kann zum Programmabbruch fuehren.");
			System.out.println();
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen. Unter '" + filePath
					+ "' wurde keine Datei gefunden. Dies kann\nzum Programmabbruch fuehren.");
			System.out.println();
		}
	}

	private void transferSnakesFromFile(Element item) throws Exception {
		/*
		 * Hier werden die Schlangen, die in der Eingabedatei stehen, in das Modell
		 * geschrieben. Haben die Daten ein ungueltiges Format, so wird eine Ausnahme
		 * abgefangen und eine Fehlermeldung ausgegeben.
		 */
		try {
			Solution transferredSolution = new Solution();
			for (Element element : item.getChildren()) {
				transferredSolution.addSnake(new Snake(findeSnakeTypeWithID(element.getAttributeValue("art"))));
				for (Element snakeElement : element.getChildren()) {
					transferredSolution.getSnakes().get(transferredSolution.getSnakes().size() - 1)
							.addElement(new SnakeElement(findFieldWithID(snakeElement.getAttributeValue("feld"))));
				}
			}
			transferredModel.setSolution(transferredSolution);
		} catch (Exception e) {
			System.out.println(
					"Es ist zu einem Fehler gekommen! Die Daten fuer die Schlangen der eingelesenen Datei sind fehlerhaft.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferSnakeTypesFromFile(Element item) throws Exception {
		/*
		 * Hier werden die Schlangenarten, die in der Eingabedatei stehen, in das Modell
		 * geschrieben. Haben die Daten ein ungueltiges Format, so wird eine Ausnahme
		 * abgefangen und eine Fehlermeldung ausgegeben. Ausserdem wird eine Ausnahme
		 * erzeugt, wenn eine Schlangenart eine Nachbarschaftsstruktur hat, die dem
		 * Modell aktuell nicht bekannt ist. Das heisst, wenn dem Modell eine
		 * Nachbarschaftsstruktur hinzugefuegt werden soll, so muessen auch hier
		 * Anpassungen vorgenommen werden.
		 */
		try {
			for (Element element : item.getChildren()) {
				if (element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ").equals("Distanz")) {
					INeighborhood transferredNeighborhood = new DistanceNeighborhood(Integer.parseInt(element
							.getChild("Nachbarschaftsstruktur").getChild("Parameter").getAttributeValue("wert")));
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if (element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ").equals("Sprung")) {
					List<Integer> transferredParameters = new ArrayList<Integer>();
					for (Element parameter : element.getChild("Nachbarschaftsstruktur").getChildren()) {
						transferredParameters.add(Integer.parseInt(parameter.getAttributeValue("wert")));
					}
					INeighborhood transferredNeighborhood = new JumpNeighborhood(transferredParameters);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if (element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ").equals("Stern")) {
					List<Integer> transferredParameters = new ArrayList<Integer>();
					for (Element parameter : element.getChild("Nachbarschaftsstruktur").getChildren()) {
						transferredParameters.add(Integer.parseInt(parameter.getAttributeValue("wert")));
					}
					INeighborhood transferredNeighborhood = new StarNeighborhood(transferredParameters);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			System.out.println(
					"Es ist zu einem Fehler gekommen! Die Daten fuer die Schlangenarten der eingelesenen Datei sind fehlerhaft.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferJungleFromFile(Element item) throws Exception {
		/*
		 * Hier wird der Dschungel, der in der Eingabedatei steht, in das Modell
		 * geschrieben. Dabei wird erst ein leerer Dschungel erzeugt und danach wird
		 * dieser nach und nach mit den Daten der eingelesenen Datei gefuellt. Haben die
		 * Daten ein ungueltiges Format, so wird eine Ausnahme abgefangen und eine
		 * Fehlermeldung ausgegeben.
		 */
		try {
			List<Element> children = item.getChildren();
			Jungle transferredJungle = new Jungle(Integer.parseInt(item.getAttributeValue("zeilen")),
					Integer.parseInt(item.getAttributeValue("spalten")), item.getAttributeValue("zeichen"), 0);
			for (Element element : children) {
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))]
						.setUsage(Integer.parseInt(element.getAttributeValue("verwendbarkeit")));
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))]
						.setPoints(Integer.parseInt(element.getAttributeValue("punkte")));
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))].setCharacter(element.getText());
			}
			transferredModel.setJungle(transferredJungle);
		} catch (Exception e) {
			System.out.println(
					"Es ist zu einem Fehler gekommen! Die Daten fuer den Dschungel der eingelesenen Datei sind fehlerhaft.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferTimeFromFile(Element item) throws Exception {
		/*
		 * Hier werden die Zeitangaben, die in der Eingabedatei stehen, in das Modell
		 * geschrieben. Hierbei wird auf die Groesse der Zeitangabe der Datei Ruecksicht
		 * genommen. Haben die Daten ein ungueltiges Format, so wird eine Ausnahme
		 * abgefangen und eine Fehlermeldung ausgegeben.
		 */
		try {
			Double[] transferredTime = { 0.0, 0.0 };
			String transferredUnitOfTime = item.getAttributeValue("einheit");
			if (item.getChildren().size() == 2) {
				transferredTime[0] = Double.parseDouble(item.getChildText("Vorgabe"));
				transferredTime[1] = Double.parseDouble(item.getChildText("Abgabe"));
			} else {
				transferredTime[0] = Double.parseDouble(item.getChildText("Vorgabe"));
			}
			transferredModel.setUnitOfTime(transferredUnitOfTime);
			transferredModel.setTime(transferredTime);
		} catch (Exception e) {
			System.out.println(
					"Es ist zu einem Fehler gekommen! Die Daten fuer die Zeit der eingelesenen Datei sind fehlerhaft.");
			System.out.println();
			throw new Exception();
		}
	}

	private SnakeType findeSnakeTypeWithID(String id) {
		/*
		 * Eine Hilfsmethode, um die Schlangen aus einer Datei in das Modell zu
		 * uebertragen.
		 */
		for (SnakeType snakeType : transferredModel.getSnakeTypes()) {
			if (snakeType.getId().equals(id)) {
				return snakeType;
			}
		}
		return null;
	}

	private Field findFieldWithID(String id) {
		/*
		 * Eine Hilfsmethode, um die Schlangen aus einer Datei in das Modell zu
		 * uebertragen.
		 */
		for (int i = 0; i < transferredModel.getJungle().getRows(); i++) {
			for (int j = 0; j < transferredModel.getJungle().getColumns(); j++) {
				if (transferredModel.getJungle().getFields()[i][j].getId().equals(id)) {
					return transferredModel.getJungle().getFields()[i][j];
				}
			}
		}
		return null;
	}

	@Override
	public IModel getTransferredModel() {
		return transferredModel;
	}
}