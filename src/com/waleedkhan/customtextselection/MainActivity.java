/**
 * @author Waleed Khan
 */

package com.waleedkhan.customtextselection;

import com.waleedkhan.customtextselection.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.TextView.BufferType;



public class MainActivity extends Activity {

	private TextView tv;

	private final String sampleText = "Looking back on those incidents, he was always appalled by the memory of his passivity, " +
			"hard though it was to see what else he could have done. He could have refused to pay for the " +
			"gravy damage to his room, could have refused to change his shoes, could have refused to kneel to " +
			"supplicate for his B.A. He had preferred to surrender and get the degree. The memory of that " +
			"surrender made him more stubborn , less willing to compromise, to make an accommodation " +
			"with injustice, no matter how persuasive the reasons. Looking back on those incidents, he was always appalled by the memory of his passivity, " +
			"hard though it was to see what else he could have done. He could have refused to pay for the " +
			"gravy damage to his room, could have refused to change his shoes, could have refused to kneel to";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		tv = (TextView) findViewById(R.id.text);
		tv.setText(sampleText , BufferType.SPANNABLE);
		tv.setTextIsSelectable(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
