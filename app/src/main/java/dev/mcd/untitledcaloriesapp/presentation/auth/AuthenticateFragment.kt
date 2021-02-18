package dev.mcd.untitledcaloriesapp.presentation.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mcd.untitledcaloriesapp.R
import dev.mcd.untitledcaloriesapp.databinding.AuthenticateFragmentBinding
import dev.mcd.untitledcaloriesapp.presentation.auth.AuthenticateFragmentDirections.authenticateToHome
import dev.mcd.untitledcaloriesapp.presentation.auth.AuthenticateViewModel.Events.ShowHome
import dev.mcd.untitledcaloriesapp.presentation.auth.AuthenticateViewModel.Events.ShowLoginError
import dev.mcd.untitledcaloriesapp.presentation.auth.AuthenticateViewModel.Events.ShowSignUpError
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthenticateFragment : Fragment(R.layout.authenticate_fragment) {

    private lateinit var binding: AuthenticateFragmentBinding
    private val viewModel: AuthenticateViewModel by hiltNavGraphViewModels(R.id.auth_graph)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = AuthenticateFragmentBinding.bind(view)
        setupViewModel()
        setupUI()
    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.events.collect {
                when (it) {
                    is ShowHome -> findNavController().navigate(authenticateToHome())
                    is ShowLoginError -> showError(it.cause, R.string.authenticate_login_error)
                    is ShowSignUpError -> showError(it.cause, R.string.authenticate_sign_up_error)
                }
            }
        }
    }

    private fun setupUI() {
        with(binding) {
            loginButton.setOnClickListener {
                validateInput(viewModel::onLogin)
            }
            signUpButton.setOnClickListener {
                validateInput(viewModel::onSignUp)
            }
        }
    }

    private fun validateInput(onSuccess: (email: String, password: String) -> Unit) {
        with(binding) {
            val email = emailText.text?.toString()
            val password = passwordText.text?.toString()

            if (email.isNullOrBlank() || password.isNullOrBlank()) {
                showNoInputError()
            } else {
                onSuccess(email, password)
            }
        }
    }

    private fun showNoInputError() {
        Toast.makeText(requireContext(), R.string.authenticate_error_no_input, Toast.LENGTH_SHORT).show()
    }

    private fun showError(cause: Throwable?, @StringRes id: Int) {
        Toast.makeText(requireContext(), "${getString(id)}\ncause=$cause", Toast.LENGTH_LONG).show()
    }
}
