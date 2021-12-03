package br.com.raveline.coroutinesstudy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.databinding.SecondNavigationFragmentBinding
import br.com.raveline.coroutinesstudy.viewmodel.SecondNavigationViewModel
import br.com.raveline.coroutinesstudy.viewmodel.SecondNavigationViewModelFactory

class SecondNavigationFragment : Fragment() {

    private var _binding: SecondNavigationFragmentBinding? = null
    private val secondBinding get() = _binding!!

    private val secondNavigationViewModelFactory = SecondNavigationViewModelFactory()
    private val secondViewModel: SecondNavigationViewModel by viewModels { secondNavigationViewModelFactory }

    private val args: SecondNavigationFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SecondNavigationFragmentBinding.inflate(inflater, container, false)

        secondViewModel.textFromOne.value = args.anotherText

        secondBinding.textViewSecondNavigationFragment.text = secondViewModel.textValue.value

        secondBinding.textViewSecondNavigationFragment.setOnClickListener {
            findNavController().navigate(R.id.action_secondNavigationFragment_to_thirdNavigationFragment)
        }

        secondBinding.fabSecondNavigationFragment.setOnClickListener {
            findNavController().navigate(R.id.action_secondNavigationFragment_to_firstNavigationFragment)
        }
        return secondBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}