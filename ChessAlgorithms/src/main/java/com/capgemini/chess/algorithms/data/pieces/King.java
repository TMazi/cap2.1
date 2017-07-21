package com.capgemini.chess.algorithms.data.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class King implements Piece {

	private final Color color;
	private final PieceType type = PieceType.KING;

	public King(Color color) {
		this.color = color;
	}

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleLocations = new ArrayList<Coordinate>();
		for (int x = currentLocation.getX() - 1; x <= currentLocation.getX() + 1; x++) {
			if (x >= 0 && x < Board.SIZE) {
				for (int y = currentLocation.getY() - 1; y <= currentLocation.getY() + 1; y++) {
					if (y >= 0 && y < Board.SIZE) {
						if (x != currentLocation.getX() || y != currentLocation.getY())
							possibleLocations.add(new Coordinate(x, y));
					}
				}
			}
		}
		if (color == Color.WHITE)
			possibleLocations.addAll(getWhiteCastle());
		else
			possibleLocations.addAll(getBlackCastle());
		return possibleLocations;
	}

	private List<Coordinate> getWhiteCastle() {
		List<Coordinate> result = new ArrayList<>();
		result.add(new Coordinate(2, 0));
		result.add(new Coordinate(6, 0));
		return result;

	}

	private List<Coordinate> getBlackCastle() {
		List<Coordinate> result = new ArrayList<>();
		result.add(new Coordinate(2, 7));
		result.add(new Coordinate(6, 7));
		return result;

	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public PieceType getType() {
		return type;
	}

	@Override
	public boolean equals(Object second) {
		if (second == this) {
			return true;
		}
		if (second == null || second.getClass() != getClass()) {
			return false;
		}
		if (getType() == ((King) second).getType())
			if (getColor() == ((King) second).getColor())
				return true;
		return false;
	}

}
