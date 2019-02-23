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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author eofir
 */
public class MOB {

    public static Rectangle addFrame(Group group, double r, Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = group.getBoundsInLocal();
        double x = bb.getMinX() + bb.getWidth() / 2;
        double y = bb.getMinY() + bb.getHeight() / 2;
        Rectangle rect = new Rectangle(x - r, y - r, 2 * r, 2 * r);
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        group.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    public static Rectangle addFrame(Group group, Color frameColor, Color fillColor, double frameWidth, double insets) {
        Bounds bb = group.getBoundsInLocal();
        Rectangle rect = new Rectangle(bb.getMinX() - insets, bb.getMinY() - insets,
                2 * insets + bb.getWidth(), 2 * insets + bb.getHeight());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(5);
        rect.setArcWidth(5);
        group.getChildren().add(rect);
        rect.toBack();
        return rect;
    }

    public static void centerNodeAt(Node node, double x, double y) {
        Bounds bb = node.getBoundsInParent();
        double xx = bb.getMinX() + bb.getWidth() / 2;
        double yy = bb.getMinY() + bb.getHeight() / 2;
        node.setLayoutX(node.getLayoutX() + x - xx);
        node.setLayoutY(node.getLayoutY() + y - yy);
    }

    /**
     * Center the node in its parent
     *
     * @param node
     */
    public static void centerNode(Node node) {
        Parent parent = node.getParent();
        double height = parent.getBoundsInLocal().getHeight();
        double width = parent.getBoundsInLocal().getWidth();
        centerNodeAt(node, width / 2, height / 2);
    }

    public static void setOpacity(double opacity, Node... nodes) {
        for (Node node : nodes) {
            node.setOpacity(opacity);
        }
    }

    public static void setOpacity(double opacity, Collection<Node> nodes) {
        for (Node node : nodes) {
            node.setOpacity(opacity);
        }
    }

    public static Line[] createPolygonLines(Point2D pts[]) {
        return MOB.createPolygonLines(pts, 2);
    }

    public static Line[] createPolygonLines(Point2D pts[], double width) {
        if (width < 0) {
            width = 1;
        }
        if (pts == null) {
            return null;
        }
        if (pts.length < 2) {
            return null;
        }
        Line[] lines = new Line[pts.length];
        for (int i = 1; i < lines.length; i++) {
            lines[i] = new Line(pts[i - 1].getX(), pts[i - 1].getY(), pts[i].getX(), pts[i].getY());
            lines[i].setStrokeWidth(width);
        }
        lines[0] = new Line(pts[pts.length - 1].getX(), pts[pts.length - 1].getY(), pts[0].getX(), pts[0].getY());
        lines[0].setStrokeWidth(width);
        return lines;
    }

    public static Polygon createPolygon(Point2D... pts) {
        if (pts == null) {
            return null;
        }
        double ptsCoord[] = new double[pts.length * 2];
        for (int i = 0; i < pts.length; i++) {
            ptsCoord[2 * i] = pts[i].getX();
            ptsCoord[2 * i + 1] = pts[i].getY();
        }
        Polygon poly = new Polygon(ptsCoord);
        return poly;
    }

    // <editor-fold defaultstate="collapsed" desc="SNAPSHOT">
    public static void captureAndSaveDisplay(Group group) {
        Bounds bb = group.getBoundsInParent();
        captureAndSaveDisplay(group, bb.getMinX(), bb.getMinY(), bb.getWidth(), bb.getHeight());
    }

    public static void captureAndSaveDisplay(Group pane, double minX, double minY, double width, double height) {
        if (pane == null) {
            return;
        }
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                //Pad the capture area
                WritableImage writableImage = new WritableImage((int) width,
                        (int) height);
                SnapshotParameters param = new SnapshotParameters();
                param.setFill(Color.TRANSPARENT);
                Rectangle2D rect = new Rectangle2D(minX, minY, width, height);
                param.setViewport(new Rectangle2D(minX, minY, width, height));

                pane.snapshot(param, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    // </editor-fold>
    private static class DragContext {

        double mouseAnchorX, mouseAnchorY, initialTranslateX, initialTranslateY;
    }

    public static void makeDragable(Node node) {
        final DragContext dragContext = new DragContext();
        EventHandler<MouseEvent> onMousePressed = (EventHandler<MouseEvent>) node.getOnMousePressed();
        node.setOnMousePressed((MouseEvent mouseEvent) -> {
            if (onMousePressed!=null)
                onMousePressed.handle(mouseEvent);
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            dragContext.mouseAnchorX = mouseEvent.getSceneX();
            dragContext.mouseAnchorY = mouseEvent.getSceneY();
            dragContext.initialTranslateX
                    = node.getTranslateX();
            dragContext.initialTranslateY
                    = node.getTranslateY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            if (mouseEvent.getButton() != MouseButton.PRIMARY) {
                return;
            }
            node.setTranslateX(
                    dragContext.initialTranslateX
                    + mouseEvent.getSceneX()
                    - dragContext.mouseAnchorX);
            node.setTranslateY(
                    dragContext.initialTranslateY
                    + mouseEvent.getSceneY()
                    - dragContext.mouseAnchorY);
        });

    }
}
