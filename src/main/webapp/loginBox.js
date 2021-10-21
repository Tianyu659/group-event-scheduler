'use strict';

const e = React.createElement;

const formStyle = {
    margin: 'auto',
    padding: '10px',
    border: '1px solid #c9c9c9',
    borderRadius: '5px',
    background: '#f5f5f5',
    width: '220px',
    display: 'block'
};

const labelStyle = {
    margin: '10px 0 5px 0',
    fontFamily: 'Arial, Helvetica, sans-serif',
    fontSize: '15px',
};

const inputStyle = {
    margin: '5px 0 10px 0',
    padding: '5px',
    border: '1px solid #bfbfbf',
    borderRadius: '3px',
    boxSizing: 'border-box',
    width: '100%'
};

const submitStyle = {
    margin: '10px 0 0 0',
    padding: '7px 10px',
    border: '1px solid #efffff',
    borderRadius: '3px',
    background: '#3085d6',
    width: '100%',
    fontSize: '15px',
    color: 'white',
    display: 'block'
};

const headerStyle = {
  textAlign: 'center',
  fontSize: '2rem',
  fontFamily: 'Source Sans Pro, sans-serif'
}

const registerLinkStyle = {
  textAlign: 'center',
  fontSize: '1rem',
  fontFamily: 'Source Sans Pro, sans-serif'
}



const Field = React.forwardRef(({label, type, id}, ref) => {
    return (
      <div>
        <label style={labelStyle} >{label}</label>
        <input id={id} ref={ref} type={type} style={inputStyle} />
      </div>
    );
});

const Form = (props) => {
    const usernameRef = React.useRef();
    const passwordRef = React.useRef();

    const printData = data => {
      //const json = JSON.stringify(data, null, 4);
      //alert(json);

    // make login page verify username and password
    const trimmed = data.trim();
    if(trimmed == "failure") {
        alert("Username or password incorrect, try again.");
        usernameRef.current.value = "";
        passwordRef.current.value = "";
    } else {
        window.location.replace("http://localhost:8080/calendar.jsp");
    }
  
  };

    const handleSubmit = e => {
        e.preventDefault();
        const data = {
            username: usernameRef.current.value,
            password: passwordRef.current.value
        };
        sendEncrypted('/login', usernameRef.current.value + '\n' + passwordRef.current.value, (res) => printData(res));

    };
    return (
      <div>
        <h2 style={headerStyle}>Log In </h2>
        <form style={formStyle} onSubmit={handleSubmit}  >
          <Field id="username_field" ref={usernameRef} label="Username:" type="text" />
          <Field id = "password_field" ref={passwordRef} label="Password:" type="password" />
          <div>
            <span style={registerLinkStyle}><a href="http://localhost:8080/register.jsp">Create Account</a></span>
          </div>
          <div>
            <button style={submitStyle} type="submit" >Submit</button>
          </div>
        </form>
      </div>
    );
};



const domContainer2 = document.querySelector('#login_box_container');
ReactDOM.render(e(Form), domContainer2);
