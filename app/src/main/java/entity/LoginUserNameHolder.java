package entity;

public class LoginUserNameHolder {

    private static LoginUserNameHolder INSTANCE = null;
    private String mLoginName;
    
    private LoginUserNameHolder() {
    }

    public static LoginUserNameHolder getInstance(){

        if (INSTANCE == null) {
            INSTANCE = new LoginUserNameHolder();
        }
        return INSTANCE;
    }

    public void setName(String name){
        mLoginName = name;
    }

    public String getName(){
        return mLoginName;
    }

}
