package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public interface Shape {
    void intersect(Vector source, Vector direction, HitCallback callback);
}
