package entity;

public class LoginUserNameHolder {

    // TODO メンバ変数はm
    private static LoginUserNameHolder mName = null;
    // TODO メンバ変数はm
    private String mLoginName;
    
    public LoginUserNameHolder() {
    }

    // TODO タイポ
    public static LoginUserNameHolder getInstance(){

        if (mName == null) {
            mName = new LoginUserNameHolder();
        }
        return mName;
    }

    public void setName(String name){
        mLoginName = name;
    }

    public String getName(){
        return mLoginName;
    }

}
