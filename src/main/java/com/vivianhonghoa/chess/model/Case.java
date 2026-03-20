package com.vivianhonghoa.chess.model;

import com.vivianhonghoa.chess.model.engine.Board;

public record Case(int row,
                   int col
){

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
