package dudukov.andrii.newsview.controller.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import dudukov.andrii.newsview.R;
import dudukov.andrii.newsview.model.ArticleModel;
import dudukov.andrii.newsview.controller.ArticleActivity;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticlesViewHolder> {

    private List<ArticleModel> articles;
    private Context context;

    public ArticlesAdapter(List<ArticleModel> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    public static class ArticlesViewHolder extends RecyclerView.ViewHolder {

        public ImageView imgNews;
        public TextView txtTitle;
        public TextView txtDescription;

        public ArticlesViewHolder(View itemView) {
            super(itemView);
            imgNews = itemView.findViewById(R.id.img_news);
            txtTitle = itemView.findViewById(R.id.textView_title);
            txtDescription = itemView.findViewById(R.id.textView_description);
        }
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        ArticlesViewHolder articlesViewHolder = new ArticlesViewHolder(view);
        return articlesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, final int position) {
        ArticleModel currentItem = articles.get(position);

        Picasso.with(context)
                .load(currentItem.getUrlToImage())
                .placeholder(R.mipmap.image_empty)
                .error(R.mipmap.image_empty)
                .into(holder.imgNews);

        holder.txtTitle.setText(currentItem.getTitle());

        if (currentItem.getDescription().length() != 0) {
            holder.txtDescription.setText(currentItem.getDescription());
        } else {
            holder.txtDescription.setText(R.string.description_absent);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticleActivity.class);
                intent.putExtra("articleId", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}
