package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int inventory;
    private int min;
    private int max;

    /**
     *
     * @param id id
     * @param name name
     * @param Inv Inv
     * @param price price
     * @param min min
     * @param max max
     */

    public Product(int id, String name, int Inv, double price, int min, int max ) {
        this.id = id;
        this.name = name;
        this.inventory = Inv;
        this.price = price;
        this.min = min;
        this.max = max;
    }

    /**
     * Gets id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set id
     * @param id set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     * @param name set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets price
     * @param price set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets Inv
     * @return Inv
     */
    public int getInv() {
        return inventory;
    }

    /**
     * Sets Inv
     * @param inventory set
     */
    public void setInv(int inventory) {
        this.inventory = inventory;
    }

    /**
     * Gets min
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * Sets min
     * @param min set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Gets max
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Sets max
     * @param max set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return associated parts
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;
    }
}