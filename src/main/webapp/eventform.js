'use strict';

const { useState } = React;
const e = React.createElement;

const EventForm = (props) => {

    const [creatingUser, setCreatingUser] = useState(''); // username of user that clicked 'create event'
    const [events, setEvents] = useState(data); // list of events
    const [times, setTimes] = useState([]);
    const [selectedEvent, setSelectedEvent] = useState(0); // id of event. 
    const [selectedTimes, setSelectedTimes] = useState([]);
    const [invitedUsers, setInvitedUsers] = useState([]);

    const setActiveEvent = (e, id) => {
        if(selectedEvent != 0) document.getElementById(selectedEvent).classList.remove("border");
        setSelectedEvent(id);
        document.getElementById(id).classList.add("border");
    }

    const toggleTime = (e, section, num) => {
        let id = "";
        id += section;
        id += num;
        if(selectedTimes.includes(num)) {
            var oldArr = selectedTimes.filter(i => i != num);
            setSelectedTimes(oldArr);
            document.getElementById(id).classList.remove("border");
        } else {
            var newArr = selectedTimes;
            newArr.push(num);
            setSelectedTimes(newArr);
            document.getElementById(id).classList.add("border");
        }
    }

    return (
        <div>

            <div className="event-form-holder">

                <h1 className="header">create event</h1> 

                <div className="form-section">
                    <span className="form-label">select event</span>
                    
                    <div> 
                        {events.map((e) => {
                            let days = "";
                            for(let i = 0; i < e.days.length; i++) {
                                days += e.days[i];
                                days += " ";
                            }
                            return (
                                <div className="event-section" key={e.name} id={e.id} onClick={(ev) => setActiveEvent(ev, e.id)}>
                                    <span>{e.name}</span>
                                    <br />
                                    <span>{e.location}</span> 
                                    <br />
                                    <span>{days}</span>
                                </div>
                            );
                        })}
                    </div>
                </div>

                <div className="form-section">
                    <span className="form-label">invite users</span>
                    
                    <div> 
                        <span>User 1: </span>
                        <input type="text"></input>
                        <br />
                        <span>User 2: </span>
                        <input type="text"></input>
                        <br />
                        <span>User 3: </span>
                        <input type="text"></input>
                        <br />
                        <span>User 4: </span>
                        <input type="text"></input>
                        <br />
                        <span>User 5: </span>
                        <input type="text"></input>
                        <br />
                    </div>
                </div>

                <div className="form-section">
                    <span className="form-label">select times</span>
                    
                    <div> 
                        <div className="time-section-container">
                            <div className="time-section-holder">
                                <div id="a7" className="time-section" onClick={(e) => toggleTime(e, "a", 7)}><span>7:00 AM</span></div>
                                <div id="a8" className="time-section" onClick={(e) => toggleTime(e, "a", 8)}><span>8:00 AM</span></div>
                                <div id="a9" className="time-section" onClick={(e) => toggleTime(e, "a", 9)}><span>9:00 AM</span></div>
                                <div id="a10" className="time-section" onClick={(e) => toggleTime(e, "a", 10)}><span>10:00 AM</span></div>
                                <div id="a11" className="time-section" onClick={(e) => toggleTime(e, "a", 11)}><span>11:00 AM</span></div>
                                <div id="a12" className="time-section" onClick={(e) => toggleTime(e, "a", 12)}><span>12:00 PM</span></div>
                            </div>
                            <div className="time-section-holder">
                                <div id="a13" className="time-section" onClick={(e) => toggleTime(e, "a", 13)}><span>1:00 PM</span></div>
                                <div id="a14" className="time-section" onClick={(e) => toggleTime(e, "a", 14)}><span>2:00 PM</span></div>
                                <div id="a15" className="time-section" onClick={(e) => toggleTime(e, "a", 15)}><span>3:00 PM</span></div>
                                <div id="a16" className="time-section" onClick={(e) => toggleTime(e, "a", 16)}><span>4:00 PM</span></div>
                                <div id="a17" className="time-section" onClick={(e) => toggleTime(e, "a", 17)}><span>5:00 PM</span></div>
                                <div id="a18" className="time-section" onClick={(e) => toggleTime(e, "a", 18)}><span>6:00 PM</span></div>
                            </div>
                            <div className="time-section-holder">
                                <div id="a19" className="time-section" onClick={(e) => toggleTime(e, "a", 19)}><span>7:00 PM</span></div>
                                <div id="a20" className="time-section" onClick={(e) => toggleTime(e, "a", 20)}><span>8:00 PM</span></div>
                                <div id="a21" className="time-section" onClick={(e) => toggleTime(e, "a", 21)}><span>9:00 PM</span></div>
                                <div id="a22" className="time-section" onClick={(e) => toggleTime(e, "a", 22)}><span>10:00 PM</span></div>
                                <div id="a23" className="time-section" onClick={(e) => toggleTime(e, "a", 23)}><span>11:00 PM</span></div>
                                <div id="a24" className="time-section" onClick={(e) => toggleTime(e, "a", 24)}><span>12:00 PM</span></div>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="form-section">
                    <button type="button" className="btn btn-primary form-button-submit">Submit</button>
                </div>
            </div>

        </div>
    );

}

const domCalendarContainer = document.querySelector('#eventform-component');
ReactDOM.render(e(EventForm), domCalendarContainer);