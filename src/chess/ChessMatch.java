package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				mat[i][j] = (ChessPiece)board.piece(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		ValidateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}
	}
	
	private void ValidateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}
	
	private void placeNewPiece(ChessPiece piece, char column, int row) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	private void initialSetup() {
		placeNewPiece(new Rook(board, Color.WHITE), 'c', 1);
        placeNewPiece(new Rook(board, Color.WHITE), 'c', 2);
        placeNewPiece(new Rook(board, Color.WHITE), 'd', 2);
        placeNewPiece(new Rook(board, Color.WHITE), 'e', 2);
        placeNewPiece(new Rook(board, Color.WHITE), 'e', 1);
        placeNewPiece(new King(board, Color.WHITE), 'd', 1);

        placeNewPiece(new Rook(board, Color.BLACK), 'c', 7);
        placeNewPiece(new Rook(board, Color.BLACK), 'c', 8);
        placeNewPiece(new Rook(board, Color.BLACK), 'd', 7);
        placeNewPiece(new Rook(board, Color.BLACK), 'e', 7);
        placeNewPiece(new Rook(board, Color.BLACK), 'e', 8);
        placeNewPiece(new King(board, Color.BLACK), 'd', 8);
	}
}
