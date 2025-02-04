package wavegame;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import java.util.Random;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class GameManager {

    //Final Variables
    final public static Random rand = new Random();
    final public static Color proj_color = Color.GREEN;
    final public static Color explosion_color = Color.orange;
    public static int enemies = 5;
    final public static Dimension BOARD_SIZE = new Dimension(800,400);

    static BasicFrame bf = new BasicFrame("Rounds");

    //Menu for game
    static Card bc1 = bf.getCard();

    //Main Game state
    static Card bc2 = bf.getCard();

    //Inbetween rounds
    static Card bc3 = bf.getCard();


    public static void main(String[] args) {
        final SpriteComponent sc = new SpriteComponent();
        sc.setPreferredSize(BOARD_SIZE);
        //Main Menu
        bc1.setPainter(new BackgroundPainter(new Picture("dungeon.png")));

        //Main Scene
        sc.getScene().setPainter(new BackgroundPainter(new Picture("dungeon.png")));

        //Menu Button
        String[][] menu_labels ={
                {"Title"},
                {"Button"}
        };
        bc1.setStringLayout(menu_labels);
        //Words on buttons
        JLabel title = new JLabel("Wave Game");
        title.setForeground(Color.white);
        bc1.add(title);
        JButton jstart = new JButton("Start");
        jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bc2.showCard();
                bc2.requestFocus();
                ClockWorker.initialize(7);
            }
        });

        bc1.add("Button",jstart);
        String[][] layout = {{
                "Wave"
        }};
        bc2.setLayout(new BasicLayout());
        bc2.add("x=0,y=0,w=1,h=1", sc);
        bf.show();

        //spawn player
        final Player player = new Player(sc.getScene());
        sc.getScene().setFocus(player);
        bc2.addKeyListener(player.ka);

        ClockWorker.addTask(sc.moveSprites());

    }
}