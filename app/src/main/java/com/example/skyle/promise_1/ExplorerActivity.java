package com.example.skyle.promise_1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Skyle on 2015/10/24.
 */

public class ExplorerActivity extends AppCompatActivity {

    public class Item {
        public String name;
        public boolean is_dir;
    }

    private ArrayList<Item> Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView listViewExplorer = (ListView) findViewById(R.id.listViewExplorer);

        LoadData("");
        listViewExplorer.setAdapter(new ListViewAdapter());
    }

    public void LoadData(String path) {
        //TODO: Load Data
        Item it[] = new Item[10];
        for (int i = 4; i >= 0; i--) {
            it[i] = new Item();
            it[i].name = "TestFolder" + i;
            it[i].is_dir = true;
            it[i + 5] = new Item();
            it[i + 5].name = "TestFile" + i;
            it[i + 5].is_dir = false;
        }

        Collections.sort(Items, new Comparator<Item>() {
            @Override
            public int compare(Item lhs, Item rhs) {
                if(lhs.is_dir==rhs.is_dir)
                    return lhs.name.compareTo(rhs.name);
                else
                    return lhs.is_dir ? 1 : 0;
            }
        });

        for(int i= 0; i < 10; i++)
            Items.add(it[i]);
    }

    public class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LinearLayout linearLayout =
                    (LinearLayout) ExplorerActivity.this.getLayoutInflater().inflate(R.layout.browser_item, null);
            ImageView imageViewItemType = (ImageView) linearLayout.findViewById(R.id.imageViewItemType);
            TextView textViewItemName = (TextView) linearLayout.findViewById(R.id.textViewItemName);
            if(Items.get(position).is_dir){ //TODO: set item image

            }else{

            }
            /*switch (Items.get(position).type) {
                case Item.TYPE_FOLDER:
                    //imageViewItemType.setImageResource(R.drawable.XX);
                case Item.TYPE_FILE:
                    //imageViewItemType.setImageResource();
            }*/
            textViewItemName.setText(Items.get(position).name);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(ExplorerActivity.this, "hi " + position, Toast.LENGTH_SHORT).show();
                }
            });
            return linearLayout;
        }
    }
}
