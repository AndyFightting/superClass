package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.suguiming.superclass.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AlbumDirActivity extends AppCompatActivity {

    private static AlbumResultListener resultListener;
    private ImageView backImage;

    private ListView listView;
    private HashMap<String, List<String>> dirMap = new HashMap<>();
    private List<AlbumDirModel> folderList = new ArrayList<>();
    private AlbumDirAdapter adapter;

    private final static int SCAN_OK = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SCAN_OK:
                    initListView();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_album_dir);

        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("相册");

        backImage = (ImageView)findViewById(R.id.back_image);

        getLocalImages();
    }

   public static void startActivity(Activity activity,AlbumResultListener listener){
       resultListener = listener;

       Intent intent = new Intent(activity, AlbumDirActivity.class);
       activity.startActivity(intent);
   }


    private void initListView() {
        Iterator<Map.Entry<String, List<String>>> item = dirMap.entrySet().iterator();

        while (item.hasNext()) {
            Map.Entry<String, List<String>> entry = item.next();
            String key = entry.getKey();
            List<String> value = entry.getValue();

            AlbumDirModel mImageBean = new AlbumDirModel();
            mImageBean.setFolderName(key);
            mImageBean.setImageCount(value.size());
            mImageBean.setFolderImagePath(value.get(0));

            folderList.add(mImageBean);
        }

        listView = (ListView) findViewById(R.id.album_dir_list);
        adapter = new AlbumDirAdapter(this, R.layout.album_dir_item, folderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                List<String> imagePathList = dirMap.get(folderList.get(position).getFolderName());
                AlbumPhotoActivity.startActivity(AlbumDirActivity.this,imagePathList);
            }
        });
    }


    public void getLocalImages() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = AlbumDirActivity.this.getContentResolver();

                Cursor mCursor = mContentResolver.query(
                        mImageUri,
                        null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png", "image/jpg"},
                        MediaStore.Images.Media.DATE_MODIFIED);

                while (mCursor.moveToNext()) {
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    String parentName = new File(path).getParentFile().getName();

                    if (!dirMap.containsKey(parentName)) {
                        List<String> chileList = new ArrayList<>();
                        chileList.add(path);
                        dirMap.put(parentName, chileList);
                    } else {
                        dirMap.get(parentName).add(path);
                    }
                }
                mCursor.close();
                mHandler.sendEmptyMessage(SCAN_OK);
            }
        }).start();
    }


    public void backImageTap(View v) {
        finish();
        if (resultListener!=null){
            resultListener.complete(backImage);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (resultListener!=null){
            resultListener.complete(backImage);
        }
    }

    class AlbumDirAdapter extends ArrayAdapter<AlbumDirModel> {
        private int layoutId;
        private Point mPoint = new Point(0, 0);

        public AlbumDirAdapter(Context context, int resourceId, List<AlbumDirModel> objects) {
            super(context, resourceId, objects);
            layoutId = resourceId;
        }

        @Override
        public int getCount() {
            return folderList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            AlbumDirModel model = getItem(position);
            ViewHolder viewHolder;
            View layoutView;

            if (convertView == null) {
                layoutView = LayoutInflater.from(getContext()).inflate(layoutId, null);
                viewHolder = new ViewHolder();

                viewHolder.name = (TextView) layoutView.findViewById(R.id.dir_name);

                viewHolder.image = (CacheImageView) layoutView.findViewById(R.id.dir_image);
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
            String path = model.getFolderImagePath();
            viewHolder.image.setTag(path);
            viewHolder.name.setText(model.getFolderName() + " (" + model.getImageCount() + ")");

            Bitmap bitmap = CacheImageLoader.getInstance().loadCacheImage(path, mPoint, new CacheImageLoader.CacheImageCallBack() {
                @Override
                public void onImageLoader(Bitmap bitmap, String path) {
                    ImageView mImageView = (ImageView) listView.findViewWithTag(path);
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

        class ViewHolder {
            CacheImageView image;
            TextView name;
        }
    }
}
