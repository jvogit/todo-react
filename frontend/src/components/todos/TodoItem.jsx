import React, { useState, } from "react";
import {
  Checkbox
} from "baseui/checkbox";
import { requestWithToken } from "utils/Request";
import { Input } from "baseui/input";
import { Paragraph2 } from "baseui/typography";

const TodoItem = ({ id, completed, text, editing = false, onUpdate = () => {} }) => {
  const [textState, setTextState] = useState(text);
  const [completedState, setCompletedState] = useState(completed);

  const doUpdates = ({ text = textState, completed = completedState } = {}) => {
    setTextState(text);
    setCompletedState(completed);
    onUpdate(text, completed);
    requestWithToken(
      "PUT",
      `/api/todo/item/${id}`,
      {
        data: {
          text,
          completed,
        }
      });
  };

  return (
    <div style={{
      display: "flex",
      width: "100%",
      flexDirection: "row",
      alignItems: "center",
    }}>
      <div
        style={{
          paddingRight: "10px",
        }}
      >
        {!editing && <Checkbox checked={completedState} onChange={(e) => {
          setCompletedState(e.target.checked);
          doUpdates({ completed: e.target.checked });
        }} />}
      </div>
      <div
        style={{
          width: "100%",
        }}
      >
        {!editing
          ? <Paragraph2 $style={{ textDecoration: completedState ? "line-through" : null }} >{textState}</Paragraph2>
          : <Input value={textState} onChange={(e) => setTextState(e.target.value)} onBlur={() => doUpdates()} />
        }
      </div>
    </div>
  )
}

export default TodoItem;
