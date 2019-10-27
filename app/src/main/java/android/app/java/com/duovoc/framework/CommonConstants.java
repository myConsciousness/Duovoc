package android.app.java.com.duovoc.framework;

public final class CommonConstants {

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
     * ISO基準の日時形式。
     */
    public static final String FORMAT_ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private CommonConstants() {
    }
}
