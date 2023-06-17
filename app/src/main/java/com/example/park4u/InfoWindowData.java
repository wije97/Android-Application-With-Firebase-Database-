package com.example.park4u;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class InfoWindowData implements GoogleMap.InfoWindowAdapter{

    private Activity context;

    public InfoWindowData (Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        TextView tvSubTitle = (TextView) view.findViewById(R.id.tv_subtitle);
        ImageView imgIcon = (ImageView) view.findViewById(R.id.ivIcon);

        tvTitle.setText(marker.getTitle());
        tvSubTitle.setText(marker.getSnippet());


        return view;
    }
}
