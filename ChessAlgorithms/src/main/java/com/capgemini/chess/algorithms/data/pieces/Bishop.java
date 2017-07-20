package com.capgemini.chess.algorithms.data.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Bishop implements Piece {

	private final Color color;
	private final PieceType type = PieceType.BISHOP;

	public Bishop(Color color) {
		this.color = color;
	}

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleCoordinates = new ArrayList<Coordinate>();
		int x = currentLocation.getX();
		int y = currentLocation.getY();
		while (x > 0 && y > 0) {
			x--;
			y--;
			possibleCoordinates.add(new Coordinate(x, y));
		}
		x = currentLocation.getX();
		y = currentLocation.getY();
		
		while (x < Board.SIZE - 1 && y < Board.SIZE - 1) {
			x++;
			y++;
			possibleCoordinates.add(new Coordinate(x, y));
		}
		
		x = currentLocation.getX();
		y = currentLocation.getY();
		
		while(x > 0 && y < Board.SIZE-1) {
			x--;
			y++;
			possibleCoordinates.add(new Coordinate(x,y));
		}
		
		x = currentLocation.getX();
		y = currentLocation.getY();
		while(x < Board.SIZE-1 && y > 0) {
			x++;
			y--;
			possibleCoordinates.add(new Coordinate(x,y));
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
		if(getType() == ((Bishop) second).getType())
			if(getColor() == ((Bishop) second).getColor())
				return true;
		return false;
	}
}
