package com.example.aiartcreator.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aiartcreator.R
import com.example.aiartcreator.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val buttons = listOf(surrealistBtn, steampunkBtn, rickmortyBtn, renaissanceBtn, portraitBtn)
            val categoryTextViews = listOf(surrealistTV, steampunkTV, rickmortyTV, renaissanceTV, portraitTV)

            promptET.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val text = s.toString()
                    createBtn.setOnClickListener {

                        promptET.text.clear()
                        findNavController().navigate(
                            HomeFragmentDirections.actionHomeFragmentToDownloadFragment(
                                text,
                                category
                            )
                        )
                    }
                }
            })
            buttons.forEachIndexed { index, button ->
                button.setOnClickListener {
                    reset()
                    val categoryTextView = categoryTextViews[index]
                    category = categoryTextView.text.toString()
                    button.text = "Using"
                    button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    button.setBackgroundResource(R.drawable.btn_home_using)
                }
            }

            settingsIcon.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToSettingsFragment())
            }

            historyIcon.setOnClickListener {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToHistoryFragment())
            }
        }
    }

    private fun reset() {
        binding.apply {
            val buttons = listOf(surrealistBtn, steampunkBtn, rickmortyBtn, renaissanceBtn, portraitBtn)
            buttons.forEach { button ->
                button.text = "Use"
                button.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
                button.setBackgroundResource(R.drawable.btn_home_use)
            }
        }
    }
}