package com.capgemini.chess.algorithms.data.pieces;

import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

/**
 * Interface for the chess game figures
 * @author TMAZUREK
 *
 */

public interface Piece {
	
	Move validateMove(Piece[][] state, Coordinate from, Coordinate to) throws InvalidMoveException;
	
	public List<Coordinate> getPossibleLocations(Coordinate currentLocation);

	Color getColor();

	PieceType getType();

	void setMoved();
	
	boolean wasMoved();
}
