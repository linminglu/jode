package com.tools.payhelper.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {
	private SQLiteDatabase db;
	private DBHelper helper;
	public DBManager(Context context){
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }
	
	 public void addQrCode(QrCodeBean qrCodeBean) {
        db.beginTransaction();// 开始事务
        try {
        	String dt=System.currentTimeMillis()/1000+"";
            db.execSQL("INSERT INTO qrcode VALUES(null,?,?,?,?,?)", new Object[] { qrCodeBean.getMoney(), qrCodeBean.getMark(), qrCodeBean.getType(), qrCodeBean.getPayurl(), dt });
            db.setTransactionSuccessful();// 事务成功
        } finally {
            db.endTransaction();// 结束事务
        }
    }
	public void addOrder(OrderBean ordereBean) {
		 db.beginTransaction();// 开始事务
		 try {
			 String dt=System.currentTimeMillis()/1000+"";
			 db.execSQL("INSERT INTO payorder VALUES(null,?,?,?,?,?,?,?)", new Object[] { ordereBean.getMoney(), ordereBean.getMark(), ordereBean.getType(), ordereBean.getNo(), dt ,ordereBean.getResult() , ordereBean.getTime() });
			 db.setTransactionSuccessful();// 事务成功
		 } finally {
			 db.endTransaction();// 结束事务
		 }
	 }
	
	public void updateOrder(String no,String result) {
		 db.beginTransaction();// 开始事务
		 try {
			 db.execSQL("UPDATE payorder SET result=?,time=time+1 WHERE tradeno=?", new Object[] {result, no});
			 db.setTransactionSuccessful();// 事务成功
		 } finally {
			 db.endTransaction();// 结束事务
		 }
	 }
	public void addTradeNo(String paramString1, String paramString2)
	{
		this.db.beginTransaction();
		try
		{
			this.db.execSQL("INSERT INTO tradeno VALUES(null,?,?)", new Object[] { paramString1, paramString2 });
			this.db.setTransactionSuccessful();
			return;
		}
		finally
		{
			this.db.endTransaction();
		}
	}
	public boolean isExistTradeNo(String paramString)
	{
		boolean bool = false;
		Cursor cursor = ExecSQLForCursor("SELECT * FROM tradeno WHERE tradeno='" + paramString + "'");
		if (cursor.getCount() > 0) {
			bool = true;
		}
		cursor.close();
		return bool;
	}
	 public ArrayList<QrCodeBean> FindQrCodes(String money,String mark,String type) {
    	String sql = "SELECT * FROM qrcode WHERE money =" + "'" + money + "' and mark='"+mark+"' and type='"+type+"'";
        ArrayList<QrCodeBean> list = new ArrayList<QrCodeBean>();
        Cursor c = ExecSQLForCursor(sql);
        while (c.moveToNext()) {
            QrCodeBean info = new QrCodeBean();
            info.setMoney(c.getString(c.getColumnIndex("money")));
            info.setMark(c.getString(c.getColumnIndex("mark")));
            info.setType(c.getString(c.getColumnIndex("type")));
            info.setPayurl(c.getString(c.getColumnIndex("payurl")));
            info.setDt(c.getString(c.getColumnIndex("dt")));
            list.add(info);
        }
        c.close();
        return list;
    }
	public ArrayList<OrderBean> FindPayOrderAll() {
		String sql = "SELECT * FROM payorder";
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			OrderBean info = new OrderBean();
			info.setMoney(c.getString(c.getColumnIndex("money")));
			info.setMark(c.getString(c.getColumnIndex("mark")));
			info.setType(c.getString(c.getColumnIndex("type")));
			info.setNo(c.getString(c.getColumnIndex("tradeno")));
			info.setDt(c.getString(c.getColumnIndex("dt")));
			info.setResult(c.getString(c.getColumnIndex("result")));
			info.setTime(c.getInt(c.getColumnIndex("time")));
			list.add(info);
		}
		c.close();
		return list;
	}
	public ArrayList<QrCodeBean> FindQrcodeAll() {
		String sql = "SELECT * FROM qrcode";
		ArrayList<QrCodeBean> list = new ArrayList<QrCodeBean>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			QrCodeBean info = new QrCodeBean();
			info.setMoney(c.getString(c.getColumnIndex("money")));
			info.setMark(c.getString(c.getColumnIndex("mark")));
			info.setType(c.getString(c.getColumnIndex("type")));
			info.setPayurl(c.getString(c.getColumnIndex("payurl")));
			info.setDt(c.getString(c.getColumnIndex("dt")));
			list.add(info);
		}
		c.close();
		return list;
	}
	 public ArrayList<QrCodeBean> FindQrCodes(String mark) {
		 String sql = "SELECT * FROM qrcode WHERE mark='"+mark+"'";
		 ArrayList<QrCodeBean> list = new ArrayList<QrCodeBean>();
		 Cursor c = ExecSQLForCursor(sql);
		 while (c.moveToNext()) {
			 QrCodeBean info = new QrCodeBean();
			 info.setMoney(c.getString(c.getColumnIndex("money")));
			 info.setMark(c.getString(c.getColumnIndex("mark")));
			 info.setType(c.getString(c.getColumnIndex("type")));
			 info.setPayurl(c.getString(c.getColumnIndex("payurl")));
			 info.setDt(c.getString(c.getColumnIndex("dt")));
			 list.add(info);
		 }
		 c.close();
		 return list;
	 }
	public ArrayList<OrderBean> FindOrders(String money,String mark,String type) {
    	String sql = "SELECT * FROM payorder WHERE money =" + "'" + money + "' and mark='"+mark+"' and type='"+type+"'";
    	ArrayList<OrderBean> list = new ArrayList<OrderBean>();
    	Cursor c = ExecSQLForCursor(sql);
    	while (c.moveToNext()) {
    		OrderBean info = new OrderBean();
    		info.setMoney(c.getString(c.getColumnIndex("money")));
    		info.setMark(c.getString(c.getColumnIndex("mark")));
    		info.setType(c.getString(c.getColumnIndex("type")));
    		info.setNo(c.getString(c.getColumnIndex("tradeno")));
    		info.setDt(c.getString(c.getColumnIndex("dt")));
    		info.setResult(c.getString(c.getColumnIndex("result")));
    		info.setTime(c.getInt(c.getColumnIndex("time")));
    		list.add(info);
    	}
    	c.close();
    	return list;
    }
	public ArrayList<OrderBean> FindOrders(String mark) {
		String sql = "SELECT * FROM payorder WHERE mark='"+mark+"'";
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			OrderBean info = new OrderBean();
			info.setMoney(c.getString(c.getColumnIndex("money")));
			info.setMark(c.getString(c.getColumnIndex("mark")));
			info.setType(c.getString(c.getColumnIndex("type")));
			info.setNo(c.getString(c.getColumnIndex("tradeno")));
			info.setDt(c.getString(c.getColumnIndex("dt")));
			info.setResult(c.getString(c.getColumnIndex("result")));
    		info.setTime(c.getInt(c.getColumnIndex("time")));
			list.add(info);
		}
		c.close();
		return list;
	}
	public ArrayList<OrderBean> FindOrdersByNo(String no) {
		String sql = "SELECT * FROM payorder WHERE tradeno='"+no+"'";
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			OrderBean info = new OrderBean();
			info.setMoney(c.getString(c.getColumnIndex("money")));
			info.setMark(c.getString(c.getColumnIndex("mark")));
			info.setType(c.getString(c.getColumnIndex("type")));
			info.setNo(c.getString(c.getColumnIndex("tradeno")));
			info.setDt(c.getString(c.getColumnIndex("dt")));
			info.setResult(c.getString(c.getColumnIndex("result")));
			info.setTime(c.getInt(c.getColumnIndex("time")));
			list.add(info);
		}
		c.close();
		return list;
	}
	public ArrayList<OrderBean> FindAllOrders() {
		String sql = "SELECT * FROM payorder where result <> 'success' and time<3 ";
		ArrayList<OrderBean> list = new ArrayList<OrderBean>();
		Cursor c = ExecSQLForCursor(sql);
		while (c.moveToNext()) {
			OrderBean info = new OrderBean();
			info.setMoney(c.getString(c.getColumnIndex("money")));
			info.setMark(c.getString(c.getColumnIndex("mark")));
			info.setType(c.getString(c.getColumnIndex("type")));
			info.setNo(c.getString(c.getColumnIndex("tradeno")));
			info.setDt(c.getString(c.getColumnIndex("dt")));
			info.setResult(c.getString(c.getColumnIndex("result")));
			info.setTime(c.getInt(c.getColumnIndex("time")));
			list.add(info);
		}
		c.close();
		return list;
	}
	    
    /**
     * 执行SQL，返回一个游标
     * 
     * @param sql
     * @return
     */
    private Cursor ExecSQLForCursor(String sql) {
        Cursor c = db.rawQuery(sql, null);
        return c;
    }
    public void clearAll(){
		delete("payorder");
		delete("qrcode");
		delete("tradeno");
	}
	private void delete(String table) {
		db.beginTransaction();// 开始事务
		try {
			this.db.delete(table,null,null);
			db.setTransactionSuccessful();// 事务成功
		} finally {
			db.endTransaction();// 结束事务
		}
	}


}
