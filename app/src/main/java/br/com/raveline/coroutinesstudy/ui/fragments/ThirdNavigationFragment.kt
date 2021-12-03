package br.com.raveline.coroutinesstudy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raveline.coroutinesstudy.R
import br.com.raveline.coroutinesstudy.databinding.ThirdNavigationFragmentBinding
import br.com.raveline.coroutinesstudy.viewmodel.ThirdNavigationViewModel
import br.com.raveline.coroutinesstudy.viewmodel.ThirdNavigationViewModelFactory

class ThirdNavigationFragment : Fragment() {


    private var _binding: ThirdNavigationFragmentBinding? = null
    private val thirdBinding get() = _binding!!

    private val thirdNavigationViewModelFactory = ThirdNavigationViewModelFactory()
    private val thirdViewModel: ThirdNavigationViewModel by viewModels { thirdNavigationViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ThirdNavigationFragmentBinding.inflate(inflater, container, false)

        thirdBinding.textViewThirdNavigationFragment.setOnClickListener {
            findNavController().navigate(R.id.action_thirdNavigationFragment_to_firstNavigationFragment)
        }

        thirdBinding.fabThirdNavigationFragment.setOnClickListener {
            findNavController().navigate(R.id.action_thirdNavigationFragment_to_secondNavigationFragment)
        }

        return thirdBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}