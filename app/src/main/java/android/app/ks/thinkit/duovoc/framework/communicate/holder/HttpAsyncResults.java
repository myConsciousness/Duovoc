package android.app.ks.thinkit.duovoc.framework.communicate.holder;

import android.app.ks.thinkit.duovoc.framework.communicate.property.HttpStatusCode;
import android.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;

import java.util.List;

public final class HttpAsyncResults {

    private final HttpStatusCode httpStatusCode;

    private final List<? extends ModelAccessor> modelAccessorList;

    public HttpAsyncResults(
            HttpStatusCode httpStatusCode,
            List<? extends ModelAccessor> modelAccessorList) {

        this.httpStatusCode = httpStatusCode;
        this.modelAccessorList = modelAccessorList;
    }

    public HttpStatusCode getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public List<? extends ModelAccessor> getModelAccessorList() {
        return this.modelAccessorList;
    }

    public boolean isHttpStatusOk() {
        return this.httpStatusCode == HttpStatusCode.HTTP_OK;
    }
}
