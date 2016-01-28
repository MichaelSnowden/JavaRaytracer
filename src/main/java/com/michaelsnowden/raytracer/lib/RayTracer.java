package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public class RayTracer {
    public static Vector trace(Shape[] objects, double ambient, Vector light, Vector background, Vector source, Vector
            direction, int depth) {
        Vector []intersections = {null};
        Vector []normals = {null};
        Vector []colors = {null};
        final double[] minDistance = {Double.MAX_VALUE};
        final HitCallback callback = (intersection, normal, color) -> {
            double distance = source.distanceTo(intersection);
            if (distance < minDistance[0]) {
                minDistance[0] = distance;
                intersections[0] = intersection;
                normals[0] = normal;
                colors[0] = color;
            }
        };

        for (Shape object : objects) {
            object.intersect(source, direction, callback);
        }

        if (minDistance[0] == Double.MAX_VALUE) {
            return background;
        }
        Vector intersection = intersections[0];
        Vector normal = normals[0];
        Vector color = colors[0];

        final double intensity = Math.max(ambient, light.sub(intersection).normalize().dot(normal));
        Vector direct = color.scale(intensity);
        if (depth == 1) {
            return direct;
        } else {
            final Vector bounce = direction.sub(normal.scale(2 * direction.dot(normal)));
            final Vector reflected = trace(objects, ambient, light, background, intersection, bounce, depth - 1);
            return direct.mix(reflected, 0.5 + 0.5 * Math.pow(intensity, 30));
        }
    }
}
