import React, { useState, useEffect } from "react";
import InputLabel from "@mui/material/InputLabel";
import MenuItem from "@mui/material/MenuItem";
import FormControl from "@mui/material/FormControl";
import Select from "@mui/material/Select";
import "./css/Timebelt.css"
const Timebelt = props => {
  const timebelt = props.timebelt;
  const setTimebelt = props.setTimebelt;

  const onChange = (e) => {
    console.log(e.target.value);
    const nextInfo = {
      [e.target.name] : e.target.value
    }
    setTimebelt(nextInfo);
  }

  return (
    <div className="dropdown">
      <FormControl variant="standard" sx={{ m: 1, minWidth: 100 }}>
        <InputLabel id="demo-simple-select-standard-label">타임벨트</InputLabel>
        <Select
          labelId="demo-simple-select-standard-label"
          id="timebelt"
          value={timebelt}
          onChange={onChange}
          label="타임벨트"
          name="timebelt"
        >
          <MenuItem value={"점심1"}>점심1</MenuItem>
          <MenuItem value={"저녁1"}>저녁1</MenuItem>
        </Select>
      </FormControl>
    </div>
  );
};
export default Timebelt;
