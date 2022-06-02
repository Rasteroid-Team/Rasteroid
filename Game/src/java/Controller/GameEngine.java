package Controller;


import Model.GameObject;
import Model.Player;
import Testing.InputAdapter;
import View.GraphicEngine;
import View.Objects.ObjectModels.Players.PlayerColors;
import View.Resources;
import communications.CommunicationController;
import communications.ConnectionInterface;
import communications.ProtocolDataPacket;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

public class GameEngine extends Thread implements ConnectionInterface {

  // 0 up, 1 right, 2 down, 3 left
  private static String [] connections = new String[4];

  private static double ups_max = 60.0;
  private static double ups_period = 1E+3 / ups_max;
  final GraphicEngine graphics;
  private final GameControl game;
  private boolean is_running = false;
  private double ups_average;
  private double fps_average;

  CommunicationController comController;
  private static List<GameObject> transferingList = new ArrayList<>();


  public GameEngine(GameControl game, GraphicEngine graphics) {
    this.game = game;
    this.graphics = graphics;
    this.addMouseClickDetector();
    this.comController = new CommunicationController();
    comController.addAllListeners(this);
    //this.comController = new CommunicationController(9999, 2);
  }

  public double get_average_ups() {
    return ups_average;
  }

  public double get_average_fps() {
    return fps_average;
  }

  public static String[] getConnections() {
    return connections;
  }

  public void init() {
    is_running = true;
    start();
  }

  @Override
  public void run() {
    super.run();
    //Declaramos las variables de tiempo y ciclo de vida
    int update_count = 0;
    int frame_count = 0;
    long init_time;
    long current_time;
    long sleep_time;

    //El loop del juego
    init_time = System.currentTimeMillis();
    while (is_running) {
      //Intentamos actualizar y renderizar los objetos de la clase Juego
      try {
        synchronized (graphics) {
          game.update();
          update_count++;
          game.render(graphics.lock_canvas());
        }
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } finally {
        if (graphics != null) {
          try {
            graphics.unlock_and_post();
            frame_count++;
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }

      //Se transfieren los objetos
      this.transferObjects();

      //Pausamos el loop para que no exceda los UPS objetivo
      current_time = System.currentTimeMillis() - init_time;
      sleep_time = (long) (update_count * ups_period - current_time);
      if (sleep_time > 0) {
        try {
          sleep(sleep_time);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

      //Nos saltamos varios frames para mantener los UPS objetivo
      while (sleep_time < 0 && update_count < ups_max - 1) {
        game.update();
        update_count++;
        current_time = System.currentTimeMillis() - init_time;
        sleep_time = (long) (update_count * ups_period - current_time);
      }

      //Calculamos el UPS y FPS objetivo
      current_time = System.currentTimeMillis() - init_time;
      if (current_time >= 1000) {
        ups_average = update_count / (1E-3 * current_time);
        fps_average = frame_count / (1E-3 * current_time);
        update_count = 0;
        frame_count = 0;
        init_time = System.currentTimeMillis();
      }
    }
  }

  public void set_max_ups(int ups) {
    ups_max = ups;
    ups_period = 1E+3 / ups_max;
  }

  private void addMouseClickDetector() {
    graphics.addMouseListener(new MouseListener(){
      @Override
      public void mouseClicked(MouseEvent e) {}
      @Override
      public void mouseEntered(MouseEvent e) {}
      @Override
      public void mouseExited(MouseEvent e) {}
      @Override
      public void mousePressed(MouseEvent e) {
        //check if mouse position is inside one of the circles
        //if it is, show either the connection logic and a disconnect button
        //if the connection is live
        //or if it isn't show a menu asking for an ip address
        //check bottom mid circle

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int diameter = 25;

        if(e.getX() <= screenSize.getWidth()/2+diameter/2 && e.getX() >= screenSize.getWidth()/2-diameter/2 &&
                e.getY() <= screenSize.getHeight()-15 && e.getY() >= screenSize.getHeight()-diameter-15){
          System.out.println("Pressed bottom");
          if(GameEngine.getConnections()[2] == null){
            //show a panel asking for an ip
            String ip = JOptionPane.showInputDialog(graphics,
                    "Ip to connect to?", null);
            if(ip != null && !ip.isEmpty()){
              comController.connectToIp(ip);
              GameEngine.getConnections()[2] = "waiting";
            }
          }else{
            //show panel with connection info and disconnect button
            //ipLabel.setText(main.getInfo(3));
            //dataDialog.setVisible(true);
          }
        }//then check top mid
        else if(e.getX() <= screenSize.getWidth()/2+diameter/2 && e.getX() >= screenSize.getWidth()/2-diameter/2 &&
                e.getY() <= 15+diameter && e.getY() >= 15){
          System.out.println("Pressed top");
          if(GameEngine.getConnections()[0] == null){
            //show a panel asking for an ip
            String ip = JOptionPane.showInputDialog(graphics,
                    "Ip to connect to?", null);
            if(ip != null && !ip.isEmpty()){
              comController.connectToIp(ip);
              GameEngine.getConnections()[0] = "waiting";
            }
          }else{
            //show panel with connection info and disconnect button
            //ipLabel.setText(main.getInfo(1));
            //dataDialog.setVisible(true);
          }
        }//then check right
        else if(e.getX() <= screenSize.getWidth()-15 && e.getX() >= screenSize.getWidth()-diameter-15 &&
                e.getY() <= screenSize.getHeight()/2+diameter/2 && e.getY() >= screenSize.getHeight()/2-diameter/2){
          System.out.println("Pressed right");
          if(GameEngine.getConnections()[1] == null){
            //show a panel asking for an ip
            String ip = JOptionPane.showInputDialog(graphics,
                    "Ip to connect to?", null);
            if(ip != null && !ip.isEmpty()){
              comController.connectToIp(ip);
              GameEngine.getConnections()[1] = "waiting";
            }
          }else{
            //show panel with connection info and disconnect button
            //ipLabel.setText(main.getInfo(2));
            //dataDialog.setVisible(true);
          }
        }//finally check left
        else if(e.getX() <= 0+diameter+15 && e.getX() >= 15 &&
                e.getY() <= screenSize.getHeight()/2+diameter/2 && e.getY() >= screenSize.getHeight()/2-diameter/2){
          System.out.println("Pressed left");
          if(GameEngine.getConnections()[3] == null){
            //show a panel asking for an ip
            String ip = JOptionPane.showInputDialog(graphics,
                    "Ip to connect to?", null);
            if(ip != null && !ip.isEmpty()){
              comController.connectToIp(ip);
              GameEngine.getConnections()[3] = "waiting";
            }
          }else{
            //show panel with connection info and disconnect button
            //ipLabel.setText(main.getInfo(4));
          }
        }
      }
      @Override
      public void mouseReleased(MouseEvent e) {}
    });
  }

  @Override
  public void onMessageReceived(ProtocolDataPacket packet) {
    switch (packet.getId()) {
      case 150 -> {
        Player player = (Player) packet.getObject();
        player.setModel(Resources.PLAYER_HR75());
        player.getModel().set_aura_color(player.getColor());
        player.setStateList(player.getModel().get_machine_states());
        player.repositionAfterTransfer();
        GameControl.add_object(player);
      }
      case 120 -> {
        int direction = (int) packet.getObject();
        //Gira la direccion pasada para que sea correcta
        direction = (direction < 2) ? direction + 2 : direction - 2;
        connections[direction] = packet.getSourceID();
      }
      case 152 -> {
        int[] movement = (int[]) packet.getObject();
        int strength = movement[0];
        int angle = movement[1];
        int i = 0;
        boolean found = false;

        while (i < GameControl.objects.size() && !found) {
          if (GameControl.objects.get(i) instanceof Player && ((Player)GameControl.objects.get(i)).getAssociatedMac() != null
                  && ((Player) GameControl.objects.get(i)).getAssociatedMac().equals(packet.getSourceID())) {

            ((Player) GameControl.objects.get(i)).getPlayerBody().setAngle(angle);
            ((Player) GameControl.objects.get(i)).getPlayerBody().setPotenciaAcceleracion(strength);
            System.out.println(strength);
            if (strength == 0){
              ((Player) GameControl.objects.get(i)).getPlayerBody().setAccelerando(false);
            } else {
              ((Player) GameControl.objects.get(i)).getPlayerBody().setAccelerando(true);
            }
            found = true;
          }
          i++;
        }
      }
      case 151 -> {
        int i = 0;
        boolean found = false;

        while (i < GameControl.objects.size() && !found) {
          if (GameControl.objects.get(i) instanceof Player && ((Player)GameControl.objects.get(i)).getAssociatedMac() != null
                  && ((Player) GameControl.objects.get(i)).getAssociatedMac().equals(packet.getSourceID())) {

            ((Player) GameControl.objects.get(i)).setShooting(true);
            found = true;
          }
          i++;
        }
      }
    }
  }

  @Override
  public void onConnectionAccept(String mac) {

    if (comController.getConnectedDeviceType(mac) == CommunicationController.MVL){
      GameControl.add_object(new Player(Resources.PLAYER_HR75(), PlayerColors.cyan, mac));
    }
    else {
      int i = 0;
      boolean found = false;
      while (!found && i < connections.length) {
        if (connections[i] != null && connections[i].equals("waiting")) {
          connections[i] = mac;
          comController.sendMessage(comController.createPacket(mac, 120, i));
          found = true;
        }
        i++;
      }
    }

  }

  @Override
  public void onConnectionClosed(String mac) {

  }

  @Override
  public void onLookupUpdate(ArrayList<String> macs) {

  }

  public void sendObject (GameObject object, String targetMac){

  }

  public static void addTransferingObject(GameObject object)
  {
    synchronized (transferingList) {
      transferingList.add(object);
    }
    GameControl.remove_object(object);
  }

  private void transferObjects(){
    synchronized (transferingList) {
      for (GameObject object : transferingList) {
        if (object instanceof Player) {
          ((Player)object).setModel(null);
          object.setStateList(null);
          ((Player)object).repositionBeforeTransfer();
          comController.sendMessage(comController.createPacket(object.getTransferingTo(), 150, object));
          comController.sendMessage(comController.createPacket(((Player)object).getAssociatedMac(), 180,object.getTransferingTo()));
        }
      }
      transferingList.clear();
    }
  }
}
