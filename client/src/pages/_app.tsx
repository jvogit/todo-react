import { ApolloProvider } from "@apollo/client";
import { ChakraProvider, ColorModeScript, theme } from "@chakra-ui/react";
import Layout from "../components/Layout";
import { useApollo } from "../utils/createApollo";

export default function App({ Component, pageProps }) {
  const apolloClient = useApollo(pageProps);

  return (
    <ApolloProvider client={apolloClient}>
      <ChakraProvider theme={theme}>
        <ColorModeScript />
        <Layout>
          <Component {...pageProps} />
        </Layout>
      </ChakraProvider>
    </ApolloProvider>
  );
}
