package task.impl;

import android.util.Log;

public class URLList {

    public final String login = "http://133.139.115.154:8080/assets/api/login";

    public final String newAccount = "http://133.139.115.154:8080/assets/api/user";

    public String reference = "http://133.139.115.154:8080/assets/api/asset/";

    public final String propertyInfo = "http://133.139.115.154:8080/assets/api/assetlist";

    public final String name = "http://133.139.115.154:8080/assets/api/userlist";

    public final String edit = "http://133.139.115.154:8080/assets/api/asset";

    public final String delete = "http://133.139.115.154:8080/assets/api/asset";

    public final String regist = "http://133.139.115.154:8080/assets/api/asset";

    public String getLogin() {
        return login;
    }

    public String getNewAccount() {
        return newAccount;
    }

    public String getReference(String param) {
        return reference+param;
    }

    public String getPropertyInfo() {
        return propertyInfo;
    }

    public String getName() {
        return name;
    }

    public String getEdit() {
        return edit;
    }

    public String getDelete() {
        return delete;
    }

    public String getRegist() {
        return regist;
    }

}
