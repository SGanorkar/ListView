package com.example.gdrivebackup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomAdapter extends BaseAdapter implements Filterable
{
    private CustFilter filter;

    public static Context context;
    public static ArrayList<String> custIdList, custNameList;

    public static ArrayList<String> temporarylist;

    public CustomAdapter(Context context, ArrayList<String> custIdList, ArrayList<String> custNameList)
    {
        CustomAdapter.context = context;
        CustomAdapter.custIdList = custIdList;
        CustomAdapter.custNameList = custNameList;

        temporarylist = custNameList;
    }

    @Override
    public int getCount()
    {
        return temporarylist.size();
    }

    @Override
    public Object getItem(int i)
    {
        return temporarylist.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View itemView = null;
        if(itemView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            itemView = inflater.inflate( R.layout.item_subitem, null );

            TextView custId = (TextView) itemView.findViewById(R.id.sampleCustId);
            custId.setText("Customer ID : "+ custIdList.get(i));

            TextView custName = itemView.findViewById(R.id.sampleCustName);
            custName.setText(custNameList.get(i));
        }
        return itemView;
    }

    @Override
    public Filter getFilter()
    {
        if(filter == null)
        {
            filter = new CustFilter();
        }
        return filter;
    }

    private class CustFilter extends Filter
    {
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results)
        {
            if (results.count == 0)
            {
                notifyDataSetInvalidated();
            }
            else
            {
                custNameList.clear();
                custNameList.addAll((ArrayList<String>) results.values);
                notifyDataSetChanged();
            }
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0)
            {
                // No filter implemented we return all the list
                results.values = custNameList;
                results.count = custNameList.size();
            }
            else
            {
                ArrayList<String> FilteredList= new ArrayList<String>();

                for (int i = 0; i < custNameList.size(); i++)
                {
                    String data = custNameList.get(i);
                    if (data.toLowerCase().contains(constraint.toString()))
                    {
                        FilteredList.add(data);
                        Log.i("Add", data);
                    }
                }
                results.values = FilteredList;
                results.count = FilteredList.size();
            }
            return results;
        }
    };
}

