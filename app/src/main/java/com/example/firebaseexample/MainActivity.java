package com.example.firebaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class MainActivity extends AppCompatActivity {


    private Button uploadButton;
    private RecyclerView recyclerView;

    private GridView gridView;
    private ViewAdapter imageAdapter;
    private List<String> imageURLs;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("images");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uploadButton = findViewById(R.id.uploadBtn);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        imageURLs = new ArrayList<>();
        // Thêm các URL hình ảnh vào imageURLs ở đây

        imageAdapter = new ViewAdapter(imageURLs);
        recyclerView.setAdapter(imageAdapter);


        // Load danh sách hình ảnh từ Firebase Storage và hiển thị lên RecyclerView
//        loadImagesFromFirebase();

        loadInitialImagesFromFirebase();

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển sang activity_upload_image khi nhấn nút uploadButton
                Intent intent = new Intent(MainActivity.this, Upload_Image_Activity.class);
                startActivity(intent);
            }
        });

    }

//    private void loadImagesFromFirebase() {
//        // Lấy danh sách tất cả các file trong thư mục "images" trên Firebase Storage
//        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
//            @Override
//            public void onSuccess(ListResult listResult) {
//                for (StorageReference item : listResult.getItems()) {
//                    // Lấy URL của hình ảnh và thêm vào danh sách imageURLs
//                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            imageURLs.add(uri.toString());
//                            imageAdapter.notifyDataSetChanged(); // Cập nhật RecyclerView khi có dữ liệu mới
//                        }
//                    });
//                }
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                // Xử lý khi có lỗi xảy ra trong quá trình lấy dữ liệu từ Firebase Storage
//                Toast.makeText(MainActivity.this, "Failed to load images from Firebase Storage.", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }

    private void loadInitialImagesFromFirebase() {
        storageRef.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()) {

                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imageURLs.add(uri.toString());
                            imageAdapter.notifyItemInserted(imageURLs.size() - 1);
                        }
                    });

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Failed to load images from Firebase Storage.", Toast.LENGTH_SHORT).show();
            }
        });
    }

}