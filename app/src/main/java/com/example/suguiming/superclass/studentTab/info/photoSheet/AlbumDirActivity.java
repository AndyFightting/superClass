package com.example.suguiming.superclass.studentTab.info.photoSheet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.suguiming.superclass.utils.ImageUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AlbumDirActivity extends AppCompatActivity {

    private ListView listView;
    private List<AlbumDirModel> modelList = new ArrayList<>();
    private AlbumDirAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        setContentView(R.layout.activity_album_dir);
        TextView titleTv = (TextView) findViewById(R.id.title_tv);
        titleTv.setText("相册");

        initData();

        listView = (ListView)findViewById(R.id.album_dir_list);
        adapter = new AlbumDirAdapter(this,R.layout.album_dir_item,modelList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlbumPhotoActivity.startActivity(AlbumDirActivity.this);
            }
        });
    }

    private void initData(){
      List<HashMap<String,String>> list =   ImageUtil.getLocalImages(this);
        for (int i=0;i<list.size();i++){
            HashMap<String,String> map = list.get(i);

            AlbumDirModel model = new AlbumDirModel();
            model.photoPath = map.get("thumbnail_path");

            modelList.add(model);
        }
    }

    public void backImageTap(View v){
        finish();
    }

    class AlbumDirAdapter extends ArrayAdapter<AlbumDirModel> {
        private int layoutId;
        public AlbumDirAdapter(Context context, int resourceId, List<AlbumDirModel> objects){
            super(context,resourceId,objects);
            layoutId = resourceId;
        }

        @Override
        public int getCount() {
            return modelList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            AlbumDirModel model = getItem(position);
            ViewHolder viewHolder;
            View layoutView;

            if (convertView == null){
                layoutView = LayoutInflater.from(getContext()).inflate(layoutId,null);
                viewHolder = new ViewHolder();

                viewHolder.image = (ImageView)layoutView.findViewById(R.id.dir_image);
                viewHolder.name = (TextView)layoutView.findViewById(R.id.dir_name);

                layoutView.setTag(viewHolder);
            }else {
                layoutView = convertView;
                viewHolder = (ViewHolder)layoutView.getTag();
            }
            //--------在下面赋值 ----------------
            viewHolder.name.setText( model.getPhotoPath());

            Bitmap bitmap=BitmapFactory.decodeFile(model.getPhotoPath());
            viewHolder.image.setImageBitmap(bitmap);

            return layoutView;
        }

        class ViewHolder{
            ImageView image;
            TextView name;
        }
    }
}
