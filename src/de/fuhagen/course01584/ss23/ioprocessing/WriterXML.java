package de.fuhagen.course01584.ss23.ioprocessing;

import org.jdom2.*;
import org.jdom2.output.*;

import de.fuhagen.course01584.ss23.model.*;

import java.io.*;

/**
 * An implementation of the IWriter interface to enable writing data from the
 * model to XML files. A document is created first, and then the model's
 * information is gradually added to it. Finally, this document can be output
 * and saved at a specified location.
 * 
 * @author Philip Redecker
 *
 */
public class WriterXML implements IWriter {
	private IModel toBeTransferredModel;
	private Document doc;

	/**
	 * A parameterized constructor for the WriterXML class. A model is passed that
	 * needs to be written to a file. During instantiation, this model is then
	 * transferred to the internal model of the Writer class.
	 * 
	 * @param model The model to be written to a file.
	 */
	public WriterXML(IModel model) {
		super();
		this.toBeTransferredModel = model;
	}

	/**
	 * A parameterless constructor, allowing this class to be used for future
	 * program changes, such as for testing purposes.
	 */
	public WriterXML() {
		super();
	}

	@Override
	public void writeInFile(String fileName) throws Exception {
		// Check if the output file name has a valid format.
		if (fileName == null || !fileName.endsWith(".xml")) {
			System.out.println("An error occurred! The specified file path for saving does not have "
					+ "the correct format. The path must be in the form 'filename.xml'. The file "
					+ "could not be saved.");
			System.out.println();
			throw new IllegalArgumentException("The specified file path for saving does not have the correct format. "
					+ "The path must be in the form 'filename.xml'.");
		}

		/*
		 * Create the document to which data will be gradually added, and ensure that
		 * this document conforms to the specifications of the 'schlangenjagd.dtd' file.
		 */
		Element root = new Element("Schlangenjagd");
		DocType dType = new DocType("Schlangenjagd", "schlangenjagd.dtd");
		doc = new Document(root, dType);

		// Transfer model data using private methods.
		transferTimeFromModel(doc);
		transferJungleAndFieldsFromModel(doc);
		transferSnakeTypesFromModel(doc);
		transferSnakesFromModel(doc);

		// Output the document and save it at the specified location.
		XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		xml.output(doc, new FileWriter(fileName));
	}

	private void transferSnakesFromModel(Document doc) {
		/*
		 * Here, the snakes from the model's solution are transferred. However, snakes
		 * are only written here if they were present in the model beforehand. If there
		 * are no snakes, this step is skipped.
		 */
		if (toBeTransferredModel.getSolution() != null) {
			Element snakes = new Element("Schlangen");
			for (Snake snake : toBeTransferredModel.getSolution().getSnakes()) {
				Element newSnake = new Element("Schlange").setAttribute("art", snake.getType().getId());
				for (SnakeElement snakeElement : snake.getElements()) {
					Element newSnakeElement = new Element("Schlangenglied");
					newSnakeElement.setAttribute("feld", snakeElement.getField().getId());
					newSnake.addContent(newSnakeElement);
				}
				snakes.addContent(newSnake);
			}
			doc.getRootElement().addContent(snakes);
		}
	}

	private void transferSnakeTypesFromModel(Document doc) {
		/*
		 * Here, the snake types from the model, with ID, points, amount, and
		 * neighborhood structure, are added to the document.
		 */
		Element snakeTypes = new Element("Schlangenarten");
		for (SnakeType type : toBeTransferredModel.getSnakeTypes()) {
			Element snakeType = new Element("Schlangenart");
			snakeType.setAttribute("id", type.getId());
			snakeType.setAttribute("punkte", Integer.toString(type.getPoints()));
			snakeType.setAttribute("anzahl", Integer.toString(type.getAmount()));
			snakeType.addContent(new Element("Zeichenkette").setText(type.getSigns()));
			Element neighborhood = new Element("Nachbarschaftsstruktur");
			for (int parameters : type.getStructure().getParameters()) {
				Element newParameters = new Element("Parameter").setAttribute("wert", Integer.toString(parameters));
				neighborhood.addContent(newParameters);
			}
			snakeType.addContent(neighborhood.setAttribute("typ", type.getStructure().getType()));
			snakeTypes.addContent(snakeType);
		}
		doc.getRootElement().addContent(snakeTypes);
	}

	private void transferJungleAndFieldsFromModel(Document doc) {
		/*
		 * Here, the jungle from the model, along with all fields and information about
		 * rows, columns, and characters, is added to the document.
		 */
		Element jungle = new Element("Dschungel");
		jungle.setAttribute(new Attribute("zeilen", Integer.toString(toBeTransferredModel.getJungle().getRows())));
		jungle.setAttribute(new Attribute("spalten", Integer.toString(toBeTransferredModel.getJungle().getColumns())));
		jungle.setAttribute(new Attribute("zeichen", toBeTransferredModel.getJungle().getSigns()));
		if (toBeTransferredModel.getJungle().getFields()[0][0].getCharacter() != null) {
			for (int i = 0; i < toBeTransferredModel.getJungle().getFields().length; i++) {
				for (int j = 0; j < toBeTransferredModel.getJungle().getFields()[i].length; j++) {
					Element field = new Element("Feld");
					field.setText(toBeTransferredModel.getJungle().getFields()[i][j].getCharacter());
					field.setAttribute("id", toBeTransferredModel.getJungle().getFields()[i][j].getId());
					field.setAttribute("zeile",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getRow()));
					field.setAttribute("spalte",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getColumn()));
					field.setAttribute("verwendbarkeit",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getUsage()));
					field.setAttribute("punkte",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getPoints()));
					jungle.addContent(field);
				}
			}
		}
		doc.getRootElement().addContent(jungle);
	}

	private void transferTimeFromModel(Document doc) {
		/*
		 * Here, the time along with the time unit in the model is written into the
		 * document.
		 */
		Element time = new Element("Zeit");
		time.setAttribute(new Attribute("einheit", toBeTransferredModel.getUnitOfTime()));
		time.addContent(new Element("Vorgabe").setText(Double.toString(toBeTransferredModel.getTime()[0])));
		if (toBeTransferredModel.getTime()[1] != 0.0) {
			time.addContent(new Element("Abgabe").setText(Double.toString(toBeTransferredModel.getTime()[1])));
		}
		doc.getRootElement().addContent(time);
	}

	/**
	 * A method that returns the document created in this class. It should be noted
	 * that the variable may be empty if the method is used before a document is
	 * even created (this is done by the other methods of this class). The method is
	 * mainly intended to sufficiently test this class and its methods.
	 * 
	 * @return The value of the variable <code>document</code>.
	 */
	public Document getDocument() {
		return doc;
	}

	@Override
	public IModel getToBeTransferredModel() {
		return toBeTransferredModel;
	}

	@Override
	public void setToBeTransferredModel(IModel toBeTransferredModel) {
		this.toBeTransferredModel = toBeTransferredModel;
	}
}