package com.mais.leantasks.sql;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

/**
 * General class for all tables. Each table is public members of this class so we can use it like that:
 * Table.table_name.operation
 * 
 * e.g. Table.tasks.selectAll()
 * 
 * @author Adam
 *
 */
public class Table {
		protected SQLiteDatabase database;
		protected DBHelper dbHelper;
		public Tasks tasks;
		public Users users;
		
		public Table(){}
		
		public Table(Context context) {
			dbHelper = new DBHelper(context);
			open();
			tasks = new Tasks(database);
			users = new Users(database);
		}

		public void open() throws SQLException {
			database = dbHelper.getWritableDatabase();
		}

		public void close() {
			dbHelper.close();
		}

		
}
