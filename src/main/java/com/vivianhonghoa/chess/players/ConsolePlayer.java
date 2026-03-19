package com.vivianhonghoa.chess.players;

import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.Player;

public class ConsolePlayer extends Player {

    public ConsolePlayer(Piece.Color color) {
        super(color);
    }

    @Override
    public void onTurnStart() {

    }

}
