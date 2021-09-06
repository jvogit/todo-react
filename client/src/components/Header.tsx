import { CloseIcon, ExternalLinkIcon, HamburgerIcon } from "@chakra-ui/icons";
import {
  Box, Button, ButtonGroup, Flex, FlexProps, Heading, Link as StyledLink, Stack, useDisclosure
} from "@chakra-ui/react";
import React from "react";
import Link from "next/link";
import { useMeQuery } from "../generated/graphql";
import { ColorModeSwitcher } from "./ColorModeSwitcher";
import ProfileMenu from "./profiles/ProfileMenu";

const Header: React.FC<FlexProps> = (props) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const handleToggle = () => (isOpen ? onClose() : onOpen());
  const { data, loading } = useMeQuery({ notifyOnNetworkStatusChange: true });

  return (
    <Flex
      as="header"
      align="center"
      justify="space-between"
      wrap="wrap"
      padding={3}
      {...props}
    >
      <Flex align="center" mr={5}>
        <Heading size="lg" letterSpacing={"tighter"}>
          <Link href="/">spring-react-nextjs</Link>
        </Heading>
      </Flex>

      <Box display={{ base: "block", md: "none" }} onClick={handleToggle}>
        {isOpen ? <CloseIcon /> : <HamburgerIcon />}
      </Box>

      <Stack
        direction={{ base: "column", md: "row" }}
        display={{ base: isOpen ? "block" : "none", md: "flex" }}
        width={{ base: "full", md: "auto" }}
        alignItems="center"
        flexGrow={1}
        mt={{ base: 4, md: 0 }}
      >
        <StyledLink href="https://chakra-ui.com" isExternal>
          Chakra Docs <ExternalLinkIcon mx="2px" />
        </StyledLink>
      </Stack>

      <Box
        display={{ base: isOpen ? "block" : "none", md: "block" }}
        mt={{ base: 4, md: 0 }}
      >
        {
          (loading || !data || !data.me) ? (
            <ButtonGroup variant="outline">
              <Link href="/signup">
                <Button onClick={onClose}>
                  Sign up
                </Button>
              </Link>
              <Link href="/login">
                <Button onClick={onClose}>
                  Login
                </Button>
              </Link>
              <ColorModeSwitcher />
            </ButtonGroup>
          ) : (
            <ButtonGroup>
              <ColorModeSwitcher />
              <ProfileMenu user={data.me} />
            </ButtonGroup>
          )
        }
      </Box>
    </Flex>
  );
};

export default Header;
