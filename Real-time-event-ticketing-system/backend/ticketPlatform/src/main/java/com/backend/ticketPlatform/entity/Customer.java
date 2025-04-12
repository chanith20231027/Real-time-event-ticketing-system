package com.backend.ticketPlatform.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final TicketPool ticketPool;
    private final int retrieveRate;
    private final int maxTickets;
    private int ticketsRetrievedByCustomer = 0; // Track tickets retrieved by this customer
    private volatile boolean isRunning = true;

    public Customer(TicketPool ticketPool, int retrieveRate, int maxTickets) {
        this.ticketPool = ticketPool;
        this.retrieveRate = retrieveRate;
        this.maxTickets = maxTickets;
    }

    @Override
    public void run() {
        while (isRunning && ticketsRetrievedByCustomer < maxTickets) {
            for (int i = 0; i < retrieveRate && ticketsRetrievedByCustomer < maxTickets; i++) {
                String ticket = ticketPool.removeTicket();
                if (ticket != null) {
                    ticketsRetrievedByCustomer++; // Increment this customer's counter
                    logger.info("Customer purchased: {}. Total retrieved by this customer: {}. Tickets remaining in pool: {}", 
                        ticket, ticketsRetrievedByCustomer, ticketPool.getAvailableTickets());
                }
            }
            try {
                Thread.sleep(1000); // Wait for 1 second before next retrieval attempt
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        logger.info("Customer reached ticket limit or stopped. Total tickets purchased: {}", ticketsRetrievedByCustomer);
    }

    public void stop() {
        isRunning = false;
    }

    public int getTicketsRetrievedByCustomer() {
        return ticketsRetrievedByCustomer;
    }
}
