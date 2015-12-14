package com.example.suguiming.superclass.studentTab;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentListFragment extends BaseFragment {

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        listView = (ListView)view.findViewById(R.id.student_list);
        listView.setAdapter(new StudentAdapter());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),StudentInfoActivity.class);
                startActivity(intent);
            }
        });
        return view;

    }
    public void tabClicked(){

    }


    private class StudentAdapter extends BaseAdapter{
        @Override
        public int getCount(){
            return 20;
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
            ViewHolder viewHolder;
            if (view == null){
                LayoutInflater inflater = LayoutInflater.from(StudentListFragment.this.getActivity());
                view = inflater.inflate(R.layout.item_student_list,null);

                viewHolder = new ViewHolder();
                viewHolder.nameTv = (TextView)view.findViewById(R.id.name_tv);
                viewHolder.infoTv = (TextView)view.findViewById(R.id.info_tv);
                viewHolder.phoneImg = (ImageView)view.findViewById(R.id.phone_img);
                view.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder)view.getTag();
            }
            return view;
        }
        private class ViewHolder{
            ImageView phoneImg;
            TextView  nameTv;
            TextView infoTv;
        }

    }

}
