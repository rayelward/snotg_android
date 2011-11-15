package edu.depaul.snotg_android.Activity;

import edu.depaul.snotg_android.R;
import edu.depaul.snotg_android.SnotgAndroidConstants;
import edu.depaul.snotg_android.Auth.AppInfo;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class AccountList extends ListActivity {
	protected AccountManager accountManager;
	protected Intent intent;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = AccountManager.get(getApplicationContext());
        Account[] accounts = accountManager.getAccountsByType("com.google");
        this.setListAdapter(new ArrayAdapter<Account>(this, R.layout.list_item, accounts));        
    }

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Account account = (Account)getListView().getItemAtPosition(position);
		String username = null;
		if (account != null)
			username = account.name.substring(0, account.name.indexOf("@"));
		
		Intent intent = new Intent(this, AppInfo.class);
		intent.putExtra("account", account);
		//intent.putExtra("username", username);
		SnotgAndroidConstants.STATE_USERNAME = username;
		
		startActivity(intent);
	}
}