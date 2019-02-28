package dudukov.andrii.newsview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import dudukov.andrii.newsview.R;
import dudukov.andrii.newsview.controller.Controller;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewArticles;
    private RecyclerView.LayoutManager layoutManager;
    private Controller controller;
    private TextView textViewConnection;
    private Button btnRetry;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewConnection = findViewById(R.id.textView_connection);
        progressBar = findViewById(R.id.progress_bar);
        btnRetry = findViewById(R.id.btn_retry);
        recyclerViewArticles = findViewById(R.id.recyclerView);
        btnRetry.setOnClickListener(this);
        controller = Controller.getInstance();

        showNews();
    }

    private void showNews(){
        progressBar.setVisibility(View.VISIBLE);
        if(controller.isNetworkAvailable(this)) {
            layoutManager = new LinearLayoutManager(this);
            controller.fetchDataOfArticles(recyclerViewArticles, this, progressBar);
            recyclerViewArticles.setLayoutManager(layoutManager);
            recyclerViewArticles.setHasFixedSize(true);
            textViewConnection.setVisibility(View.INVISIBLE);
            btnRetry.setVisibility(View.INVISIBLE);
        }
        else {
            textViewConnection.setVisibility(View.VISIBLE);
            btnRetry.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_retry:
                showNews();
        }
    }
}
