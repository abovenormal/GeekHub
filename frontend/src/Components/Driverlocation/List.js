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
import { apiInstance } from "../../api/index";
const List = (props) => {

  function Row(props) {
    const { row } = props;
    const [open, setOpen] = useState(false);
    const [nowOpen, setNowOpen] = useState(false);
    const API = apiInstance();
    const [longitude, setLongitude] = useState("0");
    const [latitude, setLatitude] = useState("0");
    const [timestamp, setTimestamp] = useState("0");

    useEffect(() => {
      const script = document.createElement("script");
      console.log(longitude);
      script.innerHTML = `
          function initTmap() {
              var map = new Tmapv2.Map("map_div", {
                  center: new Tmapv2.LatLng(${latitude},${longitude}),
                  width: "100%",
                  height: "100%",
                  zoom:16
              });
          
              var marker = new Tmapv2.Marker({
                position: new Tmapv2.LatLng(${latitude},${longitude}),
                map: map
              });	
            }
          initTmap();
     `;
      script.type = "text/javascript";
      script.async = "async";
      document.head.appendChild(script);
    }, [open]);
    async function GetLocation(id) {
      // 열릴 때만 Get 요청 보내기 위해 분기 추가 했다가
      // 열린 채로 새로 고침 안 되서 잠시 삭제
      // 아래 if문 추가로 해결 완료
      try {
        const res = await API.get("/location/getLocation", {
          params: { driver: id },
        });
        console.log(res.data);
        setLatitude(res.data.latitude);
        setLongitude(res.data.longitude);
        setTimestamp(res.data.timestamp); // timestamp
        setOpen(!open);
      } catch (err) {
        console.log(err);
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
                // GetLocation(row.id)
                GetLocation(1);
              }}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell
            component="th"
            scope="row"
            onClick={() => {
              // GetLocation(row.id)
              GetLocation(1);
            }}
            sx={{
              cursor: "pointer",
            }}
          >
            <div className="list-username">{row.userName}</div>
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={12}>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <div className="table">
                <Box sx={{ width: "100%" }}>
                  <Typography variant="h6" gutterBottom component="div">
                    
                  </Typography>

                  <Table size="small" aria-label="purchases">
                    <TableHead>
                      <TableRow>
                        <TableCell><div className="list-header">픽업존</div></TableCell>
                        <TableCell><div className="list-header">도착 예정</div></TableCell>
                        <TableCell><div className="list-header">도착 시각</div></TableCell>
                        <TableCell><div className="list-header">오차</div></TableCell>
                        <TableCell><div className="list-header">픽업 사진</div></TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {row.spotResponseDtoList.map((taskRow) => (
                        <TableRow key={taskRow.spotName}>
                          <TableCell component="th" scope="row">
                            <div className="list-body">{taskRow.spotName}</div>
                          </TableCell>
                          <TableCell><div className="list-body">{taskRow.expectedTime.slice(11,16)}</div></TableCell>
                          <TableCell><div className="list-body">{taskRow.arrivedTime.slice(11,16)}</div></TableCell>
                          <TableCell><div className={`list-body + ${(taskRow.dif > 0) ? "dif" : ""}`}>{taskRow.dif}분</div></TableCell>
                          <TableCell>
                            <div
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
                            </div>
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
                        GetLocation(1);
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
                  <div className="now-map" id="map_div"></div>
                </div>
              </div>
            </Collapse>
          </TableCell>
        </TableRow>
      </React.Fragment>
    );
  }

  const rows = props.listData;
  console.log(rows)
  return (
    <TableContainer component={Paper}>
      <Table aria-label="collapsible table">
        <TableHead>
          <TableRow></TableRow>
        </TableHead>
        <TableBody sx={{ width: 1000 }}>
          {rows.map((row) => (
            <Row key={row.userName} row={row}></Row>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default List;
