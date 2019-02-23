/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 *
 * @author eofir
 */
public class SnapshotCamera {

    boolean addFrame = false;
    Color fillColor = Color.TRANSPARENT;
    Color frameColor = Color.TRANSPARENT;
    double frameWidth = 3;
    double insets = 10;

    private double minX = 0, minY = 0, width = 100, height = 100;

    public SnapshotCamera() {

    }

    public void setCameraBounds(double minX, double minY, double width, double height) {
        this.minX = minX;
        this.minY = minY;
        this.width = width;
        this.height = height;
        System.out.println("minX =" + minX + ", minY=" + minY + ", width=" + width + ", height=" + height);
    }

    public void setCameraBounds(Bounds b) {
        setCameraBounds(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
    }

    public void setCameraBounds(Node node) {
        setCameraBounds(node.getBoundsInParent());
    }

    public RenderedImage takeSnapshot(Group group) {
        if (group == null) {
            return null;
        }
        Rectangle frame = null;
        if (addFrame) {
            frame = createFrame(group);
        }
        WritableImage writableImage = new WritableImage((int) width,
                (int) height);
        SnapshotParameters param = new SnapshotParameters();
        param.setFill(Color.TRANSPARENT);

        param.setViewport(new Rectangle2D(minX, minY, width, height));
        group.snapshot(param, writableImage);
        if (addFrame) {
            removeFrame(group, frame);
        }
        return SwingFXUtils.fromFXImage(writableImage, null);
    }

    public void saveSnapshot(Group group) {
        if (group == null) {
            return;
        }
        Rectangle frame = null;

        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png files (*.png)", "*.png"));

        //Prompt user to select a file
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                RenderedImage renderedImage = takeSnapshot(group);
                //Write the snapshot to the chosen file
                ImageIO.write(renderedImage, "png", file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void addFrame(boolean add) {
        addFrame = add;
    }

    public void setInsets(double insets) {
        if (insets >= 0) {
            this.insets = insets;
        }
    }

    public void setFrameColor(Color color) {
        frameColor = (color == null) ? Color.TRANSPARENT : color;
    }

    /**
     * Set the background color of the frame
     *
     * @param color The background color of the frame
     */
    public void setBackgraoundColor(Color color) {
        fillColor = (color == null) ? Color.TRANSPARENT : color;
    }

    public void setFrameWidth(double width) {
        if (width >= 0) {
            frameWidth = width;
        }
    }

    private double arc = 0;

    public void setFrameArc(double arc) {
        if (arc >= 0) {
            this.arc = arc;
        }
    }

    private Rectangle createFrame(Group group) {
        Point2D min = group.parentToLocal(minX - (insets + frameWidth / 2), minY - (insets + frameWidth / 2));
        Point2D max = group.parentToLocal(minX + width + insets + frameWidth / 2, minY + height + insets + frameWidth / 2);
        Rectangle rect = new Rectangle(min.getX(), min.getY(), max.getX() - min.getX(), max.getY() - min.getY());
        rect.setFill(fillColor);
        rect.setStroke(frameColor);
        rect.setStrokeWidth(frameWidth);
        rect.setArcHeight(arc);
        rect.setArcWidth(arc);
        group.getChildren().add(rect);
        rect.toBack();
        double extra = (insets + frameWidth);
        minX -= extra;
        minY -= extra;
        width += 2 * extra;
        height += 2 * extra;
        return rect;
    }

    private void removeFrame(Group group, Rectangle frame) {
        group.getChildren().remove(frame);
        double extra = (insets + frameWidth);
        minX += extra;
        minY += extra;
        width -= 2 * extra;
        height -= 2 * extra;
    }

    public void zoomBy(double d) {
        if (d<=0)
            return;
        
        minX = minX/d + (minX+width/2)*(d-1)/d;
        minY = minY/d + (minY+height/2)*(d-1)/d;
        width/=d;
        height/=d;
    }

}
