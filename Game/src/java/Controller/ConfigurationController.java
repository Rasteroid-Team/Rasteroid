package Controller;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationController {
    Properties p = new Properties();
    InetAddress i;
    String ip;
    String nombre;

    public ConfigurationController() {
        try {
            i = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        ip = i.getHostAddress().replace(".", "") + ".properties";
        Path path = Paths.get(ip);
        String ruta = String.valueOf(path.toAbsolutePath());
        System.out.println("Ruta: " + ruta.replace(ip,"Game\\src\\Resources\\config\\")+ip);
        nombre = ruta.replace(ip,"Game\\src\\Resources\\config\\")+ip;
    }

    public ArrayList<String> connect(){
        ArrayList<String> listConnection = new ArrayList<>();
        if (ip != null) {
            try {
                String n = nombre;
                p.load(new FileReader(n));
                String top = p.getProperty("0");
                listConnection.add(top);
                String right = p.getProperty("1");
                listConnection.add(right);
                String bottom = p.getProperty("2");
                listConnection.add(bottom);
                String left = p.getProperty("3");
                listConnection.add(left);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listConnection;
    }
}
