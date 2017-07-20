package com.capgemini.chess.algorithms.implementation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.Move;
import com.capgemini.chess.algorithms.data.enums.BoardState;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.enums.MoveType;
import com.capgemini.chess.algorithms.data.enums.PieceType;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.pieces.*;
import com.capgemini.chess.algorithms.implementation.exceptions.InvalidMoveException;
import com.capgemini.chess.algorithms.implementation.exceptions.KingInCheckException;
import com.capgemini.chess.algorithms.validators.CheckValidator;
import com.capgemini.chess.algorithms.validators.MoveValidator;

/**
 * Class for managing of basic operations on the Chess Board.
 *
 * @author Michal Bejm
 *
 */
public class BoardManager {

	private Board board = new Board();

	public BoardManager() {
		initBoard();
	}

	public BoardManager(List<Move> moves) {
		initBoard();
		for (Move move : moves) {
			addMove(move);
		}
	}

	public BoardManager(Board board) {
		this.board = board;
	}

	/**
	 * Getter for generated board
	 *
	 * @return board
	 */
	public Board getBoard() {
		return this.board;
	}

	/**
	 * Performs move of the chess piece on the chess board from one field to
	 * another.
	 *
	 * @param from
	 *            coordinates of 'from' field
	 * @param to
	 *            coordinates of 'to' field
	 * @return move object which includes moved piece and move type
	 * @throws InvalidMoveException
	 *             in case move is not valid
	 */
	public Move performMove(Coordinate from, Coordinate to) throws InvalidMoveException {

		Move move = validateMove(from, to);

		addMove(move);

		return move;
	}

	/**
	 * Calculates state of the chess board.
	 *
	 * @return state of the chess board
	 */
	public BoardState updateBoardState() {

		Color nextMoveColor = calculateNextMoveColor();

		boolean isKingInCheck = isKingInCheck(nextMoveColor);
		boolean isAnyMoveValid = isAnyMoveValid(nextMoveColor);

		BoardState boardState;
		if (isKingInCheck) {
			if (isAnyMoveValid) {
				boardState = BoardState.CHECK;
			} else {
				boardState = BoardState.CHECK_MATE;
			}
		} else {
			if (isAnyMoveValid) {
				boardState = BoardState.REGULAR;
			} else {
				boardState = BoardState.STALE_MATE;
			}
		}
		this.board.setState(boardState);
		return boardState;
	}

	/**
	 * Checks threefold repetition rule (one of the conditions to end the chess
	 * game with a draw).
	 *
	 * @return true if current state repeated at list two times, false otherwise
	 */
	public boolean checkThreefoldRepetitionRule() {

		// there is no need to check moves that where before last capture/en
		// passant/castling
		int lastNonAttackMoveIndex = findLastNonAttackMoveIndex();
		List<Move> omittedMoves = this.board.getMoveHistory().subList(0, lastNonAttackMoveIndex);
		BoardManager simulatedBoardManager = new BoardManager(omittedMoves);

		int counter = 0;
		for (int i = lastNonAttackMoveIndex; i < this.board.getMoveHistory().size(); i++) {
			Move moveToAdd = this.board.getMoveHistory().get(i);
			simulatedBoardManager.addMove(moveToAdd);
			boolean areBoardsEqual = Arrays.deepEquals(this.board.getPieces(),
					simulatedBoardManager.getBoard().getPieces());
			if (areBoardsEqual) {
				counter++;
			}
		}

		return counter >= 2;
	}

	/**
	 * Checks 50-move rule (one of the conditions to end the chess game with a
	 * draw).
	 *
	 * @return true if no pawn was moved or not capture was performed during
	 *         last 50 moves, false otherwise
	 */
	public boolean checkFiftyMoveRule() {

		// for this purpose a "move" consists of a player completing his turn
		// followed by his opponent completing his turn
		if (this.board.getMoveHistory().size() < 100) {
			return false;
		}

		for (int i = this.board.getMoveHistory().size() - 1; i >= this.board.getMoveHistory().size() - 100; i--) {
			Move currentMove = this.board.getMoveHistory().get(i);
			PieceType currentPieceType = currentMove.getMovedPiece().getType();
			if (currentMove.getType() != MoveType.ATTACK || currentPieceType == PieceType.PAWN) {
				return false;
			}
		}

		return true;
	}

	// PRIVATE

	private void initBoard() {

		this.board.setPieceAt(new Rook(Color.BLACK), new Coordinate(0, 7));
		this.board.setPieceAt(new Knight(Color.BLACK), new Coordinate(1, 7));
		this.board.setPieceAt(new Bishop(Color.BLACK), new Coordinate(2, 7));
		this.board.setPieceAt(new Queen(Color.BLACK), new Coordinate(3, 7));
		this.board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		this.board.setPieceAt(new Bishop(Color.BLACK), new Coordinate(5, 7));
		this.board.setPieceAt(new Knight(Color.BLACK), new Coordinate(6, 7));
		this.board.setPieceAt(new Rook(Color.BLACK), new Coordinate(7, 7));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(x, 6));
		}

		this.board.setPieceAt(new Rook(Color.WHITE), new Coordinate(0, 0));
		this.board.setPieceAt(new Knight(Color.WHITE), new Coordinate(1, 0));
		this.board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(2, 0));
		this.board.setPieceAt(new Queen(Color.WHITE), new Coordinate(3, 0));
		this.board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		this.board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(5, 0));
		this.board.setPieceAt(new Knight(Color.WHITE), new Coordinate(6, 0));
		this.board.setPieceAt(new Rook(Color.WHITE), new Coordinate(7, 0));

		for (int x = 0; x < Board.SIZE; x++) {
			this.board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(x, 1));
		}
	}

	private void addMove(Move move) {

		addRegularMove(move);

		if (move.getType() == MoveType.CASTLING) {
			addCastling(move);
		} else if (move.getType() == MoveType.EN_PASSANT) {
			addEnPassant(move);
		}

		this.board.getMoveHistory().add(move);
	}

	private void addRegularMove(Move move) {
		Piece movedPiece = this.board.getPieceAt(move.getFrom());
		this.board.setPieceAt(null, move.getFrom());
		this.board.setPieceAt(movedPiece, move.getTo());

		performPromotion(move, movedPiece);
	}

	private void performPromotion(Move move, Piece movedPiece) {
		if (movedPiece.getType() == PieceType.PAWN && movedPiece.getColor() == Color.WHITE
				&& move.getTo().getY() == (Board.SIZE - 1)) {
			this.board.setPieceAt(new Queen(Color.WHITE), move.getTo());
		}
		if (movedPiece.getType() == PieceType.PAWN && movedPiece.getColor() == Color.BLACK
				&& move.getTo().getY() == 0) {
			this.board.setPieceAt(new Queen(Color.BLACK), move.getTo());
		}
	}

	private void addCastling(Move move) {
		if (move.getFrom().getX() > move.getTo().getX()) {
			Piece rook = this.board.getPieceAt(new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(0, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() + 1, move.getTo().getY()));
		} else {
			Piece rook = this.board.getPieceAt(new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(null, new Coordinate(Board.SIZE - 1, move.getFrom().getY()));
			this.board.setPieceAt(rook, new Coordinate(move.getTo().getX() - 1, move.getTo().getY()));
		}
	}

	private void addEnPassant(Move move) {
		Move lastMove = this.board.getMoveHistory().get(this.board.getMoveHistory().size() - 1);
		this.board.setPieceAt(null, lastMove.getTo());
	}

	private Move validateMove(Coordinate from, Coordinate to) throws InvalidMoveException, KingInCheckException {

		
		//TODO zmienic if(blabla) na funkcje rzucajace wyjatek
		Piece[][] state = board.getPieces();
		List<Coordinate> potentialCoordinates;
		if (from.getX() >= Board.SIZE || from.getX() < 0 || from.getY() >= Board.SIZE || from.getY() < 0
				|| state[from.getX()][from.getY()] == null
				|| state[from.getX()][from.getY()].getColor() != calculateNextMoveColor()) {
			throw new InvalidMoveException();
		}
		Piece piece = state[from.getX()][from.getY()];

		if (piece.getType() == PieceType.PAWN) {
			potentialCoordinates = isEnPassantPossibleOrNot(from);
		} else if (piece.getType() == PieceType.KING)
			potentialCoordinates = isCastlingPossibleOrNot(from);
		else
			potentialCoordinates = piece.getPossibleLocations(from);

		if (!potentialCoordinates.contains(to))
			throw new InvalidMoveException();

		Move result = MoveValidator.moveValidate(piece, state, from, to);
		if (result == null) {
			throw new InvalidMoveException();
		}

		if (willBeCheckedAfterMove(from, to)) {
			throw new KingInCheckException();
		}

		return result;
	}

	private boolean isKingInCheck(Color kingColor) {

		boolean continues = true;
		Coordinate kingsLocation = new Coordinate(0, 0);
		Piece[][] state = board.getPieces();
		int i = 0;
		while (continues && i < Board.SIZE) {
			int j = 0;
			while (continues && j < Board.SIZE) {
				if (state[i][j] != null && state[i][j].getType() == PieceType.KING)
					if (state[i][j].getColor() == kingColor) {
						kingsLocation = new Coordinate(i, j);
						continues = false;
					}
				j++;
			}
			i++;
		}
		return CheckValidator.isInCheck(kingsLocation, kingColor, state);
	}

	private boolean isAnyMoveValid(Color nextMoveColor) {

		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				Coordinate temp = new Coordinate(i, j);
				Piece piece = board.getPieceAt(temp);
				if (piece != null) {
					List<Coordinate> potentialCoordinates = new ArrayList<>();
					if (piece.getColor() == nextMoveColor) {
						if (piece.getType() == PieceType.PAWN) {
							potentialCoordinates = isEnPassantPossibleOrNot(temp);
						} else if (piece.getType() == PieceType.KING)
							potentialCoordinates = isCastlingPossibleOrNot(temp);
						else
							potentialCoordinates = piece.getPossibleLocations(temp);
					}
					for (int x = 0; x < potentialCoordinates.size(); x++) {
						try {
							validateMove(temp, potentialCoordinates.get(x));
							return true;
						} catch (KingInCheckException e) {

						} catch (InvalidMoveException e) {

						}
					}
				}
			}
		}
		return false;
	}

	private List<Coordinate> isEnPassantPossibleOrNot(Coordinate from) {

		Piece temp = board.getPieceAt(from);
		List<Coordinate> result = temp.getPossibleLocations(from);
		int yMove;
		if (temp.getColor() == Color.BLACK)
			yMove = from.getY() - 1;
		else
			yMove = from.getY() + 1;

		if (board.getMoveHistory().size() > 0) {
			Move lastMove = board.getMoveHistory().get(board.getMoveHistory().size() - 1);
			if (lastMove.getMovedPiece().getType() == PieceType.PAWN && lastMove.getTo().getY() == from.getY()
					&& Math.abs(lastMove.getTo().getY() - lastMove.getFrom().getY()) == 2) {
				if (lastMove.getTo().getX() == from.getX() + 1) {
					result.remove(new Coordinate(from.getX() - 1, yMove));
				} else if (lastMove.getTo().getX() == from.getX() - 1) {
					result.remove(new Coordinate(from.getX() + 1, yMove));
				}
			} else {
				result.remove(new Coordinate(from.getX() + 1, yMove));
				result.remove(new Coordinate(from.getX() - 1, yMove));
			}
		} else {
			result.remove(new Coordinate(from.getX() + 1, yMove));
			result.remove(new Coordinate(from.getX() - 1, yMove));
		}
		return result;
	}

	private List<Coordinate> isCastlingPossibleOrNot(Coordinate from) {
		Piece temp = board.getPieceAt(from);
		List<Coordinate> temporaryList = temp.getPossibleLocations(from);
		if (temp.getColor() == Color.BLACK)
			return isCastlingForBlackKing(from, temporaryList);
		else
			return isCastlingForWhiteKing(from, temporaryList);
	}

	private List<Coordinate> isCastlingForBlackKing(Coordinate from, List<Coordinate> temporaryList) {

		List<Coordinate> result = temporaryList;
		if (from.equals(new Coordinate(4, 7))) {
			if (board.getPieceAt(new Coordinate(5, 7)) != null || board.getPieceAt(new Coordinate(6, 7)) != null)
				result.remove(new Coordinate(6, 7));
			if (board.getPieceAt(new Coordinate(1, 7)) != null || board.getPieceAt(new Coordinate(2, 7)) != null
					|| board.getPieceAt(new Coordinate(3, 7)) != null)
				result.remove(new Coordinate(2, 7));
			List<Move> history = board.getMoveHistory();
			for (int i = 0; i < board.getMoveHistory().size(); i++) {
				Move move = history.get(i);
				Piece temp = move.getMovedPiece();
				if (temp.getType() == PieceType.KING && temp.getColor() == Color.BLACK) {
					result.remove(new Coordinate(2, 7));
					result.remove(new Coordinate(6, 7));
					return result;
				}
				if (temp.getType() == PieceType.ROOK && temp.getColor() == Color.BLACK) {
					if (move.getFrom().equals(new Coordinate(0, 7)))
						result.remove(new Coordinate(2, 7));
					if (move.getFrom().equals(new Coordinate(7, 7)))
						result.remove(new Coordinate(6, 7));
				}
			}
			if (willBeCheckedAfterMove(new Coordinate(4, 7), new Coordinate(5, 7)))
				result.remove(new Coordinate(6, 7));
			if (willBeCheckedAfterMove(new Coordinate(4, 7), new Coordinate(3, 7)))
				result.remove(new Coordinate(2, 7));

		} else {
			result.remove(new Coordinate(2, 7));
			result.remove(new Coordinate(6, 7));
		}
		return result;
	}

	private List<Coordinate> isCastlingForWhiteKing(Coordinate from, List<Coordinate> temporaryList) {

		List<Coordinate> result = temporaryList;
		if (from.equals(new Coordinate(4, 0))) {
			if (board.getPieceAt(new Coordinate(5, 0)) != null || board.getPieceAt(new Coordinate(6, 0)) != null)
				result.remove(new Coordinate(6, 0));
			if (board.getPieceAt(new Coordinate(1, 0)) != null || board.getPieceAt(new Coordinate(2, 0)) != null
					|| board.getPieceAt(new Coordinate(3, 0)) != null)
				result.remove(new Coordinate(2, 0));
			List<Move> history = board.getMoveHistory();
			for (int i = 0; i < board.getMoveHistory().size(); i++) {
				Move move = history.get(i);
				Piece temp = move.getMovedPiece();
				if (temp.getType() == PieceType.KING && temp.getColor() == Color.WHITE) {
					result.remove(new Coordinate(2, 0));
					result.remove(new Coordinate(6, 0));
					return result;
				}
				if (temp.getType() == PieceType.ROOK && temp.getColor() == Color.WHITE) {
					if (move.getFrom().equals(new Coordinate(0, 0)))
						result.remove(new Coordinate(2, 0));
					if (move.getFrom().equals(new Coordinate(7, 0)))
						result.remove(new Coordinate(6, 0));
				}
			}
			if (willBeCheckedAfterMove(new Coordinate(4, 0), new Coordinate(5, 0)))
				result.remove(new Coordinate(6, 0));
			if (willBeCheckedAfterMove(new Coordinate(4, 0), new Coordinate(3, 0)))
				result.remove(new Coordinate(2, 0));
		} else {
			result.remove(new Coordinate(2, 0));
			result.remove(new Coordinate(6, 0));
		}
		return result;
	}

	private boolean willBeCheckedAfterMove(Coordinate from, Coordinate to) {
		Piece piece = board.getPieces()[from.getX()][from.getY()];
		Board temporaryBoard = new Board();
		for (int i = 0; i < Board.SIZE; i++) {
			for (int j = 0; j < Board.SIZE; j++) {
				temporaryBoard.setPieceAt(board.getPieceAt(new Coordinate(i, j)), new Coordinate(i, j));
			}
		}
		temporaryBoard.setPieceAt(null, from);
		temporaryBoard.setPieceAt(piece, to);
		BoardManager temporaryManager = new BoardManager(temporaryBoard);
		if (temporaryManager.isKingInCheck(calculateNextMoveColor()))
			return true;
		return false;
	}

	private Color calculateNextMoveColor() {
		if (this.board.getMoveHistory().size() % 2 == 0) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	private int findLastNonAttackMoveIndex() {
		int counter = 0;
		int lastNonAttackMoveIndex = 0;

		for (Move move : this.board.getMoveHistory()) {
			if (move.getType() != MoveType.ATTACK) {
				lastNonAttackMoveIndex = counter;
			}
			counter++;
		}

		return lastNonAttackMoveIndex;
	}

}
