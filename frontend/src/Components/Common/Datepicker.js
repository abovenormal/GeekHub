import React, { useState, useEffect } from 'react';

import TextField from '@mui/material/TextField';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';

const Datepicker = props => {
  const selected = props.selected
  const setSelected = props.setSelected
  const [value, setValue] = useState('11-18-22');
  useEffect(()=> {
    if (value) {
    const nextInfo ={
      ...selected,
      "date" : `${value.$y}-${value.$M + 1}-${value.$D}`
    }
    setSelected(nextInfo)
  }
  }, [value])
  return (
    <LocalizationProvider dateAdapter={AdapterDayjs}>
      <DatePicker
        className="date-picker"
        label="Date"
        value={value}
        onChange={(newValue) => {
          setValue(newValue);
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