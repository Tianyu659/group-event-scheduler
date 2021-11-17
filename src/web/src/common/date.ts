export const DAYS = [
  "Sunday",
  "Monday",
  "Tuesday",
  "Wednesday",
  "Thursday",
  "Friday",
  "Saturday",
];
export const MONTHS = [
  "January",
  "February",
  "March",
  "April",
  "May",
  "June",
  "July",
  "August",
  "September",
  "October",
  "November",
  "December",
];

function pad(number: number): string {
  const result = "0" + number;
  return result.slice(result.length - 2);
}

export function yesterday(): Date {
  const date = new Date();
  date.setDate(date.getDate() - 1);
  return date;
}

export function isSameDate(left: Date, right: Date): boolean {
  return (
    left.getFullYear() === right.getFullYear() &&
    left.getMonth() === right.getMonth() &&
    left.getDate() === right.getDate()
  );
}

export function formatMonth(date: Date): string {
  return `${MONTHS[date.getMonth()]} ${date.getFullYear()}`;
}

export function formatMonthRange(start: Date, end: Date): string {
  if (start.getFullYear() === end.getFullYear()) {
    return `${MONTHS[start.getMonth()]} to ${
      MONTHS[end.getMonth()]
    } ${end.getFullYear()}`;
  } else {
    return `${formatMonth(start)} to ${formatMonth(end)}`;
  }
}

export function formatDayMonthDate(date: Date, useEnglish = false): string {
  if (useEnglish) {
    if (isSameDate(date, yesterday())) {
      return "yesterday";
    } else if (isSameDate(date, new Date())) {
      return "today";
    }
  }
  return `${DAYS[date.getDay()]}, ${MONTHS[date.getMonth()]} ${date.getDate()}`;
}

export function formatMonthYear(date: Date): string {
  return `${MONTHS[date.getMonth()]} ${date.getFullYear()}`;
}

export function formatTime(date: Date, useEnglish = false): string {
  if (useEnglish) {
    const difference = Math.floor(
      (new Date().valueOf() - date.valueOf()) / 1000 / 60
    );
    if (difference < 60) {
      return `${difference} minutes ago`;
    }
  }
  let hours = date.getHours();
  const meridian = hours < 12 ? "AM" : "PM";
  if (hours > 12) {
    hours -= 12;
  } else if (hours === 0) {
    hours = 12;
  }
  return `${hours}:${pad(date.getMinutes())} ${meridian}`;
}

export function formatDateTime(date: Date, useEnglish = false): string {
  if (useEnglish) {
    const difference = Math.floor(
      (new Date().valueOf() - date.valueOf()) / 1000 / 60
    );
    if (difference < 60) {
      return `${difference} minutes ago`;
    }
  }
  return `${formatDayMonthDate(date, useEnglish)} at ${formatTime(
    date,
    false
  )}`;
}

export function formatDateTimeShort(date: Date, useEnglish = true): string {
  const today = new Date();
  if (useEnglish) {
    if (isSameDate(date, yesterday())) {
      return "Yesterday";
    } else if (isSameDate(date, today)) {
      return "Today";
    }
  }
  if (
    today.getMonth() === date.getMonth() &&
    today.getFullYear() === date.getFullYear()
  ) {
    return `${MONTHS[date.getMonth()]} ${date.getDate()}`;
  }
  return `${MONTHS[date.getMonth()]} ${date.getDate()}`;
}

export function formatDuration(difference: number): string {
  const seconds = difference / 1000;
  if (seconds < 60) {
    const s = Math.round(seconds);
    return `${s.toFixed(0)} second${s === 1 ? "" : "s"}`;
  }
  const minutes = seconds / 60;
  if (minutes < 60) {
    const m = Math.round(minutes);
    return `${m.toFixed(0)} minute${m === 1 ? "" : "s"}`;
  }
  const h = Math.floor(minutes / 60);
  if (h < 24) {
    return `${h.toFixed(0)} hour${h === 1 ? "" : "s"}`;
  }
  const d = Math.floor(minutes / 60 / 24);
  return `${d.toFixed(0)} day${d === 1 ? "" : "s"}`;
}
