package com.capgemini.chess.algorithms.data.pieces;

import java.util.List;
import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Rook implements Piece {
	private final Color color;
	private final PieceType type = PieceType.ROOK;

	public Rook(Color color) {
		this.color = color;
	}

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {
		List<Coordinate> possibleCoordinats = new ArrayList<Coordinate>();

		for(int i = 0; i < Board.SIZE; i++) {
			if(i != currentLocation.getX())
				possibleCoordinats.add(new Coordinate(i, currentLocation.getY()));
		}
		for(int i = 0; i < Board.SIZE; i++) {
			if(i != currentLocation.getY())
				possibleCoordinats.add(new Coordinate(currentLocation.getX(), i));
		}
		
		return possibleCoordinats;
				
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
		if(getType() == ((Rook) second).getType())
			if(getColor() == ((Rook) second).getColor())
				return true;
		return false;
	}
}
