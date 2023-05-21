package com.example.aiartcreator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.aiartcreator.viewmodel.AppViewModel
import com.example.aiartcreator.adapter.HistoryAdapter
import com.example.aiartcreator.daoModel.RoomEntity
import com.example.aiartcreator.databinding.FragmentHistoryBinding
import com.example.aiartcreator.utils.RecDecor
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by viewModel<AppViewModel>()
    private val imageList : MutableList<RoomEntity> = mutableListOf()
    private lateinit var mAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            backBtn.setOnClickListener {
                findNavController().navigateUp()
            }
        }
        createRecyclerView()
    }

    private fun createRecyclerView() {
        getAllData()
        mAdapter = HistoryAdapter(imageList)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.addItemDecoration(RecDecor())
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)

    }

    private fun getAllData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allImageList.collectLatest {
                imageList.addAll(it)
                mAdapter.notifyDataSetChanged()
            }
        }
    }
}