package com.capgemini.chess.algorithms.data.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Class being the representation of Queen during the chess game
 * 
 * @author TMAZUREK
 *
 */

public class Queen implements Piece {

	private final Color color;
	private final PieceType type = PieceType.QUEEN;
	private boolean wasMoved;

	public Queen(Color color) {
		wasMoved = false;
		this.color = color;
	}

	/**
	 * Method used to check, if the possible move is a valid one for Queen, using
	 * the possibilities of Bishop and Rook
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
		Move result;

		if (from.getX() == to.getX() || from.getY() == to.getY()) {
			Rook rook = new Rook(getColor());
			result = rook.validateMove(state, from, to);
		} else {
			Bishop bishop = new Bishop(getColor());
			result = bishop.validateMove(state, from, to);
		}
		result.setMovedPiece(this);

		return result;
	}
	
	/**
	 * Method returning every potential possible location for queen
	 * 
	 * @param currentLocation
	 *            location of queen
	 * @return list of potential possible locations for queen
	 */

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleCoordinates = new ArrayList<Coordinate>();

		Rook rook = new Rook(getColor());
		possibleCoordinates.addAll(rook.getPossibleLocations(currentLocation));
		Bishop bishop = new Bishop(getColor());
		possibleCoordinates.addAll(bishop.getPossibleLocations(currentLocation));

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
		if (getType() == ((Queen) second).getType())
			if (getColor() == ((Queen) second).getColor())
				return true;
		return false;
	}
}
