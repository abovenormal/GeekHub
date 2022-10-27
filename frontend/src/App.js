import { Route, Routes } from "react-router-dom";

import Nav from "./Components/Common/Nav"
import Dashboard from "./Pages/Dashboard";
import Chat from "./Pages/Chat";
import Driverlocation from "./Pages/Driverlocation";
import Log from "./Pages/Log";
import Login from "./Pages/Login";
import User from "./Pages/User";
import Signup from "./Pages/Signup";
import './App.css';

function App() {
  return (
    <div className="App">
      <Nav />
      <header className="App-header">
        <Routes>
          <Route path="/" element={<Dashboard />} />
          <Route path="/chat" element={<Chat />}/>
          <Route path="/driverlocation" element={<Driverlocation/>}/>
          <Route path="/log" element={<Log />}/>
          <Route path="/login" element={<Login />}/>
          <Route path="/user" element={<User />}/>
          <Route path="/signup" element={<Signup />}/>
        </Routes>
      </header>
    </div>
  );
}

export default App;
