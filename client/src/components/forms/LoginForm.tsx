import { Button, FormControl, FormLabel, Input, useToast } from '@chakra-ui/react';
import { Field, Form, Formik } from "formik";
import * as React from 'react';
import { useRouter } from 'next/router';
import { MeDocument, useLoginMutation, MeQuery } from '../../generated/graphql';
import { setAccessToken } from '../../utils/accessToken';

const LoginForm: React.FC<{}> = () => {
  const [login] = useLoginMutation();
  const toast = useToast();
  const router = useRouter();

  return (
    <Formik
      initialValues={{
        usernameOrEmail: "",
        password: "",
      }}
      onSubmit={async (values, actions) => {
        console.log("submitted");

        const response = await login({
          variables: {
            input: {
              usernameOrEmail: values.usernameOrEmail,
              password: values.password,
            }
          },
          update: (store, { data }) => {
            if (!data) return null;

            store.writeQuery<MeQuery>({
              query: MeDocument,
              data: {
                me: data.login.user,
              }
            });
          }
        });

        if (!response.data || !response.data.login.token) {
          toast({
            title: "Error",
            description: "An error occured.",
            status: "error",
            duration: 5000,
            isClosable: true,
          });
        } else {
          setAccessToken(response.data.login.token);
          router.push("/profile");
        }

        actions.setSubmitting(false);
      }}
    >
      {({ isSubmitting }) => (
        <Form>
          <Field name="usernameOrEmail">
            {({ field, form }: any) => (
              <FormControl isRequired>
                <FormLabel htmlFor="usernameOrEmail">Username or Email</FormLabel>
                <Input {...field} id="usernameOrEmail" placeholder="usernameOrEmail" />
              </FormControl>
            )}
          </Field>
          <Field name="password">
            {({ field, form }: any) => (
              <FormControl isRequired>
                <FormLabel htmlFor="password">Password</FormLabel>
                <Input {...field} type="password" id="password" placeholder="password" />
              </FormControl>
            )}
          </Field>
          <Button
            mt={6}
            isLoading={isSubmitting}
            type="submit"
          >
            Login
          </Button>
        </Form>
      )}
    </Formik>
  );
};

export default LoginForm;