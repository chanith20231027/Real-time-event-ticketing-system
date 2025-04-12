package com.backend.ticketPlatform.entity;

import java.util.concurrent.ConcurrentLinkedQueue;

public class TicketPool {
    private final ConcurrentLinkedQueue<String> tickets = new ConcurrentLinkedQueue<>();
    private int maxCapacity;

    public TicketPool(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public synchronized void addTicket(String ticket) {
        if (tickets.size() >= maxCapacity) {
            return; // Don't add if we're at capacity
        }
        tickets.add(ticket);
        notifyAll();
    }

    public synchronized String removeTicket() {
        while (tickets.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        String ticket = tickets.poll();
        notifyAll(); // Notify vendors that space is available
        return ticket;
    }

    public int getAvailableTickets() {
        return tickets.size();
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
}
