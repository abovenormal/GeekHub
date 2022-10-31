import React from "react";
import Dropdown from "../Components/Common/Dropdown"
import List from "../Components/Log/List";
import "./css/Log.css"
const Log = () => {
  return (
    <div className="container">
      <Dropdown />
      <List />
    </div>
  );
};
export default Log;
