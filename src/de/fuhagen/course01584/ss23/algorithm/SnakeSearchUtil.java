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
	 * @param modell Das Modell, dass der Utility Klasse uebergeben werden soll.
	 */
	public SnakeSearchUtil(IModel modell) {
		super();
		this.model = modell;
		this.averagePointsAllTypes = getAveragePointsAllTypes(modell.getSnakeTypes());
		this.pointsOfMostValuableType = getPointsOfMostValuableSnakeType(modell.getSnakeTypes());
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
		List<SnakeType> zulArten = new ArrayList<SnakeType>();
		for (SnakeType schlangenart : model.getSnakeTypes()) {
			zulArten.add(schlangenart);
		}
		zulArten.sort(new SnakeTypeComp());
		return zulArten;
	}

	@Override
	public List<Field> createValidStartingFields(SnakeType art) {
		/*
		 * Es werden alle Startfelder fuer eine bestimmte Schlangenart aufgelistet.
		 * Dabei werden nur die Felder hinzugefuegt, die dasselbe Zeichen wie das erste
		 * Zeichen der Zeichenkette der Schlangenart und die eine Verwendbarkeit von
		 * mehr als 0 haben. Die Felder werden schliesslich mit einem Feldkomparator
		 * sortiert. Details zu dem Feldkomparator finden sich weiter unten in dieser
		 * Klasse hier.
		 */
		List<Field> zulStart = new ArrayList<Field>();
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getCharacter().equals(art.getZeichenkette().substring(0, 1))
						&& model.getJungle().getFields()[i][j].getUsage() > 0) {
					zulStart.add(model.getJungle().getFields()[i][j]);
				}
			}
		}
		zulStart.sort(new FieldComp(art));
		return zulStart;
	}

	@Override
	public List<Field> createValidNeighbors(SnakeElement vorherigesGlied, Snake dieseSchlange) {
		/*
		 * Hiermit werdenzu einer Schlange und einem Schlangenglied die moeglichen
		 * Nachbarn aufgelistet. Es werden die Nachbarn hinzugefuegt, die mit
		 * getNachbarn() gefunden wurden und die eine Verwendbarkeit von mehr als 0
		 * haben. Schliesslich werden die Felder mit einem Feldkomparator
		 * sortiert.Details zu dem Feldkomparator finden sich weiter unten in dieser
		 * Klasse hier.
		 */
		List<Field> zulNachbarn = new ArrayList<Field>();
		for (Field nachbar : dieseSchlange.getType().getStruktur().getNeighbors(model.getJungle(),
				vorherigesGlied.getField())) {
			if (nachbar.getCharacter().equals(dieseSchlange.characterOfNextElement()) && nachbar.getUsage() > 0) {
				zulNachbarn.add(nachbar);
			}
		}
		zulNachbarn.sort(new FieldComp(dieseSchlange.getType()));
		return zulNachbarn;
	}

	@Override
	public IModel getModel() {
		return model;
	}

	@Override
	public void setModel(IModel modell) {
		this.model = modell;
	}

	private Double getProbablePointsOfOneType(SnakeType art) {
		/*
		 * Eine Hilfsfunktion fuer den Schlangenartkomparator. Es werden fuer eine
		 * Schlangenart die Punkte berechnet, die eine Schlange dieser Art in dem
		 * aktuellen Dschungel vorraussichtlich haben wird. Mit einbezogen werden
		 * hierbei alle Felder des Dschungels, die ein Zeichen haben, dass in der
		 * Zeichenkette der Schlange vorkommt.
		 */
		int punkteGesamt = 0;
		int anzahlFelder = 0;
		Double punkte = 0.0;
		for (int i = 0; i < model.getJungle().getFields().length; i++) {
			for (int j = 0; j < model.getJungle().getFields()[0].length; j++) {
				if (model.getJungle().getFields()[i][j].getUsage() > 0
						&& art.getZeichenkette().contains(model.getJungle().getFields()[i][j].getCharacter())) {
					punkteGesamt += model.getJungle().getFields()[i][j].getPoints();
					anzahlFelder++;
				}
			}
		}
		if (anzahlFelder > 0) {
			punkte = ((double) punkteGesamt / anzahlFelder);
		}
		return punkte;
	}

	private int getAmountNeighbors(SnakeType art, Jungle dschungel, Field feld) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Fuer eine Schlangenart, einen
		 * Dschungel und ein (aktuelles) Feld wird die Anzahl der Nachbarn mit
		 * Verwendbarkeit groesser als 0 ausgegeben.
		 */
		int anzahl = 0;
		for (Field nachbar : art.getStruktur().getNeighbors(dschungel, feld)) {
			if (nachbar.getUsage() > 0) {
				anzahl++;
			}
		}
		return anzahl;
	}

	private Double getAveragePointsAllTypes(List<SnakeType> arten) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Es werden die durchschnittlichen
		 * Punkte einer Schlangenart in einer Probleminstanz berechnet.
		 */
		int punkte = 0;
		for (SnakeType schlangenart : arten) {
			punkte += schlangenart.getPunkte();
		}
		if (arten.size() == 0) {
			return 0.0;
		} else {
			Double punkteGes = (double) (punkte / arten.size());
			return punkteGes;
		}
	}

	private int getPointsOfMostValuableSnakeType(List<SnakeType> arten) {
		/*
		 * Eine Hilfsfunktion fuer den Feldkomparator. Fuer eine Probleminstanz werden
		 * die Punkte der Schlangenart ausgegeben, die die meisten Punkte hat.
		 */
		int punkte = 0;
		for (SnakeType schlangenart : arten) {
			if (schlangenart.getPunkte() > punkte) {
				punkte = schlangenart.getPunkte();
			}
		}
		return punkte;
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
		public int compare(SnakeType art1, SnakeType art2) {
			if (((art1.getZeichenkette().length() * getProbablePointsOfOneType(art1))
					+ art1.getPunkte()) > ((art2.getZeichenkette().length() * getProbablePointsOfOneType(art2))
							+ art2.getPunkte())) {
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

		public FieldComp(SnakeType art) {
			super();
			this.type = art;
		}

		@Override
		public int compare(Field feld1, Field feld2) {
			if (pointsOfMostValuableType - averagePointsAllTypes > 15.0) {
				if (((getAmountNeighbors(type, model.getJungle(), feld1)) < (getAmountNeighbors(type,
						model.getJungle(), feld2)))) {
					return -1;
				}
			} else {
				if (getAmountNeighbors(type, model.getJungle(), feld1)
						- getAmountNeighbors(type, model.getJungle(), feld2) > 4) {
					return -1;
				}
			}
			if (feld1.getPoints() > feld2.getPoints()) {
				return -1;
			} else {
				return 0;
			}
		}
	}
}