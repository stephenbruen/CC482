package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


import static model.Inventory.*;

/** This creates the Main Screen Controller class*/
public class MainScreenController implements Initializable {

    @FXML
    private TableView<Part> Parts;

    @FXML
    private TableColumn<Part, Integer> PartID;

    @FXML
    private TableColumn<Part, String> PartName;

    @FXML
    private TableColumn<Part, Integer> PartInventoryLevel;

    @FXML
    private TableColumn<Part, Double> PartPrice;

    @FXML
    private Button addPartButton;

    @FXML
    private TableView<Product> Products;

    @FXML
    private TableColumn<Product, Integer> ProductID;

    @FXML
    private TableColumn<Product, String> ProductName;

    @FXML
    private TableColumn<Product, Integer> ProductInventoryLevel;

    @FXML
    private TableColumn<Product, Double> ProductPrice;
    @FXML
    private TextField partsearchtext;
    @FXML
    private TextField productsearchtext;
    static int productindex;




    /** This searches for a part with Name or ID, then shows matches */
    public void partSearchButtonPushed(javafx.event.ActionEvent actionEvent) {
        ObservableList<Part> searchedpart = FXCollections.observableArrayList();
        String stuff = partsearchtext.getText();

        for (Part p: allParts) {
            if (p.getName().contains(stuff)){
                searchedpart.add(p);
            }
            try{
                int stuff2 = Integer.parseInt(partsearchtext.getText());
                if (p.getId()==stuff2) {
                    searchedpart.add(p);
                }

            } catch (NumberFormatException e) {

            }
        }



        if (searchedpart.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("ERROR");
            alert.setHeaderText("Part was not found");
            alert.setContentText("search again");
            alert.showAndWait();

        }
        else{
            Parts.setItems(searchedpart);
        }
        if(stuff == " "){
            Parts.setItems(allParts);

        }

    }





    /** This opens the add Part screen. */
    public void addPartButtonPushed(ActionEvent actionEvent) throws IOException {
        Parent addparts = FXMLLoader.load(getClass().getResource("../view/AddPart.fxml"));
        Scene addPartsScene = new Scene(addparts);
        Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartsScene);
        addPartStage.show();

    }

    /** This opens the modify part screen. */
    public void modifyPartButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException {


        Stage modifyPartStage;
        Parent root;
        modifyPartStage=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/ModifyPart.fxml"));
        root = fxmlloader.load();
        Scene scene = new Scene(root);
        modifyPartStage.setScene(scene);
        modifyPartStage.show();
        ModifyPartController modifyScrCntrl = fxmlloader.getController();
        Part part=Parts.getSelectionModel().getSelectedItem();
        modifyScrCntrl.setPart(part);

    }
    /** This deletes the part selected. */
    public void deletePartButtonPushed(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("are you sure you want to remove the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Part parttodelete = Parts.getSelectionModel().getSelectedItem();
            Inventory.deletePart(parttodelete);
            updatePartTableView();
        }
        else {
            System.out.println("You canceled.");
        }

    }
    /** This searches for a product with Name or ID. */
    public void productSearchButtonPushed(javafx.event.ActionEvent actionEvent) {
        {
            ObservableList<Product> searchedproducts = FXCollections.observableArrayList();
            String stuff = productsearchtext.getText();

            for (Product p: allProducts) {
                if (p.getProductName().contains(stuff)){
                    searchedproducts.add(p);
                }
                try{
                    int stuff2 = Integer.parseInt(productsearchtext.getText());
                    if (p.getProductID()==stuff2) {
                        searchedproducts.add(p);
                    }

                } catch (NumberFormatException e) {

                }
            }



            if (searchedproducts.size() == 0){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ERROR");
                alert.setHeaderText("Part was not found");
                alert.setContentText("search again");
                alert.showAndWait();

            }
            else{
                Products.setItems(searchedproducts);
            }
            if(stuff == " "){
                Products.setItems(allProducts);

            }

        }}




    /** This deletes the product selected. */
    public void deleteProductButtonPushed(javafx.event.ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel");
        alert.setHeaderText("Cancel");
        alert.setContentText("are you sure you want to remove the part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {

            Product producttodelete = Products.getSelectionModel().getSelectedItem();
            if (producttodelete.getAllAssociatedParts().isEmpty() == false){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setContentText("Cannot remove a product with associated parts ");
                alert2.showAndWait();
            }
            else{Inventory.deleteProduct(producttodelete);
                UpdateProductsTableView();}
        }
        else {
            System.out.println("You canceled.");
        }


    }
    /** This opens the modify product screen. */
    public void modifyProductButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException {

        Stage modifyProductStage;
        Parent root;
        modifyProductStage=(Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource("../view/ModifyProduct.fxml"));
        root = fxmlloader.load();
        Scene scene = new Scene(root);
        modifyProductStage.setScene(scene);
        modifyProductStage.show();
        ModifyProductController modifyScrCntrl = fxmlloader.getController();
        Product product1=Products.getSelectionModel().getSelectedItem();
        productindex = allProducts.indexOf(product1);

        modifyScrCntrl.setProduct(product1);
    }
    /** This opens the add product screen. */
    public void addProductButtonPushed(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent addproducts = FXMLLoader.load(getClass().getResource("../view/AddProduct.fxml"));
        Scene addPartsScene = new Scene(addproducts);
        Stage addPartStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        addPartStage.setScene(addPartsScene);
        addPartStage.show();
    }
    /** This populates the table view with parts. */
    public void updatePartTableView() {
        Parts.setItems(getAllParts());
    }
    /** This populates the table view with products. */
    public void UpdateProductsTableView() {
        Products.setItems(getAllProducts());
    }

    /** This initializes and populates the tableviews on the main screen. */
    public void initialize(URL url, ResourceBundle rb) {
        PartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        PartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));
        PartInventoryLevel.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        Parts.setItems(getAllParts());
        updatePartTableView();
        UpdateProductsTableView();
        ProductID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        ProductName.setCellValueFactory(new PropertyValueFactory<>("ProductName"));
        ProductInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("ProductInStock"));
        ProductPrice.setCellValueFactory(new PropertyValueFactory<Product,Double>("ProductPrice"));
        UpdateProductsTableView();

    }
    /** This closes the application. */
    public void Exit(ActionEvent event) {
        System.exit(0);
    }
}
