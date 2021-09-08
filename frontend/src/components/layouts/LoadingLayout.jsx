import { StyledSpinnerNext } from "baseui/spinner";
import React from "react";

const LoadingLayout = () => {
  return (
    <div
      style={{
        position: "absolute",
        top: "50%",
        left: "50%",
        transform: "translate(-50%,-50%)",
      }}
    >
      <StyledSpinnerNext />
    </div>
  );
};

export default LoadingLayout;