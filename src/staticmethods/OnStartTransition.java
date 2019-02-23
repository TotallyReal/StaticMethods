/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author eofir
 */
public class OnStartTransition extends Transition{

    public OnStartTransition(EventHandler<ActionEvent> handler){
        setCycleDuration(Duration.ZERO);
        setOnFinished(handler);
    }
    
    @Override
    protected void interpolate(double frac) {        
    }
    
}
