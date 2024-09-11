package com.rk.notes.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rk.notes.adapter.RealTimeDataFetchAdapter
import com.rk.notes.dataModel.RealTimeData
import com.rk.notes.R
import com.rk.notes.databinding.ActivityRetriveFromRealTimeBinding
import com.rk.notes.databinding.UpdateNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RetriveFromRealTime : Fragment(), RealTimeDataFetchAdapter.OnItemClickListener {
    private lateinit var binding: ActivityRetriveFromRealTimeBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RealTimeDataFetchAdapter // Declare adapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRetriveFromRealTimeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()
        navigateToAddNote()
    }

    private fun init() {
        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()
        recyclerView = binding.recyclerView3
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Initialize adapter with an empty list and set it to RecyclerView
        adapter = RealTimeDataFetchAdapter(mutableListOf(), this)
        recyclerView.adapter = adapter
    }

    private fun navigateToAddNote() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_retriveFromRealTime_to_realTimeDatabase)
        }
    }

    private fun getData() {
        val dataList = mutableListOf<RealTimeData>()
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (noteSnapshot in snapshot.children) {
                    val title = noteSnapshot.child("title").getValue(String::class.java)
                    val description = noteSnapshot.child("description").getValue(String::class.java)
                    val email = noteSnapshot.child("email").getValue(String::class.java)
                    val dateTime = noteSnapshot.child("dateTime").getValue(String::class.java)

                    val noteId = noteSnapshot.key
                    if (email == auth.currentUser?.email.toString()) {
                        if (title != null && description != null && dateTime != null) {
                            val data = RealTimeData(
                                noteId.toString(),
                                title,
                                description,
                                email,
                                dateTime
                            )
                            dataList.add(data)
                        }
                    }
                }
                adapter.updateNote(dataList)
                Log.d("data", dataList.toString())
                if (dataList.isEmpty()) {
                    binding.animationView.visibility = View.VISIBLE
                    binding.textView5.visibility = View.VISIBLE
                } else {
                    binding.animationView.visibility = View.GONE
                    binding.textView5.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read data from Firebase.", error.toException())
            }
        })
    }

    override fun onDeleteClick(id: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record deleted successfully")
            } else {
                Log.e(TAG, "Failed to delete record", task.exception)
            }
        }
    }

    override fun onUpdateClick(data: RealTimeData) {
        val dialogBinding = UpdateNoteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
            .setTitle("Update Note")
            .setPositiveButton("Update") { dialog, _ ->
                val newtitle = dialogBinding.updateTitle.text.toString()
                val newdescription = dialogBinding.updateDescription.text.toString()
                updateNote(data.noteId, newtitle, newdescription, data.email, getCurrentDateTime())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitle.setText(data.title)
        dialogBinding.updateDescription.setText(data.description)
        dialog.show()
    }

    private fun updateNote(
        id: String,
        newtitle: String,
        newdescription: String,
        email: String,
        date: String
    ) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(id)
        val updateNote = RealTimeData(id, newtitle, newdescription, email, date)
        databaseReference.setValue(updateNote).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record Updated successfully")
            } else {
                Log.e(TAG, "Failed to Updated record", task.exception)
            }
        }
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}
