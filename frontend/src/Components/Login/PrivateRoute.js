import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ authenticated, component:Component}) => {
    console.log(`토큰 정보 ${authenticated}`)
    return(
        
        authenticated?Component:<Navigate to="/login"></Navigate>
    )
}

export default PrivateRoute;