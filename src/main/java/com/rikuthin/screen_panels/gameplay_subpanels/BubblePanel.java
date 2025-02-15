package com.rikuthin.screen_panels.gameplay_subpanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JLabel;
import javax.swing.JPanel;

// import com.rikuthin.game_objects.Alien;
// import com.rikuthin.game_objects.Bat;

/**
   A component that displays all the game entities
*/

public class BubblePanel extends JPanel {
   public BubblePanel() {
         setBackground(new Color(200, 170, 170));
         setPreferredSize(new Dimension(580, 590));

         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
         String[] fontNames = ge.getAvailableFontFamilyNames();

         for (String fontName: fontNames) {
            JLabel label = new JLabel(fontName);
            label.setFont(new Font(fontName, Font.PLAIN, 16));
            add(label);
         }
   }
}
   
//    Bat bat;
//    Alien alien;

//    public BubblePanel () {
// 	bat = null;
//  	alien = null;
//    }


//    public void createGameEntities() {
//        bat = new Bat (this, 50, 350); 
//        alien = new Alien (this, 200, 10, bat); 
//    }


//    public void drawGameEntities() {

//        if (bat != null) {
//        	  bat.draw();
//        }
//    }


//    public void updateGameEntities(int direction) {

// 	if (bat == null)
//  	   return;

// 	bat.erase();
//        	bat.move(direction);

//    }


//    public void dropAlien() {
// 	if (alien != null) {
// 		alien.start();
// 	}
//    }


//    public boolean isOnBat (int x, int y) {
// 	if (bat != null)
//       	   return bat.isOnBat(x, y);
//   	else
// 	   return false;
//    }

// }