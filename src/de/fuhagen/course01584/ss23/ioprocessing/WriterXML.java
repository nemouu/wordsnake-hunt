package de.fuhagen.course01584.ss23.ioprocessing;

import org.jdom2.*;
import org.jdom2.output.*;

import de.fuhagen.course01584.ss23.model.*;

import java.io.*;

/**
 * Eine Implementierung der Schnittstelle ISchreiber, um das Schreiben von Daten
 * des Modelles in XML-Dateien zu ermoeglichen. Es wird zunaechst ein Dokument
 * erstellt und dann werden diesem nach und nach die Informationen des Modelles
 * hinzugefuegt. Schliesslich wird kann dieses Dokument dann ausgegeben und an
 * einer angegebenen Stelle gespeichert werden.
 * 
 * @author Philip Redecker
 *
 */
public class WriterXML implements IWriter {
	private IModel toBeTransferredModel;
	private Document doc;

	/**
	 * Ein parametrisierter Konstruktor fuer die Klasse SchreiberXML. Es wird ein
	 * Modell uebergeben, das in eine Datei geschrieben werden soll. Bei der
	 * Instanziierung wird dieses Modell dann an das interne Modell der
	 * Schreiberklasse uebergeben.
	 * 
	 * @param model Das Modell, das in eine Datei geschrieben werden soll.
	 */
	public WriterXML(IModel model) {
		super();
		this.toBeTransferredModel = model;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public WriterXML() {
		super();
	}

	@Override
	public void writeInFile(String fileName) throws Exception {
		// Es wird geprueft ob der Name der Ausgabedatei ein gueltiges Format hat.
		if (fileName == null || !fileName.endsWith(".xml")) {
			System.out.println("Es ist zu einem Fehler gekommen! Der angegebene Dateipfad zum Speichern hat nicht\ndas "
					+ "richtige Format. Der Pfad muss in der Form 'Dateiname.xml' angegeben werden. Die\nDatei "
					+ "konnte nicht gespeichert werden.");
			System.out.println();
			throw new IllegalArgumentException("Der angegebene Dateipfad zum Speichern hat nicht das "
					+ "richtige Format. Der Pfad muss\nin der Form 'Dateiname.xml' angegeben werden.");
		}

		/*
		 * Es wird das Dokument erstellt, dem nun nach und nach Daten hinzugefuegt
		 * werden, und es wird sichergestellt, dass dieses Dokument den Vorgaben der
		 * Datei 'schlangenjagd.dtd' entspricht.
		 */
		Element root = new Element("Schlangenjagd");
		DocType dType = new DocType("Schlangenjagd", "schlangenjagd.dtd");
		doc = new Document(root, dType);

		// Die Daten des Modelles werden mit Hilfe dieser privaten Methoden uebergeben.
		transferTimeFromModel(doc);
		transferJungleAndFieldsFromModel(doc);
		transferSnakeTypesFromModel(doc);
		transferSnakesFromModel(doc);

		// Das Dokument wird ausgegeben und an der angegebenen Stelle gespeichert.
		XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		xml.output(doc, new FileWriter(fileName));
	}

	private void transferSnakesFromModel(Document doc) {
		/*
		 * Hier werden die Schlangen aus der Loesung des Modelles des Programmes
		 * uebertragen. Es werden hier aber nur Schlangen geschrieben, wenn diese auch
		 * vorher im Modell standen. Sind keine Schlangen vorhanden, wird dieser Schritt
		 * uebersprungen.
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
		 * Hier werden die Schlangenarten des Modelles, mit ID, Punkten, Anzahl und
		 * Nachbarschaftsstruktur, dem Dokument hinzugefuegt.
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
		 * Hier wird der Dschungel des Modelles, mit allen Feldern und den Angaben fuer
		 * Zeilen, Spalten und Zeichenmenge, dem Dokument hinzugefuegt.
		 */
		Element jungle = new Element("Dschungel");
		jungle.setAttribute(
				new Attribute("zeilen", Integer.toString(toBeTransferredModel.getJungle().getRows())));
		jungle.setAttribute(
				new Attribute("spalten", Integer.toString(toBeTransferredModel.getJungle().getColumns())));
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
					field.setAttribute("verwendbarkeit", Integer
							.toString(toBeTransferredModel.getJungle().getFields()[i][j].getUsage()));
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
		 * Hier wird die Zeit zusammen mit der Zeiteinheit, die sich im Modell befindet,
		 * in das Dokument geschrieben.
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
	 * Eine Methode, die die das Dokument, das in dieser Klasse erstellt wird
	 * zurueckgibt. Es ist darauf zu achten, dass die Variable eventuell leer ist,
	 * falls die Methode genutzt wird bevor ueberhaupt ein Dokument erstellt wurde
	 * (dies erledigen die anderen Methoden dieser Klasse). Die Methode ist
	 * hauptsaechlich gedacht, um diese Klasse und ihre Methoden ausreichend testen
	 * zu koennen.
	 * 
	 * @return Der Wert der Variable <code>dokument</code>.
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