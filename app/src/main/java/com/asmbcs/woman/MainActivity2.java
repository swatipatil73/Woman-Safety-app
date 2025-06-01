package com.asmbcs.woman;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asmbcs.woman.adapter.BlogAdapter;
import com.asmbcs.woman.modelclass.Blog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private RecyclerView recyclerView;
    ImageView menuButton;
    private BlogAdapter blogAdapter;
    private SearchView searchView;
    private List<Blog> blogList;
    private List<Blog> filteredList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recycle_depart);
        searchView = findViewById(R.id.searchView);
        menuButton = findViewById(R.id.menuButton);
        ImageView scannerIcon = findViewById(R.id.scannerIcon);

        scannerIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity2.this, ContactActivity.class);
            startActivity(intent);
        });

        menuButton.setOnClickListener(view -> showPopupMenu(view));
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setItemPrefetchEnabled(false);  // Disable prefetching
        recyclerView.setLayoutManager(layoutManager);

        // Initialize blog list
        blogList = new ArrayList<>();
        blogList.add(new Blog("Self Defense Tips", "https://media.assettype.com/TNIE/import/2018/10/16/original/thumbnail.jpg?w=1200&ar=40:21&auto=format%2Ccompress&ogImage=true&mode=crop&enlarge=true&overlay=false&overlay_position=bottom&overlay_width=100", "Learn how to protect yourself in dangerous situations."));
        blogList.add(new Blog("Emergency Contacts", "https://tse1.mm.bing.net/th?id=OIP.iZwe0Ohw240kK4duM_9OHQHaE7&pid=Api&P=0&h=180", "Know whom to call in emergencies."));
        blogList.add(new Blog("Safe Travel Guide", "https://tse1.mm.bing.net/th?id=OIP.2OzfiI2EaUgSVcBS3ODKMwHaDt&pid=Api&P=0&h=180", "Tips for traveling safely alone."));
        blogList.add(new Blog("Cyber Safety", "https://tse1.mm.bing.net/th?id=OIP.bzxsneHKIJhmzGztL3PMAwHaDg&pid=Api&P=0&h=180", "How to stay safe from online threats."));

        filteredList = new ArrayList<>(blogList);
        blogAdapter = new BlogAdapter(this, filteredList);
        recyclerView.setAdapter(blogAdapter);


        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterBlogs(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterBlogs(newText);
                return false;
            }
        });
        // Search functionality
    }


    private void showPopupMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.std_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.menu_message) {
                Intent intent = new Intent(this, login.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.menu_tip) {
                Intent intent = new Intent(this, tips.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.menu_live_location) {
                Intent intent = new Intent(this, ContactActivity.class);
                startActivity(intent);
                return true;
            } else if (itemId == R.id.menu_contact) {
                Intent intent = new Intent(this, phonecall.class);
                startActivity(intent);
                return true;
            } else {
                return false;
            }
        });


        popupMenu.show();
    }



    private void filterBlogs(String query) {
        List<Blog> filteredList = new ArrayList<>();
        for (Blog blog : blogList) {
            if (blog.getTitle().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(blog);
            }
        }
        blogAdapter.updateList(filteredList); // Update adapter with filtered list
    }

}