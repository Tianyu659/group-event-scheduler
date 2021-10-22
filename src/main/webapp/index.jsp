<%@ page import="csci310.*" %>
<html>
    <head>
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Source+Sans+Pro&display=swap" rel="stylesheet">
        <%@ include file="header.jsp"%>
    </head>
    <body>
        <div id="login_box_container"></div>
        <script src="https://unpkg.com/react@17/umd/react.development.js" crossorigin></script>
        <script src="https://unpkg.com/react-dom@17/umd/react-dom.development.js" crossorigin></script>
        <script src="https://unpkg.com/babel-standalone@6/babel.min.js"></script>

        <script type="text/babel" src="loginBox.js"></script>
        <script src="encryptionUtil.js"></script>

    </body>
    <%@ include file="footer.jsp"%>
</html>