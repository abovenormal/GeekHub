import React, { useRef, useEffect, useState } from "react";
import "./css/Chat.css";
import { apiInstance } from "../api/index";
import axios from "axios";
import SockJS from "sockjs-client";
import { over } from "stompjs";
import SendIcon from "@mui/icons-material/Send";
import Loading from "../asset/image/loading.gif";

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
  const [loading, setLoading] = useState(true);
  const [message, setMessage] = useState("");
  const [nowSelected, setNowSelected] = useState("");
  const sendChatHandler = async () => {
    if (message === "") return;
    const now = new Date();
    const utcNow = now.getTime() + now.getTimezoneOffset() * 60 * 1000;
    const koreaTimeDiff = 9 * 60 * 60 * 1000;
    const koreaNow = new Date(utcNow + koreaTimeDiff);
    const time = new Date();
    time.setHours(koreaNow.getHours);
    time.setMinutes(koreaNow.getMinutes);
    const chatMessage = {
      sender: "1",
      content: message,
      roomId: roomIdx,
      timestamp: new Date(),
    };
    stompClient.send(`/app/sendMessage`, {}, JSON.stringify(chatMessage));
    setMessage("");
  };

  const onError = (err) => {
    throw err;
  };

  const onMessageReceived = () => {
    axios("https://k7c205.p.ssafy.io/api/chat/message", {
      method: "GET",
      params: {
        roomIdx: roomIdx,
      },
    })
      .then((res) => {
        setChat(res.data);
      })
      .catch((err) => console.log("Update Price error", err));
  };

  const onConnected = () => {
    if (stompClient && stompClient.connected) {
      stompClient.subscribe(`/chat/${roomIdx}`, onMessageReceived);
      setLoading(false);
    }
  };

  async function getData() {
    try {
      const res = await apiInstance().get("chat/rooms");
      setData(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
  }
  useEffect(() => {
    setLoading(true);
    let Sock = new SockJS("https://k7c205.p.ssafy.io/chatapi/endpoint");
    stompClient = over(Sock);
    stompClient.connect({}, onConnected, onError);
  }, [roomIdx]);
  useEffect(() => {
    getData();
  }, []);
  useEffect(() => {
    if (scrollRef && scrollRef.current)
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
  const onToggle = (schoolIdx) => {
    setNowSelected(schoolIdx);
  };
  useEffect(() => {
    let result = [];
    for (let i = 0; i < rows.length; i++) {
      result.push(
        <div className="desc-contact">
          <p className="name">{rows[i].localSchool}</p>
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
          {chat[i].userId == 1 ? (
            <>
              <div className="message text-only">
                <div className="response">
                  <p className="text"> {chat[i].content}</p>
                  <p className="response-time"> {chat[i].created_at}</p>
                </div>
              </div>
            </>
          ) : (
            <>
              <div className="username">{chat[i].name} 드라이버</div>
              <div className="message text-only">
                <p className="text">{chat[i].content}</p>
              </div>
              <p className="time"> {chat[i].created_at}</p>
            </>
          )}
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
        <div className="rows">
          <div className="discussions">
            <div className="discussion">
              <div className="discussion-title">👇🏻채팅방을 선택해주세요.</div>
            </div>
            {/* {rowsMap} */}
            {rowsMap.map((row, i) => (
              <div
                onClick={() => {
                  setRoomIdx(rows[i].id);
                  setRoomName(rows[i].localSchool);
                  onToggle(rows[i].id);
                  axios("https://k7c205.p.ssafy.io/api/chat/message", {
                    method: "GET",
                    params: {
                      roomIdx: rows[i].id,
                    },
                  })
                    .then((res) => {
                      setChat(res.data);
                      onToggle(rows[i].id);
                    })
                    .catch((err) => console.log("Update Price error", err));
                }}
                className={
                  nowSelected === rows[i].id
                    ? "discussion selected-chat"
                    : "discussion message-active"
                }
              >
                {row}
              </div>
            ))}
          </div>
          {!loading ? (
            <div className="chat">
              <div className="header-chat">
                <p className="name">{roomName} 드라이버님들과의 채팅방</p>
              </div>
              <div className="messages-chat" ref={scrollRef}>
                {chatMap}
              </div>
              <div className="footer-chat">
                <input
                  type="text"
                  className="write-message"
                  placeholder="메세지 입력"
                  value={message}
                  onKeyPress={(e) => {
                    if (e.key == "Enter") {
                      sendChatHandler();
                    }
                  }}
                  onChange={(e) => setMessage(e.target.value)}
                ></input>
                <SendIcon
                  className="clickable icon"
                  color="primary"
                  onClick={sendChatHandler}
                />
              </div>
            </div>
          ) : (
            <div className="chat">
              <div className="header-chat">
                <p className="name">{roomName} 드라이버님들과의 채팅방</p>
              </div>
              <div className="messages-chat-loading" ref={scrollRef}>
                <img src={Loading} className="loading"></img>
              </div>
              <div className="footer-chat">
                <div className="write-message" placeholder="메세지 입력"></div>
                <SendIcon className="clickable icon" color="primary" />
              </div>
            </div>
          )}
        </div>
      </div>
    </div>
  );
};
export default Chat;
