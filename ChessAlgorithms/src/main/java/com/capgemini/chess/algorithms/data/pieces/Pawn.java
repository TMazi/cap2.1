package com.capgemini.chess.algorithms.data.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Pawn implements Piece {

	private final Color color;
	private final PieceType type = PieceType.PAWN;

	public Pawn(Color color) {
		this.color = color;
	}

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		if (color == Color.WHITE)
			return possibleForWhite(currentLocation);
		else
			return possibleForBlack(currentLocation);
	}

	private List<Coordinate> possibleForWhite(Coordinate currentLocation) {
		List<Coordinate> possibleCoordinates = new ArrayList<>();
		if (currentLocation.getY() == 1)
			possibleCoordinates.add(new Coordinate(currentLocation.getX(), currentLocation.getY() + 2));
		for (int i = -1; i < 2; i++) {
			if (currentLocation.getX() + i >= 0 && currentLocation.getX() < Board.SIZE)
				possibleCoordinates.add(new Coordinate(currentLocation.getX() + i, currentLocation.getY() + 1));
		}
		return possibleCoordinates;
	}

	private List<Coordinate> possibleForBlack(Coordinate currentLocation) {
		List<Coordinate> possibleCoordinates = new ArrayList<>();
		if (currentLocation.getY() == 6)
			possibleCoordinates.add(new Coordinate(currentLocation.getX(), currentLocation.getY() - 2));
		for (int i = -1; i < 2; i++) {
			if (currentLocation.getX() + i >= 0 && currentLocation.getX() < Board.SIZE)
				possibleCoordinates.add(new Coordinate(currentLocation.getX() + i, currentLocation.getY() - 1));
		}
		return possibleCoordinates;
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
		if (getType() == ((Pawn) second).getType())
			if (getColor() == ((Pawn) second).getColor())
				return true;
		return false;
	}
}
