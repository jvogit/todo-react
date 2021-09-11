import { EditIcon } from '@chakra-ui/icons';
import { Button, ButtonGroup, Center, Checkbox, FormControl, FormErrorMessage, FormLabel, HStack, IconButton, Input, List, ListItem, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Spinner, useDisclosure, VStack } from '@chakra-ui/react';
import { Formik, Form, Field, useFormik } from 'formik';
import * as React from 'react';
import * as Yup from 'yup';
import { TodosDocument, TodosQuery, useCreateTodoMutation, useTodosQuery, useUpdateTodoMutation } from '../../generated/graphql';

interface EditTodoProps {
  id: string;
  completed: boolean;
  item: string;
}

const EditTodo: React.FC<EditTodoProps> = (props) => {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const [updateTodo] = useUpdateTodoMutation();
  const validationSchema = Yup.object({
    item: Yup.string().required('Required'),
  });
  const formik = useFormik({
    initialValues: {
      item: props.item,
    },
    validationSchema,
    onSubmit: async (values, { setSubmitting, resetForm }) => {
      setSubmitting(true);
      updateTodo({
        variables: {
          todo: {
            id: props.id,
            item: values.item,
            completed: props.completed,
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