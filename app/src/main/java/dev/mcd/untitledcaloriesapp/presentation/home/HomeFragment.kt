package dev.mcd.untitledcaloriesapp.presentation.home

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.HomeFragmentBinding
import dev.mcd.untitledcaloriesapp.domain.calories.entity.DayOverview
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeekOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeFragmentDirections.actionAuthenticate
import dev.mcd.untitledcaloriesapp.presentation.home.HomeFragmentDirections.actionSaveEntry
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.DisplayDayOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.DisplayWeekOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.NavigateAuthentication
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.NavigateCreateEntry
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.ShowError
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.home_fragment) {

    private lateinit var binding: HomeFragmentBinding
    private val viewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = HomeFragmentBinding.bind(requireView())
        setupViewModel()
        setupUI()
    }

    private fun setupUI() {
        with(binding) {
            addEntryButton.setOnClickListener {
                viewModel.onAddEntryClicked()
            }
        }
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.start()
            viewModel.events.observe(viewLifecycleOwner) {
                when (it) {
                    is NavigateAuthentication -> findNavController().navigate(actionAuthenticate())
                    is NavigateCreateEntry -> findNavController().navigate(actionSaveEntry())
                    is DisplayWeekOverview -> displayWeekOverview(it.overview)
                    is DisplayDayOverview -> displayDayOverview(it.overview)
                    is ShowError -> displayError()
                }
            }
        }
    }

    private fun displayWeekOverview(overview: WeekOverview) {
        with(binding.weeklyOverview) {
            dayValues = overview.entries
            limit = overview.limit
        }
    }

    private fun displayDayOverview(overview: DayOverview) {
        with(binding) {
            addEntryButton.isEnabled = true
            addEntryButton.text = if (overview.hasAddedEntryToday) {
                getString(R.string.home_entry_cta_update)
            } else {
                getString(R.string.home_entry_cta_add)
            }
        }
    }

    private fun displayError() {
        Toast.makeText(requireContext(), R.string.home_error, Toast.LENGTH_SHORT).show()
    }
}
