package edu.iu.eribecke.project6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import edu.iu.eribecke.project6.databinding.FragmentNoteBinding


class NoteFragment : Fragment() {
    // TODO: Rename and change types of parameters
    //setting up binding
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        //initializing variables
        val noteId = NoteFragmentArgs.fromBundle(requireArguments()).noteId

        val application = requireNotNull(this.activity).application

        val viewModel : HomeViewModel by activityViewModels()
        viewModel.noteId = noteId
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        //navigates back to home fragment when navigateToHome value is changed
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                view.findNavController()
                    .navigate(R.id.action_noteFragment_to_homeFragment)
                viewModel.onNavigatedToList()
            }
        })
        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}