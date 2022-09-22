package com.example.bloodpressure.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.bloodpressure.R
import com.example.bloodpressure.adapters.PagerAdapter
import com.example.bloodpressure.databinding.FragmentTabBinding

class TabFragment : Fragment() {

    companion object {
        fun newInstance() = TabFragment()
        lateinit var bindingTab: FragmentTabBinding
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingTab = DataBindingUtil.inflate(inflater, R.layout.fragment_tab,container,false)
        return bindingTab.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pagerAdapter = PagerAdapter(childFragmentManager)
        bindingTab.viewPager.adapter = pagerAdapter
        bindingTab.tabs.setupWithViewPager(bindingTab.viewPager)
    }

}