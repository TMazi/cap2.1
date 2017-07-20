import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.pieces.*;
import com.capgemini.chess.algorithms.implementation.BoardManager;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;

public class MAIN {

	public static void main(String[] args) {
		Piece pawn = new Pawn(Color.WHITE);
		Board board = new Board();
		BoardManager bm = new BoardManager(board);
		board.setPieceAt(pawn, new Coordinate(0,3));
		try {
			bm.performMove(new Coordinate(0,3), new Coordinate(0,4));
			System.out.println(board.getMoveHistory().get(0).getMovedPiece().toString());
		} catch (InvalidMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

}
