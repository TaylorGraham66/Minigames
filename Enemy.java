package wavegame;

import basicgraphics.CollisionEventType;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;
import basicgraphics.sounds.ReusableClip;

import java.awt.*;

public class Enemy extends Sprite {
    static int enemyCount;
    public Picture enemy_pic;

    public Enemy(Scene sc) {
        super(sc);
        enemy_pic = new Picture("tiefighter.png");
        while (true) {
            setX(GameManager.rand.nextInt(GameManager.BOARD_SIZE.width)-GameManager.proj_size);
            setY(GameManager.rand.nextInt(GameManager.BOARD_SIZE.height)-GameManager.proj_size);
            if (Math.abs(getX() - GameManager.BOARD_SIZE.width / 2) < 2 * GameManager.proj_size
                    && Math.abs(getY() - GameManager.BOARD_SIZE.height / 2) < 2 * GameManager.proj_size) {
                // Overlaps with center, try again
            } else {
                break;
            }
        }
        // A random speed
        setVel(2 * GameManager.rand.nextDouble() - 1, 2 * GameManager.rand.nextDouble());
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if(se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
            }
            if (se.xhi) {
                setX(0);
            }
            if (se.ylo) {
                setY(sc.getSize().height - getHeight());
            }
            if (se.yhi) {
                setY(0);
            }
        }
    }
}
