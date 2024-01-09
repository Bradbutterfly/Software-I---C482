package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Inventory for all parts and products
 */

public class Inventory implements Initializable {

    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds part
     * @param newPart added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds product
     * @param newProduct added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Looks up part
     * @param partId id
     * @return part
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * @param partName name
     * @return part name
     */
    public static ObservableList <Part> lookupPart(String partName) {
        ObservableList<Part> partsNames = FXCollections.observableArrayList();

        for (Part pn : allParts) {
            if (pn.getName().contains(partName)) {
                partsNames.add(pn);
            }
        }
        return partsNames;
    }

    /**
     * Looks up product
     * @param productId id
     * @return product
     */
    public static Product lookupProduct(int productId) {
        for (Product product : allProducts) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * Observable list product
     */
    public static ObservableList <Product> lookupProduct(String prodName) {
        ObservableList<Product> pdNames = FXCollections.observableArrayList();

        for (Product pd : allProducts) {
            if (pd.getName().contains(prodName)) {
                pdNames.add(pd);
            }
        }
        return pdNames;
    }

    /**
     * Updates part
     * @param index position in list
     * @param selectPart replaces part
     */
    public static void updatePart(Integer index, Part selectPart) {
        allParts.set(index, selectPart);
    }

    /**
     * Updates product
     * @param index position in list
     * @param selectProduct replaces product
     */
    public static void updateProduct(Integer index, Product selectProduct) {
        allProducts.set(index, selectProduct);
    }

    /**
     * Deletes part
     * @param selectPart part to be deleted
     */
    public static void deletePart(Part selectPart) {
        for (Part part : allParts) {
            if (part.equals(selectPart)) {
                allParts.remove(part);
                return;
            }
        }
    }

    /**
     * Deletes product
     * @param selectProduct product to be deleted
     */
    public static void deleteProduct(Product selectProduct) {
        for (Product product : allProducts) {
            if (product.equals(selectProduct)) {
                allProducts.remove(selectProduct);
                return;
            }
        }
    }

    /**
     * Observable list part
     * @return all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Observable list product
     * @return all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}