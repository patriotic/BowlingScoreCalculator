package com.example.bowlingscorecalculator.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bowlingscorecalculator.BR;
import com.example.bowlingscorecalculator.R;
import com.example.bowlingscorecalculator.model.Frame;

public class FrameAdapter extends ListAdapter<Frame, FrameAdapter.ViewHolder> {
    public static final DiffUtil.ItemCallback<Frame> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Frame>() {
                @Override
                public boolean areItemsTheSame(@NonNull Frame oldItem, @NonNull Frame newItem) {
                    return oldItem.hashCode() == newItem.hashCode();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Frame oldItem, @NonNull Frame newItem) {
                    return oldItem.equals(newItem);
                }
            };

    protected FrameAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.frame_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frame item = getItem(position);
        holder.mBinding.setVariable(BR.item, item);
        holder.mBinding.executePendingBindings();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding mBinding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }
    }

}
