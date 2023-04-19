package com.android.ex.th2;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.ex.databinding.ItemSpendingBinding;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Spending> list = new ArrayList<>();
    private final OnClickListener listener;

    public ItemAdapter(OnClickListener listener) {
        this.listener = listener;
    }

    public interface OnClickListener {
        void OnItemClick(Spending spending, View view);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSpendingBinding binding = ItemSpendingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(list.get(position));
        holder.binding.item.setOnClickListener(view -> listener.OnItemClick(list.get(position), view));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reloadData(List<Spending> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        ItemSpendingBinding binding;

        public ItemViewHolder(@NonNull ItemSpendingBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Spending spending) {
            binding.nameTxt.setText(spending.getName());
            binding.descriptionTxt.setText(spending.getDescription());
            binding.price.setText("Gia: " + spending.getPrice() + "K");
            binding.dateTxt.setText(spending.getDate());
        }
    }
}
