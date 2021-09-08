
import React, { useState } from "react";
import { Input } from "baseui/input";
import { FormControl } from "baseui/form-control";
import { Button } from "baseui/button";
import ErrorNotification from "components/notifications/ErrorNotification";
import { connect } from "react-redux";
import { LOGIN_REQUEST } from "utils/storeConsts";

const LoginForm = ({ error, login }) => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function onSubmit(e) {
    e.preventDefault();
    login(username, password);
  }

  return (
    <form onSubmit={onSubmit}>
      {error && <ErrorNotification errorMessage={"Wrong username or password!"} />}
      <FormControl label={"Username"}>
        <Input
          required
          onChange={e => setUsername(e.target.value)}
          placeholder="Username"
        />
      </FormControl>
      <FormControl label={"Password"}>
        <Input
          required
          onChange={e => setPassword(e.target.value)}
          placeholder="Password"
          type="password"
        />
      </FormControl>
      <Button
        type="submit"
        overrides={{
          BaseButton: {
            style: {
              width: "100%"
            }
          }
        }}
      >
        Log in
      </Button>
    </form>
  );
}

const mapStateToProps = (state) => {
  return {
    user: state.auth.user,
    error: state.auth.error,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    login: (username, password) => dispatch({ type: LOGIN_REQUEST, username, password }),
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(LoginForm);