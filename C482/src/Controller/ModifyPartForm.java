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

public class ModifyPartForm implements Initializable {

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
    Integer partIndex = null;

    /**
     * Label
     */
    public Label ModifyPartCompany;

    /**
     * Text Field
     */
    public TextField ModifyPartID;
    public TextField ModifyPartName;
    public TextField ModifyPartInv;
    public TextField ModifyPartPrice;
    public TextField ModifyPartMax;
    public TextField ModifyPartMin;
    public TextField ModifyPartCompanyName;

    /**
     * Buttons
     */
    public Button SaveButton;
    public Button CancelButton;

    /**
     * Radio Buttons
     */
    public RadioButton ModifyPartInHouse;
    public RadioButton ModifyPartOutsourced;

    /**
     * Toggle Group
     */
    public ToggleGroup InHouseToggle;

    /**
     * Save Button initiated
     * @param event save button
     * @IOException FXMLoader
     */
    public void OnSaveButton(ActionEvent event) throws IOException {
        String Name = ModifyPartName.getText();
        int Inv = Integer.parseInt(ModifyPartInv.getText());
        double Price = Double.parseDouble(ModifyPartInv.getText());
        int Min = Integer.parseInt(ModifyPartMin.getText());
        int Max = Integer.parseInt(ModifyPartMax.getText());

        if (Min > Max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Minimum value can not be greater than maximum value.");
            alert.showAndWait();
            return; }
        if (Inv > Max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be more than maximum value");
            alert.showAndWait();
            return;}
        if (Inv < Min) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be less than minimum value");
            alert.showAndWait();
            return;}

        if (ModifyPartInHouse.isSelected() && Integer.parseInt(ModifyPartInv.getText()) >= Integer.parseInt(ModifyPartMin.getText()) && Integer.parseInt(ModifyPartMin.getText()) <= Integer.parseInt(ModifyPartMax.getText())) {
            int Id = Integer.parseInt(ModifyPartID.getText());
            Max = Integer.parseInt(ModifyPartMax.getText());
            Min = Integer.parseInt(ModifyPartMin.getText());
            int MachineId = Integer.parseInt(ModifyPartCompanyName.getText());
            Inventory.updatePart(partIndex,  new InHouse(Id, Name, Inv, Price, Min, Max, MachineId));

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        } else if (ModifyPartOutsourced.isSelected() && Integer.parseInt(ModifyPartInv.getText()) >= Integer.parseInt(ModifyPartMin.getText()) && Integer.parseInt(ModifyPartMin.getText()) <= Integer.parseInt(ModifyPartMax.getText())) {
            int Id = Integer.parseInt(ModifyPartID.getText());
            Max = Integer.parseInt(ModifyPartMax.getText());
            Min = Integer.parseInt(ModifyPartMin.getText());
            String CompanyName = (ModifyPartCompanyName.getText());
            Inventory.updatePart(partIndex, new OutSourced(Id, Name, Inv, Price, Min, Max, CompanyName));

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
    public void OnModifyPartInHouse() {
        ModifyPartCompany.setText("Machine ID");
    }
    public void OnModifyPartOutsourced() {
        ModifyPartCompany.setText("Company Name");
    }

    /**
     * Initialize controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Set Part
     */
    public void setPart(Part partSelected) {

        partIndex = Inventory.getAllParts().indexOf(partSelected);

        ModifyPartID.setText(Integer.toString(partSelected.getId()));
        ModifyPartName.setText(partSelected.getName());
        ModifyPartMin.setText(Integer.toString(partSelected.getMin()));
        ModifyPartInv.setText(Integer.toString(partSelected.getInv()));
        ModifyPartPrice.setText(Double.toString(partSelected.getPrice()));
        ModifyPartMax.setText(Integer.toString(partSelected.getMax()));

        if (partSelected instanceof InHouse inH) {
            ModifyPartInHouse.setSelected(true);
            ModifyPartCompanyName.setText(Integer.toString(inH.getMachineID()));
            ModifyPartCompany.setText("Machine Id");
            ModifyPartCompanyName.setPromptText("Machine Id");

        } else {

            if (partSelected instanceof OutSourced outS) {
                ModifyPartOutsourced.setSelected(true);
                ModifyPartCompanyName.setText(outS.getCompanyName());
                ModifyPartCompany.setText("Company Name");
                ModifyPartCompanyName.setPromptText("Company Name");
            }
        }
    }
}