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
          false, //d
          false //space bar
    };
     
    @Override
    public void keyTyped(KeyEvent e) {
        switch (e.getKeyChar()) {
            case 'w' -> // La tecla de arriba
                    active_keys[0] = true;
            case 'a' ->   //  IZQUIERDA -->
                    active_keys[1] = true;
            case 'd' ->//  DERECHA -->
                    active_keys[2] = true;
        }
        if (Character.isSpaceChar(e.getKeyChar()))
        {
            active_keys[3] = true;
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
                System.out.println("we");
                break;
            case 'a':   //  IZQUIERDA -->   
                active_keys[1] = false;
                break;
            case 'd'://  DERECHA -->     
            active_keys[2] = false;
             break; 
        }
        if (Character.isSpaceChar(e.getKeyChar()))
        {
            active_keys[3] = false;
        }
    }

    public boolean no_key_pressed()
    {
        boolean no_key = true;
        int i = 0;
        while (no_key && i < active_keys.length)
        {
            if (active_keys[i])
            {
                no_key = false;
            }
            i++;
        }
        return no_key;
    }
            
    public boolean[] get_active_keys(){
        return active_keys;
    }
}
