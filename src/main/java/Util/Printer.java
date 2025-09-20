package Util;

import Entity.Category;
import Entity.Customer;

import java.util.ArrayList;
import java.util.List;

public class Printer {

    public static String customerHeader(){
        return "ID\tName\tEmail\tPhone";
    }

    public static String categoryHeader(){
        return "ID\tName\tDescription";
    }


    public static void printCustomers(List<Customer> customers) {
        System.out.println(customerHeader());
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    public static void printCategories(List<Category> categories){
        System.out.println(categoryHeader());
        for (Category category : categories){
            System.out.println(category);
        }
    }
}
