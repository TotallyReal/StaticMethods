/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package staticmethods;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

/**
 *
 * @author eofir
 */
public class GEOM {
    
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
    
    public static Polygon createPolygon(Point2D... pts){
        if (pts == null)
            return null;
        double ptsCoord[] = new double[pts.length*2];
        for (int i = 0; i < pts.length; i++) {
            ptsCoord[2*i] = pts[i].getX();
            ptsCoord[2*i+1] = pts[i].getY();
        }
        Polygon poly = new Polygon(ptsCoord);        
        return poly;
    }
    
    /**
     * Creates a list of points representing a regular polygon with n vertices.
     *
     * The polygon will be of radius r, and its initial point will be in the
     * given angle (in radians). If n is smaller than 3, then returns a
     * triangle. If the radius is nonpositive, then return a polygon of radius
     * 1.
     *
     * @param n The number of vertices in the polygon
     * @param r The radius of the polygon
     * @param angle The beginning angle in radians
     * @return A list of points for the polygon
     */
    public static Point2D[] regularPolygonPoints(int n, double r, double angle) {
        if (n < 3) {
            n = 3;
        }
        if (r < 1) {
            r = 1;
        }
        Point2D vertices[] = new Point2D[n];
        
        double base = 2*Math.PI/n;
        for (int i = 0; i < n; i++) {
            vertices[i] = new Point2D(r * Math.cos(i*base+angle), r * Math.sin(i*base+angle));
            //temp.mult(root);
        }
        return vertices;
    }
    
    /**
     * Creates a list of points representing a regular polygon with n vertices.
     *
     * The polygon will be of radius r, and its initial point will be (r,0). 
     * If n is smaller than 3, then returns a triangle. If the radius is 
     * nonpositive, then return a polygon of radius 1.
     *
     * @param n The number of vertices in the polygon
     * @param r The radius of the polygon
     * @return A list of points for the polygon
     */
    public static Point2D[] regularPolygonPoints(int n, double r) {
        return regularPolygonPoints(n,r,0);
    }
}
