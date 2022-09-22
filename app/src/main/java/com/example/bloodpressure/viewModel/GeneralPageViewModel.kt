package com.example.bloodpressure.viewModel

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModel
import com.example.bloodpressure.R
import com.example.bloodpressure.adapters.dataClass.Card
import com.example.bloodpressure.db.MyDBHelper
import com.example.bloodpressure.fragment.GeneralPage
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayDateGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayLowerGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayUpperGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.bindingGeneralPage
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsCDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsCareCheck
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsCheckTxt
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsHDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsHealthyCheck
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsSDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsSymptomsCheck
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsUhDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsUnHealthyCheck
import com.example.bloodpressure.fragment.GeneralPage.Companion.dateDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.lowerDB
import com.example.bloodpressure.fragment.GeneralPage.Companion.nPickLowerValues
import com.example.bloodpressure.fragment.GeneralPage.Companion.nPickUpperValues
import com.example.bloodpressure.fragment.GeneralPage.Companion.upperDB
import com.example.bloodpressure.fragment.Statistics
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import im.dacer.androidcharts.LineView
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

class GeneralPageViewModel : ViewModel() {

    companion object{
        val calendar = Calendar.getInstance()
        var year = 0
        var month = 0
        var day = 0
        var hour = 0
        var minute = 0
        lateinit var cards: Card
    }

    var dateSt = ""
    var chipsHSt = ""
    var chipsUhSt = ""
    var chipsSSt = ""
    var chipsCSt = ""
    var sugarSt = 0
    var sugarStml = 0
    var chipsGroup = ""
    var colors = intArrayOf()

    fun getDateTimeCalendar(txtRecord: TextView, context: Context){
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        var sdf = SimpleDateFormat("dd.MM.yyyy HH:mm")
        var currentDate = sdf.format(Date())
        txtRecord.text = "${context.getString(R.string.record)} $currentDate"
    }

    fun graph(graph: LineView, context: Context, scrollGraph: HorizontalScrollView, txtOnbord: LinearLayout){
        var helper = MyDBHelper(context!!)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT DATE, UPPER, LOWER, CHIPSHEALTHY, CHIPSUNHEALTHY, CHIPSSYMPTOMS, CHIPSCARE, DAYS, MONTH, YEARS, HOURS, MINUTE FROM USERS ORDER BY YEARS, MONTH, DAYS, HOURS, MINUTE ASC", null)
        arrayDateGraph = arrayListOf()
        arrayUpperGraph = arrayListOf()
        arrayLowerGraph = arrayListOf()

        while (rs.moveToNext()) {
            dateDB = rs.getString(0)
            upperDB = rs.getString(1).toInt()
            lowerDB = rs.getString(2).toInt()
            chipsHDB = rs.getString(3).replace(",", " | ")
            chipsUhDB = rs.getString(4).replace(",", " | ")
            chipsSDB = rs.getString(5).replace(",", " | ")
            chipsCDB = rs.getString(6).replace(",", " | ")
            arrayDateGraph.add(dateDB)
            arrayUpperGraph.add(upperDB)
            arrayLowerGraph.add(lowerDB)

            cards = Card(dateDB, chipsHDB, chipsUhDB, chipsSDB, chipsCDB, upperDB.toString(), lowerDB.toString())
            Statistics.adapter.addCard(GeneralPageViewModel.cards)
        }

        if(dateDB != "") {
            scrollGraph.visibility = View.VISIBLE
            txtOnbord.visibility = View.GONE
            var bloodLists = ArrayList<ArrayList<Int>>()
            bloodLists = arrayListOf(arrayUpperGraph, arrayLowerGraph)
            graph.setDrawDotLine(false) //optional
            graph.getResources().getColor(R.color.md_white_1000)
            graph.setShowPopup(LineView.SHOW_POPUPS_All) //optional
            graph.setBottomTextList(arrayDateGraph as ArrayList<String>?)
            graph.setColorArray(intArrayOf(Color.RED, Color.BLUE))
            graph.marginBottom
            graph.paddingBottom
            graph.setDataList(bloodLists)
        }
    }

    fun chipsColorHealthy(chipGroupHealthy: ChipGroup, view: View){
        chipsGroup = "Healthy"
        handleSelection(view)
        chipGroupHealthy.children.forEach {
            val chip = it as Chip
            chip.chipBackgroundColor = colorStates()
            (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                handleSelection(view)
            }
        }
    }

    fun chipsColorUnhealthy(chipGroupUnhealthy: ChipGroup, view: View){
        chipsGroup = "Unhealthy"
        handleSelection(view)
        chipGroupUnhealthy.children.forEach {
            val chip = it as Chip
            chip.chipBackgroundColor = colorStates()
            (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                handleSelection(view)
            }
        }
    }

    fun chipsColorSymptoms(chipGroupSymptoms: ChipGroup, view: View){
        chipsGroup = "Symptoms"
        handleSelection(view)
        chipGroupSymptoms.children.forEach {
            val chip = it as Chip
            chip.chipBackgroundColor = colorStates()
            (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                handleSelection(view)
            }
        }
    }

    fun chipsColorCare(chipGroupCare: ChipGroup, view: View){
        chipsGroup = "Care"
        handleSelection(view)
        chipGroupCare.children.forEach {
            val chip = it as Chip
            chip.chipBackgroundColor = colorStates()
            (it as Chip).setOnCheckedChangeListener { buttonView, isChecked ->
                handleSelection(view)
            }
        }
    }

    fun colorStates(): ColorStateList {
        val states = arrayOf(
            intArrayOf(android.R.attr.state_checked),
            intArrayOf(-android.R.attr.state_checked)
        )
        when (chipsGroup){
            "Healthy" -> colors = intArrayOf(Color.parseColor("#69F0AE"), Color.parseColor("#E0E0E0"))
            "Unhealthy" -> colors = intArrayOf(Color.parseColor("#FF8A80"), Color.parseColor("#E0E0E0"))
            "Symptoms" -> colors = intArrayOf(Color.parseColor("#81D4fA"),Color.parseColor("#E0E0E0"))
            "Care" -> colors = intArrayOf(Color.parseColor("#FFF590"),Color.parseColor("#E0E0E0"))
        }
        return ColorStateList(states, colors)
    }

    fun handleSelection(view: View){
        bindingGeneralPage.chipGroupHealthy.checkedChipIds.forEach{
            val chip = view?.findViewById<Chip>(it)
            chipsHealthyCheck.add("${chip?.text}")
            chip.isChecked = true
        }
        bindingGeneralPage.chipGroupUnhealthy.checkedChipIds.forEach{
            val chip = view?.findViewById<Chip>(it)
            chipsUnHealthyCheck.add("${chip?.text}")
            chip.isChecked = true
        }
        bindingGeneralPage.chipGroupSymptoms.checkedChipIds.forEach{
            val chip = view?.findViewById<Chip>(it)
            chipsSymptomsCheck.add("${chip?.text}")
            chip.isChecked = true

        }
        bindingGeneralPage.chipGroupCare.checkedChipIds.forEach{
            val chip = view?.findViewById<Chip>(it)
            chipsCareCheck.add("${chip?.text}")
            chip.isChecked = true
        }
    }

    fun btnSaveText(btnSave: ExtendedFloatingActionButton, context: Context){
        btnSave.text = "$nPickUpperValues / $nPickLowerValues ${context.getString(R.string.save)}"
    }
}