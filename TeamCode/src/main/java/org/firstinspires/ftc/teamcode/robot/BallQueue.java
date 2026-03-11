package org.firstinspires.ftc.teamcode.robot;

/**
 * Simple ball queue tracker for burst shooting.
 * Keeps count of how many balls are queued to be shot.
 */
public class BallQueue {
    private int count = 0;

    public void queue() {
        count++;
    }

    public void queue(int numBalls) {
        count += numBalls;
    }

    public void dequeue() {
        if (count > 0) count--;
    }

    public void clear() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    public boolean hasQueuedBalls() {
        return count > 0;
    }
}

