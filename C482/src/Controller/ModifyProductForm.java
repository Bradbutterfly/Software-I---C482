package Controller;

import Model.Inventory;
import Model.Product;
import Model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ModifyProductForm implements Initializable {

    /**
     * RUNTIME ERROR:
     * Added (min>max),(inv>max),(inv<min) to OnSave as the function operated but did not include those specifications initially on build.
     *
     * FUTURE ENHANCEMENTS:
     * Create functions outside of button to be called to OnAction for readability.
     * Add higher filter functions to search functions.
     * Multi add, remove function.
     */

    Parent scene;
    Stage stage;
    Integer productIndex = null;

    /**
     *Text Fields
     */
    public TextField ModifyProductID;
    public TextField ModifyProductName;
    public TextField ModifyProductInv;
    public TextField ModifyProductPriceCost;
    public TextField ModifyProductMax;
    public TextField ModifyProductMin;
    public TextField ModifyProductSearchBar;

    /**
     * Table Views
     */
    public TableView<Part> ModifyProductTable1;
    public TableView<Part> ModifyProductTable2;

    /**
     * Table Column
     */
    public TableColumn<Part, Integer> IDCol1;
    public TableColumn<Part, String> NameCol1;
    public TableColumn<Part, Integer> InvCol1;
    public TableColumn<Part, Double> PriceCostCol1;
    public TableColumn<Part, Integer> IDCol2;
    public TableColumn<Part, String> NameCol2;
    public TableColumn<Part, Integer> InvCol2;
    public TableColumn<Part, Double> PriceCostCol2;

    /**
     * Buttons
     */
    public Button ModifyProductAdd;
    public Button ModifyProductRemove;
    public Button ModifyProductSearch;
    public Button ModifyProductSave;
    public Button ModifyProductCancel;

    /**
     * Observable Lists
     */
    private final ObservableList<Part> partSelected = FXCollections.observableArrayList();

    /**
     * Product Search filters letters and integers
     */
    public void onModifyProductSearch() {
        String prdSearch = ModifyProductSearchBar.getText();
        ObservableList<Part> sp = Inventory.lookupPart(prdSearch);
        if (sp.isEmpty()) {
                int id = Integer.parseInt(prdSearch);
                Part search = Inventory.lookupPart(id);
                if (search != null) {
                    sp.add(search);
                }
            }
        ModifyProductTable1.setItems(sp);
        ModifyProductTable1.refresh();
    }

    /**
     * Product Add initiated
     */
    public void OnModifyProductAdd() {
        Part part = ModifyProductTable1.getSelectionModel().getSelectedItem();
        if (part == null)
            return;
        partSelected.add(part);
        ModifyProductTable2.setItems(partSelected);
    }

    /**
     * Product Remove initiated
     */
    public void OnModifyProductRemove() {
        Part part = ModifyProductTable2.getSelectionModel().getSelectedItem();
        if (part == null)
            return;
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Do you want to delete this part?",
                ButtonType.OK,
                ButtonType.CANCEL);
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                partSelected.remove(part);
            }
        });
    }

    /**
     * Product Save initiated
     * @param event save product button
     * @IOException FXMLoader
     */
    public void OnModifyProductSave(ActionEvent event) throws IOException {
        int id = Integer.parseInt(ModifyProductID.getText());
        String name = ModifyProductName.getText();
        int Inv = Integer.parseInt(ModifyProductInv.getText());
        double price = Double.parseDouble(ModifyProductPriceCost.getText());
        int max = Integer.parseInt(ModifyProductMax.getText());
        int min = Integer.parseInt(ModifyProductMin.getText());

            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Minimum value can not be greater than maximum value");
                alert.showAndWait();
            } else if (Inv > max) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inventory cannot be more than maximum value");
                alert.showAndWait();
            } else if (Inv < min) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Inventory cannot be less than minimum value");
                alert.showAndWait();
            } else {
            Product selectProduct = new Product(id, name, Inv, price, min, max);
            Inventory.updateProduct(productIndex, selectProduct);
            selectProduct.getAllAssociatedParts().addAll(partSelected);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Product Cancel initiated
     * @param event cancel product
     * @IOException FXMLoader
     */
    public void OnModifyProductCancel(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Initialize controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
         * Part table view
         */
        ModifyProductTable1.setItems(Inventory.getAllParts());
        IDCol1.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NameCol1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InvCol1.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        PriceCostCol1.setCellValueFactory(new PropertyValueFactory<>("Price"));

        /*
         * Product table view
         */
        ModifyProductTable2.setItems(partSelected);
        IDCol2.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NameCol2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InvCol2.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        PriceCostCol2.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
     * Set Product
     */
    public void setProduct(Product productSelected) {

        productIndex = Inventory.getAllProducts().indexOf(productSelected);

        ModifyProductID.setText(Integer.toString(productSelected.getId()));
        ModifyProductName.setText(productSelected.getName());
        ModifyProductMin.setText(Integer.toString(productSelected.getMin()));
        ModifyProductInv.setText(Integer.toString(productSelected.getInv()));
        ModifyProductPriceCost.setText(Double.toString(productSelected.getPrice()));
        ModifyProductMax.setText(Integer.toString(productSelected.getMax()));

        ObservableList<Part> productList = productSelected.getAllAssociatedParts();
        partSelected.addAll(productList);

    }
}
