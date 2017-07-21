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
import com.capgemini.chess.algorithms.validators.CheckValidator;

/**
 * Class being the representation of King during the chess game
 * 
 * @author TMAZUREK
 *
 */

public class King implements Piece {

	private final Color color;
	private final PieceType type = PieceType.KING;
	private boolean wasMoved;

	public King(Color color) {
		wasMoved = false;
		this.color = color;
	}

	/**
	 * Method used to check, if the possible move is a valid one for king
	 * 
	 * @param state
	 *            current state of board
	 * @param from
	 *            where the move begins
	 * @param to
	 *            destination of move
	 * @return if the move is correct it is the Move instance, otherwise throws
	 *         InvalidMoveException
	 */

	@Override
	public Move validateMove(Piece[][] state, Coordinate from, Coordinate to) throws InvalidMoveException {
		List<Coordinate> possibles = getPossibleLocations(from);
		if (getColor() == Color.BLACK)
			possibles.addAll(getPossibleCastlingForBlackKing(state, from));
		else
			possibles.addAll(getPossibleCastlingForWhiteKing(state, from));

		if (!possibles.contains(to))
			throw new InvalidMoveException();

		Move result = new Move();
		MoveType type = MoveTypeChecker.getMoveType(getColor(), state, to);
		if (type == null)
			throw new InvalidMoveException();

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(this);
		result.setType(type);

		if (type == MoveType.ATTACK && Math.abs(to.getX() - from.getX()) > 1)
			result.setType(MoveType.CASTLING);

		return result;
	}

	/**
	 * Method returning every potential possible location for king, including castling
	 * 
	 * @param currentLocation
	 *            location of king
	 * @return list of potential possible locations for king
	 */

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
		return possibleLocations;
	}

	private List<Coordinate> getPossibleCastlingForBlackKing(Piece[][] state, Coordinate from) {

		List<Coordinate> result = new ArrayList<>();
		Coordinate kingLocation = new Coordinate(4, 7);
		if (from.equals(kingLocation) && !wasMoved()) {
			if (state[5][7] == null && state[6][7] == null && state[7][7] != null && !state[7][7].wasMoved()) {
				if (willNotBeChecked(state, kingLocation, new Coordinate(5, 7)))
					result.add(new Coordinate(6, 7));
			}
			if (state[3][7] == null && state[2][7] == null && state[1][7] == null && state[0][7] != null
					&& !state[0][7].wasMoved()) {
				if (willNotBeChecked(state, kingLocation, new Coordinate(3, 7)))
					result.add(new Coordinate(2, 7));
			}
		}
		return result;
	}

	private List<Coordinate> getPossibleCastlingForWhiteKing(Piece[][] state, Coordinate from) {

		List<Coordinate> result = new ArrayList<>();
		Coordinate kingLocation = new Coordinate(4, 0);
		if (from.equals(kingLocation) && !wasMoved()) {
			if (state[5][0] == null && state[6][0] == null && state[7][0] != null && !state[7][0].wasMoved()) {
				if (willNotBeChecked(state, kingLocation, new Coordinate(5, 7)))
					if (willNotBeChecked(state, kingLocation, new Coordinate(5, 0)))
						result.add(new Coordinate(6, 0));
			}
			if (state[3][0] == null && state[2][0] == null && state[1][0] == null && state[0][0] != null
					&& !state[0][0].wasMoved()) {
				if (willNotBeChecked(state, kingLocation, new Coordinate(3, 0)))
					result.add(new Coordinate(2, 0));
			}
		}
		return result;
	}

	private boolean willNotBeChecked(Piece[][] state, Coordinate from, Coordinate to) {
		Color kingColor = getColor();
		Piece temp;
		temp = state[to.getX()][to.getY()];

		state[to.getX()][to.getY()] = state[from.getX()][from.getY()];
		state[from.getX()][from.getY()] = null;

		boolean result = !CheckValidator.isInCheck(kingColor, state);

		state[from.getX()][from.getY()] = state[to.getX()][to.getY()];
		state[to.getX()][to.getY()] = temp;

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
		if (getType() == ((King) second).getType())
			if (getColor() == ((King) second).getColor())
				return true;
		return false;
	}

}
