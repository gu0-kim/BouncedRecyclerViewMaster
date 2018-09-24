# BouncedRecyclerViewMaster
## 说明
让任何recycelerView方便的加入弹性效果
## 使用方法
在你的recyclerview外包一层，其他不用做任何修改。
```
<com.gu.devel.bounced.recyclerview.BouncedRecyclerViewParent
        android:id="@+id/parent"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <android.support.v7.widget.RecyclerView
            android:id="@+id/rv"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </com.gu.devel.bounced.recyclerview.BouncedRecyclerViewParent>
```
## 效果
<img width="540" height=“960” src="https://github.com/gu0-kim/BouncedRecyclerViewMaster/blob/master/art/device-2018-09-24-223214.gif"></img>
## 下载
![image](https://github.com/gu0-kim/BouncedRecyclerViewMaster/blob/master/art/qrcode.png)