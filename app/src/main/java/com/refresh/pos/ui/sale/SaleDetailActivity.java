package com.refresh.pos.ui.sale;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.refresh.pos.R;
import com.refresh.pos.domain.inventory.LineItem;
import com.refresh.pos.domain.sale.Sale;
import com.refresh.pos.domain.sale.SaleLedger;
import com.refresh.pos.techicalservices.NoDaoSetException;

public class SaleDetailActivity extends Activity{
	SharedPreferences sharedPreferences;
	private TextView totalBox;
	private TextView dateBox;
	private ListView lineitemListView;
	private List<Map<String, String>> lineitemList;
	private Sale sale;
	private int saleId;
	private SaleLedger saleLedger;

	private TextView customerTV;
	private TextView phnTV;
	private EditText customerET;
	private EditText phnET;
	Button customerbtn,phnbtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		try {
			saleLedger = SaleLedger.getInstance();
		} catch (NoDaoSetException e) {
			e.printStackTrace();
		}

		saleId = Integer.parseInt(getIntent().getStringExtra("id"));
		sale = saleLedger.getSaleById(saleId);

		initUI(savedInstanceState);
	}



	@SuppressLint("NewApi")
	private void initiateActionBar() {
		if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setTitle(getResources().getString(R.string.sale));
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33B5E5")));
		}
	}

	private void initUI(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_saledetail);

		initiateActionBar();

		totalBox = (TextView) findViewById(R.id.totalBox);
		dateBox = (TextView) findViewById(R.id.dateBox);
		lineitemListView = (ListView) findViewById(R.id.lineitemList);

		customerTV=(TextView)findViewById(R.id.customerTV);
		phnTV=(TextView)findViewById(R.id.phnTV);
		customerET=(EditText)findViewById(R.id.customerET);
		phnET=(EditText)findViewById(R.id.phnET);
		customerbtn=(Button)findViewById(R.id.customerbtn);
		phnbtn=(Button)findViewById(R.id.phnbtn);



		//SharedPreferences.Editor editor=sharedPreferences.edit();

	}

	private void showList(List<LineItem> list) {
		lineitemList = new ArrayList<Map<String, String>>();
		for(LineItem line : list) {
			lineitemList.add(line.toMap());
		}

		SimpleAdapter sAdap = new SimpleAdapter(SaleDetailActivity.this, lineitemList,
				R.layout.listview_lineitem, new String[]{"name","quantity","price"}, new int[] {R.id.name,R.id.quantity,R.id.price});
		lineitemListView.setAdapter(sAdap);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	public void update() {
		totalBox.setText(sale.getTotal() + "");
		dateBox.setText(sale.getEndTime() + "");
		showList(sale.getAllLineItem());
	}
	public void customerAndPhn (View view){




		String s=customerET.getText().toString();
		String s1=phnET.getText().toString();
		customerTV.setText(s);
		phnTV.setText(s1);

	}
	public void print(View view) {
		Toast.makeText(this, "No Device", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onResume() {
		super.onResume();
		update();
	}
}
