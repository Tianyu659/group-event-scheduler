package csci310.servlets;

import javax.servlet.http.HttpSession;

public enum SessionAttributes {
    keys;
    
    public void setAttribute(HttpSession session,Object o) {
        session.setAttribute(name(),o);
    }
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(HttpSession session) {
        return (T)session.getAttribute(name());
    }
}
