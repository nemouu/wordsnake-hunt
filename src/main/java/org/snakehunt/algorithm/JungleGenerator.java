package org.snakehunt.algorithm;

import java.util.*;

import org.snakehunt.model.*;

/**
 * This class provides constructors and methods to enable the creation of simple
 * problem instances for snake hunting. This means that snakes should not
 * intersect when creating such problem instances, and each field should be used
 * at most once. The data for rows, columns, character set of the jungle, and
 * the data for distributing snake types are used for creation.
 * 
 * @author Philip Redecker
 */
public class JungleGenerator {
	private IModel model;
	private int minimumSnakes;
	private SnakeType currentType;
	private Jungle jungle;
	private Jungle newJungle;
	private Long currentTime;
	private List<SnakeType> snakeTypes;
	private boolean failed;
	private boolean full;

	/**
	 * Creates a problem instance based on the data of the model when a suitable
	 * model is provided. If the data or parts of the data are missing, an error
	 * message is displayed.
	 * 
	 * @param model The model based on which the jungle generator generates the
	 *              jungle. It is important to pass a model that has enough data so
	 *              that a jungle can be generated. If this is not the case, an
	 *              error message will be displayed.
	 *
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed to the constructor.
	 */
	public JungleGenerator(IModel model) throws IllegalArgumentException {
		super();
		this.snakeTypes = model.getSnakeTypes();
		if (model.getJungle().getRows() * model.getJungle().getColumns() < minimumNumberUsedFields()) {
			System.out.println(
					"The model to be passed to the jungle generator contains more\nsnakes than can fit in the jungle. "
							+ "Like this a jungle cannot be generated.");
			System.out.println();
			throw new IllegalArgumentException(
					"The constructor for the component jungle generator cannot be passed a model with"
							+ " more snakes than can fit in the jungle.");
		}
		this.model = model;
		this.minimumSnakes = minimumNumberUsedFields();
		this.jungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
				model.getJungle().getSigns(), 1);
		this.newJungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
				model.getJungle().getSigns(), 1);
		this.currentTime = System.nanoTime();
		this.failed = false;
		this.full = false;
	}

	/**
	 * A parameterless constructor, so that it is possible to use this class for
	 * testing, for example, in future program changes.
	 */
	public JungleGenerator() {
		super();
	}

	/**
	 * The main method of this class. Three other private methods of this class are
	 * used to generate a jungle based on the data of the model. All snakes are
	 * traversed one by one and distributed based on the data of the model. The
	 * private method 'placeSnakeElement' is always used to place the elements of
	 * the snakes placed in this method. If a snake no longer fits in the jungle, it
	 * is gradually removed, and attempts are made to position the snakes
	 * differently. It has to be considered at this point that the jungle generator
	 * does not terminate quickly in the case of a particularly unfavorable
	 * distribution of the snakes. If this happens, a new attempt is started under
	 * the same conditions.
	 * 
	 * @throws Exception If the jungle generator fails twice in a row, an exception
	 *                   is thrown.
	 */
	public void generateJungle() throws Exception {
		placeSnake();
		fillRestOfJungle(newJungle);
		if (failed) {
			System.out.println("The generator has failed. The snakes may have been distributed unfavorably.\n"
					+ "Trying again...");
			System.out.println();
			this.snakeTypes = model.getSnakeTypes();
			this.minimumSnakes = minimumNumberUsedFields();
			this.jungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
					model.getJungle().getSigns(), 1);
			this.newJungle = new Jungle(model.getJungle().getRows(), model.getJungle().getColumns(),
					model.getJungle().getSigns(), 1);
			this.currentTime = System.nanoTime();
			this.failed = false;
			placeSnake();
			fillRestOfJungle(newJungle);
			if (failed) {
				System.out.println("The generator has failed again. The snakes may be unfavorably distributed again.\n"
						+ "Restarting the program may fix the problem. If an error occurs again,\n"
						+ "there may be another issue.");
				System.out.println();
				throw new Exception();
			}
		}
	}

	private void placeSnake() {
		/*
		 * Here, it is checked whether all snakes have been distributed. If this is the
		 * case, they are added to a new jungle. A time limit of 10 seconds is set for
		 * generating a jungle.
		 */
		if (jungle.numberOfTakenFields() == minimumSnakes && numberOfTypesTotal() == 0 && full == false) {
			for (int i = 0; i < jungle.getFields().length; i++) {
				for (int j = 0; j < jungle.getFields()[0].length; j++) {
					newJungle.getFields()[i][j] = new Field(jungle.getFields()[i][j].getId(), i, j, 1, 1,
							jungle.getFields()[i][j].getCharacter());
				}
			}
			this.full = true;
			return;
		}
		if (System.nanoTime() - currentTime > 10000000000.0 || full == true) {
			return;
		}

		// All snake types whose count is still greater than 0 are listed.
		List<SnakeType> validSnakeTypes = createValidSnakeTypes();
		for (SnakeType snakeType : validSnakeTypes) {
			snakeType.setAmount(snakeType.getAmount() - 1);
			SnakeType previousSnakeType = currentType;
			currentType = snakeType;

			/*
			 * All starting fields for the current snake type are listed, and for each of
			 * these starting fields, an attempt is made to place a snake of the current
			 * snake type. If placing fails, the type count is increased again so that
			 * placing can be attempted again with a different starting field.
			 */
			List<Field> validStartingFields = createValidStartingFields();
			for (Field startingField : validStartingFields) {
				jungle.getFields()[startingField.getRow()][startingField.getColumn()]
						.setCharacter(currentType.getSigns().substring(0, 1));
				placeSnakeElement(startingField, 1);
				jungle.getFields()[startingField.getRow()][startingField.getColumn()].setCharacter(null);
			}
			snakeType.setAmount(snakeType.getAmount() + 1);
			currentType = previousSnakeType;
		}
	}

	private void placeSnakeElement(Field previousField, int elementIndex) {
		/*
		 * If the snake is completely distributed, an attempt is made to distribute the
		 * next snake.
		 */
		if (elementIndex == currentType.getSigns().length()) {
			placeSnake();
			return;
		}

		/*
		 * The neighbors of the last snake element are listed, and attempts are made to
		 * distribute the snake step by step. If this fails, the fields in the jungle
		 * are released again, and 'placeSnake' is returned to.
		 */
		List<Field> validNeighborFields = createValidNeighbors(previousField);
		for (Field neighborField : validNeighborFields) {
			jungle.getFields()[neighborField.getRow()][neighborField.getColumn()]
					.setCharacter(currentType.getSigns().substring(elementIndex, elementIndex + 1));
			placeSnakeElement(neighborField, elementIndex + 1);
			if (System.nanoTime() - currentTime > 10000000000.0 || full == true) {
				if (full == false) {
					this.failed = true;
					return;
				} else {
					return;
				}
			}
			jungle.getFields()[neighborField.getRow()][neighborField.getColumn()].setCharacter(null);
		}
	}

	/**
	 * The empty fields of a jungle are filled with characters randomly selected
	 * from the character set of the jungle.
	 * 
	 * @param jungle The jungle in which the empty fields should be filled.
	 */
	private void fillRestOfJungle(Jungle jungle) {
		for (int i = 0; i < jungle.getRows(); i++) {
			for (int j = 0; j < jungle.getColumns(); j++) {
				if (jungle.getFields()[i][j].getCharacter() == null) {
					if (jungle.getSigns().equals("")) {
					} else {
						int rand = new Random().nextInt(0, jungle.getSigns().length());
						jungle.getFields()[i][j].setCharacter(jungle.getSigns().substring(rand, rand + 1));
					}
				}
			}
		}
	}

	private List<SnakeType> createValidSnakeTypes() {
		/*
		 * All snake types of the model with a count greater than 0 are returned, i.e.,
		 * those that still need to be distributed. Finally, the snake types are
		 * shuffled to ensure a random distribution.
		 */
		List<SnakeType> validSnakeTypes = new ArrayList<SnakeType>();
		for (SnakeType snakeType : model.getSnakeTypes()) {
			if (snakeType.getAmount() > 0) {
				validSnakeTypes.add(snakeType);
			}
		}
		Collections.shuffle(validSnakeTypes);
		return validSnakeTypes;
	}

	private List<Field> createValidNeighbors(Field previousField) {
		/*
		 * All neighbors of the last field are determined, where the arrangement of the
		 * neighbors in the jungle is always based on the neighborhood structure of the
		 * current snake type. Finally, the fields are shuffled to ensure a random
		 * distribution.
		 */
		List<Field> validNeighborFields = new ArrayList<Field>();
		for (Field neighborField : currentType.getStructure().getNeighbors(jungle, previousField)) {
			if (neighborField.getCharacter() == null) {
				validNeighborFields.add(neighborField);
			}
		}
		Collections.shuffle(validNeighborFields);
		return validNeighborFields;
	}

	private List<Field> createValidStartingFields() {
		/*
		 * All possible starting fields, i.e., all fields that do not have a character
		 * yet, are listed and shuffled to ensure a random distribution.
		 */
		List<Field> validStartingFields = new ArrayList<Field>();
		for (int i = 0; i < jungle.getFields().length; i++) {
			for (int j = 0; j < jungle.getFields()[0].length; j++) {
				if (jungle.getFields()[i][j].getCharacter() == null) {
					validStartingFields.add(jungle.getFields()[i][j]);
				}
			}
		}
		Collections.shuffle(validStartingFields);
		return validStartingFields;
	}

	private int minimumNumberUsedFields() {
		int amount = 0;
		for (SnakeType snakeType : snakeTypes) {
			amount += snakeType.getAmount() * snakeType.getSigns().length();
		}
		return amount;
	}

	private int numberOfTypesTotal() {
		int amount = 0;
		for (SnakeType snakeType : snakeTypes) {
			amount += snakeType.getAmount();
		}
		return amount;
	}

	/**
	 * Returns the current value of the private variable 'failed'. This makes it
	 * possible to determine whether a jungle could be generated within a certain
	 * time. Additionally, a second attempt can be started directly, as often a
	 * timeout is only due to an unlucky distribution of the snakes in the jungle.
	 * 
	 * @return Value of the variable <code>failed</code>.
	 */
	public boolean getFailed() {
		return failed;
	}

	/**
	 * Returns the model currently in the jungle generator. This is mainly for
	 * future changes and also for associated tests.
	 * 
	 * @return Value of the variable <code>model</code>.
	 */
	public IModel getModel() {
		return model;
	}

	/**
	 * The model of the jungle generator can be set through this. For example, it is
	 * possible to pass a model even if the parameterless constructor was initially
	 * used. It is also generally possible to change the model after class
	 * instantiation.
	 * 
	 * @param model The model to be passed.
	 * @throws IllegalArgumentException An exception is thrown if an inappropriate
	 *                                  model is passed.
	 */
	public void setModel(IModel model) throws IllegalArgumentException {
		if (model.getJungle().getRows() * model.getJungle().getColumns() < minimumNumberUsedFields()) {
			System.out.println("The model to be passed to the jungle generator contains more\nsnakes "
					+ "than can fit in the jungle. A jungle cannot be generated.");
			System.out.println();
			throw new IllegalArgumentException(
					"The jungle generator cannot be passed a model with" + " more snakes than can fit in the jungle.");
		}
		this.model = model;
	}

	/**
	 * Only the jungle of the model in the jungle generator is returned. This is
	 * mainly for future program extension and/or tests, as access to this part of
	 * the program is guaranteed.
	 * 
	 * @return The value of the variable <code>jungle</code>'.
	 */
	public Jungle getJungle() {
		return jungle;
	}

	/**
	 * Only the jungle can be changed here if desired. This is mainly for future
	 * program extension and/or tests.
	 * 
	 * @param jungle The jungle to be passed to the model of the jungle generator.
	 */
	public void setJungle(Jungle jungle) {
		this.jungle = jungle;
	}

	/**
	 * The number of fields to be covered by the snakes in the jungle when
	 * distributed is passed. This includes the length and number of the individual
	 * snake types.
	 * 
	 * @return The number of fields to be used by the snake types of the model.
	 */
	public int getMinimumSnakes() {
		return minimumSnakes;
	}

	/**
	 * Returns the new jungle. This allows access to the jungle generated by the
	 * jungle generator. It should be noted that the new jungle is incomplete if
	 * returned before it has been filled.
	 * 
	 * @return The value of the variable <code>newJungle</code>.
	 */
	public Jungle getNewJungle() {
		return newJungle;
	}

	/**
	 * Returns a list of snake types present in the model of the jungle generator.
	 * This is for future program extension and/or tests.
	 * 
	 * @return The value of the variable <code>snakeTypes</code>.
	 */
	public List<SnakeType> getSnakeTypes() {
		return snakeTypes;
	}

	/**
	 * It is possible to change and/or extend the snake types present in the model
	 * of the jungle generator. This is for future program extension and/or tests.
	 * 
	 * @param snakeTypes The list of snake types to be passed to the model of the
	 *                   jungle generator.
	 */
	public void setSnakeTypes(List<SnakeType> snakeTypes) {
		this.snakeTypes = snakeTypes;
	}
}