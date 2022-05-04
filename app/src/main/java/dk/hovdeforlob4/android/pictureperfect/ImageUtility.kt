package dk.hovdeforlob4.android.pictureperfect

import android.graphics.Color

class ImageUtility {

    // image
    fun ConvertToColourModel(strHmap:HashMap<String, Int>):HashMap<ColourModel, Int>{
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

    // image
    fun GetCollour(x:Int, y:Int):Pair<CoordinateModel, ColourModel>{

        val pixel: Int = bitmap_image.getPixel(x, y)
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)

        return Pair(CoordinateModel(x, y), ColourModel(red, green, blue))
    }

    // image
    fun FindTheMustUsedCollour(numLst:HashMap<CoordinateModel, ColourModel>, diffNumslst:List<ColourModel>):HashMap<String, Int>{
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

    // image
    fun GetDifficeNum(numlst:HashMap<CoordinateModel, ColourModel>):List<ColourModel>{
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