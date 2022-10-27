package com.example.geekhub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.geekhub.databinding.FragmentChattingBinding

class ChattingFragment : Fragment() {

    lateinit var binding : FragmentChattingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChattingBinding.inflate(inflater,container,false)

        binding.adminChatting.visibility = View.VISIBLE

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        setTimeSpinner()
        setLocationSpinner()
        timeSpinnerHandler()
        locationSpinnerHandler()

        binding.adminTitle.setOnClickListener{
            openAdmin()
        }
        binding.colleagueTitle.setOnClickListener{
            openColleague()
        }


    }


    private fun setTimeSpinner () {
        val spinner = binding.spinnerTime
        var item =  arrayOf("저녁1","저녁2")
        val adapter = ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,item)
        spinner.adapter = adapter
    }
    private fun setLocationSpinner () {
        val spinner = binding.spinnerLocation
        var item = arrayOf("알촌1동","알촌2동")
        val adapter = ArrayAdapter(requireActivity(),android.R.layout.simple_spinner_item,item)
        spinner.adapter = adapter

    }

    private fun timeSpinnerHandler () {
        binding.spinnerTime.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun locationSpinnerHandler() {
        binding.spinnerLocation.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun openAdmin () {
       binding.adminChatting.visibility = View.VISIBLE
       binding.colleagueChatting.visibility = View.INVISIBLE
    }

    private fun openColleague () {
        binding.colleagueChatting.visibility = View.VISIBLE
        binding.adminChatting.visibility = View.INVISIBLE
    }



}