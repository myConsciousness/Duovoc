package android.app.java.com.duovoc.framework.communicate.property;

public enum RequestMethod {
    Post("POST"),
    Get("GET");

    private String method;
    RequestMethod(String method) {
        this.method = method;
    }

    public String getMethodName() {
        return this.method;
    }
}
