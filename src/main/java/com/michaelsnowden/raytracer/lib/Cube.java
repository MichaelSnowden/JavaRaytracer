package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class Cube implements Shape {
    private Vector center;
    private double width;
    private final Vector color;
    private final double x;
    private final double y;
    private final double z;
    private Shape[] shapes;

    public Cube(Vector center, double width, Vector color, double x, double y, double z) {
        this.center = center;
        this.width = width;
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
        double width2 = width / 2;
        Vector v0 = new Vector(center.getX() - width2, center.getY() - width2, center.getZ() - width2);
        Vector v1 = new Vector(center.getX() - width2, center.getY() - width2, center.getZ() + width2);
        Vector v2 = new Vector(center.getX() - width2, center.getY() + width2, center.getZ() - width2);
        Vector v3 = new Vector(center.getX() - width2, center.getY() + width2, center.getZ() + width2);
        Vector v4 = new Vector(center.getX() + width2, center.getY() - width2, center.getZ() - width2);
        Vector v5 = new Vector(center.getX() + width2, center.getY() - width2, center.getZ() + width2);
        Vector v6 = new Vector(center.getX() + width2, center.getY() + width2, center.getZ() - width2);
        Vector v7 = new Vector(center.getX() + width2, center.getY() + width2, center.getZ() + width2);
        Triangle t0 = new Triangle(v0, v1, v2, color);
        Triangle t1 = new Triangle(v1, v3, v2, color);
        Triangle t2 = new Triangle(v4, v0, v6, color);
        Triangle t3 = new Triangle(v6, v0, v2, color);
        Triangle t4 = new Triangle(v2, v3, v6, color);
        Triangle t5 = new Triangle(v3, v7, v6, color);
        Triangle t6 = new Triangle(v1, v5, v3, color);
        Triangle t7 = new Triangle(v5, v7, v3, color);
        Triangle t8 = new Triangle(v0, v4, v1, color);
        Triangle t9 = new Triangle(v4, v5, v1, color);
        Triangle t10 = new Triangle(v5, v4, v6, color);
        Triangle t11 = new Triangle(v6, v7, v5, color);
        shapes = new Shape[]{t0, t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11};
    }

    @Override
    public void intersect(Vector source, Vector direction, HitCallback callback) {
        for (Shape shape : getTriangles()) {
            shape.intersect(source, direction, callback);
        }
    }

    public Shape[] getTriangles() {
        return shapes;
    }
}
