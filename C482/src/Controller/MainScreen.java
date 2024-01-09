package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreen implements Initializable {

    /**
     * RUNTIME ERROR:
     *
     * FUTURE ENHANCEMENTS:
     * Create functions outside of button to be called to OnAction for readability.
     * Add higher filter functions to search functions.
     * Add multiple item functionality in add, modify, delete.
     */

    Stage stage;
    Parent scene;

    /**
     * Table Columns
     */
    public TableColumn<Part, Integer> PartIDCol;
    public TableColumn<Part, String> PartNameCol;
    public TableColumn<Part, String> PartInvCol;
    public TableColumn<Part, Double> PartPriceCol;
    public TableColumn<Product, Integer> ProductsIDCol;
    public TableColumn<Product, String> ProductsNameCol;
    public TableColumn<Product, String> ProductsInvCol;
    public TableColumn<Product, Double> ProductsPriceCol;

    /**
     * Buttons
     */
    public Button AddParts;
    public Button ModifyParts;
    public Button DeleteParts;
    public Button AddProducts;
    public Button ModifyProducts;
    public Button DeleteProducts;
    public Button exitButton;
    public Button SearchParts;
    public Button SearchProducts;

    /**
     * TextField
     */
    public TextField SearchPartsSearch;
    public TextField SearchProductsSearch;

    /**
     * TableView
     */
    public TableView<Product> ProductsTable;
    public TableView<Part> PartsTable;

    /**
     * Add Part screen initiated
     * @param event add part button
     * @IOException loader
     */
    public void OnAddPart(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddPartForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Modify Part screen initiated
     * @param event modify part button
     * @IOException loader
     */
    public void OnModifyPart(ActionEvent event) throws IOException {
        Part part = PartsTable.getSelectionModel().getSelectedItem();
            if (part != null) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartForm PartForm = loader.getController();
            PartForm.setPart(part);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        } if (part == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Select a part to be modified" );
                alert.showAndWait();
        }
    }

    /**
     * Delete Part initiated
     */
    public void OnDeletePart() {
            Part selectPart = PartsTable.getSelectionModel().getSelectedItem();
            if (selectPart != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("CONFIRM");
            alert.setContentText("Are you sure you want to delete this part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(selectPart);
                PartsTable.refresh();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Please select a part to be deleted. " );
            alert.showAndWait();
        }
    }

    /**
     * Search Part filters letters and integers
     */
    public void OnSearchParts() {
        String partSearch = SearchPartsSearch.getText();
        ObservableList<Part> pSearch = Inventory.lookupPart(partSearch);
        if (pSearch.isEmpty()) {
            int id = Integer.parseInt(partSearch);
            Part search = Inventory.lookupPart(id);
            if (search != null) {
                pSearch.add(search);
            }
        }
        PartsTable.setItems(pSearch);
        PartsTable.refresh();
    }

    /**
     * Add Product screen initiated
     * @param event add product button
     * @IOException loader
     */
    public void OnAddProducts(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/AddProductForm.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Modify Product screen initiated
     * @param event modify product button
     * @IOException  loader
     */
    public void OnModifyProducts(ActionEvent event) throws IOException {
        Product product = ProductsTable.getSelectionModel().getSelectedItem();
        if (product != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/View/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductForm ProductForm = loader.getController();
            ProductForm.setProduct(product);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a product to be modified");
            alert.showAndWait();
        }
    }

    /**
     * Delete Product initiated
     */
    public void OnDeleteProducts() {
        try {
            Product selectProduct = ProductsTable.getSelectionModel().getSelectedItem();
            ObservableList<Part> list = selectProduct.getAllAssociatedParts();
            if (list.size() >= 1) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Product has an associated part ");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Are you sure you want to delete this Product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    Inventory.deleteProduct(selectProduct);
                    ProductsTable.refresh();
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Select a product to be deleted");
            alert.showAndWait();
        }
    }

    /**
     * Search Product filters letter and integers
     */
    public void OnSearchProducts() {
        String productSearch = SearchProductsSearch.getText();
        ObservableList<Product> sProduct = Inventory.lookupProduct(productSearch);
        if (sProduct.isEmpty()) {
                int id = Integer.parseInt(productSearch);
                Product search = Inventory.lookupProduct(id);
                if (search != null) {
                    sProduct.add(search);
                }
            }
        ProductsTable.setItems(sProduct);
        ProductsTable.refresh();
    }

    /**
     * Exit Button initiated
     */
    public void OnExitButton() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Do you wish to exit the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElseThrow() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Initialize controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PartsTable.setItems(Inventory.getAllParts());
        ProductsTable.setItems(Inventory.getAllProducts());

        PartIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("Inv"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("Inv"));

        ProductsIDCol.setCellValueFactory(new PropertyValueFactory<>("Id"));
        ProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        ProductsInvCol.setCellValueFactory(new PropertyValueFactory<>("Inv"));
        ProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }
}