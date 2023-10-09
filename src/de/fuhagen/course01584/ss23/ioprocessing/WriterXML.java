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
	private IModel zuUebergebendesModell;
	private Document dokument;

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
		this.zuUebergebendesModell = modell;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public WriterXML() {
		super();
	}

	@Override
	public void schreibeInDatei(String dateiName) throws Exception {
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
		dokument = new Document(root, dType);

		// Die Daten des Modelles werden mit Hilfe dieser privaten Methoden uebergeben.
		uebergebeZeitAusModell(dokument);
		uebergebeDschungelUndFelderAusModell(dokument);
		uebergebeSchlangenartenAusModell(dokument);
		uebergebeSchlangenAusModell(dokument);

		// Das Dokument wird ausgegeben und an der angegebenen Stelle gespeichert.
		XMLOutputter xml = new XMLOutputter();
		xml.setFormat(Format.getPrettyFormat());
		xml.output(dokument, new FileWriter(dateiName));
	}

	private void uebergebeSchlangenAusModell(Document doc) {
		/*
		 * Hier werden die Schlangen aus der Loesung des Modelles des Programmes
		 * uebertragen. Es werden hier aber nur Schlangen geschrieben, wenn diese auch
		 * vorher im Modell standen. Sind keine Schlangen vorhanden, wird dieser Schritt
		 * uebersprungen.
		 */
		if (zuUebergebendesModell.getLoesung() != null) {
			Element schlangen = new Element("Schlangen");
			for (Snake schlange : zuUebergebendesModell.getLoesung().getSchlangen()) {
				Element neueSchlange = new Element("Schlange").setAttribute("art", schlange.getArt().getId());
				for (SnakeElement glied : schlange.getGlieder()) {
					Element neuesGlied = new Element("Schlangenglied");
					neuesGlied.setAttribute("feld", glied.getFeld().getId());
					neueSchlange.addContent(neuesGlied);
				}
				schlangen.addContent(neueSchlange);
			}
			doc.getRootElement().addContent(schlangen);
		}
	}

	private void uebergebeSchlangenartenAusModell(Document doc) {
		/*
		 * Hier werden die Schlangenarten des Modelles, mit ID, Punkten, Anzahl und
		 * Nachbarschaftsstruktur, dem Dokument hinzugefuegt.
		 */
		Element schlangenarten = new Element("Schlangenarten");
		for (SnakeType art : zuUebergebendesModell.getSchlangenarten()) {
			Element schlangenart = new Element("Schlangenart");
			schlangenart.setAttribute("id", art.getId());
			schlangenart.setAttribute("punkte", Integer.toString(art.getPunkte()));
			schlangenart.setAttribute("anzahl", Integer.toString(art.getAnzahl()));
			schlangenart.addContent(new Element("Zeichenkette").setText(art.getZeichenkette()));
			Element nachbarschaft = new Element("Nachbarschaftsstruktur");
			for (int parameter : art.getStruktur().getParameter()) {
				Element neuerParameter = new Element("Parameter").setAttribute("wert", Integer.toString(parameter));
				nachbarschaft.addContent(neuerParameter);
			}
			schlangenart.addContent(nachbarschaft.setAttribute("typ", art.getStruktur().getArt()));
			schlangenarten.addContent(schlangenart);
		}
		doc.getRootElement().addContent(schlangenarten);
	}

	private void uebergebeDschungelUndFelderAusModell(Document doc) {
		/*
		 * Hier wird der Dschungel des Modelles, mit allen Feldern und den Angaben fuer
		 * Zeilen, Spalten und Zeichenmenge, dem Dokument hinzugefuegt.
		 */
		Element dschungel = new Element("Dschungel");
		dschungel.setAttribute(
				new Attribute("zeilen", Integer.toString(zuUebergebendesModell.getDschungel().getZeilen())));
		dschungel.setAttribute(
				new Attribute("spalten", Integer.toString(zuUebergebendesModell.getDschungel().getSpalten())));
		dschungel.setAttribute(new Attribute("zeichen", zuUebergebendesModell.getDschungel().getZeichenmenge()));
		if (zuUebergebendesModell.getDschungel().getFelder()[0][0].getZeichen() != null) {
			for (int i = 0; i < zuUebergebendesModell.getDschungel().getFelder().length; i++) {
				for (int j = 0; j < zuUebergebendesModell.getDschungel().getFelder()[i].length; j++) {
					Element feld = new Element("Feld");
					feld.setText(zuUebergebendesModell.getDschungel().getFelder()[i][j].getZeichen());
					feld.setAttribute("id", zuUebergebendesModell.getDschungel().getFelder()[i][j].getId());
					feld.setAttribute("zeile",
							Integer.toString(zuUebergebendesModell.getDschungel().getFelder()[i][j].getZeile()));
					feld.setAttribute("spalte",
							Integer.toString(zuUebergebendesModell.getDschungel().getFelder()[i][j].getSpalte()));
					feld.setAttribute("verwendbarkeit", Integer
							.toString(zuUebergebendesModell.getDschungel().getFelder()[i][j].getVerwendbarkeit()));
					feld.setAttribute("punkte",
							Integer.toString(zuUebergebendesModell.getDschungel().getFelder()[i][j].getPunkte()));
					dschungel.addContent(feld);
				}
			}
		}
		doc.getRootElement().addContent(dschungel);
	}

	private void uebergebeZeitAusModell(Document doc) {
		/*
		 * Hier wird die Zeit zusammen mit der Zeiteinheit, die sich im Modell befindet,
		 * in das Dokument geschrieben.
		 */
		Element zeit = new Element("Zeit");
		zeit.setAttribute(new Attribute("einheit", zuUebergebendesModell.getZeiteinheit()));
		zeit.addContent(new Element("Vorgabe").setText(Double.toString(zuUebergebendesModell.getZeit()[0])));
		if (zuUebergebendesModell.getZeit()[1] != 0.0) {
			zeit.addContent(new Element("Abgabe").setText(Double.toString(zuUebergebendesModell.getZeit()[1])));
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
	public Document getDokument() {
		return dokument;
	}

	@Override
	public IModel getZuUebergebendesModell() {
		return zuUebergebendesModell;
	}

	@Override
	public void setZuUebergebendesModell(IModel zuUebergebendesModell) {
		this.zuUebergebendesModell = zuUebergebendesModell;
	}
}