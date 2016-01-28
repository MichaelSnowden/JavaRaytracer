package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class Vector {
    private double x;
    private double y;
    private double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y, z + v.z);
    }

    public Vector sub(Vector v) {
        return new Vector(x - v.x, y - v.y, z - v.z);
    }

    public double dot(Vector v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public Vector scale(double k) {
        return new Vector(x * k, y * k, z * k);
    }

    public Vector normalize() {
        return scale(1 / Math.sqrt(dot(this)));
    }

    public double distanceTo(Vector v) {
        return Math.sqrt(v.sub(this).dot(v.sub(this)));
    }

    public Vector mix(Vector b, double r) {
        return scale(r).add(b.scale(1 - r));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector cross(Vector v) {
        return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }
}
