package Model;

public class InHouse extends Part {

    private final int MachineID;

    /**
     * Creates a new object
     * @param id id
     * @param name name
     * @param Inv Inv
     * @param price price
     * @param min min
     * @param max max
     * @param MachineID machine id
     */
    public InHouse (int id, String name, int Inv, double price, int min, int max, int MachineID) {
        super(id, name, Inv, (int) price, min, max);
        this.MachineID = MachineID;
    }

    /**
     * Gets machine id
     */
    public  int getMachineID() {
        return MachineID;
    }
}
