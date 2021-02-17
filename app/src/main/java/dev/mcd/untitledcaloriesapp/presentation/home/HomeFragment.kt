package dev.mcd.untitledcaloriesapp.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.HomeFragmentBinding
import dev.mcd.untitledcaloriesapp.domain.calories.entity.WeeklyOverview
import dev.mcd.untitledcaloriesapp.presentation.home.HomeFragmentDirections.actionAuthenticate
import dev.mcd.untitledcaloriesapp.presentation.home.HomeFragmentDirections.actionCreateEntry
import dev.mcd.untitledcaloriesapp.presentation.home.HomeViewModel.Events.*
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
                    is ShowAuthentication -> findNavController().navigate(actionAuthenticate())
                    is ShowCreateEntry -> findNavController().navigate(actionCreateEntry())
                    is ShowOverview -> displayOverview(it.overview)
                }
            }
        }
    }

    private fun displayOverview(overview: WeeklyOverview) {
        binding.weeklyOverview.dayValues = overview.entries
    }
}
