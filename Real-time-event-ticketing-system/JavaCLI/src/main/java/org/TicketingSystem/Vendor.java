package org.TicketingSystem;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private int totalTicketByOne;
    private long ticketReleaseRate;
    private TicketPool ticketPool;

    public Vendor(int totalTicketByOne, long ticketReleaseRate, TicketPool ticketPool) {
        this.totalTicketByOne = totalTicketByOne;
        this.ticketReleaseRate = ticketReleaseRate;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {
        for (int i = 0; i < totalTicketByOne; i++) {
            Ticket ticket = new Ticket(i, "IEEE robots", new BigDecimal("5000"));
            ticketPool.addTicket(ticket);

            try {
                Thread.sleep(1000 / ticketReleaseRate);
            } catch (Exception e) { }

        }
    }
}
