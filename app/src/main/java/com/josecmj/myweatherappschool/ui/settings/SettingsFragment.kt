package com.josecmj.myweatherappschool.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.josecmj.myweatherappschool.R
import com.josecmj.myweatherappschool.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        settingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val sharedPref = context?.getSharedPreferences(getString(R.string.pref_file), Context.MODE_PRIVATE)

        if (sharedPref != null) {
            val langPT = sharedPref.getBoolean("lang_PT", true)
            val tempC = sharedPref.getBoolean("temp_C", true)

            if(langPT) {
                binding.radioButtonPt.isChecked = true;
            } else {
                binding.radioButtonEn.isChecked = true;
            }

            if(tempC) {
                binding.radioButtonC.isChecked = true;
            } else {
                binding.radioButtonF.isChecked = true;
            }
        }


        binding.btnSave.setOnClickListener{
            if (sharedPref != null) {
                with(sharedPref.edit()){
                    putBoolean("lang_PT",binding.radioButtonPt.isChecked)
                    putBoolean("temp_C",binding.radioButtonC.isChecked)
                    apply()
                }
            }
        }
        return root
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}