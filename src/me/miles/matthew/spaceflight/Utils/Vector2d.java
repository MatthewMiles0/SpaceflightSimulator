package me.miles.matthew.spaceflight.Utils;

public class Vector2d {
    // A class to represent a 2D vector
    
    // The x and y coordinates of the vector
    public double x, y;

    // Constructors
    public Vector2d() {
        x = 0;
        y = 0;
    }

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2d(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    // Methods
    public double length() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public Vector2d normalize() {
        double length = length();

        if (length != 0) {
            x /= length;
            y /= length;
        }

        return this;
    }

    public Vector2d normalize(double length) {
        double currentLength = length();

        if (currentLength != 0) {
            x *= length / currentLength;
            y *= length / currentLength;
        }

        return this;
    }

    public Vector2d rotate(double angle) {
        double cos = (double) Math.cos(angle);
        double sin = (double) Math.sin(angle);

        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;

        x = newX;
        y = newY;

        return this;
    }

    public Vector2d add(Vector2d v) {
        x += v.x;
        y += v.y;

        return this;
    }

    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    public Vector2d subtract(Vector2d v) {
        x -= v.x;
        y -= v.y;

        return this;
    }

    public Vector2d subtract(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    public Vector2d multiply(double scalar) {
        x *= scalar;
        y *= scalar;

        return this;
    }
}
