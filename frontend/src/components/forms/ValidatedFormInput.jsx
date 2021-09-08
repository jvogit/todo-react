import React, { useState } from 'react';
import { Input } from 'baseui/input';
import { useStyletron } from 'baseui';
import { FormControl } from 'baseui/form-control';
import { Alert } from 'baseui/icon';

function Negative() {
  const [css, theme] = useStyletron();
  return (
    <div
      className={css({
        display: 'flex',
        alignItems: 'center',
        paddingRight: theme.sizing.scale500,
        color: theme.colors.negative400,
      })}
    >
      <Alert size="18px" />
    </div>
  );
}


const ValidatedFormInput = ({ label, onValidate = () => true, onChange = () => { }, errorMessage, type }) => {
  const [error, setError] = useState(false);
  const [visited, setVisited] = useState(false);
  const isInvalid = visited && error;

  function handleChange(e) {
    setError(!onValidate(e.target.value));
    onChange(e.target.value);
  }

  return (
    <FormControl
      label={label}
      error={
        isInvalid ? errorMessage : null
      }
    >
      <Input
        required
        type={type}
        onBlur={() => setVisited(true)}
        onChange={handleChange}
        error={isInvalid}
        overrides={isInvalid ? { After: Negative } : {}}
      />
    </FormControl>
  );
}

export default ValidatedFormInput;
