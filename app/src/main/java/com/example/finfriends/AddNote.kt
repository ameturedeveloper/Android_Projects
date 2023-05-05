package com.example.finfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import java.text.SimpleDateFormat
import java.util.*

class AddNote : AppCompatActivity() {

    lateinit var noteTitleEdit : EditText
    lateinit var noteEdit : EditText
    lateinit var saveBtn : Button
    lateinit var calBtn : Button
    lateinit var amount : EditText
    lateinit var viewModel: NoteViewModel
    var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        viewModel = ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(application))
            .get(NoteViewModel::class.java)

        noteTitleEdit = findViewById(R.id.idEdtNoteName)
        noteEdit = findViewById(R.id.idEdtNoteDesc)
        saveBtn = findViewById(R.id.idBtn)
        calBtn = findViewById(R.id.button)
        amount = findViewById(R.id.money)


        val noteType = intent.getStringExtra("noteType")
        if(noteType.equals("Edit")){
            val noteTitle = intent.getStringExtra("noteTitle")
            val noteDesc = intent.getStringExtra("noteDesc")
            val noteMo = intent.getStringExtra("noteMoney")
            noteId = intent.getIntExtra("noteId",-1)
            saveBtn.setText("Update Note")
            noteTitleEdit.setText(noteTitle)
            noteEdit.setText(noteDesc)
            amount.setText(noteMo)
            calTotal()
        }else{
            saveBtn.setText("Save Note")
        }

        saveBtn.setOnClickListener {
            val noteTitle = noteTitleEdit.text.toString()
            val noteDescription = noteEdit.text.toString()
            val amountRs =  amount.text.toString()

            if(noteType.equals("Edit")){
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val curDateTime : String = sdf.format(Date())
                    val updateNote = Note(noteTitle,noteDescription,curDateTime,calTotal())
                    updateNote.id = noteId
                    viewModel.updateNote(updateNote)
                    Toast.makeText(this,"NoteUpdated..",Toast.LENGTH_SHORT).show()
                }
            }else{
                if(noteTitle.isNotEmpty() && noteDescription.isNotEmpty()){
                    val sdf = SimpleDateFormat("dd MMM, yyyy - HH:mm")
                    val curDateTime : String = sdf.format(Date())
                    viewModel.addNote(Note(noteTitle,noteDescription,curDateTime,calTotal()))
                    Toast.makeText(this,"$noteTitle Added",Toast.LENGTH_SHORT).show()
                }
            }
            startActivity(Intent(applicationContext,MainActivity::class.java))
            this.finish()
        }

       calBtn.setOnClickListener {
           calTotal()
       }
    }

    fun calTotal():String{

        val input = amount.text.toString()
        var output = "0"
        try {
            output =  calculate(input).toString()
            calBtn.setText(output)
        }catch (e : java.lang.Exception){
            Toast.makeText(this,"Invalid Input", Toast.LENGTH_SHORT).show()
        }

        return output

    }

    fun calculate(s: String): Int {

        if(s == null) return 0

        val stackNumber = Stack<Int>()
        val chars = s.toCharArray()
        var sign: Char? = null
        var i = -1
        while (++i < chars.size) {
            val letter = chars[i]
            if (letter.isDigit()) {
                var num = Character.getNumericValue(letter)
                while ((i + 1) < s.length && chars[i + 1].isDigit()) {
                    num = num * 10 + Character.getNumericValue(chars[i + 1])
                    i++
                }
                when (sign) {
                    null -> stackNumber.push(num)
                    '+' -> stackNumber.push(num)
                    '-' -> stackNumber.push(-1 * num)
                    '*' -> stackNumber.push(num * stackNumber.pop())
                    '/' -> stackNumber.push(stackNumber.pop() / num)
                }
            } else {
                if (letter == '+' || letter == '-' || letter == '*' || letter == '/')
                    sign = letter
            }
        }

        return if (stackNumber.isEmpty()) 0 else stackNumber.sum()
    }
}