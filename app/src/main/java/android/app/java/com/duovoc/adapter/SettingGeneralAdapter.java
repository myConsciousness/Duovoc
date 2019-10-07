package android.app.java.com.duovoc.adapter;

import android.annotation.SuppressLint;
import android.app.java.com.duovoc.R;
import android.app.java.com.duovoc.holder.SettingGeneralSingleRow;
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
 * File Name       : SettingGeneralAdapter.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 設定画面における総合設定リストの操作を定義したアダプタです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class SettingGeneralAdapter extends BaseAdapter {

    /**
     * クラス名。
     */
    private static final String TAG = SettingGeneralAdapter.class.getSimpleName();

    /**
     * アクティビティの状態。
     */
    private Context context;

    /**
     * 設定画面における総合設定リストの単数行オブジェクト。
     */
    private List<SettingGeneralSingleRow> listViewItemsList;

    /**
     * 当該アダプタのコンストラクタ。
     *
     * @param context       アクティビティの状態。
     * @param listViewItems 設定画面における総合設定リストの単数行オブジェクト。
     */
    public SettingGeneralAdapter(final Context context, final List<SettingGeneralSingleRow> listViewItems) {

        if (context == null || listViewItems == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        this.context = context;
        this.listViewItemsList = listViewItems;
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
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.setting_general_list_items, null);

        final TextView textViewWord = row.findViewById(R.id.setting_list_item_title);
        final TextView textViewLastPracticed = row.findViewById(R.id.setting_list_item_summary);

        textViewWord.setText(this.listViewItemsList.get(position).getTitle());
        textViewLastPracticed.setText(this.listViewItemsList.get(position).getSummary());

        return row;
    }
}
