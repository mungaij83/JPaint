package jpaint.plugin.line;

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

public class LineControl extends AnchorPane implements Command {

    private String controlName;
    double startX, startY, stopX, stopY;
    double x, y;

    @FXML
    private Button button;

    public LineControl() {
        this.controlName = "tool.line";
        UILoader uiLoader = new UILoader(getClass().getResource("line.fxml"), this);
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
            stopX = e.getX();
            stopY = e.getY();
            gs.beginPath();
        } else if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
            gs.clearRect(0, 0, 5000, 5000);
            stopX = e.getX();
            stopY = e.getY();
            //gs.moveTo(startX, startY);
            //gs.lineTo(e.getX(), e.getY());
            gs.strokeLine(startX, startY, e.getX(), e.getY());
            gs.stroke();
        } else if (e.getEventType() == MouseEvent.MOUSE_RELEASED) {
            gc.setFill(color);
            gc.setLineWidth(size);
            gc.setStroke(color);
            gc.setLineCap(StrokeLineCap.ROUND);
            gc.setLineJoin(StrokeLineJoin.ROUND);
            gs.clearRect(0, 0, 5000, 500);
            gs.closePath();
            gc.beginPath();
            gc.strokeLine(startX, startY, e.getX(), e.getY());
            gc.stroke();
        }
    }

    @Override
    public void executeText(GraphicsContext gc, GraphicsContext gs, KeyEvent e, Color color, int size) {
        //No-op
    }
}
