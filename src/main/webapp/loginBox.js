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

/*
 * Converts the argument into a base 64 string.
 *
 * @param {bytes} Uint8Array or ArrayBuffer.
 */
function b64Encode(bytes) {
    if(typeof(bytes) !== 'Uint8Array')
        // Input is ArrayBuffer, convert to typed array.
        bytes = new Uint8Array(bytes);
    let o = '';
    for(b of bytes)
        o += String.fromCharCode(b);
    return window.btoa(o);
}
/*
 * Sends a message to a target.
 *
 * @param {url} The target's URL.
 * @param {type} The type of message
 *               (see developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/responseType).
 *               A null, undefined, or empty string value yields the default value of 'text'.
 * @param {msg} Message to send.
 * @param {callback} An optional function to run after the message is sent successfully. It
 *                   is called with the response value of the XHR request
 *                   (see developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/response).
 */
async function send(url,type,msg,callback) {
    let xhr = new XMLHttpRequest();
    xhr.responseType = type? type : 'text';
    xhr.open('POST',url,true);
    if(callback)
        xhr.onreadystatechange = () => {
            if(
                xhr.readyState === XMLHttpRequest.DONE && (
                    xhr.status === 0 ||
                    200 <= status ||
                    status < 400
                )
            ) callback(xhr.response)
        };
    xhr.send(msg);
}
/*
 * Sends an encrypted text message to a target.
 *
 * The encryption method used is a hybrid RSA and AES routine. The client reqeusts a public
 * RSA-OAEP key by sending an empty message. The client then encrypts the message using AES
 * -CBC and encrypts the AES key using the RSA key. Finally, the client sends the data to
 * the server which can then decrypt the AES key using its private key.
 *
 * The server is expected to receive the initialization vector, RSA-encrypted AES key, and
 * the AES-encrypted message in order.
 *
 * @param {url} The target's URL.
 * @param {msg} Message to send.
 * @param {callback} An optional function to run after the message is sent successfully. It
 *                   is called with the response value of the XHR request
 *                   (see developer.mozilla.org/en-US/docs/Web/API/XMLHttpRequest/response).
 */
async function sendEncrypted(url,msg,callback) {
    send(url,'blob',null,async (response) => {
        const reader = new FileReader();
        reader.addEventListener('loadend',() => {
            const view = new Uint8Array(reader.result);
            const iv = window.crypto.getRandomValues(new Uint8Array(16));
            window.crypto.subtle.generateKey(
                // Generate AES key
                {
                    name:'AES-CBC',
                    length:128
                },
                true,
                ['encrypt','decrypt']
            ).then(key =>
                window.crypto.subtle.encrypt(
                    // Encrypt message with AES
                    {
                        name:'AES-CBC',
                        iv
                    },
                    key,
                    new TextEncoder().encode(msg)
                ).then(aes => [
                    key, // AES key
                    aes  // AES encrypted message
                ])
            ).then(aes =>
                window.crypto.subtle.exportKey(
                    'raw',
                    aes[0]
                ).then(out => [
                    b64Encode(out), // AES key
                    aes[1]          // AES encrypted message
                ])
            ).then(aes =>
                window.crypto.subtle.importKey(
                    // Import RSA public key from servlet
                    'spki',
                    view,
                    {
                        name:'RSA-OAEP',
                        hash:{
                            name:'SHA-256'
                        }
                    },
                    true,
                    ['encrypt']
                ).then(rsa => [
                    aes[0], // AES key
                    aes[1], // AES encrypted message
                    rsa     // RSA public key
                ])
            ).then(aes =>
                window.crypto.subtle.encrypt(
                    // Encrypt AES key with RSA
                    {
                        name:'RSA-OAEP'
                    },
                    aes[2],
                    new TextEncoder().encode(aes[0])
                ).then(rsa => [
                    rsa,   // RSA encrypted AES key
                    aes[1] // AES encrypted message
                ])
            ).then(out =>
                send(
                    url,
                    null,
                    b64Encode(iv)+','+
                    b64Encode(out[0])+','+
                    b64Encode(out[1]),
                    callback
                )
            );
        });
        reader.readAsArrayBuffer(response);
    });
}



const Field = React.forwardRef(({label, type}, ref) => {
    return (
      <div>
        <label style={labelStyle} >{label}</label>
        <input ref={ref} type={type} style={inputStyle} />
      </div>
    );
});


const printData = data => {
    const json = JSON.stringify(data, null, 4);
    alert(json);

};



const Form = (props) => {
    const usernameRef = React.useRef();
    const passwordRef = React.useRef();

    const handleSubmit = e => {
        e.preventDefault();
        const data = {
            username: usernameRef.current.value,
            password: passwordRef.current.value
        };
        sendEncrypted('/login', usernameRef.current.value + '\n' + passwordRef.current.value, printData(data));

    };
    return (
      <form style={formStyle} onSubmit={handleSubmit}  >
        <Field ref={usernameRef} label="Username:" type="text" />
        <Field ref={passwordRef} label="Password:" type="password" />
        <div>
          <button style={submitStyle} type="submit" >Submit</button>
        </div>
      </form>
    );
};



const domContainer2 = document.querySelector('#login_box_container');
//ReactDOM.render(React.createElement('div', null, 'Hello World'), domContainer2);
ReactDOM.render(e(Form), domContainer2);
