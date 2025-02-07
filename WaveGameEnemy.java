package minigames;

import basicgraphics.CollisionEventType;
import basicgraphics.Scene;
import basicgraphics.Sprite;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.SpriteComponent;
import basicgraphics.images.Picture;

public class WaveGameEnemy extends Sprite{

    static int enemyCount;
    public Picture enemy_pic;

    public WaveGameEnemy(Scene sc) {
        super(sc);
        enemyCount++;
        enemy_pic = new Picture("squid_enemy.png");
        setPicture(enemy_pic);
        while (true) {
            setX(Home.rand.nextInt(Home.SCREEN_SIZE.width)-5);
            setY(Home.rand.nextInt(Home.SCREEN_SIZE.height)-5);
            if (Math.abs(getX() - Home.SCREEN_SIZE.width / 1.5) < 3 * 10
                    && Math.abs(getY() - Home.SCREEN_SIZE.height / 1.5) < 3 * 10) {
                // Overlaps with center, try again
            } else {
                break;
            }
        }
        setVel(1.5 * Home.rand.nextDouble() - 1, 1.5 * Home.rand.nextDouble());
    }

    @Override
    public void processEvent(SpriteCollisionEvent se) {
        SpriteComponent sc = getSpriteComponent();
        if(se.eventType == CollisionEventType.WALL_INVISIBLE) {
            if (se.xlo) {
                setX(sc.getSize().width - getWidth());
            }if (se.xhi) {
                setX(0);
            }if (se.ylo) {
                setY(sc.getSize().height - getHeight());
            }if (se.yhi) {
                setY(0);
            }
        }
    }
}