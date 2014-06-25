/**
 *
 *	An extended TextView class that supports text selection and copying with arrow keys. 
 *
 *  @author Waleed Khan
 * 
 */

package com.waleedkhan.customtextselection;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.Selection;
import android.text.Spannable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomTextView extends TextView {

	private final int buttonBoxID = 420;
	private boolean isEnabled = true;
	private RelativeLayout buttonBox;

	public CustomTextView(Context context) {
		super(context);
		initialize(context);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	private void initialize(final Context context)
	{
		ImageButton startBack = new ImageButton(context);
		startBack.setImageResource(R.drawable.ic_menu_back);
		ImageButton startForward = new ImageButton(context);
		startForward.setImageResource(R.drawable.ic_menu_forward);

		Button copy = new Button(context);
		copy.setText("COPY");

		ImageButton endBack = new ImageButton(context);
		endBack.setImageResource(R.drawable.ic_menu_back);
		ImageButton endForward = new ImageButton(context);
		endForward.setImageResource(R.drawable.ic_menu_forward);

		/**
		 * OnTouch listeners for buttons to repeat action while pressed.
		 */
		startBack.setOnTouchListener(new OnTouchListener() {
			private Handler mHandler;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (Selection.getSelectionStart((Spannable) getText()) > 0)
					{
						Selection.setSelection((Spannable) getText(), Selection.getSelectionStart((Spannable) getText())-1, Selection.getSelectionEnd((Spannable) getText()));
					}
					if (mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(mAction, 400);
					break;
				case MotionEvent.ACTION_UP:
					if (mHandler == null) return true;
					mHandler.removeCallbacks(mAction);
					mHandler = null;
					break;
				}
				return false;
			}
			Runnable mAction = new Runnable() 
			{
				@Override public void run() {
					if (Selection.getSelectionStart((Spannable) getText()) > 0)
					{
						Selection.setSelection((Spannable) getText(), Selection.getSelectionStart((Spannable) getText())-1, Selection.getSelectionEnd((Spannable) getText()));
					}
					mHandler.postDelayed(this, 70);
				}
			};
		});

		startForward.setOnTouchListener(new OnTouchListener() {
			private Handler mHandler;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Selection.setSelection((Spannable) getText(), Selection.getSelectionStart((Spannable) getText())+1, Selection.getSelectionEnd((Spannable) getText()));
					if (mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(mAction, 400);
					break;
				case MotionEvent.ACTION_UP:
					if (mHandler == null) return true;
					mHandler.removeCallbacks(mAction);
					mHandler = null;
					break;
				}
				return false;
			}
			Runnable mAction = new Runnable() 
			{
				@Override public void run() {
					Selection.setSelection((Spannable) getText(), Selection.getSelectionStart((Spannable) getText())+1, Selection.getSelectionEnd((Spannable) getText()));
					mHandler.postDelayed(this, 70);
				}
			};
		});

		copy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String selectedString = getText().toString().substring(getSelectionStart(), getSelectionEnd());
				ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
				ClipData clip = ClipData.newPlainText("Copied Text", selectedString);
				clipboard.setPrimaryClip(clip);
				clearFocus();
			}
		});

		endBack.setOnTouchListener(new OnTouchListener() {
			private Handler mHandler;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Selection.extendLeft((Spannable) getText(), getLayout());
					if (mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(mAction, 400);
					break;
				case MotionEvent.ACTION_UP:
					if (mHandler == null) return true;
					mHandler.removeCallbacks(mAction);
					mHandler = null;
					break;
				}
				return false;
			}
			Runnable mAction = new Runnable() 
			{
				@Override public void run() {
					Selection.extendLeft((Spannable) getText(), getLayout());
					mHandler.postDelayed(this, 70);
				}
			};
		});

		endForward.setOnTouchListener(new OnTouchListener() {
			private Handler mHandler;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Selection.extendRight((Spannable) getText(), getLayout());
					if (mHandler != null) return true;
					mHandler = new Handler();
					mHandler.postDelayed(mAction, 400);
					break;
				case MotionEvent.ACTION_UP:
					if (mHandler == null) return true;
					mHandler.removeCallbacks(mAction);
					mHandler = null;
					break;
				}
				return false;
			}
			Runnable mAction = new Runnable() 
			{
				@Override public void run() {
					Selection.extendRight((Spannable) getText(), getLayout());
					mHandler.postDelayed(this, 70);
				}
			};
		});

		/**
		 * Add buttons to layout.
		 */

		LinearLayout lin = new LinearLayout(getContext());
		LinearLayout.LayoutParams linParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		lin.setLayoutParams(linParams);
		lin.addView(startBack);
		lin.addView(startForward);
		lin.addView(copy);
		lin.addView(endBack);
		lin.addView(endForward);

		buttonBox = new RelativeLayout(getContext());
		RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		relParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		relParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		buttonBox.setLayoutParams(relParams);
		buttonBox.addView(lin);
		buttonBox.setId(buttonBoxID);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		//////////////////////// TO REMOVE ///////////////////////
		InputMethodManager inputMethodManager = (InputMethodManager)  getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(((Activity) getContext()).getCurrentFocus().getWindowToken(), 0);
		/////////////////////////////////////////////////////////


		/**
		 * Show buttons on text selection.
		 */
		
		if (hasSelection() && isEnabled)
		{
			if (((ViewGroup) ((ViewGroup)getRootView().findViewById(android.R.id.content)).getChildAt(0)).findViewById(buttonBoxID) == null)
				((ViewGroup) ((ViewGroup)getRootView().findViewById(android.R.id.content)).getChildAt(0)).addView(buttonBox);
			buttonBox.setVisibility(VISIBLE);
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!hasSelection() || !isEnabled) buttonBox.setVisibility(INVISIBLE);
	}

	public void enable(boolean value)
	{
		this.isEnabled = value;
	}
}

