package com.vivianhonghoa.chess.players;

import com.vivianhonghoa.chess.model.Case;
import com.vivianhonghoa.chess.model.pieces.Piece;
import com.vivianhonghoa.chess.model.players.Player;

public class ConsolePlayer extends Player {

    public ConsolePlayer(Piece.Color color) {
        super(color);
    }

    @Override
    public void onTurnStart() {

    }

    /**
     * Executes a command entered by the user.
     *
     * A command is a string with two parts separated by a space
     * each part is a position in the format "a1", "b2", etc.
     * Example : to make a move from a1 to b2, the command would be "a1 b2"
     * is a move is not valid (no piece, wrong color, etc.) a CommandException is thrown with an appropriate message
     *
     * There are special commands like : back, resign
     */
    public void executeCommand(String command) throws CommandException {
        command = command.trim().toLowerCase();

        if(command.equals("back")){
            if(this.isTurn()) {
                throw new CommandException("Can't go back on your turn.");
            }
            gameEngine.undo(getNumber());
            return;
        }

        if(command.equals("resign")){
            if(!this.isTurn()) {
                throw new CommandException("Can't resign when it's not your turn.");
            }
            gameEngine.resign(getNumber());
            return;
        }

        if (getNumber() != gameEngine.getCurrentPlayerNumber()) {
            throw new CommandException("It's not your turn !");
        }

        String[] parts = command.trim().split(" ");
        if (parts.length != 2 || parts[0].length() != 2 || parts[1].length() != 2) {
            throw new CommandException("Invalid command format. Expected format: 'a1 b2'");
        }
        String from = parts[0];
        String to = parts[1];
        try {
            int fromRow = Integer.parseInt(String.valueOf(from.charAt(1))) - 1;
            int fromCol = from.charAt(0) - 'a';
            int toRow = Integer.parseInt(String.valueOf(to.charAt(1))) - 1;
            int toCol = to.charAt(0) - 'a';
            if(fromRow < 0 || fromRow > 7 || fromCol < 0 || fromCol > 7 || toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
                throw new CommandException("Row must be between 1 and 8, column must be between a and h.");
            }
            if(!gameEngine.makeMove(new Case(fromRow, fromCol), new Case(toRow, toCol))){
                throw new CommandException("Invalid move. Please try again.");
            }
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }

    }

    public static class CommandException extends Exception {
        public CommandException(String message) {
            super(message);
        }
    }

}
