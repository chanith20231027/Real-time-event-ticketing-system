package com.backend.ticketPlatform.entity;

import com.backend.ticketPlatform.service.TicketingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vendor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);
    private final TicketPool ticketPool;
    private final int ticketReleaseRate;
    private final TicketingService service;
    private final int maxTickets;
    private int ticketsReleasedByVendor = 0;
    private volatile boolean isRunning = true;

    public Vendor(TicketPool ticketPool, int ticketReleaseRate, TicketingService service, int maxTickets) {
        this.ticketPool = ticketPool;
        this.ticketReleaseRate = ticketReleaseRate;
        this.service = service;
        this.maxTickets = maxTickets;
    }

    @Override
    public void run() {
        while (isRunning && ticketsReleasedByVendor < maxTickets && service.canReleaseTickets()) {
            synchronized (service) {
                if (service.canReleaseTickets() && ticketsReleasedByVendor < maxTickets) {
                    int remainingVendorTickets = maxTickets - ticketsReleasedByVendor;
                    int remainingTotalTickets = service.getTotalTickets() - service.getTicketsReleased();
                    int ticketsToAdd = Math.min(
                        Math.min(ticketReleaseRate, remainingVendorTickets),
                        remainingTotalTickets
                    );

                    if (ticketsToAdd > 0) {
                        for (int i = 0; i < ticketsToAdd; i++) {
                            ticketPool.addTicket("Ticket-" + (service.getTicketsReleased() + i + 1));
                        }
                        service.incrementTicketsReleased(ticketsToAdd);
                        ticketsReleasedByVendor += ticketsToAdd;
                        logger.info("Vendor released {} tickets. Total released by this vendor: {}. Tickets in pool: {}", 
                            ticketsToAdd, ticketsReleasedByVendor, ticketPool.getAvailableTickets());
                    }
                }
            }
            try {
                Thread.sleep(1000); // Wait for 1 second before next release attempt
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        logger.info("Vendor finished. Total tickets released: {}", ticketsReleasedByVendor);
    }

    public void stop() {
        isRunning = false;
    }

    public int getTicketsReleasedByVendor() {
        return ticketsReleasedByVendor;
    }
}
