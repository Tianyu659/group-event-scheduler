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
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const handleSubmit = e => {
        e.preventDefault();
        console.log(firstName, lastName, email, password);
        window.location.replace("http://localhost:8080/calendar.jsp");
        handleClose();
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
                    id = "r_firstname_field"
                    label="First Name"
                    variant="filled"
                    required
                    value={firstName}
                    onChange={e => setFirstName(e.target.value)}
                />
                <TextField
                    id = "r_lastname_field"
                    label="Last Name"
                    variant="filled"
                    required
                    value={lastName}
                    onChange={e => setLastName(e.target.value)}
                />
                <TextField
                    id = "r_email_field"
                    label="Email"
                    variant="filled"
                    type="email"
                    required
                    value={email}
                    onChange={e => setEmail(e.target.value)}
                />
                <TextField
                    id = "r_password_field"
                    label="Password"
                    variant="filled"
                    type="password"
                    required
                    value={password}
                    onChange={e => setPassword(e.target.value)}
                />
                <div>
                    <Button variant="contained" onClick={handleCancel}>
                        Cancel
                    </Button>
                    <Button type="submit" variant="contained" color="primary">
                        Signup
                    </Button>
                </div>
            </form>
        </div>
    );
};

const domContainer3 = document.querySelector('#registration_box_container');
ReactDOM.render(e(Form), domContainer3);