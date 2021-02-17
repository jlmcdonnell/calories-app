package dev.mcd.untitledcaloriesapp.presentation.auth.confirmed

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.EmailConfirmedFragmentBinding
import dev.mcd.untitledcaloriesapp.presentation.auth.confirmed.EmailConfirmedViewModel.Events
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmailConfirmedFragment : Fragment(R.layout.email_confirmed_fragment) {

    private lateinit var binding: EmailConfirmedFragmentBinding
    private val viewModel: EmailConfirmedViewModel by navGraphViewModels(R.id.auth_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = EmailConfirmedFragmentBinding.bind(view)
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    Events.ShowHome -> {
                        findNavController().navigate(EmailConfirmedFragmentDirections.confirmedToHome())
                    }
                }
            }
        }
    }

    private fun setupUI() {
        with(binding) {
            getStartedButton.setOnClickListener {
                viewModel.onGetStartedClicked()
            }
        }
    }
}
