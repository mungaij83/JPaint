/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
/**
 *
 * @author njoroge
 */

public interface Command {
    void execute(GraphicsContext gc, GraphicsContext gs,MouseEvent e, Color color, int sizeTool);
    String getName();
    void setAction(EventHandler<ActionEvent> value);
    void executeText(GraphicsContext gc, GraphicsContext gs,KeyEvent e, Color color, int size);
}