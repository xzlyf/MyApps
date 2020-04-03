package com.xz.myapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.xz.base.BaseRecyclerAdapter;
import com.xz.base.BaseRecyclerViewHolder;
import com.xz.myapp.R;
import com.xz.myapp.entity.ClassEntity;

/**
 * @author czr
 * @date 2020/4/3
 */
public class ClassAdapter extends BaseRecyclerAdapter<ClassEntity> {
    public ClassAdapter(Context context) {
        super(context);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ((ViewHolder) holder).tvName.setText(mList.get(position).getName());
    }

    @Override
    protected BaseRecyclerViewHolder createNewViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_main_list, parent, false));
    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvName.setOnClickListener(openListener);
        }

        View.OnClickListener openListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext,mList.get(getLayoutPosition()).getC()));
            }
        };
    }
}
