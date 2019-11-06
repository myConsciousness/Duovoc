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

public final class SwitchLearningLanguageAdapter extends ArrayAdapter<LearningLanguageSingleRow> {

    private static final String TAG = SwitchLearningLanguageAdapter.class.getName();

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
        return this.learningLanguageView(
                R.layout.dialog_learning_language_list,
                position,
                convertView,
                parent
        );
    }

    @Override
    public View getDropDownView(int position, View convertView, @NotNull ViewGroup parent) {
        return this.learningLanguageView(
                R.layout.dialog_learning_language_drop_down_list,
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

    private View learningLanguageView(final int layout, final int position, View convertView, final ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(this.getContext())
                    .inflate(layout, parent, false);
        }

        final LearningLanguageSingleRow singleRow = this.getItem(position);

        if (singleRow != null) {
            final TextView textViewLearningLanguage = convertView.findViewById(R.id.dialog_learning_language);
            textViewLearningLanguage.setText(singleRow.getLearningLanguage());
        }

        return convertView;
    }
}
