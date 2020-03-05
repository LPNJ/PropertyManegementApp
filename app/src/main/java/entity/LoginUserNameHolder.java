package entity;

public class LoginUserNameHolder {

    // TODO メンバ変数はm
    private static LoginUserNameHolder name = null;
    // TODO メンバ変数はm
    private String loginName;

    public LoginUserNameHolder() {
    }

    // TODO タイポ
    public static LoginUserNameHolder getInstanse(){

        if (name == null) {
            name = new LoginUserNameHolder();
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
