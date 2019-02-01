package com.eebbk.bfc.db.demo.basic.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.eebbk.bfc.db.demo.R;
import com.eebbk.bfc.db.demo.baseui.BaseActivity;
import com.eebbk.bfc.db.demo.db.DbManager;
import com.eebbk.bfc.db.demo.db.entity.UserInfo;
import com.eebbk.bfc.db.demo.db.entity.UserInfoDao;
import com.eebbk.bfc.db.demo.util.IdUtil;
import com.eebbk.bfc.db.demo.util.L;
import com.eebbk.bfc.db.demo.util.ToastUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.greenrobot.dao.query.LazyList;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;

public class BasicOperationActivity extends BaseActivity implements View.OnClickListener,
        MyAdapter.OnItemClickListener {

    private Button mInsertBtn;
    private Button mUpdateBtn;
    private Button mDeleteBtn;
    private Button mQueryBtn;

    private UserEditUIPanel mUserPanel;
    private EditText mMultiInsertCountsEtv;
    private Button mMultiInsertBtn;

    private BatchEditUIPanel mBatchPanel;

    private TextView mMsgTv;

    private ListView mListView;
    private MyAdapter mAdapter;

    private UserEditUIPanel mUpdateUserItemPanel;
    private List<WhereCondition> mOldConditions;

    private Handler mHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_operation);
        setTitle("基本操作");

        initView();
    }

    private void initView(){
        mInsertBtn = findView(R.id.insert_btn);
        mUpdateBtn = findView(R.id.update_btn);
        mDeleteBtn = findView(R.id.delete_btn);
        mQueryBtn = findView(R.id.query_btn);

        mUserPanel = new UserEditUIPanel(this, findView(R.id.insert_panel), mInsertUserListener);
        mMultiInsertCountsEtv = findView(R.id.multi_insert_etv);
        mMultiInsertBtn = findView(R.id.insert_multi_ok_btn);
        mMultiInsertBtn.setOnClickListener(this);

        mBatchPanel = new BatchEditUIPanel(this, findView(R.id.query_panel), mBatchPanelListener);

        mUpdateUserItemPanel = new UserEditUIPanel(this, findView(R.id.update_item_panel), mUpdateUserListener);

        mMsgTv = findView(R.id.msg_tv);
        mListView = findView(R.id.list_view);
        mAdapter = new MyAdapter(this, this);
        mListView.setAdapter(mAdapter);

        mInsertBtn.setOnClickListener(this);
        mUpdateBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);
        mQueryBtn.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        if(mUpdateUserItemPanel.isShow()){
            mUpdateUserItemPanel.hidePanel();
            return;
        }
        if(mUserPanel.isShow()){
            mUserPanel.hidePanel();
            return;
        }
        if(mBatchPanel.isShow()){
            mBatchPanel.hidePanel();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert_btn:
                insertBtnClick();
                break;
            case R.id.update_btn:
                updateBtnClick();
                break;
            case R.id.delete_btn:
                deleteBtnClick();
                break;
            case R.id.query_btn:
                mBatchPanel.showPanel();
                break;
            case R.id.insert_multi_ok_btn:
                processMultiInsert();
                break;
            default:
                break;
        }
    }

    private void insertBtnClick(){
        if(!mUserPanel.isShow()){
            mUserPanel.showPanel(null);
        } else {
            mUserPanel.hidePanel();
        }
    }

    private void processInsert(UserInfo userInfo){
        mListView.setVisibility(View.INVISIBLE);

        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        long startTime = System.currentTimeMillis();
        long rowId = -1;
        Exception ex = null;
        try {
            rowId = userInfoDao.insert(userInfo);
        } catch (Exception e){
            ex = e;
            L.e(e, " insert error ");
        }
        long endTime = System.currentTimeMillis();
        showInsertMsg("插入", startTime, endTime, rowId > 0, userInfo, ex);
    }

    private void processMultiInsert(){
        mListView.setVisibility(View.INVISIBLE);

        long count = -1;
        try {
            String numberStr = mMultiInsertCountsEtv.getEditableText().toString();
            numberStr = numberStr.trim();
            numberStr = numberStr.replaceAll("^[0]+","");
            count = Long.parseLong(numberStr);
        } catch (Exception e){
            showToast("请输入有效的数（>0 且 <50000 的整数）！");
            return;
        }
        if(count <= 0){
            showToast("请输入有效的数（>0 且 <50000 的整数）！");
            return;
        }
        if(count > 50000){
            showToast("请输入有效的数（>0 且 <50000 的整数）！");
            return;
        }

        mUserPanel.hidePanel();
        ArrayList<UserInfo> list = new ArrayList<>();
        UserInfo userInfo = null;
        for(long i = 0; i < count; i ++){
            userInfo = new UserInfo();
            userInfo.setUserId(i + "+uuid[" + IdUtil.generateId() +"]");
            userInfo.setUserName("userName" + i);
            userInfo.setUserPwd("userPwd" + i);
            list.add(userInfo);
        }
        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        userInfoDao.insertInTx();
        long startTime = System.currentTimeMillis();
        Exception ex = null;
        try {
            userInfoDao.insertInTx(list);
        } catch (Exception e){
            ex = e;
            L.e(e, " insert error ");
        }
        long endTime = System.currentTimeMillis();
        list.clear();
        showInsertMsg("批量插入", startTime, endTime, ex==null, userInfo, ex);
    }

    private void updateBtnClick(){
        mListView.setVisibility(View.INVISIBLE);

        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        List<UserInfo> list = userInfoDao.loadAll();
        if(list == null || list.size() < 1){
            showToast(" 数据库中没有数据！ ");
            return;
        }

        UserInfo userInfo = list.get(0);
        UserInfo oldInfo = new UserInfo();
        oldInfo.setUserId(userInfo.getUserId());
        oldInfo.setUserName(userInfo.getUserName());
        oldInfo.setUserPwd(userInfo.getUserPwd());

        long startTime = System.currentTimeMillis();
        userInfo.setUserName("张三");
        userInfo.setUserPwd("密码：" + (int) (Math.random() * 100000));
        userInfoDao.update(userInfo);
        long endTime = System.currentTimeMillis();

        showUpdateMsg("更新", startTime, endTime, true, oldInfo, userInfo);
    }

    private void deleteBtnClick(){
        mListView.setVisibility(View.INVISIBLE);

        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        Query<UserInfo> query = userInfoDao.queryBuilder().build();
        LazyList  data = (query == null?null:query.listLazy());
        int size = data==null?0:data.size();
        long startTime = System.currentTimeMillis();
        userInfoDao.deleteAll();
        long endTime = System.currentTimeMillis();
        showDeleteAllMsg("删除", startTime, endTime, true, size);
    }

    private void processQuery(List<WhereCondition> conditions){
        mListView.setVisibility(View.VISIBLE);

        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        long startTime = System.currentTimeMillis();
        Query<UserInfo> query = null;
        if(conditions.isEmpty()){
            query = userInfoDao.queryBuilder().build();
        } else {
            if(conditions.size() == 1){
                query = userInfoDao.queryBuilder().where(conditions.get(0)).build();
            } else {
                query = userInfoDao.queryBuilder().where(conditions.get(0), conditions.get(1)).build();
            }
        }
        LazyList  data = (query == null?null:query.listLazy());
        mAdapter.bindData(data);
        mAdapter.notifyDataSetChanged();

        long endTime = System.currentTimeMillis();
        int size = data==null?0:data.size();
        showQueryMsg("查询", startTime, endTime, true, size);
        if(size <= 0){
            showToast(" 没有查询到数据 ");
        }
    }

    public void showInsertMsg(String operation, long startTime, long endTime, boolean result, UserInfo info, Exception ex){
        StringBuilder sb = new StringBuilder();
        sb.append("<br> 执行操作：" + operation);
        sb.append("<br> 开始时间：" + formatTime(startTime));
        sb.append("<br> 结束时间：" + formatTime(endTime));
        sb.append("<br> 处理时间(毫秒)：" + (endTime - startTime));

        sb.append("<br> " + operation + ": <br> " +getUserInfoStr(info));

        sb.append("<br><br> 结果： " + getResultStr(result));
        sb.append("<br> 异常信息： " + Log.getStackTraceString(ex));

        mMsgTv.setText(Html.fromHtml(sb.toString()));
    }

    public void showUpdateMsg(String operation, long startTime, long endTime, boolean result, UserInfo oldInfo, UserInfo newInfo){
        StringBuilder sb = new StringBuilder();
        sb.append("<br> 执行操作：" + operation);
        sb.append("<br> 开始时间：" + formatTime(startTime));
        sb.append("<br> 结束时间：" + formatTime(endTime));
        sb.append("<br> 处理时间(毫秒)：" + (endTime - startTime));
        sb.append("<br> 结果： " + getResultStr(result));
        sb.append("<br> 数据更新前：" + getUserInfoStr(oldInfo));
        sb.append("<br>");
        sb.append("<br> 数据更新后：" + getUserInfoStr(newInfo));

        mMsgTv.setText(Html.fromHtml(sb.toString()));
    }

    public void showDeleteAllMsg(String operation, long startTime, long endTime, boolean result, int size){
        StringBuilder sb = new StringBuilder();
        sb.append("<br> 执行操作：" + operation);
        sb.append("<br> 开始时间：" + formatTime(startTime));
        sb.append("<br> 结束时间：" + formatTime(endTime));
        sb.append("<br> 处理时间(毫秒)：" + (endTime - startTime));
        sb.append("<br> 结果： " + getResultStr(result));
        sb.append("<br> " + operation + "条数: " + size);

        mMsgTv.setText(Html.fromHtml(sb.toString()));
    }

    public void showDeleteMsg(String operation, long startTime, long endTime, boolean result, UserInfo info){
        StringBuilder sb = new StringBuilder();
        sb.append("<br> 执行操作：" + operation);
        sb.append("<br> 开始时间：" + formatTime(startTime));
        sb.append("<br> 结束时间：" + formatTime(endTime));
        sb.append("<br> 处理时间(毫秒)：" + (endTime - startTime));
        sb.append("<br> 结果： " + getResultStr(result));
        sb.append("<br> " + operation + ": <br> " + getUserInfoStr(info));

        mMsgTv.setText(Html.fromHtml(sb.toString()));
    }

    public void showQueryMsg(String operation, long startTime, long endTime, boolean result, int count){
        StringBuilder sb = new StringBuilder();
        sb.append("<br> 执行操作：" + operation);
        sb.append("<br> 开始时间：" + formatTime(startTime));
        sb.append("<br> 结束时间：" + formatTime(endTime));
        sb.append("<br> 处理时间(毫秒)：" + (endTime - startTime));
        sb.append("<br> 结果： " + getResultStr(result));

        if(count < 1){
            sb.append("<br> 数据集： 没有数据！");
        } else {
            sb.append("<br> 数据集： " + count + "条");
        }

        mMsgTv.setText(Html.fromHtml(sb.toString()));
    }

    public void showToast(String msg){
        ToastUtil.showToast(this, msg);
    }

    @Override
    public void onItemDelete(UserInfo info) {
        long startTime = System.currentTimeMillis();
        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        userInfoDao.delete(info);
        long endTime = System.currentTimeMillis();
        showDeleteMsg("删除", startTime, endTime, true, info);

        reQuery();
    }

    @Override
    public void onItemUpdate(UserInfo userInfo) {
        if(mUpdateUserItemPanel != null){
            mUpdateUserItemPanel.showPanel(userInfo);
        }
    }

    private UserEditUIPanel.OnEditPanelListener mInsertUserListener = new UserEditUIPanel.OnEditPanelListener() {
        @Override
        public void onUserPanelOkBtnClick(UserInfo oldUserInfo, UserInfo newUserInfo) {
            mUserPanel.hidePanel();
            processInsert(newUserInfo);
        }

        @Override
        public void onUserPanelCancelBtnClick() {
            mUserPanel.hidePanel();
        }
    };

    private UserEditUIPanel.OnEditPanelListener mUpdateUserListener = new UserEditUIPanel.OnEditPanelListener() {
        @Override
        public void onUserPanelOkBtnClick(UserInfo oldUserInfo, UserInfo newUserInfo) {
            mUpdateUserItemPanel.hidePanel();
            UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();

            long startTime = System.currentTimeMillis();
            userInfoDao.update(newUserInfo);
            long endTime = System.currentTimeMillis();

            reQuery();
            showUpdateMsg("更新", startTime, endTime, true, oldUserInfo, newUserInfo);
        }

        @Override
        public void onUserPanelCancelBtnClick() {
            mUpdateUserItemPanel.hidePanel();
        }
    };

    private BatchEditUIPanel.OnEditPanelListener mBatchPanelListener = new BatchEditUIPanel.OnEditPanelListener() {
        @Override
        public void onBatchBtnClick(List<WhereCondition> conditions) {
            mBatchPanel.hidePanel();
            processQuery(conditions);
        }
    };

    private void reQuery(){
        UserInfoDao userInfoDao = DbManager.getInstance().getDaoSession().getUserInfoDao();
        QueryBuilder qb = userInfoDao.queryBuilder();

        List<WhereCondition> conditions = new ArrayList<>();
        if(mOldConditions != null){
            conditions = mOldConditions;
        }

        Query<UserInfo> query = null;
        if(conditions.isEmpty()){
            query = userInfoDao.queryBuilder().build();
        } else {
            if(conditions.size() == 1){
                query = userInfoDao.queryBuilder().where(conditions.get(0)).build();
            } else {
                query = userInfoDao.queryBuilder().where(conditions.get(0), conditions.get(1)).build();
            }
        }
        LazyList  data = (query == null?null:query.listLazy());
        mAdapter.bindData(data);
        mAdapter.notifyDataSetChanged();
    }

    private String getResultStr(boolean result){
        return result ? "成功" : "失败";
    }

    private String getUserInfoStr(UserInfo info){
        if(info == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<br> UserId: " + info.getUserId());
        sb.append("<br> UserName: " + info.getUserName());
        sb.append("<br> UserPwd: " + info.getUserPwd());
        return sb.toString();
    }

    private String formatTime(long time){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(time));
    }

}
