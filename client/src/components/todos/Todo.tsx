import { Checkbox } from '@chakra-ui/react';
import * as React from 'react';
import { Todo as TodoQL, useUpdateTodoMutation } from '../../generated/graphql';
import EditTodo from './EditTodo';

interface TodoProps {
  todo: TodoQL;
}

const Todo: React.FC<TodoProps> = ({ todo }) => {
  const [updateTodo] = useUpdateTodoMutation();
  
  return (
    <>
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
      <EditTodo todo={todo} />
    </>
  );
};

export default Todo;