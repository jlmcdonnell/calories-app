package dev.mcd.untitledcaloriesapp.presentation.auth.confirm

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.ConfirmEmailFragmentBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmEmailFragment : Fragment(R.layout.confirm_email_fragment) {

    private lateinit var binding: ConfirmEmailFragmentBinding
    private val confirmedViewModel: ConfirmEmailViewModel by navGraphViewModels(R.id.auth_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = ConfirmEmailFragmentBinding.bind(view)
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            confirmedViewModel.events.collect {
                TODO()
            }
        }
    }

    private fun setupUI() {
        with(binding) {
        }
    }
}
