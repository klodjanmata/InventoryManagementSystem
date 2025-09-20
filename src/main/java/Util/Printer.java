package Util;

import Entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class Printer {

    public static String header(){
        return "ID\tName\tEmail\tPhone";
    }

    public static void printCustomers(List<Customer> customers) {
        System.out.println(header());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }
}
