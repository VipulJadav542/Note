package com.rk.notes.Fragments

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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rk.notes.Adapter.RealTimeDataFetchAdapter
import com.rk.notes.DataModel.RealTimeData
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
    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRetriveFromRealTimeBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_retriveFromRealTime_to_realTimeDatabase)
        }

        databaseReference = FirebaseDatabase.getInstance().reference
        auth = FirebaseAuth.getInstance()

        recyclerView = binding.recyclerView3
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val dataList = mutableListOf<RealTimeData>()

        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataList.clear()
                for (noteSnapshot in snapshot.children) {
                    val title = noteSnapshot.child("title").getValue(String::class.java)
                    val description = noteSnapshot.child("description").getValue(String::class.java)
                    val email = noteSnapshot.child("email").getValue(String::class.java)
                    val date_time = noteSnapshot.child("date_time").getValue(String::class.java)

                    val noteId = noteSnapshot.key
                    if (email == auth.currentUser?.email.toString()) {
                        if (title != null && description != null && date_time != null) {
                            val data = RealTimeData(
                                noteId.toString(),
                                title,
                                description,
                                email,
                                date_time
                            )
                            dataList.add(data)
                        }
                    }
                }
                val adapter = RealTimeDataFetchAdapter(
                    dataList,
                    this@RetriveFromRealTime
                )
                if(dataList.isEmpty())
                {
                    binding.animationView.visibility=View.VISIBLE
                    binding.textView5.visibility=View.VISIBLE
                }
                recyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "Failed to read data from Firebase.", error.toException())
            }
        })

        return view
    }

    override fun OnDeleteClick(Id: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("Notes").child(Id)
        databaseReference.removeValue().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "Record deleted successfully")
            } else {
                Log.e(TAG, "Failed to delete record", task.exception)
            }
        }
    }

    override fun OnUpdateClick(
        Id: String,
        title: String,
        description: String,
        email: String,
        date: String
    ) {
        val dialogBinding = UpdateNoteBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext()).setView(dialogBinding.root)
            .setTitle("Update Note")
            .setPositiveButton("Update") { dialog, _ ->
                val newtitle = dialogBinding.updateTitle.text.toString()
                val newdescription = dialogBinding.updateDescription.text.toString()
                UpdateNote(Id, newtitle, newdescription, email, getCurrentDateTime())
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialogBinding.updateTitle.setText(title)
        dialogBinding.updateDescription.setText(description)
        dialog.show()
    }

    private fun UpdateNote(
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
