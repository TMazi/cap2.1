package com.capgemini.chess.algorithms.validators;

import static org.junit.Assert.*;

import org.junit.Test;

import com.capgemini.chess.algorithms.data.Coordinate;
import com.capgemini.chess.algorithms.data.enums.Color;
import com.capgemini.chess.algorithms.data.generated.Board;
import com.capgemini.chess.algorithms.data.pieces.*;

public class CheckValidatorTest {

	@Test
	public void shouldBeCheckAttackedFromLeft() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 0));

		// when
		boolean result = CheckValidator.isInCheck(Color.WHITE, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckAttackedFromRight() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(7, 0));

		// when
		boolean result = CheckValidator.isInCheck(Color.WHITE, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromBottom() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(4, 5));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromTop() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 5));
		board.setPieceAt(new Queen(Color.WHITE), new Coordinate(4, 3));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromUpperLeft() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(2, 5));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromUpperRight() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(6, 5));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromDownLeft() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(2, 2));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedFromDownRight() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(6, 2));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedByKnight() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));
		board.setPieceAt(new Knight(Color.WHITE), new Coordinate(2, 1));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldBeCheckedAttackedByPawn() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Pawn(Color.WHITE), new Coordinate(3, 6));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

	@Test
	public void shouldNotBeCheckedFromSideOtherPiece() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.WHITE), new Coordinate(4, 0));
		board.setPieceAt(new Rook(Color.BLACK), new Coordinate(1, 0));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(3, 0));

		// when
		boolean result = CheckValidator.isInCheck(Color.WHITE, board.getPieces());

		// then
		assertFalse(result);
	}

	@Test
	public void shouldNotBeCheckedFromAcrossOtherPiece() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(2, 5));
		board.setPieceAt(new Pawn(Color.BLACK), new Coordinate(3, 6));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertFalse(result);
	}

	@Test
	public void shouldBlackKingBeCheckedFromPawn() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 7));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(3, 6));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);

	}

	@Test
	public void shouldWhiteKingBeCheckedFromPawn() {
		// given
		Board board = new Board();
		board.setPieceAt(new King(Color.BLACK), new Coordinate(4, 0));
		board.setPieceAt(new Bishop(Color.WHITE), new Coordinate(3, 1));

		// when
		boolean result = CheckValidator.isInCheck(Color.BLACK, board.getPieces());

		// then
		assertTrue(result);
	}

}
