package com.vivianhonghoa.chess.model.players;

import com.vivianhonghoa.chess.model.engine.History;

import java.util.concurrent.atomic.AtomicInteger;

public record PlayerContext(String name, Player player, AtomicInteger remainingTime, History history){
}
