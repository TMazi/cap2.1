package com.capgemini.chess.algorithms.data.pieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;

/**
 * Chess piece definition
 * 
 * @author Michal Bejm
 *
 */

public interface Piece {
	
	List<Coordinate> getPossibleLocations(Coordinate location);
	Color getColor();
	PieceType getType();
}
/*public enum Piece {
	
	WHITE_KING(PieceType.KING, Color.WHITE),
    WHITE_QUEEN(PieceType.QUEEN, Color.WHITE),
    WHITE_BISHOP(PieceType.BISHOP, Color.WHITE),
    WHITE_KNIGHT(PieceType.KNIGHT, Color.WHITE),
    WHITE_ROOK(PieceType.ROOK, Color.WHITE),
    WHITE_PAWN(PieceType.PAWN, Color.WHITE),
    BLACK_KING(PieceType.KING, Color.BLACK),
    BLACK_QUEEN(PieceType.QUEEN, Color.BLACK),
    BLACK_BISHOP(PieceType.BISHOP, Color.BLACK),
    BLACK_KNIGHT(PieceType.KNIGHT, Color.BLACK),
    BLACK_ROOK(PieceType.ROOK, Color.BLACK),
    BLACK_PAWN(PieceType.PAWN, Color.BLACK);

    private final PieceType type;
    private final Color color;

    Piece(PieceType type, Color color) {
        this.type = type;
        this.color = color;
    }

	public PieceType getType() {
		return type;
	}

	public Color getColor() {
		return color;
	}
}*/