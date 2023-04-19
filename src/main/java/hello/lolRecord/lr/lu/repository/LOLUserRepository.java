package hello.lolRecord.lr.lu.repository;

import hello.lolRecord.lr.lu.dto.LOLUserJoinForm;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
public class LOLUserRepository {
    private final DataSource dataSource;
    private final Logger log = LoggerFactory.getLogger(getClass());

    private Connection getConnection(DataSource dataSource) throws SQLException {
        return dataSource.getConnection();

    }
    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }


    public int save(LOLUserJoinForm lolUser) throws SQLException {
        String sql = "insert into LOL_USER" +
                "(USER_ID,PWD,BIRTH,PHONE,ADRESS,GENDER,LOL_NICK,MAIN_POSITION,SUB_POSITION) " +
                "values(?,?,?,?,?,?,?,?,?)";
        Connection con = getConnection(dataSource);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,lolUser.getUSER_ID());
        pstmt.setString(2,lolUser.getPWD());
        pstmt.setString(3,lolUser.getBIRTH());
        pstmt.setString(4,lolUser.getPHONE());
        pstmt.setString(5,lolUser.getADDRESS());
        pstmt.setString(6,lolUser.getGENDER());
        pstmt.setString(7,lolUser.getLOL_NICK());
        pstmt.setString(8,lolUser.getMAIN_POSITION());
        pstmt.setString(9,lolUser.getSUB_POSITION());
        pstmt.executeUpdate();
        //close(con,stmt,rs);

        return 1;

    }

    public String findPwd(String user_id) throws SQLException {
        String sql = "select PWD from LOL_USER " +
                "where USER_ID = ?";
        String PWD;
        Connection con = getConnection(dataSource);
        PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setString(1,user_id);
        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            PWD = rs.getString("PWD");
            log.info("패스워드 : {}",PWD);
            return PWD;
        } else{
            throw new NoSuchElementException("해당 유저(아이디)를 찾을수 없습니다.");
        }
    }

}
