package Model;

public class OutSourced extends Part {

    private final String CompanyName;

    /**
     * Creates a new object
     * @param id id
     * @param name name
     * @param Inv inv
     * @param price price
     * @param min min
     * @param max max
     * @param CompanyName compnay name
     */

    public OutSourced(int id, String name, int Inv, double price, int min, int max, String CompanyName) {
        super(id, name, Inv, (int) price, min, max);
        this.CompanyName = CompanyName;
    }

    /**
     * Gets company name
     */
    public String getCompanyName() {
        return CompanyName;
    }
}