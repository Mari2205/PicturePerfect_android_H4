package dk.hovdeforlob4.android.pictureperfect

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView

val REQUEST_IMAGE_CAPTURE = 1
//rmlateinit var currentPhotoPath: String
lateinit var bitmap_image:Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }


    fun btnClick(view: View){
        val coordinateAndColourCodeHmap = HashMap<CoordinateModel, ColourModel>()

        val pixelCoordinates = GetPixelesCoordinats()

        for (item in pixelCoordinates){
            val coordinateAndColourCodePair = GetCollour(item.x, item.y)
            val xyCoord = coordinateAndColourCodePair.first
            val rgbColour = coordinateAndColourCodePair.second
            coordinateAndColourCodeHmap[xyCoord] = rgbColour
        }
        val diffColourLst = GetDifficeNum(coordinateAndColourCodeHmap)

        calcdisLowerthen20(coordinateAndColourCodeHmap)
        GrupeColour(coordinateAndColourCodeHmap)

        val t = ""

    }


    // TODO needs cleaning
    fun GetPixelesCoordinats():List<CoordinateModel>{
        val imHeight = bitmap_image.height
        val imWidth = bitmap_image.width

        val coordinatesLst = mutableListOf<CoordinateModel>()
        // X = width and Y = Height
        var heightCount:Int = 0
        var totalCount:Int = 0
        for (P in 0 until imHeight-1) {
            heightCount += 1
            var widthCount:Int = 0
            Log.d("count", "Height count is $heightCount")
            for (pixel in 0 until imWidth-1) {
                widthCount += 1
                totalCount += 1
                Log.d("count", "Wigth count is $widthCount")

                coordinatesLst.add(CoordinateModel(widthCount, heightCount))
            }
        }
        Log.d("count", "total count of pixles is ${totalCount.toString()}")

        return coordinatesLst
    }


    fun GetCollour(x:Int, y:Int):Pair<CoordinateModel, ColourModel>{

        val pixel: Int = bitmap_image.getPixel(x, y)
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)

        return Pair(CoordinateModel(x, y), ColourModel(red, green, blue))
    }


    fun GrupeColour(xycoordAndRgbColour:HashMap<CoordinateModel, ColourModel>){
        val groupeLst = mutableListOf<ColourGroupe>()
        var groupeNum = 1

//        val discalc = Calculator().calculate_distance()
        for (item in xycoordAndRgbColour){

        }

        for (item in xycoordAndRgbColour){
            val groupeItemLst = mutableListOf<GroupeItem_Model>()

            val coord = item.key
            val rgbColour = item.value
            val groupeItem = GroupeItem_Model(coord, rgbColour)
            groupeItemLst.add(groupeItem)
            groupeLst.add(ColourGroupe(groupeNum, groupeItemLst.toList()))

        }
    }

    fun calcdisLowerthen20(xycoordAndRgbColour:HashMap<CoordinateModel, ColourModel>):List<ColourDis_Model>{
        val calc = Calculator()
        val disLst = mutableListOf<ColourDis_Model>()
        Log.d("tag", "clac met run")

        for (item1 in xycoordAndRgbColour){
            for (item2 in xycoordAndRgbColour){
                val dis = calc.calculateColourDistance(item1.value, item2.value)
                if (dis <= 20.0){
                    disLst.add(ColourDis_Model(dis, item1.value, item2.value))
                    Log.d("tag", "loop")
                }
            }
        }
        return disLst
    }


    fun test (rgbCollourLst:List<ColourModel>):List<Int>{
        val outputLst = mutableListOf<Int>()

        for (item in rgbCollourLst){
            val collourNum = item.red + item.green + item.blue
            outputLst.add(collourNum)
        }

        return  outputLst
    }

    fun FindTheMustUsedCollour(numLst:List<Int>, diffNumslst:List<Int>){
       val map = HashMap<Int, Int>()
        for (item in diffNumslst){
//            val tes = numLst.count{it == 706}
            val tesAll = numLst.count{it == item}
            map.put(item, tesAll)
        }

        var value = 0
        var biggest = 0
        for (item in map) {
            if (biggest < item.value) {
                value = item.key
                biggest = item.value
            }
        }
        value
        biggest

//        map.toSortedMap()
//        val t = ""

    }

    fun GetDifficeNum(numlst:HashMap<CoordinateModel, ColourModel>):List<ColourModel>{
        val diffLst = mutableListOf<ColourModel>()
        val templst = mutableListOf<ColourModel>()
//        for (item in numlst){
//            Log.d("tag", "loooop : ${item.value.red}, ${item.value.green}, ${item.value.blue}")
//        }
//        for (item in numlst){
//            for (item2 in diffLst + 1){
//                val t = ""
//            }
//        }
//        val numlstKeys = numlst.keys
////        val de = f.first()
//        val x = numlstKeys.first().x
//        val y = numlstKeys.first().y
//        val coord = CoordinateModel(x, y)

//        val lst2 = mutableListOf<Int>()
//        val lst = mutableListOf<Int>()
//        val lst3 = mutableListOf<Int>()
//        lst.add(21)
//        lst2.add(12)
//        lst2.add(12)
//        lst2.add(12)
//
//        for (itr in lst) {
//           for (fff in lst2){
//                lst.add(1)
//            }
////            lst.add(lst3[0])
////            lst3.removeAll(lst3)
//            val t =""
//        }

        val lst23 = mutableListOf<String>()
        for (item in numlst){
            val blue = item.value.blue
            val green = item.value.green
            val red = item.value.red

            lst23.add("$red,$green,$blue")

        }

        val e = lst23.toTypedArray()
        val f = e.distinct()

        for (item in f){
            val d = item.split(",")
            val red = d[0].toInt()
            val green = d[1].toInt()
            val blue = d[2].toInt()
            diffLst.add(ColourModel(red,green, blue))
            val t =""
        }




//        var count = 0
//        var isFirstItem2 = true
//        for (item in numlst) {
//            if (isFirstItem2){
//                diffLst.add(item.value)
//                isFirstItem2 = false
//            }
//
//            for (diffItem in diffLst) {
//                if (diffItem.red != item.value.red &&
//                    diffItem.green != item.value.green &&
//                    diffItem.blue != item.value.blue) {
//
//                    templst.add(item.value)
//                }
//                count += 1
//                Log.d("ta", "$count) log c ${diffItem.red}, ${diffItem.green}, ${diffItem.blue}")
//            }
//            diffLst.addAll(templst)
//            templst.removeAll(templst)
//            val t = ""
//        }
//
//
//
//
//
//
//
//
//
////        val t = ""
//        var isFirstItem = true
//        for (item in numlst) {
//            if (isFirstItem){
//                diffLst.add(item.value)
//                isFirstItem = false
//            }
//            for (diffItem in diffLst) {
//                val t = ""
//
//                if (diffItem.red != item.value.red && diffItem.green != item.value.green && diffItem.blue != item.value.blue) {
//                    diffLst.add(item.value)
//                }
//            }
////            if (!diffLst.contains(item.value)){
////                diffLst.add(item.value)
////            }
//        }
        return diffLst
    }




    fun takepicture_btnclick(view: View){
        dispatchTakePictureIntent(view)
    }

    fun dispatchTakePictureIntent(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.e("err", "Error mgs: ${e.toString()}")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView_picture = findViewById<ImageView>(R.id.imageView_picture)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            imageView_picture.setImageBitmap(imageBitmap)
//            val bitmap_imag:Bitmap = imageBitmap
            bitmap_image = imageBitmap
        }
    }


}