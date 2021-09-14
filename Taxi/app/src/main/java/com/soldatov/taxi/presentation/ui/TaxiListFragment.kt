package com.soldatov.taxi.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.data.utils.Result
import com.soldatov.taxi.R
import com.soldatov.taxi.presentation.utils.ViewModelFactory
import com.soldatov.taxi.presentation.viewmodel.MainActivityViewModel

class TaxiListFragment : Fragment() {

    private lateinit var taxiList: RecyclerView
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var taxiListAdapter: TaxiListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_taxi_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(activity as ViewModelStoreOwner, ViewModelFactory())
                .get(MainActivityViewModel::class.java)

        setupObservers()
        taxiListAdapter = TaxiListAdapter(viewModel)
        taxiList.adapter = taxiListAdapter
        taxiList.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        taxiList = view.findViewById(R.id.taxi_list)
    }

    private fun setupObservers() {
        viewModel.taxiInfo.observe(context as LifecycleOwner, {
            when (it) {
                is Result.Success -> {
                    taxiListAdapter.setTaxiList(it.data)
                }
                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                Result.Loading -> {
                    //TODO preloader
                }
            }
        })
    }
}