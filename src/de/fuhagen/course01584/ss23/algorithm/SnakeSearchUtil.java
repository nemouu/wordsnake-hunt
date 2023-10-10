package de.fuhagen.course01584.ss23.algorithm;

import java.util.*;

import de.fuhagen.course01584.ss23.model.*;

/**
 * Eine Klasse SchlangenSucheUtil, die die Schnittstelle ISchlangenSucheUtil und
 * damit die in ISchlangenSucheUtil angegebenen Methoden implementiert. Es
 * werden hier verschiedene Funktionen angeboten, die die Schlangensuche in der
 * Klasse SchlangenSuche vereinfachen und beschleunigen sollen. Mit der
 * Implementierung durch eine Schnittstelle soll die Funktionalitaet anpassbar
 * bleiben.
 * 
 * @author Philip Redecker
 *
 */
public class SnakeSearchUtil implements ISnakeSearchUtil {
	private IModel model;
	private Double averagePointsAllTypes;
	private int pointsOfMostValuableType;

	/**
	 * Ein parametrisierter Konstruktor, dem das Modell in dem gesucht wird, direkt
	 * uebergeben wird. Es wird hier unter keinen Umstaenden zu einer Ausnahme
	 * kommen, da diese in diesem Fall schon vorher in der Klasse SchlangenSuche
	 * abgefangen wird.
	 * 
	 * @param model Das Modell, dass der Utility Klasse uebergeben werden soll.
	 */
	public SnakeSearchUtil(IModel model) {
		super();
		this.model = model;
		this.averagePointsAllTypes = getAveragePointsAllTypes(model.getSnakeTypes());
		this.pointsOfMostValuableType = getPointsOfMostValuableSnakeType(model.getSnakeTypes());
	}

	/**
	 * Ein parameterloser Konstruktor, so, dass es bei zukuenftiger Aanderung des
	 * Programmes moeglich ist, diese Klasse zum Beispiel zum Testen zu nutzen.
	 */
	public SnakeSearchUtil() {
		super();
	}

	@Override
	public List<SnakeType> createValidSnakeTypes() {
		/*
		 * Es werden alle Schlangenarten des Modelles aufgelistet und mittels einem
		 * Komparator so verglichen, dass die Schlangenart, die vorraussichtlich die
		 * meisten Punkte erzielt, ganz vorne in der Liste steht. Details zu dem
		 * Schlangenartkomparator finden sich weiter unten in dieser Klasse hier.
		 */
		List<SnakeType> validSnakeTypes = new ArrayList<SnakeType>();
		for (SnakeType snakeType : model.getSnakeTypes()) {
			validSnakeTypes.add(snakeType);
		}
		validSnakeTypes.sort(new SnakeTypeComp());
		return validSnakeTypes;
	}

	@Override
	public List<Field> createValidStartingFields(SnakeType type) {
		/*
		 * Es werden alle Startfelder fuer eine bestimmte Schlangenart aufgelistet.
		 * Dabei werden nur die Felder hinzugefuegt, die dasselbe Zeichen wie das erste
		 * Zeichen der Zeichenkette der Schlangenart und die eine Verwendbarkeit von
		 * mehr als 0 haben. Die Felder werden schliesslich mit einem Feldkomparator
		 * sortiert. Details zu dem Feldkomparator finden sich weiter unten in dieser
		 * Klasse hier.
		 */
		List<Field> validStartingFields = new ArrayList<Field>();
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getCharacter().equals(type.getSigns().substring(0, 1))
						&& model.getJungle().getFields()[i][j].getUsage() > 0) {
					validStartingFields.add(model.getJungle().getFields()[i][j]);
				}
			}
		}
		validStartingFields.sort(new FieldComp(type));
		return validStartingFields;
	}

	@Override
	public List<Field> createValidNeighbors(SnakeElement previousElement, Snake currentSnake) {
		/*
		 * Hiermit werdenzu einer Schlange und einem Schlangenglied die moeglichen
		 * Nachbarn aufgelistet. Es werden die Nachbarn hinzugefuegt, die mit
		 * getNachbarn() gefunden wurden und die eine Verwendbarkeit von mehr als 0
		 * haben. Schliesslich werden die Felder mit einem Feldkomparator
		 * sortiert.Details zu dem Feldkomparator finden sich weiter unten in dieser
		 * Klasse hier.
		 */
		List<Field> validNeighborFields = new ArrayList<Field>();
		for (Field neighborField : currentSnake.getType().getStructure().getNeighbors(model.getJungle(),
				previousElement.getField())) {
			if (neighborField.getCharacter().equals(currentSnake.characterOfNextElement()) && neighborField.getUsage() > 0) {
				validNeighborFields.add(neighborField);
			}
		}
		validNeighborFields.sort(new FieldComp(currentSnake.getType()));
		return validNeighborFields;
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel model) {
		this.model = model;
	}

	private Double getProbablePointsOfOneType(SnakeType type) {
		/*
		 * Eine Hilfsfunktion fuer den Schlangenartkomparator. Es werden fuer eine
		 * Schlangenart die Punkte berechnet, die eine Schlange dieser Art in dem
		 * aktuellen Dschungel vorraussichtlich haben wird. Mit einbezogen werden
		 * hierbei alle Felder des Dschungels, die ein Zeichen haben, dass in der
		 * Zeichenkette der Schlange vorkommt.
		 */
		int pointsTotal = 0;
		int numberOfFields = 0;
		Double points = 0.0;
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getUsage() > 0
						&& type.getSigns().contains(model.getJungle().getFields()[i][j].getCharacter())) {
					pointsTotal += model.getJungle().getFields()[i][j].getPoints();
					numberOfFields++;
				}
			}
		}
		if (numberOfFields > 0) {
			points = ((double) pointsTotal / numberOfFields);
		}
		return points;
	}

	private int getAmountNeighbors(SnakeType type, Jungle jungle, Field field) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Fuer eine Schlangenart, einen
		 * Dschungel und ein (aktuelles) Feld wird die Anzahl der Nachbarn mit
		 * Verwendbarkeit groesser als 0 ausgegeben.
		 */
		int amount = 0;
		for (Field neighborField : type.getStructure().getNeighbors(jungle, field)) {
			if (neighborField.getUsage() > 0) {
				amount++;
			}
		}
		return amount;
	}

	private Double getAveragePointsAllTypes(List<SnakeType> types) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Es werden die durchschnittlichen
		 * Punkte einer Schlangenart in einer Probleminstanz berechnet.
		 */
		int points = 0;
		for (SnakeType snakeType : types) {
			points += snakeType.getPoints();
		}
		if (types.size() == 0) {
			return 0.0;
		} else {
			Double pointsTotal = (double) (points / types.size());
			return pointsTotal;
		}
	}

	private int getPointsOfMostValuableSnakeType(List<SnakeType> types) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Fuer eine Probleminstanz werden
		 * die Punkte der Schlangenart ausgegeben, die die meisten Punkte hat.
		 */
		int points = 0;
		for (SnakeType snakeType : types) {
			if (snakeType.getPoints() > points) {
				points = snakeType.getPoints();
			}
		}
		return points;
	}

	/**
	 * Eine selbst erstellte Komparator Klasse, die die Comparator Klasse aus
	 * java.util implementiert. Es soll dadruch besser moeglich sein die
	 * tatsaechlichen Punkte der einzelnen Schlangenarten zu vergleichen. Dabei wird
	 * immer die Laenge der jeweiligen Schlange und die durchschnittliche Punktzahl
	 * der Felder, die fuer die Schlange ueberhaupt in Frage kommen, mit einbezogen.
	 * 
	 * @author Philip Redecker
	 *
	 */
	private class SnakeTypeComp implements Comparator<SnakeType> {

		@Override
		public int compare(SnakeType type1, SnakeType type2) {
			if (((type1.getSigns().length() * getProbablePointsOfOneType(type1))
					+ type1.getPoints()) > ((type2.getSigns().length() * getProbablePointsOfOneType(type2))
							+ type2.getPoints())) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	/**
	 * Eine selbst erstellte Komparator Klasse, die die Comparator Klasse aus
	 * java.util implementiert. Hier kann insgesamt nach drei verschiedenen
	 * Kriterien sortiert werden. Hat eine Probleminstanz Schlangen, die besonders
	 * viele Punkte haben im Vergleich zu anderen Schlangen desselben Problemes so
	 * wird nach der MRV Strategie gesucht. Das heisst es werden zunaechst immer die
	 * Wege gegangen, die am wahrscheinlichsten zu einem Abbruch fuehren, denn so
	 * kann so schnell wie moeglich weiter gesucht werden. Sind die Punkte aller
	 * Schlangen eher gleich, so wird nach der Anzahl der freien Nachbarfelder
	 * sortiert. So kann sichergestellt werden dass zum Beispiel Felder in der Ecke
	 * eines Dschungels vermieden werden. Schliesslich wird in beiden dieser Faelle
	 * bei Gleichheit einfach nach Feldpunkten sortiert.
	 * 
	 * @author Philip Redecker
	 *
	 */
	private class FieldComp implements Comparator<Field> {
		private SnakeType type;

		public FieldComp(SnakeType type) {
			super();
			this.type = type;
		}

		@Override
		public int compare(Field field1, Field field2) {
			if (pointsOfMostValuableType - averagePointsAllTypes > 15.0) {
				if (((getAmountNeighbors(type, model.getJungle(), field1)) < (getAmountNeighbors(type,
						model.getJungle(), field2)))) {
					return -1;
				}
			} else {
				if (getAmountNeighbors(type, model.getJungle(), field1)
						- getAmountNeighbors(type, model.getJungle(), field2) > 4) {
					return -1;
				}
			}
			if (field1.getPoints() > field2.getPoints()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}