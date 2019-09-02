package com.example.jaloliddin.bestblog.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jaloliddin.bestblog.R;
import com.example.jaloliddin.bestblog.models.ModelRecyclerComments;

import java.util.List;

public class MylRecyclerAdapterComments extends RecyclerView.Adapter<MylRecyclerAdapterComments.ViewHolder> {

    private Context context;
    private List<ModelRecyclerComments> modelRecyclerCom;

    public MylRecyclerAdapterComments(Context context, List<ModelRecyclerComments> modelrecyclerCom) {
        this.context = context;
        this.modelRecyclerCom = modelrecyclerCom;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_read,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final ModelRecyclerComments modelRecyclerComments=modelRecyclerCom.get(position);
            holder.itemReadUserName.setText(modelRecyclerComments.getUserName());
            holder.itemReadUserComment.setText(modelRecyclerComments.getUserComment());
            holder.itemReadUserCommentTime.setText(modelRecyclerComments.getCommentTime());
    }

    @Override
    public int getItemCount() {

        return modelRecyclerCom.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemReadUserName,itemReadUserComment,itemReadUserCommentTime;

        public ViewHolder(View itemView) {
            super(itemView);

            itemReadUserName=(TextView) itemView.findViewById(R.id.itemReadUserName);
            itemReadUserComment=(TextView) itemView.findViewById(R.id.itemReadUserComment);
            itemReadUserCommentTime=(TextView) itemView.findViewById(R.id.itemReadUserCommentTime);

        }
    }
}
