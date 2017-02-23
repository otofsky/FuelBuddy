package com.fuelbuddy.mobile.util;

import java.math.BigDecimal;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.


import java.math.BigDecimal;


/**
 * Utilities for comparing numbers.
 *
 * @since 3.0
 * @version $Id$
 */
public class Precision {
    /**
     * Smallest positive number such that {@code 1 - EPSILON} is not
     * numerically equal to 1: {@value}.
     */
    public static final double EPSILON = 0x1.0p-53;
    /**
     * Safe minimum, such that {@code 1 / SAFE_MIN} does not overflow.
     * In IEEE 754 arithmetic, this is also the smallest normalized
     * number 2<sup>-1022</sup>: {@value}.
     */
    public static final double SAFE_MIN = 0x1.0p-1022;
    /**
     * Offset to order signed double numbers lexicographically.
     */
    private static final long SGN_MASK = 0x8000000000000000L;
    /**
     * Offset to order signed double numbers lexicographically.
     */
    private static final int SGN_MASK_FLOAT = 0x80000000;

    /**
     * Private constructor.
     */
    private Precision() {
    }


    public static String round(double x, int scale) {
        double d = round(x, scale, BigDecimal.ROUND_HALF_UP);
        String distance = String.valueOf(d).replace(".", ",");
        return distance;
    }

    /**
     * Rounds the given value to the specified number of decimal places.
     * The value is rounded using the given method which is any method defined
     * in {@link BigDecimal}.
     * If {@code x} is infinite or {@code NaN}, then the value of {@code x} is
     * returned unchanged, regardless of the other parameters.
     *
     * @param x              Value to round.
     * @param scale          Number of digits to the right of the decimal point.
     * @param roundingMethod Rounding method as defined in {@link BigDecimal}.
     * @return the rounded value.
     * @throws ArithmeticException      if {@code roundingMethod == ROUND_UNNECESSARY}
     *                                  and the specified scaling operation would require rounding.
     * @throws IllegalArgumentException if {@code roundingMethod} does not
     *                                  represent a valid rounding mode.
     * @since 1.1 (previously in {@code MathUtils}, moved as of version 3.0)
     */
    public static double round(double x, int scale, int roundingMethod) {
        try {
            return (new BigDecimal
                    (Double.toString(x))
                    .setScale(scale, roundingMethod))
                    .doubleValue();
        } catch (NumberFormatException ex) {
            if (Double.isInfinite(x)) {
                return x;
            } else {
                return Double.NaN;
            }
        }
    }

}
