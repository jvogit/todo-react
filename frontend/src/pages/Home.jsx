import { ALIGNMENT, Cell, Grid } from "baseui/layout-grid";
import { H1, H2, H3, Label1, Label2, Paragraph1 } from "baseui/typography";
import {
  Card,
  StyledBody,
  StyledAction,
} from "baseui/card";
import React from "react";
import { Button } from "baseui/button";
import { useHistory } from "react-router-dom";

export const Home = () => {
  const history = useHistory();
  return (
    <section
    >
      <div
        style={{
          paddingTop: "60px",
          paddingBottom: "60px",
        }}
      >
        <Grid
          align={ALIGNMENT.center}
        >
          <Cell span={[4, 8, 6]}>
            <div
              style={{
                display: "flex",
                flexDirection: "column",
                justifyContent: "center",
                alignItems: "center",
                paddingBottom: "60px"
              }}
            >
              <H1>Todo React</H1>
              <Label1>A simple React Todo App</Label1>
              <Label2>Built By: Justin Vo</Label2>
            </div>
          </Cell>
          <Cell span={[4, 8, 6]}>
            <Card
              title="Join now!"
              overrides={{
                Title: H2
              }}
            >
              <StyledBody>
                <Paragraph1>
                  Sign up for an account for personalized daily reminders!
                </Paragraph1>
                <H3>Features</H3>
                <ul>
                  <li>Personalized daily todos!</li>
                  <li>Check off completed todos!</li>
                  <li>Fully editable todo list!</li>
                  <li>Prioritize certain tasks by ordering!</li>
                </ul>
                <H3>But, what's different?</H3>
                <Label1>Almost nothing.</Label1>
                <Paragraph1>
                  This app was an introduction for me to 
                  full stack web development. The source code can be
                  found on <a href="https://github.com/jvogit/todo-react" target="_blank" rel="noreferrer">GitHub</a>.
                  This app was built with <a href="https://baseweb.design/" target="_blank" rel="noreferrer">baseweb</a> design which
                  I thought looked pretty neat-o. I never particularly liked
                  frontend development, but I guess it's pretty cool now!
                </Paragraph1>
              </StyledBody>
              <StyledAction>
                <Button
                  $style={{
                    width: "100%"
                  }}
                  onClick={() => history.push("/signup")}
                >
                  Sign up
                </Button>
              </StyledAction>
            </Card>
          </Cell>
        </Grid>
      </div>
    </section>
  );
}

export default Home;