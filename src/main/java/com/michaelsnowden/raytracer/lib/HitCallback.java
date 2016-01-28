package com.michaelsnowden.raytracer.lib;

/**
 * @author michael.snowden
 */
public interface HitCallback {
    void receive(Vector intersection, Vector normal, Vector color);
}
