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
    private Properties properties = new Properties();
    private String file;
    private String connectionsPath;
    JSONParser parser = new JSONParser();
    Object object;

    public Properties getProperties() {
        return properties;
    }

    public ConfigurationController() {
        file = "connections.properties";
        Path path = Paths.get(file);
        String ruta = String.valueOf(path.toAbsolutePath());
        connectionsPath = ruta.replace(file,"Game\\src\\Resources\\config\\")+file;
    }

    public ArrayList<String> connect(){
        ArrayList<String> listConnection = new ArrayList<>();
        if (connectionsPath != null) {
            try {
                this.getProperties().load(new FileReader(connectionsPath));
                //positions: 0 top, 1 right, 2 bottom, 3 left
                for (int i=0; i<4; i++){
                    listConnection.add(this.getProperties().getProperty(""+i));
                }
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
        String oldKills = (String) config.get("kills");
        records.add(name);
        records.add(oldKills);
        return records;
    }

    public void writeRecord(String name, int newKills){
        int oldKills = Integer.parseInt(readRecord().get(1));
        if (newKills > oldKills)
        {
            String kills = String.valueOf(newKills);
            JSONObject json = new JSONObject();
            json.put("nombre", name);
            json.put("kills", kills);
            try {
                String jsonFile = "record.json";
                Path path = Paths.get(jsonFile);
                String ruta = String.valueOf(path.toAbsolutePath());
                String rutaAbsoluta = ruta.replace(jsonFile,"Game\\src\\Resources\\config\\")+jsonFile;
                FileWriter file = new FileWriter(rutaAbsoluta);
                file.write(json.toJSONString());
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

