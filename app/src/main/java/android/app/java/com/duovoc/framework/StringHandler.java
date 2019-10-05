package android.app.java.com.duovoc.framework;

import java.util.Arrays;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : StringHandler.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/09/30
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 文字列を操作する汎用的な機能を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
final public class StringHandler {

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private StringHandler() {
    }

    /**
     * 入力情報を基に区切り文字で分割した文字列のリストを返却します。
     * 当該メソッドは正規表現による分割処理を行わないため、
     * String::split(String)と比較して高速に動作します。
     *
     * @param value     分割対象文字列。
     * @param separator 区切り文字。
     * @return 分割された文字列のリスト。
     * @throws IllegalArgumentException 無効な入力情報を検知した際に発生します。
     */
    public static String[] split(final String value, final char separator) {

        if (!StringChecker.isEffectiveString(value)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final char[] charArray = value.toCharArray();
        final int charArrayLength = charArray.length;
        int countSeparator = 1;

        // 文字列終端に位置する区切り文字はカウントに含めない
        for (int i = 0, length = charArrayLength - 1; i < length; i++) {
            if (charArray[i] == separator) {
                countSeparator++;
            }
        }

        final String[] result = new String[countSeparator];
        int splitedCount = 0;
        int offset = 0;

        for (int i = 0; i <= charArrayLength; i++) {
            if (i == charArrayLength || charArray[i] == separator) {
                final String splitedValue = String.valueOf(charArray, offset, i - offset);
                result[splitedCount++] = splitedValue;
                offset = i + 1;
            }

            // 以下終了条件はString::split(String)を踏襲する
            if (i < charArrayLength
                    && i >= (charArrayLength - 1)
                    && charArray[i] == separator) {
                break;
            }
        }

        return result;
    }

    /**
     * 入力情報を基に区切り文字で分割した文字列のリストを返却します。
     * 当該メソッドは正規表現による分割処理を行わないため、
     * String::split(String)と比較して高速に動作します。
     *
     * @param value     分割対象文字列。
     * @param separator 区切り文字。
     * @return 分割された文字列のリスト。
     * @throws IllegalArgumentException 無効な入力情報を検知した際に発生します。
     * @see #split(String, char)
     */
    public static String[] split(final String value, final String separator) {

        if (!StringChecker.isEffectiveString(value) ||
                !StringChecker.isEffectiveString(separator)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        if (separator.length() == 1) {
            return split(value, separator.charAt(0));
        }

        final char[] charArray = value.toCharArray();
        final int charArrayLength = charArray.length;

        final char[] separatorCharArray = separator.toCharArray();
        final int separatorCharArrayLength = separatorCharArray.length;
        final char separatorAtTheFront = separatorCharArray[0];

        String[] result = new String[8];
        int splitedCount = 0;
        int offset = 0;

        for (int i = 0; i <= charArrayLength; i++) {
            if (i == charArrayLength) {

                /*
                 * 残りの文字列を返却用リストに格納します。
                 * 但し、文字列終端に位置する分割文字を設定しない仕様に合わせるため、
                 * 文字列の最後尾で区切り文字と一致した場合は何もしません。
                 */
                if (i != offset) {
                    result[splitedCount] = String.valueOf(charArray, offset, i - offset);
                    splitedCount++;
                }
            } else if (charArray[i] == separatorAtTheFront) {

                boolean equal = true;
                for (int j = 0; j < separatorCharArrayLength; j++) {
                    // 現在のインデックスから分割文字数分の文字リストが等しいか判定する
                    if ((i + j) >= charArrayLength
                            || separatorCharArray[j] != charArray[i + j]) {
                        equal = false;
                        break;
                    }
                }

                if (equal) {
                    // 分割対象文字列と分割文字が等価の場合は要素数0の配列を返却する
                    if (charArrayLength == separatorCharArrayLength) {
                        break;
                    }

                    result[splitedCount] = String.valueOf(charArray, offset, i - offset);
                    i += separatorCharArrayLength - 1;
                    splitedCount++;
                    offset = i + 1;

                    /*
                     * 返却用の配列が一杯になった場合は、
                     * 新たな配列（返却用配列の要素数*2）を生成する
                     */
                    if (result.length <= (splitedCount + 1)) {
                        String[] newArray = new String[result.length * 2];
                        System.arraycopy(result, 0, newArray, 0, result.length);
                        result = newArray;
                    }
                }
            }
        }

        // 返却用配列から余分な領域を削除する
        if (result.length > splitedCount) {
            String[] newArray = new String[splitedCount];
            System.arraycopy(result, 0, newArray, 0, splitedCount);
            result = newArray;
        }

        return result;
    }

    /**
     * 入力情報として渡された文字列を結合し、
     * 結合した結果を文字列として返却します。
     * 当該メソッドでは文字列は区切り文字毎に結合されます。
     *
     * @param separator 区切り文字。
     * @param sequences 結合する文字列。
     * @return 結合された文字列。
     * @throws IllegalArgumentException 結合対象の文字列リストがnullの場合に発生します。
     */
    public static String concatSequence(final char separator, final String... sequences) {

        if (sequences == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final String stringSeparator = String.valueOf(separator);
        final StringBuilder sb = new StringBuilder();

        if (StringChecker.isEffectiveString(stringSeparator)) {
            Arrays.stream(sequences).forEach(sequence -> sb.append(sequence).append(separator));
            sb.setLength(sb.length() - stringSeparator.length());
        } else {
            Arrays.stream(sequences).forEach(sb::append);
        }

        return sb.toString();
    }
}
