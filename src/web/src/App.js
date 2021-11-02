import React, { useState} from "react";
import FirstPage from "./components/FirstPage";
import HomePage from "./components/HomePage";
import './styling/App.css';

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  if (loggedIn) {
    return (
      <HomePage />
    );
  }
  return (
    <FirstPage stateChanger = {setLoggedIn}/>
  );
}

export default App;
