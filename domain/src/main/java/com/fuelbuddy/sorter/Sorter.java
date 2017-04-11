package com.fuelbuddy.sorter;

import java.util.List;

/**
 * Created by zjuroszek on 06.03.17.
 */

public interface Sorter<T, E> {

    List<T> sort(List<T> collection, E e);



}
