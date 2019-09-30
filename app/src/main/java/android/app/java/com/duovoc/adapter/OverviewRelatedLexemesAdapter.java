package android.app.java.com.duovoc.adapter;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.RelatedLexemesSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

final public class OverviewRelatedLexemesAdapter extends BaseAdapter {

    private static final String TAG = OverviewRelatedLexemesAdapter.class.getSimpleName();

    private static final String VALUE_UNDEFINED = "-";

    private Context context;
    private List<RelatedLexemesSingleRow> listViewItemsList;

    public OverviewRelatedLexemesAdapter(final Context context, final List<RelatedLexemesSingleRow> listViewItemsList) {
        this.context = context;
        this.listViewItemsList = listViewItemsList;
    }

    @Override
    public int getCount() {
        return listViewItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return listViewItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listViewItemsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.related_lexemes_list_items, null);

        final TextView textViewWord = row.findViewById(R.id.relatedLexeme);
        final TextView textViewLessonName = row.findViewById(R.id.relatedLessonName);

        textViewWord.setText(listViewItemsList.get(position).getWord());
        textViewLessonName.setText(listViewItemsList.get(position).getLessonName());

        return row;
    }

    public List<RelatedLexemesSingleRow> getListViewItemsList() {
        return this.listViewItemsList;
    }

    @Override
    public boolean isEnabled(int position) {
        final String word = this.listViewItemsList.get(position).getWord();
        return !VALUE_UNDEFINED.equals(word);
    }
}
