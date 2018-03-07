package edu.mum.cs.uis.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import edu.mum.cs.uis.model.Admin;
import edu.mum.cs.uis.model.Category;
import edu.mum.cs.uis.model.Image;
import edu.mum.cs.uis.model.Item;
import edu.mum.cs.uis.model.Status;
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
				
				User user = null;
				if(isAdmin)
					user = new Admin(firstName,lastName,userName,password);
				else
					user = new User(firstName,lastName,userName,password,isAdmin);
				
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


	public boolean addCategory(String name) {
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement("insert into CATEGORIES (NAME) values (?)");
			pstmt.setString(1, name);
			pstmt.executeUpdate();
			return true;
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
		return false;
	}
	
	public List<Category> getCategories() {
		Connection conn = dbConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Category> categories = new ArrayList<Category>();
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery("SELECT * from CATEGORIES");
			while(rs.next()) {
				
				int id = rs.getInt("CATID");
				String name = rs.getString("NAME");
				Category cat = new Category(id,name);
				categories.add(cat);
			}
			closeResultSet(rs);
		} catch(SQLException sqlEx) {
			printSQLException(sqlEx);
		} finally {
            // release resources
            try {
                if (stmt != null) {
                	stmt.close();
                	stmt = null;
                }
            } catch (SQLException sqle) {
                printSQLException(sqle);
            }
        }
		return categories;
	}	
	
	@Override
	public boolean addItem(Item item) {
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			
			pstmt = conn.prepareStatement("insert into IMAGES (PATH) values (?)",Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, item.getImg().getPath());
			pstmt.execute();
			
			ResultSet rs = pstmt.getGeneratedKeys();
			int generatedKey = 0;
			if (rs.next()) {
			    generatedKey = rs.getInt(1);
			}
			System.out.println("Inserted record's ID: " + generatedKey);
			
			pstmt = conn.prepareStatement("insert into ITEMS (TITLE, DESCRIPTION,PRICE,CREATIONDATE,STATUS, USERID,CATID,IMGID) values (?, ?, ?, ?, ?,?,?,?)");
			pstmt.setString(1, item.getTitle());
			pstmt.setString(2, item.getDescription());
			
			pstmt.setDouble(3, item.getPrice());
			pstmt.setDate(4, java.sql.Date.valueOf(item.getCreationDate()));
			pstmt.setString(5, item.getStatus().toString());
			
			pstmt.setInt(6, item.getUserId());
			pstmt.setInt(7, item.getCat().getId());
			pstmt.setInt(8, generatedKey);
			
			pstmt.executeUpdate();
			return true;
			
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
		return false;
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
//    	System.out.println(dao.validateLogin("userName1", "password"));
//    	dao.addCategory("VEHICLE");
//    	dao.addCategory("ELECTRONICS");
//    	dao.addCategory("LAPTOP1");
    	
    	List<Category> cats = new ArrayList<>();
    	cats = dao.getCategories();
    	for(Category c:cats) {
    		System.out.println(c);
    	}
    	
    	Image img = new Image(0, "/x/y/z");
    	Category category = new Category(1, "VEHICLE");
    	
		Item item = new Item("title", "description", 25.5, LocalDate.now(), Status.CREATED, img , category, 1);
		
    	dao.addItem(item);
    	
    	
	}

	

}
