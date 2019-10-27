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

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewRelatedLexemesAdapter.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 詳細画面における語彙素リストの操作を定義したアダプタです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class OverviewRelatedLexemesAdapter extends BaseAdapter {

    /**
     * クラス名。
     */
    private static final String TAG = OverviewRelatedLexemesAdapter.class.getSimpleName();

    /**
     * 語彙素リストで表示する項目がない場合に表示する初期値。
     */
    private static final String VALUE_UNDEFINED = "-";

    /**
     * アクティビティの状態。
     */
    private Context context;

    /**
     * 詳細画面における語彙素リストの単数行リストオブジェクト。
     */
    private List<RelatedLexemesSingleRow> listViewItemsList;

    /**
     * 当該アダプタのコンストラクタ。
     *
     * @param context           アクティビティの状態。
     * @param listViewItemsList 詳細画面における語彙素リストの単数行リストオブジェクト。
     */
    public OverviewRelatedLexemesAdapter(final Context context, final List<RelatedLexemesSingleRow> listViewItemsList) {
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
        @SuppressLint("ViewHolder") final View row = layoutInflater.inflate(R.layout.related_lexemes_list_items, null);

        final TextView textViewWord = row.findViewById(R.id.relatedLexeme);
        final TextView textViewLessonName = row.findViewById(R.id.relatedLessonName);

        textViewWord.setText(this.listViewItemsList.get(position).getWord());
        textViewLessonName.setText(this.listViewItemsList.get(position).getLessonName());

        return row;
    }

    /**
     * 詳細画面における語彙素リストの単数行リストオブジェクトを返却するGetterメソッド。
     *
     * @return 詳細画面における語彙素リストの単数行リストオブジェクト。
     */
    public List<RelatedLexemesSingleRow> getListViewItemsList() {
        return this.listViewItemsList;
    }

    @Override
    public boolean isEnabled(int position) {
        final String word = this.listViewItemsList.get(position).getWord();
        return !VALUE_UNDEFINED.equals(word);
    }
}
