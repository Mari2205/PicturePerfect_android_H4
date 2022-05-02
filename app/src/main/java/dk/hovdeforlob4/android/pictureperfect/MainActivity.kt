package dk.hovdeforlob4.android.pictureperfect

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

val REQUEST_IMAGE_CAPTURE = 1
//lateinit var currentPhotoPath: String
lateinit var bitmap_image:Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val test = RGB_cube().calculate_distance(listOf<CollourModel>())
        val t = ""
    //val c = Color.pack(163,145,121)
    }
    fun getPixelCounter(view: View){
        val imHeight = bitmap_image.height
        val imWidth = bitmap_image.width

        val rgbCollourCodesLst = mutableListOf<CollourModel>()
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

                rgbCollourCodesLst.add(GetCollour(widthCount,heightCount))
                val t = ""
            }
        }
        Log.d("count", "total count of pixles is ${totalCount.toString()}")

        val tl = test(rgbCollourCodesLst)
        val diffnums = GetDifficeNum(tl)
       FindTheMustUsedCollour(tl,diffnums)
        val t = ""
    }

    fun GetCollour(x:Int, y:Int):CollourModel{ //TODO: return pair<Coordinate, Collour>

        val pixel: Int = bitmap_image.getPixel(x, y)
        val red = Color.red(pixel)
        val green = Color.green(pixel)
        val blue = Color.blue(pixel)

        return CollourModel(red, green, blue)
    }

    fun test (rgbCollourLst:List<CollourModel>):List<Int>{
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

            val tes = numLst.count{it == 706}
            val tesAll = numLst.count{it == item}
            map.put(item, tesAll)
        }

        map.toSortedMap()
        val t = ""

    }

    fun GetDifficeNum(numlst:List<Int>):List<Int>{
        val diffLst = mutableListOf<Int>()
        for (item in numlst){
            if(!diffLst.contains(item)){
                diffLst.add(item)
            }
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