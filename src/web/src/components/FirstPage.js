import React, { useState} from "react";
import addNotification from 'react-push-notification';

const axios = require('axios');

const FirstPage = ({loggedInStateChanger, ...rest}) => {
  const [username, setUserName] = useState('');
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [password, setPassword] = useState('');
  const [password2, setPassword2] = useState('');

  const [login, setLogin] = useState(true);

  const handleSubmit = () => {
    if (login) {
        const body = {
            "username": username,
            "password": password
        }
        axios.post("localhost:8080/users/", body)
            .then((token) => {
                loggedInStateChanger(true)
                console.log(token)
            })
            .catch((err) => {handleError(err)});
    } else {
        const body = {
            "username": username,
            "password": password,
            "firstName": firstName,
            "lastName": lastName
        }
        axios.post("localhost:8080/session/", body)
            .then((token) => {
                loggedInStateChanger(true)
                console.log(token)
            })
            .catch((err) => {handleError(err)});
    }
  }

  const handleError = (error) => {
    console.log(error);
    addNotification({
        title: 'Error Submitting Form',
        subtitle: 'An error occured when logging in/signing up',
        message: error,
        theme: 'darkblue',
        native: true // when using native, your OS will handle theming.
    });
  }

  const invalidForm = () => {
    return (firstName === null || firstName === '' || lastName === null || lastName === '' || username === null || username === '' || password === null || password === '' || password !== password2); 
  }

  if (!login) {
    return (
        <div class = "first page box"> 
            <form class = "register_form" onSubmit={handleSubmit()}>
                <input type="text" placeholder="First name" value = {firstName} onChange={(e)=>setFirstName(e.target.value)} />
                <br />
                <input type="text" placeholder="Last name" value = {lastName} onChange={(e)=>setLastName(e.target.value)} /> 
                <br />
                <input type="text" placeholder="Username" value = {username} onChange={(e)=>setUserName(e.target.value)} />
                <br />
                <input type="password" placeholder="Password" value = {password} onChange={(e)=>setPassword(e.target.value)} />
                <br />
                <input type="password" placeholder="Confirm password" value = {password2} onChange={(e)=>setPassword2(e.target.value)} />
                <br />
                <button block size="lg" type = "submit" disabled={invalidForm()}>
                    Register!
                </button>
                <button block size="lg" type="reset" onClick={() => setLogin(!login)}>
                    Already Registered? Click Here to Login.
                </button>
            </form>
        </div>
    )
  }
  return (
    <div class = "first page box"> 
        <form class = "login_form" onSubmit={handleSubmit()}>
            <input type="text" placeholder="Username" value = {username} onChange={(e)=>setUserName(e.target.value)} />
            <br />
            <input type="password" placeholder="Password" value = {password} onChange={(e)=>setPassword(e.target.value)} /> 
            <br />
            <button block size="lg" type = "submit" disabled={username === null || username === '' || password === null || password === ''}>
                Login!
            </button>
            <button block size="lg" type="reset" onClick={() => setLogin(!login)}>
                Not Registered? Click Here to Create an Account.
            </button>
        </form>
    </div>
  )
}

export default FirstPage;