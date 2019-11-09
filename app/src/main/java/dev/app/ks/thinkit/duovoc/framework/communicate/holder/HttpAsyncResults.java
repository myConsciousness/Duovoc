package dev.app.ks.thinkit.duovoc.framework.communicate.holder;

import java.util.List;

import dev.app.ks.thinkit.duovoc.framework.communicate.property.HttpStatusCode;
import dev.app.ks.thinkit.duovoc.framework.model.holder.ModelAccessor;

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
