package dk.hovdeforlob4.android.pictureperfect

import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

class RGB_cube {

    /**
     * this method calculate the value between the elements in the list of collourmodel
     */
    fun calculate_distance(colourLst:List<CollourModel>): Int{
        //TODO("not yet implement")
//
//        val vR = 227 - 255
//        val vG = 133 - 192
//        val vB = 29 - 0

        val collourDistance = Math.sqrt(Math.pow((227.0 - 255.0), 2.0) +
                                        Math.pow((133.0 - 192.0), 2.0) +
                                        Math.pow((29.0  - 0.0),   2.0))

//        val powR = Math.pow(vR.toDouble(), 2.0)
//        val powG = Math.pow(vG.toDouble(), 2.0)
//        val powB = Math.pow(vB.toDouble(), 2.0)
//
//        val powRes = powR + powG + powB
//        val resulst = Math.sqrt(powRes)


        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.CEILING
        val roundetcollourDistance = decimalFormat.format(collourDistance)

        var totalDistance = 0
        for (item in colourLst){
            for (item2 in colourLst){
                val dis = Math.pow(item2.toString().toDouble(),2.0)
            }
        }
        return  0


    }
}