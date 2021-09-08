import { LabelSmall } from "baseui/typography";
import React from "react";
// get our fontawesome imports
import { faGithub, faLinkedin } from "@fortawesome/free-brands-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useStyletron } from "baseui";
import { Button, KIND, SHAPE, SIZE } from "baseui/button";

const Footer = () => {
  const [, theme] = useStyletron();
  return (
    <footer>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          justifyContent: "center",
          alignItems: "center",
          padding: "10px"
        }}
      >
        <LabelSmall>Built with React and baseweb.</LabelSmall>
        <div
          style={{
            display: "flex",
            justifyContent: "space-evenly",
            alignItems: "center",
            paddingTop: "10px"
          }}
        >
          <Button
            kind={KIND.minimal}
            size={SIZE.compact}
            shape={SHAPE.circle}
          >
            <a href="https://github.com/jvogit/todo-react" target="_blank" rel="noreferrer">
              <FontAwesomeIcon icon={faGithub} style={{ color: theme.colors.primary }} />
            </a>
          </Button>
          <Button
            kind={KIND.minimal}
            size={SIZE.compact}
            shape={SHAPE.circle}
          >
            <a href="https://www.linkedin.com/in/justinnvo/">
              <FontAwesomeIcon icon={faLinkedin} style={{ color: theme.colors.primary }} />
            </a>
          </Button>
        </div>
      </div>
    </footer>
  );
};

export default Footer;