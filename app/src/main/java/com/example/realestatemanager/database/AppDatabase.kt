package com.example.realestatemanager.database

import android.content.Context
import com.example.realestatemanager.database.dao.*
import android.content.ContentValues
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.realestatemanager.database.converter.*
import com.example.realestatemanager.database.converter.TypeConverter
import com.example.realestatemanager.model.*
import java.util.*

@Database(entities = [Property::class,
    Address::class,
    Agent::class,
    PropertyAndLocationOfInterest::class,
    PropertyPhoto::class,
    PropertyAndPropertyPhoto::class] , version = 7, exportSchema = false)
@TypeConverters(
    CityConverter::class,
    CountryConverter::class,
    DateConverter::class,
    DistrictConverter::class,
    LocationOfInterestConverter::class,
    TypeConverter::class,
    WordingConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun addressDao(): AddressDao
    abstract fun agentDao(): AgentDao
    abstract fun propertyAndLocationOfInterestDao(): PropertyAndLocationOfInterestDao
    abstract fun propertyAndPropertyPhotoDao(): PropertyAndPropertyPhotoDao
    abstract fun propertyPhotoDao(): PropertyPhotoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        private val contentValuesAddress = ContentValues()
        private val contentValuesProperty = ContentValues()
        private val contentValuesAgent = ContentValues()
        private val contentValuesPropertyAndLocationOfInterest = ContentValues()
        private val contentValuesPropertyPhoto = ContentValues()
        private val contentValuesPropertyAndPropertyPhoto = ContentValues()

        private const val NAME = "name"
        private const val FIRSTNAME = "firstName"

        private const val PATH = "path"
        private const val COMPLEMENT = "complement"
        private const val DISTRICT = "district"
        private const val CITY = "city"
        private const val POSTAL_CODE = "postalCode"
        private const val COUNTRY = "country"
        private const val TYPE = "type"
        private const val PRICE = "price"
        private const val SURFACE = "surface"
        private const val ROOMS = "rooms"
        private const val BEDROOMS = "bedrooms"
        private const val BATHROOMS = "bathrooms"
        private const val DESCRIPTION = "description"
        private const val ADDRESS_ID = "addressId"
        private const val AVAILABLE = "available"
        private const val ENTRY_DATE = "entryDate"
        private const val SALE_DATE = "saleDate"
        private const val AGENT_ID = "agentId"

        private const val PROPERTY_ID = "propertyId"
        private const val LOCATION_OF_INTEREST_ID = "locationOfInterestId"

        private const val WORDING = "wording"
        private const val IS_THIS_THE_ILLUSTRATION = "isThisTheIllustration"

        private const val PROPERTY_PHOTO_ID = "propertyPhotoId"

        private var propertyPhotoId = 0

        fun getInstance(context: Context): AppDatabase {
            INSTANCE.let {}
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, "Database.db")
                            .fallbackToDestructiveMigration()
                            .addCallback(prepopulateDatabase())
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }

        private fun prepopulateDatabase(): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)

                    addEveryAgentsIntoDatabase(db)

                    firstProperty(db)
                    secondProperty(db)
                    thirdProperty(db)
                    fourthProperty(db)
                    fifthProperty(db)
                    sixthProperty(db)
                    seventhProperty(db)
                    eighthProperty(db)
                    ninthProperty(db)
                    tenthProperty(db)

                    addFirstPropertyAndLocationOfInterestIntoDatabase(1, db)
                    addSecondPropertyAndLocationOfInterestIntoDatabase(2, db)
                    addThirdPropertyAndLocationOfInterestIntoDatabase(3, db)
                    addFourthPropertyAndLocationOfInterestIntoDatabase(4, db)
                    addFifthPropertyAndLocationOfInterestIntoDatabase(5, db)
                    addSixthPropertyAndLocationOfInterestIntoDatabase(6, db)
                    addSeventhPropertyAndLocationOfInterestIntoDatabase(7, db)
                    addEighthPropertyAndLocationOfInterestIntoDatabase(8, db)
                    addNinthPropertyAndLocationOfInterestIntoDatabase(9, db)
                    addTenthPropertyAndLocationOfInterestIntoDatabase(10, db)

                    addFirstPropertyPhotoIntoDatabase(db)
                    addSecondPropertyPhotoIntoDatabase(db)
                    addThirdPropertyPhotoIntoDatabase(db)
                    addFourthPropertyPhotoIntoDatabase(db)
                    addFifthPropertyPhotoIntoDatabase(db)
                    addSixthPropertyPhotoIntoDatabase(db)
                    addSeventhPropertyPhotoIntoDatabase(db)
                    addEighthPropertyPhotoIntoDatabase(db)
                    addNinthPropertyPhotoIntoDatabase(db)
                    addTenthPropertyPhotoIntoDatabase(db)

                    addFirstPropertyAndPropertyPhotoIntoDatabase(1, db)
                    addSecondPropertyAndPropertyPhotoIntoDatabase(2, db)
                    addThirdPropertyAndPropertyPhotoIntoDatabase(3, db)
                    addFourthPropertyAndPropertyPhotoIntoDatabase(4, db)
                    addFifthPropertyAndPropertyPhotoIntoDatabase(5, db)
                    addSixthPropertyAndPropertyPhotoIntoDatabase(6, db)
                    addSeventhPropertyAndPropertyPhotoIntoDatabase(7, db)
                    addEighthPropertyAndPropertyPhotoIntoDatabase(8, db)
                    addNinthPropertyAndPropertyPhotoIntoDatabase(9, db)
                    addTenthPropertyAndPropertyPhotoIntoDatabase(10, db)
                }
            }
        }

        private fun addEveryAgentsIntoDatabase(db: SupportSQLiteDatabase) {
            buildAgentAndInsert("Nee", "Harmonie", db)
            buildAgentAndInsert("Lafaille", "Clelie", db)
            buildAgentAndInsert("Beauvau", "Elisa", db)
            buildAgentAndInsert("Boutroux", "Josette", db)
            buildAgentAndInsert("Lafaille", "Albert", db)
            buildAgentAndInsert("Delaplace", "Omer", db)
            buildAgentAndInsert("Courtial", "Robert", db)
            buildAgentAndInsert("Gaudreau", "Christopher", db)
        }

        private fun buildAgentAndInsert(name: String, firstName: String, db: SupportSQLiteDatabase) {
            contentValuesAgent.put(NAME, name)
            contentValuesAgent.put(FIRSTNAME, firstName)
            db.insert("Agent", OnConflictStrategy.IGNORE, contentValuesAgent)
        }

        private fun buildFakeAddress(path: String,
                                     complement: String? = null,
                                     district: Int,
                                     city: Int = CityConverter.fromCity(City.NEW_YORK),
                                     postalCode: String,
                                     country: Int = CountryConverter.fromCountry(Country.UNITED_STATES)) {
            contentValuesAddress.put(PATH, path)
            if (complement == null ) {
                contentValuesAddress.putNull(COMPLEMENT)
            } else {
                contentValuesAddress.put(COMPLEMENT, complement)
            }
            contentValuesAddress.put(DISTRICT, district)
            contentValuesAddress.put(CITY, city)
            contentValuesAddress.put(POSTAL_CODE, postalCode)
            contentValuesAddress.put(COUNTRY, country)
        }

        private fun buildFakeProperty(type: Int,
                                      price: Long,
                                      surface: Int,
                                      rooms: Int,
                                      bedrooms: Int,
                                      bathrooms: Int,
                                      description: String,
                                      addressId: Int,
                                      available: Boolean,
                                      entryDate: Long,
                                      saleDate: Long? = null,
                                      agentId: Int) {
            contentValuesProperty.put(TYPE, type)
            contentValuesProperty.put(PRICE, price)
            contentValuesProperty.put(SURFACE, surface)
            contentValuesProperty.put(ROOMS, rooms)
            contentValuesProperty.put(BEDROOMS, bedrooms)
            contentValuesProperty.put(BATHROOMS, bathrooms)
            contentValuesProperty.put(DESCRIPTION, description)
            contentValuesProperty.put(ADDRESS_ID, addressId)
            contentValuesProperty.put(AVAILABLE, available)
            contentValuesProperty.put(ENTRY_DATE, entryDate)
            if (saleDate == null) {
            } else {
                contentValuesProperty.put(SALE_DATE, saleDate)
            }
            contentValuesProperty.put(AGENT_ID, agentId)
        }

        private fun insertValue(db: SupportSQLiteDatabase) {
            db.insert("Address", OnConflictStrategy.IGNORE, contentValuesAddress)
            db.insert("Property", OnConflictStrategy.IGNORE, contentValuesProperty)
        }

        private fun firstProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "122 Watchogue Road",
                district = DistrictConverter.fromDistrict(District.STATEN_ISLAND),
                postalCode = "NY 10314"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.HOUSE),
                price = 599000,
                surface = 1180,
                rooms = 6,
                bedrooms = 3,
                bathrooms = 1,
                description = "Detached charming one-family colonial in prime Westerleigh location!! Entering this lovely home there is a vestibule before opening up into a spacious living room. The first floor open layout spans to the formal dining room and lovely kitchen with access to the backyard deck. The second level offers three generously sized bedrooms, and an oversized bathroom with a jacuzzi tub. Hardwood floors throughout the entire home. The finished basement has a 3/4 bath with a summer kitchen and separate entrance",
                addressId = 1,
                available = true,
                entryDate = DateConverter.fromDate(Date(1549574288)),
                agentId = 1
            )
            insertValue(db)
        }

        private fun secondProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "126 Tyrrell Street",
                complement = "#4A",
                district = DistrictConverter.fromDistrict(District.STATEN_ISLAND),
                postalCode = "NY 10307"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.DUPLEX),
                price = 925000,
                surface = 2200,
                rooms = 7,
                bedrooms = 4,
                bathrooms = 3,
                description = "Beautiful 2 family home in Tottenville Staten Island. A mid-block location. Main unit entry foyer, Living room, dining room, kitchen with breakfast area, 3 bedrooms and 2.5 baths, laundry/storage and a one car garage. Second unit studio with kitchen and 1 full bath. Entertainers dream backyard with a pool and gazebo.",
                addressId = 2,
                available = true,
                entryDate = DateConverter.fromDate(Date(1562752237)),
                agentId = 2
            )
            insertValue(db)
        }

        private fun thirdProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "270 West 17th St",
                complement = "#65A",
                district = DistrictConverter.fromDistrict(District.MANHATTAN),
                postalCode = "NY 10022"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.CONDO),
                price = 1600000,
                surface = 847,
                rooms = 5,
                bedrooms = 2,
                bathrooms = 2,
                description = "Welcome to The Chelsea Grand, one of Chelsea's most and sought-after condominium buildings. This luxury doorman building offers a modern design with top-level service to its residents. A beautiful courtyard to layout and escape the bustling energy of the city, a stone's throw away from the Hudson River Greenway, an exceptional promenade that caters to biking, running, and physical exercise all around. Coupled with Chelsea piers, Little Island, picnic areas, and lovely playgrounds.",
                addressId = 3,
                available = true,
                entryDate = DateConverter.fromDate(Date(1562586286)),
                agentId = 3
            )
            insertValue(db)
        }

        private fun fourthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "784 Park Avenue",
                complement = "#1",
                district = DistrictConverter.fromDistrict(District.MANHATTAN),
                postalCode = "NY 10021"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.PENTHOUSE),
                price = 7995000,
                surface = 5955,
                rooms = 8,
                bedrooms = 3,
                bathrooms = 4,
                description = "Extensively-renovated beauty in the distinguished Emery Roth luxury prewar cooperative at 784 Park Avenue! This impeccably-redesigned duplex 3 Bedroom 4 Bath stunner boasts the true feel of a real home offering generously-proportioned rooms, refined finishes throughout and a gracious layout for living and entertaining.",
                addressId = 4,
                available = true,
                entryDate = DateConverter.fromDate(Date(1548328108)),
                agentId = 4
            )
            insertValue(db)
        }

        private fun fifthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "460 W 236th St",
                district = DistrictConverter.fromDistrict(District.BRONX),
                postalCode = "NY 10463"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.PENTHOUSE),
                price = 650000,
                surface = 6240,
                rooms = 5,
                bedrooms = 2,
                bathrooms = 2,
                description = "Welcome home to the Latitude Condominium! 2 bedroom, 2 bath luxury condo, corner unit residence. Luxury finishes by Andres Escobar feature Stone Quartz Countertops, Mahogany Wood Cabinets, Glass Ceramic Tile Splashes, Custom Rain Showers, Radiant Heat Limestone Tile Floor, and stackable washer/dryer in the unit. Other apartment features include hardwood flooring throughout, ample closet space, central A/C/HEATING PTAC units, and an open floor plan which is convenient for entertaining.",
                addressId = 5,
                available = true,
                entryDate = DateConverter.fromDate(Date(1561979308)),
                agentId = 5
            )
            insertValue(db)
        }

        private fun sixthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "1100 Grand Concourse",
                district = DistrictConverter.fromDistrict(District.BRONX),
                postalCode = "NY 10456"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.TOWNHOUSE),
                price = 259999,
                surface = 800,
                rooms = 4,
                bedrooms = 1,
                bathrooms = 1,
                description = "Pre-war, oversized 1 bedroom 1 bathroom residence located in the Grand Concourse Historic District. Enjoy this Grandiose floor plan offering high ceilings, large living room space with large windows, eat-in kitchen, and ample closet space throughout the home. Washer/dryer are allowed in the unit (currently available with the sale).",
                addressId = 6,
                available = true,
                entryDate = DateConverter.fromDate(Date(1563359033)),
                agentId = 6
            )
            insertValue(db)
        }

        private fun seventhProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "1501 Voorhies Avenue",
                district = DistrictConverter.fromDistrict(District.BROOKLYN),
                postalCode = "NY 11235"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.CONDO),
                price = 3280000,
                surface = 2502,
                rooms = 7,
                bedrooms = 4,
                bathrooms = 3,
                description = "Perched atop revered 1 Brooklyn Bay, this sprawling four-bedroom, three-bathroom penthouse impresses with stunning views, two private terraces and gorgeous designer interiors in an amenity-rich Sheepshead Bay condominium.",
                addressId = 7,
                available = true,
                entryDate = DateConverter.fromDate(Date(1558347833)),
                saleDate = DateConverter.fromDate(Date(1560595695)),
                agentId = 7
            )
            insertValue(db)
        }

        private fun eighthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "215 N 10th St",
                district = DistrictConverter.fromDistrict(District.BROOKLYN),
                postalCode = "NY 11211"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.CONDO),
                price = 1700000,
                surface = 1022,
                rooms = 4,
                bedrooms = 2,
                bathrooms = 2,
                description = "Situated in the center of Brooklyn’s most vibrant neighborhood, NX at 215 North 10th Street is designed to meet the expectations of an ever-changing life. Designed inside and out by famed architect Morris Adjmi, NX meets Williamsburg’s contemporary landscape that combines the vernacular of its row houses and warehouses." ,
                addressId = 8,
                available = true,
                entryDate = DateConverter.fromDate(Date(1560081674)),
                agentId = 1
            )
            insertValue(db)
        }

        private fun ninthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "20-10 26th St",
                district = DistrictConverter.fromDistrict(District.QUEENS),
                postalCode = "NY 11105"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.PENTHOUSE),
                price = 999000,
                surface = 1200,
                rooms = 5,
                bedrooms = 2,
                bathrooms = 1,
                description = "Fully renovated brick home, this Ditmars area one family is move-in ready. With its gleaming, refinished hardwood floors, this adorable home enjoys both eastern and western exposures. Large living room opens to a formal dining room and renovated kitchen. Exit in the rear to an attached sunroom and private rear yard plus a detached garage. Full finished basement with exit to the front and rear of the home.",
                addressId = 9,
                available = true,
                entryDate = DateConverter.fromDate(Date(1559915888)),
                agentId = 4
            )
            insertValue(db)
        }

        private fun tenthProperty(db: SupportSQLiteDatabase) {
            buildFakeAddress(
                path = "68-34 76th St",
                district = DistrictConverter.fromDistrict(District.QUEENS),
                postalCode = "NY 11379"
            )
            buildFakeProperty(
                type = TypeConverter.fromType(Type.HOUSE),
                price = 1250000,
                surface = 3417,
                rooms = 12,
                bedrooms = 6,
                bathrooms = 4,
                description = "Welcome to 68-34 76th Street, perfectly situated in Middle Village, Queens. This beautifully renovated two-family home has everything you've been searching for. Featuring 2 incredibly spacious, well-designed apartments, each with Samsung state of the art stainless steel appliances and en suite master baths are a must-see!",
                addressId = 10,
                available = false,
                entryDate = DateConverter.fromDate(Date(1559075359)),
                agentId = 6
            )
            insertValue(db)
        }

        private fun addFirstPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addSecondPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addThirdPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addFourthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addFifthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.TRAIN),db)
        }

        private fun addSixthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.PARK),db)
        }

        private fun addSeventhPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
        }

        private fun addEighthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addNinthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun addTenthPropertyAndLocationOfInterestIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SCHOOL),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.COMMERCES),db)
            buildPropertyAndLocationOfInterestAndInsert(propertyId,LocationOfInterestConverter.fromLocationOfInterest(LocationOfInterest.SUBWAYS),db)
        }

        private fun buildPropertyAndLocationOfInterestAndInsert(propertyId: Int, locationOfInterestId: Int, db: SupportSQLiteDatabase) {
            contentValuesPropertyAndLocationOfInterest.put(PROPERTY_ID, propertyId)
            contentValuesPropertyAndLocationOfInterest.put(LOCATION_OF_INTEREST_ID, locationOfInterestId)
            db.insert("PropertyAndLocationOfInterest", OnConflictStrategy.IGNORE, contentValuesPropertyAndLocationOfInterest)
        }

        private fun addFirstPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("6.jpg", WordingConverter.fromWording(Wording.GARDEN), false, db)
            buildPropertyPhotoAndInsert("7.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
        }

        private fun addSecondPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.STREET_VIEW), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("6.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("7.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("8.jpg", WordingConverter.fromWording(Wording.GARDEN), false, db)
        }

        private fun addThirdPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.TERRACE), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
        }

        private fun addFourthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.STAIRS), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("6.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("7.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
        }

        private fun addFifthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
        }

        private fun addSixthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.HALL), false, db)
        }

        private fun addSeventhPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.BALCONY), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
        }

        private fun addEighthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.TERRACE), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
        }

        private fun addNinthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.STAIRS), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
        }

        private fun addTenthPropertyPhotoIntoDatabase(db: SupportSQLiteDatabase) {
            buildPropertyPhotoAndInsert("0.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), true, db)
            buildPropertyPhotoAndInsert("1.jpg", WordingConverter.fromWording(Wording.KITCHEN), false, db)
            buildPropertyPhotoAndInsert("2.jpg", WordingConverter.fromWording(Wording.LIVING_ROOM), false, db)
            buildPropertyPhotoAndInsert("3.jpg", WordingConverter.fromWording(Wording.BEDROOM), false, db)
            buildPropertyPhotoAndInsert("4.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("5.jpg", WordingConverter.fromWording(Wording.BATHROOM), false, db)
            buildPropertyPhotoAndInsert("6.jpg", WordingConverter.fromWording(Wording.TERRACE), false, db)
        }

        private fun buildPropertyPhotoAndInsert(name: String, wording: Int, isThisTheIllustration: Boolean, db: SupportSQLiteDatabase) {
            contentValuesPropertyPhoto.put(NAME, name)
            contentValuesPropertyPhoto.put(WORDING, wording)
            contentValuesPropertyPhoto.put(IS_THIS_THE_ILLUSTRATION, isThisTheIllustration)
            db.insert("PropertyPhoto", OnConflictStrategy.IGNORE, contentValuesPropertyPhoto)
        }

        private fun addFirstPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId, getPropertyPhotoId(), db)
        }

        private fun addSecondPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addThirdPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addFourthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addFifthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addSixthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addSeventhPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addEighthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addNinthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun addTenthPropertyAndPropertyPhotoIntoDatabase(propertyId: Int, db: SupportSQLiteDatabase) {
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
            buildPropertyAndPropertyPhotoAndInsert(propertyId,getPropertyPhotoId(), db)
        }

        private fun getPropertyPhotoId(): Int {
            propertyPhotoId++
            return propertyPhotoId
        }

        private fun buildPropertyAndPropertyPhotoAndInsert(propertyId: Int, propertyPhotoId: Int, db: SupportSQLiteDatabase) {
            contentValuesPropertyAndPropertyPhoto.put(PROPERTY_ID, propertyId)
            contentValuesPropertyAndPropertyPhoto.put(PROPERTY_PHOTO_ID, propertyPhotoId)
            db.insert("PropertyAndPropertyPhoto", OnConflictStrategy.IGNORE, contentValuesPropertyAndPropertyPhoto)
        }
    }
}