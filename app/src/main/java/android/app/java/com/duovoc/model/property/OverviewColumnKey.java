package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.CommonConstants;
import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringChecker;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.framework.model.CursorHandler;
import android.app.java.com.duovoc.holder.OverviewHolder;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ======================================================================
 * Project Name    : Duovoc
 * File Name       : OverviewColumnKey.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 論理モデル名「概要情報」のカラム項目を操作する処理を定義したEnumクラスです。
 * 概要情報を操作する際には当該Enumクラスを使用します。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public enum OverviewColumnKey implements IModelMapKey {

    /**
     * 物理カラム名「strength_bars」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#strength_bars
     */
    StrengthBars(Key.strength_bars) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getIntegerOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getStrengthBars());
        }
    },

    /**
     * 物理カラム名「infinitive」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#infinitive
     */
    Infinitive(Key.infinitive) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getInfinitive());
        }
    },

    /**
     * 物理カラム名「normalized_string」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#normalized_string
     */
    NormalizedString(Key.normalized_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getNormalizedString());
        }
    },

    /**
     * 物理カラム名「pos」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#pos
     */
    Pos(Key.pos) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getPos());
        }
    },

    /**
     * 物理カラム名「last_practiced_ms」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#last_practiced_ms
     */
    LastPracticedMs(Key.last_practiced_ms) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getLongOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLastPracticedMs());
        }
    },

    /**
     * 物理カラム名「skill」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#skill
     */
    Skill(Key.skill) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getSkill());
        }
    },

    /**
     * 物理カラム名「related_lexemes」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#related_lexemes
     */
    RelatedLexemes(Key.related_lexemes) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            final int index = cursor.getColumnIndex(this.getKeyName());

            if (index >= 0) {
                final String value = cursor.getString(cursor.getColumnIndex(this.getKeyName()));
                final List<String> stringList = new ArrayList<>();

                if (StringChecker.isEffectiveString(value)) {
                    final String[] relatedLexemes = StringHandler.split(value, CommonConstants.CHAR_SEPARATOR_PERIOD);
                    Collections.addAll(stringList, relatedLexemes);
                }

                modelMap.put(this, stringList);
            }
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {

            final List<String> values = overviewHolder.getRelatedLexemes();
            StringBuilder relatedLexemes = new StringBuilder();

            if (!values.isEmpty()) {
                values.forEach(relatedLexeme -> relatedLexemes.append(relatedLexeme).append(CommonConstants.CHAR_SEPARATOR_PERIOD));
                // 末尾のデリミタを削除
                relatedLexemes.setLength(relatedLexemes.length() - 1);
            }
            contentValues.put(this.getKeyName(), relatedLexemes.toString());
        }
    },

    /**
     * 物理カラム名「last_practiced」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#last_practiced
     */
    LastPracticed(Key.last_practiced) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLastPracticed());
        }
    },

    /**
     * 物理カラム名「strength」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#strength
     */
    Strength(Key.strength) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getDoubleIfNotEmpty(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getStrength());
        }
    },

    /**
     * 物理カラム名「skill_url_title」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#skill_url_title
     */
    SkillUrlTitle(Key.skill_url_title) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getSkillUrlTitle());
        }
    },

    /**
     * 物理カラム名「gender」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#gender
     */
    Gender(Key.gender) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getGender());
        }
    },

    /**
     * 物理カラム名「id」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#id
     */
    Id(Key.id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getId());
        }
    },

    /**
     * 物理カラム名「lexeme_id」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#lexeme_id
     */
    LexemeId(Key.lexeme_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLexemeId());
        }
    },

    /**
     * 物理カラム名「word_string」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#word_string
     */
    WordString(Key.word_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getWordString());
        }
    },

    /**
     * 物理カラム名「user_id」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#user_id
     */
    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getUserId());
        }
    },

    /**
     * 物理カラム名「language_string」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#language_string
     */
    LanguageString(Key.language_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLanguageString());
        }
    },

    /**
     * 物理カラム名「language」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#language
     */
    Language(Key.language) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLanguage());
        }
    },

    /**
     * 物理カラム名「from_language」を表す項目です。
     * 当該項目では以下の処理が定義されています。
     * <p>
     * 1, setModelMap(Cursor, ModelMap<OverviewColumnKey>, Object)
     * -> モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * <p>
     * 2, setContentValues(ContentValues, OverviewHolder)
     * -> モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     *
     * @see #setModelMap(Cursor, ModelMap)
     * @see #setContentValues(ContentValues, OverviewHolder)
     * @see Key#from_language
     */
    FromLanguage(Key.from_language) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {
            modelMap.put(this, CursorHandler.getStringOrThrow(cursor, this.getKeyName()));
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getFromLanguage());
        }
    };

    /**
     * カラムの物理名を格納するフィールドです。
     *
     * @see Key#strength_bars
     * @see Key#infinitive
     * @see Key#normalized_string
     * @see Key#pos
     * @see Key#last_practiced_ms
     * @see Key#skill
     * @see Key#related_lexemes
     * @see Key#last_practiced
     * @see Key#strength
     * @see Key#skill_url_title
     * @see Key#gender
     * @see Key#id
     * @see Key#lexeme_id
     * @see Key#word_string
     * @see Key#user_id
     * @see Key#language_string
     * @see Key#language
     * @see Key#from_language
     */
    private Key key;

    /**
     * 当該Enumのコンストラクタです。
     *
     * @see #getKeyName()
     * @see Key#strength_bars
     * @see Key#infinitive
     * @see Key#normalized_string
     * @see Key#pos
     * @see Key#last_practiced_ms
     * @see Key#skill
     * @see Key#related_lexemes
     * @see Key#last_practiced
     * @see Key#strength
     * @see Key#skill_url_title
     * @see Key#gender
     * @see Key#id
     * @see Key#lexeme_id
     * @see Key#word_string
     * @see Key#user_id
     * @see Key#language_string
     * @see Key#language
     * @see Key#from_language
     */
    OverviewColumnKey(Key key) {
        this.key = key;
    }

    /**
     * 当該項目に紐付くカラムの物理名を文字列として返却します。
     *
     * @return カラムの物理名。
     */
    public String getKeyName() {
        return this.key.name();
    }

    /**
     * モデルオブジェクトのselect操作をした際に取得結果をモデルマップへ格納する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param cursor   カーソルオブジェクト。
     * @param modelMap 値を設定するモデルマップ。
     */
    public abstract void setModelMap(Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap);

    /**
     * モデルオブジェクトの挿入処理をする際に挿入情報を設定する処理です。
     * 当該Enumクラスの項目は当該抽象メソッドを必ず実装する必要があります。
     *
     * @param contentValues  挿入情報を保持するオブジェクト。
     * @param overviewHolder 概要情報を保持するオブジェクト。
     */
    public abstract void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder);

    /**
     * 論理モデル名「概要情報」のカラムに紐付く物理名を定義したEnumクラスです。
     * 概要情報のカラムに紐付く物理名は必ず当該Enumクラスへ定義する必要があります。
     *
     * @see #getKeyName()
     */
    private enum Key {
        strength_bars,
        infinitive,
        normalized_string,
        pos,
        last_practiced_ms,
        skill,
        related_lexemes,
        last_practiced,
        strength,
        skill_url_title,
        gender,
        id,
        lexeme_id,
        word_string,
        user_id,
        language_string,
        language,
        from_language,
    }
}
