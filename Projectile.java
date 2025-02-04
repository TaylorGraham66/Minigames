package wavegame;

import basicgraphics.BasicFrame;
import basicgraphics.Scene;
import basicgraphics.SpriteCollisionEvent;
import basicgraphics.images.Picture;
import basicgraphics.Sprite;
import java.awt.*;

public class Projectile extends Sprite{

    //Projectile balls (stolen from basicShooter
    static Picture makeBall(Color color, int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }

    public Projectile(Scene sc) {
        super(sc);
        setPicture(makeBall(Color.red, 5));
    }

    //Collision
    @Override
    public void processEvent(SpriteCollisionEvent se) {
        //setActive(false);
    }
}
