package com.webfarmatics.proteckapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.webfarmatics.proteckapp.R;

import java.util.ArrayList;
import java.util.List;


public class StateAdapterOne extends ArrayAdapter<String> {

    private Context context;
    private int resourceId;
    private List<String> items, tempItems, suggestions;
    public StateAdapterOne(@NonNull Context context, int resourceId, List<String> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<String>(items);
        suggestions = new ArrayList<String>();
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        try {
            if (convertView == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                view = inflater.inflate(resourceId, parent, false);
            }
            String city = getItem(position);
            TextView tvCity = (TextView) view.findViewById(R.id.tvStateTitle);
            tvCity.setText(city);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public String getItem(int position) {
        return items.get(position);
    }
    @Override
    public int getCount() {
        return items.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return cityFilter;
    }


    private Filter cityFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String city = (String) resultValue;
            return city;
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (String city: tempItems) {
                    if (city.toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
                        suggestions.add(city);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            ArrayList<String> tempValues = (ArrayList<String>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (String cityObj : tempValues) {
                    add(cityObj);
                    notifyDataSetChanged();
                }
            } else {
                clear();
                notifyDataSetChanged();
            }
        }
    };
}