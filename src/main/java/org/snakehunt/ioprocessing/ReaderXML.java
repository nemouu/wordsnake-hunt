package org.snakehunt.ioprocessing;

import java.io.*;
import java.util.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.input.sax.XMLReaders;

import org.snakehunt.model.*;

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
				if ("Time".equals(item.getName())) {
					transferTimeFromFile(item);
				}
				if ("Jungle".equals(item.getName())) {
					transferJungleFromFile(item);
				}
				if ("SnakeTypes".equals(item.getName())) {
					transferSnakeTypesFromFile(item);
				}
				if ("Snakes".equals(item.getName())) {
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
				transferredSolution.addSnake(new Snake(findSnakeTypeWithID(element.getAttributeValue("type"))));
				for (Element snakeElement : element.getChildren()) {
					transferredSolution.getSnakes().get(transferredSolution.getSnakes().size() - 1)
							.addElement(new SnakeElement(findFieldWithID(snakeElement.getAttributeValue("field"))));
				}
			}
			transferredModel.setSolution(transferredSolution);
		} catch (Exception e) {
			System.out.println("An error occurred! The data for the snakes in the file is flawed.");
			System.out.println();
		}
	}

	private void transferSnakeTypesFromFile(Element item) throws Exception {
		try {
			for (Element element : item.getChildren()) {
				if ("Distance".equals(element.getChild("NeighborhoodStructure").getAttributeValue("type"))) {
					INeighborhood transferredNeighborhood = new DistanceNeighborhood(Integer.parseInt(element
							.getChild("NeighborhoodStructure").getChild("Parameter").getAttributeValue("value")));
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Word").getText(),
							Integer.parseInt(element.getAttributeValue("points")),
							Integer.parseInt(element.getAttributeValue("amount"))));
				} else if ("Jump".equals(element.getChild("NeighborhoodStructure").getAttributeValue("type"))) {
					List<Integer> transferredParameters = new ArrayList<Integer>();
					for (Element parameter : element.getChild("NeighborhoodStructure").getChildren()) {
						transferredParameters.add(Integer.parseInt(parameter.getAttributeValue("value")));
					}
					INeighborhood transferredNeighborhood = new JumpNeighborhood(transferredParameters);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Word").getText(),
							Integer.parseInt(element.getAttributeValue("points")),
							Integer.parseInt(element.getAttributeValue("amount"))));
				} else if ("Star".equals(element.getChild("NeighborhoodStructure").getAttributeValue("type"))) {
					List<Integer> transferredParameters = new ArrayList<Integer>();
					for (Element parameter : element.getChild("NeighborhoodStructure").getChildren()) {
						transferredParameters.add(Integer.parseInt(parameter.getAttributeValue("value")));
					}
					INeighborhood transferredNeighborhood = new StarNeighborhood(transferredParameters);
					transferredModel.addSnakeType(new SnakeType(element.getAttributeValue("id"),
							transferredNeighborhood, element.getChild("Word").getText(),
							Integer.parseInt(element.getAttributeValue("points")),
							Integer.parseInt(element.getAttributeValue("amount"))));
				} else {
					throw new Exception();
				}
			}
		} catch (Exception e) {
			System.out.println("An error occurred! The data for the snake types in the file is flawed.");
			System.out.println();
		}
	}

	private void transferJungleFromFile(Element item) throws Exception {
		try {
			List<Element> children = item.getChildren();
			Jungle transferredJungle = new Jungle(Integer.parseInt(item.getAttributeValue("rows")),
					Integer.parseInt(item.getAttributeValue("cols")), item.getAttributeValue("signs"), 0);
			for (Element element : children) {
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("row"))][Integer
						.parseInt(element.getAttributeValue("col"))]
						.setUsage(Integer.parseInt(element.getAttributeValue("usability")));
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("row"))][Integer
						.parseInt(element.getAttributeValue("col"))]
						.setPoints(Integer.parseInt(element.getAttributeValue("points")));
				transferredJungle.getFields()[Integer.parseInt(element.getAttributeValue("row"))][Integer
						.parseInt(element.getAttributeValue("col"))].setCharacter(element.getText());
			}
			transferredModel.setJungle(transferredJungle);
		} catch (Exception e) {
			System.out.println("An error occurred! The data for the jungle in the file is flawed.");
			System.out.println();
		}
	}

	private void transferTimeFromFile(Element item) throws Exception {
		try {
			Double[] transferredTime = { 0.0, 0.0 };
			String transferredUnitOfTime = item.getAttributeValue("unit");
			if (item.getChildren().size() == 2) {
				transferredTime[0] = Double.parseDouble(item.getChildText("Limit"));
				transferredTime[1] = Double.parseDouble(item.getChildText("Elapsed"));
			} else {
				transferredTime[0] = Double.parseDouble(item.getChildText("Limit"));
			}
			transferredModel.setUnitOfTime(transferredUnitOfTime);
			transferredModel.setTime(transferredTime);
		} catch (Exception e) {
			System.out.println("An error occurred! The data for the time in the file is flawed.");
			System.out.println();
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