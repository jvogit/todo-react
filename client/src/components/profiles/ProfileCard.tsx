import { Avatar, Button, Container, Heading, VStack } from '@chakra-ui/react';
import { update } from 'lodash';
import * as React from 'react';
import { TodosDocument, TodosQuery, useCreateTodoMutation, User, useTodosQuery } from '../../generated/graphql';

interface Props {
  user: Pick<User, "id" | "username">
}

const ProfileCard: React.FC<Props> = ({ user }) => {

  const { data, loading } = useTodosQuery();
  const [ createTodo ] = useCreateTodoMutation({ notifyOnNetworkStatusChange: true });

  return (
    <VStack
      w="full"
      rounded={"xl"}
      spacing={6}
      boxShadow={'2xl'}
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
        <pre id="json" style={{ position: "relative", overflowX: "auto" }}>
          {
            JSON.stringify(user, null, 2)
          }
        </pre>
      </Container>
      <Container maxW="100%">
        <pre id="json" style={{ position: "relative", overflowX: "auto" }}>
          {
            !loading ? JSON.stringify(data.todos, null, 2) : "Loading..."
          }
        </pre>
      </Container>
      <Container maxW="100%">
        <Button
          w="100%"
          onClick={() => {
            createTodo({
              variables: {
                item: "Fill this out!"
              },
              update: (store, { data }) => {
                const prevData = store.readQuery<TodosQuery>({
                  query: TodosDocument
                });

                store.writeQuery<TodosQuery>({
                  query: TodosDocument,
                  data: { todos: [...prevData.todos, data.createTodo] }
                });
              }
            })
          }}
        >
          Create Todo
        </Button>
      </Container>
    </VStack>
  );
};

export default ProfileCard;