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
import Swal from "sweetalert2";
import {apiInstance} from "../../api/index"
const List = (props) => {
  const driverData = props.driverData;
  function Row(props) {
    const { row } = props;
    const [open, setOpen] = React.useState(false);
    const [logData, setLogData] = useState([]);

    const API = apiInstance();
    async function getLog(id) {
      // 열릴 때만 Get 요청 보내기 위해 분기 추가 했다가
      // 열린 채로 새로 고침 안 되서 잠시 삭제
     try {
       const res = await API.post('/location/sendLog', {params : {driver:id}})
       console.log(res.data)
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
                setOpen(!open);
                setLogData([
                  {
                    driverId: "221343",
                    longitude: "38.12312",
                    latitude: "23.123123",
                    timeStamp: "2022.11.08.11:11",
                  },
                  {
                    driverId: "221343",
                    longitude: "39.9212",
                    latitude: "24.999123",
                    timeStamp: "2022.11.08.11:14",
                  },
                ]);
              }}
            >
              {open ? <KeyboardArrowUpIcon /> : <KeyboardArrowDownIcon />}
            </IconButton>
          </TableCell>
          <TableCell
            component="th"
            scope="row"
            onClick={() => {
              setOpen(!open);
              // 여기에 로그 불러오는 함수(get(id))
              getLog(row.id)
              setLogData([
                {
                  driverId: "221343",
                  longitude: "38.12312",
                  latitude: "23.123123",
                  timeStamp: "2022.11.08.11:11",
                },
                {
                  driverId: "221343",
                  longitude: "39.9212",
                  latitude: "24.999123",
                  timeStamp: "2022.11.08.11:14",
                },
              ]);
            }}
            sx={{
              cursor: "pointer",
            }}
          >
            {row.name}
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
                <div >
                  <Typography
                    variant="h6"
                    gutterBottom
                    component="div"
                    sx={{ margin: "0.5rem" }}
                  >
                    사진
                  </Typography>
                  <div className="img-container">
                    {row.task.map((taskRow) => (
                      <div className="img-box">
                        <img
                        className="img"
                        src={taskRow.pickupPicture}
                        onClick={() => {
                          const pickupZone = taskRow.pickupZone;
                          const pickupPicture = taskRow.pickupPicture;
                          const arrivalTime = taskRow.arrivalTime;
                          Swal.fire({
                            title: pickupZone,
                            text: `도착 시각 : ${arrivalTime}`,
                            imageUrl: pickupPicture,
                            imageWidth: 300,
                            imageHeight: 300,
                            imageAlt: "Pickup image",
                          });
                        }}
                        />
                        <p>{taskRow.pickupZone}</p>
                      </div>
                    ))}
                  </div>

                  <Typography
                    variant="h6"
                    gutterBottom
                    component="div"
                    sx={{ margin: "0.5rem" }}
                  >
                    GPS
                  </Typography>
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
                        <TableRow key={log.timeStamp}>
                          <TableCell component="th" scope="row">
                            {JSON.stringify(log.timeStamp)}
                          </TableCell>
                          <TableCell>{JSON.stringify(log.latitude)}</TableCell>
                          <TableCell>{JSON.stringify(log.longitude)}</TableCell>
                          {/* <TableCell component="th" scope="row">
                            {log.info.timeStamp}
                          </TableCell>
                          <TableCell>{log.info.latitude}</TableCell>
                          <TableCell>{log.info.longitude}</TableCell> */}
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

  const rows = driverData;
  return (
    <TableContainer component={Paper} className="table-container">
      <Table aria-label="collapsible table">
        <TableBody>
          {rows.map((row) => (
            <Row key={row.name} row={row}/>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
  );
};

export default List;
