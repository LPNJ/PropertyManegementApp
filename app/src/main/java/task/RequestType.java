package task;

public enum RequestType {
    LOGIN("login", "POST"),
    NEW_ACCOUNT("user", "POST"),
    REGISTER("asset","POST"),
    EDIT("asset","PUT"),
    DELETE("asset","DELETE"),
    GET_REFERENCE("asset","GET"),
    GET_PROPERTIES("assetlist","GET"),
    GET_NAMES("userlist","GET");

    private final String mUrl;
    private final String mMethod;
    private static final String HOST_NAME = "http://133.139.115.154:8080/assets/api/";

    RequestType(String url, String method) {
        mUrl = HOST_NAME + url;
        mMethod = method;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getMethod() {
        return mMethod;
    }
}
