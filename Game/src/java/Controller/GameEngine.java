package Controller;


import View.GraphicEngine;

public class GameEngine extends Thread {

  private static double ups_max = 60.0;
  private static double ups_period = 1E+3 / ups_max;
  final GraphicEngine graphics;
  private final GameControl game;
  private boolean is_running = false;
  private double ups_average;
  private double fps_average;

  public GameEngine(GameControl game, GraphicEngine graphics) {
    this.game = game;
    this.graphics = graphics;
  }

  public double get_average_ups() {
    return ups_average;
  }

  public double get_average_fps() {
    return fps_average;
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
}
