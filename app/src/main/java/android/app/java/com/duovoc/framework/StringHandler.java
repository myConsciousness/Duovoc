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
public final class StringHandler {

    /**
     * 半角スペースを表す文字コード。
     */
    private static final char CODE_HALF_WIDTH_SPACE = '\u0020';

    /**
     * 全角スペースを表す文字コード。
     */
    private static final char CODE_FULL_WIDTH_SPACE = '\u3000';

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
     * @throws IllegalArgumentException 不正な入力情報を検知した際に発生します。
     */
    public static String[] split(final String value, final char separator) {

        if (value == null) {
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
     * @throws IllegalArgumentException 不正な入力情報を検知した際に発生します。
     * @see #split(String, char)
     */
    public static String[] split(final String value, final String separator) {

        if (value == null || separator == null) {
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
     * シーケンスの先頭から指定された文字列を検索し、最初に見つかった文字列を置換します。
     *
     * @param sequence    置換対象の文字列。
     * @param searchWord  検索文字列。
     * @param replaceWord 置換文字列。
     * @return 置換後文字列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     * @see #replace(String, String, String, int)
     */
    public static String replace(final String sequence, final String searchWord, final String replaceWord) {
        return replace(sequence, searchWord, replaceWord, 0);
    }

    /**
     * 指定された位置から文字列を検索し、最初に見つかった文字列を置換します。
     *
     * @param sequence    置換対象の文字列。
     * @param searchWord  検索文字列。
     * @param replaceWord 置換文字列。
     * @param fromIndex   検索開始位置。
     * @return 置換後文字列。
     * @throws IllegalArgumentException  不正な入力を検知した際に発生します。
     * @throws IndexOutOfBoundsException 指定された検索開始位置が検索範囲外の場合に発生します。
     * @see #replace(String, String, String)
     */
    public static String replace(
            final String sequence,
            final String searchWord,
            final String replaceWord,
            final int fromIndex) {

        if (!StringChecker.isEffectiveString(sequence)
                || !StringChecker.isEffectiveString(searchWord)
                || replaceWord == null) {
            // should  not be happened
            throw new IllegalArgumentException();
        }

        if (sequence.length() < fromIndex) {
            // should not be happened
            throw new IndexOutOfBoundsException();
        }

        final int foundIndex = sequence.indexOf(searchWord, fromIndex);
        if (foundIndex == -1) {
            return sequence;
        }

        return sequence.substring(0, foundIndex)
                + replaceWord
                + sequence.substring(foundIndex + searchWord.length());
    }

    /**
     * 渡された全シーケンスを検索し、見つかった全ての文字列を置換文字列に置換します。
     * 置換対象文字列がシーケンスに存在しない場合は渡された検索対象文字列を返却します。
     * 当該メソッドは正規表現による置換処理を行わないため、
     * String::replaceAll(String, String)と比較して高速に動作します。
     *
     * @param sequence    検索対象の文字列。
     * @param searchWord  検索文字列。
     * @param replaceWord 置換文字列。
     * @return 置換後文字列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     */
    public static String replaceAll(final String sequence, final String searchWord, final String replaceWord) {

        if (!StringChecker.isEffectiveString(sequence)
                || !StringChecker.isEffectiveString(searchWord)
                || replaceWord == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        int[] foundIndexes = searchAll(sequence, searchWord);
        if (foundIndexes.length == 0) {
            return sequence;
        }

        final StringBuilder replacedSequence = new StringBuilder();
        final int searchWordLength = searchWord.length();
        int position = 0;

        for (int i = 0, foundIndexesCount = foundIndexes.length; i < foundIndexesCount; i++) {
            if (i < foundIndexesCount - 1) {
                replacedSequence
                        .append(sequence.substring(position, foundIndexes[i + 1]))
                        .append(replaceWord)
                        .append(sequence.substring(foundIndexes[i] + searchWordLength, foundIndexes[i + 1]));

                // 次のインデックスを設定する
                position = foundIndexes[i + 1];

            } else {
                // 最後の置換処理
                replacedSequence
                        .append(sequence.substring(position, foundIndexes[i + 1]))
                        .append(replaceWord)
                        .append(sequence.substring(foundIndexes[i] + searchWordLength));
            }
        }

        return replacedSequence.toString();
    }

    /**
     * シーケンスの先頭から指定された文字列を検索し、
     * 最初に見つかった対象の先頭インデックスを返却します。
     * 検索対象文字列に検索文字列が存在しない場合は-1を返却します。
     *
     * @param sequence   検索対象の文字列。
     * @param searchWord 検索文字列。
     * @return 検索文字列が最初に出現したインデックス。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     * @see #search(String, String, int)
     */
    public static int search(final String sequence, final String searchWord) {
        return search(sequence, searchWord, 0);
    }

    /**
     * 指定された位置から文字列を検索し、
     * 最初に見つかった対象の先頭インデックスを返却します。
     * 検索対象文字列に検索文字列が存在しない場合は-1を返却します。
     *
     * @param sequence   検索対象の文字列。
     * @param searchWord 検索文字列。
     * @param fromIndex  検索開始位置。
     * @return 検索文字列が最初に出現したインデックス。
     * @throws IllegalArgumentException  不正な入力を検知した際に発生します。
     * @throws IndexOutOfBoundsException 指定された検索開始位置が検索範囲外の場合に発生します。
     * @see #search(String, String)
     */
    public static int search(final String sequence, final String searchWord, final int fromIndex) {

        if (!StringChecker.isEffectiveString(sequence)
                || !StringChecker.isEffectiveString(searchWord)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        if (sequence.length() < fromIndex) {
            // should not be happened
            throw new IndexOutOfBoundsException();
        }

        return sequence.indexOf(searchWord, fromIndex);
    }

    /**
     * シーケンスの先頭から指定された文字列を検索し、
     * 見つかった全ての出現インデックスを数値配列として返却します。
     * 検索対象文字列に検索文字列が存在しない場合は空の数値配列を返却します。
     *
     * @param sequence   検索対象の文字列。
     * @param searchWord 検索文字列。
     * @return 全ての出現インデックスを格納した数値配列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     */
    public static int[] searchAll(final String sequence, final String searchWord) {

        if (!StringChecker.isEffectiveString(sequence)
                || !StringChecker.isEffectiveString(searchWord)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        int startIndex = sequence.indexOf(searchWord);
        if (startIndex == -1) {
            return new int[]{};
        }

        final int searchWordLength = searchWord.length();
        int foundCount = 1;
        int[] foundIndexes = new int[8];
        foundIndexes[0] = startIndex;

        for (int i = 1, sequenceLength = sequence.length(); i < sequenceLength; i++) {

            final int nextStartIndex = startIndex + searchWordLength;
            if (sequenceLength < nextStartIndex) {
                break;
            }

            startIndex = sequence.indexOf(searchWord, nextStartIndex);
            if (startIndex == -1) {
                break;
            }

            foundIndexes[i] = startIndex;
            foundCount++;

            /*
             * 返却用の配列が一杯になった場合は、
             * 新たな配列（返却用配列の要素数 * 2）を生成する。
             */
            if (foundIndexes.length == foundCount) {
                int[] newArray = new int[foundIndexes.length * 2];
                System.arraycopy(foundIndexes, 0, newArray, 0, foundIndexes.length);
                foundIndexes = newArray;
            }
        }

        // 返却用配列から余分な領域を削除する
        if (foundIndexes.length > foundCount) {
            int[] newArray = new int[foundCount];
            System.arraycopy(foundIndexes, 0, newArray, 0, foundCount);
            foundIndexes = newArray;
        }

        return foundIndexes;
    }

    /**
     * 文字列の前方後方の空白文字を除去し返却します。
     * 空白文字以外の文字が文字列中に存在しない場合は空文字列を返却します。
     * 当該メソッドに空または空文字列が渡された場合はIllegalArgumentExceptionが発生します。
     * <p>
     * 判定に使用する空白文字は以下の通りです。
     * 1, '\u0020' (半角スペース文字)
     * 2, '\u3000' (全角スペース文字)
     *
     * @param sequence 前方後方の空白を除去する文字列。
     * @return 前方後方の空白を除去した文字列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     * @see #trimHead(String)
     * @see #trimTail(String)
     */
    public static String trim(final String sequence) {
        return trimTail(trimHead(sequence));
    }

    /**
     * 文字列の前方空白文字を除去し返却します。
     * 空白文字以外の文字が文字列中に存在しない場合は空文字列を返却します。
     * 当該メソッドに空または空文字列が渡された場合はIllegalArgumentExceptionが発生します。
     * <p>
     * 判定に使用する空白文字は以下の通りです。
     * 1, '\u0020' (半角スペース文字)
     * 2, '\u3000' (全角スペース文字)
     *
     * @param sequence 前方空白除去の対象文字列。
     * @return 前方空白除去後の文字列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     */
    public static String trimHead(final String sequence) {

        if (sequence == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        if (sequence.length() <= 0) {
            return "";
        }

        for (int i = 0, sequenceLength = sequence.length(); i < sequenceLength; i++) {
            final char character = sequence.charAt(i);

            if (character > CODE_HALF_WIDTH_SPACE
                    && character != CODE_FULL_WIDTH_SPACE) {
                return sequence.substring(i);
            }
        }

        return "";
    }

    /**
     * 文字列の後方空白文字を除去し返却します。
     * 空白文字以外の文字が文字列中に存在しない場合は空文字列を返却します。
     * 当該メソッドに空または空文字列が渡された場合はIllegalArgumentExceptionが発生します。
     * <p>
     * 判定に使用する空白文字は以下の通りです。
     * 1, '\u0020' (半角スペース文字)
     * 2, '\u3000' (全角スペース文字)
     *
     * @param sequence 後方空白除去の対象文字列。
     * @return 後方空白除去後の文字列。
     * @throws IllegalArgumentException 不正な入力を検知した際に発生します。
     */
    public static String trimTail(final String sequence) {

        if (sequence == null) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        if (sequence.length() <= 0) {
            return "";
        }

        for (int i = sequence.length() - 1; i >= 0; i--) {
            final char character = sequence.charAt(i);

            if (character > CODE_HALF_WIDTH_SPACE
                    && character != CODE_FULL_WIDTH_SPACE) {
                return sequence.substring(0, i + 1);
            }
        }

        return "";
    }

    /**
     * 入力情報として渡された文字列を結合し、
     * 結合した結果を文字列として返却します。
     * 当該メソッドでは文字列は第一引数の区切り文字毎に結合されます。
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
