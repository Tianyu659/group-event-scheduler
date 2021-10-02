<%@ page import="csci310.*" %>
<html>
<body>
    

    <%
        Hello h = new Hello();
    %>
    <h2><%= h.greet("world") %>!</h2>

    <a href="login.jsp">Login</a>
    <a href="register.jsp">Register</a>
    <a href="calendar.jsp">Calendar</a>

    <script src="https://unpkg.com/react@17/umd/react.development.js" crossorigin></script>
    <script src="https://unpkg.com/react-dom@17/umd/react-dom.development.js" crossorigin></script>
    <script src="https://unpkg.com/babel-standalone@6/babel.min.js"></script>

    <script type="text/babel" src="counterButton.js"></script>
</body>
</html>
