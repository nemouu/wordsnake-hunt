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
 * An implementation of the IReader interface that allows reading data from XML
 * files and transferring this data into the program's internal data model.
 * Initially, an 'empty' model is instantiated upon construction, and then it is
 * gradually filled with the data contained in the XML file. It is essential to
 * note that only XML files that conform to the 'schlangenjagd.dtd' file can be
 * read. If they do not conform to this DTD, an error message will be displayed.
 * 
 * @author Philip Redecker
 *
 */
public class ReaderXML implements IReader {
	private IModel transferredModel;

	/**
	 * A parameterless constructor in which an instance of a model is created
	 * directly upon instantiation. This ensures that an internal model is available
	 * when the corresponding methods are called.
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
				if ("Zeit".equals(item.getName())) {
					transferTimeFromFile(item);
				}
				if ("Dschungel".equals(item.getName())) {
					transferJungleFromFile(item);
				}
				if ("Schlangenarten".equals(item.getName())) {
					transferSnakeTypesFromFile(item);
				}
				if ("Schlangen".equals(item.getName())) {
					transferSnakesFromFile(item);
				}
			}
			if (transferredModel.getTime() == null) {
				Double[] time = { 30.0, 0.0 };
				transferredModel.setTime(time);
				transferredModel.setUnitOfTime("s");
			}
		} catch (JDOMException e) {
			System.out.println("Error reading the file. The file found at '" + filePath
					+ "' does not have the correct format. This may lead to program termination.");
			System.out.println();
		} catch (IOException e) {
			System.out.println("Error reading the file. No file was found at '" + filePath
					+ "'. This may lead to program termination.");
			System.out.println();
		}
	}

	private void transferSnakesFromFile(Element item) throws Exception {
		try {
			Solution transferredSolution = new Solution();
			for (Element element : item.getChildren()) {
				transferredSolution.addSnake(new Snake(findSnakeTypeWithID(element.getAttributeValue("art"))));
				for (Element snakeElement : element.getChildren()) {
					transferredSolution.getSnakes().get(transferredSolution.getSnakes().size() - 1)
							.addElement(new SnakeElement(findFieldWithID(snakeElement.getAttributeValue("feld"))));
				}
			}
			transferredModel.setSolution(transferredSolution);
		} catch (Exception e) {
			System.out.println("An error occurred! The data for the snakes in the file is flawed.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferSnakeTypesFromFile(Element item) throws Exception {
		try {
			for (Element element : item.getChildren()) {
				if ("Distanz".equals(element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ"))) {
					INeighborhood transferredNeighborhood = new DistanceNeighborhood(Integer.parseInt(element
							.getChild("Nachbarschaftsstruktur").getChild("Parameter").getAttributeValue("wert")));
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if ("Sprung".equals(element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ"))) {
					List<Integer> transferredParameters = new ArrayList<Integer>();
					for (Element parameter : element.getChild("Nachbarschaftsstruktur").getChildren()) {
						transferredParameters.add(Integer.parseInt(parameter.getAttributeValue("wert")));
					}
					INeighborhood transferredNeighborhood = new JumpNeighborhood(transferredParameters);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Zeichenkette").getText(),
							Integer.parseInt(element.getAttributeValue("punkte")),
							Integer.parseInt(element.getAttributeValue("anzahl"))));
				} else if ("Stern".equals(element.getChild("Nachbarschaftsstruktur").getAttributeValue("typ"))) {
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
			System.out.println("An error occurred! The data for the snake types in the file is flawed.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferJungleFromFile(Element item) throws Exception {
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
			System.out.println("An error occurred! The data for the jungle in the file is flawed.");
			System.out.println();
			throw new Exception();
		}
	}

	private void transferTimeFromFile(Element item) throws Exception {
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
			System.out.println("An error occurred! The data for the time in the file is flawed.");
			System.out.println();
			throw new Exception();
		}
	}

	private SnakeType findSnakeTypeWithID(String id) {
		for (SnakeType snakeType : transferredModel.getSnakeTypes()) {
			if (snakeType.getId().equals(id)) {
				return snakeType;
			}
		}
		return null;
	}

	private Field findFieldWithID(String id) {
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