package br.com.raveline.coroutinesstudy.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.raveline.coroutinesstudy.databinding.FirstNavigationFragmentBinding
import br.com.raveline.coroutinesstudy.viewmodel.FirstNavigationViewModel
import br.com.raveline.coroutinesstudy.viewmodel.FirstNavigationViewModelFactory

class FirstNavigationFragment : Fragment() {

    private var _binding: FirstNavigationFragmentBinding? = null
    private val firstBinding get() = _binding!!

    private val firstNavigationViewModelFactory = FirstNavigationViewModelFactory()
    private val firstViewModel: FirstNavigationViewModel by viewModels { firstNavigationViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FirstNavigationFragmentBinding.inflate(inflater, container, false)

        firstBinding.textViewFirstNavigationFragment.setOnClickListener {

            val action: FirstNavigationFragmentDirections.ActionFirstNavigationFragmentToSecondNavigationFragment

            if (!TextUtils.isEmpty(firstBinding.editTextFirstNavigationFragment.text.toString())) {
                /* val bundle =
                     bundleOf("text" to firstBinding.editTextFirstNavigationFragment.text.toString())

                 findNavController().navigate(
                     R.id.action_firstNavigationFragment_to_secondNavigationFragment,
                     bundle
                 )*/

                val value = firstBinding.editTextFirstNavigationFragment.text.toString()

                action = FirstNavigationFragmentDirections
                    .actionFirstNavigationFragmentToSecondNavigationFragment()
                    .setAnotherText(value)

            } else {
                action = FirstNavigationFragmentDirections
                    .actionFirstNavigationFragmentToSecondNavigationFragment()

            }

            findNavController().navigate(action)
        }

        return firstBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}