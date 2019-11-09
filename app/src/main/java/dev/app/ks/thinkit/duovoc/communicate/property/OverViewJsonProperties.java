package dev.app.ks.thinkit.duovoc.communicate.property;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dev.app.ks.thinkit.duovoc.framework.IJsonProperties;
import dev.app.ks.thinkit.duovoc.framework.StringChecker;
import dev.app.ks.thinkit.duovoc.model.holder.OverviewHolder;

public enum OverViewJsonProperties implements IJsonProperties {
    StrengthBars(Key.strength_bars) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setStrengthBars(this.getInt(jsonObject, key));
            }
        }
    },

    Infinitive(Key.infinitive) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setInfinitive(this.getString(jsonObject, key));
            }
        }
    },

    NormalizedString(Key.normalized_string) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setNormalizedString(this.getString(jsonObject, key));
            }
        }
    },

    Pos(Key.pos) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setPos(this.getString(jsonObject, key));
            }
        }
    },

    LastPracticedMs(Key.last_practiced_ms) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setLastPracticedMs(this.getLong(jsonObject, key));
            }
        }
    },

    Skill(Key.skill) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setSkill(this.getString(jsonObject, key));
            }
        }
    },

    RelatedLexemes(Key.related_lexemes) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();

            if (!jsonObject.isNull(key)) {
                final List<String> convertedList = this.convertToListFrom((JSONArray) jsonObject.get(key));
                overviewHolder.setRelatedLexemes(convertedList);
            }
        }
    },

    LastPracticed(Key.last_practiced) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setLastPracticed(this.getString(jsonObject, key));
            }
        }
    },

    Strength(Key.strength) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setStrength(this.getDouble(jsonObject, key));
            }
        }
    },

    SkillUrlTitle(Key.skill_url_title) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setSkillUrlTitle(this.getString(jsonObject, key));
            }
        }
    },

    Gender(Key.gender) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setGender(this.getString(jsonObject, key));
            }
        }
    },

    Id(Key.id) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setId(this.getString(jsonObject, key));
            }
        }
    },

    LexemeId(Key.lexeme_id) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setLexemeId(this.getString(jsonObject, key));
            }
        }
    },

    WordString(Key.word_string) {
        @Override
        public void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException {
            final String key = this.getKeyName();
            if (!jsonObject.isNull(key)) {
                overviewHolder.setWordString(this.getString(jsonObject, key));
            }
        }
    };

    private Key key;

    OverViewJsonProperties(Key key) {
        this.key = key;
    }

    protected String getString(JSONObject jsonObject, String key) throws JSONException {
        return (String) jsonObject.get(key);
    }

    protected int getInt(JSONObject jsonObject, String key) throws JSONException {
        return (Integer) jsonObject.get(key);
    }

    protected Long getLong(JSONObject jsonObject, String key) throws JSONException {
        return (Long) jsonObject.get(key);
    }

    protected Double getDouble(JSONObject jsonObject, String key) throws JSONException {
        return (Double) jsonObject.get(key);
    }

    protected List<String> convertToListFrom(JSONArray jsonArray) throws JSONException {

        final List<String> converted = new ArrayList<>();

        if (jsonArray == null) {
            return converted;
        }

        int jsonArraySize = jsonArray.length();
        for (int i = 0; i < jsonArraySize; i++) {

            final String item = jsonArray.get(i).toString();
            if (StringChecker.isEffectiveString(item)) {
                converted.add(item);
            }
        }

        return converted;
    }

    @Override
    public String getKeyName() {
        return this.key.name();
    }

    public abstract void setOverviewHolder(JSONObject jsonObject, OverviewHolder overviewHolder) throws JSONException;

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
    }
}
