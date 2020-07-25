package com.zxyono.recite.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class DecimalUtils {
    // 保留小数点后多少位（四舍五入）
    public static Double halfup(Double target, int digital) {
        DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.HALF_UP);
        df.setMaximumFractionDigits(digital);
        df.setMinimumFractionDigits(digital);

        return Double.parseDouble(df.format(target));
    }

    public static void main(String[] args) {
        System.out.println(halfup(12.149, 2));
    }
}