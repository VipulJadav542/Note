import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.rk.notes.DataModel.RealTimeData
import com.rk.notes.R
import com.rk.notes.databinding.ActivityRealTimeDatabaseBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RealTimeDatabase : Fragment() {
    private lateinit var binding: ActivityRealTimeDatabaseBinding
    private lateinit var databaseReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityRealTimeDatabaseBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.show.setOnClickListener {
            findNavController().navigate(R.id.action_realTimeDatabase_to_retriveFromRealTime)
        }

        auth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        binding.save.setOnClickListener {
            if(binding.notetitle.text.isNotEmpty() && binding.notedescription.text.isNotEmpty())
            {
                val email = auth.currentUser?.email
                val title = binding.notetitle.text.toString()
                val description = binding.notedescription.text.toString()

                val key = FirebaseDatabase.getInstance().getReference("Notes").key
                val noteItem = RealTimeData(
                    key.toString(),
                    title,
                    description,
                    email.toString(),
                    getCurrentDateTime()
                )
                databaseReference.child("Notes").push().setValue(noteItem)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_realTimeDatabase_to_retriveFromRealTime)
                            binding.notetitle.text.clear()
                            binding.notedescription.text.clear()
                            Toast.makeText(requireContext(), "Added Successfully", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(requireContext(), "Failed!", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else
            {
                binding.notetitle.error="enter note title"
                binding.notedescription.error="enter note description"
            }

        }
        return view
    }

    private fun getCurrentDateTime(): String {
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
        return dateFormat.format(currentDate)
    }
}
