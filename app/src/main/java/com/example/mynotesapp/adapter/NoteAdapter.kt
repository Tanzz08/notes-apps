package com.example.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.databinding.ItemNoteBinding
import com.example.mynotesapp.entity.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<NoteAdapter.NoteVIewHolder>() {

    var listNotes = ArrayList<Note>()
        set(listNotes) {
            if (listNotes.size > 0) {
                this.listNotes.clear()
            }
            this.listNotes.addAll(listNotes)
        }

    inner class NoteVIewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvItemTitle.text = note.title
            binding.tvItemDate.text = note.date
            binding.tvItemDescription.text = note.description
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }

    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int?)

    }

    // metode untuk menambahkan di recycler view dan memberi animasi jika ada data baru dengan menggunakan fungsi not
    fun addItem(note: Note) {
        this.listNotes.add(note)
        notifyItemInserted(this.listNotes.size - 1)
    }

    // metode untuk memperbarui item di recyclerview
    fun updateItem(position: Int, note: Note) {
        this.listNotes[position] = note
        notifyItemChanged(position, note)
    }

    // metode untuk menghapus data
    fun removeItem(position: Int) {
        this.listNotes.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNotes.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteVIewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteVIewHolder(view)
    }

    override fun getItemCount(): Int = this.listNotes.size

    override fun onBindViewHolder(holder: NoteVIewHolder, position: Int) {
        holder.bind(listNotes[position])
    }
}