package com.example.kyohei.android_list_tab_api;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by kyohei on 15/04/08.
 */
public class DetailDialogFragment extends DialogFragment {

    static DetailDialogFragment newInstance(String imgUrl) {
        DetailDialogFragment f = new DetailDialogFragment();
        Bundle args = new Bundle();
        args.putString("url", imgUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        View v = inflater.inflate(R.layout.detail, container, false);
        Bundle arguments = getArguments();
        if( arguments != null) {
            String imgUrl = arguments.getString("url");
            if( imgUrl != null) {
                NetworkImageView networkImageView = (NetworkImageView) v.findViewById(R.id.image);
                ImageLoader imageLoader = AppController.getInstance().getImageLoader();
                networkImageView.setImageUrl(imgUrl, imageLoader);
            }
        }

        return v;
    }
}
