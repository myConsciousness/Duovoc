package android.app.java.com.duovoc.model.property;

import android.app.java.com.duovoc.framework.IModelMapKey;
import android.app.java.com.duovoc.framework.ModelMap;
import android.app.java.com.duovoc.framework.StringHandler;
import android.app.java.com.duovoc.holder.OverviewHolder;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum OverviewColumnKey implements IModelMapKey {
    StrengthBars(Key.strength_bars) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setIntIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getStrengthBars());
        }
    },

    Infinitive(Key.infinitive) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getInfinitive());
        }
    },

    NormalizedString(Key.normalized_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getNormalizedString());
        }
    },

    Pos(Key.pos) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getPos());
        }
    },

    LastPracticedMs(Key.last_practiced_ms) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setLongIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLastPracticedMs());
        }
    },

    Skill(Key.skill) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getSkill());
        }
    },

    RelatedLexemes(Key.related_lexemes) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            final int index = cursor.getColumnIndex(this.getKeyName());

            if (index >= 0) {
                final String value = cursor.getString(cursor.getColumnIndex(this.getKeyName()));
                final String[] relatedLexemes = value.split(FORMAT_DELIMITER);
                final List<String> stringList = new ArrayList<>();

                for (String relatedLexeme : relatedLexemes) {
                    stringList.add(relatedLexeme);
                }

                modelMap.put(this, StringHandler.removeEmptyValue(stringList));
            }
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {

            final List<String> values = overviewHolder.getRelatedLexemes();
            StringBuilder relatedLexemes = new StringBuilder();

            if (!values.isEmpty()) {
                for (String relatedLexeme : values) {
                    relatedLexemes.append(relatedLexeme).append(FORMAT_DELIMITER);
                }

                // 末尾のデリミタを削除
                relatedLexemes.setLength(relatedLexemes.length() - 1);
            }

            contentValues.put(this.getKeyName(), relatedLexemes.toString());
        }
    },

    LastPracticed(Key.last_practiced) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLastPracticed());
        }
    },

    Strength(Key.strength) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setDoubleIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getStrength());
        }
    },

    SkillUrlTitle(Key.skill_url_title) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getSkillUrlTitle());
        }
    },

    Gender(Key.gender) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getGender());
        }
    },

    Id(Key.id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getId());
        }
    },

    LexemeId(Key.lexeme_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLexemeId());
        }
    },

    WordString(Key.word_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getWordString());
        }
    },

    UserId(Key.user_id) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getUserId());
        }
    },

    LanguageString(Key.language_string) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLanguageString());
        }
    },


    Language(Key.language) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getLanguage());
        }
    },

    FromLanguage(Key.from_language) {
        @Override
        public void setModelMap(final Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap) {

            this.setStringIfNotEmpty(cursor, modelMap);
        }

        @Override
        public void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder) {
            contentValues.put(this.getKeyName(), overviewHolder.getFromLanguage());
        }
    };

    private Key key;
    OverviewColumnKey(Key key) {
        this.key = key;
    }

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

    private static final String FORMAT_DELIMITER = ",";

    protected void setStringIfNotEmpty(final Cursor cursor, final ModelMap<OverviewColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getString(index));
        }
    }

    protected void setIntIfNotEmpty(final Cursor cursor, final ModelMap<OverviewColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getInt(index));
        }
    }

    protected void setDoubleIfNotEmpty(final Cursor cursor, final ModelMap<OverviewColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getDouble(index));
        }
    }

    protected void setLongIfNotEmpty(final Cursor cursor, final ModelMap<OverviewColumnKey, Object> modelMap) {

        final int index = cursor.getColumnIndex(this.getKeyName());

        if (index >= 0) {
            modelMap.put(this, cursor.getLong(index));
        }
    }

    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setModelMap(Cursor cursor, ModelMap<OverviewColumnKey, Object> modelMap);
    public abstract void setContentValues(ContentValues contentValues, OverviewHolder overviewHolder);
}
