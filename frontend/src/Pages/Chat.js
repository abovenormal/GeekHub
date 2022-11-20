import React, { useRef, useEffect, useState } from "react";
import "./css/Chat.css";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../api/index";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import axios from "axios";
import SockJS from "sockjs-client";
import { over } from 'stompjs';
import { now } from "moment";

let stompClient = null;
const Chat = () => {
  const scrollRef = useRef();

  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);
  const [rowsMap, setRowsMap] = useState([]);
  const [chat, setChat] = useState([]);
  const [chatMap, setChatMap] = useState([]);
  const [roomName, setRoomName] = useState("");
  const [roomIdx, setRoomIdx] = useState("");
  const [message, setMessage] = useState("");
  const sendChatHandler = async () => {
    if (message === '') return;
    const now = new Date();
    const utcNow = now.getTime() + (now.getTimezoneOffset() * 60 * 1000);
    const koreaTimeDiff = 9 * 60 * 60 * 1000;
    const koreaNow = new Date(utcNow + koreaTimeDiff);
    const time = new Date()
    time.setHours(koreaNow.getHours)
    time.setMinutes(koreaNow.getMinutes)
    console.log(time)
    const chatMessage = {
      sender : "1",
      content : message,
      roomId : roomIdx,
      timestamp : new Date()
    };
    console.log(chatMessage)
    console.log(JSON.stringify(chatMessage))
    stompClient.send(`/app/sendMessage`, {}, JSON.stringify(chatMessage));
    setMessage('');

  }

  const onError = err => {
    console.log("에러났다.`")
    throw err;
  }

  const onMessageReceived = () => {
    console.log("메시지 받았니?")
    axios("https://k7c205.p.ssafy.io/api/chat/message", {
              method: "GET",
              params: {
                roomIdx: roomIdx,
              },
            })
              .then((res) => {
                console.log(res);
                setChat(res.data);
              })
              .catch((err) => console.log("Update Price error", err));
  }

  const onConnected = () => {
    console.log("커넥트 확인")
    stompClient.subscribe(`/chat/${roomIdx}`, onMessageReceived);
  }

  async function getData() {
    try {
      const res = await apiInstance().get("chat/rooms");
      console.log(res.data);
      setData(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
  }
  useEffect(() => {
    console.log("연결 시작")
    let Sock = new SockJS("https://k7c205.p.ssafy.io/chatapi/endpoint")
    stompClient = over(Sock)
    stompClient.debug = null;
    console.log(roomIdx)
    stompClient.connect({}, onConnected, onError);
  }, [roomIdx])
  useEffect(() => {
    getData();
  }, []);
  useEffect(() => {
    scrollRef.current.scrollTop = scrollRef.current.scrollHeight;
  });
  useEffect(() => {
    let result = [];
    for (let i = 0; i < data.length; i++) {
      let item = data[i];
      let temp = {};
      temp.id = item._id;
      temp.localSchool = item.localSchool;
      result.push(temp);
    }
    setRows(result);
  }, [data]);
  useEffect(() => {
    let result = [];
    for (let i = 0; i < rows.length; i++) {
      result.push(
        <div
          className="discussion message-active"
          onClick={(e) => {
            // console.log(rows[i].id)
            setRoomIdx(rows[i].id)
            setRoomName(rows[i].localSchool);
            axios("https://k7c205.p.ssafy.io/api/chat/message", {
              method: "GET",
              params: {
                roomIdx: rows[i].id,
              },
            })
              .then((res) => {
                // console.log(res);
                setChat(res.data);
              })
              .catch((err) => console.log("Update Price error", err));
            console.log(rows[i]);
          }}

        >
          <div className="desc-contact">
            <p className="name">{rows[i].localSchool}</p>
          </div>
        </div>
      );
    }
    setRowsMap(result);
  }, [rows]);
  useEffect(() => {
    let result = [];
    for (let i = 0; i < chat.length; i++) {
      result.push(
        <>
          <div className="message">
            <p className="text"> {chat[i].content}</p>
          </div>
          <p className="time"> {chat[i].created_at}</p>
        </>
      );
    }
    setChatMap(result);
  }, [chat]);
  const columns = [
    {
      field: "localSchool",
      headerName: "방 이름",
      width: 200,
      editable: true,
    },
  ];

  return (
    <div className="chat-container">
      <div className="container">
        <div className="row">
          <div className="discussions">
            <div className="discussion search">
              <div className="searchbar">채팅방 목록</div>
            </div>
            {rowsMap}
          </div>
          <div className="chat">
            <div className="header-chat">
              <i className="icon fa fa-user-o" aria-hidden="true"></i>
              <p className="name">{roomName}</p>
              <i
                className="icon clickable fa fa-ellipsis-h right"
                aria-hidden="true"
              ></i>
            </div>
            <div className="messages-chat" ref={scrollRef}>
              {chatMap}
            </div>
            <div className="footer-chat">
              <i
                className="icon fa fa-smile-o clickable"
                style={{ fontSize: 25 }}
                aria-hidden="true"
              ></i>
              <input
                type="text"
                className="write-message"
                placeholder="메세지 입력"
                value={message}
                onChange={e => setMessage(e.target.value)}
              ></input>
              <i
                className="icon send fa fa-paper-plane-o clickable"
                aria-hidden="true"
                c0js0
                onClick={sendChatHandler}
              ></i>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
export default Chat;
