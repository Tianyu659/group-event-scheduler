const { useState } = React;
const { makeStyles, TextField, Button } = MaterialUI;
const e = React.createElement;

const useStyles = makeStyles(theme => ({
    root: {
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        spacing: 2,

        '& .MuiTextField-root': {
            spacing: 2,
            width: '300px',
        },
        '& .MuiButtonBase-root': {
            margin: 2,
        },
    },
}));

const headerStyle = {
    textAlign: 'center',
    fontSize: '2rem',
    fontFamily: 'Source Sans Pro, sans-serif'
  }

const Form = ({ handleClose }) => {
    const classes = useStyles();
    // create state variables for each input
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [password_confirmed, confirmPassword] = useState('');

    const handleSubmit = e => {
        e.preventDefault();
        if(password != password_confirmed)
        {
            alert("password does not match");
        }
        else
        {
        console.log(username, password);
        window.location.replace("http://localhost:8080/calendar.jsp");
        handleClose();
        }
    };



    const handleCancel = e => {
        e.preventDefault();
        window.location.replace("/");
        handleClose();
    }

    return (
        <div>
            <h2 style={headerStyle}>Sign Up</h2>
            <form className={classes.root} onSubmit={handleSubmit}>
                <TextField
                    id = "r_username_field"
                    label="Username"
                    variant="filled"
                    type="username"
                    required
                    value={username}
                    onChange={e => setUsername(e.target.value)}
                />
                <TextField
                    id = "r_password_field"
                    name = "password"
                    label="Password"
                    variant="filled"
                    type="password"
                    required
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                <TextField
                    id = "r_password_reentered_field"
                    name = "confirm_password"
                    label = "Password Comfirm"
                    variant= "filled"
                    type= "password"
                    required
                    value={password_confirmed}
                    onChange={e => confirmPassword(e.target.value)}
                    />
                <div>{}</div>
                <div>
                    <Button variant="contained" onClick={handleCancel}>
                        Cancel
                    </Button>
                    <Button type="submit" variant="contained" color="primary">
                        Create User
                    </Button>
                </div>
            </form>
        </div>
    );
};

const domContainer3 = document.querySelector('#registration_box_container');
ReactDOM.render(e(Form), domContainer3);