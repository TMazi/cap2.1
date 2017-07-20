package com.capgemini.chess.algorithms.validators;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.pieces.Piece;

public class CheckValidator {

	public static boolean isInCheck(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		if (isAttackedFromSide(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedFromTopBottom(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedFromAcross(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedByKnight(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedByPawn(kingsLocation, kingsColor, state))
			return true;

		return false;
	}

	private static boolean isAttackedFromSide(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
		while (continues && kingsLocation.getX() - i >= 0) {
			temp = state[kingsLocation.getX() - i][kingsLocation.getY()];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.ROOK || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		continues = true;
		i = 1;
		while (continues && kingsLocation.getX() + i < Board.SIZE) {
			temp = state[kingsLocation.getX() + i][kingsLocation.getY()];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.ROOK || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		return false;
	}

	private static boolean isAttackedFromTopBottom(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
		while (continues && kingsLocation.getY() - i >= 0) {
			temp = state[kingsLocation.getX()][kingsLocation.getY() - i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.ROOK || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		continues = true;
		i = 1;
		while (continues && kingsLocation.getY() + i < Board.SIZE) {
			temp = state[kingsLocation.getX()][kingsLocation.getY() + i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.ROOK || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		return false;
	}

	private static boolean isAttackedFromAcross(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
		while (continues && kingsLocation.getX() - i >= 0 && kingsLocation.getY() - i >= 0) {
			temp = state[kingsLocation.getX() - i][kingsLocation.getY() - i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.BISHOP || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		continues = true;
		i = 1;
		while (continues && kingsLocation.getX() + i < Board.SIZE && kingsLocation.getY() + i < Board.SIZE) {
			temp = state[kingsLocation.getX() + i][kingsLocation.getY() + i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.BISHOP || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		continues = true;
		i = 1;
		while (continues && kingsLocation.getX() - i >= 0 && kingsLocation.getY() + i < Board.SIZE) {
			temp = state[kingsLocation.getX() - i][kingsLocation.getY() + i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.BISHOP || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		continues = true;
		i = 1;
		while (continues && kingsLocation.getX() + i < Board.SIZE && kingsLocation.getY() - i >= 0) {
			temp = state[kingsLocation.getX() + i][kingsLocation.getY() - i];
			if (temp != null) {
				if (temp.getColor() == kingsColor)
					continues = false;
				else {
					if (temp.getType() == PieceType.BISHOP || temp.getType() == PieceType.QUEEN)
						return true;
					else
						return false;
				}
			}
			i++;
		}
		return false;
	}

	private static boolean isAttackedByKnight(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {

		List<Coordinate> possibleCoordinats = new LinkedList<>();
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() - 2, kingsLocation.getY() + 1));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() - 2, kingsLocation.getY() - 1));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() + 2, kingsLocation.getY() + 1));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() + 2, kingsLocation.getY() - 1));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() - 1, kingsLocation.getY() + 2));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() - 1, kingsLocation.getY() - 2));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() + 1, kingsLocation.getY() + 2));
		possibleCoordinats.add(new Coordinate(kingsLocation.getX() + 1, kingsLocation.getY() - 2));

		// TODO
		possibleCoordinats = possibleCoordinats.stream().filter(isOutOfTheBoard()).collect(Collectors.toList());

		/*
		 * for (int i = 0; i < possibleCoordinats.size(); i++) { Coordinate coor
		 * = possibleCoordinats.get(i); if (coor.getX() < 0 || coor.getX() >=
		 * Board.SIZE || coor.getY() < 0 || coor.getY() >= Board.SIZE) {
		 * possibleCoordinats.remove(i); i--; } }
		 */
		Piece temp;
		for (int i = 0; i < possibleCoordinats.size(); i++) {
			temp = state[possibleCoordinats.get(i).getX()][possibleCoordinats.get(i).getY()];
			if (temp != null && temp.getColor() != kingsColor)
				if (temp.getType() == PieceType.KNIGHT)
					return true;
		}
		return false;

	}

	private static Predicate<Coordinate> isOutOfTheBoard() {
		return coor -> coor.getX() >= 0 && coor.getX() < Board.SIZE && coor.getY() >= 0 && coor.getY() < Board.SIZE;
	}

	private static boolean isAttackedByPawn(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		if (kingsColor == Color.BLACK) {
			if (kingsLocation.getY() - 1 >= 0) {
				if (kingsLocation.getX() > 0) {
					temp = state[kingsLocation.getX() - 1][kingsLocation.getY() - 1];
					if (temp.getColor() != kingsColor && temp.getType() == PieceType.PAWN)
						return true;
				}
				if (kingsLocation.getX() < Board.SIZE - 1) {
					temp = state[kingsLocation.getX() + 1][kingsLocation.getY() - 1];
					if (temp != null && temp.getColor() != kingsColor && temp.getType() == PieceType.PAWN)
						return true;
				}
			}
		} else {
			if (kingsLocation.getY() + 1 < Board.SIZE) {
				if (kingsLocation.getX() > 0) {
					temp = state[kingsLocation.getX() - 1][kingsLocation.getY() + 1];
					if (temp != null && temp.getColor() != kingsColor && temp.getType() == PieceType.PAWN)
						return true;
				}
				if (kingsLocation.getX() < Board.SIZE - 1) {
					temp = state[kingsLocation.getX() + 1][kingsLocation.getY() + 1];
					if (temp != null && temp.getColor() != kingsColor && temp.getType() == PieceType.PAWN)
						return true;
				}
			}
		}
		return false;
	}
}
