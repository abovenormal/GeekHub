import React from 'react';
import { ResponsiveLine } from '@nivo/line'

const MyResponsiveLine = ( {data_line} ) => {
  return (
    <ResponsiveLine
    data={data_line}
    margin={{ top: 10, right: 50, bottom: 50, left: 60 }}
    xScale={{ type: 'point' }}
    yScale={{
        type: 'linear',
        min: -5,
        max: 5,
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
        tickRotation: 0,
        legend: '픽업존',
        legendOffset: 36,
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
    enableCrosshair={false}
    useMesh={true}
    motionConfig="stiff"
/>
  )}

export default MyResponsiveLine;