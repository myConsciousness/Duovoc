create table if not exists overview_information(
	row_id INTEGER NOT NULL PRIMARY KEY autoincrement,
	id TEXT NOT NULL UNIQUE,
	user_id NOT NULL,
	language_string TEXT NOT NULL,
	language TEXT NOT NULL,
	from_language TEXT NOT NULL,
	lexeme_id TEXT,
	related_lexemes TEXT,
	strength_bars INTEGER,
	infinitive TEXT,
	word_string TEXT,
	english_word TEXT,
	normalized_string TEXT,
	pos TEXT,
	last_practiced_ms INTEGER,
	skill TEXT,
	last_practiced TEXT,
	display_last_practiced TEXT,
	strength REAL,
	skill_url_title TEXT,
	gender TEXT,
	modified_datetime TEXT NOT NULL
)
;

commit;