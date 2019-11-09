package dev.app.ks.thinkit.duovoc.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dev.app.ks.thinkit.duovoc.R;
import dev.app.ks.thinkit.duovoc.holder.FromLanguageSingleRow;

public final class SwitchFromLanguageAdapter extends ArrayAdapter<FromLanguageSingleRow> {

    private static final String TAG = SwitchFromLanguageAdapter.class.getName();

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
        return this.fromLanguageView(
                R.layout.dialog_from_language_list,
                position,
                convertView,
                parent
        );
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.fromLanguageView(
                R.layout.dialog_from_language_drop_down_list,
                position,
                convertView,
                parent
        );
    }

    @Override
    public int getCount() {
        return this.listViewItemsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View fromLanguageView(final int layout, final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(this.getContext())
                    .inflate(layout, parent, false);
        }

        final FromLanguageSingleRow singleRow = this.getItem(position);

        if (singleRow != null) {
            final TextView textViewFromLanguage = convertView.findViewById(R.id.dialog_from_language);
            textViewFromLanguage.setText(singleRow.getFromLanguage());
        }

        return convertView;
    }
}