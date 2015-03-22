package client;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import client.communication.ClientCommunicator;
import server.database.Database;
import server.database.DatabaseException;
import server.database.UserDAO;
import shared.communication.ValidateUserCredentials;
import shared.communication.ValidateUserResponse;
import shared.model.Credentials;
import shared.model.User;
import shared.model.UserInfo;

public class ValidateUserUnitTest
{
    @BeforeClass
    public static void setUpBeforeClass() throws Exception
    {
        // Load database driver
        Database.initialize();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception
    {
        return;
    }

    private Database db;
    private UserDAO dbUser;
    private ClientCommunicator cCom;

    @Before
    public void setUp() throws Exception
    {
        // Delete all users from the database
        db = new Database();
        db.startTransaction();

        ArrayList<User> users = db.getUserDAO().getAll();

        for (User u : users)
        {
            db.getUserDAO().delete(u);
        }

        db.endTransaction(true);

        // Prepare database for test case
        db = new Database();
        dbUser = db.getUserDAO();
        cCom = new ClientCommunicator();
    }

    @After
    public void tearDown() throws Exception
    {
        db = null;
        dbUser = null;
    }

    @Test
    public void testValidate() throws DatabaseException
    {
        db.startTransaction();
        User davis = new User(new Credentials("davishyer", "davishyer"), new UserInfo("Davis", "Hyer", "davishyer@gmail.com"));
        dbUser.add(davis);
        db.endTransaction(true);
        //valid user
        ValidateUserResult result = cCom.validateUser(new ValidateUserCredentials("davishyer", "davishyer"));
        assertEquals(result.getFirstName(), "Davis");
        //invalid user
        ValidateUserResult result2 = cCom.validateUser(new ValidateUserCredentials("invalid", "invalid"));
        assertEquals(null, result2.getFirstName());
    }
}
