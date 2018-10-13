package com.example.annikadiekmann.geoguessswipe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<GeoObject> mGeoObjects = new ArrayList<>();

        for (int i = 0; i < GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.length; i++) {
        mGeoObjects.add(new GeoObject(GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES[i], GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IDS[i],
                            GeoObject.PRE_DEFINED_GEO_OBJECT_IMAGE_IN_EUROPE[i]));}


        RecyclerView mGeoRecyclerView = findViewById(R.id.recyclerView);

        mGeoRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));

        final GeoObjectAdapter mAdapter = new GeoObjectAdapter(this, mGeoObjects);
        mGeoRecyclerView.setAdapter(mAdapter);


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =

            new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder
                        target) {
                    return false;
                }

                //Called when a user swipes left or right on a ViewHolder
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {

                    //Get the index corresponding to the selected position
                    GeoObjectViewHolder geoObjectViewHolder = (GeoObjectViewHolder) viewHolder;
                    int position = (viewHolder.getAdapterPosition());
                    mGeoObjects.remove(position);
                    mAdapter.notifyItemRemoved(position);


                    if (swipeDir == ItemTouchHelper.LEFT && geoObjectViewHolder.mGeoImageInEurope || swipeDir == ItemTouchHelper.RIGHT && !geoObjectViewHolder.mGeoImageInEurope) {
               
                        Toast.makeText(MainActivity.this, "Great", Toast.LENGTH_SHORT).show();
                    } else {
                        country = ", it's in " + GeoObject.PRE_DEFINED_GEO_OBJECT_NAMES.toString();
                        Toast.makeText(MainActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                    }

                }

            };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mGeoRecyclerView);



    }
}
