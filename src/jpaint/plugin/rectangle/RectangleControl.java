package jpaint.plugin.rectangle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import jpaint.Command;
import jpaint.UILoader;


public class RectangleControl extends AnchorPane implements Command {

    private String controlName;
    double startX,startY,stopX,stopY;
    double width,height;
    @FXML
    private Button button;

    public RectangleControl() {
        this.controlName = "tool.rectangle";
        UILoader uiLoader = new UILoader(getClass().getResource("rectangle.fxml"),this);
        uiLoader.load();
    }

    @Override
    public String getName() {
        return controlName;
    }

    @Override
    public void setAction(EventHandler<ActionEvent> value) {
        button.setOnAction(value);
    }

    @Override
    public void execute(GraphicsContext gc, GraphicsContext gs,MouseEvent e, Color color, int size) {
        gs.setFill(color);
        if(e.getEventType()==MouseEvent.MOUSE_PRESSED) {
            this.startX=e.getX();
            this.startY=e.getY();
            gs.beginPath();
            gs.moveTo(e.getX(), e.getY());
        }else if(e.getEventType()==MouseEvent.MOUSE_DRAGGED){
            width=e.getX()-startX;
            height=e.getY()-startY;
            gs.clearRect(0, 0, 5000, 5000);
            gs.beginPath();
            gs.moveTo(startX, startY);
            gs.fillRect(startX,startY,width,height);
            gs.stroke();
        }else if(e.getEventType()==MouseEvent.MOUSE_RELEASED){
            width=e.getX()-startX;
            height=e.getY()-startY;
            gs.clearRect(0, 0, 5000, 500);
            gc.setFill(color);
            gc.beginPath();
            gc.moveTo(startX,startY);
            gc.fillRect(startX, startY, width, height);
            gc.stroke();
        }
    }

    @Override
    public void executeText(GraphicsContext gc, GraphicsContext gs, KeyEvent e, Color color, int size) {
        //No-op
    }
}
