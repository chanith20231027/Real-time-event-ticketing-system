package com.backend.ticketPlatform.service;

import com.backend.ticketPlatform.entity.Customer;
import com.backend.ticketPlatform.entity.TicketPool;
import com.backend.ticketPlatform.entity.Vendor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TicketingService {
    private final TicketPool ticketPool;
    private final List<Vendor> vendors = new ArrayList<>();
    private final List<Customer> customers = new ArrayList<>();
    private int totalTickets;
    private int ticketsReleased = 0;
    private int ticketReleaseRate;
    private int customerRetrieveRate;
    private int maxTicketCapacity;
    private int ticketsPerVendor;
    private int ticketsPerCustomer;

    public TicketingService() {
        this.ticketPool = new TicketPool(0); // Will be configured later
    }

    public void configureSystem(int totalTickets, int ticketReleaseRate, int customerRetrieveRate, 
                              int maxTicketCapacity, int ticketsPerVendor, int ticketsPerCustomer) {
        if (totalTickets > maxTicketCapacity) {
            throw new IllegalArgumentException("Total tickets cannot exceed maximum ticket capacity.");
        }
        if (ticketsPerVendor <= 0) {
            throw new IllegalArgumentException("Tickets per vendor must be greater than 0.");
        }
        if (ticketsPerCustomer <= 0) {
            throw new IllegalArgumentException("Tickets per customer must be greater than 0.");
        }
        
        // Stop existing threads if any
        stopAllThreads();
        
        this.totalTickets = totalTickets;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrieveRate = customerRetrieveRate;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketsPerVendor = ticketsPerVendor;
        this.ticketsPerCustomer = ticketsPerCustomer;
        this.ticketsReleased = 0;
        
        ticketPool.setMaxCapacity(maxTicketCapacity);
    }

    public void startVendors(int vendorCount) {
        for (int i = 0; i < vendorCount; i++) {
            Vendor vendor = new Vendor(ticketPool, ticketReleaseRate, this, ticketsPerVendor);
            vendors.add(vendor);
            new Thread(vendor).start();
        }
    }

    public void startCustomers(int customerCount) {
        for (int i = 0; i < customerCount; i++) {
            Customer customer = new Customer(ticketPool, customerRetrieveRate, ticketsPerCustomer);
            customers.add(customer);
            new Thread(customer).start();
        }
    }

    private void stopAllThreads() {
        vendors.forEach(Vendor::stop);
        customers.forEach(Customer::stop);
        vendors.clear();
        customers.clear();
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public List<Vendor> getVendors() {
        return vendors;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public boolean canReleaseTickets() {
        return ticketsReleased < totalTickets;
    }

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getTicketsReleased() {
        return ticketsReleased;
    }

    public synchronized void incrementTicketsReleased(int count) {
        this.ticketsReleased += count;
    }
}
