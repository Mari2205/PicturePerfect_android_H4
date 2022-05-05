package dk.hovdeforlob4.android.pictureperfect

import android.util.Log
import kotlinx.coroutines.runBlocking

class Presenter : Present{

    val imageUtilities = ImageUtility()

    override fun GiveTop5Colours(pixelCoordinates: List<CoordinateModel>): HashMap<Int, ColourModel> {
        val lst = mutableListOf<ColourDis_Model>()

        val listSize = pixelCoordinates.size

        val pixelCoordinates1 = pixelCoordinates.subList(0, (listSize + 1) / 2)
        val pixelCoordinates2 = pixelCoordinates.subList((listSize + 1) / 2, listSize)

        val lstFromT1 = TheardLst(pixelCoordinates1)
        val lstFromT2 = TheardLst((pixelCoordinates2))
        lst.addAll(lstFromT1)
        lst.addAll(lstFromT2)

        val groups = imageUtilities.grupeColour(lst)
        val sum = SumCount(groups)
        val top5 = FindTop5(sum)
        return  top5
    }


    private fun TheardLst(pixelCoordinates:List<CoordinateModel>): List<ColourDis_Model> = runBlocking{
        runInThread(pixelCoordinates)

    }


    private fun runInThread(pixelCoordinates:List<CoordinateModel>):List<ColourDis_Model>{

        val coordinateAndColourCodeHmap = HashMap<CoordinateModel, ColourModel>()
        for (item in pixelCoordinates){
            val coordinateAndColourCodePair = imageUtilities.getCollour(item.x, item.y)
            val xyCoord = coordinateAndColourCodePair.first
            val rgbColour = coordinateAndColourCodePair.second
            coordinateAndColourCodeHmap[xyCoord] = rgbColour
        }
        val diffColourLst = imageUtilities.getDifficeNum(coordinateAndColourCodeHmap)
        val coloursUsed = imageUtilities.findTheMustUsedCollour(coordinateAndColourCodeHmap, diffColourLst)
        val coverted = imageUtilities.convertToColourModel(coloursUsed)
        val lst = calcdisLowerthen20(coverted)
        return lst
    }


    private fun SumCount(lst:List<ColourGroupe>): HashMap<Int, ColourModel>{
        var cout = 0
        var color = ColourModel(0,0,0)
        val hm = HashMap<Int, ColourModel>()

        for (co in lst){
            cout = 0
            for (item in lst){
                if (co.groupeId == item.groupeId){
                    cout += item.count
                    color = item.colour
                }
            }
            hm[cout] = color
        }

        return hm
    }


    private fun calcdisLowerthen20(xycoordAndRgbColour:HashMap<ColourModel, Int>):List<ColourDis_Model>{
        val calc = Calculator()
        val disLst = mutableListOf<ColourDis_Model>()

        Log.d("tag", "clac met run")

        for (item1 in xycoordAndRgbColour){
            for (item2 in xycoordAndRgbColour){
                if (item1.key != item2.key){
                    val dis = calc.calculateColourDistance(item1.key, item2.key)
                    if (dis <= 20.0){
                        disLst.add(ColourDis_Model(dis, item1.key, item2.key, item1.value, item2.value))
                        Log.d("tag", "loop")
                    }

                }
            }
        }
        return disLst
    }

    //TODO:
    // fun maybe better name
    // valribale find an better name then "hm"
    // fix for wtih maybe a substring??
    // genral better variable names
    private fun FindTop5(hm:HashMap<Int, ColourModel>):HashMap<Int, ColourModel>{
        var count = 0
        val hms = hm.toSortedMap(reverseOrder())
        val lst = HashMap<Int, ColourModel>()


        for (i in hms){
            count += 1
            if (count == 6){
                return lst
            }
            else{
                lst[i.key] = i.value
            }
        }
        return hm
    }

}