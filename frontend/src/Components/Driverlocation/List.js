import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import Box from "@mui/material/Box";
import Collapse from "@mui/material/Collapse";
import IconButton from "@mui/material/IconButton";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Typography from "@mui/material/Typography";
import Paper from "@mui/material/Paper";
import KeyboardArrowDownIcon from "@mui/icons-material/KeyboardArrowDown";
import KeyboardArrowUpIcon from "@mui/icons-material/KeyboardArrowUp";
import Swal from "sweetalert2";
import RefreshIcon from "@mui/icons-material/Refresh";
import "./css/List.css";
import marker from "../../asset/image/marker.png"
import { apiInstance } from "../../api/index";
import { Looks } from "@material-ui/icons";
const List = (props) => {
  
  function Row(props) {
    const { row } = props;
    const [open, setOpen] = useState(false);
    const API = apiInstance();
    const [longitude, setLongitude] = useState("0");
    const [latitude, setLatitude] = useState("0");
    const [timestamp, setTimestamp] = useState("0");
    let allTask = 0;
    let completeTask = 0;
    
    {row.spotResponseDtoList.map((c) => {
      console.log(`c is ${c}`)
      if (c.status === 2) {
        allTask += 1
        completeTask += 1
      } else if (c.status === 1) {
        allTask += 1
      }
    })}
    const successRatio = parseInt((completeTask / allTask) * 100);
    useEffect(() => {
      const script = document.createElement("script");
      // console.log(longitude);
      script.innerHTML = `
          function initTmap() {
              var map = new Tmapv2.Map("map_div", {
                  center: new Tmapv2.LatLng(${latitude},${longitude}),
                  width: "100%",
                  height: "100%",
                  zoom:17
              });
          
              var marker = new Tmapv2.Marker({
                position: new Tmapv2.LatLng(${latitude},${longitude}),
                map: map,
                icon: '${marker}',
                iconSize : new Tmapv2.Size(30,44)
              });	
            }
          initTmap();
     `;
      script.type = "text/javascript";
      script.async = "async";
      document.head.appendChild(script);
    }, [open]);
    async function GetLocation(userIdx) {
      // 열릴 때만 Get 요청 보내기 위해 분기 추가 했다가
      // 열린 채로 새로 고침 안 되서 잠시 삭제
      // 아래 if문 추가로 해결 완료
      if ( allTask > 0 ) {
      try {
        const res = await API.get("/location/getLocation", {
          params: { driver: userIdx },
        });
        // console.log(res.data);
        setLatitude(res.data.latitude);
        setLongitude(res.data.longitude);
        setTimestamp(res.data.timestamp); // timestamp
        setOpen(!open);
      } catch (error) {
        if (error.response.status === 400) {
          // console.log(error.response.status);
          setOpen(!open);
          setLatitude(0);
          setLongitude(0);
          setTimestamp("");
        }
      }
    }
    }
    return (
      <React.Fragment>
        <TableRow
          sx={{
            "& > *": {
              borderBottom: "unset",
            },
            "&:hover": {
              color: "#10b981",
              backgroundColor: "rgba( 0, 0, 0, 0.08 )",
            },
          }}
        >
          <TableCell
            sx={{
              width: "10%",
            }}
          >
            <IconButton
              aria-label="expand row"
              size="small"
              onClick={() => {
                // console.log(row);
                GetLocation(row.userIdx);
                
              }}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell
            component="th"
            scope="row"
            onClick={() => {
              GetLocation(row.userIdx);
            }}
            sx={{
              cursor: "pointer",
            }}
          >
            <div className="list-username">{row.userName}</div>
            
            {(allTask === 0) ? <div>오늘 일 없음ㅋㅋ</div> : (allTask === completeTask) ? <div>100% 완료</div> : <div className="success-ratio">{successRatio}% 진행 중</div>}
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={12}>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <div className="table">
                <Box sx={{ width: "100%" }}>
                  <Typography
                    variant="h6"
                    gutterBottom
                    component="div"
                  ></Typography>
                  <Table size="small" aria-label="purchases">
                    <TableHead>
                      <TableRow>
                        <TableCell>
                          <div className="list-header">분류</div>
                        </TableCell>
                        <TableCell>
                          <div className="list-header">장소</div>
                        </TableCell>
                        <TableCell>
                          <div className="list-header">도착 예정</div>
                        </TableCell>
                        <TableCell>
                          <div className="list-header">도착 시각</div>
                        </TableCell>
                        <TableCell>
                          <div className="list-header">오차</div>
                        </TableCell>
                        <TableCell>
                          <div className="list-header">사진</div>
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody >
                      {row.spotResponseDtoList.map((taskRow, index) => (
                        <TableRow key={index} className={(taskRow.spotCategory === "DESTINATION") ? "list-destination" : (taskRow.spotCategory==="HUB") ? "list-hub" : ""}>
                          <TableCell component="th" scope="row" sx={{width:"10%"}}>
                            <div className="list-body">
                              {(taskRow.spotCategory==="STORE") ? "[픽업] " : (taskRow.spotCategory==="DESTINATION") ? "[배달] " : "[허브] "}
                            </div>
                          </TableCell>
                          <TableCell>
                            <div className="list-body">
                            {taskRow.spotName}
                            </div>
                          </TableCell>
                          <TableCell>
                            <div className="list-body">
                              {taskRow.expectedTime.slice(11, 16)}
                            </div>
                          </TableCell>
                          <TableCell>
                            <div className="list-body">
                              {taskRow.arrivedTime !== null
                                ? taskRow.arrivedTime.slice(11, 16)
                                : ""}
                            </div>
                          </TableCell>
                          <TableCell>
                            <div
                              className={`list-body + ${
                                taskRow.dif > 0 ? "dif-red" : taskRow.dif < 0 ? "dif-blue" : ""
                              }`}
                            >
                              {taskRow.arrivedTime !== null
                                ? `${taskRow.dif}분`
                                : ""}
                            </div>
                          </TableCell>
                          <TableCell>
                            {(!taskRow.imageUrl) ? "" : (taskRow.spotCategory === "STORE") ? <div
                              className="pickupPic"
                              onClick={() => {
                                const pickupPicture = taskRow.imageUrl;
                                Swal.fire({
                                  // title: '픽업 사진',
                                  text: "픽업을 완료하였습니다.",
                                  imageUrl: pickupPicture,
                                  imageWidth: 300,
                                  imageHeight: 300,
                                  imageAlt: "Pickup image",
                                });
                              }}
                            >
                              보기
                            </div> : <div
                              className="pickupPic"
                              onClick={() => {
                                const pickupPicture = taskRow.imageUrl;
                                Swal.fire({
                                  // title: '픽업 사진',
                                  text: "배달을 완료하였습니다.",
                                  imageUrl: pickupPicture,
                                  imageWidth: 300,
                                  imageHeight: 300,
                                  imageAlt: "Drop image",
                                });
                              }}
                            >
                              보기
                            </div> }
                            
                          </TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </Box>
                <div className="now-location">
                  <div className="now-title">
                    기사님의 최근 위치 | {timestamp}
                    <div
                      className="now-icon"
                      // onClick={()=> {GetLocation(row.id)}}
                      onClick={() => {
                        GetLocation(row.userIdx);
                      }}
                    >
                      <RefreshIcon
                        sx={{
                          width: "1rem",
                          "&:hover": {
                            color: "#10b981",
                            backgroundColor: "rgba( 0, 0, 0, 0.08 )",
                          },
                        }}
                      />
                    </div>
                  </div>
                  {latitude === 0 && longitude === 0 ? (
                    <h4>조회된 데이터가 없습니다.</h4>
                  ) : (
                    <div className="now-map" id="map_div"></div>
                  )}
                </div>
              </div>
            </Collapse>
          </TableCell>
        </TableRow>
        
      </React.Fragment>
    );
  }

  const rows = props.listData;

  return (
    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow></TableRow>
        </TableHead>
        <TableBody sx={{ width: 1000 }}>
          {rows.map((row, index) => (
            <Row key={index} row={row}></Row>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default List;
