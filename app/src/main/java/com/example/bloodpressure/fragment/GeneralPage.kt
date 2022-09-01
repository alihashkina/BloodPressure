package com.example.bloodpressure.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.Color.BLUE
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.bloodpressure.R
import com.example.bloodpressure.databinding.GeneralPageFragmentBinding
import com.example.bloodpressure.db.MyDBHelper
import com.example.bloodpressure.viewModel.GeneralPageViewModel
import com.example.bloodpressure.viewModel.GeneralPageViewModel.Companion.day
import com.example.bloodpressure.viewModel.GeneralPageViewModel.Companion.hour
import com.example.bloodpressure.viewModel.GeneralPageViewModel.Companion.minute
import com.example.bloodpressure.viewModel.GeneralPageViewModel.Companion.month
import com.example.bloodpressure.viewModel.GeneralPageViewModel.Companion.year

class GeneralPage : Fragment(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    companion object {
        fun newInstance() = GeneralPage()
        lateinit var bindingGeneralPage: GeneralPageFragmentBinding
        var chipsCheckTxt = ""
        var arrayDateGraph : MutableList<String> = mutableListOf()
        var arrayUpperGraph : ArrayList<Int> = arrayListOf()
        var arrayLowerGraph : ArrayList<Int> = arrayListOf()
        var dateDB = ""
        var nPickUpperValues = 120
        var nPickLowerValues = 80
    }

    var saveyear = 0
    var savemonth = 0
    var saveday = 0
    var savehour = 0
    var saveminute = 0

    private lateinit var viewModel: GeneralPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingGeneralPage= DataBindingUtil.inflate(inflater, R.layout.general_page_fragment,container,false)
        return bindingGeneralPage.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(GeneralPageViewModel::class.java)

        bindingGeneralPage.scrollGraph.post {
            bindingGeneralPage.scrollGraph.fullScroll(View.FOCUS_RIGHT)
        }

            bindingGeneralPage.nPickUpper.setOnValueChangedListener { picker, oldVal, newVal ->
                nPickUpperValues = newVal
            }

        bindingGeneralPage.nPickLower.setOnValueChangedListener { picker, oldVal, newVal ->
            nPickLowerValues = newVal
        }

        viewModel.graph(bindingGeneralPage.graph, requireContext(), bindingGeneralPage.scrollGraph, bindingGeneralPage.txtOnbord)

        bindingGeneralPage.btnSave.setOnClickListener{
            viewModel.chipsCheck(bindingGeneralPage.chip1, bindingGeneralPage.chip2, bindingGeneralPage.chip3, bindingGeneralPage.chip4, bindingGeneralPage.chip5)
            var cv = ContentValues()
            cv.put("DATE", "${bindingGeneralPage.txtRecord.text.drop(7)}")
            cv.put("UPPER", nPickUpperValues)
            cv.put("LOWER", nPickLowerValues)
            cv.put("CHIPS", "${chipsCheckTxt}")
            cv.put("DAYS", bindingGeneralPage.txtRecord.text.toString().drop(7).split(".")?.get(0).toInt())
            cv.put("MONTH", bindingGeneralPage.txtRecord.text.toString().drop(7).split(".")?.get(1).toInt())
            cv.put("YEARS", bindingGeneralPage.txtRecord.text.toString().drop(7).split(".")?.get(2).dropLast(5).replace(" ", "").toInt())
            cv.put("HOURS", bindingGeneralPage.txtRecord.text.toString().drop(7).split(" ")?.get(1).dropLast(2).replace(":", "").toInt())
            cv.put("MINUTE", bindingGeneralPage.txtRecord.text.toString().drop(7).split(":")?.get(1).toInt())
            MyDBHelper(requireContext()).readableDatabase.insert("USERS", null, cv)
            chipsCheckTxt = ""
            viewModel.graph(bindingGeneralPage.graph, requireContext(), bindingGeneralPage.scrollGraph, bindingGeneralPage.txtOnbord)
            bindingGeneralPage.scrollGraph.post {
                bindingGeneralPage.scrollGraph.fullScroll(View.FOCUS_RIGHT)
            }
        }

        if(bindingGeneralPage.txtRecord.text == ""){
            viewModel.getDateTimeCalendar(bindingGeneralPage.txtRecord)
        }
        pickDate()
    }

    private fun pickDate(){
        bindingGeneralPage.txtRecord.setOnClickListener{
            viewModel.getDateTimeCalendar(bindingGeneralPage.txtRecord)
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        saveday = day
        savemonth = month
        saveyear = year
        viewModel.getDateTimeCalendar(bindingGeneralPage.txtRecord)
        TimePickerDialog(requireContext(), this, hour, minute, false).show()
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        savehour = hour
        saveminute = minute
        bindingGeneralPage.txtRecord.text = "Record $saveday.${savemonth + 1}.$saveyear $savehour:$saveminute"
    }

}