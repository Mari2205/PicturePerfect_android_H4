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
import android.widget.TextView
import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.*
import kotlin.collections.HashMap

val REQUEST_IMAGE_CAPTURE = 1
//rmlateinit var currentPhotoPath: String
lateinit var bitmap_image:Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val p:Present = Presenter()
//        val output = p.GiveTop5()
    }


    fun btnClick(view: View){
         val pixelCoordinates = GetPixelesCoordinats()
//        val lst = mutableListOf<ColourDis_Model>()
//
//
//        val listSize = pixelCoordinates.size    // get the size of the list
//
//        val pixelCoordinates1 = pixelCoordinates.subList(0, (listSize + 1) / 2)
//        val pixelCoordinates2 = pixelCoordinates.subList((listSize + 1) / 2, listSize)
//
//        val lstFromT1 = TheardLst(pixelCoordinates1)
//        val lstFromT2 = TheardLst((pixelCoordinates2))
//        lst.addAll(lstFromT1)
//        lst.addAll(lstFromT2)
////        for (item in lst)
////        {
////            Log.d("theard", "list of res : ${item.Cont2}")
////        }
////        Log.d("theard", "groupe start")
//        val groups = GrupeColour(lst)
//        val sum = SumCount(groups)
//        val top5 = Top5(sum)


        val presenter:Present = Presenter()
        val top5 = presenter.GiveTop5(pixelCoordinates)
        printToTxtbox(top5)
    }

//    // per
//    fun TheardLst(pixelCoordinates:List<CoordinateModel>): List<ColourDis_Model> = runBlocking{
//        runInThread(pixelCoordinates)
//
//    }
//
//    // per
//    fun runInThread(pixelCoordinates:List<CoordinateModel>):List<ColourDis_Model>{
////        val coordinateAndColourCodeHmap = HashMap<CoordinateModel, ColourModel>()
////        for (item in pixelCoordinates){
////            val coordinateAndColourCodePair = GetCollour(item.x, item.y)
////            val xyCoord = coordinateAndColourCodePair.first
////            val rgbColour = coordinateAndColourCodePair.second
////            coordinateAndColourCodeHmap[xyCoord] = rgbColour
////        }
////        val diffColourLst = GetDifficeNum(coordinateAndColourCodeHmap)
////        val coloursUsed = FindTheMustUsedCollour(coordinateAndColourCodeHmap, diffColourLst)
////        val coverted = ConvertToColourModel(coloursUsed)
////        val lst = calcdisLowerthen20(coverted)
////        return lst
//    }


    fun printToTxtbox(lst:HashMap<Int, ColourModel>){
        val txtBox = findViewById<TextView>(R.id.textView)
        var text = ""
        for (item in lst){
            text += "ca count of pixels : ${item.key} - colour nyance : [${item.value.red}, ${item.value.green}, ${item.value.blue}]\n"
        }
        txtBox.text = text

    }

//    // image - per
//    fun Top5(hm:HashMap<Int, ColourModel>):HashMap<Int, ColourModel>{
//        var count = 0
//        val hms = hm.toSortedMap(reverseOrder())
//        val lst = HashMap<Int, ColourModel>()
//
//
//        for (i in hms){
//            count += 1
//            if (count == 6){
//                return lst
//            }
//            else{
//                lst[i.key] = i.value
//            }
//        }
//        return hm
//    }

//    // image
//    fun ConvertToColourModel(strHmap:HashMap<String, Int>):HashMap<ColourModel, Int>{
//        val hMap = HashMap<ColourModel, Int>()
//
////        val f = strHmap.keys.toTypedArray()
////        val uniqueValuesStrArr  = f.distinct()
//
//        for (item in strHmap){
//            val d = item.key.split(",")
//            val red = d[0].toInt()
//            val green = d[1].toInt()
//            val blue = d[2].toInt()
//            hMap[ColourModel(red,green, blue)] = item.value
//        }
//
//        return hMap
//    }


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

//    // image
//    fun GetCollour(x:Int, y:Int):Pair<CoordinateModel, ColourModel>{
//
//        val pixel: Int = bitmap_image.getPixel(x, y)
//        val red = Color.red(pixel)
//        val green = Color.green(pixel)
//        val blue = Color.blue(pixel)
//
//        return Pair(CoordinateModel(x, y), ColourModel(red, green, blue))
//    }

//    // image - per
//    fun GrupeColour(xycoordAndRgbColour:List<ColourDis_Model>): List<ColourGroupe>{
//        var groupeNum = 0
//
//        val rgbAndGrouplst = HashMap<String, ColourGroupe>()
//        for (item in xycoordAndRgbColour) {
//            Log.d(
//                "grpus",
//                "data : Count: ${item.Cont2} dis: ${item.distance} | ${item.colour1.red}, ${item.colour1.green}, ${item.colour1.blue} - ${item.colour2.red}, ${item.colour2.green}, ${item.colour2.blue}"
//            )
//
//            val blue = item.colour1.blue
//            val green = item.colour1.green
//            val red = item.colour1.red
//
//            val strRgbColour = "$red,$green,$blue"
//
//            rgbAndGrouplst[strRgbColour] = ColourGroupe(groupeNum, item.colour2, item.Cont2)
//
//        }
//
//        val grupeColourlst = mutableListOf<ColourGroupe>()
//        for (item in rgbAndGrouplst){
//            for (item2 in rgbAndGrouplst){
//                if (item.key == item2.key){
//                    groupeNum += 1
//                    grupeColourlst.add(ColourGroupe(groupeNum, item2.value.colour, item2.value.count))
//                }
//            }
//        }
//
//        return grupeColourlst
//    }
//
//    // image - calc - per
//    fun SumCount(lst:List<ColourGroupe>): HashMap<Int, ColourModel>{
//        var cout = 0
//        var color = ColourModel(0,0,0)
//        val hm = HashMap<Int, ColourModel>()
//
//        for (co in lst){
//            cout = 0
//            for (item in lst){
//                if (co.groupeId == item.groupeId){
//                    cout += item.count
//                    color = item.colour
//                }
//            }
//            hm[cout] = color
//        }
//
//        return hm
//    }
//
//    // image - calc - per
//    fun calcdisLowerthen20(xycoordAndRgbColour:HashMap<ColourModel, Int>):List<ColourDis_Model>{
//        val calc = Calculator()
//        val disLst = mutableListOf<ColourDis_Model>()
//
//        Log.d("tag", "clac met run")
//
//        for (item1 in xycoordAndRgbColour){
//            for (item2 in xycoordAndRgbColour){
//                if (item1.key != item2.key){
//                    val dis = calc.calculateColourDistance(item1.key, item2.key)
//                    if (dis <= 20.0){
//                        disLst.add(ColourDis_Model(dis, item1.key, item2.key, item1.value, item2.value))
//                        Log.d("tag", "loop")
//                    }
//
//                }
//            }
//        }
//        return disLst
//    }

//    // image
//    fun FindTheMustUsedCollour(numLst:HashMap<CoordinateModel, ColourModel>, diffNumslst:List<ColourModel>):HashMap<String, Int>{
//        val hmap = HashMap<String, Int>()
//        val test = mutableListOf<ColourModel>()
//        test.addAll(diffNumslst)
//
//        val strLst = mutableListOf<String>()
//
//        for (item in numLst){
//            val blue = item.value.blue
//            val green = item.value.green
//            val red = item.value.red
//
//            strLst.add("$red,$green,$blue")
//        }
//
//        val lstS = strLst.groupingBy { it }.eachCount().filter { it.value > 1 }.toMap()
//
//        for (item in lstS){
//            hmap[item.key] = item.value
//        }
//
//        return hmap
//    }
//
//    // image
//    fun GetDifficeNum(numlst:HashMap<CoordinateModel, ColourModel>):List<ColourModel>{
//        val diffLst = mutableListOf<ColourModel>()
//        val strLst = mutableListOf<String>()
//
//        for (item in numlst){
//            val blue = item.value.blue
//            val green = item.value.green
//            val red = item.value.red
//
//            strLst.add("$red,$green,$blue")
//        }
//
//        val strArr = strLst.toTypedArray()
//        val uniqueValuesStrArr  = strArr.distinct()
//
//        for (item in uniqueValuesStrArr){
//            val d = item.split(",")
//            val red = d[0].toInt()
//            val green = d[1].toInt()
//            val blue = d[2].toInt()
//            diffLst.add(ColourModel(red,green, blue))
//        }
//
//        return diffLst
//    }




    fun takepicture_btnclick(view: View){
        dispatchTakePictureIntent(view)
    }

    private fun dispatchTakePictureIntent(view: View) {
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