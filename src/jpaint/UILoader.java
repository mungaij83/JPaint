/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;

/**
 *
 * @author njoroge
 */
public class UILoader {

    private URL resource;
    private Object obj;

    public UILoader(URL resource, Object obj) {
        this.resource = resource;
        this.obj=obj;
    }

    public void load() {
        FXMLLoader fxmlLoader = new FXMLLoader(resource);
        fxmlLoader.setRoot(obj);
        fxmlLoader.setController(obj);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}