package Model;

public class User {
    protected String phone_number;//pk
    protected String password;
    protected String name;
    protected String state;//alive means normal, banned for ban,deleted for delete

    public User(String phone_number,String password,String name){
        setPhone_number(phone_number);
        setName(name);
        setPassword(password);
        setState("alive");
    }
    public User(){
        //nothing
    }
    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setState(String state) {
        this.state = state;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}
