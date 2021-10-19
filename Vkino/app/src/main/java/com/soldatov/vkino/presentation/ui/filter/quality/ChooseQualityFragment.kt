package com.soldatov.vkino.presentation.ui.filter.quality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soldatov.domain.models.Quality
import com.soldatov.domain.models.result.QualitiesResult
import com.soldatov.vkino.databinding.FragmentChooseQualityBinding
import com.soldatov.vkino.presentation.ui.filter.FilterFragmentViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ChooseQualityFragment: Fragment() {

    private lateinit var binding: FragmentChooseQualityBinding
    private val qualitiesListAdapter = QualitiesListAdapter()
    private val viewModel by sharedViewModel<FilterFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseQualityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.qualitiesList.adapter = qualitiesListAdapter
        binding.qualitiesList.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        binding.submit.setOnClickListener {
            val chosenQualities = qualitiesListAdapter.getChosenQualities()
            viewModel.setChosenQualities(chosenQualities)
            findNavController().popBackStack()
        }

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.qualities.observe(viewLifecycleOwner, {
            when (it) {
                is QualitiesResult.Success -> {
                    showQualitiesList(it.data)
                }
            }
        })
    }

    private fun showQualitiesList(qualities: List<Quality>) {
        val chosenQualities = viewModel.getChosenQualities()
        qualities.forEach{
            it.isChecked = chosenQualities.contains(it)
        }
        qualitiesListAdapter.setQualities(qualities)
    }
}