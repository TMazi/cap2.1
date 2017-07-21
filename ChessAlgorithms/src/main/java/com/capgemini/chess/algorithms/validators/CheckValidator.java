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

	public static boolean isInCheck(Color kingsColor, Piece[][] state) {
		
		Coordinate kingsLocation = null;
		boolean continues = true;
		int i = 0;
		while (continues && i < Board.SIZE) {
			int j = 0;
			while (continues && j < Board.SIZE) {
				if (state[i][j] != null && state[i][j].getType() == PieceType.KING)
					if (state[i][j].getColor() == kingsColor) {
						kingsLocation = new Coordinate(i, j);
						continues = false;
					}
				j++;
			}
			i++;
		}
		if(kingsLocation == null)
			return false;
		
		if (isAttackedByPawn(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedByKnight(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedByRookOrQueen(kingsLocation, kingsColor, state))
			return true;
		if (isAttackedByBishopOrQueen(kingsLocation, kingsColor, state))
			return true;

		return false;
	}

	private static boolean isAttackedByRookOrQueen(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		return

		isAttackedFromLeft(kingsLocation, kingsColor, state) ||

		isAttackedFromRight(kingsLocation, kingsColor, state) ||

		isAttackedFromTop(kingsLocation, kingsColor, state) ||

		isAttackedFromBottom(kingsLocation, kingsColor, state);

	}

	private static boolean isAttackedFromLeft(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
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
		return false;
	}

	private static boolean isAttackedFromRight(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
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

	private static boolean isAttackedFromTop(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
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
		return false;
	}

	private static boolean isAttackedFromBottom(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
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

	private static boolean isAttackedByBishopOrQueen(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		return isAttackedFromUpperLeft(kingsLocation, kingsColor, state)
				|| isAttackedFromLowerRight(kingsLocation, kingsColor, state)
				|| isAttackedFromLowerLeft(kingsLocation, kingsColor, state)
				|| isAttackedFromUpperRight(kingsLocation, kingsColor, state);
	}

	private static boolean isAttackedFromUpperLeft(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
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
		return false;
	}

	private static boolean isAttackedFromLowerRight(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
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
		return false;
	}

	private static boolean isAttackedFromLowerLeft(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
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
		return false;
	}

	private static boolean isAttackedFromUpperRight(Coordinate kingsLocation, Color kingsColor, Piece[][] state) {
		Piece temp;
		boolean continues = true;
		int i = 1;
		while (continues && kingsLocation.getX() + i < Board.SIZE && kingsLocation.getY() - i >= 0)

		{
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

		List<Coordinate> possibleCoordinates = new LinkedList<>();
		int x = kingsLocation.getX();
		int y = kingsLocation.getY();

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

		Piece temp;

		for (int i = 0; i < possibleCoordinates.size(); i++) {
			temp = state[possibleCoordinates.get(i).getX()][possibleCoordinates.get(i).getY()];
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
		if (kingsColor == Color.BLACK) {
			return isBlackAttackedByPawn(kingsLocation, state);
		} else {
			return isWhiteAttackedByPawn(kingsLocation, state);
		}

	}

	private static boolean isBlackAttackedByPawn(Coordinate kingsLocation, Piece[][] state) {
		Piece temp;
		if (kingsLocation.getY() - 1 >= 0) {
			if (kingsLocation.getX() > 0) {
				temp = state[kingsLocation.getX() - 1][kingsLocation.getY() - 1];
				if (temp != null && temp.getColor() != Color.BLACK && temp.getType() == PieceType.PAWN)
					return true;
			}
			if (kingsLocation.getX() < Board.SIZE - 1) {
				temp = state[kingsLocation.getX() + 1][kingsLocation.getY() - 1];
				if (temp != null && temp.getColor() != Color.BLACK && temp.getType() == PieceType.PAWN)
					return true;
			}
		}
		return false;
	}

	private static boolean isWhiteAttackedByPawn(Coordinate kingsLocation, Piece[][] state) {
		Piece temp;
		if (kingsLocation.getY() + 1 < Board.SIZE) {
			if (kingsLocation.getX() > 0) {
				temp = state[kingsLocation.getX() - 1][kingsLocation.getY() + 1];
				if (temp != null && temp.getColor() != Color.WHITE && temp.getType() == PieceType.PAWN)
					return true;
			}
			if (kingsLocation.getX() < Board.SIZE - 1) {
				temp = state[kingsLocation.getX() + 1][kingsLocation.getY() + 1];
				if (temp != null && temp.getColor() != Color.WHITE && temp.getType() == PieceType.PAWN)
					return true;
			}
		}
		return false;
	}

}
