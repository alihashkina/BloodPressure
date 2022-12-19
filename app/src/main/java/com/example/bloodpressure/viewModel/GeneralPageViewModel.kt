package com.example.bloodpressure.viewModel

import android.content.ContentValues
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bloodpressure.MainActivity.Companion.helper
import com.example.bloodpressure.R
import com.example.bloodpressure.adapters.CardAdapter
import com.example.bloodpressure.adapters.CardAdapter.Companion.deleteCard
import com.example.bloodpressure.adapters.dataClass.Card
import com.example.bloodpressure.db.MyDBHelper
import com.example.bloodpressure.fragment.GeneralPage.Companion.bindingGeneralPage
import com.example.bloodpressure.fragment.GeneralPage.Companion.tinyDB
import com.example.bloodpressure.fragment.Statistics
import com.example.bloodpressure.viewModel.StatisticsViewModel.Companion.counterSts
import kotlin.collections.ArrayList

class GeneralPageViewModel : ViewModel() {

    var counter: MutableLiveData<Int> = MutableLiveData(0)

    companion object{
        var dateDB = ""
        var arrayUpperGraph: ArrayList<Int> = arrayListOf()
        var arrayLowerGraph: ArrayList<Int> = arrayListOf()
        var arrayDateGraph : MutableList<String> = mutableListOf()
    }

    lateinit var cards: Card
    var chipsHDB = ""
    var chipsUhDB = ""
    var chipsSDB = ""
    var chipsCDB = ""
    var chipsODB = ""
    var upperDB = 120
    var lowerDB = 80
    var idDB = 0
    var cv = ContentValues()

    fun addDB(context: Context, dateDB: String, upperDB: Int, lowerDB: Int, chipsHealthyDB: List<String>, chipsUnHealthyDB: List<String>, chipsSymptomsDB: List<String>, chipsCareDB: List<String>, daysDB: Int, monthDB: Int, yearsDB: Int, hoursDB: Int, minuteDB: Int, chipsOtherDB: List<String>, idDB: Int){

        cv.put("DATE", dateDB)
        cv.put("UPPER", upperDB)
        cv.put("LOWER", lowerDB)
        cv.put("CHIPSHEALTHY", chipsHealthyDB.toString())
        cv.put("CHIPSUNHEALTHY", chipsUnHealthyDB.toString().replace("[", "").replace("]", ""))
        cv.put("CHIPSSYMPTOMS", chipsSymptomsDB.toString().replace("[", "").replace("]", ""))
        cv.put("CHIPSCARE", chipsCareDB.toString().replace("[", "").replace("]", ""))
        cv.put("DAYS", daysDB)
        cv.put("MONTH", monthDB)
        cv.put("YEARS", yearsDB)
        cv.put("HOURS", hoursDB)
        cv.put("MINUTE", minuteDB)
        cv.put("CHIPSOTHER", chipsOtherDB.toString().replace("[", "").replace("]", ""))
        cv.put("ID", idDB)

        MyDBHelper(context).writableDatabase.insert("USERS", null, cv)
    }

    fun readDB(){
        var db = helper.readableDatabase
        var rs = db.rawQuery(
            "SELECT DATE, UPPER, LOWER, CHIPSHEALTHY, CHIPSUNHEALTHY, CHIPSSYMPTOMS, CHIPSCARE, CHIPSOTHER, DAYS, MONTH, YEARS, HOURS, MINUTE, ID FROM USERS ORDER BY YEARS, MONTH, DAYS, HOURS, MINUTE ASC",
            null
        )

        arrayDateGraph = arrayListOf()
        arrayUpperGraph = arrayListOf()
        arrayLowerGraph = arrayListOf()

        if(!deleteCard){
            Statistics.adapter.update()
        }

        while (rs != null && rs.getCount() > 0 && rs.moveToNext()) {
            dateDB = rs.getString(0)
            upperDB = rs.getString(1).toInt()
            lowerDB = rs.getString(2).toInt()
            chipsHDB = rs.getString(3).replace(",", " | ")
            chipsUhDB = rs.getString(4).replace(",", " | ")
            chipsSDB = rs.getString(5).replace(",", " | ")
            chipsCDB = rs.getString(6).replace(",", " | ")
            chipsODB = rs.getString(7).replace(",", " | ")
            idDB = rs.getString(13).toInt()

            tinyDB.putString("DateDB", "+")

            arrayDateGraph.add(dateDB)
            arrayUpperGraph.add(upperDB)
            arrayLowerGraph.add(lowerDB)

            if (!deleteCard) {
                cards = Card(
                    dateDB,
                    chipsHDB,
                    chipsUhDB,
                    chipsSDB,
                    chipsCDB,
                    upperDB,
                    lowerDB,
                    chipsODB,
                    idDB
                )
                Statistics.adapter.addCard(cards)
            }

            counter.value?.let {
                counter.value = it + 1
            }
        }

    }

    fun deleteAllDB(context: Context){
        Toast.makeText(context, context.getString(R.string.toastDelete), Toast.LENGTH_SHORT).show()
        helper.writableDatabase.delete("USERS", null, null)
        counterSts.value?.let {
            counterSts.value = true
        }
        readDB()
    }

    fun deleteCardDB(context: Context){
        Toast.makeText(context, context.getString(R.string.toastDelete), Toast.LENGTH_SHORT).show()
        helper.writableDatabase.delete("USERS", "USERID=${CardAdapter.idDB}", null)
        readDB()
        bindingGeneralPage.deleteCard.callOnClick()
    }

    fun getHealthyList(context: Context):List<String>{
        return  listOf(
            context.resources.getString(R.string.breathingExercises),
            context.resources.getString(R.string.cardioWorkout),
            context.resources.getString(R.string.dairy),
            context.resources.getString(R.string.diet),
            context.resources.getString(R.string.gardening),
            context.resources.getString(R.string.goodSleep),
            context.resources.getString(R.string.highFiberFood),
            context.resources.getString(R.string.housework),
            context.resources.getString(R.string.lowSaltDiet),
            context.resources.getString(R.string.seafood),
            context.resources.getString(R.string.sex),
            context.resources.getString(R.string.sport),
            context.resources.getString(R.string.stretching),
            context.resources.getString(R.string.vegetables),
            context.resources.getString(R.string.walking),
            context.resources.getString(R.string.yoga),
            context.resources.getString(R.string.otherPhysicalActivity)

        )
    }
    fun getUnhealthyList(context: Context):List<String>{
        return  listOf(
            context.resources.getString(R.string.alcohol),
            context.resources.getString(R.string.coffee),
            context.resources.getString(R.string.energeticDrinks),
            context.resources.getString(R.string.fastfood),
            context.resources.getString(R.string.fatFood),
            context.resources.getString(R.string.irregularEating),
            context.resources.getString(R.string.irregularSports),
            context.resources.getString(R.string.lateDinner),
            context.resources.getString(R.string.longSitting),
            context.resources.getString(R.string.noActivity),
            context.resources.getString(R.string.overeating),
            context.resources.getString(R.string.overpressure),
            context.resources.getString(R.string.pastry),
            context.resources.getString(R.string.processedFood),
            context.resources.getString(R.string.salt),
            context.resources.getString(R.string.smoking),
            context.resources.getString(R.string.soda),
            context.resources.getString(R.string.stress),
            context.resources.getString(R.string.sugar),
            context.resources.getString(R.string.sweets)

        )
    }
    fun getSymptomsList(context: Context):List<String>{
        return  listOf(
            context.resources.getString(R.string.confused),
            context.resources.getString(R.string.coordinationProblems),
            context.resources.getString(R.string.crankyOrImpatient),
            context.resources.getString(R.string.decreasedVision),
            context.resources.getString(R.string.dizzy),
            context.resources.getString(R.string.dryMouth),
            context.resources.getString(R.string.drySkin),
            context.resources.getString(R.string.dyspnea),
            context.resources.getString(R.string.energetic),
            context.resources.getString(R.string.fastHeartbeat),
            context.resources.getString(R.string.fatigue),
            context.resources.getString(R.string.feelWell),
            context.resources.getString(R.string.goodMood),
            context.resources.getString(R.string.happy),
            context.resources.getString(R.string.headache),
            context.resources.getString(R.string.healSlowly),
            context.resources.getString(R.string.hunger),
            context.resources.getString(R.string.itchySkin),
            context.resources.getString(R.string.loseWeightWithoutTrying),
            context.resources.getString(R.string.nausea),
            context.resources.getString(R.string.nervous),
            context.resources.getString(R.string.nightmares),
            context.resources.getString(R.string.numbOrTinglingHandsOrFeet),
            context.resources.getString(R.string.painInChest),
            context.resources.getString(R.string.paleSkin),
            context.resources.getString(R.string.shaky),
            context.resources.getString(R.string.sleepy),
            context.resources.getString(R.string.sweaty),
            context.resources.getString(R.string.thirsty),
            context.resources.getString(R.string.urinateALot),
            context.resources.getString(R.string.weak),
            context.resources.getString(R.string.otherSymptoms)
        )
    }
    fun getCareList(context: Context):List<String>{
        return  listOf(
            context.resources.getString(R.string.aceInhibitors),
            context.resources.getString(R.string.alphaBlockers),
            context.resources.getString(R.string.alpha2ReceptorAgonists),
            context.resources.getString(R.string.angiotensin2ReceptorBlockers),
            context.resources.getString(R.string.betaBlockers),
            context.resources.getString(R.string.calciumChannelBlockers),
            context.resources.getString(R.string.centralAgonists),
            context.resources.getString(R.string.combinedAlphaAndBetablockers),
            context.resources.getString(R.string.diuretics),
            context.resources.getString(R.string.peripheralAdrenergicInhibitors),
            context.resources.getString(R.string.vasodilators),
            context.resources.getString(R.string.otherDrugs)
        )
    }
}