package com.example.realestatemanager.util

import android.content.Context
import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.res.TypedArrayUtils.getString
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import android.net.ConnectivityManager
import com.example.realestatemanager.R
import com.example.realestatemanager.model.*
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.*
import kotlin.math.roundToLong


/**
 * Created by Philippe on 21/02/2018.
 */

object Utils {

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    /**
     * Conversion de la date d'aujourd'hui en un format plus approprié
     * NOTE : NE PAS SUPPRIMER, A MONTRER DURANT LA SOUTENANCE
     * @return
     */
    val todayDate: String
        get() {
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
     * @propertyId is the name of the directory
     * @name is the name of the file as to be selected
     * */

    fun getInternalBitmap(propertyId: String?, name: String?, context: Context?): Bitmap {
        val folder = File(context?.filesDir, propertyId)
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

    fun setInternalBitmap(photo: Bitmap?, propertyId: String, name: String, context: Context?) {
        val folder = File(context?.filesDir, propertyId)
        val file = File(folder, name)
        file.parentFile.mkdirs()
        val fos = FileOutputStream(file)
        try {
            photo?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
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

    fun deleteInternalBitmap(propertyId: String, name: String?, context: Context) {
        val folder = File(context.filesDir, propertyId)
        val file = File(folder, name)
        file.delete()
    }

    fun sendNotification(context: Context, somethingHappened: String?) {
        val CHANNEL_ID = "CHANNEL_ID"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("RealEstateManager")
            .setContentText(somethingHappened)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        NotificationManagerCompat.from(context).notify(0, builder.build())
    }

    //---TO-DATABASE---\\
    fun returnComplementOrNull(complement: String) = if (complement.isNotEmpty()) { complement } else { null }

    fun fromStringToDistrict(district: String) =
        when(district) {
            "Bronx" -> District.BRONX
            "Brooklyn" -> District.BROOKLYN
            "Manhattan" -> District.MANHATTAN
            "Queens" -> District.QUEENS
            "Staten Island" -> District.STATEN_ISLAND
            else -> District.BRONX
        }

    fun fromStringToCity(city: String) =
        when(city) {
            "New York" -> City.NEW_YORK
            else -> City.NEW_YORK
        }

    fun fromStringToCountry(country: String) =
        when(country) {
            "United States" -> Country.UNITED_STATES
            else -> Country.UNITED_STATES
        }

    fun fromStringToType(type: String) =
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

    fun fromStringToAgent(fullNameAgent: String) =
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

    fun fromWordingToString(wording: Wording?) =
        when(wording) {
            Wording.STREET_VIEW -> "Street view"
            Wording.LIVING_ROOM -> "Living room"
            Wording.HALL -> "Hall"
            Wording.KITCHEN -> "Kitchen"
            Wording.DINING_ROOM -> "Dining room"
            Wording.BATHROOM -> "Bathroom"
            Wording.BALCONY -> "Balcony"
            Wording.BEDROOM -> "Bedroom"
            Wording.TERRACE -> "Terrace"
            Wording.WALK_IN_CLOSET -> "Walk in closet"
            Wording.OFFICE -> "Office"
            Wording.ROOF_TOP -> "Roof top"
            Wording.PLAN -> "plan"
            Wording.HALLWAY -> "Hallway"
            Wording.VIEW -> "View"
            Wording.GARAGE -> "Garage"
            Wording.SWIMMING_POOL -> "Swimming pool"
            Wording.FITNESS_CENTRE -> "Fitness centre"
            Wording.SPA -> "Spa"
            Wording.CINEMA -> "Cinema"
            Wording.CONFERENCE -> "Conference"
            Wording.STAIRS -> "Stairs"
            Wording.GARDEN -> "Garden"
            Wording.FLOOR -> "Floor"
            else -> "Unknown wording"
        }

    //---TO-UI---\\

    fun fromTypeToString(type: Type) =
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

    fun fromDistrictToString(district: District?) =
        when(district) {
            District.MANHATTAN -> "Manhattan"
            District.BROOKLYN -> "Brooklyn"
            District.STATEN_ISLAND -> "Staten Island"
            District.QUEENS -> "Queens"
            District.BRONX -> "Bronx"
            else -> "District unknown"
        }

    fun fromCityToString(city: City?) =
        when(city) {
            City.NEW_YORK -> "New York"
            else -> "City unknown"
        }

    fun fromCountryToString(country: Country?) =
        when(country) {
            Country.UNITED_STATES -> "United States"
            else -> "Country unknown"
        }

    fun fromAgentIdToString(agentId: Int) =
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

    fun fromPriceToString(price: Long) = "$" + NumberFormat.getIntegerInstance().format(price)

    fun fromSurfaceToString(surface: Int?) = surface.toString() + "sq ft"

    fun fromAgentToString(firstName: String?, name: String?) = "$firstName $name"

    fun fromEntryDateToString(entryDate: Long) = dateFormat.format(Date(entryDate))

    fun fromSaleDateToString(saleDate: Long?) =
        if (saleDate != null) {
            dateFormat.format(Date(saleDate))
        } else {
            ""
        }

    fun fromStringToWording(wording: String?) =
        when(wording) {
            "Street View" -> Wording.STREET_VIEW
            "Living room" -> Wording.LIVING_ROOM
            "Hall" -> Wording.HALL
            "Kitchen" -> Wording.KITCHEN
            "Dining room" -> Wording.DINING_ROOM
            "Bathroom" -> Wording.BATHROOM
            "Balcony" -> Wording.BALCONY
            "Bedroom" -> Wording.BEDROOM
            "Terrace" -> Wording.TERRACE
            "Walk in closet" -> Wording.WALK_IN_CLOSET
            "Office" -> Wording.OFFICE
            "Roof top" -> Wording.ROOF_TOP
            "plan" -> Wording.PLAN
            "Hallway" -> Wording.HALLWAY
            "View" -> Wording.VIEW
            "Garage" -> Wording.GARAGE
            "Swimming pool" -> Wording.SWIMMING_POOL
            "Fitness centre" -> Wording.FITNESS_CENTRE
            "Spa" -> Wording.SPA
            "Cinema" -> Wording.CINEMA
            "Conference" -> Wording.CONFERENCE
            "Stairs" -> Wording.STAIRS
            "Garden" -> Wording.GARDEN
            "Floor" -> Wording.FLOOR
            else -> Wording.STREET_VIEW
        }

    fun createNamePhoto(index: Int) = "$index.jpg"
}
