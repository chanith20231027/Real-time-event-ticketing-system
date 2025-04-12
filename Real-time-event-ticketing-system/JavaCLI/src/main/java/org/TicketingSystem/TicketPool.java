package org.TicketingSystem;

import java.util.Vector;

public class TicketPool {

    private Vector<Ticket> ticketVector;
    private int maxTicketCapacity;

    public TicketPool(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketVector = new Vector<>();
    }

    public  synchronized void addTicket(Ticket ticket) {

        while(ticketVector.size() >= maxTicketCapacity) {
            try{
                wait();
            }catch(Exception e){}
        }

        this.ticketVector.add(ticket);
        notifyAll();

        System.out.println("Ticket added By: " + Thread.currentThread().getName() + " Current size is: " + ticketVector.size());

    }

    public synchronized Ticket purchaseTicket() {

        while (ticketVector.isEmpty()) {
            try {
                wait();
            }catch (Exception e) {}
        }

        Ticket ticket = ticketVector.removeFirst();
        notifyAll();
        System.out.println("Ticket purchased by: " + Thread.currentThread().getName() + " Current size is: " +  ticketVector.size() + " Ticket is: " + ticket);

        return ticket;
    }


}
