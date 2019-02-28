package dudukov.andrii.newsview.model.api.responses;

import java.util.List;

import dudukov.andrii.newsview.model.ArticleModel;

public class ArticlesResponse {

    private String status;
    private int totalResults;
    private List<ArticleModel> articles;

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<ArticleModel> getArticles() {
        return articles;
    }
}
