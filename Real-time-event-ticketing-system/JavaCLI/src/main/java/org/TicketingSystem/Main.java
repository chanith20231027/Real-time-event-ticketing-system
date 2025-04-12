package org.TicketingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);
        char op, opConf;

        do {
            System.out.println("Enter 'Y' to START / Enter 'N' to STOP: ");
            op = scan.next().toLowerCase().charAt(0);

            if (op == 'y') {

                System.out.println("The system is started.");

                SystemConfig Config1 = new SystemConfig();

                do{
                    System.out.println("Enter 'Y' to input new parameters / Enter 'N' to load previous parameters: ");
                    opConf = scan.next().toLowerCase().charAt(0);

                    if (opConf == 'y') {

                        inputParameters(Config1);
                        saveConfig(Config1);
                        runConfig(Config1);

                    } else if (opConf == 'n') {

                        loadConfig(Config1);
                        runConfig(Config1);

                    }

                }while(opConf != 'n' & opConf != 'y');

            } else {
                if (op == 'n') {
                    System.out.println("The system is stopped.");
                }else {
                    System.out.println("Invalid input.");
                }

            }

        } while (op != 'n');

        scan.close();

    }


    public static void inputParameters(SystemConfig config) {

        Scanner scan2 = new Scanner(System.in);

        int totalT, ticketR, customerR, maxTicketC, vCount, cCount, tByVendor, tByCustomer;

        do {
            try {
                System.out.println("Enter total tickets: ");
                totalT = scan2.nextInt();
                if (totalT < 1) {
                    System.out.println("Total tickets must be positive.");
                }
            }catch(Exception e){
                System.out.println("Input must be an integer.");
                scan2.next();
                totalT = -1;
            }
        }while (totalT < 1);

        do {
            try {
                System.out.println("Enter ticket release rate: ");
                ticketR = scan2.nextInt();
                if (ticketR < 1) {
                    System.out.println("Ticket release rate must be positive.");
                }
            }catch(Exception e){
                System.out.println("Input must be an integer.");
                scan2.next();
                ticketR = -1;
            }
        }while (ticketR < 1);

        do {
            try {
                System.out.println("Enter customer retrieval rate: ");
                customerR = scan2.nextInt();
                if (customerR < 1) {
                    System.out.println("Customer retrieval rate must be positive.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                customerR = -1;
            }

        }while (customerR< 1);

        do {
            try {
                System.out.println("Enter Maximum ticket capacity: ");
                maxTicketC = scan2.nextInt();
                if (maxTicketC < totalT) {
                    System.out.println("Maximum ticket capacity must be greater than total tickets and It must not be negative.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                maxTicketC = -1;
            }

        }while (maxTicketC < totalT);

        do {
            try {
                System.out.println("Enter vendor count: ");
                vCount = scan2.nextInt();
                if (vCount < 1) {
                    System.out.println("Vendor count must be positive.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                vCount= -1;
            }

        }while (vCount< 1);

        do {
            try {
                System.out.println("Enter customer count: ");
                cCount = scan2.nextInt();
                if (cCount < 1) {
                    System.out.println("customer count must be positive.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                cCount= -1;
            }

        }while (cCount< 1);

        do {
            try {
                System.out.println("Enter number of tickets by one vendor: ");
                tByVendor = scan2.nextInt();
                if (tByVendor < 1) {
                    System.out.println("Number of tickets by one vendor must be positive.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                tByVendor= -1;
            }

        }while(tByVendor< 1);

        do {
            try {
                System.out.println("Enter number of tickets by one customer: ");
                tByCustomer = scan2.nextInt();
                if (tByCustomer < 1) {
                    System.out.println("Number of tickets by one customer must be positive.");
                }
            }catch(Exception e) {
                System.out.println("Input must be an integer.");
                scan2.next();
                tByCustomer = -1;
            }

        }while(tByCustomer< 1);

        config.setTotalTickets(totalT);
        config.setTicketReleaseRate(ticketR);
        config.setCustomerRetrievalRate(customerR);
        config.setMaxTicketCapacity(maxTicketC);
        config.setVendorCount(vCount);
        config.setCustomerCount(cCount);
        config.setTicketsByOneVendor(tByVendor);
        config.setTicketsByOneCustomer(tByCustomer);
    }

    public static void saveConfig(SystemConfig config) {

        try(FileWriter writer = new FileWriter("TextConfig.txt")){
            writer.write(String.valueOf(config.getTotalTickets()) + "\n");
            writer.write(String.valueOf(config.getTicketReleaseRate()) + "\n");
            writer.write(String.valueOf(config.getCustomerRetrievalRate()) + "\n");
            writer.write(String.valueOf(config.getMaxTicketCapacity()) + "\n");
            writer.write(String.valueOf(config.getVendorCount()) + "\n");
            writer.write(String.valueOf(config.getCustomerCount()) + "\n");
            writer.write(String.valueOf(config.getTicketsByOneVendor()) + "\n");
            writer.write(String.valueOf(config.getTicketsByOneCustomer()) + "\n");

        }catch (Exception e) {
            System.out.println("save config failed.");

        }

    }

    public static void loadConfig(SystemConfig config) {
        String a, b, c, d, e, f, g, h;

        try(BufferedReader breader = new BufferedReader(new FileReader("TextConfig.txt"))){

            a = breader.readLine();
            b = breader.readLine();
            c = breader.readLine();
            d = breader.readLine();
            e = breader.readLine();
            f = breader.readLine();
            g = breader.readLine();
            h = breader.readLine();

            breader.close();

            config.setTotalTickets(Integer.parseInt(a));
            config.setTicketReleaseRate(Integer.parseInt(b));
            config.setCustomerRetrievalRate(Integer.parseInt(c));
            config.setMaxTicketCapacity(Integer.parseInt(d));
            config.setVendorCount(Integer.parseInt(e));
            config.setCustomerCount(Integer.parseInt(f));
            config.setTicketsByOneVendor(Integer.parseInt(g));
            config.setTicketsByOneCustomer(Integer.parseInt(h));

            System.out.println("Previous Total tickets: " + config.getTotalTickets());
            System.out.println("Previous Ticket release rate: " + config.getTicketReleaseRate());
            System.out.println("Previous Customer Retrieval: " + config.getCustomerRetrievalRate());
            System.out.println("Previous Maximum ticket capacity: " + config.getMaxTicketCapacity());
            System.out.println("Previous Vendor count: " + config.getVendorCount());
            System.out.println("Previous Customer count: " + config.getCustomerCount());
            System.out.println("Previous Tickets by one vendor: " + config.getTicketsByOneVendor());
            System.out.println("Previous Tickets by one customer: " + config.getTicketsByOneCustomer());

        }catch(Exception e2){
            System.out.println("load config failed.");
        }

    }

    public static void runConfig(SystemConfig config) {

        TicketPool ticketPool = new TicketPool(config.getMaxTicketCapacity());

        Vendor[] vendors = new Vendor[config.getVendorCount()];
        for (int i = 0; i < vendors.length; i++) {
            vendors[i] = new Vendor(config.getTicketsByOneVendor(), config.getTicketReleaseRate(), ticketPool);
            Thread vendorThread = new Thread(vendors[i], "Vendor ID: " + i);
            vendorThread.start();
        }

        Customer[] customers = new Customer[config.getCustomerCount()];
        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Customer(config.getCustomerRetrievalRate(), config.getTicketsByOneCustomer(), ticketPool);
            Thread customerThread = new Thread(customers[i], "Customer ID: " + i);
            customerThread.start();
        }
    }
}