package ShirtCreator.Persistence;

public class Customer {

    public static int globalCount = 0;
    private int id, plz;
    private String firstName, lastName, street, location, email;
    private boolean deleted;

    // CONSTRUCTOR
    public Customer(String firstName, String lastName, String street, int plz, String location, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.plz = plz;
        this.location = location;
        this.email = email;
        this.id = globalCount++;
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public int getPlz() {
        return plz;
    }

    public void setPlz(int plz) {
        this.plz = plz;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
