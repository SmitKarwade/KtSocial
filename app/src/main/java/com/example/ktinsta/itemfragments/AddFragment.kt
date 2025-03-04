package com.example.ktinsta.itemfragments

import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.ktinsta.modal.Video
import com.example.instagram.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

class AddFragment : Fragment() {
    private lateinit var uploadBtn: MaterialButton
    private lateinit var recordBtn: MaterialButton
    private lateinit var finalUploadBtn: MaterialButton
    private lateinit var mediaLauncher: ActivityResultLauncher<String>
    private lateinit var materialTextView: MaterialTextView
    private lateinit var imageView: ImageView
    private lateinit var circularProgressIndicator: CircularProgressIndicator
    private var videoUri: Uri? = null

    private val firebaseFirestore = FirebaseFirestore.getInstance()
    private val coleref = firebaseFirestore.collection("Videos")

    private val firebaseStorage = FirebaseStorage.getInstance()
    private val storageReference = firebaseStorage.reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        circularProgressIndicator = view.findViewById(R.id.circular_loading)
        uploadBtn = view.findViewById(R.id.upload_btn)
        recordBtn = view.findViewById(R.id.record_btn)
        finalUploadBtn = view.findViewById(R.id.final_upload_btn)
        materialTextView = view.findViewById(R.id.item_selected)
        imageView = view.findViewById(R.id.image_thumbNail)

        mediaLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                videoUri = uri
                showThumbnail()
            }
        }

        uploadBtn.setOnClickListener { mediaLauncher.launch("video/*") }

        finalUploadBtn.setOnClickListener {
            saveDataInFirebase()
            circularProgressIndicator.visibility = View.VISIBLE
        }

        return view
    }

    private fun saveDataInFirebase() {
        videoUri?.let { uri ->
            val reference = storageReference.child("Videos").child("video_${Timestamp.now().seconds}.mp4")
            reference.putFile(uri).addOnSuccessListener {
                reference.downloadUrl.addOnSuccessListener { downloadUri ->
                    val video = Video(downloadUri.toString())
                    coleref.add(video).addOnSuccessListener {
                        Toast.makeText(context, "Video uploaded successfully", Toast.LENGTH_LONG).show()
                        circularProgressIndicator.visibility = View.GONE
                    }.addOnFailureListener {
                        showToast("Unable to upload")
                    }
                }.addOnFailureListener {
                    showToast("Unable to get download URL")
                }
            }.addOnFailureListener {
                showToast("Unable to store data")
            }
        } ?: showToast("No video selected")
    }

    private fun showThumbnail() {
        val retriever = MediaMetadataRetriever()
        try {
            retriever.setDataSource(context, videoUri)
            val bitmap = retriever.getFrameAtTime(2000000)
            imageView.setImageBitmap(bitmap)
            imageView.visibility = View.VISIBLE
            materialTextView.visibility = View.VISIBLE
            finalUploadBtn.visibility = View.VISIBLE
        } catch (e: Exception) {
            showToast("Unable to generate thumbnail")
        } finally {
            retriever.release()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        circularProgressIndicator.visibility = View.GONE
    }
}
