package com.webfarmatics.proteckapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.Brand;

import java.util.List;

public class BrandsSpinnerAdapter extends BaseAdapter {

    private Context context;
    private List<Brand> stringArrayList;

    public BrandsSpinnerAdapter(Context context, List<Brand> stringArrayList) {
        this.context = context;
        this.stringArrayList = stringArrayList;
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return stringArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewOne = convertView;

        if (convertView == null) { // if it's not recycled, initialize some
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOne = inflater.inflate(R.layout.adpt_brand, parent, false);
        }

        TextView _tv_title = viewOne.findViewById(R.id.tv_title);
        _tv_title.setText(stringArrayList.get(position).getBrandName());

        return viewOne;
    }
}
