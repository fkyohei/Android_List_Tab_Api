package com.example.kyohei.android_list_tab_api;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kyohei on 15/04/16.
 */
public class ShotsListFragment extends ListFragment implements AbsListView.OnScrollListener {

    public static final String TAG = ShotsListFragment.class.getSimpleName();

    private static final int MAX_COUNT = 50;

    private int mCount = 1;
    private String mCategory;

    private ShotsListAdapter mAdapter;
    private List<Shots> mList;
    private ListView mListView;
    private View mFooter;

    public ShotsListFragment () {

    }

    public static ShotsListFragment newInstance(String category) {
        Log.i(TAG, "newInstance");
        Log.i(TAG, "category = " + category);

        ShotsListFragment fragment = new ShotsListFragment();
        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.i(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);

        mListView = getListView();
        mListView.addFooterView(getFooter());
        mListView.setOnScrollListener(this);

        mCategory = getCategory();
        mAdapter = new ShotsListAdapter(getActivity(), Shots.getCategoryList(mCategory, mCount));
        setListAdapter(mAdapter);

        JsonObjectRequest jsonObjReq = MyRequest.getsInstance()
                .MyJsonObjectRequest(getCategory(), 1, new RequestListener.SuccesseListener() {
                    @Override
                    public void onResponse(Object response) {
                        mAdapter.add(Shots.getCategoryList(mCategory, mCount));
                    }
                }, new RequestListener.FailureListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 通信失敗時に何かしたければする
                    }
                });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Log.i(TAG, "onListItemClick:");
        Log.i(TAG, "position = " + position);
        Log.i(TAG, "id = " + id);
        super.onListItemClick(l, v, position, id);
        if(mList != null && mList.get(position) != null) {
            DetailDialogFragment detailDialogFragment = DetailDialogFragment.newInstance(mList.get(position).imageUrl);
            detailDialogFragment.show(getFragmentManager(), TAG);
        } else {
            Log.d("Mylog", "List is null");
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        Log.d(TAG, "onScrollStateChanged");
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if( mAdapter == null) {
            return;
        }

        // 最後尾まだスクロールした場合
        if( totalItemCount == firstVisibleItem + visibleItemCount) {
            Log.i(TAG, "onScroll");
            Log.i(TAG, "firstViewItem = " + firstVisibleItem);
            Log.i(TAG, "visibleItemCount = " + visibleItemCount);
            Log.i(TAG, "totalItemCount = " + totalItemCount);
            additionalReading();
        }
    }

    private String getCategory() {
        if( mCategory == null) {
            mCategory = getArguments().getString("category");
        }
        return mCategory;
    }

    private List<Shots> getList() {
        if( mList == null) {
            mList = new ArrayList<Shots>();
            addListData();
        }
        return mList;
    }

    private ListView getMyListView() {
        if( mListView == null) {
            mListView = getListView();
        }
        return mListView;
    }

    private View getFooter() {
        if( mFooter == null) {
            mFooter = getLayoutInflater(getArguments()).inflate(R.layout.listview_footer, null);
        }
        return mFooter;
    }

    private void invisibleFooter() {
        Log.i(TAG, "visibleFooter");
        getMyListView().removeFooterView(getFooter());
    }

    private void addListData() {
        Log.i(TAG, "addListData");
        List<Shots> addList = Shots.getCategoryList(getCategory(), mCount);
        getList().addAll(addList);
        mCount++;
    }

    private void additionalReading() {
        Log.i(TAG, "additionalReading");
        if( mCount >= MAX_COUNT) {
            invisibleFooter();
            return;
        }

        mCount++;
        JsonObjectRequest jsonObjReq = MyRequest.getsInstance()
                .MyJsonObjectRequest(mCategory, mCount, new RequestListener.SuccesseListener(){
                    @Override
                    public void onResponse(Object response) {
                        addListData();
                        mAdapter.add(Shots.getCategoryList(mCategory, mCount));
                    }
                }, new RequestListener.FailureListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // 通信失敗時に何か表示したい場合などは処理を追加
                    }
                });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


}
