import React, { useState } from "react";
import "./css/List.css";
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
import TablePagination from '@mui/material/TablePagination';
import Swal from "sweetalert2";
import Toast from "../../utils/Toast"
import { apiInstance } from "../../api/index";
import logoDotBlack from "../../asset/image/logo-dot-black.png";
import logoDotWhite from "../../asset/image/logo-dot-white.png";
const List = (props) => {
  const listData = props.listData;
  const selected = props.selected;
  function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [logData, setLogData] = useState([]);

    const API = apiInstance();
    async function getLog50(id) {
      console.log(id)
      console.log(selected.date)
      // 열릴 때만 Get 요청 보내기 위해 분기 추가 했다가
      // 열린 채로 새로 고침 안 되서 잠시 삭제
      try {
        const res = await API.get("/location/getLog50", {
          params: { 
            driver: id,
            date: selected.date
          },
        });
        console.log(res.data);
        setLogData(res.data);
        setOpen(!open);
      }
      catch (err) {
        console.log(err);
      }
    }

    async function getLog(id) {
      try {
        const res = await API.get("/location/getLog", {
          params: { 
            driver: id,
            date: selected.date
          },
        });
        console.log(res.data);
        setLogData(res.data);
        setOpen(!open);
      }
      catch (err) {
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
                getLog50(row.userIdx);
              }}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell
            component="th"
            scope="row"
            onClick={() => {
              // 여기에 로그 불러오는 함수(get(userIdx))
              getLog50(row.userIdx);
            }}
            sx={{
              cursor: "pointer",
            }}
          >
            <div className="list-username">{row.userName}</div>
          </TableCell>
        </TableRow>
        <TableRow>
          <TableCell
            style={{ paddingBottom: 0, paddingTop: 0 }}
            colSpan={6}
            className="typography-container"
          >
            <Collapse in={open} timeout="auto" unmountOnExit>
              <Box sx={{ margin: 1 }}>
                <div>
                  <Typography
                    variant="h5"
                    gutterBottom
                    component="div"
                    sx={{ margin: "0.5rem" }}
                  >
                    사진
                  </Typography>
                  <div className="img-container">
                    {row.spotResponseDtoList.map((taskRow) => (
                      <div className="img-box">
                        {(taskRow.imageUrl) ? 
                        <>
                        <img
                          className="img"
                          src={taskRow.imageUrl}
                          onClick={() => {
                            const spotName = taskRow.spotName;
                            const imageUrl = taskRow.imageUrl;
                            const arrivedTime = taskRow.arrivedTime;
                            Swal.fire({
                              title: spotName,
                              text: `도착 시각 : ${arrivedTime}`,
                              imageUrl: imageUrl,
                              imageWidth: 300,
                              imageHeight: 300,
                              imageAlt: "Pickup image",
                            });
                          }}
                        />
                        <h3>{taskRow.spotName}</h3></> : <img src={logoDotBlack}></img>}
                        
                      </div>
                    ))}
                  </div>
                  

                  <Typography
                    variant="h5"
                    gutterBottom
                    component="div"
                    sx={{ margin: "0.5rem" }}
                  >
                    GPS          
                  </Typography>
                  <span className="all-show" onClick={()=>{
                    getLog(row.userIdx)
                  }}>로그 기록 전체 보기</span>
                  <Table
                    size="small"
                    aria-label="purchases"
                    className="table-container"
                  >
                    <TableHead>
                      <TableRow>
                        <TableCell>시간</TableCell>
                        <TableCell>위도</TableCell>
                        <TableCell>경도</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableBody>
                      {logData.map((log) => (
                        <TableRow key={log.timestamp}>
                          <TableCell component="th" scope="row">
                            {JSON.stringify(log.timestamp)}
                          </TableCell>
                          <TableCell>{JSON.stringify(log.latitude)}</TableCell>
                          <TableCell>{JSON.stringify(log.longitude)}</TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                  </Table>
                </div>
              </Box>
            </Collapse>
          </TableCell>
        </TableRow>
      </React.Fragment>
    );
  }

  const rows = listData;
  return (
    <TableContainer component={Paper} className="table-container">
      <Table aria-label="collapsible table">
        <TableBody>
          {rows.map((row) => (
            <Row key={row.name} row={row} />
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default List;
