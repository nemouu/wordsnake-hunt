package de.fuhagen.course01584.ss23.view;

import de.fuhagen.course01584.ss23.model.IModel;

/**
 * In dieser Klasse werden Konstruktoren und Methoden bereitgestellt, um die
 * Darstellung in Form von einer Textausgabe auf der Konsole zu ermoeglichen.
 * 
 * @author Philip Redecker
 *
 */
public class ViewText implements IView {
	private IModel modelToBeViewed;

	/**
	 * Ein parametrisierter Konstruktor fuer die Klasse DarstellungTextausgabe dem
	 * direkt das darzustellende Modell uebergeben wird.
	 * 
	 * @param modelToBeViewed Das Modell, das dargestellt werden soll.
	 */
	public ViewText(IModel modelToBeViewed) {
		super();
		this.modelToBeViewed = modelToBeViewed;
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public ViewText() {
		super();
	}

	/**
	 * Es wird das Modell zurueckgegeben, dass sich aktuell in der
	 * DarstellungTextausgabe befindet. Dies ist vor allem fuer kuenftige
	 * Aenderungen und auch fuer damit einhergehende Tests gedacht.
	 * 
	 * @return Der Wert der Variablen <code>darzustellendesModell</code>.
	 */
	public IModel getModelToBeViewed() {
		return modelToBeViewed;
	}

	/**
	 * Es kann das Modell der Klasse DarstellungTextausgabe gesetzt werden. So ist
	 * es zum Beispiel moeglich ein Modell zu uebergeben, auch wenn zunaechst der
	 * parameterlose Konstruktor genutzt wurde. Es ist auch im Allgemeinen moeglich
	 * das Modell nach Instanziierung der Klasse zu aendern.
	 * 
	 * @param modelToBeViewed Das Modell, das uebergeben werden soll.
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