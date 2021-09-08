import React from "react";

export const CenterLayout = ({ children }) => {
  return (
    <div
      style={{
        margin: "0 auto",
        width: "76%",
        padding: "10px",
      }}
    >
      {children}
    </div>
  );
}

export default CenterLayout;