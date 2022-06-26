package com.example.travelapp.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelapp.data.util.Resource
import com.example.travelapp.databinding.FragmentTrainStationsBinding
import com.example.travelapp.presentation.adapter.TrainStationListAdapter
import com.example.travelapp.presentation.viewmodel.TrainStationsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrainStationsFragment : Fragment() {

    private lateinit var binding: FragmentTrainStationsBinding
    private val viewModel: TrainStationsViewModel by viewModels()
    private lateinit var trainStationListAdapter: TrainStationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrainStationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupObservers()
        binding.searchStations.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    newText?.let {
                        if (it.isEmpty()) {
                            viewModel.clearSearch()
                            viewModel.getTrainStations()
                        } else {
                            viewModel.enableSearch()
                            viewModel.searchTrainStations(newText)
                        }
                    }
                    return false
                }
            }
        )
        binding.searchStations.setOnCloseListener {
            resetStations()
            true
        }
    }

    private fun resetStations() {
        viewModel.clearSearch()
        viewModel.getTrainStations()
        binding.searchStations.onActionViewCollapsed()
    }

    private fun setupObservers() {
        viewModel.trainStations.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    hideErrorMessage()
                    response.data?.let { result ->
                        trainStationListAdapter.differ.submitList(result) {
                            binding.rvTrainStations.scrollToPosition(0)
                        }
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        showErrorMessage(response.message)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        trainStationListAdapter = TrainStationListAdapter()
        val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        binding.rvTrainStations.apply {
            adapter = trainStationListAdapter
            addItemDecoration(divider)
            layoutManager = LinearLayoutManager(activity)
        }
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showErrorMessage(message: String) {
        binding.itemErrorMessage.errorCard.visibility = View.VISIBLE
        binding.itemErrorMessage.tvErrorMessage.text = message
    }

    private fun hideErrorMessage() {
        binding.itemErrorMessage.errorCard.visibility = View.GONE
    }

}