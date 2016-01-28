package com.michaelsnowden.raytracer;

import com.michaelsnowden.raytracer.lib.*;
import com.michaelsnowden.raytracer.lib.Shape;
import javafx.scene.input.KeyCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * @author michael.snowden
 */
public class App {
    public static void main(String[] args) {
        final Shape[] shapes = {
                new Sphere(0.1, new Vector(-1, 1, 0), new Vector(0.5, 0.5, 0.5)),
                new Sphere(500, new Vector(0, -500, 0), new Vector(1, 1, 0)),
                new Sphere(1, new Vector(0, 0, 3), new Vector(1, 0, 0)),
                new Sphere(1, new Vector(-2, 1, 4), new Vector(0, 1, 0)),
                new Sphere(1, new Vector(2, 1, 4), new Vector(0, 0, 1)),
                new Triangle(new Vector(0, 0, 2), new Vector(1, 0, 2), new Vector(0, 1, 2), new Vector(1, 0, 1)),
                new Cube(new Vector(0, 0.5, 3), 1, new Vector(0, 1, 1), 0, 0, 0)
        };
        final Vector[] camera = {new Vector(-1, 1, 0)};
        final double[] ambient = {0.2};
        final Vector[] light = {new Vector(2, 2, 0)};
        final Vector backgroundColor = new Vector(0, 0, 0);
        final double[] horizontalAngle = {0};
        final double[] verticalAngle = {0};
        final int depth = 3;

        final JFrame frame = new JFrame() {

            @Override
            public void paint(Graphics g) {
                final BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                for (int x = 0; x < getWidth(); x++) {
                    for (int y = 0; y < getHeight(); y++) {
                        Matrix rotation = Matrix.rotationMatrix(verticalAngle[0], horizontalAngle[0], 0);
                        Vector direction = new Vector(
                                (double) x / Math.min(getWidth(), getHeight()) - 0.5,
                                (double) y / Math.min(getWidth(), getHeight()) - 0.5,
                                1.0)
                                .normalize();
                        direction = rotation.multiply(direction);

                        final Vector color = RayTracer.trace(shapes, ambient[0], light[0], backgroundColor, camera[0],
                                direction, depth);
                        final Color toColor = new Color((int) (color.getX() * 255), (int) (color.getY() * 255), (int)
                                (color.getZ() * 255));
                        image.setRGB(x, getHeight() - y - 1, toColor.getRGB());
                    }
                }
                g.drawImage(image, 0, 0, null);
            }
        };

        Map<Integer, Boolean> keysPressed = new HashMap<>();
        for (KeyCode keyCode : KeyCode.values()) {
            keysPressed.put(keyCode.impl_getCode(), false);
        }

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                keysPressed.put(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                keysPressed.put(e.getKeyCode(), false);
            }
        });

        final Timer timer = new Timer(10, e -> {
            final double deltaPosition = 0.1;
            final double deltaAngle = 0.1;
            if (keysPressed.get(KeyCode.W.impl_getCode())) {
                Matrix rotation = Matrix.rotationMatrix(verticalAngle[0], horizontalAngle[0], 0);
                camera[0] = camera[0].add(rotation.multiply(new Vector(0, 0, deltaPosition)));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.S.impl_getCode())) {
                Matrix rotation = Matrix.rotationMatrix(verticalAngle[0], horizontalAngle[0], 0);
                camera[0] = camera[0].add(rotation.multiply(new Vector(0, 0, -deltaPosition)));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.A.impl_getCode())) {
                Matrix rotation = Matrix.rotationMatrix(verticalAngle[0], horizontalAngle[0], 0);
                camera[0] = camera[0].add(rotation.multiply(new Vector(-deltaPosition, 0, 0)));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.D.impl_getCode())) {
                Matrix rotation = Matrix.rotationMatrix(verticalAngle[0], horizontalAngle[0], 0);
                camera[0] = camera[0].add(rotation.multiply(new Vector(deltaPosition, 0, 0)));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.LEFT.impl_getCode())) {
                horizontalAngle[0] = horizontalAngle[0] - deltaAngle;
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.RIGHT.impl_getCode())) {
                horizontalAngle[0] = horizontalAngle[0] + deltaAngle;
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.UP.impl_getCode())) {
                verticalAngle[0] = verticalAngle[0] - deltaAngle;
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.DOWN.impl_getCode())) {
                verticalAngle[0] = verticalAngle[0] + deltaAngle;
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.SHIFT.impl_getCode()) && keysPressed.get(KeyCode.SPACE.impl_getCode())) {
                camera[0] = camera[0].add(new Vector(0, -deltaPosition, 0));
                frame.repaint();
            }
            if (!keysPressed.get(KeyCode.SHIFT.impl_getCode()) && keysPressed.get(KeyCode.SPACE.impl_getCode())) {
                camera[0] = camera[0].add(new Vector(0, deltaPosition, 0));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.I.impl_getCode())) {
                final Vector v = new Vector(0, 0, deltaPosition);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.K.impl_getCode())) {
                final Vector v = new Vector(0, 0, -deltaPosition);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.J.impl_getCode())) {
                final Vector v = new Vector(-deltaPosition, 0, 0);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.L.impl_getCode())) {
                final Vector v = new Vector(deltaPosition, 0, 0);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.O.impl_getCode())) {
                final Vector v = new Vector(0, -deltaPosition, 0);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
            if (keysPressed.get(KeyCode.P.impl_getCode())) {
                final Vector v = new Vector(0, deltaPosition, 0);
                light[0] = light[0].add(v);
                ((Sphere) shapes[0]).setCenter(((Sphere) shapes[0]).getCenter().add(v));
                frame.repaint();
            }
        });
        timer.start();

//        frame.setUndecorated(true);
        frame.setFocusable(true);
        frame.requestFocus();
        frame.setSize(200, 200);
        frame.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(frame);
    }
}
