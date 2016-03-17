package com.bulbulhossen.sharetext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView tv = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.textview);
		
		// Register a callback when user selects text
		tv.setCustomSelectionActionModeCallback(new Callback() {

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch(item.getItemId()){
					case R.id.menu_item_share:
						String selectedText = getSelectedText();
						shareIntent(selectedText);
						return true;
					default:
						break;
				}
				return false;
			}

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				getMenuInflater().inflate(R.menu.share_menu, menu);
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				/**
				 * Use the following code if you want to remove the default icons (select all, cut or copy). 
				 */
				// Remove the "select all" option
	            //menu.removeItem(android.R.id.selectAll);
	            // Remove the "cut" option
	            //menu.removeItem(android.R.id.cut);
	            // Remove the "copy all" option
	            //menu.removeItem(android.R.id.copy);
				return false;
			}
			
		});
	}
	
	/**
	 * Returns the selected text
	 * @return String selectedText
	 */
	private String getSelectedText() {
		String selectedText = "";
		int min = 0;
        int max = tv.getText().length();
        if (tv.isFocused()) {
            final int textStartIndex = tv.getSelectionStart();
            final int textEndIndex = tv.getSelectionEnd();

            min = Math.max(0, Math.min(textStartIndex, textEndIndex));
            max = Math.max(0, Math.max(textStartIndex, textEndIndex));
            selectedText = tv.getText().subSequence(min, max).toString().trim();
        }
        return selectedText;
        // Perform your
	}
	
	/**
	 * Opens a share menu
	 * @param String selectedText
	 */
	private void shareIntent(String Text) {
		if(!Text.equals("")) {
			Intent shareIntent = new Intent(Intent.ACTION_SEND);
	        shareIntent.setType("text/plain");
	        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject here");
	        shareIntent.putExtra(Intent.EXTRA_TEXT, Text);
	        startActivity(Intent.createChooser(shareIntent, "Share Via"));
		} else {
			Toast.makeText(this, R.string.empty_text, Toast.LENGTH_LONG).show();
		}
	}
}
