import React from 'react';
import { Notification, KIND } from 'baseui/notification';

export const ErrorNotification = ({ errorMessage }) => {
  return (
    <Notification
      kind={KIND.negative}
      closeable
      overrides={{
        Body: { style: { width: 'auto' } },
      }}
    >
      <b>An error has occured!</b>
      <p>
        {errorMessage}
      </p>
    </Notification>
  );
}

export default ErrorNotification;