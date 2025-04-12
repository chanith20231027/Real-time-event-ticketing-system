package com.backend.ticketPlatform.controller;

import com.backend.ticketPlatform.dto.TicketLogDTO;
import com.backend.ticketPlatform.entity.Vendor;
import com.backend.ticketPlatform.entity.Customer;
import com.backend.ticketPlatform.service.TicketingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {
    @Autowired
    private TicketingService ticketingService;

    @PostMapping("/configure")
    public ResponseEntity<?> configureSystem(@RequestParam int totalTickets,
                                             @RequestParam int ticketReleaseRate,
                                             @RequestParam int customerRetrieveRate,
                                             @RequestParam int maxTicketCapacity,
                                             @RequestParam int ticketsPerVendor,
                                             @RequestParam int ticketsPerCustomer) {
        try {
            ticketingService.configureSystem(totalTickets, ticketReleaseRate, customerRetrieveRate, maxTicketCapacity, 
                                           ticketsPerVendor, ticketsPerCustomer);
            return ResponseEntity.ok("System configured successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error configuring system: " + e.getMessage());
        }
    }

    @PostMapping("/start")
    public ResponseEntity<?> startSystem(@RequestParam int vendors, @RequestParam int customers) {
        try {
            ticketingService.startVendors(vendors);
            ticketingService.startCustomers(customers);
            return ResponseEntity.ok("System started with " + vendors + " vendors and " + customers + " customers.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error starting system: " + e.getMessage());
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> getAvailableTickets() {
        try {
            int availableTickets = ticketingService.getTicketPool().getAvailableTickets();
            return ResponseEntity.ok(availableTickets);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting ticket status: " + e.getMessage());
        }
    }


    @GetMapping("/vendor-stats")
    public ResponseEntity<?> getVendorStats() {
        try {
            List<Integer> stats = ticketingService.getVendors().stream()
                    .map(Vendor::getTicketsReleasedByVendor)
                    .toList();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting vendor stats: " + e.getMessage());
        }
    }

    @GetMapping("/customer-stats")
    public ResponseEntity<?> getCustomerStats() {
        try {
            List<Integer> stats = ticketingService.getCustomers().stream()
                    .map(Customer::getTicketsRetrievedByCustomer)
                    .toList();
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting customer stats: " + e.getMessage());
        }
    }

    @GetMapping("/logs")
    public ResponseEntity<?> getTicketLogs() {
        try {
            TicketLogDTO logs = new TicketLogDTO();
            
            // Get vendor logs
            List<TicketLogDTO.VendorLogDTO> vendorLogs = IntStream.range(0, ticketingService.getVendors().size())
                .mapToObj(i -> new TicketLogDTO.VendorLogDTO(
                    i + 1,
                    ticketingService.getVendors().get(i).getTicketsReleasedByVendor()
                ))
                .collect(Collectors.toList());
            
            // Get customer logs
            List<TicketLogDTO.CustomerLogDTO> customerLogs = IntStream.range(0, ticketingService.getCustomers().size())
                .mapToObj(i -> new TicketLogDTO.CustomerLogDTO(
                    i + 1,
                    ticketingService.getCustomers().get(i).getTicketsRetrievedByCustomer()
                ))
                .collect(Collectors.toList());
            
            // Set all logs data
            logs.setVendorLogs(vendorLogs);
            logs.setCustomerLogs(customerLogs);
            logs.setTotalTicketsReleased(ticketingService.getTicketsReleased());
            logs.setTotalTicketsPurchased(customerLogs.stream().mapToInt(TicketLogDTO.CustomerLogDTO::getTicketsPurchased).sum());
            logs.setAvailableTickets(ticketingService.getTicketPool().getAvailableTickets());
            
            return ResponseEntity.ok(logs);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error getting ticket logs: " + e.getMessage());
        }
    }
}
