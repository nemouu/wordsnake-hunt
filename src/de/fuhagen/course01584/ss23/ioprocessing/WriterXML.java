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
	 * @param modell Das Modell, das in eine Datei geschrieben werden soll.
	 */
	public WriterXML(IModel modell) {
		super();
		this.toBeTransferredModel = modell;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public WriterXML() {
		super();
	}

	@Override
	public void writeInFile(String dateiName) throws Exception {
		// Es wird geprueft ob der Name der Ausgabedatei ein gueltiges Format hat.
		if (dateiName == null || !dateiName.endsWith(".xml")) {
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
		xml.output(doc, new FileWriter(dateiName));
	}

	private void transferSnakesFromModel(Document doc) {
		/*
		 * Hier werden die Schlangen aus der Loesung des Modelles des Programmes
		 * uebertragen. Es werden hier aber nur Schlangen geschrieben, wenn diese auch
		 * vorher im Modell standen. Sind keine Schlangen vorhanden, wird dieser Schritt
		 * uebersprungen.
		 */
		if (toBeTransferredModel.getSolution() != null) {
			Element schlangen = new Element("Schlangen");
			for (Snake schlange : toBeTransferredModel.getSolution().getSchlangen()) {
				Element neueSchlange = new Element("Schlange").setAttribute("art", schlange.getType().getId());
				for (SnakeElement glied : schlange.getElements()) {
					Element neuesGlied = new Element("Schlangenglied");
					neuesGlied.setAttribute("feld", glied.getField().getId());
					neueSchlange.addContent(neuesGlied);
				}
				schlangen.addContent(neueSchlange);
			}
			doc.getRootElement().addContent(schlangen);
		}
	}

	private void transferSnakeTypesFromModel(Document doc) {
		/*
		 * Hier werden die Schlangenarten des Modelles, mit ID, Punkten, Anzahl und
		 * Nachbarschaftsstruktur, dem Dokument hinzugefuegt.
		 */
		Element schlangenarten = new Element("Schlangenarten");
		for (SnakeType art : toBeTransferredModel.getSnakeTypes()) {
			Element schlangenart = new Element("Schlangenart");
			schlangenart.setAttribute("id", art.getId());
			schlangenart.setAttribute("punkte", Integer.toString(art.getPunkte()));
			schlangenart.setAttribute("anzahl", Integer.toString(art.getAnzahl()));
			schlangenart.addContent(new Element("Zeichenkette").setText(art.getZeichenkette()));
			Element nachbarschaft = new Element("Nachbarschaftsstruktur");
			for (int parameter : art.getStruktur().getParameters()) {
				Element neuerParameter = new Element("Parameter").setAttribute("wert", Integer.toString(parameter));
				nachbarschaft.addContent(neuerParameter);
			}
			schlangenart.addContent(nachbarschaft.setAttribute("typ", art.getStruktur().getType()));
			schlangenarten.addContent(schlangenart);
		}
		doc.getRootElement().addContent(schlangenarten);
	}

	private void transferJungleAndFieldsFromModel(Document doc) {
		/*
		 * Hier wird der Dschungel des Modelles, mit allen Feldern und den Angaben fuer
		 * Zeilen, Spalten und Zeichenmenge, dem Dokument hinzugefuegt.
		 */
		Element dschungel = new Element("Dschungel");
		dschungel.setAttribute(
				new Attribute("zeilen", Integer.toString(toBeTransferredModel.getJungle().getRows())));
		dschungel.setAttribute(
				new Attribute("spalten", Integer.toString(toBeTransferredModel.getJungle().getColumns())));
		dschungel.setAttribute(new Attribute("zeichen", toBeTransferredModel.getJungle().getSigns()));
		if (toBeTransferredModel.getJungle().getFields()[0][0].getCharacter() != null) {
			for (int i = 0; i < toBeTransferredModel.getJungle().getFields().length; i++) {
				for (int j = 0; j < toBeTransferredModel.getJungle().getFields()[i].length; j++) {
					Element feld = new Element("Feld");
					feld.setText(toBeTransferredModel.getJungle().getFields()[i][j].getCharacter());
					feld.setAttribute("id", toBeTransferredModel.getJungle().getFields()[i][j].getId());
					feld.setAttribute("zeile",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getRow()));
					feld.setAttribute("spalte",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getColumn()));
					feld.setAttribute("verwendbarkeit", Integer
							.toString(toBeTransferredModel.getJungle().getFields()[i][j].getUsage()));
					feld.setAttribute("punkte",
							Integer.toString(toBeTransferredModel.getJungle().getFields()[i][j].getPoints()));
					dschungel.addContent(feld);
				}
			}
		}
		doc.getRootElement().addContent(dschungel);
	}

	private void transferTimeFromModel(Document doc) {
		/*
		 * Hier wird die Zeit zusammen mit der Zeiteinheit, die sich im Modell befindet,
		 * in das Dokument geschrieben.
		 */
		Element zeit = new Element("Zeit");
		zeit.setAttribute(new Attribute("einheit", toBeTransferredModel.getUnitOfTime()));
		zeit.addContent(new Element("Vorgabe").setText(Double.toString(toBeTransferredModel.getTime()[0])));
		if (toBeTransferredModel.getTime()[1] != 0.0) {
			zeit.addContent(new Element("Abgabe").setText(Double.toString(toBeTransferredModel.getTime()[1])));
		}
		doc.getRootElement().addContent(zeit);
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
	public void setToBeTransferredModel(IModel zuUebergebendesModell) {
		this.toBeTransferredModel = zuUebergebendesModell;
	}
}