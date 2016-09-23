package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.Image;
import android.support.v4.content.ContextCompat;
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

    private static AlbumResultListener resultListener;
    private static Activity dirActivity;

    public GridView gridView;
    private List<AlbumPhotoModel> modelList = new ArrayList<>();
    private GridAdapter adapter;

    private TextView previewTv;
    private TextView sureTv;
    private TextView numTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setMainView(R.layout.activity_album);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("选择照片");

        previewTv = (TextView) findViewById(R.id.pre_tv);
        sureTv = (TextView) findViewById(R.id.sure_tv);
        numTv = (TextView) findViewById(R.id.num_tv);

        initData();

        gridView = (GridView) findViewById(R.id.photo_grid);
        adapter = new GridAdapter(this, R.layout.album_photo_item, modelList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        checkSelectedNum();
    }

    public static void startActivity(Activity activity, List<String> list, AlbumResultListener listener) {
        resultListener = listener;
        dirActivity = activity;

        Intent intent = new Intent(activity, AlbumPhotoActivity.class);
        intent.putStringArrayListExtra("imageList", (ArrayList<String>) list);
        activity.startActivity(intent);
    }

    private void initData() {
        List<String> imagePathList = getIntent().getStringArrayListExtra("imageList");
        if (imagePathList != null && imagePathList.size() > 0) {
            for (int i = 0; i < imagePathList.size(); i++) {
                AlbumPhotoModel model = new AlbumPhotoModel();
                model.imagePath = imagePathList.get(i);
                modelList.add(model);
            }
        }
    }

    public void backImageTap(View v) {
        finish();
    }

    public void previewTap(View v) {


    }

    public void sureTap(View v) {
        List<String> resultList = new ArrayList<>();
        for (AlbumPhotoModel model : modelList) {
            if (model.isSelected) {
                resultList.add(model.getImagePath());
            }
        }
        resultListener.complete(v, resultList);

        finish();
        dirActivity.finish();
    }

    private void checkSelectedNum() {
        int num = 0;
        for (AlbumPhotoModel model : modelList) {
            if (model.isSelected) {
                num++;
            }
        }

        if (num > 0) {
            previewTv.setTextColor(ContextCompat.getColor(this, R.color.black));
            previewTv.setClickable(true);

            numTv.setVisibility(View.VISIBLE);
            numTv.setText(num + "");

            sureTv.setTextColor(ContextCompat.getColor(this, R.color.black));
            sureTv.setClickable(true);
        } else {
            previewTv.setTextColor(ContextCompat.getColor(this, R.color.gray));
            previewTv.setClickable(false);

            numTv.setVisibility(View.GONE);

            sureTv.setTextColor(ContextCompat.getColor(this, R.color.gray));
            sureTv.setClickable(false);
        }
    }


    //---------adapter------------
    class GridAdapter extends ArrayAdapter<AlbumPhotoModel> {
        private Point mPoint = new Point(0, 0);
        private int layoutId;

        public GridAdapter(Context context, int resourceId, List<AlbumPhotoModel> objects) {
            super(context, resourceId, objects);
            layoutId = resourceId;
        }

        @Override
        public int getCount() {
            return modelList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final AlbumPhotoModel model = getItem(position);

            ViewHolder viewHolder;
            View layoutView;

            if (convertView == null) {
                layoutView = LayoutInflater.from(getContext()).inflate(layoutId, null);
                viewHolder = new ViewHolder();

                viewHolder.checkImage = (ImageView) layoutView.findViewById(R.id.check_image);
                viewHolder.image = (CacheImageView) layoutView.findViewById(R.id.grid_photo);
                viewHolder.image.setOnMeasureListener(new CacheImageView.OnMeasureListener() {
                    @Override
                    public void onMeasureSize(int width, int height) {
                        mPoint.set(width, height);
                    }
                });

                layoutView.setTag(viewHolder);
            } else {
                layoutView = convertView;
                viewHolder = (ViewHolder) layoutView.getTag();
            }
            //--------在下面赋值 ----------------
            viewHolder.image.setTag(model.getImagePath());
            Bitmap bitmap = CacheImageLoader.getInstance().loadCacheImage(model.getImagePath(), mPoint, new CacheImageLoader.CacheImageCallBack() {
                @Override
                public void onImageLoader(Bitmap bitmap, String path) {
                    ImageView mImageView = (ImageView) gridView.findViewWithTag(path);
                    if (bitmap != null && mImageView != null) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }
            });
            if (bitmap != null) {
                viewHolder.image.setImageBitmap(bitmap);
            } else {
                viewHolder.image.setImageResource(R.mipmap.no_photo);
            }

            //-----check image 处理------
            if (model.isSelected) {
                viewHolder.checkImage.setImageResource(R.mipmap.checked);
            } else {
                viewHolder.checkImage.setImageResource(R.mipmap.checked_no);
            }

            viewHolder.checkImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (model.isSelected) {
                        model.isSelected = false;
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.mipmap.checked_no);
                    } else {
                        model.isSelected = true;
                        ImageView imageView = (ImageView) v;
                        imageView.setImageResource(R.mipmap.checked);
                    }

                    checkSelectedNum();
                }
            });

            return layoutView;
        }

        class ViewHolder {
            CacheImageView image;
            ImageView checkImage;
        }
    }
}
