package Pruebas_Sistensis;
import java.sql.*;
import java.util.Scanner;
/**
 * @author DiegoFS
 */
public class ConectarTabla {
	private static Connection miConexion;
	public static void main(String[] args) {
		
		try {
			//1.Crear conexion
			miConexion = DriverManager.getConnection("jdbc:postgresql://192.168.56.100:5432/ReaderHistory", "postgres", "1234");
			
			//Para poder capturar la entrada de datos
		    Scanner leer =new Scanner(System.in);
		    System.out.print("Username: ");
		    String username =leer.next();
		    System.out.print("password: ");
		    String password =leer.next();
		    
		    
		    //makeInsert(username,password,filename);
		    //createUsers(username,password);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}
	
	public static void makeInsert(String username, String password, String filename) throws SQLException {	
		Statement InsertStatement = miConexion.createStatement();
		try {
			//ResultSet InsertResultset = InsertStatement.executeQuery("INSERT INTO books (id,id_user,file_name,read_date) VALUES ((SELECT (MAX(Id)+1) FROM books),(SELECT id FROM users WHERE username LIKE '"+ username+"' AND password LIKE '"+password+"'),'filename',CURRENT_TIMESTAMP);");
			ResultSet InsertResultset = InsertStatement.executeQuery("INSERT INTO books (id,id_user,file_name,read_date) VALUES (1,(SELECT id FROM users WHERE username LIKE '"+ username+"' AND password LIKE '"+password+"'),'"+filename+"',CURRENT_TIMESTAMP);");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void createUsers(String username, String password) throws SQLException {
		Statement createUserStatement = miConexion.createStatement();
		try {
			ResultSet miResultset = createUserStatement.executeQuery("INSERT INTO users(id,username,password) VALUES ((SELECT (MAX(id)+1) FROM users),'"+username+"','"+password+"');");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
