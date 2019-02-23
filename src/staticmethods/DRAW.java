/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.util.Duration;

/**
 *
 * @author eofir
 */
public class DRAW {
    
    public static Animation drawLine(Line line) {
        if (line == null) {
            return null;
        }
        double dx = line.getEndX()-line.getStartX();
        double dy = line.getEndY()-line.getStartY();
        double length = Math.sqrt(dx*dx + dy*dy);
        Timeline tl = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(line.endXProperty(), line.getStartX()),
                        new KeyValue(line.endYProperty(), line.getStartY()),
                        new KeyValue(line.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.millis(1), new KeyValue(line.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(length),
                        new KeyValue(line.endXProperty(), line.getEndX()),
                        new KeyValue(line.endYProperty(), line.getEndY())
                )
        );
        return tl;
    }
    
    public static Animation drawArc(Arc arc){
        if (arc == null)
            return null;
        double length = arc.getLength();
        double time = 100*arc.getRadiusX()*length/360; //circle of radius 10 takes 1 second to draw
        Timeline tl = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new KeyValue(arc.lengthProperty(), 0),
                        new KeyValue(arc.opacityProperty(), 0)
                ),
                new KeyFrame(Duration.millis(1), new KeyValue(arc.opacityProperty(), 1)),
                new KeyFrame(Duration.millis(time),
                        new KeyValue(arc.lengthProperty(), length)
                )
        );
        return tl;
    }
    
}
