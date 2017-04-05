/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint;

/**
 *
 * @author njoroge
 */
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class PluginManager {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    private List<Command> loadTools(String tool) throws ParseException{

        List<Command> command = new ArrayList<>();
        JSONParser parser=new JSONParser();
        JSONObject manifest = (JSONObject)parser.parse(tool);
        ClassLoader loader = PluginManager.class.getClassLoader();
        JSONArray plugins = (JSONArray) manifest.get("plugins");
        JSONObject obj;
        for (int i = 0; i < plugins.size(); i++) {
            obj = (JSONObject) plugins.get(i);
            try {
                Class cls = loader.loadClass(obj.get("package")+"." + obj.get("class"));
                Command cmd = (Command) cls.newInstance();
                command.add(cmd);
                System.out.print("Initialized " + obj.get("name") + "!\n");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return command;
    }

    public void loadPlugins(ObservableList<Command> commands) {
        try {
            InputStream mIn = getClass().getResourceAsStream("manifest.json");
            if (mIn != null) {
                String jsonTool = readAll(new InputStreamReader(mIn));
                //System.out.println("JSON:" + jsonTool);
                List<Command> c = loadTools(jsonTool);
                for (Command newTool : c) {
                    commands.add(newTool);
                }
            } else {
                System.out.println("Could not load manifest file");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print("Cannot initialize plugins");
        }
    }
}
