package com.orlandogareca.imdbapi.ListDataSource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.orlandogareca.imdbapi.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class CustomAdapter  extends BaseAdapter implements  OnLoadImage{
    private Context CONTEXT;
    private ArrayList<ItenList> LIST;
    public CustomAdapter (Context context, ArrayList<ItenList> list) {
        this.CONTEXT = context;
        this.LIST = list;

    }
    @Override
    public int getCount() {
        return this.LIST.size();
    }

    @Override
    public Object getItem(int position) {
        return this.LIST.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflate = (LayoutInflater) this.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflate.inflate(R.layout.item_layout, null);
        }
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView year = (TextView)convertView.findViewById(R.id.year);
        TextView type = (TextView)convertView.findViewById(R.id.type);

        title.setText(this.LIST.get(position).getTitle());
        year.setText(this.LIST.get(position).getYear());
        type.setText(this.LIST.get(position).getType());

        ImageView img = (ImageView)convertView.findViewById(R.id.poster_layout);
        //falta la programacion del hilo para la carga de imagen
        TaskImg hilo = new TaskImg();
        hilo.setLoadImage(img, this);
        hilo.execute(this.LIST.get(position).getPoster());


        return convertView;
    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {
        container.setImageBitmap(img);
    }
}
