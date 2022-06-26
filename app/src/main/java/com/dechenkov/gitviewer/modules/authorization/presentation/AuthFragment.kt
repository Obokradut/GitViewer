package com.dechenkov.gitviewer.modules.authorization.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dechenkov.gitviewer.R
import com.dechenkov.gitviewer.databinding.FragmentAuthorizationBinding
import com.dechenkov.gitviewer.modules.authorization.domain.AuthViewModel
import com.dechenkov.gitviewer.shared.bindText
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {
    private lateinit var authorizationBinding: FragmentAuthorizationBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        authorizationBinding = FragmentAuthorizationBinding.inflate(inflater, container, false)

        with(authorizationBinding) {
            authViewModel.state.observe(viewLifecycleOwner) {
                if (it is AuthViewModel.State.Loading) {
                    authButton.isEnabled = false
                    authButton.text = ""
                    authButtonProgressbar.visibility = View.VISIBLE
                } else {
                    authButton.isEnabled = true
                    authButton.text = getText(R.string.sign_in)
                    authButtonProgressbar.visibility = View.GONE
                }
                tokenField.error =
                    if (it is AuthViewModel.State.InvalidInput) it.reason
                    else ""
            }
            authButton.setOnClickListener {
                authViewModel.onSingButtonPressed()
            }
            tokenField.editText?.bindText(authViewModel.token, this@AuthFragment)
        }
        return authorizationBinding.root
    }
}