/*
 * Copyright (c) 2016. Ikamantab (Ikatan Keluarga Alumni Man Tambakberas).
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file   except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License..
 */

package com.mantambakberas.ikamantab.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.mantambakberas.ikamantab.R;
import com.mantambakberas.ikamantab.config.AppConfig;
import com.mantambakberas.ikamantab.config.AppController;
import com.mantambakberas.ikamantab.helper.CircleTransform;
import com.mantambakberas.ikamantab.helper.CircledNetworkImageView;
import com.mantambakberas.ikamantab.model.Friend;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by winnerawan on 10/18/16.
 */

public class ListFriendsAdapter extends RecyclerView.Adapter<ListFriendsAdapter.UsersViewHolder> {

    private static final String TAG = ListFriendsAdapter.class.getSimpleName();
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public List<Friend> friends;
    private int rowLayout;
    private Context context;

    public static class UsersViewHolder extends RecyclerView.ViewHolder {
        LinearLayout friendsLayout;
        TextView nameView;
        TextView jurusanView;
        CircleImageView fotoView;

        public UsersViewHolder(View v) {
            super(v);
            friendsLayout = (LinearLayout) v.findViewById(R.id.usersLayout);
            nameView = (TextView) v.findViewById(R.id.nama);
            jurusanView = (TextView) v.findViewById(R.id.jurusan);
            fotoView = (CircleImageView) v.findViewById(R.id.foto);
        }
    }

    public ListFriendsAdapter(List<Friend> friends, int rowLayout, Context context) {
        this.friends=friends;
        this.rowLayout=rowLayout;
        this.context=context;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UsersViewHolder holder, int position) {
        String default_foto = AppConfig.BASE_URL+"/api/v1/default-foto.png";
        String default_jur = "";
        holder.nameView.setText(friends.get(position).getName());
        if (friends.get(position).getFoto()==null) {
            //holder.fotoView.setImageUrl(default_foto, imageLoader);
            Picasso.with(context).load(Uri.parse(default_foto)).transform(new CircleTransform()).into(holder.fotoView);
        } else {
            //holder.fotoView.setImageUrl(friends.get(position).getFoto(), imageLoader);
            String foto = friends.get(position).getFoto();
            String foto_url = foto + "?time=" + System.currentTimeMillis();
            Picasso.with(context).load(Uri.parse(foto_url)).transform(new CircleTransform()).into(holder.fotoView);
        } if (friends.get(position).getAngkatan()==null) {
            holder.jurusanView.setText("");
        } else {
            holder.jurusanView.setText(friends.get(position).getJurusan() + " - " + friends.get(position).getAngkatan());
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        holder.friendsLayout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
