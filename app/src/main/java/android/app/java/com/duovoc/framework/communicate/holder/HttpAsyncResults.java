package android.app.java.com.duovoc.framework.communicate.holder;

import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

final public class HttpAsyncResults {

    private final HttpStatusCode httpStatusCode;

    private final ModelAccessor modelAccessor;

    public HttpAsyncResults(
            HttpStatusCode httpStatusCode,
            ModelAccessor modelAccessor) {

        this.httpStatusCode = httpStatusCode;
        this.modelAccessor = modelAccessor;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public ModelAccessor getModelAccessor() {
        return this.modelAccessor;
    }
}
