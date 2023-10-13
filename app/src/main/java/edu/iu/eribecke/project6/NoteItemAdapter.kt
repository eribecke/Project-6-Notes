package edu.iu.eribecke.project6

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.iu.eribecke.project6.databinding.NoteItemBinding


class NoteItemAdapter(val clickListener: (noteId: Long) -> Unit, val deleteClickListener: (noteId: Long) -> Unit)
: ListAdapter<Note, NoteItemAdapter.NoteItemViewHolder>(NoteDiffItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : NoteItemViewHolder = NoteItemViewHolder.inflateFrom(parent)
    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener, deleteClickListener)
    }

    //inflating recyclerView
    class NoteItemViewHolder(val binding: NoteItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): NoteItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NoteItemBinding.inflate(layoutInflater, parent, false)
                return NoteItemViewHolder(binding)
            }
        }

        //sets listeners on Note item components
        fun bind(
            item: Note,
            clickListener: (noteId: Long) -> Unit,
            deleteClickListener: (noteId: Long) -> Unit)
        {
            binding.note = item
            binding.root.setOnClickListener { clickListener(item.noteId)}
            binding.deleteButton.setOnClickListener { deleteClickListener(item.noteId) }
            }
        }
    }
