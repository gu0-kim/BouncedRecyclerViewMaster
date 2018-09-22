package com.gu.devel.bouncedrecyclerview_master;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.gu.devel.bounced_lib.recyclerview.BouncedRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

  @BindView(R.id.rv)
  BouncedRecyclerView bouncedRv;

  private SimpleAdapter mAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    bouncedRv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    mAdapter = new SimpleAdapter(getApplicationContext());
    bouncedRv.setAdapter(mAdapter);
  }

  @OnClick(R.id.scrollBtn)
  public void onScrollBtnClicked() {
    bouncedRv.setTranslationY(300);
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mAdapter.clear();
  }
}
