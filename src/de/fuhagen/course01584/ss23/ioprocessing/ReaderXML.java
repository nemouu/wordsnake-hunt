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
	public void readFile(String dateiPfad) throws Exception {
		try {
			SAXBuilder builder = new SAXBuilder(XMLReaders.DTDVALIDATING);
			Document xmlDatei = null;
			xmlDatei = builder.build(new File(dateiPfad));
			Element wurzel = xmlDatei.getRootElement();
			List<Element> items = wurzel.getChildren();
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
				Double[] zeit = { 30.0, 0.0 };
				transferredModel.setTime(zeit);
				transferredModel.setUnitOfTime("s");
			}
		} catch (JDOMException e) {
			System.out.println("Fehler beim Einlesen. Die unter '" + dateiPfad
					+ "' gefundene Datei hat nicht das richtige\nFormat. Dies kann zum Programmabbruch fuehren.");
			System.out.println();
		} catch (IOException e) {
			System.out.println("Fehler beim Einlesen. Unter '" + dateiPfad
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
			Solution uebergebeneLoesung = new Solution();
			for (Element element : item.getChildren()) {
				uebergebeneLoesung.addSchlange(new Snake(findeSnakeTypeWithID(element.getAttributeValue("art"))));
				for (Element glied : element.getChildren()) {
					uebergebeneLoesung.getSchlangen().get(uebergebeneLoesung.getSchlangen().size() - 1)
							.addElement(new SnakeElement(findFieldWithID(glied.getAttributeValue("feld"))));
				}
			}
			transferredModel.setSolution(uebergebeneLoesung);
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
					INeighborhood uebergebeneNachbarschaft = new DistanceNeighborhood(Integer.parseInt(element
							.getChild("Nachbarschaftsstruktur").getChild("Parameter").getAttributeValue("wert")));
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							uebergebeneNachbarschaft, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if (element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ").equals("Sprung")) {
					List<Integer> uebergebeneParameter = new ArrayList<Integer>();
					for (Element parameter : element.getChild("Nachbarschaftsstruktur").getChildren()) {
						uebergebeneParameter.add(Integer.parseInt(parameter.getAttributeValue("wert")));
					}
					INeighborhood uebergebeneNachbarschaft = new JumpNeighborhood(uebergebeneParameter);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							uebergebeneNachbarschaft, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if (element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ").equals("Stern")) {
					List<Integer> uebergebeneParameter = new ArrayList<Integer>();
					for (Element parameter : element.getChild("Nachbarschaftsstruktur").getChildren()) {
						uebergebeneParameter.add(Integer.parseInt(parameter.getAttributeValue("wert")));
					}
					INeighborhood uebergebeneNachbarschaft = new StarNeighborhood(uebergebeneParameter);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							uebergebeneNachbarschaft, element.getChild("Zeichenkette").getText(),
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
			Jungle uebergebenerDschungel = new Jungle(Integer.parseInt(item.getAttributeValue("zeilen")),
					Integer.parseInt(item.getAttributeValue("spalten")), item.getAttributeValue("zeichen"), 0);
			for (Element element : children) {
				uebergebenerDschungel.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))]
						.setUsage(Integer.parseInt(element.getAttributeValue("verwendbarkeit")));
				uebergebenerDschungel.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))]
						.setPoints(Integer.parseInt(element.getAttributeValue("punkte")));
				uebergebenerDschungel.getFields()[Integer.parseInt(element.getAttributeValue("zeile"))][Integer
						.parseInt(element.getAttributeValue("spalte"))].setCharacter(element.getText());
			}
			transferredModel.setJungle(uebergebenerDschungel);
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
			Double[] uebergebendeZeit = { 0.0, 0.0 };
			String uebergebeneZeiteinheit = item.getAttributeValue("einheit");
			if (item.getChildren().size() == 2) {
				uebergebendeZeit[0] = Double.parseDouble(item.getChildText("Vorgabe"));
				uebergebendeZeit[1] = Double.parseDouble(item.getChildText("Abgabe"));
			} else {
				uebergebendeZeit[0] = Double.parseDouble(item.getChildText("Vorgabe"));
			}
			transferredModel.setUnitOfTime(uebergebeneZeiteinheit);
			transferredModel.setTime(uebergebendeZeit);
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
		for (SnakeType art : transferredModel.getSnakeTypes()) {
			if (art.getId().equals(id)) {
				return art;
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