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
 * Class being the representation of Pawn during the chess game
 * @author TMAZUREK
 *
 */

public class Pawn implements Piece {

	private final Color color;
	private final PieceType type = PieceType.PAWN;
	private boolean wasMoved;

	public Pawn(Color color) {
		wasMoved = false;
		this.color = color;
	}
	
	/**
	 * Method used to check, if the possible move is a valid one for Pawn
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
		if (color == Color.WHITE) {
			possibles.removeAll(getImpossibleAttacksForWhite(state, from));
			possibles.addAll(addPotentialWhiteEnPassant(state, from));
		} else {
			possibles.removeAll(getImpossibleAttacksForBlack(state, from));
			possibles.addAll(addPotentialBlackEnPassant(state, from));
		}

		if (!possibles.contains(to))
			throw new InvalidMoveException();

		Move result = new Move();
		MoveType type = MoveTypeChecker.getMoveType(getColor(), state, to);
		if (type == null)
			throw new InvalidMoveException();
		if (type == MoveType.ATTACK && to.getX() != from.getX())
			type = MoveType.EN_PASSANT;
		if (type == MoveType.CAPTURE && to.getX() == from.getX())
			throw new InvalidMoveException();

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(this);
		result.setType(type);

		return result;
	}
	
	/**
	 * Method returning every potential possible location for pawn, including en-passant
	 * 
	 * @param currentLocation
	 *            location of pawn
	 * @return list of potential possible locations for pawn
	 */

	@Override
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation) {

		List<Coordinate> possibleLocations = new ArrayList<>();
		if (color == Color.WHITE) {
			possibleLocations.addAll(possibleForWhite(currentLocation));
		} else {
			possibleLocations.addAll(possibleForBlack(currentLocation));
		}

		return possibleLocations;
	}

	private List<Coordinate> getImpossibleAttacksForWhite(Piece[][] state, Coordinate currentLocation) {
		List<Coordinate> result = new ArrayList<>();
		if (currentLocation.getX() + 1 < Board.SIZE) {
			Piece temp = state[currentLocation.getX() + 1][currentLocation.getY() + 1];
			if (temp == null || temp.getColor() == color) {
				result.add(new Coordinate(currentLocation.getX() + 1, currentLocation.getY() + 1));
			}
		}
		if (currentLocation.getX() - 1 >= 0) {
			Piece temp = state[currentLocation.getX() - 1][currentLocation.getY() + 1];
			if (temp == null || temp.getColor() == color) {
				result.add(new Coordinate(currentLocation.getX() - 1, currentLocation.getY() + 1));
			}
		}
		return result;
	}

	private List<Coordinate> getImpossibleAttacksForBlack(Piece[][] state, Coordinate currentLocation) {
		List<Coordinate> result = new ArrayList<>();
		if (currentLocation.getX() + 1 < Board.SIZE) {
			Piece temp = state[currentLocation.getX() + 1][currentLocation.getY() - 1];
			if (temp == null || temp.getColor() == color) {
				result.add(new Coordinate(currentLocation.getX() + 1, currentLocation.getY() - 1));
			}
		}
		if (currentLocation.getX() - 1 >= 0) {
			Piece temp = state[currentLocation.getX() - 1][currentLocation.getY() - 1];
			if (temp == null || temp.getColor() == color) {
				result.add(new Coordinate(currentLocation.getX() - 1, currentLocation.getY() - 1));
			}
		}
		return result;
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

	private List<Coordinate> addPotentialWhiteEnPassant(Piece[][] state, Coordinate currentLocation) {
		List<Coordinate> result = new ArrayList<>();
		if (currentLocation.getY() == 4) {
			if (currentLocation.getX() + 1 < Board.SIZE) {
				Piece temp = state[currentLocation.getX() + 1][4];
				if (temp != null && temp.getType() == PieceType.PAWN && temp.getColor() != color)
					result.add(new Coordinate(currentLocation.getX() + 1, 5));
			}
			if (currentLocation.getX() - 1 >= 0) {
				Piece temp = state[currentLocation.getX() - 1][4];
				if (temp != null && temp.getType() == PieceType.PAWN && temp.getColor() != color)
					result.add(new Coordinate(currentLocation.getX() - 1, 5));
			}
		}
		return result;
	}

	private List<Coordinate> addPotentialBlackEnPassant(Piece[][] state, Coordinate currentLocation) {
		List<Coordinate> result = new ArrayList<>();
		if (currentLocation.getY() == 3) {
			if (currentLocation.getX() + 1 < Board.SIZE) {
				Piece temp = state[currentLocation.getX() + 1][3];
				if (temp != null && temp.getType() == PieceType.PAWN && temp.getColor() != color)
					result.add(new Coordinate(currentLocation.getX() + 1, 2));
			}
			if (currentLocation.getX() - 1 >= 0) {
				Piece temp = state[currentLocation.getX() - 1][3];
				if (temp != null && temp.getType() == PieceType.PAWN && temp.getColor() != color)
					result.add(new Coordinate(currentLocation.getX() - 1, 2));
			}
		}
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
		if (getType() == ((Pawn) second).getType())
			if (getColor() == ((Pawn) second).getColor())
				return true;
		return false;
	}
}
