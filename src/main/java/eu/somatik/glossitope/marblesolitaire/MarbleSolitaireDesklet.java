/*
 * MarbleSolitaireDesklet.java
 * 
 * Created on May 21, 2007, 10:22:39 PM
 * 
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package eu.somatik.glossitope.marblesolitaire;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;
import org.glossitope.desklet.Desklet;
import org.glossitope.desklet.DeskletContainer;
import org.glossitope.desklet.test.DeskletTester;

/**
 *
 * @author francisdb
 */
public class MarbleSolitaireDesklet extends Desklet{

    private SolitairePanel solitairePanel;

    
    /**
     * 
     * @param s 
     */
    public static void main(String s[]) {
        DeskletTester.start(MarbleSolitaireDesklet.class);
    }

    public void init() throws Exception {
        runOnEDT(new Runnable(){

            public void run() {
                 solitairePanel = new SolitairePanel();
                DeskletContainer container = getContext().getContainer();
                getContext().getContainer().setBackgroundDraggable(true);
                container.setContent(solitairePanel);
                container.setResizable(false);
                container.setShaped(true);    
                container.setVisible(true);
            }
            
        });
    }

    public void start() throws Exception {
        solitairePanel.newGame();
    }

    public void stop() throws Exception {
        this.getContext().notifyStopped();
    }

    public void destroy() throws Exception {
        // nothing here
    }
    
        
    private void runOnEDT(Runnable runnable){
        if(SwingUtilities.isEventDispatchThread()){
            System.out.println("ON EDT");
            runnable.run();
        }else{
            System.out.println("MOVING TO EDT");
            try         {
                javax.swing.SwingUtilities.invokeAndWait(runnable);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            }
}
    }
}
