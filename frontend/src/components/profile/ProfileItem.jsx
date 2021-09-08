import { Avatar } from "baseui/avatar";
import { HeadingSmall } from "baseui/typography";
import React from "react";

const ProfileItem = ({ user }) => {
  return (
    <div style={{
      display: "flex",
      flexDirection: "row",
      padding: "10px",
      alignItems: "center",
      justifyContent: "center",
    }}>
      <div style={{ paddingRight: "15px" }}>
        <Avatar name={user.username} size="scale1200" />
      </div>
      <div>
        <HeadingSmall>{user.username}</HeadingSmall>
      </div>
    </div>
  );
};

export default ProfileItem;