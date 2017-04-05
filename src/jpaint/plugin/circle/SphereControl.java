package jpaint.plugin.circle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import jpaint.Command;
import jpaint.UILoader;

public class SphereControl extends AnchorPane implements Command {

    private String controlName;
    double startX, startY, stopX, stopY;
    double x, y;

    @FXML
    private Button button;

    public SphereControl() {
        this.controlName = "tool.circle";
        UILoader uiLoader = new UILoader(getClass().getResource("sphere.fxml"), this);
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
    public void execute(GraphicsContext gc, GraphicsContext gs, MouseEvent e, Color color, int size) {

        gs.setFill(color);
        gs.setLineWidth(size);
        gs.setStroke(color);
        gs.setLineCap(StrokeLineCap.ROUND);
        gs.setLineJoin(StrokeLineJoin.ROUND);
        if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
            startX = e.getX();
            startY = e.getY();
            
            gs.beginPath();
        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            gs.clearRect(startX, startY, e.getX(), e.getY());
            gs.fillOval(startX, startY, (e.getX() - startX), (e.getY() - startY));
            gs.stroke();
        } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            gc.setFill(color);
            gc.setLineWidth(size);
            gc.setStroke(color);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineJoin(StrokeLineJoin.ROUND);
            stopX = e.getX();
            stopY = e.getY();
            gs.closePath();
            gs.clearRect(startX, startY, e.getX(), e.getY());
            gc.beginPath();
            gc.fillOval(startX, startY, (e.getX() - startX), (e.getY() - startY));
            gc.stroke();
            gc.closePath();
        }
    }

    @Override
    public void executeText(GraphicsContext gc, GraphicsContext gs, KeyEvent e, Color color, int size) {
        //No-op
    }
}
