package csci310.servlets;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import csci310.Authentication;
import csci310.Database;
import csci310.exception.NotImplementedError;
import csci310.exception.RequestException;
import csci310.forms.Form;
import csci310.forms.SessionForm;
import csci310.models.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SessionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        throw new NotImplementedError();
    }
}
