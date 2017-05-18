package com.car.contractcar.myapplication.keepcar.view.csdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;

public class CSOKCancelDialog extends Dialog {

	private Context context = null;

	private TextView msgText;
	private TextView titleText;
	private Button okBtn;
	private Button cancelBtn;

	public static CSOKCancelDialog createBuilder(Context context) {
		return new CSOKCancelDialog(context);
	}

	public CSOKCancelDialog(Context context) {
		this(context, R.style.CustomDialog);
		this.context = context;

	}

	public CSOKCancelDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {

		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public CSOKCancelDialog(Context context, int theme) {

		super(context, theme);
		this.context = context;
		setDialogContentView();
	}

	/**
	 * ����dialog�����view
	 */
	private void setDialogContentView()
	{

		// �����Լ�����Ĳ���
		View view = LayoutInflater.from(context).inflate(R.layout.showdialog_ok_cancel, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		msgText = (TextView) view.findViewById(R.id.msg_text);
		okBtn = (Button) view.findViewById(R.id.ok_btn);
		cancelBtn = (Button) view.findViewById(R.id.cancel_btn);
		cancelBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				CSOKCancelDialog.this.dismiss();
			}
		});
		okBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				CSOKCancelDialog.this.dismiss();
			}
		});
		setContentView(view);

	}

	/**
	 * ����ok��ť����
	 */
	public void setOKOnClickListener(View.OnClickListener listener){
		okBtn.setOnClickListener(listener);
	}

	/**
	 * ������ʾ���������ݣ�����string��
	 * @param msg
	 */
	public CSOKCancelDialog setMsg(String msg) {

		if (null != msgText) {
			msgText.setText(msg);
		}
		return this;
	}

	/**
	 * ������ʾ���������ݣ�����resId��
	 * @param
	 */
	public CSOKCancelDialog setMsg(int resId) {

		if (null != msgText) {
			msgText.setText(context.getString(resId));
		}
		return this;
	}

	/**
	 * ������ʾ�ı��⣨����string��
	 * @param
	 */
	public CSOKCancelDialog setAlertTitle(String t) {

		if (null != titleText) {
			titleText.setText(t);
		}
		return this;
	}

	/**
	 * ������ʾ�ı��⣨����resId��
	 * @param msg
	 */
	public CSOKCancelDialog setAlertTitle(int resId) {

		if (null != titleText) {
			titleText.setText(context.getString(resId));
		}

		return this;

	}

}