package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.Optional;

/** This displays the Add Part Screen.*/
public class AddPartController {

    @FXML
    private RadioButton InHouseButton;

    @FXML
    private RadioButton OutsourcedButton;

    @FXML
    private Text TextField;

    @FXML
    private TextField IDTextField;

    @FXML
    private TextField NameTextField;

    @FXML
    private TextField InventoryTextField;

    @FXML
    private TextField PriceTextFIeld;

    @FXML
    private TextField MaxTextField;

    @FXML
    private TextField SwitchTextField;

    @FXML
    private TextField MinTextField;

    @FXML
    private Button SaveButton;

    @FXML
    private Button CancelButton;

    private boolean isOutsourced;
    private String errorMessage = new String();





    @FXML
    /** inhouse form. */
    void InHouseButtonPushed(ActionEvent event) {
        isOutsourced = false;
        TextField.setText("MachineID");
        OutsourcedButton.setSelected(false);
    }

    @FXML
    /** outsourced form. */
    void OutSourcedButtonPushed(ActionEvent event) {
        isOutsourced = true;
        TextField.setText("Company Name");
        InHouseButton.setSelected(false);

    }

    @FXML
    /** This adds the part on the main screen. */
    void SaveButtonPushed(ActionEvent event)  {
        int partid =0;
        for (Part p : Inventory.getAllParts()){
            if(p.getId() > partid){
                partid = p.getId();
            }
        }
        IDTextField.setText(String.valueOf(++partid));
        int ID = Integer.parseInt(IDTextField.getText());
        String Name = NameTextField.getText();
        String Stock = InventoryTextField.getText();
        String Price = PriceTextFIeld.getText();
        String Max = MaxTextField.getText();
        String Both = SwitchTextField.getText();
        String Min = MinTextField.getText();

        try{
            errorMessage = ValidPart( Name,Integer.parseInt(Min),Integer.parseInt(Max), Integer.parseInt(Stock), Double.parseDouble(Price), errorMessage);
            if(errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorMessage);
                alert.showAndWait();
                errorMessage = "";
            }
            else{
                if (isOutsourced == false) {
                    System.out.println("Part name: " + Name);
                    InHouse newPart = new InHouse(ID,Name,Double.parseDouble(Price), Integer.parseInt(Stock), Integer.parseInt(Min), Integer.parseInt(Max), Integer.parseInt(Both));
                    newPart.setId(ID);
                    newPart.setName(Name);
                    newPart.setPrice(Double.parseDouble(Price));
                    newPart.setStock(Integer.parseInt(Stock));
                    newPart.setMin(Integer.parseInt(Min));
                    newPart.setMax(Integer.parseInt(Max));
                    newPart.setMachineID(Integer.parseInt(Both));
                    Inventory.addPart(newPart);
                } else {
                    System.out.println("Part name: " + Name);
                    Outsourced newPart = new Outsourced(ID, Name, Double.parseDouble(Price), Integer.parseInt(Stock), Integer.parseInt(Min), Integer.parseInt(Max), Both);
                    newPart.setId(ID);
                    newPart.setName(Name);
                    newPart.setPrice(Double.parseDouble(Price));
                    newPart.setStock(Integer.parseInt(Stock));
                    newPart.setMin(Integer.parseInt(Min));
                    newPart.setMax(Integer.parseInt(Max));
                    newPart.setCompanyName(Both);
                    Inventory.addPart(newPart);
                }

                Parent addPartSave = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
                Scene scene = new Scene(addPartSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();


            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }



    @FXML
    /** Cancels adding part. Returns the main screen. */
    void CancelButtonPushed(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

        alert.setContentText("Did you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent addPartCancel = FXMLLoader.load(getClass().getResource("../view/MainScreen.fxml"));
            Scene scene = new Scene(addPartCancel);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("You canceled adding the part");
        }

    }
    /**This checks if a part is valid
     */
    public static String ValidPart(String name, int min, int max, int stock, double price, String errorMessage){
        if (name == null) {
            errorMessage = errorMessage + "Must have name.  ";

        }
        if (stock < 1) {
            errorMessage = errorMessage + "Inventory cannot be less than 1. ";
        }
        if (price <= 0) {
            errorMessage = errorMessage + "The price must be higher than $0. ";
        }
        if (max < min) {
            errorMessage = errorMessage + "The min must be less than the max ";
        }
        if (stock < min || stock > max) {
            errorMessage = errorMessage + "Inventory must be within min and max ";
        }
        return errorMessage;
    }}