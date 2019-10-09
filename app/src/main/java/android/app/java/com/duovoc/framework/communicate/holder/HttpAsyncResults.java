package android.app.java.com.duovoc.framework.communicate.holder;

import android.app.java.com.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.java.com.duovoc.framework.model.holder.ModelAccessor;

import androidx.annotation.Nullable;

final public class HttpAsyncResults {

    private final HttpStatusCode httpStatusCode;

    @Nullable
    private final ModelAccessor modelAccessor;

    public HttpAsyncResults(
            HttpStatusCode httpStatusCode,
            @Nullable ModelAccessor modelAccessor) {

        this.httpStatusCode = httpStatusCode;
        this.modelAccessor = modelAccessor;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    @Nullable
    public ModelAccessor getModelAccessor() {
        return this.modelAccessor;
    }
}
