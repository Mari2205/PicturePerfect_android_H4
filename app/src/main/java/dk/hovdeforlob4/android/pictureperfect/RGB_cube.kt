package dk.hovdeforlob4.android.pictureperfect

import java.math.RoundingMode
import java.text.DecimalFormat

class RGB_cube {

    /**
     * this method calculate the value between the elements in the list of collourmodel
     */
    fun calculate_distance(colourLst:List<CollourModel>): Int{
        TODO("")
//rm        val collourDistance = Math.sqrt(Math.pow((227.0 - 255.0), 2.0) +
//rm                                        Math.pow((133.0 - 192.0), 2.0) +
//rm                                        Math.pow((29.0  - 0.0),   2.0))
//rm
//rm        val decimalFormat = DecimalFormat("#.##")
//rm        decimalFormat.roundingMode = RoundingMode.CEILING
//rm        val roundetcollourDistance = decimalFormat.format(collourDistance)

//        calculateColourDistance(CollourModel(255, 192, 0), CollourModel(227, 133, 29))
//
//        var totalDistance = 0
//        for (item in colourLst){
//            for (item2 in colourLst){
//                calculateColourDistance(item, item2)
//            }
//        }
    //        return  0
    }


    /**
     * this method calculate the value between two values of the type CollourModel
     * @param collour1 : CollourModel
     * @param collour2 : CollourModel
     * @return Double
     */
    private fun calculateColourDistance(collour1:CollourModel, collour2: CollourModel): Double{
        val redC1   = collour1.red.toDouble()
        val greenC1 = collour1.green.toDouble()
        val blueC1  = collour1.blue.toDouble()

        val redC2   = collour2.red.toDouble()
        val greenC2 = collour2.green.toDouble()
        val blueC2  = collour2.blue.toDouble()

        val collourDistance = Math.sqrt(Math.pow((redC2   - redC1),   2.0) +
                                        Math.pow((greenC2 - greenC1), 2.0) +
                                        Math.pow((blueC2  - blueC1),  2.0))

        val decimalFormat = DecimalFormat("#.##")
        decimalFormat.roundingMode = RoundingMode.CEILING
        val roundetcollourDistance = decimalFormat.format(collourDistance).toDouble()

        return roundetcollourDistance
    }
}