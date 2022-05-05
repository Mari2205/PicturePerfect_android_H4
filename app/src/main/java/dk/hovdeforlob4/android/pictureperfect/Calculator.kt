package dk.hovdeforlob4.android.pictureperfect


class Calculator {

    /**
     * this method calculate the value between two values of the type ColourModel
     * @param collour1 : ColourModel | takes an colourModel
     * @param collour2 : ColourModel | takes an colourModel
     * @return Double | returns an decimal number of the colour distances
     */
    fun calculateColourDistance(collour1:ColourModel, collour2: ColourModel): Double{
        val redC1   = collour1.red.toDouble()
        val greenC1 = collour1.green.toDouble()
        val blueC1  = collour1.blue.toDouble()

        val redC2   = collour2.red.toDouble()
        val greenC2 = collour2.green.toDouble()
        val blueC2  = collour2.blue.toDouble()

        val collourDistance = Math.sqrt(Math.pow((redC2   - redC1),   2.0) +
                                        Math.pow((greenC2 - greenC1), 2.0) +
                                        Math.pow((blueC2  - blueC1),  2.0))

        return collourDistance
    }

}