package csci310.servlets;

import javax.servlet.http.HttpSession;

public enum SessionAttributes {
    keys,isAuthed(false);
    
    final Object defaultValue;
    SessionAttributes(final Object defaultValue) {this.defaultValue = defaultValue;}
    SessionAttributes() {this(null);}
    
    public void setAttribute(final HttpSession session,final Object o) {
        session.setAttribute(name(),o);
    }
    @SuppressWarnings("unchecked")
    public <T> T getAttribute(final HttpSession session) {
        final Object o = session.getAttribute(name());
        return (T)(o == null? defaultValue : o);
    }
}
