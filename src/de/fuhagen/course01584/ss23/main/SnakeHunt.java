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
	public boolean solveProblem(String xmlInputFile, String xmlOutputFile) {
		try {
			readInAndTransferModel(xmlInputFile);
			searchSnakesInCurrentModel();
			writeCurrentModelInFile(xmlOutputFile);
			return true;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen. Die Schlangensuche wird abgebrochen.");
			System.out.println();
			return false;
		}
	}

	@Override
	public boolean generateProblem(String xmlInputFile, String xmlOutputFile) {
		try {
			readInAndTransferModel(xmlInputFile);
			generateJungleWithCurrentModel();
			writeCurrentModelInFile(xmlOutputFile);
			return true;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen. Der Dschungelgenerator wird abgebrochen.");
			System.out.println();
			return false;
		}
	}

	@Override
	public List<ErrorType> examineSolution(String xmlInputFile) {
		List<ErrorType> errorList = new ArrayList<ErrorType>();
		try {
			readInAndTransferModel(xmlInputFile);
			SolutionExaminer examiner = new SolutionExaminer(model);
			errorList = examiner.examineSolution();
			return errorList;
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen und das Programm wird "
					+ "abbgebrochen. Es wird eine leere Fehlerliste zurueckgegeben.");
			System.out.println();
			return errorList;
		}
	}

	@Override
	public int evaluateSolution(String xmlInputFile) {
		try {
			readInAndTransferModel(xmlInputFile);
			if (model.getSolution() == null) {
				System.out.println("Es ist keine Loesung vorhanden und deswegen kann"
						+ " nichts bewertet werden. Es wird die 0 zurueckgegeben.");
				System.out.println();
			}
			SolutionEvaluator evaluator = new SolutionEvaluator();
			return evaluator.evaluateSolution(model.getSolution());
		} catch (Exception e) {
			System.out.println("Es ist zu einem Fehler gekommen und das Programm wird "
					+ "abbgebrochen. Es wird die 0 zurueckgegeben.");
			System.out.println();
			return 0;
		}
	}

	private void readInAndTransferModel(String inputFile) throws Exception {
		/*
		 * Es wird die unter 'xmlEingabeDatei' zu findene XML-Datei eingelesen und die
		 * Daten der XML-Datei werden ins Modell des Programmes uebertragen.
		 */
		IReader readerXML = new ReaderXML();
		readerXML.readFile(inputFile);
		model = readerXML.getTransferredModel();
	}

	private void searchSnakesInCurrentModel() {
		/*
		 * Eine Methode, die als Einstieg in die Suche von Schlangen dient. Dabei wird
		 * davon ausgegangen, dass das Modell des Programmes nicht leer ist.
		 */
		ISnakeSearch search = new SnakeSearch(model);
		search.searchSnakes();
		Double[] time = { model.getTime()[0],
				model.calculateTimeInUnitGivenByModel(System.nanoTime() - search.getCurrTime()) };
		model.setTime(time);
		model.setSolution(search.getSolution());
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
		Double[] time = { model.getTime()[0], 0.0 };
		model.setTime(time);
	}

	private void viewOfCurrentModel(String xmlInputFile) throws Exception {
		/*
		 * Eine Methode, die es ermoeglicht ein Modell in einer Konsole oder einem
		 * Terminal in Textform darzustellen. Es wird hierbei entweder das aktuelle
		 * Modell des Programmes oder das eingelesene Modell dargestellt.
		 */
		if (model == null) {
			readInAndTransferModel(xmlInputFile);
		}
		if (model != null) {
			IView view = new ViewText(model);
			view.view();
		} else {
			System.out.println("Es ist kein Modell vorhanden, dass dargestellt werden koennte.");
			System.out.println();
		}
	}

	private void writeCurrentModelInFile(String outputFile) throws Exception {
		/*
		 * Das Modell des Programmes wird unter 'ausgabeDatei' in eine XML-Datei
		 * gespeichert.
		 */
		IWriter writerXML = new WriterXML(model);
		writerXML.writeInFile(outputFile);
	}

	private void printErrorList(List<ErrorType> errorList) {
		/*
		 * Eine Hilfsmethode, um die Anzahl und Art der Fehlertypen in einer Fehlerliste
		 * darstellen zu koennen.
		 */
		if (errorList.size() == 0) {
			System.out.println("Die Fehlerpruefung hat keine Fehler gefunden!");
		} else {
			int elements = 0;
			int assignment = 0;
			int usage = 0;
			int neighborhood = 0;
			for (ErrorType fehlertyp : errorList) {
				if (fehlertyp == ErrorType.ELEMENTS) {
					elements++;
				}
				if (fehlertyp == ErrorType.ASSIGNMENT) {
					assignment++;
				}
				if (fehlertyp == ErrorType.USAGE) {
					usage++;
				}
				if (fehlertyp == ErrorType.NEIGHBORHOOD) {
					neighborhood++;
				}
			}
			System.out.println("Die gegebene Loesung enthaelt die folgenden Fehler:");
			System.out.println();
			System.out.println(elements + " Fehler vom Typ GLIEDER,");
			System.out.println(assignment + " Fehler vom Typ ZUORDNUNG,");
			System.out.println(usage + " Fehler vom Typ VERWENDUNG und");
			System.out.println(neighborhood + " Fehler vom Typ NACHBARSCHAFT.");
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
		String course = "";
		String input = "";
		String output = "";

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
				course = args[0].split("=")[1];
				input = args[1].split("=")[1];
			}
			if (args.length == 3) {
				course = args[0].split("=")[1];
				input = args[1].split("=")[1];
				output = args[2].split("=")[1];
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
		SnakeHunt hunt = new SnakeHunt();
		System.out.println();
		System.out.println("Lade Daten der unter '" + input + "' zu findenden Datei in das Modell des Programmes...");
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
		for (char command : course.toCharArray()) {
			if (command == 'l') {
				l++;
			}
			if (command == 'e') {
				e++;
			}
			if (command == 'p') {
				p++;
			}
			if (command == 'b') {
				b++;
			}
			if (command == 'd') {
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
			char[] courseArray = course.toCharArray();
			for (char commmand : courseArray) {
				if (commmand == 'l') {
					try {
						System.out.println("Loese Probleminstanz...");
						System.out.println();

						if (hunt.model == null) {
							hunt.readInAndTransferModel(input);
						}
						hunt.searchSnakesInCurrentModel();
						System.out.println("Die im Modell gefundene Probleminstanz wurde geloest.");
						System.out.println();
						if (!course.contains("d") && hunt.model.getJungle().numberOfFields() != hunt.model
								.getJungle().numberOfTakenFields()) {
							System.out.println("Es ist hierbei zu beachten, dass "
									+ " der eingelesene Dschungel leere Felder enthaelt. Es wurden\n"
									+ hunt.model.getJungle().numberOfFields() + " Felder erwartet aber nur "
									+ hunt.model.getJungle().numberOfTakenFields() + " wurden eingelesen. "
									+ " Die uebrigen Felder sind leer. Mit dem\nBefehl 'e' kann ein vollstaendiger "
									+ " Dschungel erzeugt werden.");
							System.out.println();
						}
					} catch (IllegalArgumentException modelTooEmpty) {
						if (!(commmand == courseArray[courseArray.length - 1])) {
							System.out.println("Es wird versucht mit den uebrigen Befehlen fortzufahren...");
							System.out.println();
						}
					}
				}
				if (commmand == 'e') {
					System.out.println("Erzeuge Dschungel...");
					System.out.println();
					if (hunt.model == null) {
						hunt.readInAndTransferModel(input);
					}
					hunt.generateJungleWithCurrentModel();
					System.out.println(
							"Der im Modell zu findenden Probleminstanz wurde ein (neuer) Dschungel hinzugefuegt.");
					System.out.println();
				}
				if (commmand == 'p') {
					try {
						if (hunt.model == null) {
							hunt.readInAndTransferModel(input);
						}
						SolutionExaminer examiner = new SolutionExaminer(hunt.model);
						List<ErrorType> errorList = examiner.examineSolution();
						if (hunt.model != null && hunt.model.getSolution() != null) {
							hunt.printErrorList(errorList);
							System.out.println();
						}
					} catch (IllegalArgumentException modelTooEmpty) {
						if (!(commmand == courseArray[courseArray.length - 1])) {
							System.out.println("Es wird versucht mit den uebrigen Befehlen fortzufahren...");
							System.out.println();
						}
					}
				}
				if (commmand == 'b') {
					if (hunt.model == null) {
						hunt.readInAndTransferModel(input);
					}
					int evaluation;
					if (hunt.model.getSolution() == null) {
						System.out.println("Es ist keine Loesung vorhanden und deswegen kann"
								+ " nichts bewertet werden. Es wird 0 zurueckgegeben.");
						System.out.println();
						evaluation = 0;
					} else {
						SolutionEvaluator evaluator = new SolutionEvaluator();
						evaluation = evaluator.evaluateSolution(hunt.model.getSolution());
					}
					if (hunt.model != null && hunt.model.getSolution() != null) {
						System.out.println("Die gegebene Loesung hat " + evaluation + " Punkte erzielt.");
						System.out.println();
					}
				}
				if (commmand == 'd') {
					hunt.viewOfCurrentModel(input);
				}
				if ((commmand != 'l') && (commmand != 'e') && (commmand != 'p') && (commmand != 'b') && (commmand != 'd')) {
					System.out.println("Achtung! Der uebergebene Parameter " + commmand
							+ " hat keine Funktion im Programm. Der Parameter wird uebersprungen.");
					System.out.println();
				}
			}

			/*
			 * Bei Angabe einer Ausgabedatei wird in diesem Teil des Programmes schliesslich
			 * das Modell in eine Datei mit dem angegebenen Namen geschrieben.
			 */
			if (!output.equals("")) {
				hunt.writeCurrentModelInFile(output);
				System.out.println("Nach Bearbeitung der angegebenen Ablaufparameter wurde der Inhalt "
						+ "des Modelles unter\n'" + output + "' gespeichert.");
				System.out.println();
			} else {
				System.out.println("Es wird nichts gespeichert, da keine Ausgabedatei angegeben wurde.");
				System.out.println();
			}
		} catch (Exception error) {
			System.out.println("Programm wird abbgebrochen.");
			System.out.println();

			/*
			 * Der folgende Kommentar kann auskommentiert werden, wenn Informationen ueber
			 * einen fehlerhaften Programmverlauf und beziehungsweise oder einen
			 * Programmabbruch gewuenscht sind. Hierbei werden Informationen ueber die Art
			 * und den Ort der Ausnahme ausgegeben, die den Programmabbruch verursacht hat.
			 */

			// error.printStackTrace();
		}
	}
}