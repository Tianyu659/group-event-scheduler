'use strict';

const { useState } = React;
const e = React.createElement;

const counterButton = (props) => {
    const [number, setNumber] = useState(0);

    const handleClick = () => {
        setNumber(number+1);
    }

    return (
        <button onClick={handleClick}>{number}</button>
    );
}

//const domContainer2 = document.querySelector('#counter_button_container');
//ReactDOM.render(e(counterButton), domContainer2);