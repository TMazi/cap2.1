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
