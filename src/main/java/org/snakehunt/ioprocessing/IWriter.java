package org.snakehunt.ioprocessing;

import org.snakehunt.model.IModel;

/**
 * An interface to establish an interface between the data model and data
 * output. This allows the program to be rewritten in the future to output data
 * in a desired way. For example, it is possible to output data in various file
 * formats.
 * 
 * @author Philip Redecker
 *
 */
public interface IWriter {

	/**
	 * A method to write the data currently in the model to an output file. The path
	 * where the file should be saved is specified by <code>fileName</code>.
	 * 
	 * @param fileName The name the generated file should have.
	 * @throws Exception An exception is thrown if an error occurs during writing or
	 *                   the <code>fileName</code> has the wrong format.
	 */
	void writeInFile(String fileName) throws Exception;

	/**
	 * Returns the model that is currently in the writer class implementing the
	 * IWriter interface. This is primarily intended for future changes and testing.
	 * 
	 * @return Value of the variable <code>toBeTransferredModel</code>.
	 */
	IModel getToBeTransferredModel();

	/**
	 * Sets the model of the writer class. This makes it possible to pass a model,
	 * even if the parameterless constructor was initially used. It is also possible
	 * in general to change the model after the class has been instantiated.
	 * 
	 * @param toBeTransferredModel The model to be passed.
	 */
	void setToBeTransferredModel(IModel toBeTransferredModel);

}