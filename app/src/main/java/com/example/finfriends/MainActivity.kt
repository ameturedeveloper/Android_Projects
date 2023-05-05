package com.example.finfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class MainActivity : AppCompatActivity(),NoteClickDeleteInterface,NoteClickInterface {


    lateinit var viewModel: NoteViewModel
    lateinit var notesRV : RecyclerView
    lateinit var addFab : FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notesRV = findViewById(R.id.notesRV)
        addFab = findViewById(R.id.idFAB)
        notesRV.layoutManager = LinearLayoutManager(this)

        val noteRVAdapter = NoteRVAdapter(this,this,this)
        notesRV.adapter = noteRVAdapter

        viewModel = ViewModelProvider(this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                noteRVAdapter.updateList(it)
            }
        }

        addFab.setOnClickListener{
            val intent = Intent(this@MainActivity, AddNote::class.java)
            startActivity(intent)
            this.finish()
        }

    }

    override fun onNoteClick(note: Note) {
       val intent = Intent(this@MainActivity,AddNote::class.java)
        intent.putExtra("noteType","Edit")
        intent.putExtra("noteTitle",note.noteTitle)
        intent.putExtra("noteDesc",note.noteDescription)
        intent.putExtra("noteId",note.id)
        intent.putExtra("noteMoney",note.noteMoney)
        startActivity(intent)
        this.finish()
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle} Deleted", Toast.LENGTH_SHORT).show()
    }

}