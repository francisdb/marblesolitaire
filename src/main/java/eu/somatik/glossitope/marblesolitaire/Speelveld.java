package eu.somatik.glossitope.marblesolitaire;

import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.applet.AudioClip;
import java.awt.event.MouseEvent;
import java.awt.Image;

/* Generated by Together */

public class Speelveld {
    
    public static int ROWS = 7;
    public static int COLS = 7;
    
    private int[][] veld;
    
    public static final int EMPTY = 0;
    public static final int BOARD = 1;
    public static final int BALL = 2;
    
    private static final int OFFSET = 10;
    
    
    private int celWidth=40;
    private int cursorX,cursorY,selectedX,selectedY;
    private boolean selected;
    private AudioClip sounds[];
    private Image images[];
    
    
    
    public Speelveld(){
        int [][] tempVeld =
        {{EMPTY,EMPTY,BALL,BALL,BALL,EMPTY,EMPTY}, {EMPTY, EMPTY, BALL, BALL, BALL, EMPTY, EMPTY},
         {BALL,BALL,BALL,BALL,BALL,BALL,BALL},{BALL,BALL,BALL,BOARD,BALL,BALL,BALL},
         {BALL,BALL,BALL,BALL,BALL,BALL,BALL},{EMPTY,EMPTY,BALL,BALL,BALL,EMPTY,EMPTY}, {EMPTY, EMPTY, BALL, BALL, BALL, EMPTY, EMPTY}};
        
        veld= tempVeld;
        cursorX=3;
        cursorY=3;
        selected=false;
    }
    
    public void reset(){
        sounds[0].play();
        int [][] tempVeld =
        {{EMPTY,EMPTY,BALL,BALL,BALL,EMPTY,EMPTY}, {EMPTY, EMPTY, BALL, BALL, BALL, EMPTY, EMPTY},
         {BALL,BALL,BALL,BALL,BALL,BALL,BALL},{BALL,BALL,BALL,BOARD,BALL,BALL,BALL},
         {BALL,BALL,BALL,BALL,BALL,BALL,BALL},{EMPTY,EMPTY,BALL,BALL,BALL,EMPTY,EMPTY}, {EMPTY, EMPTY, BALL, BALL, BALL, EMPTY, EMPTY}};
        
        veld= tempVeld;
        cursorX=3;
        cursorY=3;
        selected=false;
    }
    
    public int[][] getVeld(){
        return veld;
    }
    
    public void setVeld(int[][] veld){
        this.veld=veld;
    }
    
    public void setStuff(AudioClip sounds[],Image images[]){
        this.sounds=sounds;
        this.images=images;
    }
    
    
    public void drawVeld(Graphics2D g2d){
        g2d.setColor(new Color(220, 197,101));
        g2d.fillRoundRect(80, 0, 140, 300, 40, 40);
        g2d.fillRoundRect(0, 80, 300, 140, 40, 40);
        g2d.translate(10, 10);
        
        for (int i=0;i<ROWS;i++)
            for (int j=0;j<COLS;j++) {
                if (this.veld[j][i]==this.BOARD){
                    drawHole(g2d,j,i);
                } else if (this.veld[j][i]==this.BALL){
                    //drawGrid(g2d,j,i);
                    drawBall(g2d,j,i);
                } else if(this.veld[j][i]==this.EMPTY){
                    //g2d.drawImage(images[0],j*celWidth,i*celWidth,null);
                }
            }
        
        drawSelect(g2d,Color.red,cursorX*celWidth,cursorY*celWidth);
        if (selected){
            drawSelect(g2d,Color.blue,selectedX*celWidth,selectedY*celWidth);
        }
        
    }
    
    private void drawHole(Graphics2D g2d, int x, int y){
        g2d.drawImage(images[1],x*celWidth,y*celWidth,null);
    }
    
    private void drawSelect(Graphics2D g2d,Color c, int x, int y){
        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(c);
        g2d.drawRect(x+1,y+1,celWidth-2,celWidth-2);
    }
    
    private void drawBall(Graphics2D g2d, int x, int y){
        g2d.drawImage(images[2],x*celWidth,y*celWidth,null);
    }
    
    public void doMouseMove(MouseEvent e){
        
        int newCursorX=(e.getX()-OFFSET)/40;
        if (newCursorX<0){
            newCursorX=0;
        }
        if (newCursorX>(ROWS-1)){
            newCursorX=ROWS-1;
        }
        int newCursorY=(e.getY()-OFFSET)/40;
        if (newCursorY<0){
            newCursorY=0;
        }
        if (newCursorY>(COLS-1)){
            newCursorY=COLS-1;
        }
        if (veld[newCursorX][newCursorY]==this.BOARD || veld[newCursorX][newCursorY]==this.BALL){
            cursorX=newCursorX;
            cursorY=newCursorY;
        }
        
    }
    
    public void doMouseClick(MouseEvent e){
        if(e.getClickCount() == 1){
            if (!selected&&veld[cursorX][cursorY]==this.BALL) {
                selectedX=cursorX;
                selectedY=cursorY;
                selected=true;
            } else{
                checkMove(selectedX,selectedY,cursorX,cursorY);
                selected=false;
            }
        }else if (e.getClickCount() == 2){
            reset();
        }
    }
    
    public void doMove(KeyEvent e){
        int newCursorX=cursorX;
        int newCursorY=cursorY;
        switch(e.getKeyCode()){
        case (KeyEvent.VK_UP):
            newCursorY--;
            if (newCursorY<0){
                newCursorY=ROWS-1;
            }
            break;
        case (KeyEvent.VK_DOWN):
            newCursorY++;
            if (newCursorY>(ROWS-1)){
                newCursorY=0;
            }
            break;
        case (KeyEvent.VK_LEFT):
            newCursorX--;
            if (newCursorX<0){
                newCursorX=COLS-1;
            }
            break;
        case (KeyEvent.VK_RIGHT):
            newCursorX++;
            if (newCursorX>(COLS-1)){
                newCursorX=0;
            }
            break;
        case (KeyEvent.VK_SPACE):
            if (!selected&&veld[cursorX][cursorY]==this.BALL) {
                selectedX=cursorX;
                selectedY=cursorY;
                selected=true;
            } else{
                checkMove(selectedX,selectedY,cursorX,cursorY);
                selected=false;
            }
            break;
        }
        
        if (veld[newCursorX][newCursorY]==this.BOARD || veld[newCursorX][newCursorY]==this.BALL){
            cursorX=newCursorX;
            cursorY=newCursorY;
        }
        
    }
    
    private void checkMove(int fx,int fy, int tx, int ty){
        if (veld[tx][ty]==this.BOARD) {
            if (fx==tx){
                if(Math.abs(fy-ty)==2)
                    if(veld[fx][(fy+ty)/2]==this.BALL) {
                        veld[fx][fy]=this.BOARD;
                        veld[tx][ty]=this.BALL;
                        veld[fx][(fy+ty)/2]=this.BOARD;
                        sounds[1].play();
                    }
                
            } else if (fy==ty){
                if(Math.abs(fx-tx)==2)
                    if(veld[(fx+tx)/2][fy]==this.BALL) {
                        veld[fx][fy]=this.BOARD;
                        veld[tx][ty]=this.BALL;
                        veld[(fx+tx)/2][fy]=this.BOARD;
                        sounds[1].play();
                    }
            }
        }
        
        checkGameOver();
        
    }
    
    public int checkBallsLeft(){
        int count=0;
        for(int i=0;i<COLS;i++)
            for (int j=0;j<ROWS;j++)
                if (veld[i][j]==this.BALL)
                    count++;
        return count;
    }
    
    public boolean checkGameOver(){
        boolean gameOver = true;
        //nog nie gedaan
        //horizontaal
        
        for (int j=0;j<ROWS;j++)
            for(int i=1 ;i<COLS-1;i++)
                if (veld[i][j]==this.BALL)
                    if((veld[i-1][j]==this.BALL&&veld[i+1][j]==this.BOARD)||(veld[i+1][j]==this.BALL&&veld[i-1][j]==this.BOARD))
                        gameOver=false;
        //verticaal
        for (int j=0;j<COLS;j++)
            for(int i=1 ;i<ROWS-1;i++)
                if (veld[j][i]==this.BALL)
                    if((veld[j][i-1]==this.BALL&&veld[j][i+1]==this.BOARD)||(veld[j][i+1]==this.BALL&&veld[j][i-1]==this.BOARD))
                        gameOver=false;
        
        
        return gameOver;
    }
}
