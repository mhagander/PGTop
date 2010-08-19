package org.postgresql.top;

import android.app.Activity;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class PGAddDatabase extends Activity implements OnClickListener {
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.save:
			final EditText pghostEditText = (EditText) findViewById(R.id.pghost);
			final EditText pgportEditText = (EditText) findViewById(R.id.pgport);
			final EditText pgdatabaseEditText = (EditText) findViewById(R.id.pgdatabase);
			final EditText pguserEditText = (EditText) findViewById(R.id.pguser);
			final EditText pgpasswordEditText = (EditText) findViewById(R.id.pgpassword);
			final CheckBox sslCheckBox = (CheckBox) findViewById(R.id.use_ssl);

			PGConnectionOpenHelper openHelper = new PGConnectionOpenHelper(
					getApplicationContext());
			SQLiteDatabase db = openHelper.getWritableDatabase();
			try {
				db
						.execSQL("INSERT INTO "
								+ PGConnectionOpenHelper.TABLE_NAME
								+ " (host, port, database, user, password, ssl) VALUES ('"
								+ pghostEditText.getText().toString() + "', '"
								+ pgportEditText.getText().toString() + "', '"
								+ pgdatabaseEditText.getText().toString()
								+ "', '" + pguserEditText.getText().toString()
								+ "', '"
								+ pgpasswordEditText.getText().toString()
								+ "', " + (sslCheckBox.isChecked() ? "1" : "0")
								+ ");");
				db.close();
				finish();
			} catch (SQLiteConstraintException e) {
				Toast.makeText(
						PGAddDatabase.this,
						"These database connection parameters are already saved. "
								+ "Remove and add again to change a password.",
						Toast.LENGTH_LONG).show();
				db.close();
			}
			break;
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.db_add);

		final Button activityButton = (Button) findViewById(R.id.save);
		activityButton.setOnClickListener(this);
	}
}