package com.capgemini.chess.algorithms.data.pieces;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Class being the representation of Knight during the chess game
 * @author TMAZUREK
 *
 */

public class Knight  implements Piece {
	private final Color color;
	private final PieceType type = PieceType.KNIGHT;
	private boolean wasMoved;

	public Knight(Color color) {
		wasMoved = false;
		this.color = color;
	}
	
	/**
	 * Method used to check, if the possible move is a valid one for Knight
	 * 
	 * @param state
	 *            current state of board
	 * @param from
	 *            where the move begins
	 * @param to
	 *            destination of move
	 * @return if the move is correct it return the Move instance, otherwise
	 *         throws InvalidMoveException
	 */
	
	@Override
	public Move validateMove(Piece[][] state, Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> possibles = getPossibleLocations(from);
		if(!possibles.contains(to))
			throw new InvalidMoveException();
		
		Move result = new Move();
		MoveType type = MoveTypeChecker.getMoveType(getColor(), state, to);
		if (type == null)
			throw new InvalidMoveException();

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(this);
		result.setType(type);

		return result;
	}

	/**
	 * Method returning every potential possible location for knight
	 * 
	 * @param currentLocation
	 *            location of knight
	 * @return list of potential possible locations for knight
	 */
	
	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleCoordinates = new LinkedList<>();
		int x = currentLocation.getX();
		int y = currentLocation.getY();

		possibleCoordinates.add(new Coordinate(x - 2, y + 1));
		possibleCoordinates.add(new Coordinate(x - 2, y - 1));
		possibleCoordinates.add(new Coordinate(x + 2, y + 1));
		possibleCoordinates.add(new Coordinate(x + 2, y - 1));
		possibleCoordinates.add(new Coordinate(x - 1, y + 2));
		possibleCoordinates.add(new Coordinate(x - 1, y - 2));
		possibleCoordinates.add(new Coordinate(x + 1, y + 2));
		possibleCoordinates.add(new Coordinate(x + 1, y - 2));
		
		possibleCoordinates = possibleCoordinates
				.stream()
				.filter(isOutOfTheBoard())
				.collect(Collectors.toList());
		
		return possibleCoordinates;
	}
	
	private static Predicate<Coordinate> isOutOfTheBoard() {
		return coor -> coor.getX() >= 0 && coor.getX() < Board.SIZE && coor.getY() >= 0 && coor.getY() < Board.SIZE;
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
	public void setMoved() {
		wasMoved = true;
	}
	
	@Override
	public boolean wasMoved() {
		return wasMoved;
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
