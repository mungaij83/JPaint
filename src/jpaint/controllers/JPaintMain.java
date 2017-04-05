/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import jpaint.Command;
import jpaint.JCanvas;
import jpaint.PluginManager;

/**
 * FXML Controller class
 *
 * @author njoroge
 */
public class JPaintMain implements Initializable {

    Stage stage;
    boolean maximized;
    boolean iconMode;
    @FXML
    HBox menuBar;
    @FXML
    TabPane docPanel;
    @FXML
    VBox previews;
    @FXML
    TilePane pallet;
    int documentCount;
    ObservableList<Tab> previewSection;
    PluginManager plugins;
    ObservableList<Command> commands;
    @FXML
    Region region;
    private Command selectedTool;
    @FXML
    private Label selectedToolLabel;
    @FXML
    private Label mousePosition;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private ChoiceBox<Integer> penSize;
    Line l;
    double xOffset = 0;
    double yOffset = 0;
    Tab add;
    
    /***
     * Ribbon menu items
     * .
     */
    @FXML
    ChoiceBox<Integer> fontSize;
    ObservableList<Integer> sizes;
    @FXML
    ChoiceBox<String> fontFamily;
    ObservableList<String> families;
    Font font;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        commands = FXCollections.observableArrayList();
        plugins = new PluginManager();
        plugins.loadPlugins(commands);
        pallet.setPrefColumns(3);
        pallet.setVgap(5);
        pallet.setHgap(5);
        families=FXCollections.observableArrayList(Font.getFamilies());
        fontFamily.setItems(families);
        fontFamily.getSelectionModel().select(Font.getDefault().getFamily());
        sizes=FXCollections.observableArrayList(new Integer[]{7,8,9,10,11,12,14,16,20,24,28,36,48,72});
        
        fontSize.setItems(sizes);
        fontSize.getSelectionModel().select(new Integer(12));
        
        for (Command node : commands) {
            pallet.getChildren().add((Node) node);
            Class nodeClass = node.getClass();
            nodeClass.cast(node);
            node.setAction((event) -> {
                getSelectedToolLabel().setText(node.getName());
                selectedTool = node;
            });
        }
        previewSection = FXCollections.observableArrayList();
        ObservableList<Integer> pens = FXCollections.observableArrayList();
        for (int i = 1; i < 10; i++) {
            pens.add(i);
        }
        penSize.setItems(pens);
        penSize.getSelectionModel().select(0);
        colorPicker.setValue(Color.BLUE);
        
        maximized = false;
        iconMode = false;
        documentCount = 0;
        docPanel.getTabs().add(createTab());
        add=new Tab("+");
        try{
        add.setGraphic(new ImageView(getClass().getResource("../ui/icons/add.png").getFile()));
        }catch(Exception e){}
        add.setClosable(false);
        docPanel.getTabs().add(add);
        docPanel.getSelectionModel().selectedItemProperty().addListener((obj,oldTab,newTab)->{
            if(newTab==add){
                int l=docPanel.getTabs().size()-1;
                Tab t=createTab();
                docPanel.getTabs().add(l, t);
                docPanel.getSelectionModel().select(l);
            }
        });
        region.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            stage.setX(event.getScreenX() + xOffset);
            stage.setY(event.getScreenY() + yOffset);
        });
        region.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            xOffset =stage.getX()-event.getScreenX();
            yOffset =stage.getY()-event.getScreenY();
        });
        
    }

    private void addPreview() {

    }
    
    private Tab createTab() {
        Tab tab = new Tab();
        documentCount++;
        JCanvas jc=new JCanvas(this);
        
        //ScrollPane scroll=new ScrollPane();
        //scroll.setContent(jc);
        //scroll.autosize();
        //jc.setCHeight(scroll.getHeight());
        tab.setContent(jc);
        tab.setText("Document " + documentCount);
        return tab;
    }

    @FXML
    private void closeApplication() {
        stage.close();
    }
    @FXML
    private void iconify() {
        stage.setIconified(!iconMode);
        iconMode = !iconMode;
    }

    @FXML
    private void maximizeMinimize() {
        stage.setMaximized(!maximized);
        maximized = !maximized;
    }

    public void setStage(Stage s) {
        this.stage = s;
    }

    public Label getSelectedToolLabel() {
        return selectedToolLabel;
    }

    public Label getMousePosition() {
        return mousePosition;
    }

    public ColorPicker getColorPicker() {
        return colorPicker;
    }

    public ChoiceBox<Integer> getPenSize() {
        return penSize;
    }

    public Command getSelectedTool() {
        return selectedTool;
    }
}
