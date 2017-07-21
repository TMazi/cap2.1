package com.capgemini.chess.algorithms.data.pieces;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;

/**
 * Class used to get the type of potential move
 * 
 * @author TMAZUREK
 *
 */

public class MoveTypeChecker {

	/**
	 * Static method to get the type of a move
	 * 
	 * @param color
	 *            actual color of moving piece
	 * @param state
	 *            actual state of board
	 * @param to
	 *            destination of move
	 * @return the type of Move
	 */
	public static MoveType getMoveType(Color color, Piece[][] state, Coordinate to) {
		if (state[to.getX()][to.getY()] != null) {
			if (state[to.getX()][to.getY()].getColor() == color)
				return null;
			else
				return MoveType.CAPTURE;
		} else
			return MoveType.ATTACK;
	}

}
