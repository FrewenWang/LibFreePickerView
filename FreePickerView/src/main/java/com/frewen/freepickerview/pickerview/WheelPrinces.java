package com.frewen.freepickerview.pickerview;

import android.view.View;

import com.frewen.freepickerview.R;
import com.frewen.freepickerview.WheelView;
import com.frewen.freepickerview.adapter.ArrayWheelAdapter;
import com.frewen.freepickerview.listener.ItemSelectedListener;
import com.frewen.freepickerview.model.CityModel;
import com.frewen.freepickerview.model.DistrictModel;
import com.frewen.freepickerview.model.ProvinceModel;

import java.util.ArrayList;
import java.util.List;

public class WheelPrinces<T> {
    private static final String TAG = WheelPrinces.class.getSimpleName();
    private View view;

    private WheelView mProvicesView;
    private WheelView mCitiesView;
    private WheelView mDistricsView;


    private boolean mLinkage = true;

    private ItemSelectedListener mOption1Listener;
    private ItemSelectedListener mOption2Listener;

    /**
     * 省市区三级联动数据结构
     */
    private List<ProvinceModel> mOptionsPrinces;
    private List<CityModel> mOptionsCities;
    private List<DistrictModel> mOptionsDistricts;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public WheelPrinces(View view) {
        super();
        this.view = view;
        setView(view);
    }

    public void setPicker(List<ProvinceModel> optionsPrinces) {

        initData(optionsPrinces);

        initCallBackListener();

    }

    private void initData(List<ProvinceModel> optionsPrinces) {
        //初始化三级联动数据
        this.mOptionsPrinces = optionsPrinces;


        int len = ArrayWheelAdapter.DEFAULT_LENGTH;

        if (null != mOptionsPrinces && !mOptionsPrinces.isEmpty()) {
            this.mOptionsCities = optionsPrinces.get(0).getCityList();
        } else {

        }

        if (null != mOptionsCities && !mOptionsCities.isEmpty()) {
            this.mOptionsDistricts = mOptionsCities.get(0).getDistrictList();
        } else {
            //如果二级城市列表就为空的话，WheelView的宽度就设成12（最宽）
            len = 12;
        }

        if (null == mOptionsDistricts || mOptionsDistricts.isEmpty()) {
            //如果三级城市列表就为空的话，WheelView的宽度就设成8（最宽）
            len = 8;
        }

        // 初始化省份显示
        mProvicesView = (WheelView) view.findViewById(R.id.options1);
        mProvicesView.setAdapter(new ArrayWheelAdapter(mOptionsPrinces, len));// 设置显示数据
        mProvicesView.setCurrentItem(0);// 初始化时显示的数据

        //初始化省份里面的城市列表显示
        mCitiesView = (WheelView) view.findViewById(R.id.options2);
        if (null != mOptionsCities) {
            mCitiesView.setAdapter(new ArrayWheelAdapter(mOptionsCities));// 设置显示数据
        }
        mCitiesView.setCurrentItem(mCitiesView.getCurrentItem());// 初始化时显示的数据

        //初始化城市里面的各个区的显示
        mDistricsView = (WheelView) view.findViewById(R.id.options3);
        if (null != mOptionsDistricts) {
            mDistricsView.setAdapter(new ArrayWheelAdapter(mOptionsDistricts));// 设置显示数据
        }
        mDistricsView.setCurrentItem(mDistricsView.getCurrentItem());// 初始化时显示的数据
    }

    private void initCallBackListener() {
        // 联动监听器
        mOption1Listener = new ItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                int opt2SelectIndex = 0;
                //得到当前位置的
                mOptionsCities = mOptionsPrinces.get(index).getCityList();

                if (mOptionsCities != null) {
                    opt2SelectIndex = mCitiesView.getCurrentItem();//上一个opt2的选中位置
                    //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt2SelectIndex = opt2SelectIndex >= mOptionsCities.size() - 1 ? mOptionsCities.size() - 1 : opt2SelectIndex;

                    mCitiesView.setAdapter(new ArrayWheelAdapter(mOptionsCities));
                    mCitiesView.setCurrentItem(opt2SelectIndex);
                }

                if (null != mOption2Listener) {
                    mOption2Listener.onItemSelected(opt2SelectIndex);
                }
            }
        };

        mOption2Listener = new ItemSelectedListener() {

            @Override
            public void onItemSelected(int index) {
                int opt3SelectIndex = 0;
                //得到当前位置的
                mOptionsDistricts = mOptionsCities.get(index).getDistrictList();

                if (mOptionsDistricts != null) {
                    opt3SelectIndex = mDistricsView.getCurrentItem();//上一个opt2的选中位置
                    //新opt2的位置，判断如果旧位置没有超过数据范围，则沿用旧位置，否则选中最后一项
                    opt3SelectIndex = opt3SelectIndex >= mOptionsDistricts.size() - 1 ? mOptionsDistricts.size() - 1 : opt3SelectIndex;

                    mDistricsView.setAdapter(new ArrayWheelAdapter(mOptionsDistricts));
                    mDistricsView.setCurrentItem(opt3SelectIndex);
                }
            }
        };
        //		// 添加联动监听
        if (mOptionsCities != null && mLinkage) {
            mProvicesView.setOnItemSelectedListener(mOption1Listener);
        }
        if (mOptionsDistricts != null && mLinkage) {
            mCitiesView.setOnItemSelectedListener(mOption2Listener);
        }

    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        if (label1 != null)
            mProvicesView.setLabel(label1);
        if (label2 != null)
            mCitiesView.setLabel(label2);
        if (label3 != null)
            mDistricsView.setLabel(label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mProvicesView.setCyclic(cyclic);
        mCitiesView.setCyclic(cyclic);
        mDistricsView.setCyclic(cyclic);
    }

    /**
     * 分别设置第一二三级是否循环滚动
     *
     * @param cyclic1,cyclic2,cyclic3
     */
    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        mProvicesView.setCyclic(cyclic1);
        mCitiesView.setCyclic(cyclic2);
        mDistricsView.setCyclic(cyclic3);
    }

    /**
     * 设置第二级是否循环滚动
     *
     * @param cyclic
     */
    public void setOption2Cyclic(boolean cyclic) {
        mCitiesView.setCyclic(cyclic);
    }

    /**
     * 设置第三级是否循环滚动
     *
     * @param cyclic
     */
    public void setOption3Cyclic(boolean cyclic) {
        mDistricsView.setCyclic(cyclic);
    }

    /**
     * 返回当前选中的结果对应的位置数组 因为支持三级联动效果，分三个级别索引，0，1，2
     *
     * @return
     */
    public int[] getCurrentItems() {
        int[] currentItems = new int[3];
        currentItems[0] = mProvicesView.getCurrentItem();
        currentItems[1] = mCitiesView.getCurrentItem();
        currentItems[2] = mDistricsView.getCurrentItem();
        return currentItems;
    }

    public void setCurrentItems(int option1, int option2, int option3) {
        if (mLinkage) {
            itemSelected(option1, option2, option3);
        }
        mProvicesView.setCurrentItem(option1);
        mCitiesView.setCurrentItem(option2);
        mDistricsView.setCurrentItem(option3);
    }

    private void itemSelected(int opt1Select, int opt2Select, int opt3Select) {

        if (null != mOptionsPrinces.get(opt1Select).getCityList()) {
            mCitiesView.setAdapter(new ArrayWheelAdapter((ArrayList) mOptionsPrinces.get(opt1Select).getCityList()));// 设置显示数据
            mCitiesView.setCurrentItem(opt2Select);
        }


        if (null != mOptionsPrinces.get(0).getCityList().get(0).getDistrictList()) {
            mDistricsView.setAdapter(new ArrayWheelAdapter((ArrayList) mOptionsPrinces.get(opt1Select).getCityList().get(opt2Select).getDistrictList()));// 设置显示数据
            mDistricsView.setCurrentItem(opt3Select);
        }

    }


}
