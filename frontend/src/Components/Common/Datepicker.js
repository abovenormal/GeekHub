import React, { useState, useEffect } from 'react';

import TextField from '@mui/material/TextField';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

const Datepicker = props => {
  let today = new Date();   
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1;  // 월
  let date = today.getDate();  // 날짜
  const selected = props.selected
  const setSelected = props.setSelected
  const [value, setValue] = useState(today);
  useEffect(()=> {
    if (value !== today) {
    const nextInfo ={
      ...selected,
      "date": `${value.$y}-${value.$M + 1}-${value.$D}`
    }
    setSelected(nextInfo)
  }
  }, [value])
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        className="date-picker"
        label="날짜"
        value={selected.date}
        name="date"
        onChange={(newValue) => {
          setValue(newValue)
        }}
        renderInput={(params) => <TextField {...params} /> }
        sx={{
          width:'300px'
        }}
      />
    </LocalizationProvider>
  );
}

export default Datepicker;