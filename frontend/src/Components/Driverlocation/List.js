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
import {apiInstance} from "../../api/index"
const List = props => {
  const listData = props.listData

  function Row(props) {
    const { row } = props;
    const [open, setOpen] = useState(false);
    const API = apiInstance();
    const [longitude, setLongitude] = useState("0");
    const [latitude, setLatitude] = useState("0");
    
    useEffect(() => {
      const script = document.createElement("script");
      console.log(longitude)
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
      try {
        const res = await API.get('/location/getLocation', {params : {driver:id}})
        console.log(res.data)
        setLatitude(res.data.latitude)
        setLongitude(res.data.longitude)
        setOpen(!open)
      }
      catch (err) {
        console.log(err)
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
                GetLocation(row.id)
              }
              }
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell
            component="th"
            scope="row"
            onClick={() => {
              GetLocation(row.id)
            }}
            sx={{
              cursor: "pointer",
            }}
          >
            {row.name}
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell style={{ paddingBottom: 0, paddingTop: 0 }} colSpan={12}>
            <Collapse in={open} timeout="auto" unmountOnExit>
              <div className="table">
                <Box sx={{ width: "100%" }}>
                  <Typography variant="h6" gutterBottom component="div">
                    배달 현황
                  </Typography>

                  <Table size="small" aria-label="purchases">
                    <TableHead>
                      <TableRow>
                        <TableCell>픽업존</TableCell>
                        <TableCell>도착 예정</TableCell>
                        <TableCell>도착 시각</TableCell>
                        <TableCell>픽업 사진</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {row.task.map((taskRow) => (
                        <TableRow key={taskRow.pickupZone}>
                          <TableCell component="th" scope="row">
                            {taskRow.pickupZone}
                          </TableCell>
                          <TableCell>{taskRow.arrivalScheduled}</TableCell>
                          <TableCell>{taskRow.arrivalTime}</TableCell>
                          <TableCell>
                            <div
                              className="pickupPic"
                              onClick={() => {
                                const pickupPicture = taskRow.pickupPicture;
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
                  <h3 className="now-title">
                    <div>기사님의 현재 위치</div>
                    <div
                      className="now-icon"
                      // onClick={()=> {GetLocation(row.id)}}
                      onClick={() => {GetLocation(row.id)}}
                      >
                      <RefreshIcon
                      sx={{
                        width: "1rem",
                        "&:hover": {
                          color: "#10b981",
                          backgroundColor: "rgba( 0, 0, 0, 0.08 )",
                        },
                      }} />
                    </div>
                  </h3>
                  <div id="map_div"></div>
                  <p>위도 : {latitude}</p>
                  <p>경도 : {longitude}</p>
                  
                </div>
              </div>
            </Collapse>
          </TableCell>
        </TableRow>
      </React.Fragment>
    );
  }
  
  const rows = listData;
  
  return (
      <TableContainer component={Paper}>
        <Table aria-label="collapsible table">
          <TableHead>
            <TableRow></TableRow>
          </TableHead>
          <TableBody sx={{ width: 1000 }}>
            {rows.map((row) => (
              <Row key={row.name} row={row}></Row>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
  );
};

export default List;
