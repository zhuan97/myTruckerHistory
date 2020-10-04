public class Store {
	
    private int id;
    private String city;
    private String address;
    private String phone;
    private int visit;
    
    // constructors
    public Store(int id, String city,String address,String phone,int visit) {
        this.id = id;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.visit = visit;
    }
    
    //accessors
    public int getId() {
        return id;
    }
    public String getCity() {
        return city;
    }
    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public int getVisit() {
        return visit;
    }
    
    //mutators
    public void setId(int id) {
        this.id = id;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public void setVisit(int visit){
        this.visit = visit;
    }
}
