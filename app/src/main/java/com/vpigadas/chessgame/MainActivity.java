package com.vpigadas.chessgame;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vpigadas.chessgame.utils.Solution;
import com.vpigadas.chessgame.views.adapter.CellAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.chess_board)
    RecyclerView recyclerView;
    @BindView(R.id.game_title)
    TextView title;
    @BindView(R.id.show_results)
    Button showResults;
    @BindView(R.id.restart_game)
    Button restartGame;

    private ArrayList<Solution> arraySolutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initLayout();
    }

    private void initLayout() {
        setGameTitle(getString(R.string.title_start_game));
        recyclerView.setAdapter(new CellAdapter(this));

        restartGame.setVisibility(View.GONE);
        showResults.setVisibility(View.GONE);
    }

    public void setGameTitle(String _title) {
        if (getString(R.string.title_not_found).equals(_title)) {
            restartGame.setVisibility(View.VISIBLE);
        }
        title.setText(_title);
    }

    public void setResults(ArrayList<Solution> solutions) {
        this.arraySolutions = solutions;

        restartGame.setVisibility(View.VISIBLE);
        showResults.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_refresh: {
                initLayout();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.restart_game)
    public void onClick(View view) {
        initLayout();
    }

    @OnClick(R.id.show_results)
    public void onCLick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Compare with:");

        final String[] arrayList = new String[arraySolutions.size()];

        for (int i = 0; i < arraySolutions.size(); i++) {
            arrayList[i] = arraySolutions.get(i).toString();
        }


        builder.setItems(arrayList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Log.d(MainActivity.class.getSimpleName(), "The wrong button was tapped: " + arrayList[whichButton]);
                RecyclerView.Adapter adapter = recyclerView.getAdapter();
                if (adapter instanceof CellAdapter) {
                    ((CellAdapter) adapter).startDrawingSolution(arraySolutions.get(whichButton));
                }

            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.create().show();
    }


}
