package com.capgemini.chess.algorithms.validators;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.pieces.*;

public class MoveValidator {

	public static Move moveValidate(Piece piece, Piece[][] state, Coordinate from, Coordinate to) {
		switch (piece.getType()) {
		case QUEEN:
			return moveValidate((Queen) piece, state, from, to);
		case BISHOP:
			return moveValidate((Bishop) piece, state, from, to);
		case ROOK:
			return moveValidate((Rook) piece, state, from, to);
		case KNIGHT:
			return moveValidate((Knight) piece, state, from, to);
		case PAWN:
			return moveValidate((Pawn) piece, state, from, to);
		case KING:
			return moveValidate((King) piece, state, from, to);
		default:
			return null;
		}
	}

	private static Move moveValidate(Queen queen, Piece[][] state, Coordinate from, Coordinate to) {

		Move result;

		if (from.getX() == to.getX() || from.getY() == to.getY())
			result = moveValidate(new Rook(queen.getColor()), state, from, to);
		else
			result = moveValidate(new Bishop(queen.getColor()), state, from, to);
		if (result != null)
			result.setMovedPiece(queen);

		return result;
	}

	private static Move moveValidate(Rook rook, Piece[][] state, Coordinate from, Coordinate to) {
		Move result = new Move();
		MoveType type = getMoveType(rook.getColor(), state, to);
		if (type == null)
			return null;

		result.setType(type);
		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(rook);

		int i = 1;
		if (to.getX() < from.getX()) {
			while (from.getX() - i > to.getX()) {
				if (state[from.getX() - i][from.getY()] != null)
					return null;
				i++;
			}
		} else if (to.getX() > from.getX()) {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY()] != null)
					return null;
				i++;
			}
		} else if (to.getY() < from.getY()) {
			while (from.getY() - i > to.getX()) {
				if (state[from.getX()][from.getY() - i] != null)
					return null;
				i++;
			}
		} else {
			while (from.getY() + i < to.getY()) {
				if (state[from.getX()][from.getY() + i] != null)
					return null;
				i++;
			}
		}

		return result;
	}

	private static Move moveValidate(Bishop bishop, Piece[][] state, Coordinate from, Coordinate to) {

		Move result = new Move();

		MoveType type = getMoveType(bishop.getColor(), state, to);
		if (type == null)
			return null;

		result.setType(type);
		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(bishop);

		int i = 1;
		if (to.getX() < from.getX()) {
			if (to.getY() < from.getY()) {
				while (from.getX() - i > to.getX()) {
					if (state[from.getX() - i][from.getY() - i] != null)
						return null;
					i++;
				}
			} else {
				while (from.getX() - i > to.getX()) {
					if (state[from.getX() - i][from.getY() + i] != null)
						return null;
					i++;
				}
			}
		} else if (to.getY() < from.getY()) {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY() - i] != null)
					return null;
				i++;
			}
		} else {
			while (from.getX() + i < to.getX()) {
				if (state[from.getX() + i][from.getY() + i] != null)
					return null;
				i++;
			}
		}
		return result;
	}

	private static Move moveValidate(Knight knight, Piece[][] state, Coordinate from, Coordinate to) {
		Move result = new Move();
		MoveType type = getMoveType(knight.getColor(), state, to);
		if (type == null)
			return null;

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(knight);
		result.setType(type);

		return result;
	}

	private static Move moveValidate(Pawn pawn, Piece[][] state, Coordinate from, Coordinate to) {
		Move result = new Move();
		MoveType type = getMoveType(pawn.getColor(), state, to);
		if (type == null)
			return null;
		if (type == MoveType.ATTACK && to.getX() != from.getX())
			type = MoveType.EN_PASSANT;
		if (type == MoveType.CAPTURE && to.getX() == from.getX())
			return null;

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(pawn);
		result.setType(type);

		return result;
	}

	private static Move moveValidate(King king, Piece[][] state, Coordinate from, Coordinate to) {
		Move result = new Move();
		MoveType type = getMoveType(king.getColor(), state, to);
		if (type == null)
			return null;

		result.setFrom(from);
		result.setTo(to);
		result.setMovedPiece(king);
		result.setType(type);

		if (type == MoveType.ATTACK && Math.abs(to.getX() - from.getX()) > 1)
			result.setType(MoveType.CASTLING);

		return result;
	}

	private static MoveType getMoveType(Color color, Piece[][] state, Coordinate to) {
		if (state[to.getX()][to.getY()] != null) {
			if (state[to.getX()][to.getY()].getColor() == color)
				return null;
			else
				return MoveType.CAPTURE;
		} else
			return MoveType.ATTACK;
	}

}
