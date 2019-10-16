package android.app.java.com.duovoc.adapter;

import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.FromLanguageSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final public class SwitchFromLanguageAdapter extends ArrayAdapter<FromLanguageSingleRow> {

    private static final String TAG = SwitchFromLanguageAdapter.class.getSimpleName();

    /**
     * 学習言語変更画面における学習時使用言語リストの単数行オブジェクト。
     */
    private List<FromLanguageSingleRow> listViewItemsList;

    public SwitchFromLanguageAdapter(Context context, List<FromLanguageSingleRow> listViewItemsList) {
        super(context, 0, listViewItemsList);

        this.listViewItemsList = listViewItemsList;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.fromLanguageView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.fromLanguageView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return this.listViewItemsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View fromLanguageView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(this.getContext())
                    .inflate(R.layout.dialog_from_language_list, parent, false);
        }

        final FromLanguageSingleRow singleRow = this.getItem(position);

        if (singleRow != null) {
            final TextView textViewFromLanguage = convertView.findViewById(R.id.dialog_from_language);
            textViewFromLanguage.setText(singleRow.getFromLanguage());
        }

        return convertView;
    }
}