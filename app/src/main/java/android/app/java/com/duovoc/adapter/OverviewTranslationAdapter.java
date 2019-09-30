package android.app.java.com.duovoc.adapter;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.HintSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

final public class OverviewTranslationAdapter extends BaseAdapter {

    private Context context;
    private List<HintSingleRow> listViewItemsList;

    public OverviewTranslationAdapter(final Context context, final List<HintSingleRow> listViewItemsList) {
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
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.hints_list_items, null);

        final TextView textViewHint = row.findViewById(R.id.hint);
        textViewHint.setText(listViewItemsList.get(position).getHint());

        return row;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
