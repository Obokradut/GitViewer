package com.dechenkov.gitviewer.modules.authorization.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.FragmentAuthorizationBinding
import com.dechenkov.gitviewer.modules.authorization.domain.AuthViewModel
import com.dechenkov.gitviewer.shared.bindTextTwoWay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var binding: FragmentAuthorizationBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorizationBinding.inflate(inflater, container, false)

        with(binding){
            authViewModel.state.observe(viewLifecycleOwner) {
                authButton.isEnabled = it !is AuthViewModel.State.Loading
                authButton.text = if (it is AuthViewModel.State.Loading) "" else getText(R.string.sign_in)
                authButtonProgressbar.visibility =
                    if(it is AuthViewModel.State.Loading) View.VISIBLE else View.GONE
                tokenField.error = if (it is AuthViewModel.State.InvalidInput) it.reason else ""
            }
            authButton.setOnClickListener {
                authViewModel.onSingButtonPressed()
            }

            tokenField.editText?.bindTextTwoWay(authViewModel.token, this@AuthFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                authViewModel.actions.collect {
                    if (it is AuthViewModel.Action.ShowError) {
                        AlertDialog.Builder(requireContext())
                            .setPositiveButton("Ok") { _,_ -> }
                            .setMessage(it.message)
                            .setTitle("Error")
                            .create()
                            .show()
                    }
                }
            }
        }
    }
}