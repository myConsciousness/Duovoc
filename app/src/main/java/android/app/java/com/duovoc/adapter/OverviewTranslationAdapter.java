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

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewTranslationAdapter.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 詳細画面におけるヒントリストの操作を定義したアダプタです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class OverviewTranslationAdapter extends BaseAdapter {

    /**
     * クラス名。
     */
    private static final String TAG = OverviewTranslationAdapter.class.getName();

    /**
     * アクティビティの状態。
     */
    private Context context;

    /**
     * 詳細画面におけるヒントリストの単数行リストオブジェクト。
     */
    private List<HintSingleRow> listViewItemsList;

    /**
     * 当該アダプタのコンストラクタ。
     *
     * @param context           アクティビティの状態。
     * @param listViewItemsList 詳細画面におけるヒントリストの単数行リストオブジェクト。
     */
    public OverviewTranslationAdapter(final Context context, final List<HintSingleRow> listViewItemsList) {
        this.context = context;
        this.listViewItemsList = listViewItemsList;
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
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.hints_list_items, null);

        final TextView textViewHint = row.findViewById(R.id.hint);
        textViewHint.setText(this.listViewItemsList.get(position).getHint());

        return row;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
