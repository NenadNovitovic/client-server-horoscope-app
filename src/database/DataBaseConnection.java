package database;
import sign.Sign;
import java.sql.*;


public class DataBaseConnection extends DataBaseConfig {
    Connection dbConnection;

    public DataBaseConnection(){
        try{
            dbConnection = this.getDbConnection();
        }catch (SQLException e){
            System.out.println("Baza nije pokrenuta.");
        }
    }

    public Connection getDbConnection()throws SQLException {
        String connectionString="jdbc:mysql://"+dbHost+"/"+dbName;

        //Class.forName("com.mysql.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString,dbUser,dbPassword);
        return dbConnection;
    }

    //read

    public Sign getSign(String imeZnaka) throws SQLException,NullPointerException{
            Sign sign= new Sign();

            String sql="SELECT * FROM signs WHERE name=?";
            PreparedStatement preparedStatement=dbConnection.prepareStatement(sql);
            preparedStatement.setString(1,imeZnaka);
            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()) {
                sign.setId(rs.getInt(1));
                sign.setName(rs.getString("name"));
                sign.setBasicDescription(rs.getString("basicDescription"));
                sign.setFamousPeopleInSameSign(rs.getString("famousPeople"));
                sign.setDailyHoroscope(rs.getString("dailyHoroscope"));
            }
            return sign;
    }

    //update

    public void updateSign(Sign sign)throws SQLException{
        String sql="UPDATE signs SET basicDescription=?,famousPeople=?,dailyHoroscope=? WHERE name=?";
            PreparedStatement preparedStatement=dbConnection.prepareStatement(sql);
            preparedStatement.setString(1,sign.getBasicDescription());
            preparedStatement.setString(2,sign.getFamousPeopleInSameSign());
            preparedStatement.setString(3,sign.getDailyHoroscope());
            preparedStatement.setString(4,sign.getName());
            preparedStatement.execute();
            System.out.println("Updated DB");
    }


    //write

}
