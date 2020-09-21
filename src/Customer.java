package courseProject;

public class Customer implements Comparable<Customer> {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private int zip;
    private String phone;
    private List<Order> orders;

    public Customer() {
        username = "";
        password = "";
        firstName = "";
        lastName = "";
        address = "";
        city = "";
        state = "";
        zip = 0;
        phone = "";
        orders = new List<>();
    }

    public Customer(String username, String password,String firstName, String lastName,String address,String city,
                    String state, int zip, String phone, List<Order> orders) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.orders = orders;
    }

    public String getUserName() {
        return username;
    }

    public String getPassWord(){
        return password;
    }
    public String getFistName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }
    public String getState(){return  state;}

    public int getZip() {
        return zip;
    }

    public String getPhone() {
        return phone;
    }

    public List<Order> getOrders(){
        return orders;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassWord(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setOrder(List<Order> orders) {
        this.orders = orders;
    }


    @Override public String toString() {
        String result = "Name: " + firstName + " " + lastName
                + "\nAddress: " + address
                + "\nCity: " + city
                + "\nState: " + state
                + "\nZip: " + zip
                + "\nPhone: " + phone
                + "\nOrders: " ;//+ orders.toString();
        orders.pointIterator();
        for(int i = 0; i < orders.getLength();i++) {
            result += " " + orders.getIterator().toString() + "\n";
            orders.advanceIterator();
        }
        return result;
    }

    public int compareTo(Customer othercustomer) {
        if(this.firstName.equals(othercustomer.firstName) && this.lastName.equals(othercustomer.lastName) && this.phone.equals(othercustomer.phone)) {
            return 0;
        }else if(this.firstName.equals(othercustomer.firstName) && this.lastName.equals(othercustomer.lastName)) {
            if(this.phone.compareTo(othercustomer.phone) < 0) {
                return -1;
            }else {
                return 1;
            }
        }else {
            if(this.firstName.equals(othercustomer.firstName)) {
                return this.lastName.compareTo(othercustomer.lastName);
            }else {
                return this.firstName.compareTo(othercustomer.firstName);
            }
        }
    }
    @Override public int hashCode() {
        String key = firstName + lastName;
        int sum = 0;
        for(int i = 0; i < key.length();i++) {
            sum += (int) key.charAt(i);
        }
        return sum;
    }

    /**
     * Determines whether two Customer objects are
     * equal by comparing firstName,lastName and phone
     * @param o the second Customer object
     * @return whether the Customer are equal
     */
    @Override public boolean equals(Object o) {
        if(o == this) {
            return true;
        }else if(!(o instanceof Customer)) {
            return false;
        }else {
            Customer m = (Customer) o;
            if(m.firstName.equals(this.firstName) && m.lastName.equals(this.lastName) && m.phone == this.phone) {
                return true;
            }
            return false;
        }
    }
}
