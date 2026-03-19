package com.vivianhonghoa.chess.model.engine;

public record Case(int row,
                   int col
){
    boolean isValid() {
        return Case.isValid(row, col);
    }

    public static boolean isValid(int row, int col) {
        return row >= 0 && row < Board.SIZE && col >= 0 && col < Board.SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Case aCase = (Case) o;
        return row == aCase.row && col == aCase.col;
    }

    @Override
    public String toString() {
        return "Position[" + row + "," + col + "]";
    }
}
