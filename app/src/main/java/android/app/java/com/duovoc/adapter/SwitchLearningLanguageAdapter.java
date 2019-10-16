package android.app.java.com.duovoc.adapter;

import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.LearningLanguageSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

final public class SwitchLearningLanguageAdapter extends ArrayAdapter<LearningLanguageSingleRow> {

    private static final String TAG = SwitchLearningLanguageAdapter.class.getSimpleName();

    /**
     * 学習言語変更画面における学習言語リストの単数行オブジェクト。
     */
    private List<LearningLanguageSingleRow> listViewItemsList;

    public SwitchLearningLanguageAdapter(Context context, List<LearningLanguageSingleRow> listViewItemsList) {
        super(context, 0, listViewItemsList);

        this.listViewItemsList = listViewItemsList;
    }

    @NotNull
    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.learningLanguageView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.learningLanguageView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return this.listViewItemsList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private View learningLanguageView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(this.getContext())
                    .inflate(R.layout.dialog_learning_language_list, parent, false);
        }

        final LearningLanguageSingleRow singleRow = this.getItem(position);

        if (singleRow != null) {
            final TextView textViewFromLanguage = convertView.findViewById(R.id.dialog_from_language);
            textViewFromLanguage.setText(singleRow.getLearningLanguage());
        }

        return convertView;
    }
}
