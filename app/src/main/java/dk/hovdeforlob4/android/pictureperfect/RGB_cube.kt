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

        val vR = 227 - 255
        val vG = 133 - 192
        val vB = 29 - 0

        val powR = Math.pow(vR.toDouble(), 2.0)
        val powG = Math.pow(vG.toDouble(), 2.0)
        val powB = Math.pow(vB.toDouble(), 2.0)

        val powRes = powR + powG + powB
        val resulst = Math.sqrt(powRes)

        val test1 = resulst.roundToInt()
        val test2 = resulst.roundToInt().toDouble()
        val test3 = resulst.roundToInt() / 100.0

        val test4 = String.format("%.3f", resulst).toDouble()

        val df = DecimalFormat("#.##")
        val test5 = df.format(resulst)
        val df2 = DecimalFormat("#.##")
        df2.roundingMode = RoundingMode.DOWN
        val test6 = df2.format(resulst)
        val df3 = DecimalFormat("#.##")
        df3.roundingMode = RoundingMode.UP
        val test7 = df3.format(resulst)
        val df4 = DecimalFormat("#.##") 
        df4.roundingMode = RoundingMode.CEILING
        val test8 = df4.format(resulst)
        val df5 = DecimalFormat("n2")
        val test9 = df5.format(resulst)

        var totalDistance = 0
        for (item in colourLst){
            for (item2 in colourLst){
                val dis = Math.pow(item2.toString().toDouble(),2.0)
            }
        }
        return  0


    }
}