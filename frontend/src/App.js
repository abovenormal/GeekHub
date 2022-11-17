import React from "react";
import { Route, Routes } from "react-router-dom";
import PrivateRoute from "./Components/Login/PrivateRoute";
import PublicRoute from "./Components/Login/PublicRoute";
import Nav from "./Components/Common/Nav";
// import Sidebar from "./Components/Common/Sidebar"
import Dashboard from "./Pages/Dashboard";
import Chat from "./Pages/Chat";
import Driverlocation from "./Pages/Driverlocation";
import Log from "./Pages/Log";
import Login from "./Pages/Login";
import User from "./Pages/User";
import Signup from "./Pages/Signup";
import Drivermap from "./Pages/Drivermap";
import Worklist from "./Pages/Worklist";
import "./App.css";
import { apiInstance } from "./api/index";

function App() {
  const token = localStorage.getItem("accesstoken");
  const isLogin = !!token;
  async function getData() {
    try {
      const res = await apiInstance().get("spot/test");
      console.log(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  setInterval(() => {
    getData();
    console.log(new Date());
  }, 30000);
  // const isLogin = true
  return (
    <div className="App-container">
      {isLogin ? <Nav /> : <></>}
      <Routes>
        <Route
          path="/"
          element={
            <PrivateRoute authenticated={token} component={<Dashboard />} />
          }
        />
        <Route
          path="/chat"
          element={<PrivateRoute component={<Chat />} authenticated={token} />}
        />
        <Route
          path="/driverlocation"
          element={
            <PrivateRoute
              component={<Driverlocation />}
              authenticated={token}
            />
          }
        />
        <Route
          path="/login"
          element={<PublicRoute component={<Login />} authenticated={token} />}
        />
        <Route
          path="/user"
          element={<PrivateRoute component={<User />} authenticated={token} />}
        />
        <Route
          path="/signup"
          element={
            <PrivateRoute component={<Signup />} authenticated={token} />
          }
        />
        <Route
          path="/log"
          element={<PrivateRoute component={<Log />} authenticated={token} />}
        />
        <Route
          path="/drivermap"
          element={
            <PrivateRoute component={<Drivermap />} authenticated={token} />
          }
        />
        <Route
          path="/worklist"
          element={
            <PrivateRoute component={<Worklist />} authenticated={token} />
          }
        />
      </Routes>
      {/* <Routes>
            <Route path="/" element={<Dashboard />}/>
            <Route path="/chat" element={<Chat />}/>
            <Route path="/driverlocation" element={<Driverlocation/>}/>
            <Route path="/log" element={<Log />}/>
            <Route path="/login" element={<Login />}/>
            <Route path="/user" element={<User />}/>
            <Route path="/signup" element={<Signup />}/>
            <Route path="*" element={<PageNotFound />} />
          </Routes> */}
    </div>
  );
}

export default App;
