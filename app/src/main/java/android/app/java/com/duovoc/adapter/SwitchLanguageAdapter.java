package android.app.java.com.duovoc.adapter;

import android.app.java.com.duovoc.holder.FromLanguageSingleRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

final public class SwitchLanguageAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    /**
     * 学習言語変更画面における学習時使用言語リストの単数行オブジェクト。
     */
    private List<FromLanguageSingleRow> listViewItemsList;


    public SwitchLanguageAdapter(Context context,
                                 List<FromLanguageSingleRow> listViewItemsList) {

        this.inflater = LayoutInflater.from(context);
        this.listViewItemsList = listViewItemsList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return convertView;
    }

    @Override
    public int getCount() {
        return this.listViewItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}