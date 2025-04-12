package com.backend.ticketPlatform.dto;

import java.util.List;

public class TicketLogDTO {
    private List<VendorLogDTO> vendorLogs;
    private List<CustomerLogDTO> customerLogs;
    private int totalTicketsReleased;
    private int totalTicketsPurchased;
    private int availableTickets;

    public static class VendorLogDTO {
        private int vendorId;
        private int ticketsReleased;

        public VendorLogDTO(int vendorId, int ticketsReleased) {
            this.vendorId = vendorId;
            this.ticketsReleased = ticketsReleased;
        }

        public int getVendorId() {
            return vendorId;
        }

        public void setVendorId(int vendorId) {
            this.vendorId = vendorId;
        }

        public int getTicketsReleased() {
            return ticketsReleased;
        }

        public void setTicketsReleased(int ticketsReleased) {
            this.ticketsReleased = ticketsReleased;
        }
    }

    public static class CustomerLogDTO {
        private int customerId;
        private int ticketsPurchased;

        public CustomerLogDTO(int customerId, int ticketsPurchased) {
            this.customerId = customerId;
            this.ticketsPurchased = ticketsPurchased;
        }

        public int getCustomerId() {
            return customerId;
        }

        public void setCustomerId(int customerId) {
            this.customerId = customerId;
        }

        public int getTicketsPurchased() {
            return ticketsPurchased;
        }

        public void setTicketsPurchased(int ticketsPurchased) {
            this.ticketsPurchased = ticketsPurchased;
        }
    }

    // Getters and setters
    public List<VendorLogDTO> getVendorLogs() {
        return vendorLogs;
    }

    public void setVendorLogs(List<VendorLogDTO> vendorLogs) {
        this.vendorLogs = vendorLogs;
    }

    public List<CustomerLogDTO> getCustomerLogs() {
        return customerLogs;
    }

    public void setCustomerLogs(List<CustomerLogDTO> customerLogs) {
        this.customerLogs = customerLogs;
    }

    public int getTotalTicketsReleased() {
        return totalTicketsReleased;
    }

    public void setTotalTicketsReleased(int totalTicketsReleased) {
        this.totalTicketsReleased = totalTicketsReleased;
    }

    public int getTotalTicketsPurchased() {
        return totalTicketsPurchased;
    }

    public void setTotalTicketsPurchased(int totalTicketsPurchased) {
        this.totalTicketsPurchased = totalTicketsPurchased;
    }

    public int getAvailableTickets() {
        return availableTickets;
    }

    public void setAvailableTickets(int availableTickets) {
        this.availableTickets = availableTickets;
    }
}
