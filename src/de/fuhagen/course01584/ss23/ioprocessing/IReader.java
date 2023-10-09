package de.fuhagen.course01584.ss23.ioprocessing;

import de.fuhagen.course01584.ss23.model.IModel;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Datenmodell und
 * Dateneingabe. Dadurch ist es moeglich in Zukunft das Programm durch eine
 * andere Form der Dateneingabe mit Daten zu vorsorgen, sollte dies gewuenscht
 * sein. Es ist zum Beispiel moeglich Daten in verschiedenen Dateiformaten
 * bereitzustellen.
 * 
 * @author Philip Redecker
 *
 */
public interface IReader {

	/**
	 * Die Eingabedatei wird unter <code>dateiPfad</code> gesucht. Wird eine Datei
	 * gefunden, wird geprueft, ob sie das richtige Format hat und anschliessend
	 * werden die Daten, die in der Datei stehen in das Datenmodell geschrieben.
	 * 
	 * @param dateiPfad Der Pfad zu der einzulesenden Datei.
	 * @throws Exception Eine Ausnahme wird geworfen, wenn keine Datei gefunden wird
	 *                   oder wenn sie das falsche Format hat.
	 */
	void readFile(String dateiPfad) throws Exception;

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell in der Leserklasse
	 * befindet, die das Interface ILeser implementiert. Dies ist vor allem fuer
	 * kuenftige Aenderungen und auch fuer damit einhergehende Tests gedacht.
	 * 
	 * @return Wert der Variablen <code>uebergebenesModell</code>.
	 */
	IModel getTransferredModel();

}