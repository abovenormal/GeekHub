import React, { useEffect, useState } from "react";
import MyResponsivePie from "./MyResponsivePie";
import MyResponsiveLine from "./MyResponsiveLine";
import GetSuccess from "../../api/GetSuccess";
import { apiInstance } from "../../api";
import "./css/Chart.css";
import { Info } from "@material-ui/icons";
import { datePickerValueManager } from "@mui/x-date-pickers/DatePicker/shared";
import YesterdayPie from "./YesterdayPie";
import { Carousel } from "react-responsive-carousel";
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import ApexChart from "react-apexcharts";

const Chart = () => {
  const [data, setData] = useState([]);
  const [map, setMap] = useState([]);
  const [loading, setLoading] = useState(true);



  useEffect(() => {
    async function getData() {
      try {
        const res = await apiInstance().get("spot/success");
        console.log(res.data);
        setData(res.data);
      } catch (err) {
        console.log(err);
      }
    }
    getData();
  }, []);
  useEffect(() => {
    let result = [];
    let temp = []
    for (let i = 0; i < data.length; i++) {
      let item = data[i];
      if (item.totalSpot == 0) {
        temp.push(<ApexChart
          className="carousel-container-chart"
          type="donut" height={300}
          series={[0]}
          options={{
            chart: {
              type: 'donut',
            },
            legend: {
              position: 'bottom'
            },
            responsive: [{
              breakpoint: 480,
            }],
            plotOptions: {
              pie: {
                donut: {
                  labels: {
                    show: true,
                    // total: {
                    //   showAlways: true,
                    //   show: true,
                    //   label: 'ALARM',
                    //   fontSize: '12px',
                    //   color: 'red'
                    // },
                    value: {
                      fontSize: '22px',
                      show: true,
                      color: 'blue',
                    },
                  },
                }
              }
            }
            , title: {
              text: item.schoolName,
              align: 'center'
            }
            , labels: ["주문", "실패"],
          }}></ApexChart>)
      } else {
        temp.push(<ApexChart
          className="carousel-container-chart"
          type="donut" height={300}
          series={[item.successSpot, data[i].totalSpot - item.successSpot]}
          options={{
            chart: {
              type: 'donut',
            },
            legend: {
              position: 'bottom'
            },
            responsive: [{
              breakpoint: 480,
            }],
            plotOptions: {
              pie: {
                donut: {
                  labels: {
                    show: true,
                    // total: {
                    //   showAlways: true,
                    //   show: true,
                    //   label: 'ALARM',
                    //   fontSize: '12px',
                    //   color: 'red'
                    // },
                    value: {
                      fontSize: '22px',
                      show: true,
                      color: 'blue',
                    },
                  },
                }
              }
            }
            , title: {
              text: item.schoolName,
              align: 'center'
            }
            , labels: ["성공", "실패"],
          }}></ApexChart>)
      }

      if (i % 3 == 2 || i == data.length - 1) {
        result.push(<div className="carousel-container">{temp}</div>)
        temp = [];
      }
    }
    setMap(result);
  }, [data]);
  useEffect(() => {
    setLoading(false);
  }, [map]);
  const pie = [];
  let yTotalSuccess = 0;
  let yTotalfail = 0;
  {
    data.map((datum) => {
      yTotalSuccess = yTotalSuccess + Number(JSON.stringify(datum.successSpot));
      yTotalfail =
        yTotalfail +
        Number(
          JSON.stringify(datum.totalSpot) - JSON.stringify(datum.successSpot)
        );
      pie.push([
        {
          id: "success",
          label: "success",
          value: Number(JSON.stringify(datum.successSpot)),
          color: "hsl(220, 70%, 50%)",
          school: datum.schoolName,
        },
        {
          id: "fail",
          label: "fail",
          value: Number(
            JSON.stringify(datum.totalSpot) - JSON.stringify(datum.successSpot)
          ),
          color: "hsl(220, 70%, 50%)",
        },
      ]);
    });
  }
  const pieTotal = [
    {
      id: "success",
      label: "success",
      value: yTotalSuccess,
      color: "hsl(220, 70%, 50%)",
      school: "전국",
    },
    {
      id: "fail",
      label: "fail",
      value: yTotalfail,
      color: "hsl(220, 70%, 50%)",
    },
  ];
  // console.log(pie); 

  // useEffect(() => {
  //   var temp = [];
  //   temp.push(
  //     <div key={1}>
  //       <div className="pie-chart2">
  //         <YesterdayPie data={pieTotal} />
  //         <div>전국</div>
  //       </div>
  //     </div>

  //   );
  //   setMap(temp);
  // }, [data]);
  return (
    <div className="chart-container">
      <div className="pie-container">
        <div className="pie-chart-container2">
          <div className="pie-chart-title">배달 요약</div>
          <div className="pie-chart-body2">
            <div className="pie-chart2">
              <YesterdayPie data={pieTotal} />
              <div>전국</div>
            </div>
          </div>
        </div>
        <div className="pie-chart-container">
          {loading ? <></> :
            <Carousel
              autoPlay
              infiniteLoop
              useKeyboardArrows
              interval="2000"
              showArrows="false"
              showStatus="false"
              showIndicators="false"
              showThumbs="false"
              swipeable="false">
              {map}
            </Carousel>}

        </div>
      </div>
    </div>
  );
};

export default Chart;
