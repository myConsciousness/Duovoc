package android.app.java.com.duovoc.framework.model.holder;

import java.util.EnumMap;

public abstract class ModelAccessor<E extends Enum<E>> {

    /**
     * クラス名。
     */
    private static final String TAG = ModelAccessor.class.getSimpleName();

    /**
     * モデルアクセサのカレント情報を保持するマップです。
     * 当該抽象クラスを継承したアクセサクラスは、
     * 当該カレント情報を参照することで値の取得と設定を行います。
     * カレント情報に紐づくキーの型は当該クラスのインスタンス生成時に行われます。
     */
    private final EnumMap<E, Object> currentModelMap;

    /**
     * 当該抽象クラスのコンストラクタです。
     * モデル項目のカレント情報を初期化する処理を行うため、
     * 当該抽象クラスを継承したアクセサクラスは当該コンストラクタを必ず呼び出す必要があります。
     *
     * @param keyType カレント情報に設定するキーの型
     */
    protected ModelAccessor(Class<E> keyType) {
        this.currentModelMap = new EnumMap<>(keyType);
        this.initializeItems();
    }

    /**
     * カレント情報に格納する項目の初期値を設定するメソッドです。
     * 当該抽象クラスを継承したアクセサクラスは当該メソッドを必ず実装する必要があります。
     * 当該抽象メソッドは当該抽象クラスのインスタンス生成時に実行されるため、
     * 継承したクラスで改めて当該抽象メソッドを呼び出す処理を定義する必要はありません。
     */
    protected abstract void initializeItems();
}
