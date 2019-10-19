package android.app.java.com.duovoc.framework;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ======================================================================
 * Project Name    : Common
 * File Name       : CalendarHandler.java
 * Encoding        : UTF-8
 * Creation Date   : 2019/10/19
 * <p>
 * Copyright © 2019 Kato Shinya. All rights reserved.
 * <p>
 * This source code or any portion thereof must not be
 * reproduced or used in any manner whatsoever.
 * ======================================================================
 * <p>
 * 日付と日時を操作する汎用的な機能を定義したユーティリティクラスです。
 *
 * @author Kato Shinya
 * @version 1.0
 * @since 1.0
 */
public final class CalendarHandler {

    /**
     * 当該クラスで扱う日付の形式です。
     */
    private static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * 1日をミリ秒で表した値です。
     * 当該フィールドの値は以下の演算結果を保持します。
     * <p>
     * 1, 1日 -> 24時間
     * 2, 1時間 -> 60分
     * 3, 1分 -> 60秒
     * 4, 1秒 -> 1000ミリ秒
     * <p>
     * 従って、1000×60×60×24 = 86400000
     */
    private static final long DayInMilliseconds = 86400000L;

    /**
     * 当該クラスのコンストラクタです。
     * 当該クラスはインスタンス生成を必要としないため修飾子をprivate指定しています。
     */
    private CalendarHandler() {
    }

    /**
     * 引数として渡された日付に加減数を足し、
     * 加減された日付を文字列として返却します。
     * <p>
     * 日付を未来へ進める場合は正の整数を指定し、
     * 日付を過去へ進める場合は負の整数を各引数に指定してください。
     * 操作する必要のない引数には0を指定してください。
     *
     * @param date            操作対象の日付。
     * @param adjustmentYear  加減する年数。
     * @param adjustmentMonth 加減する月数。
     * @param adjustmentDay   加減する日数。
     * @return 加減された日付。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static String plusDate(
            final String date,
            final int adjustmentYear,
            final int adjustmentMonth,
            final int adjustmentDay) {

        if (!isEffectiveDate(date)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int year = Integer.parseInt(date.substring(0, 4));
        final int month = Integer.parseInt(date.substring(4, 6)) - 1;
        final int day = Integer.parseInt(date.substring(6, 8));

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);

        calendar.add(Calendar.YEAR, adjustmentYear);
        calendar.add(Calendar.MONTH, adjustmentMonth);
        calendar.add(Calendar.DATE, adjustmentDay);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 引数として渡された日付の年に加減数を足し、
     * 加減された日付を文字列として返却します。
     * <p>
     * 日付を未来へ進める場合は正の整数を指定し、
     * 日付を過去へ進める場合は負の整数を引数に指定してください。
     *
     * @param date           操作対象の日付。
     * @param adjustmentYear 加減する年数。
     * @return 加減された日付。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static String plusYear(final String date, final int adjustmentYear) {

        if (!isEffectiveDate(date)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int year = Integer.parseInt(date.substring(0, 4));
        final int month = Integer.parseInt(date.substring(4, 6)) - 1;
        final int day = Integer.parseInt(date.substring(6, 8));

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);

        calendar.add(Calendar.YEAR, adjustmentYear);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 引数として渡された日付の月に加減数を足し、
     * 加減された日付を文字列として返却します。
     * <p>
     * 日付を未来へ進める場合は正の整数を指定し、
     * 日付を過去へ進める場合は負の整数を引数に指定してください。
     *
     * @param date            操作対象の日付。
     * @param adjustmentMonth 加減する月数。
     * @return 加減された日付。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static String plusMonth(final String date, final int adjustmentMonth) {

        if (!isEffectiveDate(date)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int year = Integer.parseInt(date.substring(0, 4));
        final int month = Integer.parseInt(date.substring(4, 6)) - 1;
        final int day = Integer.parseInt(date.substring(6, 8));

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);

        calendar.add(Calendar.MONTH, adjustmentMonth);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 引数として渡された日付の日に加減数を足し、
     * 加減された日付を文字列として返却します。
     * <p>
     * 日付を未来へ進める場合は正の整数を指定し、
     * 日付を過去へ進める場合は負の整数を引数に指定してください。
     *
     * @param date          操作対象の日付。
     * @param adjustmentDay 加減する日数。
     * @return 加減された日付。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static String plusDay(final String date, final int adjustmentDay) {

        if (!isEffectiveDate(date)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int year = Integer.parseInt(date.substring(0, 4));
        final int month = Integer.parseInt(date.substring(4, 6)) - 1;
        final int day = Integer.parseInt(date.substring(6, 8));

        final Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);

        calendar.add(Calendar.DATE, adjustmentDay);

        @SuppressLint("SimpleDateFormat") final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 引数として渡された2つの入力値から経過期間を取得し数値型として返却します。
     * 基準日よりも比較対象の日付が未来にある場合は負数を返却します。
     * 当該メソッドの入力値はyyyyMMdd形式である必要があります。
     *
     * @param baseDate       基準となる日付 (yyyyMMdd形式)。
     * @param comparisonDate 比較対象の日付 (yyyyMMdd形式)。
     * @return 基準日から比較対象日付を引いた経過期間。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static int getElapsedPeriodFromDate(final String baseDate, final String comparisonDate) {

        if (!isEffectiveDate(baseDate) || !isEffectiveDate(comparisonDate)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int baseYear = Integer.parseInt(baseDate.substring(0, 4));
        final int baseMonth = Integer.parseInt(baseDate.substring(4, 6)) - 1;
        final int baseDay = Integer.parseInt(baseDate.substring(6, 8));

        final int comparisonYear = Integer.parseInt(comparisonDate.substring(0, 4));
        final int comparisonMonth = Integer.parseInt(comparisonDate.substring(4, 6)) - 1;
        final int comparisonDay = Integer.parseInt(comparisonDate.substring(6, 8));

        final Calendar baseCalendar = Calendar.getInstance();
        final Calendar comparisonCalendar = Calendar.getInstance();
        baseCalendar.clear();
        comparisonCalendar.clear();

        baseCalendar.set(baseYear, baseMonth, baseDay);
        comparisonCalendar.set(comparisonYear, comparisonMonth, comparisonDay);

        final long elapsedTimeMs
                = baseCalendar.getTimeInMillis() - comparisonCalendar.getTimeInMillis();

        return (int) (elapsedTimeMs / DayInMilliseconds);
    }

    /**
     * 引数として渡された2つの入力値から経過期間を取得し数値型として返却します。
     * 基準日時よりも比較対象の日時が未来にある場合は負数を返却します。
     * 当該メソッドの入力値はyyyyMMddHHmmss形式である必要があります。
     *
     * @param baseDatetime       基準となる日時 (yyyyMMddHHmmss形式)。
     * @param comparisonDatetime 比較対象の日時 (yyyyMMddHHmmss形式)。
     * @return 基準日時から比較対象日時を引いた経過期間。
     * @throws IllegalArgumentException 不正な入力値を検知した際に発生します。
     */
    public static int getElapsedPeriodFromDatetime(final String baseDatetime, final String comparisonDatetime) {

        if (!isEffectiveDatetime(baseDatetime) || !isEffectiveDatetime(comparisonDatetime)) {
            // should not be happened
            throw new IllegalArgumentException();
        }

        final int baseYear = Integer.parseInt(baseDatetime.substring(0, 4));
        final int baseMonth = Integer.parseInt(baseDatetime.substring(4, 6)) - 1;
        final int baseDay = Integer.parseInt(baseDatetime.substring(6, 8));
        final int baseHour = Integer.parseInt(baseDatetime.substring(8, 10));
        final int baseMinute = Integer.parseInt(baseDatetime.substring(10, 12));
        final int baseSecond = Integer.parseInt(baseDatetime.substring(12, 14));

        final int comparisonYear = Integer.parseInt(comparisonDatetime.substring(0, 4));
        final int comparisonMonth = Integer.parseInt(comparisonDatetime.substring(4, 6)) - 1;
        final int comparisonDay = Integer.parseInt(comparisonDatetime.substring(6, 8));
        final int comparisonHour = Integer.parseInt(comparisonDatetime.substring(8, 10));
        final int comparisonMinute = Integer.parseInt(comparisonDatetime.substring(10, 12));
        final int comparisonSecond = Integer.parseInt(comparisonDatetime.substring(12, 14));

        final Calendar baseCalendar = Calendar.getInstance();
        final Calendar comparisonCalendar = Calendar.getInstance();
        baseCalendar.clear();
        comparisonCalendar.clear();

        baseCalendar.set(
                baseYear,
                baseMonth,
                baseDay,
                baseHour,
                baseMinute,
                baseSecond
        );

        comparisonCalendar.set(
                comparisonYear,
                comparisonMonth,
                comparisonDay,
                comparisonHour,
                comparisonMinute,
                comparisonSecond
        );

        final long elapsedTimeMs
                = baseCalendar.getTimeInMillis() - comparisonCalendar.getTimeInMillis();

        return (int) (elapsedTimeMs / DayInMilliseconds);
    }

    /**
     * 引数として渡された文字列が以下の規則に従う形式か検査し、
     * 検査結果を真偽値として返却します。
     * <p>
     * 1, 必須チェック
     * 2, 桁数チェック (8桁)
     * 3, 日付変換チェック
     *
     * @param date 検査対象の文字列。
     * @return 日付として有効な値の場合は {@code true}、それ以外は{@code false}
     */
    private static boolean isEffectiveDate(final String date) {

        if (!StringChecker.isEffectiveString(date) || date.length() != 8) {
            return false;
        }

        final int year = Integer.parseInt(date.substring(0, 4));
        final int month = Integer.parseInt(date.substring(4, 6)) - 1;
        final int day = Integer.parseInt(date.substring(6, 8));

        final Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(year, month, day);

        try {
            calendar.getTime();
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    /**
     * 引数として渡された文字列が以下の規則に従う形式か検査し、
     * 検査結果を真偽値として返却します。
     * <p>
     * 1, 必須チェック
     * 2, 桁数チェック (14桁)
     * 3, 日時変換チェック
     *
     * @param datetime 検査対象の文字列。
     * @return 日時として有効な値の場合は {@code true}、それ以外は{@code false}
     */
    private static boolean isEffectiveDatetime(final String datetime) {

        if (!StringChecker.isEffectiveString(datetime) || datetime.length() != 14) {
            return false;
        }

        final int year = Integer.parseInt(datetime.substring(0, 4));
        final int month = Integer.parseInt(datetime.substring(4, 6)) - 1;
        final int day = Integer.parseInt(datetime.substring(6, 8));
        final int hour = Integer.parseInt(datetime.substring(8, 10));
        final int minute = Integer.parseInt(datetime.substring(10, 12));
        final int second = Integer.parseInt(datetime.substring(12, 14));

        final Calendar calendar = Calendar.getInstance();
        calendar.setLenient(false);
        calendar.set(year, month, day, hour, minute, second);

        try {
            calendar.getTime();
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }
}
