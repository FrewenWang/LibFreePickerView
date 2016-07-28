package com.frewen.freepickerview.pickerview;

import android.content.Context;
import android.content.res.AssetManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.frewen.freepickerview.R;
import com.frewen.freepickerview.model.ProvinceModel;
import com.frewen.freepickerview.xmlparser.XmlParserHandler;

import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Sai on 15/11/22.
 */
public class ProvincesPickerView<T> extends BasePickerView implements View.OnClickListener {

    WheelPrinces wheelPrinces;
    private View btnSubmit, btnCancel;
    private TextView tvTitle;
    private OnOptionsSelectListener optionsSelectListener;
    private OnItemSelectedListner mItemSelectListener;

    private static final String TAG_SUBMIT = "submit";
    private static final String TAG_CANCEL = "cancel";
    private List<ProvinceModel> mProvincesData;


    public ProvincesPickerView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.layout_pickerview_options, mContentContainer);
        // -----确定和取消按钮
        btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setTag(TAG_SUBMIT);
        btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setTag(TAG_CANCEL);
        btnSubmit.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        //顶部标题
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        // ----转轮
        final View optionspicker = findViewById(R.id.optionspicker);
        wheelPrinces = new WheelPrinces(optionspicker);
    }


    public void setPicker() {

        AssetManager asset = mContext.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            mProvincesData = handler.getDataList();
            wheelPrinces.setPicker(handler.getDataList());

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }

    }

    public void setPicker(List<ProvinceModel> optionsItems) {
        wheelPrinces.setPicker(optionsItems);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     */
    public void setSelectOptions(int option1) {
        wheelPrinces.setCurrentItems(option1, 0, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     */
    public void setSelectOptions(int option1, int option2) {
        wheelPrinces.setCurrentItems(option1, option2, 0);
    }

    /**
     * 设置选中的item位置
     *
     * @param option1
     * @param option2
     * @param option3
     */
    public void setSelectOptions(int option1, int option2, int option3) {
        wheelPrinces.setCurrentItems(option1, option2, option3);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     */
    public void setLabels(String label1) {
        wheelPrinces.setLabels(label1, null, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     */
    public void setLabels(String label1, String label2) {
        wheelPrinces.setLabels(label1, label2, null);
    }

    /**
     * 设置选项的单位
     *
     * @param label1
     * @param label2
     * @param label3
     */
    public void setLabels(String label1, String label2, String label3) {
        wheelPrinces.setLabels(label1, label2, label3);
    }

    /**
     * 设置是否循环滚动
     *
     * @param cyclic
     */
    public void setCyclic(boolean cyclic) {
        wheelPrinces.setCyclic(cyclic);
    }

    public void setCyclic(boolean cyclic1, boolean cyclic2, boolean cyclic3) {
        wheelPrinces.setCyclic(cyclic1, cyclic2, cyclic3);
    }


    @Override
    public void onClick(View v) {
        String tag = (String) v.getTag();
        if (tag.equals(TAG_CANCEL)) {
            dismiss();
            return;
        } else {
            int[] optionsCurrentItems = wheelPrinces.getCurrentItems();
            if (optionsSelectListener != null) {

                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            if (optionsSelectListener != null) {

                optionsSelectListener.onOptionsSelect(optionsCurrentItems[0], optionsCurrentItems[1], optionsCurrentItems[2]);
            }
            dismiss();
            return;
        }
    }

    public List<ProvinceModel> getProvincesData() {
        return mProvincesData;
    }


    public interface OnOptionsSelectListener {
        public void onOptionsSelect(int options1, int option2, int options3);
    }

    public interface OnItemSelectedListner {
        public void onOptionItemSelect(String options1, String option2, String options3);
    }

    public void setOnoptionsSelectListener(
            OnOptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    public void setItemSelectListener(
            OnItemSelectedListner listener) {
        this.mItemSelectListener = listener;
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }
}
