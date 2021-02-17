package dev.mcd.untitledcaloriesapp.presentation.createentry

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.CreateEntryBinding
import dev.mcd.untitledcaloriesapp.presentation.createentry.CreateEntryFragmentDirections.actionCreateEntryToHome
import dev.mcd.untitledcaloriesapp.presentation.createentry.CreateEntryViewModel.Events.*
import dev.mcd.untitledcaloriesapp.presentation.extensions.hideKeyboard

@AndroidEntryPoint
class CreateEntryFragment : Fragment(R.layout.create_entry) {

    private val viewModel by hiltNavGraphViewModels<CreateEntryViewModel>(R.id.nav_graph)

    private lateinit var binding: CreateEntryBinding

    private val caloriesInput: Int?
        get() = binding.amountText.text?.toString()?.toIntOrNull()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupViewModel()
    }

    private fun setupUI() {
        binding = CreateEntryBinding.bind(requireView())
        with(binding) {
            createEntryButton.setOnClickListener {
                updateEntry()
            }
            amountText.setOnEditorActionListener { _, _, _ ->
                updateEntry()
                true
            }
        }
    }

    private fun setupViewModel() {
        viewModel.start()
        viewModel.events.observe(viewLifecycleOwner) {
            when (it) {
                is ShowNoCaloriesToday -> displayNoCaloriesToday()
                is ShowCaloriesToday -> displayCaloriesToday(it.calories)
                is ShowCreateError -> showError()
                is Dismiss -> {
                    activity?.hideKeyboard()
                    findNavController().navigate(actionCreateEntryToHome())
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(requireContext(), getString(R.string.create_entry_error), Toast.LENGTH_SHORT).show()
    }

    private fun displayCaloriesToday(calories: Int) {
        with(binding) {
            caloriesToday.text = getString(R.string.create_entry_title_calories, calories)
            createEntryButton.text = getString(R.string.create_entry_cta_update)
            createEntryButton.isVisible = true
        }
    }

    private fun displayNoCaloriesToday() {
        with(binding) {
            caloriesToday.text = getString(R.string.create_entry_title_no_calories)
            createEntryButton.text = getString(R.string.create_entry_cta_add)
            createEntryButton.isVisible = true
        }
    }

    private fun updateEntry() {
        caloriesInput?.let {
            viewModel.onCreateEntry(it)
        }
    }
}
