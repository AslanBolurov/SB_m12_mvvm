package com.skillbox.aslanbolurov.android.searchfield_mvvm

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.skillbox.aslanbolurov.android.searchfield_mvvm.databinding.FragmentMvvmPatternBinding
import kotlinx.coroutines.flow.collect

class MVVMpattern : Fragment() {

    companion object {
        fun newInstance() = MVVMpattern()
    }

    private val viewModel: MVVMpatternViewModel by viewModels()


    private var _binding: FragmentMvvmPatternBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMvvmPatternBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener {
            val textSearch: String = binding.searchField.text.toString()

            binding.tvResult.setText("Nothing was found for your request <$textSearch>")
            viewModel.onSignInClick(textSearch)
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.state.collect { state: State ->
                when (state) {
                    State.Loading -> {
                        binding.progressBar.isVisible = true
                        binding.button.isEnabled = false
                        binding.searchLayout.error = null
                        binding.searchField.addTextChangedListener(textWatcher)
                    }
                    State.Success -> {
                        binding.progressBar.isVisible = false
                        binding.button.isEnabled = true
                        binding.searchLayout.error = null
                        binding.searchField.addTextChangedListener(textWatcher)
                    }
                    is State.Error -> {
                        binding.button.isEnabled = false
                        binding.tvResult.text = "Error:${state.msg}"
                        binding.searchLayout.error = state.msg
                        binding.searchField.addTextChangedListener(textWatcher)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.error.collect {
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }
    }
    val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun afterTextChanged(s: Editable?) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (s.toString().length <= 3) {
                binding.button.isEnabled = false
            } else {
                binding.button.isEnabled = true
            }
        }


    }
}


