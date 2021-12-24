package com.example.realestatemanager.util

import android.content.Context
import android.util.Log
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import android.net.ConnectivityManager
import com.example.realestatemanager.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.*
import kotlin.math.roundToLong


/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(Date())
        }

    /**
     * Conversion d'un prix d'un bien immobilier (Dollars vers Euros)
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param dollars
     * @return
     */
    fun convertDollarToEuro(dollars: Int): Int {
        return (dollars * 0.812).roundToLong().toInt()
    }

    /**
     * Convert d'un prix d'un bien immobilier (Euros vers Dollars)
     */
    fun convertEuroToDollar(euros: Int): Int {
        return (euros * 1.232).roundToLong().toInt()
    }

    /**
     * Vérification de la connexion réseau
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @param context
     * @return
     */
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * Return an image (Bitmap) from /data/user/0/com.example.realestatemanager/files
     * @path is the name of the directory
     * @name is the name of the file as to be selected
     * */

    fun getInternalBitmap(path: String?, name: String?, context: Context?): Bitmap {
        val folder = File(context?.filesDir, path)
        val file = File(folder, name)
        return if (file.exists()) {
            BitmapFactory.decodeStream(FileInputStream(file))
        } else {
            getBitmap(R.drawable.baseline_photo_24, context)
        }
    }

    private fun getBitmap(drawableRes: Int, context: Context?): Bitmap {
        val drawable: Drawable = context?.resources!!.getDrawable(drawableRes)
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return bitmap

    }

    fun setInternalBitmap(photo: Bitmap?, path: String, name: String, context: Context?) {
        val folder = File(context?.filesDir, path)
        val file = File(folder, name)
        file.parentFile.mkdirs()
        val fos = FileOutputStream(file)
        try {
            photo?.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
