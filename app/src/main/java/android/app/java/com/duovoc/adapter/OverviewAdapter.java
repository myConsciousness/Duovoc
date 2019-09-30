package android.app.java.com.duovoc.adapter;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.OverviewSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

final public class OverviewAdapter extends BaseAdapter implements Filterable {

    private Context context;

    private List<OverviewSingleRow> listViewItemsList;
    private List<OverviewSingleRow> listViewItemsForFilter;
    private CustomFilter customFilter;

    public OverviewAdapter(final Context context, final List<OverviewSingleRow> listViewItems) {
        this.context = context;
        this.listViewItemsList = listViewItems;
        this.listViewItemsForFilter = listViewItems;
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
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.overview_list_items, null);

        final TextView textViewWord = row.findViewById(R.id.word);
        final TextView textViewLastPracticed = row.findViewById(R.id.lastPracticed);
        final TextView textViewLessonName = row.findViewById(R.id.lessonName);

        textViewWord.setText(listViewItemsList.get(position).getWord());
        textViewLastPracticed.setText(listViewItemsList.get(position).getLastPracticed());
        textViewLessonName.setText(listViewItemsList.get(position).getLessonName());

        return row;
    }

    @Override
    public Filter getFilter() {

        if (this.customFilter == null) {
            customFilter = new CustomFilter();
        }

        return customFilter;
    }

    public List<OverviewSingleRow> getListViewItemsList() {
        return this.listViewItemsList;
    }

    private class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence charSequence) {

            FilterResults filterResults = new FilterResults();
            filterResults.count = listViewItemsForFilter.size();
            filterResults.values = listViewItemsForFilter;

            if (charSequence != null && charSequence.length() > 0) {

                final String lowerCharSequence = charSequence.toString().toLowerCase();
                final List<OverviewSingleRow> filters = new ArrayList<>();

                for (OverviewSingleRow item : listViewItemsForFilter) {
                    if (item.getWord().toLowerCase().contains(lowerCharSequence)) {
                        filters.add(item);
                    }
                }

                filterResults.count = filters.size();
                filterResults.values = filters;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(
                final CharSequence charSequence,
                final FilterResults filterResults) {

            listViewItemsList = (List<OverviewSingleRow>) filterResults.values;
            notifyDataSetChanged();
        }
    }
}
