package com.example.apidemoapp.ui.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apidemoapp.BookNewsInfo;
import com.example.apidemoapp.BookNewsInfoAdapter;
import com.example.apidemoapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    int select;
    public String newUrl= "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=6fca978d0e4942f4b74171d575f4fba8";
    public String booksUrl= "https://www.googleapis.com/books/v1/volumes?q=quilting";

    private PageViewModel pageViewModel;
    PlaceholderFragment.OkHttpHandler okHttpHandler;

    private RecyclerView recyclerView;
    private BookNewsInfoAdapter newsBooksAdapterList;
    //private BookNewsInfoAdapter booksAdapterList;
    private List<BookNewsInfo> bookList = new ArrayList<>();
    BookNewsInfo bookObj, newObj;
    private List<BookNewsInfo> newsList = new ArrayList<>();

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);

        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.rvContacts);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        recyclerView.setItemAnimator(itemAnimator);

        pageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
               // textView.setText(s);
                if(s.equals("1"))
                {
                    select=1;
                   // getDataUsingAPIs();
                    okHttpHandler = new PlaceholderFragment.OkHttpHandler();
                    okHttpHandler.execute(newUrl);
                }
                else if(s.equals("2"))
                {
                    select=2;
                    okHttpHandler = new PlaceholderFragment.OkHttpHandler();
                    okHttpHandler.execute(booksUrl);

                }
            }
        });
        return root;
    }


    class OkHttpHandler extends AsyncTask {

        OkHttpClient client = new OkHttpClient();

        @Override
        protected Object doInBackground(Object[] objects)
        {
            Request.Builder builder = new Request.Builder();
            builder.url(objects[0].toString());
            Request request = builder.build();

            try {
                Response response = client.newCall(request).execute();
                String respond =  response.body().string();
                JsonParser jsonParser = new JsonParser();
                JsonElement jo = jsonParser.parse(respond);

                String name = pageViewModel.getText().toString();

                if(select==1){
                    JsonArray articleArray = jo.getAsJsonObject().get("articles").getAsJsonArray();

                    for(int i=0; i<articleArray.size(); i++) {

                        JsonObject jsonObj = articleArray.getAsJsonArray().get(i).getAsJsonObject();
                        String author =   jsonObj.get("author").getAsString();
                        String title = jsonObj.get("title").getAsString();
                        String describ = jsonObj.get("description").getAsString();
                        String imgUrl =jsonObj.get("urlToImage").getAsString();
                        String date = jsonObj.get("publishedAt").getAsString();

                        newObj = new BookNewsInfo(author,title,describ, imgUrl, date);
                        newsList.add(newObj);

                    }


                }

                if(select==2) {
                    JsonArray articleArray = jo.getAsJsonObject().get("items").getAsJsonArray();

                    for(int i=0; i<articleArray.size(); i++) {

                        JsonObject jsonObj = articleArray.getAsJsonArray().get(i).getAsJsonObject().getAsJsonObject("volumeInfo");

                        String author =   jsonObj.getAsJsonArray("authors").get(0).getAsString();// get("author").getAsString();

                        String title = jsonObj.get("title").getAsString();
                        String describ = jsonObj.get("description").getAsString();
                        String date = jsonObj.get("publishedDate").getAsString();
                        String imgUrl = jsonObj.getAsJsonObject("imageLinks").get("thumbnail").getAsString();  //get("urlToImage").getAsString();

                        newObj = new BookNewsInfo(author,title,describ, imgUrl, date);
                        bookList.add(newObj);

                    }

                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
            //return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if(select==1)
            {
                newsBooksAdapterList = new BookNewsInfoAdapter(newsList);
            }
            else
            {
                newsBooksAdapterList = new BookNewsInfoAdapter(bookList);
            }
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(newsBooksAdapterList);

        }

    }

}