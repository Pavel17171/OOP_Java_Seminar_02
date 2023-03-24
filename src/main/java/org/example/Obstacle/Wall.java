package org.example.Obstacle;

import org.example.Base.BaseObstacle;

public class Wall extends BaseObstacle {

    public Wall(int height) {
        super("wall", 0, height);
    }
}