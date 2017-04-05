/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint;

import java.util.Optional;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Transform;
import jpaint.controllers.JPaintMain;

/**
 *
 * @author njoroge
 */
public class JCanvas extends StackPane{
    JPaintMain parent;
    Canvas canvas;
    Canvas sketch;
    TextField text;
    GraphicsContext gc,gs;
    public JCanvas(JPaintMain m){
        this.parent=m;
        this.initUI();
        
    }
    public void clearWindow(){
       gc.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
    }
    public void loadFromFile(String fileName){
        
    }
    public WritableImage save(String fileName){
        WritableImage writableImage = new WritableImage((int)Math.rint(canvas.getWidth()), (int)Math.rint(canvas.getHeight()));
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(1.0, 1.0));
        return canvas.snapshot(spa, writableImage);   
    }
    public void setCWidth(double c){
        setWidth(c);
    }
    public void setCHeight(double c){
        setHeight(c);
    }
    public GraphicsContext getSetch(){
        return gs;
    }
    public GraphicsContext getCanvas(){
        return gc;
    }
    private void initUI(){
        canvas=new Canvas();
        sketch=new Canvas();
        gc=canvas.getGraphicsContext2D();
        gs=sketch.getGraphicsContext2D();
        sketch.setOpacity(1.0);
        canvas.widthProperty().bind(this.widthProperty());
        canvas.heightProperty().bind(this.heightProperty());
        sketch.widthProperty().bind(canvas.widthProperty());
        sketch.heightProperty().bind(canvas.heightProperty());
        text=new TextField();
        canvas.setFocusTraversable(true);
        sketch.setFocusTraversable(true);
        
        getChildren().add(canvas);
        getChildren().add(sketch);
        //cPane.toFront();
        getStyleClass().add("canvas");
        //Canvas
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            canvas.requestFocus();
            executeCommand(gc, gs,event);
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> executeCommand(gc, gs,event));
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> executeCommand(gc, gs,event));
        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> executeCommand(gc, gs,event));
        canvas.addEventHandler(KeyEvent.KEY_TYPED, event->executeText(gc,gs,event));
        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
           parent.getMousePosition().setText(String.format("Mouse %.0fX %.0f ", event.getX(), event.getY()));
        });
        //Sketch
        sketch.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            sketch.requestFocus();
            executeCommand(gc, gs,event);
        });
        sketch.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> executeCommand(gc, gs,event));
        sketch.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> executeCommand(gc, gs,event));
        sketch.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> executeCommand(gc, gs,event));
        sketch.addEventHandler(KeyEvent.KEY_TYPED, event->executeText(gc,gs,event));
        sketch.addEventHandler(MouseEvent.MOUSE_MOVED, event -> {
           parent.getMousePosition().setText(String.format("Mouse %.0fX %.0f ", event.getX(), event.getY()));
        });
        //Menu
        MenuItem clear=new MenuItem("Clear");
        clear.setOnAction(event->{
            Alert al=new Alert(Alert.AlertType.CONFIRMATION);
            al.setHeaderText(null);
            al.setContentText("Are you sure you want to clear?");
            Optional<ButtonType> r=al.showAndWait();
            if(r.get()==ButtonType.OK){
                clearWindow();
            }
        });
        ContextMenu context=new ContextMenu(clear);
        //canvas.setOnContextMenuRequested(event->context.show(canvas,event.getScreenX(), event.getScreenY()));
        sketch.setOnContextMenuRequested(event->context.show(sketch,event.getScreenX(), event.getScreenY()));
        
    }
    private void executeCommand(GraphicsContext ctxt, GraphicsContext sketch,MouseEvent event) {
        Command tool=parent.getSelectedTool();
        if (tool != null) {
            tool.execute(ctxt, sketch,event, parent.getColorPicker().getValue(), parent.getPenSize().getValue());
        }
    }
    private void executeText(GraphicsContext ctxt, GraphicsContext sketch,KeyEvent event) {
        Command tool=parent.getSelectedTool();
        if (tool != null) {
            //System.out.println("Text: " + event.getCharacter());
            tool.executeText(ctxt, sketch,event, parent.getColorPicker().getValue(), parent.getPenSize().getValue());
        }
    }
}
