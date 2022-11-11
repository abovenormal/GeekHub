import React from 'react';
import { ResponsiveLine } from '@nivo/line'

const MyResponsiveLine = ( {data_line} ) => {
  return (
    <ResponsiveLine
    data={data_line}
    margin={{ top: 20, right: 10, bottom: 100, left: 160 }}
    xScale={{ type: 'point' }}
    yScale={{
        type: 'linear',
        min: 'auto',
        max: 'auto',
        stacked: true,
        reverse: false
    }}
    yFormat=" >-.2f"
    axisTop={null}
    axisRight={null}
    axisBottom={{
        orient: 'bottom',
        tickSize: 5,
        tickPadding: 5,
        tickRotation: -30,
        legend: '장소',
        legendOffset: 46,
        legendPosition: 'middle'
    }}
    axisLeft={{
        orient: 'left',
        tickSize: 5,
        tickPadding: 5,
        tickRotation: 0,
        legend: '오차',
        legendOffset: -40,
        legendPosition: 'middle'
    }}
    enableGridX={true}
    colors={{ scheme: 'accent' }}
    backgroudColor="#fff"
    pointSize={10}
    pointColor={{ from: 'color', modifiers: [] }}
    pointBorderColor={{ from: 'color', modifiers: [] }}
    pointLabelYOffset={-12}
    isInteractive={true}
    enableCrosshair={true}
    useMesh={true}
    motionConfig="stiff"
/>
  )}

export default MyResponsiveLine;