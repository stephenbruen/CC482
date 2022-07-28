package model;

/**This creates the Outsourced class*/
public class Outsourced extends Part{
    private String companyName;



    /** This is the constructor. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        this.setCompanyName(companyName);
    }



    /** company name getter.*/
    public String getCompanyName() {
        return companyName;
    }

    /** company name setter*/
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}