package com.example.lenovo.ranking;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.NormalViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ArrayList<String> mRank;
    private ArrayList<String> mStatus;
    private ArrayList<String> mUser;
    private ArrayList<String> mScore;
//    private ArrayList<Integer> mPic;

    public RecyclerViewAdapter(Context context, ArrayList<String>ranks, ArrayList<String>status, ArrayList<String>users, ArrayList<String>scores){
        mContext=context;
//        mPic=pics;
        mStatus=status;
        mRank=ranks;
        mUser=users;
        mScore=scores;
        mLayoutInflater=LayoutInflater.from(context);

    }

    @Override
    public NormalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new NormalViewHolder(mLayoutInflater.inflate(R.layout.recycleview_item,parent,false));
    }

    @Override
    public void onBindViewHolder(NormalViewHolder holder, final int position) {
        holder.mTVrank.setText(mRank.get(position));
//        holder.mImageView.setBackgroundResource(mPic.get(position));
        holder.mTVstatus.setText(mStatus.get(position));
        holder.mTVuser.setText(mUser.get(position));
        holder.mTVscore.setText(mScore.get(position));
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mRank.get(position),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mRank==null ? 0 : mRank.size();
    }
    public static class NormalViewHolder extends RecyclerView.ViewHolder{
        TextView mTVrank;
//        ImageView mImageView;
        TextView mTVstatus;
        TextView mTVuser;
        TextView mTVscore;
        CardView mCardView;
        public NormalViewHolder(View itemView) {
            super(itemView);
            mTVrank=(TextView)itemView.findViewById(R.id.tv_rank);
            mTVuser=(TextView)itemView.findViewById(R.id.tv_user);
            mTVscore=(TextView)itemView.findViewById(R.id.tv_score);
//            mImageView=(ImageView)itemView.findViewById(R.id.iv_pic);
            mTVstatus=(TextView) itemView.findViewById(R.id.tv_status);
            mCardView=(CardView)itemView.findViewById(R.id.cv_item);
        }
    }
}
