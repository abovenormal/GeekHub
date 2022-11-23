import React, { useEffect, useState } from "react";
import { DataGrid } from "@mui/x-data-grid";
import { apiInstance } from "../../api/index";
import Button from "@mui/material/Button";
import "./DataGrid.css";
import DetailDropdown from "../Common/DetailDropdown";
import UpdateDropdown from "../Common/UpdateDropdown";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableCell from "@mui/material/TableCell";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import TableRow from "@mui/material/TableRow";
import Paper from "@mui/material/Paper";
import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import AddIcon from "@mui/icons-material/Add";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Modal from "@mui/material/Modal";
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
    lng: "",
  });
  const [updateSelected, setUpdateSelected] = useState({
    localCity: "",
    localSchool: "",
    spotIdx: "",
    date: `${year}-${month}-${date}`,
    hour: "",
    min: "",
    count: "",
    storename: "",
    driver: "",
    spotCategory: "STORE",
    lat: "",
    lng: "",
  });

  useEffect(() => {
    console.log(updateSelected);
  }, [updateSelected]);
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
      temp.lng = item.lon;
      result.push(temp);
    }
    setRows(result);
  }, [data]);

  const [open, setOpen] = React.useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const [open2, setOpen2] = React.useState(false);
  const handleOpen2 = () => setOpen2(true);
  const handleClose2 = () => setOpen2(false);

  return (
    <div>
      <TableContainer
        component={Paper}
        style={{ height: "100vh", width: "80vw" }}
      >
        <Table sx={{ minWidth: 650 }} aria-label="simple table">
          <TableHead style={{ backgroundColor: "#cbcdd2" }}>
            <TableRow>
              <TableCell style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}>
                분류
              </TableCell>
              <TableCell
                align="justify"
                style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
              >
                가게명
              </TableCell>
              <TableCell
                align="justify"
                style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
              >
                도착 예정 시간
              </TableCell>
              <TableCell
                align="justify"
                style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
              >
                수량
              </TableCell>
              <TableCell
                align="justify"
                style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
              >
                위도
              </TableCell>
              <TableCell
                align="justify"
                style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
              >
                경도
              </TableCell>
              <TableCell align="center">
                <Button
                  variant="contained"
                  startIcon={<AddIcon />}
                  color="success"
                  onClick={handleOpen}
                >
                  추가
                </Button>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows.map((row) => (
              <TableRow
                key={row.id}
                sx={{
                  "&:last-child td, &:last-child th": { border: 0 },
                  "&:hover": { backgroundColor: "#f6d336" },
                }}
              >
                <TableCell
                  component="th"
                  scope="row"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.spotCategory}
                </TableCell>
                <TableCell
                  align="justify"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.spotName}
                </TableCell>
                <TableCell
                  align="justify"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.expectedTime.substr(0, 10) +
                    " " +
                    row.expectedTime.substr(11, 16)}
                </TableCell>
                <TableCell
                  align="justify"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.count}
                </TableCell>
                <TableCell
                  align="justify"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.lat}
                </TableCell>
                <TableCell
                  align="justify"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  {row.lng}
                </TableCell>
                <TableCell
                  align="center"
                  style={{ fontFamily: "BMHANNAPro", fontSize: 20 }}
                >
                  <Button
                    variant="contained"
                    startIcon={<EditIcon />}
                    style={{
                      fontFamily: "BMHANNAAir",
                      fontSize: 13,
                      backgroundColor: "#101836",
                    }}
                    onClick={(e) => {
                      setUpdateSelected((prev) => {
                        return {
                          ...prev,
                          spotIdx: row.spotIdx,
                          count: row.count,
                          spotCategory: row.spotCategory,
                          storename: row.spotName,
                          lat: row.lat,
                          lng: row.lng,
                          localCity: selected.localCity,
                          localSchool: selected.localSchool,
                        };
                      });
                      handleOpen2();
                    }}
                  >
                    수정
                  </Button>{" "}
                  <Button
                    variant="contained"
                    startIcon={<DeleteIcon />}
                    color="error"
                    style={{ fontFamily: "BMHANNAAir", fontSize: 13 }}
                    onClick={() => {
                      axios(
                        "https://k7c205.p.ssafy.io/api/spot/" + row.spotIdx,
                        {
                          method: "delete",
                        }
                      )
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
                    }}
                  >
                    삭제
                  </Button>
                </TableCell>
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
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: "50%",
            bgcolor: "background.paper",
            border: "2px solid #000",
            p: 4,
          }}
        >
          <Typography id="modal-modal-title" variant="h6" component="h2">
            업무 추가
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <div style={{ height: "25vh", width: "100%" }}>
              <div class="container">
                <div className="newpicker">
                  <DetailDropdown
                    selected={selected}
                    setSelected={setSelected}
                  />
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
                          lon: selected.lng,
                          expectedTime:
                            selected.date +
                            `T${
                              selected.hour < 10
                                ? "0" + selected.hour
                                : selected.hour
                            }:${
                              selected.min < 10
                                ? "0" + selected.min
                                : selected.min
                            }`,
                          status: 1,
                          count: selected.count,
                          userName: selected.driver,
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
                          console.log("Update Price error", err);
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

      <Modal
        open={open2}
        onClose={handleClose2}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box
          sx={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
            width: "50%",
            bgcolor: "background.paper",
            border: "2px solid #000",
            p: 4,
          }}
        >
          <Typography id="modal-modal-title" variant="h6" component="h2">
            업무 수정
          </Typography>
          <Typography id="modal-modal-description" sx={{ mt: 2 }}>
            <div style={{ height: "25vh", width: "100%" }}>
              <div class="container">
                <div className="newpicker">
                  <UpdateDropdown
                    updateSelected={updateSelected}
                    setUpdateSelected={setUpdateSelected}
                  />
                </div>
                <div class="createbutton">
                  <Button
                    variant="contained"
                    onClick={() => {
                      console.log(updateSelected);

                      axios(
                        "https://k7c205.p.ssafy.io/api/spot/" +
                          updateSelected.spotIdx,
                        {
                          method: "PUT",
                          data: {
                            spotCategory: updateSelected.category,
                            spotName: updateSelected.storename,
                            lat: updateSelected.lat,
                            lon: updateSelected.lng,
                            expectedTime:
                              updateSelected.date +
                              `T${
                                updateSelected.hour < 10
                                  ? "0" + updateSelected.hour
                                  : updateSelected.hour
                              }:${
                                updateSelected.min < 10
                                  ? "0" + updateSelected.min
                                  : updateSelected.min
                              }`,
                            count: updateSelected.count,
                            userName: updateSelected.driver,
                          },
                        }
                      )
                        .then((res) => {
                          handleClose2();
                          getData();
                          Toast.fire({
                            icon: "info",
                            title: "업무가 수정 되었습니다.",
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
                          console.log("Update Price error", err);
                        });
                    }}
                  >
                    업무 수정
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
