package minigames;

import basicgraphics.Scene;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import basicgraphics.Sprite;

public class WaterRacerPlayer extends Sprite{

    public Picture initialPic;
    final double player_speed = 2;

    public WaterRacerPlayer(Scene sc) {
        super(sc);
        initialPic = new Picture("mfalcon.png");
        setPicture(initialPic);
        setX(550);
        setY(Home.SCREEN_SIZE.height / .8);
        setVel(player_speed, 0);
    }

    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc2 = getSpriteComponent();
        if (se.ylo) {
            //setY(sc2.getHeight());
        }if (se.yhi) {
            setY(0);
        }
    }
}
