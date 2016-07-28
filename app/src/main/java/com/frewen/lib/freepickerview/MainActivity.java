package com.frewen.lib.freepickerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.frewen.freepickerview.model.ProvinceModel;
import com.frewen.freepickerview.pickerview.OptionsPickerView;
import com.frewen.freepickerview.pickerview.ProvincesPickerView;
import com.frewen.freepickerview.pickerview.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button mBtnTime;
    private Button mBtnPrinces;
    private Button mBtnOptions;

    private View vMasker;

    private TimePickerView mPickerViewTime;
    private ProvincesPickerView mPickerViewProvinces;
    private OptionsPickerView mPickerViewOptions;
    private List<ProvinceModel> mProvinceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initTimeOptions();

        initPrincesDatas();

        initCallBackListener();
    }

    private void initView() {
        vMasker = findViewById(R.id.vMasker);
        mBtnTime = (Button) findViewById(R.id.tvTime);
        mBtnPrinces = (Button) findViewById(R.id.tvPrinces);
        mBtnOptions = (Button) findViewById(R.id.tvOptions);
    }

    private void initPrincesDatas() {
        //选项选择器
        mPickerViewProvinces = new ProvincesPickerView(this);
        //三级联动数据.不传则使用默认的数据
        //mPickerViewProvinces.setPicker(mProvinceList);
        mPickerViewProvinces.setPicker();
        //设置选择的三级单位
        //mPickerViewProvinces.setLabels("省", "市", "区");
        mPickerViewProvinces.setTitle("选择城市");
        //设置是否循环
        mPickerViewProvinces.setCyclic(false, true, true);
        //设置默认选中的三级项目
        mPickerViewProvinces.setSelectOptions(0, 0, 0);
        mProvinceData = mPickerViewProvinces.getProvincesData();
        //监听确定选择按钮
        mPickerViewProvinces.setOnoptionsSelectListener(new ProvincesPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                //返回的分别是三个级别的选中位置
                String tx = mProvinceData.get(options1).getName()
                        + mProvinceData.get(options1).getCityList().get(option2).getName()
                        + mProvinceData.get(options1).getCityList().get(option2).getDistrictList().get(options3).getName();
                mBtnPrinces.setText(tx);
                vMasker.setVisibility(View.GONE);
            }
        });
    }

    private void initTimeOptions() {
        //时间选择器
        mPickerViewTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        mPickerViewTime.setTime(new Date());
        mPickerViewTime.setCyclic(false);
        mPickerViewTime.setCancelable(true);
        //时间选择后回调
        mPickerViewTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Toast.makeText(MainActivity.this, "您选中的时间：" + format.format(date), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initCallBackListener() {
        //弹出时间选择器
        mBtnTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mPickerViewTime.show();
            }
        });

        //点击弹出选项选择器
        mBtnPrinces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickerViewProvinces.show();
            }
        });
    }
}
