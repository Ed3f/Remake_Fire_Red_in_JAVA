package com.PokemonGame.game;

import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;

public interface ICharacter {
    ArrayList<String> getText(Starters starter);
    Batch draw(Batch batch, String location);
}
