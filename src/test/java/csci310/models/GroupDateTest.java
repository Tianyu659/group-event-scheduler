package csci310.models;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.table.TableUtils;
import csci310.Database;
import org.junit.BeforeClass;

import java.sql.SQLException;

public class GroupDateTest {
    static JdbcConnectionSource connectionSource;
    private static Dao<GroupDate, Integer> dao;

//    @BeforeClass
//    public static void setupTestDatabase() throws SQLException {
//        UserTest.connectionSource = Database.connect();
//        TableUtils.dropTable(UserTest.connectionSource, User.class, true);
//        TableUtils.createTable(UserTest.connectionSource, User.class);
//        GroupDateTest.dao = DaoManager.createDao(UserTest.connectionSource, User.class);
//    }
}
