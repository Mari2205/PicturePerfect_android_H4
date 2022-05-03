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
        val coloursUsed = FindTheMustUsedCollour(coordinateAndColourCodeHmap, diffColourLst)
        val lst = calcdisLowerthen20(coloursUsed)
        GrupeColour(coordinateAndColourCodeHmap)

        val t = ""

    }

    fun ConvertToColourModel(strHmap:HashMap<String, Int>){
        val hMap = HashMap<ColourModel, Int>()

        val f = strHmap.keys.toTypedArray()
        val uniqueValuesStrArr  = f.distinct()

        for (item in uniqueValuesStrArr){
            val d = item.split(",")
            val red = d[0].toInt()
            val green = d[1].toInt()
            val blue = d[2].toInt()
            hMap[ColourModel(red,green, blue)] = 
        }
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

    fun calcdisLowerthen20(xycoordAndRgbColour:HashMap<String, Int>):List<ColourDis_Model>{
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



//        for (item in numLst){
//            var count = 0
//            for (item2 in test){
//                Log.d("t" , "log : ${item.value.red}, ${item.value.green}, ${item.value.blue}")
//
//                if (item.value.red == item2.red && item.value.green == item2.green && item.value.blue == item2.blue){
//                    count += 1
//
//                }
//            }
//
//            hmap[count] = ColourModel(item.value.red, item.value.green, item.value.blue)
//        }
//
//        val t =""


//        val map = HashMap<ColourModel, Int>()
//        for (item in diffNumslst){
////            val tes = numLst.count{it == 706}
//            val tesAll = numLst.count{it.value == item}
//            map.put(ColourModel(item.red,item.green,item.blue), tesAll)
//        }
//
//        var value = 0
//        var biggest = 0
//        for (item in map) {
//            if (biggest < item.value) {
//                value = item.key
//                biggest = item.value
//            }
//        }
//        value
//        biggest

//        map.toSortedMap()
//        val t = ""

    }

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