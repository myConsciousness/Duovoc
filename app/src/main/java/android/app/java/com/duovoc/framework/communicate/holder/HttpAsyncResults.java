package android.app.java.com.duovoc.framework.communicate.holder;

import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

final public class HttpAsyncResults {

    private final HttpStatusCode httpStatusCode;

    private final ModelAccessor modelAccessor;
    
    private final List<ModelAccessor> modelAccessorList;

    public HttpAsyncResults(
            HttpStatusCode httpStatusCode,
            ModelAccessor modelAccessor) {

        this.httpStatusCode = httpStatusCode;
        this.modelAccessor = modelAccessor;
        this.modelAccessorList = new ArrayList<>();
    }
    
    public HttpAsyncResults(
        HttpStatusCode httpStatusCode,
        List<ModelAccessor> modelAccessorList) {

        this.httpStatusCode = httpStatusCode;
        this.modelAccessor = null;
        this.modelAccessorList = modelAccessorList;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public ModelAccessor getModelAccessor() {
        return this.modelAccessor;
    }
    
    public List<ModelAccessor> getModelAccessorList() {
        return this.modelAccessorList;
    }
    
    public boolean isHttpStatusOk() {
        return this.httpStatusCode == HttpStatusCode.HTTP_OK;
    }
}
