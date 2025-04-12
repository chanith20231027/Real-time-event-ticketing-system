package org.TicketingSystem;

import java.math.BigDecimal;

public class Ticket {

    private int TicketId;
    private String EventName;
    private BigDecimal TicketPrice;

    public Ticket(int TicketId, String EventName, BigDecimal TicketPrice) {
        this.TicketId = TicketId;
        this.EventName = EventName;
        this.TicketPrice = TicketPrice;
    }

    public int getTicketId() {
        return TicketId;
    }
    public void setTicketId(int ticketId) {
        TicketId = ticketId;
    }

    public BigDecimal getTicketPrice() {
        return TicketPrice;
    }
    public void setTicketPrice(BigDecimal ticketPrice) {
        TicketPrice = ticketPrice;
    }

    public String getEventName() {
        return EventName;
    }
    public void setEventName(String eventName) {
        EventName = eventName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "TicketId=" + TicketId +
                ", EventName='" + EventName + '\'' +
                ", TicketPrice=" + TicketPrice +
                '}';
    }
}
