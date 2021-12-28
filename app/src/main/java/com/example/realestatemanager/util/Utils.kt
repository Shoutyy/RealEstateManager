package com.example.realestatemanager.util

import android.content.Context
import android.util.Log
import com.example.realestatemanager.model.City
import com.example.realestatemanager.model.Country
import com.example.realestatemanager.model.District
import com.example.realestatemanager.model.Type
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

    //---TO-DATABASE---\\
    fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

    fun getDistrictForDatabaseFromString(district: String) =
        when(district) {
            "Bronx" -> District.BRONX
            "Brooklyn" -> District.BROOKLYN
            "Manhattan" -> District.MANHATTAN
            "Queens" -> District.QUEENS
            "Staten Island" -> District.STATEN_ISLAND
            else -> District.BRONX
        }

    fun getCityForDatabaseFromString(city: String) =
        when(city) {
            "New York" -> City.NEW_YORK
            else -> City.NEW_YORK
        }

    fun getCountryForDatabaseFromString(country: String) =
        when(country) {
            "United States" -> Country.UNITED_STATES
            else -> Country.UNITED_STATES
        }

    fun getTypeForDatabaseFromString(type: String) =
        when(type) {
            "Flat" -> Type.FLAT
            "Penthouse" -> Type.PENTHOUSE
            "Mansion" -> Type.MANSION
            "Duplex" -> Type.DUPLEX
            "House" -> Type.HOUSE
            "Loft" -> Type.LOFT
            "Townhouse" -> Type.TOWNHOUSE
            "Condo" -> Type.CONDO
            else -> Type.FLAT
        }

    fun getAgentIdForDatabaseFromString(fullNameAgent: String) =
        when(fullNameAgent) {
            "Harmonie Nee" -> 1
            "Clelie Lafaille" -> 2
            "Elisa Beauvau" -> 3
            "Josette Boutroux" -> 4
            "Albert Lafaille" -> 5
            "Omer Delaplace" -> 6
            "Robert Courtial" -> 7
            "Christopher Gaudreau" -> 8
            else -> 1
        }

    //---TO-UI---\\

    fun getTypeIntoStringForUi(type: Type) =
        when(type) {
            Type.PENTHOUSE -> "Penthouse"
            Type.MANSION -> "Mansion"
            Type.FLAT -> "Flat"
            Type.DUPLEX -> "Duplex"
            Type.HOUSE -> "House"
            Type.LOFT -> "Loft"
            Type.TOWNHOUSE -> "Townhouse"
            Type.CONDO -> "Condo"
        }

    fun getDistrictIntoStringForUi(district: District?) =
        when(district) {
            District.MANHATTAN -> "Manhattan"
            District.BROOKLYN -> "Brooklyn"
            District.STATEN_ISLAND -> "Staten Island"
            District.QUEENS -> "Queens"
            District.BRONX -> "Bronx"
            else -> "District unknown"
        }

    fun getCityIntoStringForUi(city: City?) =
        when(city) {
            City.NEW_YORK -> "New York"
            else -> "City unknown"
        }

    fun getCountryIntoStringForUi(country: Country?) =
        when(country) {
            Country.UNITED_STATES -> "United States"
            else -> "Country unknown"
        }

    fun getAgentIntoStringForUi(agentId: Int) =
        when(agentId) {
            1 -> "Harmonie Nee"
            2 -> "Clelie Lafaille"
            3 -> "Elisa Beauvau"
            4 -> "Josette Boutroux"
            5 -> "Albert Lafaille"
            6 -> "Omer Delaplace"
            7 -> "Robert Courtial"
            8 -> "Christopher Gaudreau"
            else -> "Agent unknown"
        }
}
