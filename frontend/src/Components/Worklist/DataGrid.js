import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../../api/index";
import Button from "@mui/material/Button";
import "./DataGrid.css";
import DetailDropdown from "../Common/DetailDropdown";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import DeleteIcon from '@mui/icons-material/Delete';
import EditIcon from '@mui/icons-material/Edit';
import AddIcon from '@mui/icons-material/Add';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import axios from "axios";
import Toast from "../../utils/Toast";

function DataList() {
  let today = new Date();
  let year = today.getFullYear(); // 년도
  let month = today.getMonth() + 1; // 월
  let date = today.getDate(); // 날짜

  const [data, setData] = useState([]);
  const [rows, setRows] = useState([]);
  const [selected, setSelected] = useState({
    localCity: "",
    localSchool: "",
    date: `${year}-${month}-${date}`,
    hour: "",
    min: "",
    storename: "",
    driver: "",
    lat: "",
    lon: "",
  });

  const [position, setPosition] = useState()

  const [schoolList, setSchoolList] = useState([]);

  async function getData() {
    try {
      const res = await apiInstance().get("spot");
      console.log(res.data);
      setData(res.data);
    } catch (err) {
      console.log(err);
    }
  }
  useEffect(() => {

    getData();
  }, []);

  useEffect(() => {
    let result = [];
    for (let i = 0; i < data.length; i++) {
      let item = data[i];
      let temp = {};
      temp.spotIdx = item.spotIdx;
      temp.spotCategory = item.spotCategory;
      temp.spotName = item.spotName;
      temp.expectedTime = item.expectedTime;
      temp.arrivedTime = item.arrivedTime;
      temp.count = item.count;
      temp.lat = item.lat;
      temp.lon = item.lon;
      result.push(temp);
    }
    setRows(result);
  }, [data]);


  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);

  return (
    <div>
      <TableContainer component={Paper} style={{ height: "100vh", width: "80vw", fontFamily: "BMHANNAPro" }}>
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead>
            <TableRow>
              <TableCell>분류</TableCell>
              <TableCell align="justify">가게명</TableCell>
              <TableCell align="justify">도착 예정 시간</TableCell>
              <TableCell align="justify">수량</TableCell>
              <TableCell align="justify">위도</TableCell>
              <TableCell align="justify">경도</TableCell>
              <TableCell align="center">
                <Button variant="contained" startIcon={<AddIcon />} color="success"
                  onClick={handleOpen}>추가</Button></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow
                key={row.id}
                sx={{ '&:last-child td, &:last-child th': { border: 0 }, '&:hover': { backgroundColor: "#f6d336" } }}
              >
                <TableCell component="th" scope="row">
                  {row.spotCategory}
                </TableCell>
                <TableCell align="justify">{row.spotName}</TableCell>
                <TableCell align="justify">{row.expectedTime.substr(0, 10) + " " + row.expectedTime.substr(11, 16)}</TableCell>
                <TableCell align="justify">{row.count}</TableCell>
                <TableCell align="justify">{row.lat}</TableCell>
                <TableCell align="justify">{row.lon}</TableCell>
                <TableCell align="center">
                  {/* <Button variant="contained" startIcon={<EditIcon />}
                    onClick={() => {
                      Toast.fire({
                        icon: "info",
                        title: "조회된 데이터가 없습니다.",
                        timer: 1000,
                        position: "center",
                      });

                    }}>수정</Button>
                  {" "} */}
                  <Button variant="contained" startIcon={<DeleteIcon />} color="error"
                    onClick={() => {
                      axios("https://k7c205.p.ssafy.io/api/spot/" + row.spotIdx, {
                        method: "delete",
                      })
                        .then((res) => {
                          handleClose();
                          getData();
                          Toast.fire({
                            icon: "info",
                            title: "업무가 삭제 되었습니다.",
                            timer: 1000,
                            position: "center",
                          });
                          console.log(res.data);
                        })
                        .catch((err) => console.log("Update Price error", err));
                    }}>삭제</Button></TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={{
          position: 'absolute',
          top: '50%',
          left: '50%',
          transform: 'translate(-50%, -50%)',
          width: "50%",
          bgcolor: 'background.paper',
          border: '2px solid #000',
          p: 4,
        }}>
          <Typography id="modal-modal-title" variant="h6" component="h2">
            업무 추가
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <div style={{ height: "25vh", width: "100%" }}>
              <div class="container">
                <div className="newpicker">
                  <DetailDropdown position={position} setPosition={setPosition} selected={selected} setSelected={setSelected} />

                </div>
                <div class="createbutton">
                  <Button
                    variant="contained"
                    onClick={() => {
                      console.log(selected);

                      axios("https://k7c205.p.ssafy.io/api/spot/createname", {
                        method: "POST",
                        data: {
                          spotCategory: selected.category,
                          spotName: selected.storename,
                          lat: selected.lat,
                          lon: selected.lon,
                          expectedTime: selected.date + `T${selected.hour}:${selected.min}`,
                          status: 1,
                          count: selected.count,
                          userName: selected.driver
                        },
                      })
                        .then((res) => {
                          handleClose();
                          getData();
                          Toast.fire({
                            icon: "info",
                            title: "업무가 생성 되었습니다.",
                            timer: 1000,
                            position: "center",
                          });
                          console.log(res.data);
                        })
                        .catch((err) => {
                          Toast.fire({
                            icon: "error",
                            title: "입력을 확인해주세요.",
                            timer: 1000,
                            position: "center",
                          });
                          console.log("Update Price error", err)
                        });
                    }}
                  >
                    업무 추가
                  </Button>
                </div>
              </div>

            </div>
          </Typography>
        </Box>
      </Modal>

    </div>
  );
}

export default DataList;
