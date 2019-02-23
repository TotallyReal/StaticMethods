/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

import javafx.animation.Animation;
import javafx.scene.Node;

/**
 *
 * @author eofir
 * @param <T>
 */
public interface AnimationCreator<T extends Node> {
    
    public Animation createAnimation(T node);
    
}
