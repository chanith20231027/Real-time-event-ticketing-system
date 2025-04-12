package org.TicketingSystem;

public class SystemConfig {
    private int totalTickets;
    private int ticketReleaseRate;
    private int customerRetrievalRate;
    private int maxTicketCapacity;
    private int vendorCount;
    private int customerCount;
    private int ticketsByOneVendor;
    private int ticketsByOneCustomer;

    public void setMaxTicketCapacity(int maxTicketCapacity) {
        this.maxTicketCapacity = maxTicketCapacity;
    }
    public void setCustomerRetrievalRate(int customerRetrievalRate) {
        this.customerRetrievalRate = customerRetrievalRate;
    }
    public void setTicketReleaseRate(int ticketReleaseRate) {
        this.ticketReleaseRate = ticketReleaseRate;
    }
    public void setTotalTickets(int totalTickets) {
        this.totalTickets = totalTickets;
    }
    public void setVendorCount(int vendorCount) {
        this.vendorCount = vendorCount;
    }
    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }
    public void setTicketsByOneVendor(int ticketsByOneVendor) {
        this.ticketsByOneVendor = ticketsByOneVendor;
    }
    public void setTicketsByOneCustomer(int ticketsByOneCustomer) {
        this.ticketsByOneCustomer = ticketsByOneCustomer;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }
    public int getCustomerRetrievalRate() {
        return customerRetrievalRate;
    }
    public int getTicketReleaseRate() {
        return ticketReleaseRate;
    }
    public int getTotalTickets() {
        return totalTickets;
    }
    public int getVendorCount() {
        return vendorCount;
    }
    public int getCustomerCount() {
        return customerCount;
    }
    public int getTicketsByOneVendor() {
        return ticketsByOneVendor;
    }
    public int getTicketsByOneCustomer() {
        return ticketsByOneCustomer;
    }

    @Override
    public String toString() {
        return "SystemConfig{" +
                "totalTickets=" + totalTickets +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", vendorCount=" + vendorCount +
                ", customerCount=" + customerCount +
                ", ticketsByOneVendor=" + ticketsByOneVendor +
                ", ticketsByCustomer=" + ticketsByOneCustomer +
                '}';
    }
}


