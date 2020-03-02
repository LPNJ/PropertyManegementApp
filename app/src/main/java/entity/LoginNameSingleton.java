package entity;

public class LoginNameSingleton {

    private static LoginNameSingleton name = null;
    private String loginName;

    public LoginNameSingleton() {
    }

    public static LoginNameSingleton getInstanse(){

        if (name == null) {
            name = new LoginNameSingleton();
        }
        return name;
    }

    public void setName(String name){
        loginName = name;
    }

    public String getName(){
        return loginName;
    }

}
