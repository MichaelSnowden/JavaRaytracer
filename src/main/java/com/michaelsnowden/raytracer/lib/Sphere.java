package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class Sphere implements Shape {
    private Vector color;
    private double radius;
    private Vector center;

    public Sphere(double radius, Vector center, Vector color) {
        this.radius = radius;
        this.center = center;
        this.color = color;
    }

    public double getRadius() {
        return radius;
    }

    public Vector getCenter() {
        return center;
    }

    public void setCenter(Vector center) {
        this.center = center;
    }

    @Override
    public void intersect(Vector source, Vector direction, HitCallback callback) {
        final Vector v = source.sub(getCenter());
        final double b = -v.dot(direction);
        double v2 = v.dot(v);
        double r2 = getRadius() * getRadius();
        double d2 = b * b - v2 + r2;
        if (d2 > 0) {
            double distance;
            if (b - Math.sqrt(d2) > 0.01) {
                distance = b - Math.sqrt(d2);
            } else if (b + Math.sqrt(d2) > 0.01) {
                distance = b + Math.sqrt(d2);
            } else {
                return;
            }
            Vector intersection = source.add(direction.scale(distance));
            Vector normal = intersection.sub(center).normalize();
            callback.receive(intersection, normal, color);
        }
    }
}
