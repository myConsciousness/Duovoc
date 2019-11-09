package dev.app.ks.thinkit.duovoc.framework;

import android.app.ProgressDialog;
import android.content.Context;

public final class ProgressDialogHandler {

    private Context context;
    private ProgressDialog progressDialog;

    public ProgressDialogHandler(final Context context) {
        this.context = context;
    }

    public void showSpinnerDialog(final String title, final String message) {

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        this.progressDialog.setTitle(title);
        this.progressDialog.setMessage(message);
        this.progressDialog.setIndeterminate(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.show();
    }

    public void showHolizontalDialog(final String title, final String message) {

        this.showHolizontalDialog(title, message, 0);
    }

    public void showHolizontalDialog(final String title, final String message, final int max) {

        this.progressDialog = new ProgressDialog(this.context);
        this.progressDialog.setTitle(title);
        this.progressDialog.setMessage(message);
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        this.progressDialog.setIndeterminate(false);
        this.progressDialog.setCancelable(false);
        this.progressDialog.setMax(max);
        this.progressDialog.show();
    }

    public void setMax(final int max) {

        this.progressDialog.setMax(max);
    }

    public void incrementProgress() {

        this.progressDialog.incrementProgressBy(1);
    }

    public void dismissDialog() {

        if (this.progressDialog != null && this.progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }
}
