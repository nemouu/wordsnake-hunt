package de.fuhagen.course01584.ss23.view;

/**
 * Ein Interface zur Herstellung einer Schnittstelle zwischen Datenmodell und
 * Darstellungskomponente. Dadurch ist es moeglich in Zukunft das Programm durch
 * eine andere Form der Darstellung zu erweitern, sollte dies gewuenscht sein.
 * 
 * @author Philip Redecker
 *
 */
public interface IView {
	/**
	 * Eine Methode zum Darstellen von im Datenmodell zu findenen Daten. Es soll
	 * moeglich sein das Modell darzustellen in allen verschiedenen Situationen in
	 * denen sich das Modell befinden kann. Eine Darstellung soll also zum Beispiel
	 * moeglich sein, wenn eine Loesung vorhanden ist aber auch wenn keine Loesung
	 * vorhanden ist.
	 */
	void darstellen();

}