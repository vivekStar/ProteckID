package com.webfarmatics.proteckapp.adapters;

import android.app.Activity;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.Model;

import java.util.ArrayList;
import java.util.List;


public class ModelAdapterOne extends ArrayAdapter<Model> {

    private Context context;
    private int resourceId;
    private List<Model> items, tempItems, suggestions;
    public ModelAdapterOne(@NonNull Context context, int resourceId, ArrayList<Model> items) {
        super(context, resourceId, items);
        this.items = items;
        this.context = context;
        this.resourceId = resourceId;
        tempItems = new ArrayList<Model>(items);
        suggestions = new ArrayList<Model>();
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
            Model city = getItem(position);
            TextView tvCity = (TextView) view.findViewById(R.id.tvBrandTitle);
            tvCity.setText(city.getModelName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }
    @Nullable
    @Override
    public Model getItem(int position) {
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
            Model city = (Model) resultValue;
            return city.getModelName();
        }
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            if (charSequence != null) {
                suggestions.clear();
                for (Model city: tempItems) {
                    if (city.getModelName().toLowerCase().startsWith(charSequence.toString().toLowerCase())) {
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
            ArrayList<Model> tempValues = (ArrayList<Model>) filterResults.values;
            if (filterResults != null && filterResults.count > 0) {
                clear();
                for (Model cityObj : tempValues) {
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