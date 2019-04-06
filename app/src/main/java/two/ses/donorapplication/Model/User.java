package two.ses.donorapplication.Model;

public class User {
    private String name,email,group;

    public User(){

    }

    public User(String name, String email, String group){
        this.name = name;
        this.email = email;
        this.group = group;
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

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getGroup() {
        return group;
    }
}


