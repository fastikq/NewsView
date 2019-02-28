package dudukov.andrii.newsview.controller;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;

import dudukov.andrii.newsview.R;
import dudukov.andrii.newsview.controller.Controller;

public class ArticleActivity extends AppCompatActivity {

    ImageView imageNews;
    private TextView textView_author;
    private TextView textView_source;
    private TextView textView_publishedAt;
    private TextView textView_title;
    private TextView textView_content;
    private TextView textView_link;
    private Controller controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("News");

        imageNews = findViewById(R.id.image_selected_news);
        textView_author = findViewById(R.id.textView_author);
        textView_source = findViewById(R.id.textView_source);
        textView_publishedAt = findViewById(R.id.textView_publishedAt);
        textView_title = findViewById(R.id.textView_title);
        textView_content = findViewById(R.id.textView_content);
        textView_link = findViewById(R.id.textView_link);

        controller = Controller.getInstance();
        fillDataOfArticle();
    }

    private void fillDataOfArticle() {
        int articleId = getIntent().getIntExtra("articleId", 0);
        TextView[] textViewsForFillInfo = {textView_author, textView_source, textView_publishedAt, textView_title, textView_content, textView_link};
        controller.fetchDataOfSelectedArticle(textViewsForFillInfo, imageNews, this, articleId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return false;
    }
}
