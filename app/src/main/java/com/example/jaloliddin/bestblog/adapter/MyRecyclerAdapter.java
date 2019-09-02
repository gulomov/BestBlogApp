package com.example.jaloliddin.bestblog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaloliddin.bestblog.AcRead;
import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.models.ModelRecycler;

import java.util.List;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<ModelRecycler> modelRecyclers;

    public MyRecyclerAdapter(Context context, List<ModelRecycler> modelRecyclers) {
        this.context = context;
        this.modelRecyclers = modelRecyclers;
    }

    public void clear() {
        int size = modelRecyclers.size();
        modelRecyclers.clear();
        notifyItemRangeRemoved(0, size);
    }

    @NonNull
    @Override
    //bu methodni ichida xml filemizni inflate qilamiz
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        //ModelRecyclerga obyekt yaratib textviewlarimizni dinamic xolatga ozgartiryabmiz
        final ModelRecycler modelRecycler = modelRecyclers.get(position);
        holder.itemTitleTv.setText(modelRecycler.getTitle());
        holder.itemDescTv.setText(modelRecycler.getDesc());
        if (modelRecycler.getLike() == 0) {
            holder.itemRecyclerLikes.setVisibility(View.GONE);
        } else {
            holder.itemLikeTv.setText("" + modelRecycler.getLike());
        }
        if (modelRecycler.getComment() == 0) {
            holder.itemRecyclerComments.setVisibility(View.GONE);
        } else {
            holder.itemCommentTv.setText("" + modelRecycler.getLike());
        }
        holder.itemSeeTv.setText(modelRecycler.getSee());
        holder.itemTimeTv.setText(modelRecycler.getTime());

        holder.itemRecyclerCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MyTag", getClass().getName() + ">> onClick: getID = " + modelRecycler.getId());

                context.startActivity(new Intent(context, AcRead.class).putExtra("postID", modelRecycler.getId()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelRecyclers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemTitleTv, itemDescTv, itemLikeTv, itemCommentTv, itemSeeTv, itemTimeTv;
        CardView itemRecyclerCardView;
        LinearLayout itemRecyclerLikes;
        LinearLayout itemRecyclerComments;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTitleTv = (TextView) itemView.findViewById(R.id.itemTitleTv);
            itemDescTv = (TextView) itemView.findViewById(R.id.itemDescTv);
            itemLikeTv = (TextView) itemView.findViewById(R.id.itemLikeTv);
            itemCommentTv = (TextView) itemView.findViewById(R.id.itemCommentTv);
            itemSeeTv = (TextView) itemView.findViewById(R.id.itemSeeTv);
            itemTimeTv = (TextView) itemView.findViewById(R.id.itemTimeTv);
            itemRecyclerCardView = (CardView) itemView.findViewById(R.id.itemRecyclerCardView);
            itemRecyclerLikes = (LinearLayout) itemView.findViewById(R.id.itemRecyclerLikes);
            itemRecyclerComments = (LinearLayout) itemView.findViewById(R.id.itemRecyclerComments);
        }
    }

}

