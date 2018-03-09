package edu.mum.cs.uis.db;

import java.sql.Connection;
import java.sql.Date;
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
	
	private static UsedItemsDaoImpl instance;
	
	private UsedItemsDaoImpl() {
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
				int id = rs.getInt("USERID");
				
				User user = null;
				if(isAdmin)
					user = new Admin(id, firstName,lastName,userName,password);
				else
					user = new User(id, firstName,lastName,userName,password,isAdmin);
				
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
	
	public List<Item> getAllItemsByStatus(Status status){
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> items = new ArrayList<Item>();
		
		try {
			String selectSQL = "SELECT itm.*, img.PATH, cat.NAME AS CATNAME from ITEMS itm,IMAGES img, CATEGORIES cat WHERE itm.IMGID = img.IMGID AND itm.CATID = cat.CATID AND STATUS = ?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setString(1, status.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("ITEMID");
				String title = rs.getString("TITLE");
				String description = rs.getString("DESCRIPTION");
				Double price = rs.getDouble("PRICE");
				Date creationDate = rs.getDate("CREATIONDATE");
				
				String stts = rs.getString("STATUS");
				
				int userId = rs.getInt("USERID");
				
				int catId = rs.getInt("CATID");
				String catName = rs.getString("CATNAME");
				Category cat = new Category(catId, catName);
				
				int imgId = rs.getInt("IMGID");
				String imgPath = rs.getString("PATH");
				Image img = new Image(imgId, imgPath);
				
				Item item = new Item(title, description, price, creationDate.toLocalDate(), status, img, cat, userId);
				
				items.add(item);
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
		return items;
		
	}
	
	public List<Item> getAllItemsByUserId(int userId){
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Item> items = new ArrayList<Item>();
		
		try {
			String selectSQL = "SELECT itm.*, img.PATH, cat.NAME AS CATNAME from ITEMS itm,IMAGES img, CATEGORIES cat WHERE itm.IMGID = img.IMGID AND itm.CATID = cat.CATID AND itm.USERID = ?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1, userId);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("ITEMID");
				String title = rs.getString("TITLE");
				String description = rs.getString("DESCRIPTION");
				Double price = rs.getDouble("PRICE");
				Date creationDate = rs.getDate("CREATIONDATE");
				
				String stts = rs.getString("STATUS");
				Status status = Status.valueOf(stts);
//				int userId = rs.getInt("USERID");
				
				int catId = rs.getInt("CATID");
				String catName = rs.getString("CATNAME");
				Category cat = new Category(catId, catName);
				
				int imgId = rs.getInt("IMGID");
				String imgPath = rs.getString("PATH");
				Image img = new Image(imgId, imgPath);
				
				Item item = new Item(title, description, price, creationDate.toLocalDate(), status, img, cat, userId);
				
				items.add(item);
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
		return items;
	}
	
	public Item getItemDetailsById(int itemId){
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Item item = null;
		
		try {
			String selectSQL = "SELECT itm.*, img.PATH, cat.NAME AS CATNAME from ITEMS itm,IMAGES img, CATEGORIES cat WHERE itm.IMGID = img.IMGID AND itm.CATID = cat.CATID AND itm.ITEMID = ?";
			pstmt = conn.prepareStatement(selectSQL);
			pstmt.setInt(1, itemId);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int id = rs.getInt("ITEMID");
				String title = rs.getString("TITLE");
				String description = rs.getString("DESCRIPTION");
				Double price = rs.getDouble("PRICE");
				Date creationDate = rs.getDate("CREATIONDATE");
				
				String stts = rs.getString("STATUS");
				Status status = Status.valueOf(stts);
				
				int userId = rs.getInt("USERID");
				
				int catId = rs.getInt("CATID");
				String catName = rs.getString("CATNAME");
				Category cat = new Category(catId, catName);
				
				int imgId = rs.getInt("IMGID");
				String imgPath = rs.getString("PATH");
				Image img = new Image(imgId, imgPath);
				
				item = new Item(title, description, price, creationDate.toLocalDate(), status, img, cat, userId);
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
		return item;
	}
	
	@Override
	public boolean updateItemStatusById(int itemId, Status status) {
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("update ITEMS set STATUS = ? WHERE ITEMID = ?");
			pstmt.setString(1, status.toString());
			pstmt.setInt(2, itemId);
			
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

	@Override
	public boolean addComment(String comment, int itemId, int userId) {
		
		Connection conn = dbConnection.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement("insert into COMMENTS (USERID, ITEMID,CMNTEXT,CREATIONDATE) values (?, ?, ?, ?)");
			pstmt.setInt(1, userId);
			pstmt.setInt(2, itemId);
			pstmt.setString(3, comment);
			pstmt.setDate(4, java.sql.Date.valueOf(LocalDate.now()));
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
	
	
	@Override
	public List<Item> getAllItems() {
		
		Connection conn = dbConnection.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		List<Item> items = new ArrayList<Item>();
		
		try {
			stmt = conn.createStatement();
			
			String sqlQuery = "SELECT itm.*, img.PATH, cat.NAME AS CATNAME from ITEMS itm,IMAGES img, CATEGORIES cat WHERE itm.IMGID = img.IMGID AND itm.CATID = cat.CATID";
			rs = stmt.executeQuery(sqlQuery);
			while(rs.next()) {
				
				int id = rs.getInt("ITEMID");
				String title = rs.getString("TITLE");
				String description = rs.getString("DESCRIPTION");
				Double price = rs.getDouble("PRICE");
				Date creationDate = rs.getDate("CREATIONDATE");
				
				String stts = rs.getString("STATUS");
				Status status = Status.valueOf(stts);
				
				int userId = rs.getInt("USERID");
				
				int catId = rs.getInt("CATID");
				String catName = rs.getString("CATNAME");
				Category cat = new Category(catId, catName);
				
				int imgId = rs.getInt("IMGID");
				String imgPath = rs.getString("PATH");
				Image img = new Image(imgId, imgPath);
				
				Item item = new Item(title, description, price, creationDate.toLocalDate(), status, img, cat, userId);
				
				
				items.add(item);
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
		return items;
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
    	
    	User user = new User(0, "firstName", "lastName", "userName", "password",false);
    	UsedItemsDao dao = new UsedItemsDaoImpl();
    	
//    	dao.registerNewUser(user);
    	
    	System.out.println(dao.validateLogin("userName", "password"));
//    	System.out.println(dao.validateLogin("userName1", "password"));
//    	dao.addCategory("VEHICLE");
//    	dao.addCategory("ELECTRONICS");
//    	dao.addCategory("LAPTOP1");
    	
//    	List<Category> cats = new ArrayList<>();
//    	cats = dao.getCategories();
//    	for(Category c:cats) {
//    		System.out.println(c);
//    	}
    	
//    	Image img = new Image(0, "/x/y/z");
//    	Category category = new Category(2, "VEHICLE");
//		Item item = new Item("title1", "description1", 30, LocalDate.now(), Status.APPROVED, img , category, 1);
//    	dao.addItem(item);
    	
//    	List<Item> items = dao.getAllItemsByStatus(Status.CREATED);
//    	for(Item item:items) {
//    		System.out.println(item);
//    	}
    	
//    	List<Item> items = dao.getAllItemsByUserId(1);
//    	for(Item item:items) {
//    		System.out.println(item);
//    	}
    	
//    	System.out.println(dao.getItemDetailsById(101));
    	
    	
//    	System.out.println(dao.updateItemStatusById(101,Status.REJECTED));
//    	
//    	List<Item> items = dao.getAllItemsByUserId(1);
//    	for(Item item:items) {
//    		System.out.println(item);
//    	}
    	
//    	System.out.println(dao.addComment("comment1", 1, 1));
    	
    	List<Item> items = dao.getAllItems();
    	for(Item item:items) {
    		System.out.println(item);
    	}
	}
    
    public static UsedItemsDaoImpl getInstance() {
    	if(instance == null)
    		instance = new UsedItemsDaoImpl();
		return instance;
	}

	

	

	
	

}
