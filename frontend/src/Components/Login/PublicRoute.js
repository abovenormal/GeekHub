import React from 'react';
import { Navigate } from 'react-router-dom';

const PublicRoute = ({ authenticated, component:Component}) => {

    return(
        authenticated?<Navigate to="/" ></Navigate>:Component
    )
}

export default PublicRoute;