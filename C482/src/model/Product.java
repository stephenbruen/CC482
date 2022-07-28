package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** This creates the Product Class. */
public class Product {
    private SimpleStringProperty ProductPart;
    private SimpleIntegerProperty ProductInStock;
    private SimpleStringProperty ProductMin;
    private SimpleStringProperty ProductMax;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    public String productID;
    public String productName;
    public Double productPrice = 0.0;
    public int productInStock;
    public int min;
    public int max;


    /**This is the product constructor. */


    public Product(int productID, String productName, Double productPrice, int productInStock, int min, int max) {
        this.productID = String.valueOf(productID);
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInStock = productInStock;
        this.min = min;
        this.max = max;
    }

    /** Product ID getter */
    public int getProductID() {
        return Integer.parseInt(productID);
    }

    /** Product ID setter.*/
    public void setProductID(String productID) {
        this.productID = productID;
    }

    /** product name getter. */
    public String getProductName() {
        return productName;
    }

    /** name setter. */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /** product price getter*/
    public Double getProductPrice() {
        return productPrice;
    }

    /**price setter.*/
    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    /** stock getter.*/
    public int getProductInStock() {
        return productInStock;
    }

    /** stock setter.*/
    public void setProductInStock(int productInStock) {
        this.productInStock = productInStock;
    }

    /**  minimum getter.*/
    public int getMin() {
        return min;
    }

    /** minimum setter.*/
    public void setMin(int min) {
        this.min = min;
    }

    /** maximum getter.
     */
    public int getMax() {
        return max;
    }

    /** maximum setter.
     */
    public void setMax(int max) {
        this.max = max;
    }




    /**This method adds an associated part
     */
    public void addAssociatedPart(Part part){
        associatedParts.add(part);

    }
    /** This method deletes an associated part.
     */
    public void deleteAssociatedPart(Part part){
        associatedParts.remove(part);
    }
    /** associated parts getter.
     */
    public ObservableList<Part> getAllAssociatedParts(){
        return associatedParts;}
}