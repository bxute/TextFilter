package bxute.searchtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchManager.SearchListener {

    ListView suggestionsListView;
    ProgressBar suggestionProgressBar;
    TextView messagePanel;
    ListAdapter adapter;
    SearchManager searchManager;
    TextView countTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        suggestionsListView = findViewById(R.id.suggestionsListView);
        suggestionProgressBar = findViewById(R.id.suggestionsProgressBar);
        messagePanel = findViewById(R.id.messagePanel);
        countTv = findViewById(R.id.countTv);
        adapter = new ListAdapter(this);
        suggestionsListView.setAdapter(adapter);

        prepareSearch();

    }

    private void prepareSearch(){

        String s = getResources().getString(R.string.words);
        String[] strings = s.split(" ");
        ArrayList<String> words = new ArrayList<String>(Arrays.asList(strings));
        SearchManager.SearchBuilder builder = SearchManager.Builder();

        searchManager = builder.feedWords(words)
                .setSearchCallback(this)
                .build();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchSuggestions(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    fetchSuggestions(newText);
                }
                return true;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                suggestionsListView.setVisibility(View.GONE);
                messagePanel.setVisibility(View.GONE);
                countTv.setVisibility(View.GONE);
                suggestionProgressBar.setVisibility(View.GONE);

                return false;
            }
        });

        return true;
    }

    private void fetchSuggestions(String query) {
        searchManager.requestSuggestionsFor(query);
    }

    @Override
    public void onPreparing() {

        suggestionsListView.setVisibility(View.GONE);
        messagePanel.setVisibility(View.VISIBLE);
        suggestionProgressBar.setVisibility(View.VISIBLE);

        messagePanel.setText("Preparing...");

    }

    @Override
    public void onPrepared() {

        suggestionsListView.setVisibility(View.GONE);
        messagePanel.setVisibility(View.VISIBLE);
        suggestionProgressBar.setVisibility(View.GONE);
        messagePanel.setText("Ready For Search!!");
        countTv.setVisibility(View.GONE);

    }

    @Override
    public void onSearching() {

        suggestionsListView.setVisibility(View.GONE);
        messagePanel.setVisibility(View.VISIBLE);
        suggestionProgressBar.setVisibility(View.VISIBLE);
        countTv.setVisibility(View.VISIBLE);
        countTv.setVisibility(View.GONE);
        messagePanel.setText("Searching...");
    }

    @Override
    public void onSearched(ArrayList<String> suggestions) {

        suggestionsListView.setVisibility(View.VISIBLE);
        messagePanel.setVisibility(View.GONE);
        suggestionProgressBar.setVisibility(View.GONE);
        countTv.setVisibility(View.VISIBLE);
        countTv.setText(suggestions.size()+" Result Found!");
        adapter.setWords(suggestions);

    }
}
