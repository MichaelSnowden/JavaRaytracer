package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class Matrix {
    double a00;
    double a01;
    double a02;
    double a10;
    double a11;
    double a12;
    double a20;
    double a21;
    double a22;

    public Matrix(double a00, double a01, double a02, double a10, double a11, double a12, double a20, double a21,
                  double a22) {
        this.a00 = a00;
        this.a01 = a01;
        this.a02 = a02;
        this.a10 = a10;
        this.a11 = a11;
        this.a12 = a12;
        this.a20 = a20;
        this.a21 = a21;
        this.a22 = a22;
    }

    public Vector[] getRows() {
        return new Vector[]{
                new Vector(a00, a01, a02),
                new Vector(a10, a11, a12),
                new Vector(a20, a21, a22)
        };
    }

    public Vector[] getCols() {
        return new Vector[]{
                new Vector(a00, a10, a20),
                new Vector(a01, a11, a21),
                new Vector(a02, a12, a22)
        };
    }

    public static Matrix rotationMatrix(double x, double y, double z) {
        Matrix xMatrix = new Matrix(1, 0, 0, 0, Math.cos(x), -Math.sin(x), 0, Math.sin(x), Math.cos(x));
        Matrix yMatrix = new Matrix(Math.cos(y), 0, Math.sin(y), 0, 1, 0, -Math.sin(y), 0, Math.cos(y));
        Matrix zMatrix = new Matrix(Math.cos(z), -Math.sin(z), 0, Math.sin(z), Math.cos(z), 0, 0, 0, 1);
        return zMatrix.multiply(yMatrix).multiply(xMatrix);
    }

    public Matrix multiply(Matrix a) {
        Vector[] rows = getRows();
        Vector[] cols = a.getCols();
        return new Matrix(rows[0].dot(cols[0]), rows[0].dot(cols[1]), rows[0].dot(cols[2]),
                rows[1].dot(cols[0]), rows[1].dot(cols[1]), rows[1].dot(cols[2]),
                rows[2].dot(cols[0]), rows[2].dot(cols[1]), rows[2].dot(cols[2]));
    }

    public Vector multiply(Vector v) {
        Vector[] rows = getRows();
        return new Vector(rows[0].dot(v), rows[1].dot(v), rows[2].dot(v));
    }
}
