import React from "react";
import ReactDOM from "react-dom";
import { createStore, applyMiddleware } from "redux";
import { Provider as StoreProvider } from "react-redux";
import createSagaMiddleware from "redux-saga";
import { createBrowserHistory } from "history";
import rootReducer from "reducers/rootReducer";
import rootSaga from "sagas/rootSaga";
import App from "components/App";
import { TOKEN_VALIDATE } from "utils/storeConsts";
import { ACCESS_TOKEN } from "utils/appConsts";

const sagaMiddleware = createSagaMiddleware();
const store = createStore(rootReducer, applyMiddleware(sagaMiddleware));
const history = createBrowserHistory();

sagaMiddleware.run(rootSaga, { history });

if(localStorage.getItem(ACCESS_TOKEN)) store.dispatch({ type: TOKEN_VALIDATE });

ReactDOM.render(
  <React.StrictMode>
    <StoreProvider store={store}>
      <App history={history} />
    </StoreProvider>
  </React.StrictMode>,
  document.getElementById("root")
);
