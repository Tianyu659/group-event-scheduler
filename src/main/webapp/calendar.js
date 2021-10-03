'use strict';

const { useState } = React;
const e = React.createElement;

const Calendar = (props) => {

    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentMonth, setCurrentMonth] = useState(currentDate.getMonth());
    const [selectedDate, setSelectedDate] = useState(new Date());

    const formatMonth = (date) => {
        var strArray=['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        var d = date.getDate();
        var m = strArray[date.getMonth()];
        var y = date.getFullYear();
        return '' + m + '-' + y;
    }

    const setHeader = () => {

        return (
            <div className="header row flex-middle">
                <div className="col col-start">
                    <div className="icon" onClick={prevMonth}>
                        chevron_left
                    </div>
                </div>
                <div className="col col-center">
                    <span>
                        {formatMonth(currentDate)}
                    </span>
                </div>
                <div className="col col-end" onClick={nextMonth}>
                    <div className="icon">chevron_right</div>
                </div>
            </div> 
        );
    }

    const setDays = () => {
        console.log("days");
        var weekdays = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];
        const days = [];

        let start = currentDate.getDay();

        for(let i = 0; i < 7; i++) {
            var day = weekdays[(start+i) % 7];
            days.push(<div className="col col-center" key={i}>{day}</div>)
        }

        return <div className="days row">{days}</div>
    }

    const setCells = () => {
        console.log("cells");
        var date = currentDate;
        var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
        var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

        const rows = [];
        let days = [];
        let day = firstDay;

        while(day <= lastDay) {
            const dayHolder = day;
            days.push(
                <div className={`col cell ${day == selectedDate ? "selected" : ""}`} key={day} onClick={(e) => onClickDate(e, day)}>
                    <span className="number">{day.getDate()}</span>
                    <span className="bg">{day.getDate()}</span>
                </div>
            );
            const nextDate = new Date(day.getFullYear(), day.getMonth(), day.getDate()+1);
            day = nextDate;
        }
        rows.push(
            <div className="row" key={day}>
                {days}
            </div>
        );
        days = [];

        return <div className="body">{rows}</div>
    }

    const onClickDate = (event, day) => {
        setSelectedDate(day);
    }

    const nextMonth = () => {
        var newDate = new Date(currentDate.setMonth(currentDate.getMonth()+1));
        setCurrentDate(newDate);
    }

    const prevMonth = () => {
        var newDate = new Date(currentDate.setMonth(currentDate.getMonth()-1));
        setCurrentDate(newDate);
    }

    return (
        <div>
            <header> 
                <div id="logo">
                    <span className="icon">date_range</span>
                    <span><b>calendar</b></span>
                </div>
            </header>
            <main>
                <div className="calendar">
                    {setHeader()}
                    {setDays()}
                    {setCells()}
                </div>
            </main>
        </div>
    );

}

const domCalendarContainer = document.querySelector('#calendar-component');
ReactDOM.render(e(Calendar), domCalendarContainer);

