package edu.mum.cs.uis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import edu.mum.cs.uis.model.User;


public class UsedItemsDaoImpl implements UsedItemsDao {
	
	private DBConnection dbConnection;
	
	public UsedItemsDaoImpl() {
		this.dbConnection = DBConnection.getInstance();
	}
	
	public User registerNewUser(User user) {
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into USERS (USERNAME, PASSWORD,FIRSTNAME,LASTNAME,ISADMIN) values (?, ?, ?, ?, ?)");
			pstmt.setString(1, user.getUserName());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getFirstName());
			pstmt.setString(4, user.getLastName());
			pstmt.setBoolean(5, false);
			pstmt.executeUpdate();
			return user;
			
		} catch(SQLException sqlEx) {
			printSQLException(sqlEx);
		} finally {
            // release resources
            try {
                if (pstmt != null) {
                	pstmt.close();
                	pstmt = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
		return null;
	}	
	

	public User validateLogin(String userName, String password) {
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			pstmt = conn.prepareStatement("SELECT * from USERS where USERNAME = ? and PASSWORD = ?");
			pstmt.setString(1, userName);
			pstmt.setString(2, password);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String firstName = rs.getString("FIRSTNAME");
				String lastName = rs.getString("LASTNAME");
				boolean isAdmin = rs.getBoolean("ISADMIN");
				User user = new User(firstName,lastName,userName,password,isAdmin);
				return user;
			}
			closeResultSet(rs);
		} catch(SQLException sqlEx) {
			printSQLException(sqlEx);
		} finally {
            // release resources
            try {
                if (pstmt != null) {
                	pstmt.close();
                	pstmt = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
		return null;
	}	


	public void addCategory(String name) {
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("insert into CATEGORIES (NAME) values (?)");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			
		} catch(SQLException sqlEx) {
			printSQLException(sqlEx);
		} finally {
            // release resources
            try {
                if (pstmt != null) {
                	pstmt.close();
                	pstmt = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
	}
	
    public static void printSQLException(SQLException e)
    {
        while (e != null)
        {
            System.err.println("\n----- SQLException -----");
            System.err.println("  SQL State:  " + e.getSQLState());
            System.err.println("  Error Code: " + e.getErrorCode());
            System.err.println("  Message:    " + e.getMessage());
            // for stack traces, refer to derby.log or uncomment this:
            //e.printStackTrace(System.err);
            e = e.getNextException();
        }
    }	
    
    private void closeResultSet(ResultSet rs) {
        // ResultSet
        try {
            if (rs != null) {
                rs.close();
                rs = null;
            }
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }
    }
    
    public static void closeDBConnection(Connection conn) {
        try {
            if (conn != null) {
            	System.out.println("Closing connection to the SRSDB database on JavaDB server...");
                conn.close();
                conn = null;
                System.out.println("Successfully closed connection to the SRSDB database on JavaDB server...");
            }
        } catch (SQLException sqle) {
            printSQLException(sqle);
        }
    }    
    
    public static void main(String[] args) {
    	
    	User user = new User( "firstName", "lastName", "userName", "password",false);
    	UsedItemsDao dao = new UsedItemsDaoImpl();
    	
//    	dao.registerNewUser(user);
    	
//    	System.out.println(dao.validateLogin("userName", "password"));
    	System.out.println(dao.validateLogin("userName1", "password"));
//    	dao.addCategory("VEHICLE");
    	
    	
	}

}
