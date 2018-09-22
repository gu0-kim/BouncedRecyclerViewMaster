package com.gu.devel.bouncedrecyclerview_master;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.ViewHolder> {
  private List<String> data;
  private LayoutInflater mInflater;

  public SimpleAdapter(Context context) {
    mInflater = LayoutInflater.from(context);
    data = new ArrayList<>();
    for (int i = 0; i < 30; i++) {
      data.add(String.valueOf(i));
    }
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ViewHolder(mInflater.inflate(R.layout.text, parent, false));
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.tv.setText(data.get(position));
  }

  @Override
  public int getItemCount() {
    return data != null ? data.size() : 0;
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tv)
    TextView tv;

    ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }

  public void clear() {
    if (data != null) {
      data.clear();
      data = null;
    }
  }
}
