public class Store {
	
    private String id;
    private String address;
    private String city;
    private String state;
    private String phone;

    // constructors
    public Store(String id,String address, String city,String state,String phone) {
        this.id = id;
        this.address = address;
        this.city = city;
        this.state = state;
        this.phone = phone;
    }
    
    //accessors
    public String getId() {
        return id;
    }
    public String getAddress() {
        return address;
    }
    public String getCity() {
        return city;
    }
    public String getState(){
        return state;
    }
    public String getPhone() {
        return phone;
    }

    //mutators
    public void setId(String id) {
        this.id = id;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setState(String state) {
        this.state = state;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
}