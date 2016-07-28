package com.frewen.freepickerview.pickerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frewen.freepickerview.R;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class TimePickerView extends BasePickerView implements View.OnClickListener {

    private WheelTime mWheelTime;
    private TextView mTVTitle;
    private View mBtnSubmit, mBtnCancel;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private OnTimeSelectListener mTimeSelectListener;

    public enum Type {
        ALL, YEAR_MONTH_DAY, HOURS_MINS, MONTH_DAY_HOUR_MIN, YEAR_MONTH
    }// 四种选择模式，年月日时分，年月日，时分，月日时分

    public TimePickerView(Context context, Type modelType) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_pickerview_time, mContentContainer);

        // -----确定和取消按钮
        mBtnSubmit = findViewById(R.id.btnSubmit);
        mBtnSubmit.setTag(TAG_SUBMIT);
        mBtnCancel = findViewById(R.id.btnCancel);
        mBtnCancel.setTag(TAG_CANCEL);

        //顶部标题
        mTVTitle = (TextView) findViewById(R.id.tvTitle);


        // ----时间转轮
        final View timePickerView = findViewById(R.id.timepicker);
        mWheelTime = new WheelTime(timePickerView, modelType);


        mBtnSubmit.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        //默认选中当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置选中时间
     *
     * @param date
     */
    public void setTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date == null)
            calendar.setTimeInMillis(System.currentTimeMillis());
        else
            calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        mWheelTime.setPicker(year, month, day, hours, minute);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        mWheelTime.setCyclic(cyclic);
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnSubmit) {
            if (mTimeSelectListener != null) {
                try {
                    Date date = WheelTime.dateFormat.parse(mWheelTime.getTime());
                    mTimeSelectListener.onTimeSelect(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            dismiss();
        } else if (i == R.id.btnCancel) {
            dismiss();
        }
    }


    public interface OnTimeSelectListener {
        public void onTimeSelect(Date date);
    }

    public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
        this.mTimeSelectListener = timeSelectListener;
    }
}
