package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.suguiming.superclass.R;

import java.util.ArrayList;
import java.util.List;

public class AlbumPhotoActivity extends AppCompatActivity {

    private GridView gridView;
    private List<AlbumPhotoModel> modelList = new ArrayList<>();
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_album);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("选择照片");

        gridView = (GridView)findViewById(R.id.photo_grid);
        adapter = new GridAdapter(this,R.layout.album_photo_item,modelList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity,AlbumPhotoActivity.class);
        activity.startActivity(intent);
    }

    public void backImageTap(View v){
          finish();
    }


    class GridAdapter extends ArrayAdapter<AlbumPhotoModel> {
        private int layoutId;
        public GridAdapter(Context context, int resourceId, List<AlbumPhotoModel> objects){
            super(context,resourceId,objects);
            layoutId = resourceId;
        }

        @Override
        public int getCount() {
            return 30;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
//            AlbumDirModel model = getItem(position);
            ViewHolder viewHolder;
            View layoutView;

            if (convertView == null){
                layoutView = LayoutInflater.from(getContext()).inflate(layoutId,null);
                viewHolder = new ViewHolder();

                viewHolder.image = (ImageView)layoutView.findViewById(R.id.grid_photo);

                layoutView.setTag(viewHolder);
            }else {
                layoutView = convertView;
                viewHolder = (ViewHolder)layoutView.getTag();
            }
            //--------在下面赋值 ----------------

            return layoutView;
        }

        class ViewHolder{
            ImageView image;
        }
    }

}
