package minigames;

import basicgraphics.Scene;
import basicgraphics.Sprite;
import java.awt.Color;
import basicgraphics.ClockWorker;

public class WaveGameProj extends Sprite{

    public WaveGameProj(Scene sc, Sprite sp) {
        super(sc);
        setPicture(Home.makeBall(Color.red, 5));
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());
    }

    public WaveGameProj(Scene sc, Sprite sp, int size) {
        super(sc);
        setPicture(Home.makeBall(Color.red, size));
        setCenterX(sp.centerX());
        setCenterY(sp.centerY());

    }
}