import React, { useState } from "react";
import { Grid, Cell, } from 'baseui/layout-grid';
import { connect } from "react-redux";
import ProfileItem from "components/profile/ProfileItem";
import { HeadingSmall, } from "baseui/typography";
import TodoList from "components/todos/TodoList";
import { Button, KIND } from "baseui/button";
import { formatDate } from "utils/utils.js";
import { StatefulTabs as Tabs, Tab, FILL, } from "baseui/tabs-motion";
import { addDays } from "date-fns";

const Todo = ({ date }) => {
  const [editing, setEditing] = useState(false);
  return (
    <div style={{
      width: "100%",
    }}>
      <div style={{
        display: "flex",
        flexDirection: "row",
        justifyContent: "space-between",
        alignItems: "center",
      }}>
        <HeadingSmall>{date}</HeadingSmall>
        <Button
          kind={KIND.minimal}
          onClick={() => setEditing(!editing)}
        >
          {editing ? "Done" : "Edit"}
        </Button>
      </div>
      <div>
        <TodoList date={date} editing={editing} />
      </div>
    </div>
  );
}

const Todos = ({ user, error, inProgress }) => {
  const date = formatDate(new Date());
  const tmrw = formatDate(addDays(new Date(), 1));
  if (inProgress || error) {
    return null;
  }

  return (
    <Grid>
      <Cell span={[4, 8, 4]}>
        <ProfileItem user={user} />
      </Cell>
      <Cell span={[4, 8, 8]}>
        <Tabs
          fill={FILL.fixed}
          overrides={{
            Root: {
              style: { marginTop: "20px" }
            }
          }}
        >
          <Tab title={`TODAY ${date}`}>
            <Todo date={date} />
          </Tab>
          <Tab title={`TOMORROW ${tmrw}`}>
            <Todo date={tmrw} />
          </Tab>
        </Tabs>
      </Cell>
    </Grid>
  );
}

const mapStateToProps = (state) => {
  return {
    ...(state.auth),
  };
}

export default connect(mapStateToProps)(Todos);
