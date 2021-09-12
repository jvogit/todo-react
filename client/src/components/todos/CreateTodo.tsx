import { Button, FormControl, FormErrorMessage, FormLabel, Input, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure } from '@chakra-ui/react';
import { useFormik } from 'formik';
import * as React from 'react';
import * as Yup from 'yup';
import { TodosDocument, TodosQuery, useCreateTodoMutation } from '../../generated/graphql';

const CreateTodo: React.FC<{}> = () => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [createTodo] = useCreateTodoMutation();
  const validationSchema = Yup.object({
    item: Yup.string().required('Required'),
  });
  const formik = useFormik({
    initialValues: {
      item: '',
    },
    validationSchema,
    onSubmit: async (values, { setSubmitting, resetForm }) => {
      setSubmitting(true);
      createTodo({
        variables: {
          item: values.item
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
      });
      setSubmitting(false);
      resetForm();
      onClose();
    }
  });

  return (
    <>
      <Button
        w="100%"
        onClick={onOpen}
      >
        Create Todo
      </Button>
      <Modal isOpen={isOpen} onClose={onClose} isCentered>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Create Todo</ModalHeader>
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
              <Button
                w="100%"
                isLoading={formik.isSubmitting}
                type="submit"
              >
                Create
              </Button>
            </ModalFooter>
          </form>
        </ModalContent>
      </Modal>
    </>
  );
}

export default CreateTodo;