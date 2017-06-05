package club.cartoleirosfutebol.cartomitos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;

import club.cartoleirosfutebol.cartomitos.R;

/**
 * Created by joao.oliveira on 05/06/2017.
 */

public class OrderMercadoAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayAdapter<String> adapter;

    public OrderMercadoAdapter(Context context, ArrayAdapter<String> adapter) {
        inflater = LayoutInflater.from(context);
        this.adapter = adapter;
    }

    public int getCount() {
        return adapter.getCount();
    }

    public Object getItem(int position) {
       return adapter.getItemId(position);
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int arg0, View convertview, ViewGroup arg2) {
        ViewHolder viewHolder;
        if (convertview == null) {
            convertview = inflater.inflate(R.layout.listrow_sort_mercado, null);
            viewHolder = new ViewHolder();
            viewHolder.spinner = (Spinner) convertview.findViewById(R.id.spinner1);
            viewHolder.spinner.setAdapter(adapter);
            convertview.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertview.getTag();
        }
        return convertview;
    }

    public class ViewHolder {
        Spinner spinner;
    }
}
