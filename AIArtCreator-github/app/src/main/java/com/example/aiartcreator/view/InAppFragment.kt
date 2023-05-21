package com.example.aiartcreator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.aiartcreator.R
import com.example.aiartcreator.databinding.FragmentInAppBinding


class InAppFragment : Fragment() {
    private lateinit var binding: FragmentInAppBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInAppBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.apply {
            val viewList = listOf(weeklyPlanView,yearlyPlanView)
            startBtn.setOnClickListener {
                findNavController().navigate(InAppFragmentDirections.actionInAppFragmentToHomeFragment())
            }

            viewList.forEach { view->
                view.setOnClickListener {
                    reset()
                    view.setBackgroundResource(R.drawable.button_inapp_1)
                }
            }

            xIcon.setOnClickListener {
                findNavController().navigate(InAppFragmentDirections.actionInAppFragmentToHomeFragment())
            }
        }

    }

    private fun reset() {
        binding.apply {
            val viewList = listOf(weeklyPlanView,yearlyPlanView)
            viewList.forEach { view ->
                view.setBackgroundResource(R.drawable.button_inapp_2)
            }
        }
    }


}