package com.example.instagram.itemfragments;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.instagram.R;
import com.example.instagram.modal.Video;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddFragment extends Fragment {

    private MaterialButton upload_btn, record_btn, final_upload_btn;
    private ActivityResultLauncher<String> mediaLauncher;
    private MaterialTextView materialTextView;
    private ImageView imageView;
    private Uri videoUri;
    private CircularProgressIndicator circularProgressIndicator;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference coleref = firebaseFirestore.collection("Videos");

    private FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private StorageReference storageReference = firebaseStorage.getReference();


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add, container, false);

        circularProgressIndicator = view.findViewById(R.id.circular_loading);

        upload_btn = view.findViewById(R.id.upload_btn);
        record_btn = view.findViewById(R.id.record_btn);
        final_upload_btn = view.findViewById(R.id.final_upload_btn);
        materialTextView = view.findViewById(R.id.item_selected);
        imageView = view.findViewById(R.id.image_thumbNail);




        mediaLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri o) {
                        if (o != null){
                            videoUri = o;
                            showThumnail();
                        }
                    }
                });

        upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaLauncher.launch("video/*");
            }
        });


        final_upload_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataInFirebase();
                circularProgressIndicator.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void saveDataInFirebase() {
        if (videoUri != null){
            StorageReference reference = storageReference.child("Videos").child("video.mp4_" + Timestamp.now().getSeconds());
            reference.putFile(videoUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String strUri = uri.toString();

                            Video video = new Video();
                            video.setUrl(strUri);

                            coleref.add(video).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getContext(), "Video uploaded successfully", Toast.LENGTH_LONG).show();
                                    circularProgressIndicator.setVisibility(View.GONE);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Unable to upload", Toast.LENGTH_SHORT).show();
                                    circularProgressIndicator.setVisibility(View.GONE);
                                }
                            });
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "Unable to get download url", Toast.LENGTH_SHORT).show();
                            circularProgressIndicator.setVisibility(View.GONE);
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Unable to store data", Toast.LENGTH_SHORT).show();
                    circularProgressIndicator.setVisibility(View.GONE);
                }
            });
        }
    }

    private void showThumnail() {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        retriever.setDataSource(getContext(), videoUri);
        Bitmap bitmap = retriever.getFrameAtTime(2000000);
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
        materialTextView.setVisibility(View.VISIBLE);
        final_upload_btn.setVisibility(View.VISIBLE);
    }
}