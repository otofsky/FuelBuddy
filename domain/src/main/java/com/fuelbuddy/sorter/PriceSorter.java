package com.fuelbuddy.sorter;

import com.fuelbuddy.data.FuelPriceMode;
import com.fuelbuddy.data.GasStation;
import com.fuelbuddy.util.StringHelper;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by zjuroszek on 06.03.17.
 */

public class PriceSorter implements Sorter<GasStation, FuelPriceMode> {

    public static FuelPriceMode fuelPriceMode;


    public PriceSorter() {
    }


    public static Comparator<GasStation> PriceComparator = new Comparator<GasStation>() {
        @Override
        public int compare(GasStation o1, GasStation o2) {
            int result = 0;
            switch (fuelPriceMode) {
                case BENZIN_92:
                    result = comparePrice(o1.getPrice92(), o2.getPrice92());
                    break;
                case BENZIN_95:
                    result = comparePrice(o1.getPrice95(), o2.getPrice95());
                    break;
                case DIESEL:
                    result = comparePrice(o1.getPriceDiesel(), o2.getPriceDiesel());
                    break;
            }
            return result;
        }
    };

    private static int comparePrice(String priceA, String priceB) {
        return Double.compare(convertToDouble(priceA), convertToDouble(priceB));
    }

    private static double convertToDouble(String price) {
        if (StringHelper.isNullOrEmpty(price)) {
            return 0;
        } else {
            double v = Double.valueOf(price);
            return v;
        }
    }

    @Override
    public List<GasStation> sort(List<GasStation> collection, FuelPriceMode fuelPriceMode) {
        PriceSorter.fuelPriceMode = fuelPriceMode;
        Collections.sort(collection, PriceComparator);
        for (GasStation gs : collection) {
            gs.getPrice92();
        }
        return collection;
    }
}
