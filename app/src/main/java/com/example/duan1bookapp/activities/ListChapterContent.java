package com.example.duan1bookapp.activities;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.adapters.AdapterChapterContent;
import com.example.duan1bookapp.models.Chapter;
import com.example.duan1bookapp.models.Link;
import com.example.duan1bookapp.retrofit.IComicAPI;
import com.example.duan1bookapp.retrofit.RetrofitService;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChapterContent extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;

    private Menu mMenu;
    private Boolean isExpanded = true;
    RetrofitService retrofitService = new RetrofitService();

    private List<Link> linkList;
    private RecyclerView recycler_link;
    private LinearLayoutManager layoutManager;
    private TextView toolbarSubtitleTv; // Thêm TextView cho toolbarSubtitle

    private boolean isHorizontalLayout = true;
    private Button toggleLayoutButton;

    private SharedPreferences sharedPrefs;
    private Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listchaptercontent);
        toggleLayoutButton = findViewById(R.id.buttonToggleLayout);
        ImageButton backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại trang trước
            }
        });
        toolbarSubtitleTv = findViewById(R.id.toolbarSubtitleTv); // Tìm toolbarSubtitleTv trong layout
        sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE); // Khởi tạo SharedPreferences
        editor = sharedPrefs.edit(); // Khởi tạo Editor để chỉnh sửa SharedPreferences
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Chapter chapter = (Chapter) bundle.get("object_chapter");

        initUi();
        initToolbar();
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recycler_link);

        recycler_link.setHasFixedSize(true);
        recycler_link.setLayoutManager(layoutManager);

        int savedPosition = sharedPrefs.getInt("SavedPosition", 0); // Lấy vị trí đã lưu từ SharedPreferences
        layoutManager.scrollToPositionWithOffset(savedPosition, 0); // Di chuyển RecyclerView đến vị trí đã lưu

        recycler_link.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int currentPosition = layoutManager.findFirstVisibleItemPosition() + 1;
                updateToolbarSubtitle(currentPosition);
            }
        });

        fetchLink(chapter.id);

        // Cập nhật toolbarSubtitleTv với số trang ban đầu
        updateToolbarSubtitle(1);
    }

    public void toggleRecyclerViewLayout(View view) {
        isHorizontalLayout = !isHorizontalLayout;
        updateToggleLayoutButtonText();

        // Thay đổi chế độ hiển thị của RecyclerView dựa trên giá trị isHorizontalLayout
        if (isHorizontalLayout) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        } else {
            layoutManager = new LinearLayoutManager(this);
        }
        recycler_link.setLayoutManager(layoutManager);
    }

    private void updateToggleLayoutButtonText() {
        if (isHorizontalLayout) {
            toggleLayoutButton.setText("Dọc");
        } else {
            toggleLayoutButton.setText("Ngang");
        }
    }

    private void fetchLink(int id) {
        IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getPageList(id).enqueue(new Callback<List<Link>>() {
            @Override
            public void onResponse(Call<List<Link>> call, Response<List<Link>> response) {
                linkList = response.body();
                recycler_link.setAdapter(new AdapterChapterContent(linkList));
                // Cập nhật toolbarSubtitleTv với số trang ban đầu
                updateToolbarSubtitle(1);
            }

            @Override
            public void onFailure(Call<List<Link>> call, Throwable t) {
                // Xử lý lỗi
            }
        });
    }

    private void initUi() {
        // Tương tự, tìm view bằng ID
        recycler_link = findViewById(R.id.recycler_link);
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void updateToolbarSubtitle(int currentPage) {
        if (linkList != null && !linkList.isEmpty()) {
            int totalPage = linkList.size();
            toolbarSubtitleTv.setText(currentPage + "/" + totalPage);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lcc, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (menu != null && (!isExpanded || menu.size() != 1)) {
            mMenu.add("Add2").setIcon(R.drawable.baseline_add_24).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
        return super.onPrepareOptionsMenu(mMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        int currentPosition = layoutManager.findFirstVisibleItemPosition();
        editor.putInt("SavedPosition", currentPosition); // Lưu vị trí vào SharedPreferences
        editor.apply();
        super.onBackPressed();
    }
}
