package me.miles.matthew.spaceflight.Utils;

import java.util.ArrayList;

@SuppressWarnings("unchecked")
public class Queue<Type> {
    ArrayList<Type> queue = new ArrayList<Type>();

    /**
     * Creates an empty queue
     */
    public Queue() { }

    /**
     * Returns the entire queue in it's current form
     * @return the queue as an array
     */
    public Type[] getQueue() {
        return (Type[]) queue.toArray();
    }

    /**
     * Adds an item to the end of the queue
     */
    public void add(Type item) {
        queue.add(item);
    }

    /**
     * Gets the item at a certain index
     */
    public Type get(int index) {
        return queue.get(index);
    }

    /**
     * Remove and return the item at the front of the queue
     * @return the item at the ned fo the queue
     */
    public Type pop() {
        return queue.remove(0);
    }

    /**
     * Find the current size of the queue
     * @return The size of the queue
     */
    public int size() {
        return queue.size();
    }

    /**
     * Remove all items from the queue
     */
    public void clear() {
        queue = new ArrayList<Type>();
    }
}
