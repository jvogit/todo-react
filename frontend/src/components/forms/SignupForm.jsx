import React, { useState } from "react";
import ValidatedFormInput from "components/forms/ValidatedFormInput";
import { Button } from "baseui/button";
import ErrorNotification from "components/notifications/ErrorNotification";
import { connect } from "react-redux";
import { SIGNUP_REQUEST } from "utils/storeConsts";

const RegisterForm = ({ error, signup }) => {
  const [username, setUsername] = useState(undefined);
  const [email, setEmail] = useState(undefined);
  const [password, setPassword] = useState(undefined);

  function onSubmit(e) {
    e.preventDefault();
    signup(username, email, password);
  }

  return (
    <form onSubmit={onSubmit}>
      {error && <ErrorNotification errorMessage={error} />}
      <ValidatedFormInput
        label="Username"
        onChange={setUsername}
      />
      <ValidatedFormInput
        label="Email"
        onChange={setEmail}
      />
      <ValidatedFormInput
        label="Password"
        type="password"
        onChange={setPassword}
      />
      <ValidatedFormInput
        label="Confirm Password"
        type="password"
        onValidate={(value) => value === password}
        errorMessage={"Passwords do not match!"}
      />
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
        Sign up
      </Button>
    </form>
  );
}

const mapStateToProps = (state) => {
  return {
    error: state.signup.error,
  };
};

const mapDispatchToProps = (dispatch) => {
  return {
    signup: (username, email, password) => dispatch({ type: SIGNUP_REQUEST, username, email, password })
  };
};

export default connect(
  mapStateToProps,
  mapDispatchToProps,
)(RegisterForm);