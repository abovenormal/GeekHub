import React from "react";
import { ResponsivePie } from "@nivo/pie";

const MyResponsivePie = ( {data} ) => {
  return (
  <ResponsivePie
    data={data}
    margin={{ top: 10, right: 0, bottom: 10, left: 0 }}
    innerRadius={0.7}
    activeOuterRadiusOffset={6}
    colors={{ scheme: "set3" }}
    borderColor={{
      from: "color",
      modifiers: [["darker", "0.2"]],
    }}
    enableArcLinkLabels={false}
    arcLinkLabelsSkipAngle={10}
    arcLinkLabelsTextColor={{ from: "color", modifiers: [] }}
    arcLinkLabelsThickness={2}
    arcLinkLabelsColor={{ from: "color" }}
    arcLabelsSkipAngle={10}
    arcLabelsTextColor={{
      from: "color",
      modifiers: [["darker", 8]],
    }}
    defs={[
      {
        id: "dots",
        type: "patternDots",
        background: "inherit",
        color: "#fff",
        size: 4,
        padding: 1,
        stagger: true,
      },
      {
        id: "lines",
        type: "patternLines",
        background: "inherit",
        color: "rgba(255, 255, 255, 0.3)",
        rotation: -45,
        lineWidth: 6,
        spacing: 10,
      },
    ]}
    fill={[
    ]}

  />
)}
export default MyResponsivePie;