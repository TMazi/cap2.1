package com.capgemini.chess.algorithms.data.pieces;

import java.util.ArrayList;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Class being responsible for representing Bishop in the chess game
 * 
 * @author TMAZUREK
 *
 */

public class Bishop implements Piece {

	private final Color color;
	private final PieceType type = PieceType.BISHOP;
	private boolean wasMoved;

	public Bishop(Color color) {
		wasMoved = false;
		this.color = color;
	}

	/**
	 * Method used to check, if the possible move is a valid one for bishop
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
		if (!possibles.contains(to))
			throw new InvalidMoveException();

		Move result = new Move();

		MoveType type = MoveTypeChecker.getMoveType(getColor(), state, to);
		if (type == null)
			return null;

		result.setType(type);
		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(this);

		int i = 1;
		if (to.getX() < from.getX()) {
			if (to.getY() < from.getY()) {
				while (from.getX() - i > to.getX()) {
					if (state[from.getX() - i][from.getY() - i] != null)
						throw new InvalidMoveException();
					i++;
				}
			} else {
				while (from.getX() - i > to.getX()) {
					if (state[from.getX() - i][from.getY() + i] != null)
						throw new InvalidMoveException();
					i++;
				}
			}
		} else if (to.getY() < from.getY()) {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY() - i] != null)
					throw new InvalidMoveException();
				i++;
			}
		} else {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY() + i] != null)
					throw new InvalidMoveException();
				i++;
			}
		}
		return result;
	}

	/**
	 * Method returning every potential possible location for bishop
	 * 
	 * @param currentLocation
	 *            location of bishop on board
	 * @return list of potential possible locations for bishop
	 */

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

		while (x > 0 && y < Board.SIZE - 1) {
			x--;
			y++;
			possibleCoordinates.add(new Coordinate(x, y));
		}

		x = currentLocation.getX();
		y = currentLocation.getY();
		while (x < Board.SIZE - 1 && y > 0) {
			x++;
			y--;
			possibleCoordinates.add(new Coordinate(x, y));
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
		if (getType() == ((Bishop) second).getType())
			if (getColor() == ((Bishop) second).getColor())
				return true;
		return false;
	}
}
