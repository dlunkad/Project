package com.example.apprenticeproject.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.apprenticeproject.databinding.ActivityMainBinding
import com.example.apprenticeproject.network.NetworkResult
import com.example.apprenticeproject.ui.adapter.HeaderItem
import com.example.apprenticeproject.ui.adapter.HiringAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    private lateinit var hiringAdapter: HiringAdapter
    private var list: ArrayList<Any> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initObservers()
        initView()
    }

    private fun initObservers() {
        viewModel.dataState.observe(this) { result ->
            when (result) {
                is NetworkResult.Loading -> {
                    binding.fetch.visibility = View.VISIBLE
                    binding.reason.visibility = View.GONE
                    binding.error.visibility = View.GONE
                }

                is NetworkResult.Success -> {
                    result.data?.let { hiringResponses ->
                        var filteredData = hiringResponses.filter { !it.name.isNullOrBlank() }
                        filteredData =
                            filteredData.sortedWith(
                                compareBy(
                                    { it.listId },
                                    { it.name?.substringAfter("Item ")?.toInt() },
//                                    { it.name }, // In case we want ordering in string format we can use this instead
                                )
                            )
                        list.clear()
                        for (index in filteredData.indices) {
                            if (index == 0 ||
                                filteredData.elementAt(index).listId != filteredData.elementAt(
                                    index - 1
                                ).listId
                            ) {
                                list.add(HeaderItem(filteredData[index].listId))
                            }
                            list.add(filteredData[index])
                        }
                        hiringAdapter.notifyDataSetChanged()
                        binding.fetch.visibility = View.GONE
                        binding.reason.visibility = View.GONE
                        binding.error.visibility = View.GONE
                    }
                }

                is NetworkResult.Error -> {
                    binding.reason.text = result.message
                    binding.fetch.visibility = View.GONE
                    binding.reason.visibility = View.VISIBLE
                    binding.error.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initView() {
        hiringAdapter = HiringAdapter(list)
        binding.hiringRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = hiringAdapter
        }
        viewModel.fetchData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}