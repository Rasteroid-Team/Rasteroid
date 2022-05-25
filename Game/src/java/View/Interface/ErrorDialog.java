package View.Interface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ErrorDialog extends JDialog {

  public ErrorDialog(String TAG, String message)
  {
    //head
    try {
      setIconImage(ImageIO.read(new File("Game/src/Resources/icons/rasteroid_logo.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setTitle("Exception");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(500,150);
    setLocationRelativeTo(null); //center on screen
    setLayout(new GridBagLayout());
    getContentPane().setBackground(new Color(0x1F1F1F));
    //tag
    JLabel t_lab1 = new JLabel("An error occurred during executing \""+TAG+"\"");
    GridBagConstraints t_anchor = new GridBagConstraints();
    {
      t_lab1.setForeground(new Color(0xD05555));
      t_anchor.gridx = 0;
      t_anchor.gridy = 0;
      t_anchor.anchor = GridBagConstraints.WEST;
      t_anchor.fill = GridBagConstraints.HORIZONTAL;
      t_anchor.insets = new Insets(0,50,0, 50);
    }
    add(t_lab1, t_anchor);
    //message
    JLabel t_lab2 = new JLabel(message);
    {
      t_lab2.setForeground(new Color(0xD9D9D9));
      t_anchor.gridx = 0;
      t_anchor.gridy = 1;
      t_anchor.weightx = 1f;
      t_anchor.gridwidth = 2;
      t_anchor.fill = GridBagConstraints.HORIZONTAL;
      t_anchor.anchor = GridBagConstraints.WEST;
      t_anchor.insets = new Insets(10,50,0, 50);
    }
    add(t_lab2, t_anchor);
    //set visible
    setVisible(true);
  }

}
