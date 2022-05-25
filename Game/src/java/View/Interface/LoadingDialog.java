package View.Interface;

import View.ResourceLoader;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;


public class LoadingDialog extends JDialog implements Runnable {

  JProgressBar t_bar;
  JLabel t_percent;
  Thread th_loader;
  ResourceLoader t_loader;

  public LoadingDialog()
  {
    //head
    try {
      setIconImage(ImageIO.read(new File("Game/src/Resources/icons/rasteroid_logo.png")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    setTitle("Rasteroid preload");
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    setSize(500,150);
    setLocationRelativeTo(null); //center on screen
    setLayout(new GridBagLayout());
    getContentPane().setBackground(new Color(0x1F1F1F));
    //title
    JLabel t_lab1 = new JLabel("Loading resources...");
    GridBagConstraints t_anchor = new GridBagConstraints();
    {
      t_lab1.setForeground(new Color(0xD9D9D9));
      t_anchor.gridx = 0;
      t_anchor.gridy = 0;
      t_anchor.anchor = GridBagConstraints.WEST;
      t_anchor.fill = GridBagConstraints.HORIZONTAL;
      t_anchor.insets = new Insets(0,50,0, 50);
    }
    add(t_lab1, t_anchor);
    //percent
    t_percent = new JLabel("0%");
    {
      t_percent.setForeground(new Color(0xD9D9D9));
      t_anchor.gridx = 1;
      t_anchor.gridy = 0;
      t_anchor.anchor = GridBagConstraints.EAST;
      t_anchor.fill = GridBagConstraints.NONE;
      t_anchor.insets = new Insets(0,50,0, 50);
    }
    add(t_percent, t_anchor);
    //progress
    t_bar = new JProgressBar();
    {
      t_bar.setBorderPainted(false);
      t_bar.setForeground(Color.GREEN);
      t_bar.setBackground(Color.DARK_GRAY);
      t_bar.setMaximum(100);
      t_bar.setMinimum(0);
      t_bar.setValue(0);
      t_anchor.gridx = 0;
      t_anchor.gridy = 1;
      t_anchor.weightx = 1f;
      t_anchor.gridwidth = 2;
      t_anchor.fill = GridBagConstraints.HORIZONTAL;
      t_anchor.anchor = GridBagConstraints.WEST;
      t_anchor.insets = new Insets(10,50,0, 50);
    }
    add(t_bar, t_anchor);
  }

  public void set_percent(int percent)
  {
    t_percent.setText(percent+"%");
    t_bar.setValue(percent);
  }

  @Override
  public void setVisible(boolean b) {
    super.setVisible(b);
    if (b)
    {
      //load res
      t_loader = new ResourceLoader();
      th_loader = new Thread(this);
      th_loader.start();
    }
  }

  @Override
  public void run() {
    t_loader.init();
    do
    {
      try {
        set_percent(t_loader.get_percent_done());
        sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } while (t_loader.is_loading());
    //show 100% completed
    try {
      set_percent(t_loader.get_percent_done());
      sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //launch_application
    setVisible(false);
  }

  public Thread get_thread()
  {
    return th_loader;
  }

}
