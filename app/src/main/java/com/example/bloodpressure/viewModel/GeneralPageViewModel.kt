package com.example.bloodpressure.viewModel

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.HorizontalScrollView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModel
import com.example.bloodpressure.R
import com.example.bloodpressure.db.MyDBHelper
import com.example.bloodpressure.fragment.GeneralPage
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayDateGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayLowerGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.arrayUpperGraph
import com.example.bloodpressure.fragment.GeneralPage.Companion.chipsCheckTxt
import com.example.bloodpressure.fragment.GeneralPage.Companion.dateDB
import com.google.android.material.chip.Chip
import im.dacer.androidcharts.LineView
import java.util.*

class GeneralPageViewModel : ViewModel() {

    companion object{
        val calendar = Calendar.getInstance()
        var year = 0
        var month = 0
        var day = 0
        var hour = 0
        var minute = 0
    }

    fun chipsCheck(chip1: Chip, chip2: Chip, chip3: Chip, chip4: Chip, chip5: Chip){
        if (chip1.isChecked) {
            if(chipsCheckTxt.contains(GeneralPage.bindingGeneralPage.chip1.text.toString())){
            }else{
                chipsCheckTxt = chipsCheckTxt + "${GeneralPage.bindingGeneralPage.chip1.text.toString() + " "}"
            }
        }

        if (chip2.isChecked) {
            if(chipsCheckTxt.contains(GeneralPage.bindingGeneralPage.chip2.text.toString())){
            }else {
                chipsCheckTxt = chipsCheckTxt + "${GeneralPage.bindingGeneralPage.chip2.text.toString() + " "}"
            }
        }

        if (chip3.isChecked) {
            if(chipsCheckTxt.contains(GeneralPage.bindingGeneralPage.chip3.text.toString())){
            }else {
                chipsCheckTxt = chipsCheckTxt + "${GeneralPage.bindingGeneralPage.chip3.text.toString() + " "}"
            }
        }

        if (chip4.isChecked) {
            if(chipsCheckTxt.contains(GeneralPage.bindingGeneralPage.chip4.text.toString())){
            }else {
                chipsCheckTxt = chipsCheckTxt + "${GeneralPage.bindingGeneralPage.chip4.text.toString() + " "}"
            }
        }

        if (chip5.isChecked) {
            if(chipsCheckTxt.contains(GeneralPage.bindingGeneralPage.chip5.text.toString())){
            }else {
                chipsCheckTxt = chipsCheckTxt + "${GeneralPage.bindingGeneralPage.chip5.text.toString() + " "}"
            }
        }
    }

    fun getDateTimeCalendar(txtRecord: TextView){
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        txtRecord.text = "Record $day.${month + 1}.$year $hour:$minute"
    }

    fun graph(graph: LineView, context: Context, scrollGraph: HorizontalScrollView, txtOnbord: LinearLayout){
        var helper = MyDBHelper(context!!)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT DATE, UPPER, LOWER, CHIPS, DAYS, MONTH, YEARS, HOURS, MINUTE FROM USERS ORDER BY YEARS, MONTH, DAYS, HOURS, MINUTE ASC", null)
        arrayDateGraph = arrayListOf()
        arrayUpperGraph = arrayListOf()
        arrayLowerGraph = arrayListOf()

        while (rs.moveToNext()) {
            dateDB = rs.getString(0)
            var upperDB = rs.getString(1).toInt()
            var lowerDB = rs.getString(2).toInt()
            rs.getString(3)
            arrayDateGraph.add(dateDB)
            arrayUpperGraph.add(upperDB)
            arrayLowerGraph.add(lowerDB)
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
}