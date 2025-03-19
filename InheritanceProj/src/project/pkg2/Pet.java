/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.pkg2;

/**
 *
 * @author samsm
 */
public abstract class Pet implements Comparable{
    
    private String name;
    private String breed;
    private String sex;
    private int age;
    private double weight;
    private int ID;
    private double price;

    public Pet()
    {
        
    }
    
    public Pet(String name, String breed, String sex, int age, double weight, int ID, double price) {
        this.name = name;
        this.breed = breed;
        this.sex = sex;
        this.age = age;
        this.weight = weight;
        this.ID = ID;
        this.price =  price;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public double compareTo(Pet p1, Pet p2) {
        double total = (p1.getPrice() - p2.getPrice());
        return total;
    }
    
}
