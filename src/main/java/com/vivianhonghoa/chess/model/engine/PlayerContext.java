package com.vivianhonghoa.chess.model.engine;

import com.vivianhonghoa.chess.model.players.Player;

import java.util.concurrent.atomic.AtomicInteger;

public record PlayerContext(Player player, AtomicInteger remainingTime, History history){
}
