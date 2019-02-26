package dudukov.andrii.newsview.controller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import dudukov.andrii.newsview.R;
import dudukov.andrii.newsview.api.ApplicationAPI;
import dudukov.andrii.newsview.api.responses.ArticlesResponse;
import dudukov.andrii.newsview.api.retrofit.RetrofitClient;
import dudukov.andrii.newsview.model.ArticleModel;
import dudukov.andrii.newsview.view.recyclerview.adapter.ArticlesAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Controller {

    private static Controller ourInstance = new Controller();
    private CompositeDisposable compositeDisposable;
    private ApplicationAPI applicationAPI;
    private Retrofit retrofit;
    private List<ArticleModel> articles = new ArrayList<>();

    private Controller() {
        retrofit = RetrofitClient.getInstance();
        applicationAPI = retrofit.create(ApplicationAPI.class);
        compositeDisposable = new CompositeDisposable();
    }

    public static Controller getInstance() {
        if (ourInstance == null) {

            ourInstance = new Controller();
            return ourInstance;
        }
        return ourInstance;
    }

    public void fetchDataOfArticles(final RecyclerView recyclerView, final Context context, final ProgressBar progressBar) {

            compositeDisposable.add(applicationAPI.getArticles("us", "business", "25dfc0cf1e8248d796c99e8207552109")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArticlesResponse>() {
                        @Override
                        public void accept(ArticlesResponse containerModels) {
                            articles = containerModels.getArticles();
                            ArticlesAdapter adapter = new ArticlesAdapter(articles, context);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    }));
        }


    public void fetchDataOfSelectedArticle(TextView[] textViewsForFillInfo, ImageView imageNews, Context context,  int articleId) {
        ArticleModel selectedArticle = articles.get(articleId);

        if (selectedArticle.getAuthor() != null) {
            textViewsForFillInfo[0].setText(selectedArticle.getAuthor());
        } else {
            textViewsForFillInfo[0].setText(R.string.author_absent);
        }
        if (selectedArticle.getSource().getName() != null) {
            textViewsForFillInfo[1].setText(selectedArticle.getSource().getName());
        } else {
            textViewsForFillInfo[1].setText(R.string.source_absent);
        }
        if (selectedArticle.getPublishedAt() != null) {
            textViewsForFillInfo[2].setText(replaceString(selectedArticle.getPublishedAt()));
        } else {
            textViewsForFillInfo[2].setText(R.string.date_absent);
        }
        if (selectedArticle.getContent() != null) {
            textViewsForFillInfo[4].setText(selectedArticle.getContent());
        } else {
            textViewsForFillInfo[4].setText(R.string.content_absent);
        }
        textViewsForFillInfo[3].setText(selectedArticle.getTitle());
        textViewsForFillInfo[5].setText(selectedArticle.getUrl());

        Picasso.with(context)
                .load(selectedArticle.getUrlToImage())
                .placeholder(R.mipmap.image_empty)
                .error(R.mipmap.image_empty)
                .into(imageNews);
    }

    public Boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    private String replaceString(String oldString){
        String newString = oldString.replace('T', ' ');
        newString = newString.replace('Z', ' ');
        return newString;
    }
}
