package me.miles.matthew.spaceflight.Utils;

public class Vector2d {
    // A class to represent a 2D vector
    
    // The x and y coordinates of the vector
    public double x, y;

    // Constructors

    /**
     * Creates a new zero vector
     */
    public Vector2d() {
        x = 0;
        y = 0;
    }

    /**
     * Creates a new vector with the given coordinates
     * @param x The x coordinate
     * @param y The y coordinate
     */
    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates a new vector with the same coordinates as the given vector
     * @param v The vector to copy
     */
    public Vector2d(Vector2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    // Methods

    /**
     * Get the length of the vector
     */
    public double length() {
        return (double) Math.sqrt(x * x + y * y);
    }

    /**
     * Make the length of the vector 1 without changing direction
     */
    public Vector2d normalize() {
        double length = length();

        if (length != 0) {
            x /= length;
            y /= length;
        }

        return this;
    }

    /**
     * Resize the vector to a certain length without changing direction. Modifies the vector.
     * @param length The length to resize to
     * @return The resized vector
     */
    public Vector2d normalize(double length) {
        double currentLength = length();

        if (currentLength != 0) {
            x *= length / currentLength;
            y *= length / currentLength;
        }

        return this;
    }

    /**
     * Rotate the vector by a certain angle. Modifies the vector.
     * @param angle The angle to rotate by
     * @return The rotated vector
     */
    public Vector2d rotate(double angle) {
        double cos = (double) Math.cos(angle);
        double sin = (double) Math.sin(angle);

        double newX = x * cos - y * sin;
        double newY = x * sin + y * cos;

        x = newX;
        y = newY;

        return this;
    }

    /**
     * Add another vector to this vector. Modifies the vector.
     * @param v The vector to add
     * @return The vector after addition
     */
    public Vector2d add(Vector2d v) {
        x += v.x;
        y += v.y;

        return this;
    }

    /**
     * Add another vector from this vector. Modifies the vector.
     * @param x The x coordinate of the vector to add
     * @param y The y coordinate of the vector to add
     * @return The vector after subtraction
     */
    public Vector2d add(double x, double y) {
        this.x += x;
        this.y += y;

        return this;
    }

    /**
     * Subtract another vector from this vector. Modifies the vector.
     * @param v The vector to subtract
     * @return The vector after subtraction
     */
    public Vector2d subtract(Vector2d v) {
        x -= v.x;
        y -= v.y;

        return this;
    }

    /**
     * Subtract another vector from this vector. Modifies the vector.
     * @param x The x coordinate of the vector to subtract
     * @param y The y coordinate of the vector to subtract
     * @return The vector after subtraction
     */
    public Vector2d subtract(double x, double y) {
        this.x -= x;
        this.y -= y;

        return this;
    }

    /**
     * Multiply this vector by a scalar. Modifies the vector.
     * @param scalar
     * @return The vector after multiplication
     */
    public Vector2d multiply(double scalar) {
        x *= scalar;
        y *= scalar;

        return this;
    }
}
