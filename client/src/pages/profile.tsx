import { Center, Spinner } from "@chakra-ui/react";
import * as React from 'react';
import { useRouter } from "next/router";
import ProfileCard from "../components/profiles/ProfileCard";
import { useMeQuery } from '../generated/graphql';

const Profile: React.FC<{}> = () => {
  const { data, loading } = useMeQuery();
  const router = useRouter();

  if (loading) {
    return (
      <Center
        flexGrow={1}
      >
        <Spinner />
      </Center>
    );
  }

  if (!data || !data.me) {
    router.push("/login");

    return (<div/>);
  }

  return (
    <Center
      py={{ base: 10 }}
      maxW={{ base: "sm", md: "xl" }}
      width="full"
    >
      <ProfileCard user={data.me}/>
    </Center>
  );
};

export default Profile;