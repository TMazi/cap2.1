package com.capgemini.chess.algorithms.data.pieces;

import java.util.LinkedList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;

public class Knight  implements Piece {
	private final Color color;
	private final PieceType type = PieceType.KNIGHT;

	public Knight(Color color) {
		this.color = color;
	}

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleCoordinats = new LinkedList<>();
		possibleCoordinats.add(new Coordinate(currentLocation.getX()-2, currentLocation.getY()+1));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()-2, currentLocation.getY()-1));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()+2, currentLocation.getY()+1));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()+2, currentLocation.getY()-1));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()-1, currentLocation.getY()+2));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()-1, currentLocation.getY()-2));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()+1, currentLocation.getY()+2));
		possibleCoordinats.add(new Coordinate(currentLocation.getX()+1, currentLocation.getY()-2));
		
		for(int i = 0; i < possibleCoordinats.size(); i++) {
			Coordinate coor = possibleCoordinats.get(i);
			if(coor.getX() < 0 || coor.getX() >= Board.SIZE || coor.getY() < 0 || coor.getY() >= Board.SIZE) {
				possibleCoordinats.remove(i);
				i--;
			}
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
		if(getType() == ((Knight) second).getType())
			if(getColor() == ((Knight) second).getColor())
				return true;
		return false;
	}

}
