import { Stack } from "@chakra-ui/react";
import React from "react";
import Header from "./Header";

export default function Layout({ children }) {
  return (
    <Stack
      height="100vh"
      alignItems="center"
    > 
      <Header
        maxW="6xl"
        margin="0 auto"
        width="100%"
      />
      {children}
    </Stack>
  );
}