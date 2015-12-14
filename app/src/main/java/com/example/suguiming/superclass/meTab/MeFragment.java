package com.example.suguiming.superclass.meTab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.suguiming.superclass.R;
import com.example.suguiming.superclass.basic.BaseFragment;
import com.example.suguiming.superclass.basic.CircleImageView;
import com.squareup.picasso.Picasso;

public class MeFragment extends BaseFragment {

    private ListView meListView;
    private TextView nameTv;
    private TextView siginTv;
    private CircleImageView headImgView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_me, container, false);
        nameTv = (TextView)contentView.findViewById(R.id.name_tv);
        siginTv = (TextView)contentView.findViewById(R.id.sign_tv);
        headImgView = (CircleImageView)contentView.findViewById(R.id.header_image);

        meListView = (ListView)contentView.findViewById(R.id.me_list);
        meListView.setAdapter(new MeListAdapter());
        meListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0: {
                        Intent infoIntent = new Intent(getActivity(), BasicInfoActivity.class);
                        startActivity(infoIntent);
                        break;
                    }
                    case 1: {
                        Intent synIntent = new Intent(getActivity(), ClassSynActivity.class);
                        startActivity(synIntent);
                        break;
                    }
                    case 2: {
                        Intent countIntent = new Intent(getActivity(),CountManagerActivity.class);
                        startActivity(countIntent);

                        break;
                    }
                }
            }
        });

        return contentView;
    }

    public void tabClicked(){

    }

    private class MeListAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return 3;
        }

        @Override
        public Object getItem(int i){
            return new Integer(i);
        }

        @Override
        public long getItemId(int i){
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
           ViewItem viewItem;
            if (view == null){
                LayoutInflater inflater = LayoutInflater.from(MeFragment.this.getActivity());
                view = inflater.inflate(R.layout.item_me_list,null);

                viewItem = new ViewItem();
                viewItem.itemImage = (ImageView)view.findViewById(R.id.me_item_img);
                viewItem.itemNmae = (TextView)view.findViewById(R.id.me_item_name);
                view.setTag(viewItem);
            }else {
                viewItem = (ViewItem)view.getTag();
            }

            if (i == 0){
                viewItem.itemImage.setImageResource(R.mipmap.me_img0);
                viewItem.itemNmae.setText("基本资料");
            }else if (i == 1){
                viewItem.itemImage.setImageResource(R.mipmap.me_img1);
                viewItem.itemNmae.setText("日历同步");
            }else if (i == 2){
                viewItem.itemImage.setImageResource(R.mipmap.me_img2);
                viewItem.itemNmae.setText("账号管理");
            }

            return view;
        }

        private class ViewItem{
            ImageView itemImage;
            TextView itemNmae;
        }

    }
}
