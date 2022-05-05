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
import androidx.core.view.isVisible
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
    * @param view: View | view is the view of the widget there is call this fun
    */
    fun takepicture_btnclick(view: View){
        dispatchTakePictureIntent(view)
    }


    /**
     * this method is run btn clicked finds the top 5 must used colours in an image
     * @param view: View | view is the view of the widget there is call this fun
     */
    fun btnClick(view: View){
         val pixelCoordinates = getPixelesCoordinats()

        val presenter:Present = Presenter()
        val top5 = presenter.GiveTop5Colours(pixelCoordinates)
        printToTxtbox(top5)
    }


    private fun printToTxtbox(lst:HashMap<Int, ColourModel>){
        val txtBox = findViewById<TextView>(R.id.textView)

        txtBox.apply {
            isVisible = true
            for (item in lst){
                text = text.toString() +
                        "\nca count of pixels : ${item.key} - " +
                        "colour nyance : [${item.value.red}, ${item.value.green}, ${item.value.blue}]"
            }
        }

    }


    private fun getPixelesCoordinats():List<CoordinateModel>{
        val coordinatesLst = mutableListOf<CoordinateModel>()
        val imHeight = bitmap_image.height
        val imWidth = bitmap_image.width
        var totalPixelCount = 0
        val logTag = "pixelCount"

        for (heightPixelIndex in 0 until imHeight - 1) {
            for (widthPixelIndex in 0 until imWidth - 1) {

                totalPixelCount += 1
                Log.d(logTag, "Coordinate index is X: [$widthPixelIndex], Y: [$heightPixelIndex]")

                coordinatesLst.add(CoordinateModel(widthPixelIndex, heightPixelIndex))
            }
        }
        Log.d(logTag, "The total number of pixels is $totalPixelCount")

        return coordinatesLst
    }


    private fun dispatchTakePictureIntent(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (errMsg: ActivityNotFoundException) {
            Log.e("err", "Error mgs: $errMsg")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val imageView_picture = findViewById<ImageView>(R.id.imageView_picture)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap

            imageView_picture.setImageBitmap(imageBitmap)
            bitmap_image = imageBitmap
        }
    }

}