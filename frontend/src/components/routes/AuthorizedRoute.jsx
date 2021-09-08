import LoadingLayout from "components/layouts/LoadingLayout";
import React from "react";
import { connect } from "react-redux";
import { Route, Redirect } from "react-router-dom";

const AuthorizedRoute =  ({component: Component, user, inProgress, ...rest}) => {
  return (
    <Route {...rest} render={(props) => {
      if (inProgress) {
        return <LoadingLayout />
      }
      return user ? <Component {...props}/> : <Redirect to="/login" />;
    }} />
  );
};

const mapStateToProps = (state) => {
  return {
    user: state.auth.user,
    inProgress: state.auth.inProgress,
  }
}

export default connect(mapStateToProps)(AuthorizedRoute);
