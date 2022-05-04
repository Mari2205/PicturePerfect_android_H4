package dk.hovdeforlob4.android.pictureperfect

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import kotlin.collections.HashMap

val REQUEST_IMAGE_CAPTURE = 1
lateinit var bitmap_image:Bitmap

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    /**
    * this method takes an image what an intent to the phone camera
    * @param view: View
    */
    fun takepicture_btnclick(view: View){
        dispatchTakePictureIntent(view)
    }


    /**
     * this method is run btn clicked finds the top 5 must used colours in an image
     * @param view: View
     */
    fun btnClick(view: View){
         val pixelCoordinates = getPixelesCoordinats()

        val presenter:Present = Presenter()
        val top5 = presenter.GiveTop5(pixelCoordinates)
        printToTxtbox(top5)
    }


    private fun printToTxtbox(lst:HashMap<Int, ColourModel>){
        val txtBox = findViewById<TextView>(R.id.textView)
        var text = ""
        for (item in lst){
            text += "ca count of pixels : ${item.key} - colour nyance : [${item.value.red}, ${item.value.green}, ${item.value.blue}]\n"
        }
        txtBox.text = text

    }


    private fun getPixelesCoordinats():List<CoordinateModel>{
        // TODO needs cleaning
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