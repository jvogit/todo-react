import { EditIcon } from '@chakra-ui/icons';
import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Avatar, Box, Button, Center, Checkbox, Container, Heading, HStack, IconButton, List, ListIcon, ListItem, Spinner, VStack } from '@chakra-ui/react';
import { update } from 'lodash';
import * as React from 'react';
import { TodosDocument, TodosQuery, useCreateTodoMutation, User, useTodosQuery } from '../../generated/graphql';
import Todos from '../todos/Todos';

interface Props {
  user: Pick<User, "id" | "username">
}

const ProfileCard: React.FC<Props> = ({ user }) => {

  return (
    <VStack
      w="full"
      rounded={"xl"}
      spacing={6}
      borderWidth={2}
      p={6}
    >
      <Avatar
        size={"xl"}
        name={user.username}
      />
      <Heading>
        {user.username}
      </Heading>
      <Container maxW="100%">
        <Accordion allowToggle allowMultiple defaultIndex={1}>
          <AccordionItem>
            <AccordionButton>
              <Box flex="1" textAlign="left">
                User info
              </Box>
              <AccordionIcon />
            </AccordionButton>
            <AccordionPanel>
              <pre id="json" style={{ position: "relative", overflowX: "auto" }}>
                {
                  JSON.stringify(user, null, 2)
                }
              </pre>
            </AccordionPanel>
          </AccordionItem>
          <AccordionItem>
            <AccordionButton>
              <Box flex="1" textAlign="left">
                Todos
              </Box>
              <AccordionIcon />
            </AccordionButton>
            <AccordionPanel>
              <Todos />
            </AccordionPanel>
          </AccordionItem>
        </Accordion>
      </Container>
    </VStack>
  );
};

export default ProfileCard;