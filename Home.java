package minigames;

import basicgraphics.*;
import basicgraphics.images.BackgroundPainter;
import basicgraphics.images.Picture;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import basicgraphics.SpriteSpriteCollisionListener;
import basicgraphics.sounds.ReusableClip;


public class Home {

    final public static Random rand = new Random();
    final public static Dimension SCREEN_SIZE = new Dimension(800, 400);

    static BasicFrame bf = new BasicFrame("Minigames");

    //Main Menu
    static Card home = bf.getCard();

    //Menu Labels Layout
    static String[][] home_layout = {
            {"name", "name", "name"},
            {"game1", "game2", "game3"},
            {"game4", "game5", "game6"},
            {"exit", "exit", "exit"}
    };
    /*
   wg1 = menu for wg
   wg2 = gameplay
   wg3 = inbetween waves
    */
    static Card wg1 = bf.getCard();
    static Card wg2 = bf.getCard();
    static Card wg3 = bf.getCard();

    //Wave Game Specfic Variables
    public static int wg_enemies = 5;
    public static int wg_killCount = 0;
    public static int wg_rounds = 1;
    static JLabel wg_waveLabel = new JLabel("Wave: 1");

    public static void wg_spawn(SpriteComponent sc) {
        for (int i = 0; i < wg_enemies; i++) {
            new WaveGameEnemy(sc.getScene());
        }
    }

    static Picture makeBall(Color color,int size) {
        Image im = BasicFrame.createImage(size, size);
        Graphics g = im.getGraphics();
        g.setColor(color);
        g.fillOval(0, 0, size, size);
        return new Picture(im);
    }

    /*
    sr1 - menu for sr
    sr2 - gameplay
    sr3 - paused
     */
    static Card wr1 = bf.getCard();
    static Card wr2 = bf.getCard();
    static Card wr3 = bf.getCard();

    //Space Racer Variables

    public static void main(String[] args) {

        final ReusableClip death = new ReusableClip("die.wav");
        //Initializing Sprite Component and setting
        final SpriteComponent sc1 = new SpriteComponent();
        sc1.setPreferredSize(SCREEN_SIZE);
        sc1.getScene().setPainter(new BackgroundPainter(new Picture("dungeon.png")));
        //Background for home screen
        home.setPainter(new BackgroundPainter(new Picture("home.jpg")));

        //Main Screen Buttons
        final JButton button1 = new JButton("Wave Game");
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wg1.showCard();
                wg1.requestFocus();
            }
        });
        final JButton button2 = new JButton("Wave Rider");
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wr1.showCard();
                wr1.requestFocus();
            }
        });

        home.setStringLayout(home_layout);
        //How to make it look better?
        home.add("name", new JLabel("Minigame"));
        home.add("game1", button1);
        home.add("game2", button2);
        home.add("game3", new JButton("Button 3"));
        home.add("game4", new JButton("Button 4"));
        home.add("game5", new JButton("Button 5"));
        home.add("game6", new JButton("Button 6"));
        home.add("exit", new JButton("Exit"));

        //
        //Wave Game Code
        //
        wg1.setPainter(new BackgroundPainter(new Picture("dungeon.png")));
        //This does not work???
        //wg2.setPainter(new BackgroundPainter(new Picture("dungeon.png")));
        wg3.setPainter(new BackgroundPainter(new Picture("dungeon.png")));

        //Menu Button
        String[][] wg_labels ={
                {"Title"},
                {"Button"},
                {"Return"}
        };
        wg1.setStringLayout(wg_labels);
        //Words on buttons
        JLabel wg_title = new JLabel("Wave Game");
        wg_title.setForeground(Color.white);
        wg1.add(wg_title);
        JButton wg_jstart = new JButton("Start");
        wg_jstart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wg2.showCard();
                wg2.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        JButton wg_return = new JButton("Return");
        wg_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showCard();
                home.requestFocus();
            }
        });
        wg1.add("Button",wg_jstart);
        wg1.add("Return",wg_return);

        wg2.setLayout(new BasicLayout());
        int totalCells = 100;
        int labelHeight = 5;
        int labelYPos = 0;
        wg2.add("x=0,y=" + labelYPos + ",w=100,h=" + labelHeight, wg_waveLabel);
        wg2.add("x=0,y=" + (labelYPos + labelHeight) + ",w=100,h=" + (totalCells - labelHeight), sc1);

        //spawn player
        WaveGamePlayer wg_player = new WaveGamePlayer(sc1.getScene());
        sc1.getScene().setFocus(wg_player);
        final double player_speed = 1.65;
        wg2.addKeyListener(new KeyAdapter() {
           @Override
           public void keyPressed(KeyEvent ke) {
               if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                   wg_player.setVel(player_speed, 0);
               } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
                   wg_player.setVel(-player_speed, 0);
               } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                   wg_player.setVel(0, -player_speed);
               } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                   wg_player.setVel(0, player_speed);
               } else if(ke.getKeyCode() == ' ') {
                   final WaveGameProj bullet = new WaveGameProj(sc1.getScene(), wg_player);
                   bullet.setVel(wg_player.getVelX()*1.5, wg_player.getVelY()*1.5);
                   final int steps = 150, bloom = 20;
                   ClockWorker.addTask(new Task(steps) {
                       @Override
                       public void run() {
                           if(iteration() + bloom >= maxIteration()) {
                               Color c = Color.white;
                               if(iteration() + bloom/4 < maxIteration())
                                   c = Color.yellow;
                               bullet.setPicture(
                                       makeBall(c,5 + iteration()-steps+bloom));
                           }
                           if(iteration() == maxIteration()) {
                               bullet.destroy();
                           }
                       }
                   });
               }else if(ke.getKeyCode() == KeyEvent.VK_ESCAPE) {//Find a way to pause the game
                   wg1.showCard();
                   wg1.requestFocus();
               }else if(ke.getKeyCode() == KeyEvent.VK_ENTER) {
                   ClockWorker.finish();
                   wg3.showCard();
                   wg3.requestFocus();
               }
           }
       });

        String[][] wg_paused ={
                {"Resume"},
                {"Quit"}
        };
        wg3.setStringLayout(wg_paused);
        //Words on buttons
        JButton wg_resume = new JButton("Resume");
        wg3.add(wg_resume);
        wg_resume.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wg2.showCard();
                wg2.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        JButton wg_quit = new JButton("Quit");
        wg_quit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showCard();
                home.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        wg3.add("Resume",wg_resume);
        wg3.add("Quit",wg_quit);

        //Spawn Enemies
        wg_spawn(sc1);

        //Collisions
        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGameEnemy.class,new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGameEnemy>() {
            @Override
            public void collision(WaveGameEnemy sp1, WaveGameEnemy sp2) {
                double vx = sp1.getVelX();
                double vy = sp1.getVelY();
                sp1.setVel(sp2.getVelX(), sp2.getVelY());
                sp2.setVel(vx, vy);
            }
        });

        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGameProj.class, new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGameProj>() {
            public void collision(WaveGameEnemy sp1, WaveGameProj sp2) {
                sp1.destroy();
                sp2.destroy();
                wg_killCount++;

                if (wg_killCount >= wg_enemies) {
                    wg_rounds++;
                    wg_enemies = (int) Math.ceil(wg_enemies * 1.5);
                    wg_killCount = 0;
                    wg_waveLabel.setText("Wave: " + wg_rounds);
                    wg_spawn(sc1);
                }
            }
        });

        sc1.addSpriteSpriteCollisionListener(WaveGameEnemy.class, WaveGamePlayer.class, new SpriteSpriteCollisionListener<WaveGameEnemy, WaveGamePlayer>() {
            public void collision(WaveGameEnemy sp1, WaveGamePlayer sp2) {
                //JOptionPane.showMessageDialog("Button 1 was pressed!");
                death.playOverlapping();
                sp2.destroy();
                wg1.showCard();
                wg1.requestFocus();
            }
        });
        ClockWorker.addTask(sc1.moveSprites());
        //END OF WAVE GAME
        //
        //START OF SPACE RACER
        final SpriteComponent sc2 = new SpriteComponent();
        sc2.setPreferredSize(SCREEN_SIZE);
        sc2.getScene().setPainter(new BackgroundPainter(new Picture("racer.png")));
        sc2.getScene().setBackgroundSize(new Dimension(2000, 900));
        sc2.getScene().periodic_x = true;

        wr1.add(sc2);
        //Menu Button
        String[][] sr_labels ={
                {"WRTitle"},
                {"WRButton"},
                {"WRReturn"}
        };
        wr1.setStringLayout(sr_labels);
        //Words on buttons
        JLabel sr_title = new JLabel("Wave Rider");
        wr1.add(sr_title);
        JButton sr_start = new JButton("Start");
        sr_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wr2.showCard();
                wr2.requestFocus();
                ClockWorker.initialize(7);
            }
        });
        JButton sr_return = new JButton("Return");
        sr_return.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                home.showCard();
                home.requestFocus();
            }
        });
        wr1.add("WRButton",sr_start);
        wr1.add("WRReturn",sr_return);
        wr1.setPainter(new BackgroundPainter(new Picture("racer.png")));
        wr3.setPainter(new BackgroundPainter(new Picture("racer.png")));

        wr2.setLayout(new BasicLayout());
        wr2.add("x=0,y=0,w=1,h=1",sc2);

        WaterRacerPlayer wr_player = new WaterRacerPlayer(sc2.getScene());
        sc2.getScene().setFocus(wr_player);
        final double wr_speed = 2;
        wr2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent ke) {
                if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
                    wr_player.setVel(wr_speed, 0);
                } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
                    wr_player.setVel(wr_speed, -wr_speed);
                } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
                    wr_player.setVel(wr_speed, wr_speed);
                }
            }
        });
        ClockWorker.addTask(sc2.moveSprites());

        //End
        bf.show();
    }
}