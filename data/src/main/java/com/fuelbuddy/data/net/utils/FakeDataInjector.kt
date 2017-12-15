package com.fuelbuddy.data.net.utils

import com.fuelbuddy.data.entity.GasStationEntity
import java.io.IOException

/**
 * Created by zjuroszek on 15.12.17.
 */


open class FakeInjector



@Throws(IOException::class)
fun getGasStations(token: String, latitude: String, longitude: String): List<GasStationEntity> {
        return generateGasStationList()
}

fun generateGasStationList(): List<GasStationEntity> {

    val gasStationList : MutableList<GasStationEntity> =  mutableListOf<GasStationEntity>()

    for ((x,y) in generateLocation()){
        val gs =  GasStationEntity()
        gs.companyName = "Shell"
        gs.distance = "2"
        gs.gasStationLatitude = x
        gs.gasStationLongitude = y
        gs.gasStationName = "Shell"
        gs.gasStation_id = "1"
        gs.price92 = "5"
        gs.price95 = "6"
        gs.price95 = "3"
        gs.priceDiesel = "4"
        gs.timeUpdated = ""
        gasStationList.add(gs)
    }

    return gasStationList
}

fun generateLocation(): Map<String, String> {
    val list  = mutableMapOf( "50.029512" to "19.945915",
            "450.032048" to "19.937975","50.042550"
            to  "19.934928", "50.036266" to "19.918921")
    return list

}






