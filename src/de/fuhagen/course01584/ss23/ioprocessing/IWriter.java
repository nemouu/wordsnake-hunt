package de.fuhagen.course01584.ss23.ioprocessing;

import de.fuhagen.course01584.ss23.model.IModel;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Datenmodell und
 * Datenausgabe. Dadurch ist es moeglich in Zukunft das Programm so
 * umzuschreiben, dass es Daten auf eine gewuenschte Art ausgibt. Es ist zum
 * Beispiel moeglich Daten in verschiedenen Dateiformaten auszugeben.
 * 
 * @author Philip Redecker
 *
 */
public interface IWriter {

	/**
	 * Eine Methode, um die Daten, die aktuell im Modell stehen in eine Ausgabedatei
	 * zu schreiben. Hierbei wird der Pfad an dem die Datei gespeichert werden soll
	 * unter <code>dateiName</code> angegeben.
	 * 
	 * @param fileName Der Name, den die erzeugte Datei haben soll.
	 * @throws Exception Eine Ausnahme wird geworfen, wenn beim Schreiben ein Fehler
	 *                   passiert oder der <code>dateiName</code> das falsche Format
	 *                   hat.
	 */
	void writeInFile(String fileName) throws Exception;

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell in der Schreiberklasse
	 * befindet, die das Interface ISchreiber implementiert. Dies ist vor allem fuer
	 * kuenftige Aenderungen und auch fuer damit einhergehende Tests gedacht.
	 * 
	 * @return Wert der Variablen <code>ZuUebergebendesModell</code>.
	 */
	IModel getToBeTransferredModel();

	/**
	 * Es kann das Modell der Schreiberklasse gesetzt werden. So ist es zum Beispiel
	 * moeglich ein Modell zu uebergeben, auch wenn zunaechst der parameterlose
	 * Konstruktor uebergeben wurde. Es ist auch im Allgemeinen moeglich das Modell
	 * nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param toBeTransferredModel Das Modell, das uebergeben werden soll.
	 */
	void setToBeTransferredModel(IModel toBeTransferredModel);

}