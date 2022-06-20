package Controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ConfigurationController {
    private Properties p = new Properties();
    private InetAddress inetAddress;
    private String ip;
    private String nombre;
    JSONParser parser = new JSONParser();
    Object object;

    public Properties getP() {
        return p;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress) {
        this.inetAddress = inetAddress;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ConfigurationController() {
        try {
            this.setInetAddress(InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        this.setIp(this.getInetAddress().getHostAddress().replace(".", "") + ".properties");
        Path path = Paths.get(this.getIp());
        String ruta = String.valueOf(path.toAbsolutePath());
        System.out.println("Ruta: " + ruta.replace(this.getIp(),"Game\\src\\Resources\\config\\")+this.getIp());
        this.setNombre(ruta.replace(ip,"Game\\src\\Resources\\config\\")+this.getIp());
    }

    public ArrayList<String> connect(){
        ArrayList<String> listConnection = new ArrayList<>();
        if (this.getIp() != null) {
            try {
                String n = this.getNombre();
                this.getP().load(new FileReader(n));
                String top = this.getP().getProperty("0");
                listConnection.add(top);
                String right = this.getP().getProperty("1");
                listConnection.add(right);
                String bottom = this.getP().getProperty("2");
                listConnection.add(bottom);
                String left = this.getP().getProperty("3");
                listConnection.add(left);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listConnection;
    }

    public ArrayList<String> readRecord(){
        ArrayList<String> records = new ArrayList<>();
        String json = "record.json";
        Path path = Paths.get(json);
        String ruta = String.valueOf(path.toAbsolutePath());
        String rutaAbsoluta = ruta.replace(json,"Game\\src\\Resources\\config\\")+json;
        {
            try {
                object = parser.parse(new FileReader(rutaAbsoluta));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        JSONObject config = (JSONObject) object;
        String name = (String) config.get("nombre");
        int kills = (int) config.get("kills");
        records.add(name);
        records.add(String.valueOf(kills));
        return records;
    }

    public void writeRecord(String name, int newKills){
        int oldKills = Integer.parseInt(readRecord().get(1));
        if (newKills > oldKills)
        {
            JSONObject json = new JSONObject();
            json.put("nombre", name);
            json.put("kills", newKills);
            try {
                String jsonFile = "record.json";
                Path path = Paths.get(jsonFile);
                String ruta = String.valueOf(path.toAbsolutePath());
                String rutaAbsoluta = ruta.replace(jsonFile,"Game\\src\\Resources\\config\\")+jsonFile;
                // Para sobreescribir el fichero hay que añadir 'true' como segundo parámetro.
                FileWriter file = new FileWriter(rutaAbsoluta, true);
                file.write(json.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

