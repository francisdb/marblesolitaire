package eu.somatik.glossitope.marblesolitare;

import java.applet.Applet;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;


import javax.swing.JPanel;


public class SolitairePanel extends JPanel{
    
    private Speelveld speelVeld;
    
    private boolean bezig;
    private int score;
    private AudioClip sounds[];
    
    public SolitairePanel(){
        speelVeld = new Speelveld();
        initGUI();
        LoadStuff();
        bezig =false;
        score=0;
    }
    
    private void LoadStuff(){
        try {
            sounds = new java.applet.AudioClip[3];
            System.out.println(getClass().getClassLoader().getResource("Vwoom-Public_D-218.wav"));
            sounds[0] = Applet.newAudioClip(getClass().getClassLoader().getResource("Vwoom-Public_D-218.wav"));
            sounds[1] = Applet.newAudioClip(getClass().getClassLoader().getResource("Blunk-Public_D-339.wav"));
            sounds[2] = Applet.newAudioClip(getClass().getClassLoader().getResource("poise-xrikazen-77.wav"));
            java.awt.Image[] images = new Image[3];
            images[0] = javax.imageio.ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("leeg.gif"));
            images[1] = javax.imageio.ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("hole.gif"));
            images[2] = javax.imageio.ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("ball.gif"));
            speelVeld.setStuff(sounds, images);
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void initGUI(){
        addKeyListener(
                new KeyAdapter(){
            public void keyReleased(KeyEvent e){
                if (bezig){
                    doKey(e);
                }
            }
        });
        addMouseListener(
                new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if (bezig){
                    doMouseClick(e);
                }
            }
        });
        addMouseMotionListener(
                new MouseMotionAdapter(){
            public void mouseMoved(MouseEvent e){
                if (bezig){
                    doMouseMove(e);
                }
            }
        });
        this.setPreferredSize(new Dimension(400,400));
    }
    
    public void doKey(KeyEvent e){
        //check gewonnen
        doMove(e);
        System.out.println("Balls left: " + speelVeld.checkBallsLeft());
        if(speelVeld.checkGameOver())
            gameOver();
    }
    
    public void doMouseMove(MouseEvent e){
        speelVeld.doMouseMove(e);
        repaint();
    }
    
    public void doMouseClick(MouseEvent e){
        System.out.println("click");
        speelVeld.doMouseClick(e);
        repaint();
        System.out.println("Balls left: " + speelVeld.checkBallsLeft());
        if(speelVeld.checkGameOver())
            gameOver();
        
    }
    
    public void gameOver(){
        score= speelVeld.checkBallsLeft();
        System.out.println("GAME OVER -> " + score + " balls left!");
        bezig=false;
    }
    
    
    public void newGame(){
        speelVeld.reset();
        repaint();
        System.out.println("New game started...");
        bezig=true;
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        speelVeld.drawVeld((Graphics2D) g);
    }
    
    
    public void doMove(KeyEvent e){
        speelVeld.doMove(e);
        repaint();
    }
    
}



