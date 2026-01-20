package tw.lab.apis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Callable;

//根據使用者傳遞需求對伺服器下相對指令並回傳
public class StoreService {
	private final String url,user,passwd;
	
	public StoreService(String url,String user,String passwd) {
		this.url = url;
		this.user = user;
		this.passwd = passwd;
			
	}
	
	public void restock(int productId,int qty) throws Exception{
		withRetry(() -> {
			try(Connection conn = DriverManager.getConnection(url,user,passwd)){
				conn.setAutoCommit(false); //預設為true:不啟動交易，直接commit
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				//交易時若有讀取資料給予commit後的資料
				
				int stock = selectStockForUpdate(conn, productId);
				updateStock(conn, productId, stock+qty);
				insertLog(conn, "IN", productId, "IN:" + qty);
				
				conn.commit();
			}	
			return null;
		});
	}
	
	public void purchase(int productId,int qty) throws Exception{
		withRetry(() -> {
			try(Connection conn = DriverManager.getConnection(url,user,passwd)){
				conn.setAutoCommit(false); //預設為true:不啟動交易，直接commit
				conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
				//交易時若有讀取資料給予commit後的資料
				
				int stock = selectStockForUpdate(conn, productId);
				//數量不足，交易取消
				if (stock < qty) { 
//					conn.setAutoCommit(true);
					insertLog(conn, "ERR", productId, stock + "-" + qty);
					conn.commit();
					throw new NotEnoughException("庫存量不足");
				}
				updateStock(conn, productId, stock-qty);
				insertLog(conn, "OUT", productId, "OUT:" + qty);
				
				conn.commit();
			}
			return null;
		});
	}
	
	private int selectStockForUpdate(Connection conn,int productId) throws Exception{
		String sql = """
				SELECT stock
				FROM mytest
				WHERE id = ?
				FOR UPDATE
				""";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, productId);
			try(ResultSet rs = pstmt.executeQuery()){
				if(!rs.next()) throw new SQLException();
				return rs.getInt(1);
			}
		}
	}
	
	
	private void updateStock(Connection conn,int productId,int stock) throws Exception{
		String sql = """
				UPDATE mytest
				SET stock = ?
				WHERE id = ?
				""";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setInt(1, stock);
			pstmt.setInt(2, productId);
			pstmt.executeUpdate();
			
		}
	}
	
	private void insertLog(Connection conn,String type, int productId,String note) throws Exception {
		String sql = """
				INSERT INTO log
					(type,pid,note)
				VALUES
					(?,?,?)
				""";
		try(PreparedStatement pstmt = conn.prepareStatement(sql)){
			pstmt.setString(1, type);
			pstmt.setInt(2, productId);
			pstmt.setString(3, note);
			pstmt.executeUpdate();
		}
	}
	
	// retry 框架(framework)
	private <T> T withRetry(Callable<T> action) throws Exception {
		int max = 3; // 最大嘗試次數
		int backoff = 10; //初始嘗試時間
		for (int i = 0;i <= max;i++) {
			try {
				return action.call();
			}catch (SQLException e) {
				// Deadlock,lock
				if (i == max) throw e;		
				Thread.sleep(backoff);
				backoff *= 2;
			}
		}
		// 拋出特別例外
		throw new IllegalStateException("ERROE(1)");
		}

	
	
	
	
	
	
	
}
	

