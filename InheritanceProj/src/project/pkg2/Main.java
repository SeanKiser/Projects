/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package project.pkg2;

import java.util.*;

/**
 *
 * @author Adam Whaley and 
 */


public class Main {

    public static void main(String[] args) {

        Scanner scnr = new Scanner(System. in);
        PetStore ps = new PetStore("Sean's Black Market Pet Store");
        Dog dog1 = new Dog();
        Cat cat1 = new Cat();
        ExoticPet exPet1 = new ExoticPet();
        ArrayList<Pet> adoptionDriveList = new ArrayList<>();
        System.out.println("**** Welcome to " + ps.getStoreName() + "****");
        while (true) {

            System.out.println("Please select from one of the following menu otions");
            System.out.println("\t1. Adopt a new pet");
            System.out.println("\t2. Register a new member");
            System.out.println("\t3. Start adoption drive (add new pets)");
            System.out.println("\t4. Check current inventory");
            System.out.println("\t5. Compare Pet Prices");
            System.out.println("\t6. Exit");

            int choice1 = scnr.nextInt();

            switch (choice1) {
                case 1:
                    System.out.println("-----------------------------------");
                    purchase(ps, scnr, new ArrayList<Pet>());
                    break;
                case 2:
                    System.out.println("-----------------------------------");
                    registerNewMember(ps, scnr);
                    break;
                case 3:
                    System.out.println("-----------------------------------");
                    registerAnimal(ps, scnr);
                    break;
                case 4:
                    System.out.println("-----------------------------------");
                    System.out.println("Pet Inventory");
                    System.out.println("\tCurrent inventory of dogs: " + ps.inventoryValue("dog"));
                    System.out.println("\tCurrent inventory of cats: " + ps.inventoryValue("cat"));
                    System.out.println("\tCurrent inventory of exotic pets: " +ps.inventoryValue("exoticPet"));
                    break;
                case 5:
                    System.out.println("-----------------------------------");
                    comparePrice(dog1, cat1, exPet1, scnr, ps);
                    break;
                case 6:
                    System.out.println("Thanks for visiting!");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void purchase(PetStore petStore, Scanner scnr, ArrayList<Pet> cart) {
        System.out.println("What type of pet are you here to purchase?");
        System.out.println("\t1. Dogs");
        System.out.println("\t2. Cats"); 
        System.out.println("\t3. Exotic Pets");
        System.out.println("\t4. Exit Purchase");

        int petTypeChoice = scnr.nextInt();

        // display inventory menu
        int itemNum = 1;
        if (petTypeChoice == 1) {
            ArrayList<Dog> inventory = petStore.getAvailableDogs();
            if (!inventory.isEmpty()) {

                System.out.println("Which of the following dogs would you like to purchase?:");

                for (Dog pet : inventory) {
                    System.out.println(
                        "\t" + itemNum + ". $" + pet.getPrice() + " - " + pet.getBreed() + "(" + pet.getName() +
                        ")"
                    );
                    itemNum++;
                }
                // get user selection for product to add arraylist (cart)
                int choice = scnr.nextInt();
                if (choice <= inventory.size()) {
                    cart.add(inventory.get(choice - 1));
                    //update inventory for this item
                    petStore.removePet("dog", choice - 1);
                    //cart summary
                    System.out.println(
                        "You have " + cart.size() + " items in your cart. Are you done shopping?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");

                    int doneShopping = scnr.nextInt();
                    if (doneShopping == 1) {
                        //System.out.println("TO DO - CHEKOUT ");
                        checkout(petStore, scnr, cart);
                    } else if (doneShopping == 2) { // more shopping
                        purchase(petStore, scnr, cart); // recursively call purchase(...) until done
                    } else {
                        System.out.println("Invalid Choice.");
                    }
                } else {
                    System.out.println("Please enter a valid product number. Try again");
                    purchase(petStore, scnr, cart);
                }
            } else {
                System.out.println("Sorry! we currently have no dogs available.");
            }

        }
        else if (petTypeChoice == 2) {
            ArrayList<Cat> inventory = petStore.getAvailableCats();
            if (!inventory.isEmpty()) {

                System.out.println("Which of the following cats would you like to purchase?:");

                for (Cat pet : inventory) {
                    System.out.println(
                        "\t" + itemNum + ". $" + pet.getPrice() + " - " + pet.getBreed() + "(" + pet.getName() +
                        ")"
                    );
                    itemNum++;
                }
                // get user selection for product to add arraylist (cart)
                int choice = scnr.nextInt();
                if (choice <= inventory.size()) {
                    cart.add(inventory.get(choice - 1));
                    //update inventory for this item
                    petStore.removePet("cat", choice - 1);
                    //cart summary
                    System.out.println(
                        "You have " + cart.size() + " items in your cart. Are you done shopping?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");

                    int doneShopping = scnr.nextInt();
                    if (doneShopping == 1) {
                        //System.out.println("TO DO - CHEKOUT ");
                        checkout(petStore, scnr, cart);
                    } else if (doneShopping == 2) { // more shopping
                        purchase(petStore, scnr, cart); // recursively call purchase(...) until done
                    } else {
                        System.out.println("Invalid Choice.");
                    }
                } else {
                    System.out.println("Please enter a valid product number. Try again");
                    purchase(petStore, scnr, cart);
                }
            } else {
                System.out.println("Sorry! we currently have no cats available.");
            }

        }
        else if (petTypeChoice == 3) {
            ArrayList<ExoticPet> inventory = petStore.getAvailableExoticPets();
            if (!inventory.isEmpty()) {

                System.out.println("Which of the following cats would you like to purchase?:");

                for (ExoticPet pet : inventory) {
                    System.out.println(
                        "\t" + itemNum + ". $" + pet.getPrice() + " - " + pet.getBreed() + "(" + pet.getName() +
                        ")"
                    );
                    itemNum++;
                }
                // get user selection for product to add arraylist (cart)
                int choice = scnr.nextInt();
                if (choice <= inventory.size()) {
                    cart.add(inventory.get(choice - 1));
                    //update inventory for this item
                    petStore.removePet("exoticPet", choice - 1);
                    //cart summary
                    System.out.println(
                        "You have " + cart.size() + " items in your cart. Are you done shopping?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");

                    int doneShopping = scnr.nextInt();
                    if (doneShopping == 1) {
                        //System.out.println("TO DO - CHEKOUT ");
                        checkout(petStore, scnr, cart);
                    } else if (doneShopping == 2) { // more shopping
                        purchase(petStore, scnr, cart); // recursively call purchase(...) until done
                    } else {
                        System.out.println("Invalid Choice.");
                    }
                } else {
                    System.out.println("Please enter a valid product number. Try again");
                    purchase(petStore, scnr, cart);
                }
            } else {
                System.out.println("Sorry! we currently have no exotic pets available.");
            }

        }
        else if(petTypeChoice == 4)
        {
            double total = 0;
            for (Pet pet : cart)
            {
                total += pet.getPrice();
            }
            if (total != 0)
            {
                checkout(petStore, scnr, cart);
            }
        }
    }

    private static void checkout(PetStore petStore, Scanner scnr, ArrayList<Pet> cart) {
        // calculate total
        double total = 0;
        for (Pet pet : cart) {
            total += pet.getPrice();
        }
        System.out.println(
            "Your total comes to " + total + ". \nPlease select which member is making this" +
            " purchase."
        );

        // list members and option to register
        int num = 1;
        for (Member member : petStore.getMemberList()) {
            System.out.println(num + ". " + member.getName());
            num++;
        }
        for (PremiumMember member : petStore.getPremiumMemberList()) {
            System.out.println(num + ". " + member.getName());
            num++;
        }
        System.out.println(num + ". Register a new Member.");

        System.out.println(""); // space line
        int memberSelect = scnr.nextInt();
        Member purchaser = null;
        PremiumMember premiumPurchaser = null;

        if (memberSelect > petStore.getMemberList().size() + petStore.getPremiumMemberList().size() + 1) { // invalid selection
            System.out.println("Invalid Selection");
            checkout(petStore, scnr, cart); // recursive call if valid user input

        } else { // valid selection
            if (memberSelect == (petStore.getMemberList().size() + petStore.getPremiumMemberList().size()) + 1) { // adding a new member
                boolean premium = registerNewMember(petStore, scnr);
                if (premium) {
                    premiumPurchaser = petStore.getPremiumMemberList().get(
                        petStore.getPremiumMemberList().size() - 1
                    );
                } else {
                    purchaser = petStore.getMemberList().get(petStore.getMemberList().size() - 1);
                }
            } else if (memberSelect <= (petStore.getMemberList().size())) {
                purchaser = petStore.getMemberList().get(memberSelect - 1);
            } else { // existing premium member
                premiumPurchaser = petStore.getPremiumMemberList().get(
                    memberSelect - petStore.getMemberList().size() - 1
                );
            }

            // check if premium member and fees are due
            if (purchaser == null && premiumPurchaser != null) {
                if( !premiumPurchaser.isDuesPaid()){
                System.out.println(
                    "Premium Membership dues unpaid, $5 will be added to purchase total to cover du" +
                    "es."
                );
                total += 5;
                }
                premiumPurchaser.setDuesPaid(true);
                // update amount of purchases for this member
                premiumPurchaser.setAmountSpent(total);
                // done
                System.out.println("Your purchase total was: " + total);
                System.out.println(
                    "Congrats on your purchase " + premiumPurchaser.getName()
                );

            } else {
                // update amount of purchases for this member
                purchaser.setAmountSpent(total);
                System.out.println("Your purchase total was: " + total);
                System.out.println("Congrats on your purchase " + purchaser.getName());

            }
        }
    }

    private static boolean registerNewMember(PetStore petStore, Scanner scnr) {
        // prompt user to select membership type
        System.out.println("Let's get you registered as a new Member!");
        System.out.println( "Would you like to register as a free-tier or premium member?");
        System.out.println("\t1. Free-tier");
        System.out.println("\t2. Premium");

        // user selection
        int choice1 = scnr.nextInt();
        // if user selects a wrong choice 
        if (choice1 > 2 || choice1 < 1) {
            System.out.println("Invalid choice.");
            registerNewMember(petStore, scnr); // try again

        } else {
            // prompt user for name 
            System.out.println("Please enter your name:");
            scnr.nextLine();
            String name = scnr.nextLine();
             System.out.println("Welcome to our membership program! Thank you for registering.");
            if (choice1 == 1) { // regular membership
                petStore.addNewMember(name, false);
                return false;
            } else { // user entered 2 - premimum membership
                petStore.addNewMember(name, true);
                return true;
            }
        }
        return false;
    }
    
    private static void registerAnimal(PetStore petStore, Scanner scnr)
    {
        ArrayList<Pet> animal = new ArrayList<>();
        int petChoice = 0;
        
        while (petChoice != 4)
        {
        System.out.println("What type of pet are you surrendering today?");
        System.out.println("\t1. Dogs");
        System.out.println("\t2. Cats"); 
        System.out.println("\t3. Exotic Pets");
        System.out.println("\t4. Exit Surrender");
        
        
        petChoice = scnr.nextInt();
        
        if (petChoice == 1)
        {
            scnr.nextLine();
            System.out.println("What is your dogs name?");
            String dogName = scnr.nextLine();
            System.out.println("What is your dogs breed?");
            String dogBreed = scnr.nextLine();
            System.out.println("What is your dogs gender?");
            String dogSex = scnr.nextLine();
            System.out.println("What is your dogs age?");
            int dogAge = scnr.nextInt();
            System.out.println("What is your dogs weight?");
            double dogWeight = scnr.nextDouble();
            System.out.println("What is the price of your dog?");
            double dogPrice = scnr.nextDouble();
            animal.add(new Dog(dogName, dogBreed, dogSex, dogAge, dogWeight, 0, dogPrice));
            System.out.println(
                        "You have surrendered " + animal.size() + " pets. Will you be surrendering any more pets today?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");
            int dogDecision = scnr.nextInt();
            if (dogDecision == 2) {
                        //System.out.println("TO DO - CHEKOUT ");
                        petStore.adoptionDrive(animal);
                        petChoice = 4;
                    } else if (dogDecision == 1) { // more shopping
                        
                    }
                    else
                    {
                        System.out.println("Thats not a valid number.");
                    }
        }
        else if (petChoice == 2)
        {
            scnr.nextLine();
            System.out.println("What is your cats name?");
            String catName = scnr.nextLine();
            System.out.println("What is your cats breed?");
            String catBreed = scnr.nextLine();
            System.out.println("What is your cats gender?");
            String catSex = scnr.nextLine();
            System.out.println("What is your cats age?");
            int catAge = scnr.nextInt();
            System.out.println("What is your cats weight?");
            double catWeight = scnr.nextDouble();
            System.out.println("What is the price of your cat?");
            double catPrice = scnr.nextDouble();
            animal.add(new Cat(catName, catBreed, catSex, catAge, catWeight, 0, catPrice));
            System.out.println(
                        "You have surrendered " + animal.size() + " pets. Will you be surrendering any more pets today?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");
            int catDecision = scnr.nextInt();
            if (catDecision == 2) {
                        //System.out.println("TO DO - CHEKOUT ");
                        petStore.adoptionDrive(animal);
                        petChoice = 4;
                    } else if (catDecision == 1) { // more shopping
                        
                    }
                    else
                    {
                        System.out.println("Thats not a valid number.");
                    }
        }
        else if (petChoice == 3)
        {
            scnr.nextLine();
            System.out.println("What is your exotic pets name?");
            String name = scnr.nextLine();
            System.out.println("What is your exotic pets breed?");
            String breed = scnr.nextLine();
            System.out.println("What is your exotic pets gender?");
            String sex = scnr.nextLine();
            System.out.println("What is your exotic pets age?");
            int age = scnr.nextInt();
            System.out.println("What is your exotic pets weight?");
            double weight = scnr.nextDouble();
            System.out.println("What is the price of your exotic pet?");
            double price = scnr.nextDouble();
            animal.add(new ExoticPet(name, breed, sex, age, weight, 0, price));
            System.out.println(
                        "You have surrendered " + animal.size() + " pets. Will you be surrendering any more pets today?"
                    );
                    System.out.println("\t1. Yes");
                    System.out.println("\t2. No");
            int decision = scnr.nextInt();
            if (decision == 2) {
                        //System.out.println("TO DO - CHEKOUT ");
                        petStore.adoptionDrive(animal);
                        petChoice = 4;
                    } else if (decision == 1) { // more shopping
                        
                    }
                    else
                    {
                        System.out.println("Thats not a valid number.");
                    }
        }
        else if (petChoice == 4)
        {
            if(animal.size() > 0)
            {
                petStore.adoptionDrive(animal);
            }
        }
        else
        {
            System.out.println("Please enter a valid number. Try again.");
            registerAnimal(petStore, scnr);
        }
        }
        System.out.println("You have surrendered " + animal.size() + " pets today.");
    }
    
    private static void comparePrice(Dog d, Cat c, ExoticPet e, Scanner scnr, PetStore ps)
    {
        int compare = 0;
        
        while (compare != 4)
        {
        System.out.println("What type of animal do you want compare today?");
        System.out.println("\t1. Dogs");
        System.out.println("\t2. Cats"); 
        System.out.println("\t3. Exotic Pets");
        System.out.println("\t4. Exit Comparison");
        
        compare = scnr.nextInt();
        
        if (compare == 1)
        {
            ArrayList<Dog> inventory = ps.getAvailableDogs();
            
            System.out.println("Dog List");
            for(int i = 0; i < inventory.size(); i++)
            {
                System.out.println("\t" + (i + 1) + ". $" + inventory.get(i).getPrice() + " - " + inventory.get(i).getBreed() + "(" + inventory.get(i).getName() +
                        ")");
            }
            System.out.println("Choose the first dog that you want to compare.");
            int choice1 = scnr.nextInt();
            System.out.println("Choose the second dog that you want to compare.");
            int choice2 = scnr.nextInt();
            if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) > 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice1 - 1).getPrice()
                 - inventory.get(choice2 - 1).getPrice()) + " more than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) < 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice2 - 1).getPrice()
                 - inventory.get(choice1 - 1).getPrice()) + " less than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() 
                        + ") is equal too " + inventory.get(choice2).getBreed() + " (" + inventory.get(choice2).getName() + ")");
            }
        }
        else if (compare == 2)
        {
            ArrayList<Cat> inventory = ps.getAvailableCats();
            
            System.out.println("Cat List");
            for(int i = 0; i < inventory.size(); i++)
            {
                System.out.println("\t" + (i + 1) + ". $" + inventory.get(i).getPrice() + " - " + inventory.get(i).getBreed() + "(" + inventory.get(i).getName() +
                        ")");
            }
            System.out.println("Choose the first cat that you want to compare.");
            int choice1 = scnr.nextInt();
            System.out.println("Choose the second cat that you want to compare.");
            int choice2 = scnr.nextInt();
            if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) > 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice1 - 1).getPrice()
                 - inventory.get(choice2 - 1).getPrice()) + " more than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) < 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice2 - 1).getPrice()
                 - inventory.get(choice1 - 1).getPrice()) + " less than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() 
                        + ") is equal too " + inventory.get(choice2).getBreed() + " (" + inventory.get(choice2).getName() + ")");
            }
        }
        if (compare == 3)
        {
            ArrayList<ExoticPet> inventory = ps.getAvailableExoticPets();
            
            System.out.println("Exotic Pet List");
            for(int i = 0; i < inventory.size(); i++)
            {
                System.out.println("\t" + (i + 1) + ". $" + inventory.get(i).getPrice() + " - " + inventory.get(i).getBreed() + "(" + inventory.get(i).getName() +
                        ")");
            }
            System.out.println("Choose the first exotic pet that you want to compare.");
            int choice1 = scnr.nextInt();
            System.out.println("Choose the second exotic pet that you want to compare.");
            int choice2 = scnr.nextInt();
            if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) > 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice1 - 1).getPrice()
                 - inventory.get(choice2 - 1).getPrice()) + " more than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else if((d.compareTo(inventory.get(choice1 - 1), inventory.get(choice2 - 1))) < 0)
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() + ") costs $" + (inventory.get(choice2 - 1).getPrice()
                 - inventory.get(choice1 - 1).getPrice()) + " less than " + inventory.get(choice2 - 1).getBreed() + " (" + inventory.get(choice2 - 1).getName() + ")");
            }
            else
            {
                System.out.println(inventory.get(choice1 - 1).getBreed() + " (" + inventory.get(choice1 - 1).getName() 
                        + ") is equal too " + inventory.get(choice2).getBreed() + " (" + inventory.get(choice2).getName() + ")");
            }
        }
        else if (compare == 4)
        {
            
        }
        }
    }
    }
