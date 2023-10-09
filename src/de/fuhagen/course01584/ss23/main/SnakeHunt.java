package de.fuhagen.course01584.ss23.main;

import java.util.*;

import de.fuhagen.course01584.ss23.algorithm.*;
import de.fuhagen.course01584.ss23.ioprocessing.*;
import de.fuhagen.course01584.ss23.model.*;
import de.fuhagen.course01584.ss23.view.ViewText;
import de.fuhagen.course01584.ss23.view.IView;

/**
 * Eine Klasse, die die Schnittstelle SchlangenjagdAPI implementiert. Es wird
 * durch die implementierten Methoden des Interfaces moeglich das Programm im
 * API zu benutzen. Ausserdem werden einige private Methoden bereitgestellt, die
 * eine Nutzung des Programmes ueber die <code>Main</code> Methode und damit
 * ueber eine Konsole oder ein Terminal moeglich machen.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeHunt implements SnakeHuntAPI {
	private IModel model;

	/**
	 * Der parameterlose Konstruktor der Klasse Schlangenjagd.
	 */
	public SnakeHunt() {
		super();
	}

	@Override
	public boolean solveProblem(String xmlEingabeDatei, String xmlAusgabeDatei) {
		try {
			readInAndTransferModel(xmlEingabeDatei);
			searchSnakesInCurrentModel();
			writeCurrentModelInFile(xmlAusgabeDatei);
			return true;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen. Die Schlangensuche wird abgebrochen.");
			System.out.println();
			return false;
		}
	}

	@Override
	public boolean generateProblem(String xmlEingabeDatei, String xmlAusgabeDatei) {
		try {
			readInAndTransferModel(xmlEingabeDatei);
			generateJungleWithCurrentModel();
			writeCurrentModelInFile(xmlAusgabeDatei);
			return true;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen. Der Dschungelgenerator wird abgebrochen.");
			System.out.println();
			return false;
		}
	}

	@Override
	public List<ErrorType> examineSolution(String xmlEingabeDatei) {
		List<ErrorType> fehlerliste = new ArrayList<ErrorType>();
		try {
			readInAndTransferModel(xmlEingabeDatei);
			SolutionExaminer pruefer = new SolutionExaminer(model);
			fehlerliste = pruefer.examineSolution();
			return fehlerliste;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen und das Programm wird "
					+ "abbgebrochen. Es wird eine leere Fehlerliste zurueckgegeben.");
			System.out.println();
			return fehlerliste;
		}
	}

	@Override
	public int evaluateSolution(String xmlEingabeDatei) {
		try {
			readInAndTransferModel(xmlEingabeDatei);
			if (model.getSolution() == null) {
				System.out.println("Es ist keine Loesung vorhanden und deswegen kann"
						+ " nichts bewertet werden. Es wird die 0 zurueckgegeben.");
				System.out.println();
			}
			SolutionEvaluator bewerter = new SolutionEvaluator();
			return bewerter.evaluateSolution(model.getSolution());
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen und das Programm wird "
					+ "abbgebrochen. Es wird die 0 zurueckgegeben.");
			System.out.println();
			return 0;
		}
	}

	private void readInAndTransferModel(String eingabeDatei) throws Exception {
		/*
		 * Es wird die unter 'xmlEingabeDatei' zu findene XML-Datei eingelesen und die
		 * Daten der XML-Datei werden ins Modell des Programmes uebertragen.
		 */
		IReader xmlLeser = new ReaderXML();
		xmlLeser.readFile(eingabeDatei);
		model = xmlLeser.getTransferredModel();
	}

	private void searchSnakesInCurrentModel() {
		/*
		 * Eine Methode, die als Einstieg in die Suche von Schlangen dient. Dabei wird
		 * davon ausgegangen, dass das Modell des Programmes nicht leer ist.
		 */
		ISnakeSearch suche = new SnakeSearch(model);
		suche.searchSnakes();
		Double[] zeit = { model.getTime()[0],
				model.calculateTimeInUnitGivenByModel(System.nanoTime() - suche.getCurrTime()) };
		model.setTime(zeit);
		model.setSolution(suche.getSolution());
	}

	private void generateJungleWithCurrentModel() throws Exception {
		/*
		 * Eine Methode, die als Einstieg in das Generieren eines Dschungel dient. Dabei
		 * wird davon ausgegangen, dass das Modell des Programmes nicht leer ist.
		 */
		JungleGenerator generator = new JungleGenerator(model);
		generator.generateJungle();
		model.setJungle(generator.getNewJungle());
		model.setSolution(null);
		Double[] zeit = { model.getTime()[0], 0.0 };
		model.setTime(zeit);
	}

	private void viewOfCurrentModel(String xmlEingabeDatei) throws Exception {
		/*
		 * Eine Methode, die es ermoeglicht ein Modell in einer Konsole oder einem
		 * Terminal in Textform darzustellen. Es wird hierbei entweder das aktuelle
		 * Modell des Programmes oder das eingelesene Modell dargestellt.
		 */
		if (model == null) {
			readInAndTransferModel(xmlEingabeDatei);
		}
		if (model != null) {
			IView darstellung = new ViewText(model);
			darstellung.view();
		} else {
			System.out.println("Es ist kein Modell vorhanden, dass dargestellt werden koennte.");
			System.out.println();
		}
	}

	private void writeCurrentModelInFile(String ausgabeDatei) throws Exception {
		/*
		 * Das Modell des Programmes wird unter 'ausgabeDatei' in eine XML-Datei
		 * gespeichert.
		 */
		IWriter xmlSchreiber = new WriterXML(model);
		xmlSchreiber.writeInFile(ausgabeDatei);
	}

	private void printErrorList(List<ErrorType> fehlerliste) {
		/*
		 * Eine Hilfsmethode, um die Anzahl und Art der Fehlertypen in einer Fehlerliste
		 * darstellen zu koennen.
		 */
		if (fehlerliste.size() == 0) {
			System.out.println("Die Fehlerpruefung hat keine Fehler gefunden!");
		} else {
			int glieder = 0;
			int zuordnung = 0;
			int verwendung = 0;
			int nachbarschaft = 0;
			for (ErrorType fehlertyp : fehlerliste) {
				if (fehlertyp == ErrorType.GLIEDER) {
					glieder++;
				}
				if (fehlertyp == ErrorType.ZUORDNUNG) {
					zuordnung++;
				}
				if (fehlertyp == ErrorType.VERWENDUNG) {
					verwendung++;
				}
				if (fehlertyp == ErrorType.NACHBARSCHAFT) {
					nachbarschaft++;
				}
			}
			System.out.println("Die gegebene Loesung enthaelt die folgenden Fehler:");
			System.out.println();
			System.out.println(glieder + " Fehler vom Typ GLIEDER,");
			System.out.println(zuordnung + " Fehler vom Typ ZUORDNUNG,");
			System.out.println(verwendung + " Fehler vom Typ VERWENDUNG und");
			System.out.println(nachbarschaft + " Fehler vom Typ NACHBARSCHAFT.");
		}
	}

	/**
	 * Die Main Methode dieser Klasse bietet den Einstieg in die Nutzung des
	 * Programmes mit einer Konsole oder einem Terminal. Es koennen <code>2</code>
	 * oder <code>3</code> Parameter uebergeben werden. Die Parameter muessen in dem
	 * folgenden Format uebergeben werden:
	 * <p>
	 * java -jar ProPra.jar ablauf=&lt;Parameter&gt;
	 * eingabe=&lt;BeispielEingabe.xml&gt; ausgabe=&lt;BeispielAusgabe.xml&gt;
	 * <p>
	 * Eine Ausgabedatei anzugeben ist optional und die Parameter sind miteinander
	 * kombinierbar. Hierbei muss auf eine sinnvolle Reihenfolge geachtet werden,
	 * denn sonst wird moeglicherweise eine Fehlermeldung ausgegeben. Fuer die
	 * Parameter gibt es folgende Moeglichkeiten:
	 * <p>
	 * <code>l</code>: Fuer eine gegebene Probleminstanz wird nach einer neuen
	 * Loesung gesucht und bei Angabe einer Ausgabedatei unter dem angegebenen Pfad
	 * gespeichert.
	 * <p>
	 * <code>e</code>: Eine neue Probleminstanz wird auf Basis der gegebenen
	 * Parameter erzeugt und bei Angabe einer Ausgabedatei unter dem angegebenen
	 * Pfad gespeichert.
	 * <p>
	 * <code>p</code>: Die Zulaessigkeit der gegebenen Loesung wird ueberprueft. Bei
	 * Unzulaessigkeit werden die Art und Anzahl der verletzten Bedingungen in der
	 * Konsole ausgegeben.
	 * <p>
	 * <code>b</code>: Die Gesamtpunktzahl der Loesung wird unabhaengig von der
	 * Zulaessigkeit berechnet und in der Konsole ausgegeben.
	 * <p>
	 * <code>d</code>: Die Probleminstanz und die zugehoerige Loesung werden in der
	 * Konsole dargestellt.
	 * 
	 * @param args Die Parameter der Konsoleneingabe. Es ist darauf zu achten, dass
	 *             mindestens <code>2</code> und hoechstens <code>3</code> Parameter
	 *             uebergeben werden und dass diese die angegebene Form haben.
	 * @throws Exception Wenn ein Fehler im Ablauf des Progammes passiert wird eine
	 *                   Ausnahme ausgeloest und das Programm wird beendet. Hierbei
	 *                   ist zu beachten, dass Ausnahmen auch aus anderen Teilen des
	 *                   Programmes durchgereicht werden und dann hier abgefangen
	 *                   werden. Es wird im Zweifel immer eine Fehlermeldung
	 *                   ausgegeben, die beschreibt, wo der Fehler passiert ist.
	 */
	public static void main(String[] args) throws Exception {
		String ablauf = "";
		String eingabe = "";
		String ausgabe = "";

		/*
		 * Zu Beginn wird die Eingabe, die der Nutzer im Terminal gemacht hat
		 * ueberprueft und auf entsprechend nutzbare Stringvariablen verteilt. Danach
		 * wird fuer den weiteren Programmverlauf eine Instanz der Klasse Schlangenjagd
		 * erstellt.
		 */
		try {
			if (args.length < 2 || args.length > 3) {
				System.out.println();
				System.out.println("Es wurde die falsche Anzahl an Parametern uebergeben. Es "
						+ "muessen mindestens 2 und es duerfen hoechstens\n3 Parameter uebergeben "
						+ "werden. Die Parameter muessen in dem folgenden Format uebergeben werden:\n\n"
						+ "java -jar ProPra.jar ablauf=<Parameter> eingabe=<BeispielEingabe.xml> ausgabe=<BeispielAusgabe.xml>\n\n"
						+ "Eine Ausgabedatei anzugeben ist optional und die Parameter sind miteinander kombinierbar. Hierbei "
						+ "muss\nauf eine sinnvolle Reihenfolge geachtet werden, denn sonst wird moeglicherweise eine Fehlermeldung"
						+ "\nausgegeben. Fuer die Parameter gibt es folgende Moeglichkeiten:\n\n"
						+ "'l': Fuer eine gegebene Probleminstanz wird nach einer neuen Loesung gesucht und bei Angabe einer\n     "
						+ "Ausgabedatei unter dem angegebenen Pfad gespeichert.\n"
						+ "'e': Eine neue Probleminstanz wird auf Basis der gegebenen Parameter erzeugt und bei Angabe einer\n     "
						+ "Ausgabedatei unter dem angegebenen Pfad gespeichert.\n"
						+ "'p': Die Zulaessigkeit der gegebenen Loesung wird ueberprueft. Bei Unzulaessigkeit werden die Art\n     "
						+ "und Anzahl der verletzten Bedingungen in der Konsole ausgegeben.\n"
						+ "'b': Die Gesamtpunktzahl der Loesung wird unabhaengig von der Zulaessigkeit berechnet und in der\n     "
						+ "Konsole ausgegeben.\n"
						+ "'d': Die Probleminstanz und die zugehoerige Loesung werden in der Konsole dargestellt.\n");
				System.out.println("Programm wird abgebrochen.\n");
				return;
			}
			if (args.length == 2) {
				ablauf = args[0].split("=")[1];
				eingabe = args[1].split("=")[1];
			}
			if (args.length == 3) {
				ablauf = args[0].split("=")[1];
				eingabe = args[1].split("=")[1];
				ausgabe = args[2].split("=")[1];
			}
		} catch (Exception e) {
			System.out.println();
			System.out.println(
					"Beim Programmstart ist es zu einem Fehler gekommen. Moeglicherweise sind die Parameter in "
							+ "einem falschen\nFormat uebergeben worden. Die Parameter muessen in dem folgenden Format uebergeben werden:\n\n"
							+ "java -jar ProPra.jar ablauf=<Parameter> eingabe=<BeispielEingabe.xml> ausgabe=<BeispielAusgabe.xml>\n\n"
							+ "Eine Ausgabedatei anzugeben ist optional und die Parameter sind miteinander kombinierbar. Hierbei "
							+ "muss\nauf eine sinnvolle Reihenfolge geachtet werden, denn sonst wird moeglicherweise eine Fehlermeldung"
							+ "\nausgegeben. Fuer die Parameter gibt es folgende Moeglichkeiten:\n\n"
							+ "'l': Fuer eine gegebene Probleminstanz wird nach einer neuen Loesung gesucht und bei Angabe einer\n     "
							+ "Ausgabedatei unter dem angegebenen Pfad gespeichert.\n"
							+ "'e': Eine neue Probleminstanz wird auf Basis der gegebenen Parameter erzeugt und bei Angabe einer\n     "
							+ "Ausgabedatei unter dem angegebenen Pfad gespeichert.\n"
							+ "'p': Die Zulaessigkeit der gegebenen Loesung wird ueberprueft. Bei Unzulaessigkeit werden die Art\n     "
							+ "und Anzahl der verletzten Bedingungen in der Konsole ausgegeben.\n"
							+ "'b': Die Gesamtpunktzahl der Loesung wird unabhaengig von der Zulaessigkeit berechnet und in der\n     "
							+ "Konsole ausgegeben.\n"
							+ "'d': Die Probleminstanz und die zugehoerige Loesung werden in der Konsole dargestellt.\n");
			System.out.println("Programm wird abgebrochen.\n");
			return;
		}
		SnakeHunt jagd = new SnakeHunt();
		System.out.println();
		System.out.println("Lade Daten der unter '" + eingabe + "' zu findenden Datei in das Modell des Programmes...");
		System.out.println();

		/*
		 * Nun wird ueberprueft, ob sich mindestens einer der Parameter mehrfach in der
		 * 'ablauf' Variable befindet. Dies ist moeglich und fuehrt nicht zum
		 * Programmabbruch aber dem Nutzer wird eine Warnmeldung angezeigt.
		 */
		int l = 0;
		int e = 0;
		int p = 0;
		int b = 0;
		int d = 0;
		for (char befehl : ablauf.toCharArray()) {
			if (befehl == 'l') {
				l++;
			}
			if (befehl == 'e') {
				e++;
			}
			if (befehl == 'p') {
				p++;
			}
			if (befehl == 'b') {
				b++;
			}
			if (befehl == 'd') {
				d++;
			}
			if (l > 1 || e > 1 || b > 1 || d > 1 || p > 1) {
				System.out.println("Achtung! Ein oder mehrere Ablaufparameter wurden mehr als ein Mal "
						+ "uebergeben. Moeglicherweise ergeben sich im\nProgrammverlauf Fehler oder "
						+ "Programmteile werden unnoetigerweise zu oft ausgefuehrt. Außerdem ist zu "
						+ "beachten,\ndass das Datenmodell des Programmes sich mit den Befehlen 'l' und "
						+ "'e' aendern kann. Ein Befehl wird sich im\nZweifel an dem Datenmodell orientieren, "
						+ "das der vorherige Befehl geaendert hat.");
				System.out.println();
				break;
			}
		}

		/*
		 * Hier werden die einzelnen Parameter in der Variablen 'ablauf' nach und nach
		 * abgearbeitet. Dabei wird immer geprueft, ob schon Daten Modell stehen, denn
		 * so werden die Parameter unabhaengig voneinander. Es ist moeglich jeden
		 * Parameter an den Anfang oder das Ende von 'ablauf' zu setzen und das Programm
		 * wird entsprechend entweder erst eine Datei einlesen oder die Daten des schon
		 * vorhandenen Modelles nutzen. Schliesslich wird noch eine Warnmeldung
		 * ausgegeben, wenn ein Buchstabe uebergeben wurde, der keine Funktion hat. Dies
		 * fuehrt aber nicht zum Programmabbruch.
		 */
		try {
			char[] ablaufArray = ablauf.toCharArray();
			for (char befehl : ablaufArray) {
				if (befehl == 'l') {
					try {
						System.out.println("Loese Probleminstanz...");
						System.out.println();

						if (jagd.model == null) {
							jagd.readInAndTransferModel(eingabe);
						}
						jagd.searchSnakesInCurrentModel();
						System.out.println("Die im Modell gefundene Probleminstanz wurde geloest.");
						System.out.println();
						if (!ablauf.contains("d") && jagd.model.getJungle().numberOfFields() != jagd.model
								.getJungle().numberOfTakenFields()) {
							System.out.println("Es ist hierbei zu beachten, dass "
									+ " der eingelesene Dschungel leere Felder enthaelt. Es wurden\n"
									+ jagd.model.getJungle().numberOfFields() + " Felder erwartet aber nur "
									+ jagd.model.getJungle().numberOfTakenFields() + " wurden eingelesen. "
									+ " Die uebrigen Felder sind leer. Mit dem\nBefehl 'e' kann ein vollstaendiger "
									+ " Dschungel erzeugt werden.");
							System.out.println();
						}
					} catch (IllegalArgumentException modellZuLeer) {
						if (!(befehl == ablaufArray[ablaufArray.length - 1])) {
							System.out.println("Es wird versucht mit den uebrigen Befehlen fortzufahren...");
							System.out.println();
						}
					}
				}
				if (befehl == 'e') {
					System.out.println("Erzeuge Dschungel...");
					System.out.println();
					if (jagd.model == null) {
						jagd.readInAndTransferModel(eingabe);
					}
					jagd.generateJungleWithCurrentModel();
					System.out.println(
							"Der im Modell zu findenden Probleminstanz wurde ein (neuer) Dschungel hinzugefuegt.");
					System.out.println();
				}
				if (befehl == 'p') {
					try {
						if (jagd.model == null) {
							jagd.readInAndTransferModel(eingabe);
						}
						SolutionExaminer pruefer = new SolutionExaminer(jagd.model);
						List<ErrorType> fehlerliste = pruefer.examineSolution();
						if (jagd.model != null && jagd.model.getSolution() != null) {
							jagd.printErrorList(fehlerliste);
							System.out.println();
						}
					} catch (IllegalArgumentException modellZuLeer) {
						if (!(befehl == ablaufArray[ablaufArray.length - 1])) {
							System.out.println("Es wird versucht mit den uebrigen Befehlen fortzufahren...");
							System.out.println();
						}
					}
				}
				if (befehl == 'b') {
					if (jagd.model == null) {
						jagd.readInAndTransferModel(eingabe);
					}
					int bewertung;
					if (jagd.model.getSolution() == null) {
						System.out.println("Es ist keine Loesung vorhanden und deswegen kann"
								+ " nichts bewertet werden. Es wird 0 zurueckgegeben.");
						System.out.println();
						bewertung = 0;
					} else {
						SolutionEvaluator bewerter = new SolutionEvaluator();
						bewertung = bewerter.evaluateSolution(jagd.model.getSolution());
					}
					if (jagd.model != null && jagd.model.getSolution() != null) {
						System.out.println("Die gegebene Loesung hat " + bewertung + " Punkte erzielt.");
						System.out.println();
					}
				}
				if (befehl == 'd') {
					jagd.viewOfCurrentModel(eingabe);
				}
				if ((befehl != 'l') && (befehl != 'e') && (befehl != 'p') && (befehl != 'b') && (befehl != 'd')) {
					System.out.println("Achtung! Der uebergebene Parameter " + befehl
							+ " hat keine Funktion im Programm. Der Parameter wird uebersprungen.");
					System.out.println();
				}
			}

			/*
			 * Bei Angabe einer Ausgabedatei wird in diesem Teil des Programmes schliesslich
			 * das Modell in eine Datei mit dem angegebenen Namen geschrieben.
			 */
			if (!ausgabe.equals("")) {
				jagd.writeCurrentModelInFile(ausgabe);
				System.out.println("Nach Bearbeitung der angegebenen Ablaufparameter wurde der Inhalt "
						+ "des Modelles unter\n'" + ausgabe + "' gespeichert.");
				System.out.println();
			} else {
				System.out.println("Es wird nichts gespeichert, da keine Ausgabedatei angegeben wurde.");
				System.out.println();
			}
		} catch (Exception fehler) {
			System.out.println("Programm wird abbgebrochen.");
			System.out.println();

			/*
			 * Der folgende Kommentar kann auskommentiert werden, wenn Informationen ueber
			 * einen fehlerhaften Programmverlauf und beziehungsweise oder einen
			 * Programmabbruch gewuenscht sind. Hierbei werden Informationen ueber die Art
			 * und den Ort der Ausnahme ausgegeben, die den Programmabbruch verursacht hat.
			 */

			// fehler.printStackTrace();
		}
	}
}