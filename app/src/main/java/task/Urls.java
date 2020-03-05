package task;

// TODO 消す
import android.util.Log;

public class Urls {
    // TODO パブリック変数なのにgetterを用意する意味はない。
    // あとURLを決めておくだけならstaticクラスにするほうが良いと思う

    //puplic static
    private final String login = "http://133.139.115.154:8080/assets/api/login";

    private final String newAccount = "http://133.139.115.154:8080/assets/api/user";

    private String reference = "http://133.139.115.154:8080/assets/api/asset/";

    private final String PROPERTY_INFO = "http://133.139.115.154:8080/assets/api/assetlist";

    private final String name = "http://133.139.115.154:8080/assets/api/userlist";

    private final String edit = "http://133.139.115.154:8080/assets/api/asset";

    private final String delete = "http://133.139.115.154:8080/assets/api/asset";

    private final String regist = "http://133.139.115.154:8080/assets/api/asset";

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
        return PROPERTY_INFO;
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
