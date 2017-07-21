package com.capgemini.chess.algorithms.data.pieces;

import java.util.List;
import java.util.ArrayList;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Class being the representation of Rook during the chess game
 * 
 * @author TMAZUREK
 *
 */

public class Rook implements Piece {
	private final Color color;
	private final PieceType type = PieceType.ROOK;
	private boolean wasMoved;

	public Rook(Color color) {
		wasMoved = false;
		this.color = color;
	}
	
	/**
	 * Method used to check, if the possible move is a valid one for Rook
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

		result.setType(type);
		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(this);

		int i = 1;
		if (to.getX() < from.getX()) {
			while (from.getX() - i > to.getX()) {
				if (state[from.getX() - i][from.getY()] != null)
					throw new InvalidMoveException();
				i++;
			}
		} else if (to.getX() > from.getX()) {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY()] != null)
					throw new InvalidMoveException();
				i++;
			}
		} else if (to.getY() < from.getY()) {
			while (from.getY() - i > to.getX()) {
				if (state[from.getX()][from.getY() - i] != null)
					throw new InvalidMoveException();
				i++;
			}
		} else {
			while (from.getY() + i < to.getY()) {
				if (state[from.getX()][from.getY() + i] != null)
					throw new InvalidMoveException();
				i++;
			}
		}

		return result;
	}
	
	/**
	 * Method returning every potential possible location for rook
	 * 
	 * @param currentLocation
	 *            location of rook
	 * @return list of potential possible locations for rook
	 */

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {
		List<Coordinate> possibleCoordinats = new ArrayList<Coordinate>();

		for (int i = 0; i < Board.SIZE; i++) {
			if (i != currentLocation.getX())
				possibleCoordinats.add(new Coordinate(i, currentLocation.getY()));
		}
		for (int i = 0; i < Board.SIZE; i++) {
			if (i != currentLocation.getY())
				possibleCoordinats.add(new Coordinate(currentLocation.getX(), i));
		}

		return possibleCoordinats;

	}

	@Override
	public Color getColor() {
		return color;
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
		if (getType() == ((Rook) second).getType())
			if (getColor() == ((Rook) second).getColor())
				return true;
		return false;
	}
}
