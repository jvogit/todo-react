import { EditIcon } from '@chakra-ui/icons';
import { Center, Checkbox, HStack, IconButton, List, ListItem, Spinner, VStack } from '@chakra-ui/react';
import * as React from 'react';
import { useCreateTodoMutation, useTodosQuery, useUpdateTodoMutation } from '../../generated/graphql';
import CreateTodo from './CreateTodo';
import EditTodo from './EditTodo';

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
                    <Checkbox
                      spacing={"2rem"}
                      width="100%"
                      isChecked={todo.completed}
                      textDecoration={todo.completed ? 'line-through' : 'none'}
                      onChange={(e) => {
                        updateTodo({
                          variables: {
                            todo: {
                              id: todo.id,
                              completed: e.target.checked,
                              item: todo.item
                            }
                          }
                        })
                      }}
                    >
                      {todo.item}
                    </Checkbox>
                    <EditTodo {...todo} />
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