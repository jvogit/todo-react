import { Center, Checkbox, HStack, List, ListItem, Spinner, VStack } from '@chakra-ui/react';
import * as React from 'react';
import { useTodosQuery, useUpdateTodoMutation } from '../../generated/graphql';
import CreateTodo from './CreateTodo';
import EditTodo from './EditTodo';
import Todo from './Todo';

const Todos: React.FC<{}> = () => {
  const { data, loading } = useTodosQuery();
  const [updateTodo] = useUpdateTodoMutation();

  return (
    <VStack>
      {
        loading || !data ? <Center padding={10}><Spinner /></Center> : (
          <List spacing={5} pb={5} width="100%">
            {
              data.todos.map(todo => {
                return (<ListItem key={todo.id}>
                  <HStack justifyContent="space-between">
                    <Todo todo={todo} />
                  </HStack>
                </ListItem>);
              })
            }
          </List>
        )
      }
      <CreateTodo />
    </VStack>
  );
};

export default Todos;