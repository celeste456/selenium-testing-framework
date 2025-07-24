package model;

public class CheckoutData {
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String company;
    private final String country;
    private final String city;
    private final String address;
    private final String zip;
    private final String phone;
    private final String fax;

    public CheckoutData(String firstName, String lastName, String email,
                        String company, String country, String city,
                        String address, String zip, String phone, String fax) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.company = company;
        this.country = country;
        this.city = city;
        this.address = address;
        this.zip = zip;
        this.phone = phone;
        this.fax = fax;
    }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getAddress() { return address; }
    public String getZip() { return zip; }
    public String getPhone() { return phone; }
    public String getFax() { return fax; }
}
