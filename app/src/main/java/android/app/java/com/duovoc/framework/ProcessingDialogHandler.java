package android.app.java.com.duovoc.framework;

import android.app.ProgressDialog;
import android.content.Context;

public class ProcessingDialogHandler {

    private ProgressDialog myProgressDialog;

    public void showProcessingDialog(Context context) {
        this.myProgressDialog = new ProgressDialog(context);
        this.myProgressDialog.setMessage("Processing...");
        this.myProgressDialog.show();
    }

    public void dismissDialog() {
        if (this.myProgressDialog != null && this.myProgressDialog.isShowing()) {
            this.myProgressDialog.dismiss();
        }
    }
}
