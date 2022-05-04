package dk.hovdeforlob4.android.pictureperfect

import android.graphics.Color
import android.util.Log

class ImageUtility {

    /**
     * this method gruopes colours by rgb values
     * @param xycoordAndRgbColour : List<ColourDis_Model>
     * @return List<ColourGrpupe>
     */
    fun grupeColour(xycoordAndRgbColour:List<ColourDis_Model>): List<ColourGroupe>{
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

    /**
     * this method coverts a string of rgb colour to the colourModel
     * @param strHmap:HashMap<String, Int>
     * @return HashMap<ColourModel, Int>
     */
    fun convertToColourModel(strHmap:HashMap<String, Int>):HashMap<ColourModel, Int>{
        val hMap = HashMap<ColourModel, Int>()

//        val f = strHmap.keys.toTypedArray()
//        val uniqueValuesStrArr  = f.distinct()

        for (item in strHmap){
            val d = item.key.split(",")
            val red = d[0].toInt()
            val green = d[1].toInt()
            val blue = d[2].toInt()
            hMap[ColourModel(red,green, blue)] = item.value
        }

        return hMap
    }

    /**
     * this method gets colour by x and y coordnates
     * @param x : Int
     * @param y : Int
     * @return Pair<CoordinateModel, ColourModel>
     */
    fun getCollour(x:Int, y:Int):Pair<CoordinateModel, ColourModel>{

        val pixel: Int = bitmap_image.getPixel(x, y)
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)

        return Pair(CoordinateModel(x, y), ColourModel(red, green, blue))
    }

    /**
     * this method finds the must used colour from hashmap
     * @param numLst:HashMap<CoordinateModel, ColourModel>
     * @param diffNumslst:List<ColourModel>
     * @return HashMap<String, Int>
     */
    fun findTheMustUsedCollour(numLst:HashMap<CoordinateModel, ColourModel>, diffNumslst:List<ColourModel>):HashMap<String, Int>{
        val hmap = HashMap<String, Int>()
        val test = mutableListOf<ColourModel>()
        test.addAll(diffNumslst)

        val strLst = mutableListOf<String>()

        for (item in numLst){
            val blue = item.value.blue
            val green = item.value.green
            val red = item.value.red

            strLst.add("$red,$green,$blue")
        }

        val lstS = strLst.groupingBy { it }.eachCount().filter { it.value > 1 }.toMap()

        for (item in lstS){
            hmap[item.key] = item.value
        }

        return hmap
    }

    /**
     * this method finds the diffenct numbers form a hashmap
     * @param numlst:HashMap<CoordinateModel, ColourModel>
     * @return List<ColourModel>
     */
    fun getDifficeNum(numlst:HashMap<CoordinateModel, ColourModel>):List<ColourModel>{
        val diffLst = mutableListOf<ColourModel>()
        val strLst = mutableListOf<String>()

        for (item in numlst){
            val blue = item.value.blue
            val green = item.value.green
            val red = item.value.red

            strLst.add("$red,$green,$blue")
        }

        val strArr = strLst.toTypedArray()
        val uniqueValuesStrArr  = strArr.distinct()

        for (item in uniqueValuesStrArr){
            val d = item.split(",")
            val red = d[0].toInt()
            val green = d[1].toInt()
            val blue = d[2].toInt()
            diffLst.add(ColourModel(red,green, blue))
        }

        return diffLst
    }

}