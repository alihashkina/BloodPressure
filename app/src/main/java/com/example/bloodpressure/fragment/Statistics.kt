package com.example.bloodpressure.fragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.example.bloodpressure.R
import com.example.bloodpressure.adapters.CardAdapter
import com.example.bloodpressure.databinding.StatisticsFragmentBinding
import com.example.bloodpressure.viewModel.StatisticsViewModel

class Statistics : Fragment() {

    companion object {
        fun newInstance() = Statistics()
        lateinit var bindingStatistics: StatisticsFragmentBinding
        val adapter = CardAdapter()
        lateinit var viewModelSt: StatisticsViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingStatistics = DataBindingUtil.inflate(inflater, R.layout.statistics_fragment, container, false)
        return bindingStatistics.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModelSt = ViewModelProvider(this).get(StatisticsViewModel::class.java)
        bindingStatistics.apply {
            recyclerStatistics.layoutManager = GridLayoutManager(context, 1)
            recyclerStatistics.adapter = adapter
        }
    }

}