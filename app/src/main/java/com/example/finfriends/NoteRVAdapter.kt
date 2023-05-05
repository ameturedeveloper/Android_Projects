package com.example.finfriends

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class NoteRVAdapter(
    val context: Context,
    val noteClickDeleteInterface: NoteClickDeleteInterface,
    val noteClickInterface: NoteClickInterface
) : RecyclerView.Adapter<NoteRVAdapter.ViewHolder>() {

    private val allNotes = ArrayList<Note>()
    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
            val noteTv = itemView.findViewById<TextView>(R.id.idTVNote)
            val dateTV = itemView.findViewById<TextView>(R.id.idTVDate)
            val deleteIV = itemView.findViewById<ImageView>(R.id.idIVDelete)
            val totalTv = itemView.findViewById<TextView>(R.id.idTVTotal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.note_add,parent,false
        )
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allNotes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.noteTv.setText(allNotes.get(position).noteTitle)
        holder.dateTV.setText("Last Updated: "+ allNotes.get(position).timestamp)
        holder.totalTv.setText(allNotes.get(position).noteMoney)
        holder.deleteIV.setOnClickListener{
            noteClickDeleteInterface.onDeleteIconClick(allNotes.get(position))
        }
        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allNotes.get(position))
        }


    }

    fun updateList(newList: List<Note>){
        allNotes.clear()
        allNotes.addAll(newList)
        notifyDataSetChanged()
    }

}

interface NoteClickInterface {
    fun onNoteClick(note:Note)
}

interface NoteClickDeleteInterface {
    fun onDeleteIconClick(note: Note)
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