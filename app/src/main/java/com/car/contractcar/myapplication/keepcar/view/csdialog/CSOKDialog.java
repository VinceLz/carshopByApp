package com.car.contractcar.myapplication.keepcar.view.csdialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;

public class CSOKDialog extends Dialog {

    private Context context = null;

    private TextView msgText;
    private TextView titleText;
    private Button okBtn;

    public static CSOKDialog createBuilder(Context context) {
        return new CSOKDialog(context);
    }

    public CSOKDialog(Context context) {
        this(context, R.style.CustomDialog);
        this.context = context;

    }

    public CSOKDialog(Context context, boolean cancelable,
                      OnCancelListener cancelListener) {

        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public CSOKDialog(Context context, int theme) {

        super(context, theme);
        this.context = context;
        setDialogContentView();
    }

    /**
     * ����dialog�����view
     */
    private void setDialogContentView() {
        // �����Լ�����Ĳ���
        View view = LayoutInflater.from(context).inflate(R.layout.showdialog_ok, null);
        titleText = (TextView) view.findViewById(R.id.title_text);
        msgText = (TextView) view.findViewById(R.id.msg_text);
        okBtn = (Button) view.findViewById(R.id.ok_btn);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CSOKDialog.this.dismiss();
                CSOKDialog.this.closeOptionsMenu();
            }
        });
        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM); //设置对话框在界面底部显示
        setContentView(view);
    }

    @Override
    public void show() {
        super.show();
        setCanceledOnTouchOutside(true);
    }

    /**
     * ������ʾ���������ݣ�����string��
     *
     * @param msg
     */
    public CSOKDialog setMsg(String msg) {

        if (null != msgText) {
            msgText.setText(msg);
        }
        return this;
    }

    /**
     * ������ʾ���������ݣ�����resId��
     *
     * @param
     */
    public CSOKDialog setMsg(int resId) {

        if (null != msgText) {
            msgText.setText(context.getString(resId));
        }
        return this;
    }

    /**
     * ������ʾ�ı��⣨����string��
     *
     * @param
     */
    public CSOKDialog setAlertTitle(String t) {

        if (null != titleText) {
            titleText.setText(t);
        }
        return this;
    }

    /**
     * ������ʾ�ı��⣨����resId��
     *
     * @param
     */
    public CSOKDialog setAlertTitle(int resId) {

        if (null != titleText) {
            titleText.setText(context.getString(resId));
        }

        return this;

    }

}