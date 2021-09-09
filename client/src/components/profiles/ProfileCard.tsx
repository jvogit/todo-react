import { Avatar, Container, Heading, VStack } from '@chakra-ui/react';
import * as React from 'react';
import { User, useTodosQuery } from '../../generated/graphql';

interface Props {
  user: Pick<User, "id" | "username">
}

const ProfileCard: React.FC<Props> = ({ user }) => {

  const { data, loading } = useTodosQuery();

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
    </VStack>
  );
};

export default ProfileCard;