package edu.iu.eribecke.project6

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import edu.iu.eribecke.project6.databinding.FragmentHomeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    //setting up binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment, adding variables
        val application = requireNotNull(this.activity).application
        val dao = NoteDatabase.getInstance(application).noteDao
        val viewModelFactory = HomeViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,
            viewModelFactory).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        val view = binding.root

        //handles the action of clicking on a note
        fun noteClicked (noteId : Long){
            viewModel.onNoteClicked(noteId)
        }

        //deletes the note
        fun deleteConfirmation(noteId : Long){
            binding.viewModel?.deleteNote(noteId)
        }

        //triggers the dialogue for delete note confirmation
        fun deleteClicked (noteId : Long){
            DeleteConfirmDialogFragment(noteId,::deleteConfirmation).show(childFragmentManager,
                DeleteConfirmDialogFragment.TAG)
        }

        //initializing adapter
        val adapter = NoteItemAdapter(::noteClicked, ::deleteClicked)
        binding.notesList.adapter = adapter

        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        //transitions to notes fragment if the value of navigateToNote is changed
        viewModel.navigateToNote.observe(viewLifecycleOwner, Observer { noteId ->
            noteId?.let {
                val action = HomeFragmentDirections
                    .homeFragmentToNoteFragment(noteId)
                this.findNavController().navigate(action)
                viewModel.onNoteNavigated()
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}