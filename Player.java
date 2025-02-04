package wavegame;

import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static wavegame.GameManager.bc2;

public class Player extends Sprite {

    public Picture player_pic;

    public Player(Scene sc) {
        super(sc);
        player_pic = new Picture("mfalcon.png");
        setPicture(player_pic);
        //Puts player in the middle of the screen
        Dimension d = sc.getSize();
        setX(d.width/2);
        setY(d.height/2);
    }

    //insert player movement & collision
    final double player_speed = 2;
    KeyAdapter ka = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent ke) {
            //Normal Directions
            if(ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                setForwardDirection(player_speed, 0);
                setVel(player_speed, 0);
            }else if(ke.getKeyCode() == KeyEvent.VK_LEFT) {
                setForwardDirection(-player_speed, 0);
                setVel(-player_speed, 0);
            }else if(ke.getKeyCode() == KeyEvent.VK_UP) {
                setForwardDirection(0,-player_speed);
                setVel(0, -player_speed);
            }else if(ke.getKeyCode() == KeyEvent.VK_DOWN) {
                setForwardDirection(0,player_speed);
                setVel(0, player_speed);
            }
        }
    };

    //Collision with walls
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if (se.xlo) {
            setX(sc.getFullSize().width-getWidth());
        }
        if (se.xhi) {
            setX(0);
        }
        if (se.ylo) {
            setY(sc.getFullSize().height-getHeight());
        }
        if (se.yhi) {
            setY(0);
        }
    }
}
