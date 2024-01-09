package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddProductForm implements Initializable {

    /**
     * RUNTIME ERROR:
     * Added (min>max),(inv>max),(inv<min) to OnSave as the function operated but did not include those specifications initially on build.
     *
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
     * Text Fields
     */
    public TextField AddProductID;
    public TextField AddProductName;
    public TextField AddProductInv;
    public TextField AddProductPrice;
    public TextField AddProductMax;
    public TextField AddProductMin;
    public TextField AddProductSearchBar;

    /**
     * Table Views
     */
    public TableView<Part> AddProductTable1;
    public TableView<Part> AddProductTable2;

    /**
     * Table Columns
     */
    public TableColumn<Part, Integer> IDCol1;
    public TableColumn<Part, String> NameCol1;
    public TableColumn<Part, Integer> InvCol1;
    public TableColumn<Part, Double> PriceCostCol1;
    public TableColumn<Part, Integer> IDCol2;
    public TableColumn<Part, String> NameCol2;
    public TableColumn<Part, Double> InvCol2;
    public TableColumn<Part, Integer> PriceCostCol2;

    /**
     * Buttons
     */
    public Button AddProductAdd;
    public Button AddProductRemove;
    public Button AddProductSave;
    public Button AddProductCancel;
    public Button AddProductSearch;

    /**
     * Observable Lists
     */
    private final ObservableList<Part> partSelected = FXCollections.observableArrayList();

     /**
      * Set unique ID
     */
    private static int nextID = 1;
    public int getNextProductId() {
        return nextID++;
    }

    /**
     * Product Add initiated
    */
    public void OnAddProductAdd() {
        Part part = AddProductTable1.getSelectionModel().getSelectedItem();
        if (part == null)
            return;
        partSelected.add(part);
        AddProductTable2.setItems(partSelected);
    }

    /**
     * Product Remove initiated
     */
    public void OnAddProductRemove() {
        Part part = AddProductTable2.getSelectionModel().getSelectedItem();
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
     * Product Cancel initiated
     * @param event cancel product
     * @IOException FXMLoader
     */
    public void OnAddProductCancel(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../View/MainScreen.fxml")));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Product Save initiated
     * @param event save product
     * @IOException FXMLoader
     */
    public void OnAddProductSave(ActionEvent event) throws IOException {
            int id = getNextProductId();
            String Name = AddProductName.getText();
            int Inv = Integer.parseInt(AddProductInv.getText());
            double Price = Double.parseDouble(AddProductPrice.getText());
            int Max = Integer.parseInt(AddProductMax.getText());
            int Min = Integer.parseInt(AddProductMin.getText());
        if (Min > Max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory must be between minimum and maximum value");
            alert.showAndWait();
        } else if (Inv > Max) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be more than maximum value");
            alert.showAndWait();

        } else if (Inv < Min) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Inventory cannot be less than minimum value");
            alert.showAndWait();
        } else {
            Product newProduct = new Product(id, Name, Inv, Price, Min, Max);
            Inventory.addProduct(newProduct);
            newProduct.getAllAssociatedParts().addAll(partSelected);

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/View/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
            }
    }

    /**
     * Product Search filters letter and integers
     */
    public void OnAddProductSearch() {
        String prSearch = AddProductSearchBar.getText();
        ObservableList<Part> sp = Inventory.lookupPart(prSearch);
        if (sp.isEmpty()) {
                int id = Integer.parseInt(prSearch);
                Part search = Inventory.lookupPart(id);
                if (search != null) {
                    sp.add(search);
                }
            }
        AddProductTable1.setItems(sp);
        AddProductTable1.refresh();
    }

    /**
     * Initialize controller class
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /*
         * Part table view
         */
        AddProductTable1.setItems(Inventory.getAllParts());
        IDCol1.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NameCol1.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InvCol1.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        PriceCostCol1.setCellValueFactory(new PropertyValueFactory<>("Price"));

        /*
         * Product table view
         */
        AddProductTable2.setItems(partSelected);
        IDCol2.setCellValueFactory(new PropertyValueFactory<>("Id"));
        NameCol2.setCellValueFactory(new PropertyValueFactory<>("Name"));
        InvCol2.setCellValueFactory(new PropertyValueFactory<>("Inventory"));
        PriceCostCol2.setCellValueFactory(new PropertyValueFactory<>("Price"));
    }

    /**
    Set Product
     */
        public void setProduct (Product productSelected){
            productIndex = Inventory.getAllProducts().indexOf(productSelected);
            AddProductID.setText(Integer.toString(productSelected.getId()));
            AddProductName.setText(productSelected.getName());
            AddProductMin.setText(Integer.toString(productSelected.getMin()));
            AddProductInv.setText(Integer.toString(productSelected.getInv()));
            AddProductPrice.setText(Double.toString(productSelected.getPrice()));
            AddProductMax.setText(Integer.toString(productSelected.getMax()));

            ObservableList<Part> productList = productSelected.getAllAssociatedParts();
            partSelected.addAll(productList);
        }
    }


