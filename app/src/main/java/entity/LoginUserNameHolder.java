package entity;

// TODO Singleton ってクラス名に付けない。
// LoginUserNameを保持するからLoginUserNameHolderとかのほうが良いと思う。
public class LoginUserNameHolder {

    private static LoginUserNameHolder name = null;
    private String loginName;

    public LoginUserNameHolder() {
    }

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
