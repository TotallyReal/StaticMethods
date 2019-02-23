/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import javax.imageio.ImageIO;

/**
 *
 * @author eofir
 */
public class MFX {

    public static Duration HALF_SEC = Duration.seconds(0.5);
    public static Duration SECOND_1 = Duration.seconds(1);
    public static Duration SECOND_2 = Duration.seconds(2);
    public static Duration SECOND_3 = Duration.seconds(3);
    public static Duration SECOND_4 = Duration.seconds(4);
    public static Duration SECOND_5 = Duration.seconds(5);
    public static Duration SECOND_6 = Duration.seconds(6);
    public static Duration FAST = Duration.millis(0.1);

    // <editor-fold defaultstate="collapsed" desc="FADE IN">
    public static Transition fadeTo(Node node, double opacity) {
        FadeTransition fade = new FadeTransition();
        fade.setDuration(SECOND_1);
        fade.setNode(node);
        fade.setToValue(opacity);
        return fade;
    }
    
    public static Transition fadeToParallel(double opacity, Node... nodes){
       return MFX.parallel(node->fadeTo(node,opacity), nodes);
    }

    public static FadeTransition fadeIn(Node node) {
        return fadeIn(node, SECOND_1);
    }

    public static FadeTransition fadeIn(Node node, Duration time) {
        FadeTransition fade = new FadeTransition(time, node);
        fade.setFromValue(0);
        fade.setToValue(1);
        return fade;
    }

    public static ParallelTransition fadeInParallel(Collection <? extends Node> nodes){
        if (nodes==null)
            return null;
        return fadeInParallel(nodes.toArray(new Node[]{}));
    }

    public static ParallelTransition fadeInParallel(Node... nodes) {
        return fadeInParallel(SECOND_1, nodes);
    }

    public static ParallelTransition fadeInParallel(Duration time, Node... nodes) {
        ParallelTransition pt = new ParallelTransition();
        ObservableList<Animation> kids = pt.getChildren();
        for (Node node : nodes) {
            kids.add(fadeIn(node, time));
        }
        return pt;
    }

    public static SequentialTransition fadeInSequential(Node... nodes) {
        SequentialTransition st = new SequentialTransition();
        ObservableList<Animation> kids = st.getChildren();
        for (Node node : nodes) {
            kids.add(fadeIn(node));
        }
        return st;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="FADE OUT">
    public static FadeTransition fadeOut(Node node) {
        return fadeOut(node, SECOND_1);
    }

    public static FadeTransition fadeOut(Node node, Duration time) {
        FadeTransition fade = new FadeTransition(time, node);
        fade.setFromValue(node.getOpacity());
        fade.setToValue(0);
        return fade;
    }

    public static ParallelTransition fadeOutParallel(Collection <? extends Node> nodes){
        if (nodes==null)
            return null;
        return fadeOutParallel(nodes.toArray(new Node[]{}));
    }
    
    public static ParallelTransition fadeOutParallel(Node... nodes) {
        ParallelTransition pt = new ParallelTransition();
        ObservableList<Animation> kids = pt.getChildren();
        for (Node node : nodes) {
            kids.add(fadeOut(node));
        }
        return pt;
    }

    
    public static SequentialTransition fadeOutSequential(Collection <? extends Node> nodes){
        if (nodes==null)
            return null;
        return fadeOutSequential(nodes.toArray(new Node[]{}));
    }
    
    public static SequentialTransition fadeOutSequential(Node... nodes) {
        SequentialTransition st = new SequentialTransition();
        ObservableList<Animation> kids = st.getChildren();
        for (Node node : nodes) {
            kids.add(fadeOut(node));
        }
        return st;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="MOVE NODE">
    public static TranslateTransition moveNode(Node node, Duration time, double byX, double byY) {
        TranslateTransition tt = new TranslateTransition(time, node);
        tt.setByX(byX);
        tt.setByY(byY);
        return tt;
    }

    public static TranslateTransition moveNode(Node node, double byX, double byY) {
        return moveNode(node, SECOND_1, byX, byY);
    }

    public static TranslateTransition moveNodeFrom(Node node, Node fromNode, Node jointParent) {
        return moveNodeFrom(node, SECOND_1, fromNode, jointParent);
    }

    public static Transition moveToNode(Node node, Node toNode) {
        TranslateTransition tt = new TranslateTransition(MFX.SECOND_1, node);
        Animation quick = MFX.quickAction(evt -> {
            Point2D from = node.localToScene(0, 0);
            Point2D to = toNode.localToScene(0, 0);
            tt.setByX(to.getX() - from.getX());
            tt.setByY(to.getY() - from.getY());
        });
        return new SequentialTransition(quick,tt);
    }


    public static TranslateTransition moveFromNode(Node node, Node fromNode) {
        TranslateTransition tt = new TranslateTransition(MFX.SECOND_1, node);
        Point2D from = fromNode.localToScene(0, 0);
        Point2D to = node.localToScene(0, 0);
        tt.setFromX(from.getX() - to.getX());
        tt.setToX(0);
        tt.setFromY(from.getY() - to.getY());
        tt.setToY(0);
        return tt;
    }

    public static TranslateTransition moveNodeFrom(Node node, Duration time, Node fromNode, Node jointParent) {
        TranslateTransition tt = new TranslateTransition(time, node);
//        Node temp = fromNode;
//        double fromX = 0, fromY = 0;
//        while (temp != jointParent) {
//            if (temp == null) {
//                return null;
//            }
//            fromX += temp.getLayoutX();
//            fromY += temp.getLayoutY();
//            temp = temp.getParent();
//        }
//        temp = node;
//        double toX = 0, toY = 0;
//        while (temp != jointParent) {
//            if (temp == null) {
//                return null;
//            }
//            toX += temp.getLayoutX();
//            toY += temp.getLayoutY();
//            temp = temp.getParent();
//        }
//        tt.setFromX(fromX - toX);
//        tt.setToX(0);
//        tt.setFromY(fromY - toY);
//        tt.setToY(0);
        //return tt;
        Point2D from = fromNode.localToScene(0, 0);
        Point2D to = node.localToScene(0, 0);
        tt.setByX(to.getX() - from.getX());
        tt.setByY(to.getY() - from.getY());
        return tt;
    }

    public static Transition moveNodeFrom(Node node, double fromX, double fromY) {
        return moveNodeFrom(node, SECOND_1, fromX, fromY);
    }

    public static Transition moveNodeFrom(Node node, Duration time, double fromX, double fromY) {
        TranslateTransition tt = new TranslateTransition(time, node);
        tt.setFromX(fromX);
        tt.setToX(node.getLayoutX());
        tt.setFromY(fromY);
        tt.setToY(node.getLayoutY());
        return tt;
    }

    public static void toEnd(Animation anim) {
        if (anim != null) {
            anim.jumpTo(anim.getTotalDuration());
        }
    }

    public static Transition moveNodeTo(Node node, Duration time, double toX, double toY) {
        return moveNode(node, time, toX - node.getLayoutX(), toY - node.getLayoutY());
    }

    public static Transition moveNodeTo(Node node, double toX, double toY) {
        return moveNode(node, toX - node.getLayoutX() - node.getTranslateX(),
                toY - node.getLayoutY() - node.getTranslateY());
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="SCALE">
    public static Timeline scaleTo(Node node, double toX, double toY,
            double pivotX, double pivotY) {
        return scaleTo(node, SECOND_1, node.getScaleX(), node.getScaleY(), toX, toY, pivotX, pivotY);
    }

    public static Timeline scaleTo(Node node, Duration time,
            double toX, double toY,
            double pivotX, double pivotY) {
        return scaleTo(node, time, node.getScaleX(), node.getScaleY(), toX, toY, pivotX, pivotY);
    }

    public static Timeline scaleTo(Node node,
            double fromX, double fromY,
            double toX, double toY,
            double pivotX, double pivotY) {
        return scaleTo(node, SECOND_1, fromX, fromY, toX, toY, pivotX, pivotY);
    }

    public static Timeline scaleTo(Node node, Duration time,
            double fromX, double fromY,
            double toX, double toY,
            double pivotX, double pivotY) {
        Scale scale = new Scale();
        scale.setPivotX(pivotX);
        scale.setPivotY(pivotY);
        node.getTransforms().add(scale);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(scale.xProperty(), fromX)),
                new KeyFrame(Duration.ZERO, new KeyValue(scale.yProperty(), fromY)),
                new KeyFrame(time, new KeyValue(scale.xProperty(), toX)),
                new KeyFrame(time, new KeyValue(scale.yProperty(), toY))
        );
        return timeline;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Move Node ????">
//    public Transition moveNodeTo(Node node, Duration time, double toX, double toY) {
//        return moveNode(node, time, toX - node.getLayoutX(), toY - node.getLayoutY());
//    }
//
//    public Transition moveNodeTo(Node node, double toX, double toY) {
//        return moveNode(node, toX - node.getLayoutX() - node.getTranslateX(),
//                toY - node.getLayoutY() - node.getTranslateY());
//    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Scaling">
    public Transition shrinkY(Node node) {
        return scaleTo(node, 1, 0);
    }

    public Transition shrinkX(Node node) {
        return scaleTo(node, 0, 1);
    }

    /**
     * Scales the given node to times (xEmph,yEmph) in the x and y direction,
     * and then scales back.
     *
     * @param node
     * @param xEmph
     * @param yEmph
     * @return
     */
    public static Transition scaleEmphasis(Node node, double xEmph, double yEmph) {
        return new SequentialTransition(
                MFX.scaleTo(node, xEmph, yEmph),
                MFX.scaleTo(node, 1, 1)
        );
    }

    /**
     * Scales the given node to times (xEmph,yEmph) in the x and y direction,
     * and then scales back.
     *
     * @param node
     * @param xEmph
     * @param yEmph
     * @return
     */
    public static Transition scaleEmphasisAll(double xEmph, double yEmph, Node... nodes) {
        ParallelTransition emphUp = new ParallelTransition();
        ParallelTransition emphDown = new ParallelTransition();
        ObservableList<Animation> kidsUp = emphUp.getChildren();
        ObservableList<Animation> kidsDown = emphDown.getChildren();
        for (Node node : nodes) {
            kidsUp.add(MFX.scaleTo(node, xEmph, yEmph));
            kidsDown.add(MFX.scaleTo(node, 1, 1));
        }
        return new SequentialTransition(
                emphUp, emphDown
        );
    }

    public static Transition scaleTo(Node node, double x, double y) {
        return scaleTo(node, SECOND_1, x, y);
    }

    public static Transition scaleTo(Node node, Duration time, double x, double y) {
        ScaleTransition st = new ScaleTransition(time, node);
        st.setToY(y);
        st.setToX(x);
        return st;
    }

    public static final int LEFT_SIDE = 0;
    public static final int RIGHT_SIDE = 1;
    public static final int UP_SIDE = 2;
    public static final int DOWN_SIDE = 3;

    public Transition appearFrom(Node node, int side) {
        Bounds bounds = node.getBoundsInLocal();
        Rectangle mask = new Rectangle(bounds.getMinX(), bounds.getMinY(), bounds.getWidth(), bounds.getHeight());
        double x = 0, y = 0;
        switch (side) {
            case LEFT_SIDE:
                x = mask.getWidth();
                break;
            case RIGHT_SIDE:
                x = -mask.getWidth();
                break;
            case UP_SIDE:
                y = mask.getHeight();
                break;
            case DOWN_SIDE:
                x = -mask.getHeight();
                break;
        }
        mask.setTranslateX(-x);
        mask.setTranslateY(-y);
        node.setClip(mask);
        node.setOpacity(1);
        return moveNode(mask, x, y);
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Foldings">
    public Transition unfoldY(Node node) {
        ScaleTransition st = new ScaleTransition(SECOND_1, node);
        st.setFromY(0);
        st.setToY(1);
        return st;
    }

    public static final int FOLD_CENTER = 0;
    public static final int FOLD_LEFT = 1;
    public static final int FOLD_RIGHT = 2;
    public static final int FOLD_UP = 3;
    public static final int FOLD_DOWN = 4;
    public static final int FOLD_CENTER_VERTICAL = 5;
    public static final int FOLD_CENTER_HOR = 6;

    public Transition foldNode(Node node, int direction) {
        return foldNode(node, SECOND_1, direction);
    }

    public Transition foldNode(Node node, Duration time, int direction) {
        Transition shrink = null;
        Transition trans = null;
        switch (direction) {
            case FOLD_CENTER:
                return scaleTo(node, time, 0, 0);
            case FOLD_CENTER_VERTICAL:
                return scaleTo(node, time, 1, 0);
            case FOLD_CENTER_HOR:
                return scaleTo(node, time, 0, 1);
            case FOLD_UP:
                shrink = scaleTo(node, time, 1, 0);
                trans = moveNode(node, time, 0, -node.getBoundsInLocal().getHeight() / 2);
                break;
            case FOLD_DOWN:
                shrink = scaleTo(node, time, 1, 0);
                trans = moveNode(node, time, 0, node.getBoundsInLocal().getHeight() / 2);
                break;
            case FOLD_LEFT:
                shrink = scaleTo(node, time, 0, 1);
                trans = moveNode(node, time, -node.getBoundsInLocal().getWidth() / 2, 0);
                break;
            case FOLD_RIGHT:
                shrink = scaleTo(node, time, 0, 1);
                trans = moveNode(node, time, node.getBoundsInLocal().getWidth() / 2, 0);
                break;
        }
        return new ParallelTransition(
                shrink, trans
        );
    }

    public Transition unfoldNode(Node node, int direction) {
        return unfoldNode(node, SECOND_1, direction);
    }

    public static final int UNFOLD_CENTER = 0;
    public static final int UNFOLD_LEFT = 1;
    public static final int UNFOLD_RIGHT = 2;
    public static final int UNFOLD_UP = 3;
    public static final int UNFOLD_DOWN = 4;

    public Transition unfoldNode(Node node, Duration time, int direction) {
        Transition unfold = scaleTo(node, time, 1, 1);
        Transition trans = null;
        double width = node.getBoundsInLocal().getWidth();
        double height = node.getBoundsInLocal().getHeight();
        node.setOpacity(1);
        switch (direction) {
            case UNFOLD_CENTER:
                node.setScaleX(0);
                node.setScaleY(0);
                return unfold;
            case UNFOLD_RIGHT:
                node.setScaleX(0);
                node.setTranslateX(-width / 2);
                trans = moveNode(node, time, width / 2, 0);
                break;
            case UNFOLD_LEFT:
                node.setScaleX(0);
                node.setTranslateX(width / 2);
                trans = moveNode(node, time, -width / 2, 0);
                break;
            case UNFOLD_DOWN:
                node.setScaleY(0);
                node.setTranslateY(-height / 2);
                trans = moveNode(node, time, 0, height / 2);
                break;
            case UNFOLD_UP:
                node.setScaleY(0);
                node.setTranslateY(height / 2);
                trans = moveNode(node, time, 0, -height / 2);
                break;
        }
        return new ParallelTransition(
                unfold, trans
        );
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Color">
    public Transition colorFillTransition(Shape shape, Color color) {
        return colorFillTransition(shape, SECOND_1, color);
    }

    public Transition colorFillTransition(Shape shape, Duration time, Color color) {
        FillTransition ft = new FillTransition(time, shape);
        ft.setToValue(color);
        return ft;
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="MISC">
     public static Animation movePane(Pane pane, double targetSize, double x, double y) {
        Bounds bb = pane.getBoundsInLocal();
        double xx = bb.getMinX();
        double yy = bb.getMinY();
        Point2D lu = new Point2D(xx, yy);
        Point2D local = pane.parentToLocal(new Point2D(x, y));
        double height = pane.getHeight();
        double scale = targetSize / height;
        if (scale != 1) {
            double d = 1 / (1 - scale);
            Point2D target = lu.add(local.subtract(lu).multiply(d));
            return MFX.scaleTo(pane, scale, scale, target.getX(), target.getY());//(1-d)*xx+d*x, (1-d)*yy+d*y);
        } else {
            return MFX.moveNode(pane, xx - x, yy - y);
        }
    }
     
     public static Animation movePane(Parent pane, double targetSize, double x, double y) {
        Bounds bb = pane.getBoundsInLocal();
        double xx = bb.getMinX();
        double yy = bb.getMinY();
        Point2D lu = new Point2D(xx, yy);
        Point2D local = pane.parentToLocal(new Point2D(x, y));
        double height = pane.getBoundsInParent().getHeight();
        double scale = targetSize / height;
        if (scale != 1) {
            double d = 1 / (1 - scale);
            Point2D target = lu.add(local.subtract(lu).multiply(d));
            return MFX.scaleTo(pane, scale, scale, target.getX(), target.getY());//(1-d)*xx+d*x, (1-d)*yy+d*y);
        } else {
            return MFX.moveNode(pane, xx - x, yy - y);
        }
    }
    
    public static void hideNodes(Node... nodes) {
        for (Node node : nodes) {
            node.setOpacity(0);
        }
    }

    public static void hideNodes(Collection<Node> nodes) {
        for (Node node : nodes) {
            node.setOpacity(0);
        }
    }

    public static Transition reverseTransitionOld(Transition t) {
        t.jumpTo(t.getTotalDuration());
        t.setRate(-t.getRate());
        return t;
    }

    public static Transition reverseTransition(Transition t) {
//        t.setInterpolator(new ReverseInterpolator(t.getInterpolator()));
        t.setInterpolator(new ReverseInterpolator(t.getInterpolator()));
//        t.jumpTo(t.getTotalDuration());
//        t.setRate(-t.getRate());
        return t;
    }

    public static Transition waitFor(double seconds) {
        return new PauseTransition(Duration.seconds(seconds));
    }

    public static Transition quickAction(EventHandler<ActionEvent> handler) {
        return new Transition() {

            {
                setCycleDuration(Duration.ZERO);
                setOnFinished(handler);
            }

            @Override
            protected void interpolate(double frac) {
            }

        };
    }
    
    public static Transition oneAction(OneMethod oneMethod){
        if (oneMethod==null)
            return null;
        return new Transition() {

            boolean needToRun;
            
            {
                setCycleDuration(Duration.ZERO);
                needToRun = true;
            }

            @Override
            protected void interpolate(double frac) {
                if (needToRun){
                    oneMethod.method();
                    needToRun = false;
                }
            }

        };
    }

    public static Transition setOnStart(Animation anim, EventHandler<ActionEvent> handler) {
        if (anim == null || handler == null) {
            return null;
        }
        return new SequentialTransition(quickAction(handler), anim);
    }

    // </editor-fold>
    public static Animation sequential(AnimationCreator ac, Node... nodes) {

        SequentialTransition st = new SequentialTransition();
        ObservableList<Animation> kids = st.getChildren();
        if (nodes == null || ac == null) {
            return st;
        }
        for (Node node : nodes) {
            kids.add(ac.createAnimation(node));
        }
        return st;
    }

    public static Animation parallel(AnimationCreator ac, Collection<? extends Node> nodes) {
        if (nodes==null)
            return null;
        return parallel(ac,nodes.toArray(new Node[]{}));
    }
    
    public static Transition parallel(AnimationCreator ac, Node... nodes) {

        ParallelTransition st = new ParallelTransition();
        ObservableList<Animation> kids = st.getChildren();
        if (nodes == null || ac == null) {
            return st;
        }
        for (Node node : nodes) {
            kids.add(ac.createAnimation(node));
        }
        return st;
    }

    // <editor-fold defaultstate="collapsed" desc="Object creation">
    public static Rectangle createBackground(Pane pane, Color color, double insets) {
        Bounds bb = pane.getBoundsInLocal();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(color);
        pane.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    public static Rectangle createBackground(Node node, Pane parent, Color color, double insets) {
        Bounds bb = node.getBoundsInParent();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(color);
        parent.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    /**
     * Creates a rectangular frame around the node.
     *
     * @param node
     * @param parent
     * @param frameColor
     * @param fillColor
     * @param frameWidth
     * @param insets
     * @return
     */
    public static Rectangle createFrame(Node node, Pane parent,
            Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = node.getBoundsInParent();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        parent.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    /**
     * Creates a rectangular frame around the node.
     *
     * @param node
     * @param parent
     * @param frameColor
     * @param fillColor
     * @param frameWidth
     * @param insets
     * @return
     */
    public static Rectangle createFrame(Node node, Group parent,
            Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = node.getBoundsInParent();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        parent.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    public static Rectangle createFrame(Pane pane, Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = pane.getBoundsInLocal();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        pane.getChildren().add(rect);
        rect.toBack();
        return rect;
    }
    

    public static Rectangle createFrame(Group pane, Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = pane.getBoundsInLocal();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        pane.getChildren().add(rect);
        rect.toBack();
        return rect;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Object manipulation">

    

    // </editor-fold>
    public static Animation setTotalTime(Animation anim, double millis) {
        if (anim == null || millis <= 0) {
            return null;
        }
        anim.setRate(anim.getTotalDuration().toMillis() / millis);
        return anim;
    }
}

class ReverseInterpolator extends Interpolator {

    private final Interpolator original;

    ReverseInterpolator(Interpolator original) {
        this.original = original;
    }

    @Override
    protected double curve(double t) {
        System.out.println("t=" + t);
        double tt = original.interpolate(0d, 1d, 1 - t);
        System.out.println("-----tt=" + tt);
        System.out.println("t+tt=" + (t + tt));
        return tt;
    }

}
