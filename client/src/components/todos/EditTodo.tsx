import { EditIcon } from '@chakra-ui/icons';
import { Button, ButtonGroup, FormControl, FormErrorMessage, FormLabel, IconButton, Input, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure } from '@chakra-ui/react';
import { useFormik } from 'formik';
import * as React from 'react';
import * as Yup from 'yup';
import { Todo, useDeleteTodoMutation, useUpdateTodoMutation } from '../../generated/graphql';

interface EditTodoProps {
  todo: Todo;
}

const EditTodo: React.FC<EditTodoProps> = ({ todo }) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [updateTodo] = useUpdateTodoMutation();
  const [deleteTodo] = useDeleteTodoMutation();
  const validationSchema = Yup.object({
    item: Yup.string().required('Required'),
  });
  const formik = useFormik({
    initialValues: {
      item: todo.item,
    },
    validationSchema,
    onSubmit: async (values, { setSubmitting }) => {
      setSubmitting(true);
      updateTodo({
        variables: {
          todo: {
            id: todo.id,
            completed: todo.completed,
            item: values.item
          }
        }
      });
      setSubmitting(false);
      onClose();
    }
  });

  return (
    <>
      <IconButton
        aria-label="edit"
        variant="ghost"
        icon={<EditIcon />}
        onClick={onOpen}
      />
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Edit Todo</ModalHeader>
          <ModalCloseButton />
          <form autoComplete="off" onSubmit={formik.handleSubmit}>
            <ModalBody>
              <FormControl isInvalid={formik.errors.item && formik.touched.item} isRequired>
                <FormLabel htmlFor="item">Todo item</FormLabel>
                <Input id="item" {...formik.getFieldProps("item")} placeholder="Fill this out" />
                <FormErrorMessage>{formik.errors.item}</FormErrorMessage>
              </FormControl>
            </ModalBody>
            <ModalFooter>
              <ButtonGroup>
                <Button
                  isDisabled={formik.isSubmitting}
                  onClick={() => {
                    deleteTodo({
                      variables: {
                        id: todo.id
                      },
                      update: (cache, { data }) => {
                        cache.evict({ id: `Todo:${todo.id}` });
                      }
                    });
                    onClose();
                  }}
                >
                  Delete
                </Button>
                <Button
                  isLoading={formik.isSubmitting}
                  type="submit"
                >
                  Update
                </Button>
              </ButtonGroup>
            </ModalFooter>
          </form>
        </ModalContent>
      </Modal>
    </>
  );
}

export default EditTodo;