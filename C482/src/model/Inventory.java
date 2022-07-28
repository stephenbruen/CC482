package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.Locale;
/** Inventory Class*/
public class Inventory {

    public static ObservableList<Part> allParts = FXCollections.observableArrayList();
    public static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /** this adds a part to the part list*/
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /** This is the parts getter*/

    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /** This is the products getter*/

    public static ObservableList<Product>  getAllProducts() {
        return allProducts;
    }

    /** This searches for a part with ID*/

    public static Part lookupPart(int partID){
        ObservableList<Part> partlist = Inventory.getAllParts();
        for(int i=0; i <partlist.size(); i++){
            Part part = partlist.get(i);
            if(part.getId() == partID){

                return part;

            }
        }
        return null;
    }

    /** This searches for product*/

    public static Product lookupProduct(int productID){
        ObservableList<Product> productlist = Inventory.getAllProducts();
        for(int i=0; i <productlist.size(); i++){
            Product product = productlist.get(i);
            if(product.getProductID() == productID){

                return product;

            }
        }
        return null;
    }

    /** This searches for a part*/
    public static ObservableList<Part> lookupPart(String partstring){
        ObservableList<Part> partMatches = FXCollections.observableArrayList();
        for(Part part: allParts){
            if(part.getName().toLowerCase(Locale.ROOT).contains(partstring.toLowerCase(Locale.ROOT))){
                partMatches.add(part);

            }
        } return partMatches;
    }

    /** This searches for a product*/
    public static ObservableList<Product> lookupProduct(String productstring){
        ObservableList<Product> productMatches = FXCollections.observableArrayList();
        for(Product product: allProducts){
            if(product.getProductName().toLowerCase(Locale.ROOT).contains(productstring.toLowerCase(Locale.ROOT))){
                productMatches.add(product);

            }
        } return productMatches;
    }

    /** This updates part/s*/
    public static void updatePart(Part selectedPart) {
        for (int i = 0; i < allParts.size(); i++) {
            if (allParts.get(i).getId() == selectedPart.getId()) {

                allParts.set(i, selectedPart);
                break;
            }
        }
        return;
    }
    /** This updates a product*/
    public static void updateProduct(Product selectedProduct){
        for (int i = 0; i<allProducts.size(); i++){
            if (allProducts.get(i).getProductID() == selectedProduct.getProductID()) {
                allProducts.set(i, selectedProduct);
                break;
            }} return;
    }


    /** This deletes part/s*/

    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);

    }

    /** This deletes product/s*/

    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }
}