package dk.hovdeforlob4.android.pictureperfect

import android.util.Log
import kotlinx.coroutines.runBlocking

class Presenter : Present{

    override fun GiveTop5(pixelCoordinates: List<CoordinateModel>): HashMap<Int, ColourModel> {
        //TODO("Not yet implemented")
//        val pixelCoordinates = GetPixelesCoordinats()
        val lst = mutableListOf<ColourDis_Model>()


        val listSize = pixelCoordinates.size    // get the size of the list

        val pixelCoordinates1 = pixelCoordinates.subList(0, (listSize + 1) / 2)
        val pixelCoordinates2 = pixelCoordinates.subList((listSize + 1) / 2, listSize)

        val lstFromT1 = TheardLst(pixelCoordinates1)
        val lstFromT2 = TheardLst((pixelCoordinates2))
        lst.addAll(lstFromT1)
        lst.addAll(lstFromT2)
//        for (item in lst)
//        {
//            Log.d("theard", "list of res : ${item.Cont2}")
//        }
//        Log.d("theard", "groupe start")
        val groups = GrupeColour(lst)
        val sum = SumCount(groups)
        val top5 = Top5(sum)
        return  top5
    }




    // per
    private fun TheardLst(pixelCoordinates:List<CoordinateModel>): List<ColourDis_Model> = runBlocking{
        runInThread(pixelCoordinates)

    }

    // per
    private fun runInThread(pixelCoordinates:List<CoordinateModel>):List<ColourDis_Model>{
        val imageUtilities = ImageUtility()

        val coordinateAndColourCodeHmap = HashMap<CoordinateModel, ColourModel>()
        for (item in pixelCoordinates){
            val coordinateAndColourCodePair = imageUtilities.GetCollour(item.x, item.y)
            val xyCoord = coordinateAndColourCodePair.first
            val rgbColour = coordinateAndColourCodePair.second
            coordinateAndColourCodeHmap[xyCoord] = rgbColour
        }
        val diffColourLst = imageUtilities.GetDifficeNum(coordinateAndColourCodeHmap)
        val coloursUsed = imageUtilities.FindTheMustUsedCollour(coordinateAndColourCodeHmap, diffColourLst)
        val coverted = imageUtilities.ConvertToColourModel(coloursUsed)
        val lst = calcdisLowerthen20(coverted)
        return lst
    }




    // image - per
    fun GrupeColour(xycoordAndRgbColour:List<ColourDis_Model>): List<ColourGroupe>{
        var groupeNum = 0

        val rgbAndGrouplst = HashMap<String, ColourGroupe>()
        for (item in xycoordAndRgbColour) {
            Log.d(
                "grpus",
                "data : Count: ${item.Cont2} dis: ${item.distance} | ${item.colour1.red}, ${item.colour1.green}, ${item.colour1.blue} - ${item.colour2.red}, ${item.colour2.green}, ${item.colour2.blue}"
            )

            val blue = item.colour1.blue
            val green = item.colour1.green
            val red = item.colour1.red

            val strRgbColour = "$red,$green,$blue"

            rgbAndGrouplst[strRgbColour] = ColourGroupe(groupeNum, item.colour2, item.Cont2)

        }

        val grupeColourlst = mutableListOf<ColourGroupe>()
        for (item in rgbAndGrouplst){
            for (item2 in rgbAndGrouplst){
                if (item.key == item2.key){
                    groupeNum += 1
                    grupeColourlst.add(ColourGroupe(groupeNum, item2.value.colour, item2.value.count))
                }
            }
        }

        return grupeColourlst
    }

    // image - calc - per
    fun SumCount(lst:List<ColourGroupe>): HashMap<Int, ColourModel>{
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

    // image - calc - per
    fun calcdisLowerthen20(xycoordAndRgbColour:HashMap<ColourModel, Int>):List<ColourDis_Model>{
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

    // image - per
    fun Top5(hm:HashMap<Int, ColourModel>):HashMap<Int, ColourModel>{
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