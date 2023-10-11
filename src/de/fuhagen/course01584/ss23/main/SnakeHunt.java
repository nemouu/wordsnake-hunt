package de.fuhagen.course01584.ss23.main;

import java.util.*;

import de.fuhagen.course01584.ss23.algorithm.*;
import de.fuhagen.course01584.ss23.ioprocessing.*;
import de.fuhagen.course01584.ss23.model.*;
import de.fuhagen.course01584.ss23.view.ViewText;
import de.fuhagen.course01584.ss23.view.IView;

/**
 * A class that implements the SnakeHuntAPI interface. It makes it possible to
 * use the program through the implemented methods of the interface. In
 * addition, some private methods are provided that enable the use of the
 * program via the Main method and thus through a console or terminal.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeHunt implements SnakeHuntAPI {
	private IModel model;

	/**
	 * Parameterless constructor of the SnakeHunt class.
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
			System.out.println("An error occurred. The snake hunt is aborted.");
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
			System.out.println("An error occurred. The jungle generator is aborted.");
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
			System.out.println("An error occurred, and the program is " + "aborted. An empty error list is returned.");
			System.out.println();
			return errorList;
		}
	}

	@Override
	public int evaluateSolution(String xmlInputFile) {
		try {
			readInAndTransferModel(xmlInputFile);
			if (model.getSolution() == null) {
				System.out
						.println("No solution is present, and therefore, " + "nothing can be evaluated. Returning 0.");
				System.out.println();
			}
			SolutionEvaluator evaluator = new SolutionEvaluator();
			return evaluator.evaluateSolution(model.getSolution());
		} catch (Exception e) {
			System.out.println("An error occurred, and the program is " + "aborted. Returning 0.");
			System.out.println();
			return 0;
		}
	}

	private void readInAndTransferModel(String inputFile) throws Exception {
		/*
		 * Reads the XML file specified under 'xmlInputFile' and transfers the data from
		 * the XML file to the program's model.
		 */
		IReader readerXML = new ReaderXML();
		readerXML.readFile(inputFile);
		model = readerXML.getTransferredModel();
	}

	private void searchSnakesInCurrentModel() {
		/*
		 * A method that serves as an entry point for snake search. It is assumed that
		 * the program's model is not empty.
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
		 * A method that serves as an entry point for generating a jungle. It is assumed
		 * that the program's model is not empty.
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
		 * A method that allows displaying a model in a console or terminal in text
		 * form. Either the current model of the program or the read model is displayed.
		 */
		if (model == null) {
			readInAndTransferModel(xmlInputFile);
		}
		if (model != null) {
			IView view = new ViewText(model);
			view.view();
		} else {
			System.out.println("No model available to display.");
			System.out.println();
		}
	}

	private void writeCurrentModelInFile(String outputFile) throws Exception {
		/*
		 * Saves the model of the program in an XML file under 'outputFile'.
		 */
		IWriter writerXML = new WriterXML(model);
		writerXML.writeInFile(outputFile);
	}

	private void printErrorList(List<ErrorType> errorList) {
		/*
		 * A helper method to display the number and type of error types in an error
		 * list.
		 */
		if (errorList.size() == 0) {
			System.out.println("Error check found no errors!");
		} else {
			int elements = 0;
			int assignment = 0;
			int usage = 0;
			int neighborhood = 0;
			for (ErrorType errorType : errorList) {
				if (errorType == ErrorType.ELEMENTS) {
					elements++;
				}
				if (errorType == ErrorType.ASSIGNMENT) {
					assignment++;
				}
				if (errorType == ErrorType.USAGE) {
					usage++;
				}
				if (errorType == ErrorType.NEIGHBORHOOD) {
					neighborhood++;
				}
			}
			System.out.println("The given solution contains the following errors:");
			System.out.println();
			System.out.println(elements + " errors of type ELEMENTS,");
			System.out.println(assignment + " errors of type ASSIGNMENT,");
			System.out.println(usage + " errors of type USAGE, and");
			System.out.println(neighborhood + " errors of type NEIGHBORHOOD.");
		}
	}

	/**
	 * The main method of this class provides the entry point to use the program
	 * through a console or terminal. You can pass either <code>2</code> or
	 * <code>3</code> parameters. The parameters must be passed in the following
	 * format:
	 * <p>
	 * java -jar ProPra.jar process=&lt;Parameter&gt; input=&lt;ExampleInput.xml&gt;
	 * output=&lt;ExampleOutput.xml&gt;
	 * <p>
	 * Specifying an output file is optional, and the parameters can be combined. It
	 * is important to observe a meaningful order; otherwise, an error message might
	 * be displayed. For the parameters, the following possibilities exist:
	 * <p>
	 * <code>l</code>: For a given problem instance, a new solution is searched, and
	 * when specifying an output file, it is saved at the specified path.
	 * <p>
	 * <code>e</code>: A new problem instance is generated based on the given
	 * parameters, and when specifying an output file, it is saved at the specified
	 * path.
	 * <p>
	 * <code>p</code>: The admissibility of the given solution is checked. In case
	 * of inadmissibility, the type and number of violated conditions are output in
	 * the console.
	 * <p>
	 * <code>b</code>: The total score of the solution is calculated and output in
	 * the console, regardless of admissibility.
	 * <p>
	 * <code>d</code>: The problem instance and the corresponding solution are
	 * displayed in the console.
	 * 
	 * @param args The parameters from the console input. It is important to ensure
	 *             that at least <code>2</code> and at most <code>3</code>
	 *             parameters are passed and that they have the specified format.
	 * @throws Exception If an error occurs in the program's flow, an exception is
	 *                   thrown, and the program terminates. It is important to note
	 *                   that exceptions are also passed from other parts of the
	 *                   program and caught here. An error message is always
	 *                   displayed describing where the error occurred.
	 */

	public static void main(String[] args) throws Exception {
		String process = "";
		String input = "";
		String output = "";

		/*
		 * At the beginning, the input provided by the user in the terminal is checked
		 * and distributed into usable string variables accordingly for the further
		 * program flow. Then, an instance of the SnakeHunt class is created for the
		 * program's further execution.
		 */
		try {
			if (args.length < 2 || args.length > 3) {
				System.out.println();
				System.out.println("Incorrect number of parameters provided. "
						+ "At least 2 and at most 3 parameters must be passed. Parameters should be passed in the following format:\n\n"
						+ "java -jar ProPra.jar process=<Parameter> input=<ExampleInput.xml> output=<ExampleOutput.xml>\n\n"
						+ "Specifying an output file is optional, and the parameters can be combined. It is important "
						+ "to observe a meaningful order; otherwise, an error message may be displayed. For the parameters, "
						+ "the following options are available:\n\n"
						+ "'l': Search for a new solution for a given problem instance and save it at the specified path if an output file is specified.\n"
						+ "'e': Generate a new problem instance based on the given parameters and save it at the specified path if an output file is specified.\n"
						+ "'p': Check the admissibility of the given solution. In case of inadmissibility, the type and number of violated conditions are displayed in the console.\n"
						+ "'b': Calculate and display the total score of the solution, regardless of admissibility.\n"
						+ "'d': Display the problem instance and its corresponding solution in the console.\n");
				System.out.println("Program will be aborted.\n");
				return;
			}
			if (args.length == 2) {
				process = args[0].split("=")[1];
				input = args[1].split("=")[1];
			}
			if (args.length == 3) {
				process = args[0].split("=")[1];
				input = args[1].split("=")[1];
				output = args[2].split("=")[1];
			}
		} catch (Exception e) {
			System.out.println();
			System.out.println(
					"An error occurred at the start of the program. The parameters may have been provided in an incorrect format. Parameters should be passed in the following format:\n\n"
							+ "java -jar ProPra.jar process=<Parameter> input=<ExampleInput.xml> output=<ExampleOutput.xml>\n\n"
							+ "Specifying an output file is optional, and the parameters can be combined. It is important "
							+ "to observe a meaningful order; otherwise, an error message may be displayed. For the parameters, "
							+ "the following options are available:\n\n"
							+ "'l': Search for a new solution for a given problem instance and save it at the specified path if an output file is specified.\n"
							+ "'e': Generate a new problem instance based on the given parameters and save it at the specified path if an output file is specified.\n"
							+ "'p': Check the admissibility of the given solution. In case of inadmissibility, the type and number of violated conditions are displayed in the console.\n"
							+ "'b': Calculate and display the total score of the solution, regardless of admissibility.\n"
							+ "'d': Display the problem instance and its corresponding solution in the console.\n");
			System.out.println("Program will be aborted.\n");
			return;
		}
		SnakeHunt hunt = new SnakeHunt();
		System.out.println();
		System.out.println("Loading data from the file located at '" + input + "' into the program's model...");
		System.out.println();

		/*
		 * Now it is checked whether at least one of the parameters is repeated in the
		 * 'process' variable. This is possible and does not lead to program
		 * termination, but a warning message is displayed to the user.
		 */
		int l = 0;
		int e = 0;
		int p = 0;
		int b = 0;
		int d = 0;
		for (char command : process.toCharArray()) {
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
				System.out.println("Attention! One or more process parameters have been provided more than once. "
						+ "This may result in errors in the program flow or unnecessary execution of program parts. "
						+ "Additionally, it should be noted that the data model of the program can change with the commands 'l' and 'e'. "
						+ "A command will potentially be based on the data model that the previous command has changed.");
				System.out.println();
				break;
			}
		}

		/*
		 * Here, the individual parameters in the 'process' variable are processed one
		 * by one. It is always checked whether data model already exists because
		 * parameters will operate independently in that case. It is possible to place
		 * each parameter at the beginning or end of 'process', and the program will
		 * either read a file or use the data from the existing model accordingly.
		 * Finally, a warning message is given if a letter is passed that has no
		 * function. However, this does not lead to program termination.
		 */
		try {
			char[] courseArray = process.toCharArray();
			for (char commmand : courseArray) {
				if (commmand == 'l') {
					try {
						System.out.println("Solving problem instance...");
						System.out.println();

						if (hunt.model == null) {
							hunt.readInAndTransferModel(input);
						}
						hunt.searchSnakesInCurrentModel();
						System.out.println("The problem instance found in the model has been solved.");
						System.out.println();
						if (!process.contains("d") && hunt.model.getJungle().numberOfFields() != hunt.model.getJungle()
								.numberOfTakenFields()) {
							System.out.println("It should be noted that the loaded jungle contains empty fields. "
									+ hunt.model.getJungle().numberOfFields() + " fields were expected, but only "
									+ hunt.model.getJungle().numberOfTakenFields() + " were read. "
									+ "The remaining fields are empty. A complete jungle can be generated with the 'e' command.");
							System.out.println();
						}
					} catch (IllegalArgumentException modelTooEmpty) {
						if (!(commmand == courseArray[courseArray.length - 1])) {
							System.out.println("Attempting to proceed with the remaining commands...");
							System.out.println();
						}
					}
				}
				if (commmand == 'e') {
					System.out.println("Generating jungle...");
					System.out.println();
					if (hunt.model == null) {
						hunt.readInAndTransferModel(input);
					}
					hunt.generateJungleWithCurrentModel();
					System.out.println("A (new) jungle has been added to the problem instance found in the model.");
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
							System.out.println("Attempting to proceed with the remaining commands...");
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
						System.out.println("No solution is available, so nothing can be evaluated. Returning 0.");
						System.out.println();
						evaluation = 0;
					} else {
						SolutionEvaluator evaluator = new SolutionEvaluator();
						evaluation = evaluator.evaluateSolution(hunt.model.getSolution());
					}
					if (hunt.model != null && hunt.model.getSolution() != null) {
						System.out.println("The given solution scored " + evaluation + " points.");
						System.out.println();
					}
				}
				if (commmand == 'd') {
					hunt.viewOfCurrentModel(input);
				}
				if ((commmand != 'l') && (commmand != 'e') && (commmand != 'p') && (commmand != 'b')
						&& (commmand != 'd')) {
					System.out.println("Attention! The provided parameter " + commmand
							+ " has no function in the program. The parameter will be skipped.");
					System.out.println();
				}
			}

			/*
			 * If an output file is specified, in this part of the program, the model is
			 * written to a file with the specified name.
			 */
			if (!output.equals("")) {
				hunt.writeCurrentModelInFile(output);
				System.out.println("After processing the specified process parameters, the content "
						+ "of the model has been saved under\n'" + output + "'.");
				System.out.println();
			} else {
				System.out.println("Nothing will be saved since no output file was specified.");
				System.out.println();
			}
		} catch (Exception error) {
			System.out.println("Program will be aborted.");
			System.out.println();

			/*
			 * The following comment can be commented out if information about erroneous
			 * program flow and/or program termination is desired. Here, information about
			 * the type and location of the exception that caused the program termination is
			 * output to the terminal.
			 */

			// error.printStackTrace();
		}
	}
}