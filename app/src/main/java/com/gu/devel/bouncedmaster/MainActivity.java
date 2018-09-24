package com.gu.devel.bouncedmaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.gu.devel.bounced.recyclerview.BouncedRecyclerViewParent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.rv)
  RecyclerView rv;

  @BindView(R.id.parent)
  BouncedRecyclerViewParent parent;

  private SimpleAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mAdapter = new SimpleAdapter(getApplicationContext());
    rv.setAdapter(mAdapter);
  }

  @OnClick(R.id.scrollBtn)
  public void onScrollBtnClicked() {
    rv.setTranslationY(300);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mAdapter.clear();
    parent.clear();
  }
}
