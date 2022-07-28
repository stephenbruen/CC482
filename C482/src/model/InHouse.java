package model;

/** This creates the Inhouse class*/
public class InHouse extends Part{
    public int machineID;

    /** constructor. */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id, name, price, stock, min, max );
        this.machineID=machineID;
    }

    /** This is the getter.*/
    public int getMachineID() {
        return machineID;
    }
    /** This is the setter*/
    public void setMachineID(int machineID) {
        this.machineID = machineID;
    }
}
