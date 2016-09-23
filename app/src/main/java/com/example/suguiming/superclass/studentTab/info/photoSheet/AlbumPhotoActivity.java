package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
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
import com.example.suguiming.superclass.basic.BaseSwipeActivity;

import java.util.ArrayList;
import java.util.List;

public class AlbumPhotoActivity extends BaseSwipeActivity {

    public GridView gridView;
    private List<String> imagePathList;
    private GridAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setMainView(R.layout.activity_album);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("选择照片");

        imagePathList = getIntent().getStringArrayListExtra("imageList");

        gridView = (GridView)findViewById(R.id.photo_grid);
        adapter = new GridAdapter(this,R.layout.album_photo_item,imagePathList);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public static void startActivity(Activity activity,List<String> list){
        Intent intent = new Intent(activity,AlbumPhotoActivity.class);
        intent.putStringArrayListExtra("imageList",(ArrayList<String>)list);
        activity.startActivity(intent);
    }

    public void backImageTap(View v){
          finish();
    }


    class GridAdapter extends ArrayAdapter<String> {
        private Point mPoint = new Point(0, 0);
        private int layoutId;

        public GridAdapter(Context context, int resourceId, List<String> objects){
            super(context,resourceId,objects);
            layoutId = resourceId;
        }

        @Override
        public int getCount() {
            return imagePathList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            String imagePath = getItem(position);

            ViewHolder viewHolder;
            View layoutView;

            if (convertView == null){
                layoutView = LayoutInflater.from(getContext()).inflate(layoutId,null);
                viewHolder = new ViewHolder();

                viewHolder.image = (CacheImageView)layoutView.findViewById(R.id.grid_photo);
                viewHolder.image.setOnMeasureListener(new CacheImageView.OnMeasureListener() {
                    @Override
                    public void onMeasureSize(int width, int height) {
                        mPoint.set(width, height);
                    }
                });

                layoutView.setTag(viewHolder);
            }else {
                layoutView = convertView;
                viewHolder = (ViewHolder)layoutView.getTag();
            }
            //--------在下面赋值 ----------------
            viewHolder.image.setTag(imagePath);
            Bitmap bitmap = CacheImageLoader.getInstance().loadCacheImage(imagePath, mPoint, new CacheImageLoader.CacheImageCallBack() {
                @Override
                public void onImageLoader(Bitmap bitmap, String path) {
                    ImageView mImageView = (ImageView) gridView.findViewWithTag(path);
                    if(bitmap != null && mImageView != null){
                        mImageView.setImageBitmap(bitmap);
                    }
                }
            });

            if(bitmap != null){
                viewHolder.image.setImageBitmap(bitmap);
            }else{
                viewHolder.image.setImageResource(R.mipmap.no_photo);
            }
            return layoutView;
        }

        class ViewHolder{
            CacheImageView image;
        }
    }

}
