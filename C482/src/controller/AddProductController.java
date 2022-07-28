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
import model.*;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static model.Inventory.*;


/** Add Product Controller class. */

public class AddProductController implements Initializable {

    @FXML
    private TextField ProductIDTextField;

    @FXML
    private TextField ProductNameTextField;

    @FXML
    private TextField ProductInventoryTextField;

    @FXML
    private TextField ProductPriceTextField;

    @FXML
    private TextField ProductMaxTextField;

    @FXML
    private TextField ProductMinTextField;
    @FXML
    private TextField SearchText;

    @FXML
    private TableView<Part> PartTableView;

    @FXML
    private TableColumn<Part, Integer> PartID;

    @FXML
    private TableColumn<Part, String> PartName;

    @FXML
    private TableColumn<Part, Integer> Inventory;

    @FXML
    private TableColumn<Part, Double> Price;

    @FXML
    private TableView<Part> AssociatedPartTableView;

    @FXML
    private TableColumn<Part, Integer> AssociatedPartID;

    @FXML
    private TableColumn<Part, String> AssociatedPartName;

    @FXML
    private TableColumn<Part, Integer> AssociatedPartInventory;

    @FXML
    private TableColumn<Part, Double> AssociatedPartPrice;

    @FXML
    private TextField ProductSearchBar;

    @FXML
    private Button AddProductButton;

    @FXML
    private Button RemovePartButton;

    @FXML
    private Button SaveProductButton;

    @FXML
    private Button CancelProductButton;
    private ObservableList<Part> partslist = FXCollections.observableArrayList();
    private URL url;
    private ResourceBundle rb;
    private int ProductID;

    private String errorMessage = new String();



    @FXML
    /** This adds a part to associated parts table view. */
    void addProduct(ActionEvent event) {
        Part part = PartTableView.getSelectionModel().getSelectedItem();
        partslist.add(part);

        updateAssociatedPartsTableView();

    }

    @FXML
    /** cancels adding a product. */
    void cancelProduct(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("do you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You canceled");
        }}


    @FXML
    /** This deletes a part from the Associated parts view. */
    void removeProduct(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("are you sure you want to remove the product?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Part partdelete = AssociatedPartTableView.getSelectionModel().getSelectedItem();
            partslist.remove(partdelete);
        }
        else {
            System.out.println("You canceled.");
        }
    }




    @FXML
    /** This saves a product to the Inventory and shows it on the main screen. */
    void saveProduct(ActionEvent event) throws IOException {
        int productid =0;
        for (Product p : allProducts)
            if (p.getProductID()> productid) {
                productid = p.getProductID();
            }
        ProductIDTextField.setText(String.valueOf(++productid));
        String ID = (ProductIDTextField.getText());

        String name = ProductNameTextField.getText();
        int stock = Integer.parseInt(ProductInventoryTextField.getText());
        double price = Double.parseDouble(ProductPriceTextField.getText());
        int max = Integer.parseInt(ProductMaxTextField.getText());
        int min = Integer.parseInt(ProductMinTextField.getText());

        try {
            errorMessage = isProductValid(name, Integer.parseInt(String.valueOf(min)), Integer.parseInt(String.valueOf(max)), Integer.parseInt(String.valueOf(stock)), Double.parseDouble(String.valueOf(price)), partslist, errorMessage);
            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            } else {


                System.out.println("Product name: " + name);
                Product newproduct = new Product(Integer.parseInt(String.valueOf(ID)), name, Double.parseDouble(String.valueOf(price)), Integer.parseInt(String.valueOf(stock)), Integer.parseInt(String.valueOf(min)), Integer.parseInt(String.valueOf(max)));
                newproduct.setProductID(String.valueOf(ID));
                newproduct.setProductName(name);
                newproduct.setProductPrice(Double.parseDouble(String.valueOf(price)));
                newproduct.setProductInStock(Integer.parseInt(String.valueOf(stock)));
                newproduct.setMin(Integer.parseInt(String.valueOf(min)));
                for( Part p: partslist){
                    newproduct.addAssociatedPart(p);
                };
                model.Inventory.addProduct(newproduct);


                Parent addproduct = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene addProductsScene = new Scene(addproduct);
                Stage addProductsStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                addProductsStage.setScene(addProductsScene);
                addProductsStage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Form contains blank fields.");
            alert.showAndWait();

        }
    }




    /** This updates the all part table view on the add product screen. */
    public void updatePartTableView() {
        PartTableView.setItems(getAllParts());
    }


    /** This initializes, populates them. */
    public void initialize(URL url, ResourceBundle rb) {
        partslist.removeAll();
        PartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        PartName.setCellValueFactory(new PropertyValueFactory<Part, String>("Name"));
        Price.setCellValueFactory(new PropertyValueFactory<Part, Double>("Price"));
        Inventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("Stock"));
        PartTableView.setItems(getAllParts());
        updatePartTableView();
        AssociatedPartID.setCellValueFactory(new PropertyValueFactory<Part, Integer>("id"));
        AssociatedPartName.setCellValueFactory(new PropertyValueFactory<Part, String>("name"));
        AssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<Part, Double>("price"));
        AssociatedPartInventory.setCellValueFactory(new PropertyValueFactory<Part, Integer>("stock"));
        updateAssociatedPartsTableView();

    }
    /** This updates the Associated Parts table view. */
    public void updateAssociatedPartsTableView() {

        AssociatedPartTableView.setItems(partslist);
    }

    public void SearchButtonPushed(ActionEvent event) {
        ObservableList<Part> searchedparts = FXCollections.observableArrayList();
        String stuff = SearchText.getText();

        for (Part p : allParts) {
            if (p.getName().contains(stuff)) {
                searchedparts.add(p);
            }
            try {
                int stuff2 = Integer.parseInt(SearchText.getText());
                if (p.getId() == stuff2) {
                    searchedparts.add(p);
                }

            } catch (NumberFormatException e) {

            }
        }


        if (searchedparts.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("part was not found");
            alert.showAndWait();

        } else {
            PartTableView.setItems(searchedparts);
        }
        if (stuff == " ") {
            PartTableView.setItems(allParts);

        }
    }
    /** This tests if a product is valid *
     * @return the error message
     */

    public static String isProductValid(String name, int min, int max, int inv, double price, ObservableList<Part> parts, String errorMessage) {

        if (name == null) {
            errorMessage = errorMessage + "No name entered. ";
        }
        if (inv < 1) {
            errorMessage = errorMessage + "Inventory must be at least 1. ";
        }
        if (price <= 0) {
            errorMessage = errorMessage + "Price must be greater than $0. ";
        }
        if (max < min) {
            errorMessage = errorMessage + "The Max must be greater than or equal to the Min. ";
        }
        if (inv < min || inv > max) {
            errorMessage = errorMessage + "The inventory must be between the Min and Max values. ";
        }



        return errorMessage;
    }

}
