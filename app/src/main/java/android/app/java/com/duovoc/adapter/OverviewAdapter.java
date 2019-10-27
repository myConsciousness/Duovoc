package android.app.java.com.duovoc.adapter;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.framework.StringHandler;
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

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewAdapter.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 一覧画面における概要リストの操作を定義したアダプタです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class OverviewAdapter extends BaseAdapter implements Filterable {

    /**
     * クラス名。
     */
    private static final String TAG = OverviewAdapter.class.getSimpleName();

    /**
     * アクティビティの状態。
     */
    private Context context;

    /**
     * 一覧画面における単数行リストオブジェクト。
     */
    private List<OverviewSingleRow> listViewItemsList;

    /**
     * 一覧画面における短数行リストオブジェクト。
     * 当該リストはフィルタリング用に使用されます。
     */
    private List<OverviewSingleRow> listViewItemsForFilter;

    /**
     * 一覧画面におけるリストのフィルタ操作オブジェクト。
     *
     * @see OverviewListViewFilter
     */
    private OverviewListViewFilter overviewListViewFilter;

    /**
     * 当該アダプタのコンストラクタ。
     *
     * @param context       アクティビティの状態。
     * @param listViewItems 一覧画面における単数行オブジェクト。
     */
    public OverviewAdapter(final Context context, final List<OverviewSingleRow> listViewItems) {

        if (context == null || listViewItems == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        this.context = context;
        this.listViewItemsList = listViewItems;
        this.listViewItemsForFilter = listViewItems;
    }

    @Override
    public int getCount() {
        return this.listViewItemsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listViewItemsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.listViewItemsList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.overview_list_items, null);

        final TextView textViewWord = row.findViewById(R.id.word);
        final TextView textViewLastPracticed = row.findViewById(R.id.lastPracticed);
        final TextView textViewLessonName = row.findViewById(R.id.lessonName);

        textViewWord.setText(this.listViewItemsList.get(position).getWord());
        textViewLastPracticed.setText(this.listViewItemsList.get(position).getLastPracticed());
        textViewLessonName.setText(this.listViewItemsList.get(position).getLessonName());

        return row;
    }

    @Override
    public Filter getFilter() {

        if (this.overviewListViewFilter == null) {
            this.overviewListViewFilter = new OverviewListViewFilter();
        }

        return this.overviewListViewFilter;
    }

    /**
     * 一覧画面における単数行リストオブジェクトを返却するGetterメソッド。
     *
     * @return 一覧画面における単数行リストオブジェクト。
     */
    public List<OverviewSingleRow> getListViewItemsList() {
        return this.listViewItemsList;
    }

    /**
     * 一覧画面におけるリストのフィルタ操作を行うための処理を定義したクラスです。
     * 一覧画面においてフィルタ対象文字列が入力された場合に動作します。
     *
     * @see Filter
     */
    private class OverviewListViewFilter extends Filter {

        @Override
        protected FilterResults performFiltering(final CharSequence charSequence) {

            final FilterResults filterResults = new FilterResults();
            filterResults.count = OverviewAdapter.this.listViewItemsForFilter.size();
            filterResults.values = OverviewAdapter.this.listViewItemsForFilter;

            if (charSequence != null && charSequence.length() > 0) {
                final String lowerCharSequence = StringHandler.trim(charSequence.toString().toLowerCase());
                final List<OverviewSingleRow> filters = new ArrayList<>();

                for (OverviewSingleRow item : OverviewAdapter.this.listViewItemsForFilter) {
                    if (item.getWord().toLowerCase().contains(lowerCharSequence)
                            || item.getNormalizedWord().toLowerCase().contains(lowerCharSequence)) {
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

            OverviewAdapter.this.listViewItemsList = (List<OverviewSingleRow>) filterResults.values;
            OverviewAdapter.this.notifyDataSetChanged();
        }
    }
}
