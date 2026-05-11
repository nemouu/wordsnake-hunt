package org.snakehunt.view;

import org.snakehunt.model.IModel;

/**
 * This class provides constructors and methods to enable the representation in
 * the form of text output on the console.
 * 
 * @author Philip Redecker
 *
 */
public class ViewText implements IView {
	private IModel modelToBeViewed;

	/**
	 * A parameterized constructor for the ViewText class that directly passes the
	 * model to be displayed.
	 * 
	 * @param modelToBeViewed The model to be displayed.
	 */
	public ViewText(IModel modelToBeViewed) {
		super();
		this.modelToBeViewed = modelToBeViewed;
	}

	/**
	 * A parameterless constructor, allowing this class to be used for testing, for
	 * example, when the program is changed in the future.
	 */
	public ViewText() {
		super();
	}

	/**
	 * It returns the model that is currently in the ViewText representation. This
	 * is primarily intended for future changes and associated tests.
	 * 
	 * @return The value of the variable <code>modelToBeViewed</code>.
	 */
	public IModel getModelToBeViewed() {
		return modelToBeViewed;
	}

	/**
	 * The model of the ViewText class can be set. For example, it is possible to
	 * pass a model, even if the parameterless constructor was initially used. It is
	 * generally possible to change the model after instantiating the class.
	 * 
	 * @param modelToBeViewed The model to be passed.
	 */
	public void setModelToBeViewed(IModel modelToBeViewed) {
		this.modelToBeViewed = modelToBeViewed;
	}

	@Override
	public void view() {
		System.out.println(modelToBeViewed.toString());
		System.out.println();
	}
}