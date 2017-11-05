package com.oladimeji.lagosdevelopers.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oladimeji.lagosdevelopers.R;
import com.oladimeji.lagosdevelopers.Profile.Developers;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Oladimeji on 7/10/2017.
 */

public class DeveloperAdapter extends RecyclerView.Adapter<DeveloperAdapter.ViewHolder> {

    private List<Developers> developers;
    private Context context;


    public DeveloperAdapter(List<Developers> developers, Context context) {
        this.developers = developers;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View developerView = layoutInflater.inflate(R.layout.developers_list, parent, false);
        return new ViewHolder(developerView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Developers developer = developers.get(position);

        ImageView imageView = holder.developersImage;
        Picasso.with(context)
                .load(developer.getAvatarUrl())
                .into(imageView);

        TextView textView = holder.developersUsername;
        textView.setText(developer.getLogin());
    }

    @Override
    public int getItemCount() {
        return developers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView developersImage;
        public TextView developersUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            this.developersImage = (ImageView) itemView.findViewById(R.id.developer_image);
            this.developersUsername = (TextView) itemView.findViewById(R.id.developer_username);
        }
    }
}

