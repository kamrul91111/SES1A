package two.ses.donorapplication.Model;

public class User {
    private String name,email,group,address;

    public User(){

    }

    public User(String name, String email, String group, String address){
        this.name = name;
        this.email = email;
        this.group = group;
        this.address = address;



    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setAddress(String address) { this.group = address; }




    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }
    public String getAddress() {
        return address;
}
}




