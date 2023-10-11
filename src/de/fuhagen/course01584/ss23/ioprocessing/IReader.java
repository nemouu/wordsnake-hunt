package de.fuhagen.course01584.ss23.ioprocessing;

import de.fuhagen.course01584.ss23.model.IModel;

/**
 * An interface to establish an interface between the data model and data input.
 * This makes it possible to provide data to the program through a different
 * form of data input in the future, if desired. For example, it is possible to
 * provide data in various file formats.
 * 
 * @author Philip Redecker
 *
 */
public interface IReader {

	/**
	 * The input file is searched for at <code>filePath</code>. If a file is found,
	 * it is checked to see if it has the correct format, and then the data
	 * contained in the file is written into the data model.
	 * 
	 * @param filePath The path to the file to be read.
	 * @throws Exception An exception is thrown if no file is found or if it has the
	 *                   wrong format.
	 */
	void readFile(String filePath) throws Exception;

	/**
	 * Returns the model that is currently in the reader class implementing the
	 * IReader interface. This is primarily intended for future changes and testing.
	 * 
	 * @return Value of the variable <code>transferredModel</code>.
	 */
	IModel getTransferredModel();

}