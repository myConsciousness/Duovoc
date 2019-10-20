package android.app.java.com.duovoc.framework;

final public class CommonConstants {

    /**
     * char型のスラッシュ。
     */
    public static final char CHAR_SEPARATOR_SLASH = '/';

    /**
     * char型のピリオド。
     */
    public static final char CHAR_SEPARATOR_PERIOD = ',';

    /**
     * String型のピリオド。
     */
    public static final String STRING_SEPARATOR_PERIOD = ",";

    /**
     * String型のシステム情報から取得した改行コード。
     */
    public static final String SYSTEM_BR = System.getProperty("line.separator");

    /**
     * ダミー文字列。
     */
    public static final String DUMMY = "dummy";

    /**
     * SQLiteデータベースにおける日時の形式。
     */
    public static final String FORMAT_DATETIME_ON_DATABASE = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日時の形式。
     */
    public static final String FORMAT_DATETIME = "yyyyMMddHHmmss";

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private CommonConstants() {
    }
}
