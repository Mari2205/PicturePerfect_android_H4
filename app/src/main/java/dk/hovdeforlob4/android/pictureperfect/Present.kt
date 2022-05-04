package dk.hovdeforlob4.android.pictureperfect

interface Present {
    /**
     * this method will make an list of top must used colours in image
     */
    fun GiveTop5(pixelCoordinates:List<CoordinateModel>):HashMap<Int, ColourModel>
}