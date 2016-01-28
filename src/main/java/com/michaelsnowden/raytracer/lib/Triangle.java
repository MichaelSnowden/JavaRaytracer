package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class Triangle implements Shape {
    private final Vector v0;
    private final Vector v1;
    private final Vector v2;
    private final Vector color;

    public Triangle(Vector v0, Vector v1, Vector v2, Vector color) {
        this.v0 = v0;
        this.v1 = v1;
        this.v2 = v2;
        this.color = color;
    }

    @Override
    public void intersect(Vector p, Vector d, HitCallback callback) {
        Vector e1 = v1.sub(v0);
        Vector e2 = v2.sub(v0);

        Vector h = d.cross(e2);
        double a = e1.dot(h);

        if (a > -0.00001 && a < 0.00001)
            return;

        double f = 1 / a;

        Vector s = p.sub(v0);
        double u = f * s.dot(h);

        if (u < 0.0 || u > 1.0)
            return;

        Vector q = s.cross(e1);
        double v = f * d.dot(q);

        if (v < 0.0 || u + v > 1.0)
            return;

        double t = f * e2.dot(q);

        if (t > 0.00001)
            callback.receive(p.add(d.scale(t)), e1.cross(e2), color);
    }
}
