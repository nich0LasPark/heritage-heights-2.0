package com.example.casestudy_group1_cs2b;

import java.util.Arrays;

public class Queue {
    private String[] queue;
    private int size;
    private int front;
    private int rear;
    public boolean newPersonAdded = false; // Track if the "New Person" has already been added

    public Queue(int capacity) {
        queue = new String[capacity];
        size = 0;
        front = 0;
        rear = -1;
    }

    // Pre-fill the queue with 9 people initially
    public void fillQueue() {
        for (int i = 0; i < 9; i++) {
            enqueue("Person " + (i + 1));
        }
    }

    // Enqueue the "New Person" only once at a random position between 5th (index 4) and 10th (index 9)
    public void enqueueRandom(String person) {
        if (!newPersonAdded && size < queue.length) {
            // Random position between 4th (index 4) and 9th (index 9)
            int randomPosition = 4 + (int) (Math.random() * 6);

            // Shift elements to the right to make space for "New Person"
            for (int i = size - 1; i >= randomPosition; i--) {
                queue[(i + 1) % queue.length] = queue[i];
            }

            // Insert the new person at the random position
            queue[randomPosition] = person;
            size++;
            newPersonAdded = true; // Mark that the "New Person" has been added
        } else {
            System.out.println("The 'New Person' has already been added or the queue is full!");
        }
    }

    // Enqueue a person at the end of the queue
    public void enqueue(String person) {
        if (size < queue.length) {
            rear = (rear + 1) % queue.length;
            queue[rear] = person;
            size++;
        } else {
            System.out.println("Queue is full! Cannot enqueue.");
        }
    }

    // Dequeue a person from the front of the queue and shift all positions forward
    public String dequeue() {
        if (size > 0) {
            String person = queue[front];
            queue[front] = null; // Clear the person
            front = (front + 1) % queue.length;
            size--;
            System.out.print("Dequeuing this queue: ");
            printQueue();

            // Shift positions forward for all people except "New Person"
            //shiftPositionsForward();

            return person;
        } else {
            return "Queue is empty!";
        }
    }
    public void clearQueue() {
        // Set each element in the queue to null
        Arrays.fill(queue, null);
        // Reset the size to 0
        size = 0;
    }

    // Get the current position of a specific person (1-based index)
    public int getPosition(String person) {
        for (int i = 0; i < size; i++) {
            if (queue[(front + i) % queue.length] != null && queue[(front + i) % queue.length].equals(person)) {
                return i + 1; // Return position (1-based index)
            }
        }
        return -1; // Return -1 if the person is not found
    }

    // Print the current state of the queue
    public void printQueue() {
        for (int i = 0; i < size; i++) {
            System.out.print(queue[(front + i) % queue.length] + " ");
        }
        System.out.println();
    }
}
