package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddPartForm implements Initializable {

    /**
     * RUNTIME ERROR:
     * Added (min>max),(inv>max),(inv<min) to OnSave as the function operated but did not include those specifications initially on build.
     *
     * FUTURE ENHANCEMENTS:
     * Create functions outside of button to be called to OnAction for readability.
     * Multi add function.
     */

    Parent scene;
    Stage stage;

    /**
     * Label
     */
    public Label AddPartCompany;

    /**
     * TextFields
     */
    public TextField AddPartID;
    public TextField AddPartName;
    public TextField AddPartInventory;
    public TextField AddPartPriceCost;
    public TextField AddPartMax;
    public TextField AddPartMin;
    public TextField AddPartCompanyName;

    /**
     * Buttons
     */
    public Button SaveButton;
    public Button CancelButton;

    /**
     * Radio Buttons
     */
    public RadioButton AddPartInHouse;
    public RadioButton AddPartOutsourced;

    /**
     * Toggle Group
     */
    public ToggleGroup InHouseToggle;

    /**
     * Set unique ID
     */
    private static int nextID = 1;
    public int getNextPartId() {
        return nextID++;
    }

    /**
     * Save Button initiated
     * @param event save button
     * @IOException FXMLoader
    */
    public void OnSaveButton(ActionEvent event) throws IOException {
        String name =  AddPartName.getText();
        int inv = Integer.parseInt(AddPartInventory.getText());
        double price = Double.parseDouble(AddPartPriceCost.getText());
        int min = Integer.parseInt(AddPartMin.getText());
        int max = Integer.parseInt(AddPartMax.getText());

        if (min > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Minimum value can not be greater than maximum value.");
            alert.showAndWait();
            return; }
        if (inv > max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be more than maximum value");
            alert.showAndWait();
            return;}
        if (inv < min) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be less than minimum value");
            alert.showAndWait();
            return;}

        if (AddPartInHouse.isSelected()) {
            int id = getNextPartId();
            int MachineID = Integer.parseInt(AddPartCompanyName.getText());
            InHouse newInHouse = new InHouse(id, name, inv, price, min, max, MachineID);
            Inventory.addPart(newInHouse);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } else if (AddPartOutsourced.isSelected()) {
            int id = getNextPartId();
            String companyName = (AddPartCompany.getText());
            OutSourced newOutSourced = new OutSourced(id, name, inv, price, min, max, companyName);
            Inventory.addPart(newOutSourced);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Cancel Button initiated
     * @param event cancel button
     * @IOException FXMLoader
     */
    public void OnCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Radio Buttons set label for each option
     */
    public void OnAddPartInHouse() {
            AddPartCompany.setText("Machine ID");
    }
    public void OnAddPartOutsourced() {
            AddPartCompany.setText("Company Name");
    }

    /**
     * Initialize controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Set part
     */
    public void setPart(Part partSelected) {
        AddPartID.setText(Integer.toString(partSelected.getId()));
        AddPartName.setText(partSelected.getName());
        AddPartMin.setText(Integer.toString(partSelected.getMin()));
        AddPartInventory.setText(Integer.toString(partSelected.getInv()));
        AddPartPriceCost.setText(Double.toString(partSelected.getPrice()));
        AddPartMax.setText(Integer.toString(partSelected.getMax()));
        if (partSelected instanceof InHouse) {
            AddPartInHouse.setSelected(true);
        }
        else {
            if (partSelected instanceof OutSourced outS) {
                AddPartOutsourced.setSelected(true);
                AddPartCompanyName.setText(outS.getCompanyName());
            }

        }
    }
}

