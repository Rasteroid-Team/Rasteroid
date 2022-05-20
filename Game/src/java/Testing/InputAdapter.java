/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Testing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author DAM
 */
public class InputAdapter extends KeyAdapter{
    
    private final boolean[] active_keys = new boolean[]{
          false, //w
          false, //a
          false //d
    };
     
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w': // La tecla de arriba
                active_keys[0] = true;
                break;
            case 'a':   //  IZQUIERDA -->   
                active_keys[1] = true;
                break;
            case 'd'://  DERECHA -->     
                active_keys[2] = true;
                break; 
        }
    } 

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w': // La tecla de arriba
                active_keys[0] = false;
                break;
            case 'a':   //  IZQUIERDA -->   
                active_keys[1] = false;
                break;
            case 'd'://  DERECHA -->     
            active_keys[2] = false;
             break; 
        }
    }
            
    public boolean[] get_active_keys(){
        return active_keys;
    }
}
