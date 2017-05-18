package com.car.contractcar.myapplication.keepcar.view.csdialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.car.contractcar.myapplication.R;

public class CSProgressDialog extends Dialog {

	private Context context = null;

	private TextView msgText;
	private TextView titleText;
	private ProgressBar progressBar;

	public CSProgressDialog(Context context) {
		this(context, R.style.CustomDialog);
		this.context = context;

	}

	public CSProgressDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {

		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public CSProgressDialog(Context context, int theme) {

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
		View view = LayoutInflater.from(context).inflate(R.layout.showdialog_progress, null);
		titleText = (TextView) view.findViewById(R.id.title_text);
		msgText = (TextView) view.findViewById(R.id.msg_text); 
		progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
		setContentView(view);
		
	}

	/**
	 * ������ʾ���������ݣ�����string��
	 * @param msg
	 */
	public void setMsg(String msg) {

		if (null != msgText) {
			msgText.setText(msg);
		}
	}

	/**
	 * ������ʾ���������ݣ�����resId��
	 * @param msg
	 */
	public void setMsg(int resId) {

		if (null != msgText) {
			msgText.setText(context.getString(resId));
		}
	}

	/**
	 * ������ʾ�ı��⣨����string��
	 * @param msg
	 */
	public void setTitle(String t) {

		if (null != titleText) {
			titleText.setText(t);
		}
	}

	/**
	 * ������ʾ�ı��⣨����resId��
	 * @param msg
	 */
	public void setTitle(int resId) {

		if (null != titleText) {
			titleText.setText(context.getString(resId));
		}
	}

}