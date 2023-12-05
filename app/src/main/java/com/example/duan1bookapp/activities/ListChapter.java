package com.example.duan1bookapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1bookapp.Constants;
import com.example.duan1bookapp.R;
import com.example.duan1bookapp.a_interface.IClickItemChapterLinstener;
import com.example.duan1bookapp.adapters.AdapterChapterTitle;
import com.example.duan1bookapp.models.Category;
import com.example.duan1bookapp.models.Chapter;
import com.example.duan1bookapp.models.Customer;
import com.example.duan1bookapp.models.CustomerDataManager;
import com.example.duan1bookapp.models.Product;
import com.example.duan1bookapp.retrofit.IComicAPI;
import com.example.duan1bookapp.retrofit.OrderApi;
import com.example.duan1bookapp.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListChapter extends AppCompatActivity {
    private RetrofitService retrofitService = new RetrofitService();

    private List<Chapter> chapterList;
    private RecyclerView recycler_chapter;
    private Customer customer =null;
    private CardView cardViewComment,cardViewfavorite;

    private CustomerDataManager customerDataManager;

    private TextView textView0,textView1,textView2,textView3,textView4,textView5,textViewflavorite;
    private ImageView imageView,imageViewflavorite;
    private LinearLayout lnClickFollow;
    private Boolean isFavorite = false;
    private static final int FAVORITE_ALREADY = 1;
    private static final int FAVORITE_NOT_YET = 2;
    private static final int FAVORITE_CHECK_FAILED = -1;
    private EditText time;
    private Product product =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_chapter);
        ImageButton backBtn1 = findViewById(R.id.backBtn);
        backBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Quay lại trang trước
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            return;
        }
        product = (Product) bundle.get("object_product");
        List<Category> categoryList = (List<Category>) bundle.get("object_Category");
        customerDataManager = new CustomerDataManager(this);
        customer = customerDataManager.getUser();
        StringBuilder stringBuilder = new StringBuilder();
        for (Category element : categoryList) {
            stringBuilder.append(element.categoryName).append(","); // Use the appropriate method to get the second value
        }
        String mergedString = stringBuilder.toString().trim(); // Get the final merged string
// Use the mergedString as needed (e.g., display in a TextView, log it, etc.)

        initUi();
        fetchChapter(product.id);

        String url = Constants.URL_API+ "/api/v1/product/image/"+product.productImageName;
        Glide.with(this).load(url).into(imageView);
        ProgressBar progressBar = findViewById(R.id.progressBar_book_details);
        progressBar.setVisibility(View.GONE);

        if(!product.getCategories().isEmpty()){
            Log.d("ListChapter", "onCreate:  null");
        }else{
            Log.d("ListChapter", "onCreate:  "+product.getCategories().get(0).categoryName);

        }

        textView0.setText(product.productName);
        textView1.setText(mergedString);
        textView2.setText(product.productAuthor);
        textView3.setText(String.valueOf(product.price));
        textView4.setText(String.valueOf(product.view));

        recycler_chapter.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recycler_chapter.setLayoutManager(linearLayoutManager);
        checkIsFavorite(customer,product.id);
        cardViewComment.setOnClickListener(v -> onClickGoToComment());
        cardViewfavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite) { // Assuming isFavorite is a boolean variable indicating if the product is already favorited
                    Log.d("123456789","123456789"+isFavorite);
                    addToFavorite(customer,product.id);
                } else {
                    Log.d("123456789","987654321"+isFavorite);
                    removeFromFavorite(customer,product.id); // Implement this method to add the product to favorites
                }
            }
        });

    }

    private void addToFavorite(Customer customer, int id) {
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.addToBagFavoriteItem(customer.getId(),id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body();
                    if (success != null && success) {
                        imageViewflavorite.setImageResource(R.drawable.ic_favorite_white);
                        textViewflavorite.setText("UnFollow");
                        lnClickFollow.setBackgroundColor(Color.RED);
                        isFavorite = true;
                        Toast.makeText(getApplication(), "Added to Favorite!", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {
                    // Handle API call failure
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void removeFromFavorite(Customer customer, int id) {
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.removeFromBagFavoriteItem(customer.getId(),id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body();
                    if (success != null && success) {
                        imageViewflavorite.setImageResource(R.drawable.ic_favorite_border_white);
                        textViewflavorite.setText("Follow");
                        lnClickFollow.setBackgroundColor(getColor(R.color.blue01));
                        isFavorite = false;
                        Toast.makeText(getApplication(), "Deleted to Favorite!", Toast.LENGTH_SHORT).show();
                    } else {

                    }
                } else {
                    // Handle API call failure
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    private void initUi(){
        cardViewComment = findViewById(R.id.carview_comment);
        cardViewfavorite = findViewById(R.id.carview_favorite);
        recycler_chapter = findViewById(R.id.recycler_chapter);
        textView0 = findViewById(R.id.titleTv_lc);
        textView1 = findViewById(R.id.categoryTv_lc);
        textView2 = findViewById(R.id.authorTv_lc);
        textView3 = findViewById(R.id.priceTv_lc);
        textView4 = findViewById(R.id.viewsTv_lc);
        textView5 = findViewById(R.id.ChapterTv_lc);
        imageView = findViewById(R.id.image_view_comic_ls);
        textViewflavorite = findViewById(R.id.TV_favorited);
        imageViewflavorite = findViewById(R.id.favoritedBtn);
        lnClickFollow= findViewById(R.id.lnClickFollow);
    }

    private void fetchChapter(int mangaid) {
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getChapterList(mangaid).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                chapterList = response.body();
                AdapterChapterTitle chapterAdapter = new AdapterChapterTitle(chapterList, new IClickItemChapterLinstener() {
                    @Override
                    public void onClickItemChapter(Chapter chapter) {
                        onClickCheckPay(chapter);
                    }
                });
                recycler_chapter.setAdapter(chapterAdapter);
                String sizeofchapter = String.valueOf(chapterList.size());
                textView5.setText(sizeofchapter);
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });

    }

    private Boolean onClickCheckPay(Chapter chapter){
        Boolean aBoolean= false;
        IComicAPI iComicAPI =  retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.getVerifypayment(customer.getId(),chapter.id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    boolean isPaymentVerified = response.body();
                    if (isPaymentVerified) {
                        onClickGoToLinkList(chapter);
                    } else {
                        showConfirmDialog(chapter, onClickCheckMoney(product.price, customer.getCoin().value));
                    }
                } else {
                    // Handle unsuccessful response
                    // For example, show an error message or log the error
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.d("12345678",t.toString());

            }
        });
        return aBoolean;
    }
    private Boolean onClickCheckMoney(int productprice, int customerpremium){
        Boolean bBoolean= false;
        if(productprice <= customerpremium){
            bBoolean = true;
        }
        return bBoolean;
    }
    private void onClickGoToLinkList(Chapter chapter){
        Intent intent = new Intent(this, ListChapterContent.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object_chapter",chapter);
        intent.putExtras(bundle);
        startActivity(intent);

    }

    private void showConfirmDialog(Chapter chapter, boolean isValueAppropriate) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String message = "Choose an action to perform:\n";
        message += "*Xac Nhan: dong y mua chapter se bi tru tien trong vi\n";
        message += "*Truong hop vi khong du tien : button xac nhan se bi disable";
        builder.setMessage(message);
        builder.setPositiveButton("XacNhan", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Perform Action 1
                // For example, call a method or navigate to another activity
                Log.d("check Dialog", "onClick: action 1btn");
                performAction1(chapter);

            }
        });

        builder.setNegativeButton("BuyCoin", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // Perform Action 2
                // For example, update some data or trigger an event
                Log.d("check Dialog", "onClick: action 2btn");
                dialog.dismiss();
                onBackPressed();

            }
        });

        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss(); // Dismiss the dialog
            }
        });

        AlertDialog dialog = builder.create();

        // Disable the button if the value is not appropriate
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                Button action1Button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                Button action2Button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                if (!isValueAppropriate) {
                    action1Button.setEnabled(false);
//                        action2Button.setEnabled(false);
                }
            }
        });
        dialog.show();
    }
    private void performAction1(Chapter chapter) {
        OrderApi orderApi = retrofitService.getRetrofit().create(OrderApi.class);
        orderApi.subtractPrice(customer.getId(), product.price).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (response.isSuccessful()) {
                    int amountLeft = response.body().intValue();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
                            iComicAPI.saveItemPayToViewInBag(customer.getId(),chapter.id).enqueue(new Callback<Boolean>() {
                                @Override
                                public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                    try {
                                        if(!response.isSuccessful())
                                        {
                                            return;
                                        }
                                        Thread.sleep(1000);
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Boolean> call, Throwable t) {

                                }
                            });
                        }
                    });
                    // Show the value left in a Toast
                    Toast.makeText(getApplication(), "Amount left: " + amountLeft, Toast.LENGTH_SHORT).show();                }
            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Log.e("Error occurred", "Throwable: " + t.toString()); // Log throwable as string
                Log.e("Error occurred", "StackTrace: " + Log.getStackTraceString(t)); // Log stack trace
            }
        });
    }
    private void onClickGoToComment() {
        Intent intent = new Intent(this, Comment.class);
        startActivity(intent);
    }
    private void  checkIsFavorite(Customer customer,int id) {

        IComicAPI iComicAPI = retrofitService.getRetrofit().create(IComicAPI.class);
        iComicAPI.checkFavoriteItem(customer.id,id).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    Boolean success = response.body();
                    if (success != null && success) {
                        Log.d("ListChapter", "is Flavorite1?"+success+String.valueOf(customer.id)+String.valueOf(id));
                        // Item is already a favorite
                        isFavorite = true;
                        handleFavoriteStatus(FAVORITE_ALREADY);
                    } else {
                        Log.d("ListChapter", "is Flavorite2?"+success+String.valueOf(customer.id)+String.valueOf(id));
                        // Item is not yet a favorite
                        handleFavoriteStatus(FAVORITE_NOT_YET);
//                        isFavorite = false;
                    }
                } else {
                    // Handle API call failure
                    handleFavoriteStatus(FAVORITE_CHECK_FAILED);
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                // Handle network failure
                handleFavoriteStatus(FAVORITE_CHECK_FAILED);
//                isFavorite = false;
            }
        });

    }

    private void handleFavoriteStatus(int status) {
        // Return the status to the calling function
        returnStatusToCaller(status);
    }

    private void returnStatusToCaller(int status) {
        Log.d("list-chapter", String.valueOf(status));
        // Perform actions based on the status received
        // For example, update UI or execute other logic based on the status value
        // You can also use this method to return the status to the calling function
        if(status==1){
            imageViewflavorite.setImageResource(R.drawable.ic_favorite_white);
            textViewflavorite.setText("UnFollow");
            lnClickFollow.setBackgroundColor(Color.RED);
        }else {
            imageViewflavorite.setImageResource(R.drawable.ic_favorite_border_white);
            textViewflavorite.setText("Follow");
            lnClickFollow.setBackgroundColor(getColor(R.color.blue01));

        }
    }


}