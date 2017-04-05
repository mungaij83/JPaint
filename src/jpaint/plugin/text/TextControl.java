package jpaint.plugin.text;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import jpaint.Command;
import jpaint.UILoader;

public class TextControl extends AnchorPane implements Command {

    private String controlName;
    double startX, startY, stopX, stopY;
    double x, y;
    StringBuilder builder;
    @FXML
    private Button button;
    Font font;
    private boolean consumed;

    public TextControl() {
        this.controlName = "tool.line";
        builder = new StringBuilder("|");
        font = Font.font(14);

        consumed = false;
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
        if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if (builder.length() > 0) { //Draw current content
                gs.clearRect(0, 0, 5000, 5000);
                gc.fillText(builder.toString(), startX, startY);
                builder.delete(0, builder.length()); //Clear string builder
            }
            startX = e.getX();
            startY = e.getY();
        }
    }

    @Override
    public void executeText(GraphicsContext gc, GraphicsContext gs, KeyEvent e, Color color, int size) {
        gs.setFont(font);

        if (e.getCode() == KeyCode.ENTER) {
            gs.clearRect(0, 0, 5000, 5000);
            if (builder.length() > 0) {
                gc.setFont(font);
                gc.moveTo(startX, startY);
                gc.fillText(builder.toString(), startX, startY);
            }
            builder.delete(0, builder.length()); //Clear string builder
        } else if (!e.isMetaDown()) {
            if (e.getCode()==KeyCode.BACK_SPACE) {
                System.out.println("Delete");
                builder.deleteCharAt(builder.length() - 1);
            } else {
                if(e.getCharacter().length()>0)
                    this.builder.append(e.getCharacter());
            }
            //System.out.println(builder.toString());
            gs.clearRect(0, 0, 5000, 5000);
            gs.fillText(builder.toString(), startX, startY);
        } else {
            System.out.println("KEY:" + e.getText());
        }
    }
}
