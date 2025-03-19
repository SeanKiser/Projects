/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author samsm
 */
import java.util.*;
import java.io.*;

/**
 *
 * ITSC 1213 
 * University of North Carolina at Charlotte
 */

public class FastFoodKitchenDriver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        try
        {
            FastFoodKitchen kitchen = new FastFoodKitchen();
            Scanner sc = new Scanner(System.in);
            String line;
            Scanner fileScanner = null;
            try
            {
                fileScanner = new Scanner(new File("FastFoodKitchen2\\burgerOrders2.csv"));
                fileScanner.nextLine();
                while (fileScanner.hasNext())
                {
                    line = fileScanner.nextLine();
                    String[] seperated = line.split("[,]", 0);
                    int ham = Integer.parseInt(seperated[1]);
                    int cheese = Integer.parseInt(seperated[2]);
                    int veggie = Integer.parseInt(seperated[3]);
                    int soda = Integer.parseInt(seperated[4]);
                    boolean toGo = Boolean.parseBoolean(seperated[5]);
                    kitchen.addOrder(ham, cheese, veggie, soda, toGo);
                }
            }
            catch(FileNotFoundException ex)
            {
                System.out.println("Caught FileNotFoundException for inputData.txt. Try again making sure the file name and path are correct.");
            }    

            while (kitchen.getNumOrdersPending() != 0) {
                // see what the user wants to do
                System.out.println("Please select from the following menu of options, by typing a number:");
                System.out.println("\t 1. Order food");
                System.out.println("\t 2. Cancel last order");
                System.out.println("\t 3. Show number of orders currently pending");
                System.out.println("\t 4. Complete order");
                System.out.println("\t 5. Check on order");
                System.out.println("\t 6. Cancel order");
                System.out.println("\t 7. Exit");
                try
                {
                    int num = sc.nextInt();
                    switch (num) {
                        case 1:
                            System.out.println("How many hamburgers do you want?");
                            int ham = sc.nextInt();
                            System.out.println("How many cheeseburgers do you want?");
                            int cheese = sc.nextInt();
                            System.out.println("How many veggieburgers do you want?");
                            int veggie = sc.nextInt();
                            System.out.println("How many sodas do you want?");
                            int sodas = sc.nextInt();
                            System.out.println("Is your order to go? (Y/N)");
                            char letter = sc.next().charAt(0);
                            boolean TOGO = false;
                            if (letter == 'Y' || letter == 'y') {
                                TOGO = true;
                            }
                            int orderNum = kitchen.addOrder(ham, cheese, veggie, sodas, TOGO);
                            System.out.println("Thank you. Your order number is " + orderNum);
                            System.out.println();
                            break;
                        case 2:
                            boolean ready = kitchen.cancelLastOrder();
                            if (ready) {
                                System.out.println("Thank you. The last order has been canceled");
                            } else {
                                System.out.println("Sorry. There are no orders to cancel.");
                            }
                            System.out.println();
                            break;
                        case 3:
                            System.out.println("There are " + kitchen.getNumOrdersPending() + " pending orders");
                            break;
                        case 4:
                            System.out.println("Enter order number to complete?");
                            int order = sc.nextInt();
                            kitchen.completeSpecificOrder(order);
                            System.out.println("Your order is ready. Thank you!");
                            break;
                        case 5:
                            System.out.println("What is your order number?");
                            order = sc.nextInt();
                            ready = kitchen.isOrderDone(order);
                            if (ready) {
                                System.out.println("Sorry, no order with this number was found.");
                            } else {
                                System.out.println("No, it's not ready, but it should be up soon. Sorry for the wait.");
                            }
                            System.out.println();
                            break;
                        case 6:
                            System.out.println("What is your order number?");
                            order = sc.nextInt();
                            boolean cancel = kitchen.cancelOrder(order);
                            if (cancel) {
                                System.out.println("Your order has been successfully cancelled ");
                            } else {
                                System.out.println("Sorry, we can’t find your order number in the system");
                            }
                            System.out.println();
                            break;
                        case 7:
                            try{
                            FileOutputStream fs = new FileOutputStream("EndOfDayReport.txt");
                            FileOutputStream fs2 = new FileOutputStream("burgerOrders2.csv");
                            PrintWriter outFS = new PrintWriter(fs);
                            PrintWriter outFS2 = new PrintWriter(fs2);
                            ArrayList<BurgerOrder> incomplete = kitchen.getOrderList();
                            ArrayList<BurgerOrder> complete = kitchen.getCompletedOrders();
                            outFS.println("*****End Of Day Report*****");
                            outFS.println();
                            outFS.println("Completed Orders");
                            for(int i = 0; i < complete.size(); i++)
                            {
                                outFS.println("Order ID: " + complete.get(i).getOrderNum() + " " + complete.get(i).toString());
                            }
                            outFS.println();
                            outFS.println("Incomplete Orders");
                            for(int i = 0; i < incomplete.size(); i++)
                            {
                                outFS.println("Order ID: " + incomplete.get(i).getOrderNum() + " " + incomplete.get(i).toString());
                            }
                            outFS.println();
                            outFS.println("Total Sales");
                            outFS.println("Hamburgers: " + kitchen.hamburgerCounter(complete));
                            outFS.println("Cheeseburgers: " + kitchen.cheeseburgerCounter(complete));
                            outFS.println("Veggieburgers: " + kitchen.veggieburgerCounter(complete));
                            outFS.println("Sodas: " + kitchen.sodaCounter(complete));
                            outFS2.println("orderID,numHamburgers,numCheeseburgers,numVeggieburgers,numSodas,toGo");
                            for(int i = 0; i < incomplete.size(); i++)
                            {
                                outFS2.println(incomplete.get(i).updatedPrint());
                            }
                            outFS.close();
                            outFS2.close();               
                            fs.close();
                            fs2.close();
                            }
                            catch(FileNotFoundException ex)
                            {
                                System.out.println("Caught FileNotFoundException. Try again making sure the file name and path are correct.");
                            } 
                            catch(IOException e)
                            {
                                System.out.println("Caught IOException when closing output stream. Try again.");
                            }    
                            fileScanner.close();
                            System.exit(0);
                            break;
                        default:
                            System.out.println("Sorry, but you need to enter a 1, 2, 3, 4, 5, 6, or a 7");

                    } //end switch
                }
                catch(InputMismatchException ex)
                {
                    System.out.println("Caught InputMismatchException. Make sure to type in the right type of input.");
                    sc.next();
                }
            } //end while loop 
        }
        catch(Exception ex)
        {
            System.out.println("Caught a general exception.");
        }
    } // end main
}// end class
