import React, {ChangeEvent, HTMLInputTypeAttribute, useState} from 'react';

type InputProps<ValueType> = {
  name: string,
  title: string,
  type: HTMLInputTypeAttribute,
  onChange: (value: ValueType) => void,
  errorMessage: string | null,
  placeHolder: string
}

export const Input = <ValueType extends unknown>({name, title, type, errorMessage, onChange, placeHolder}: InputProps<ValueType>) => {

  const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
    let value = event.currentTarget.value as ValueType
    onChange(value)
  }

  return <div className="Input" style={{marginTop: "2vh", marginRight: "5vw" , display: "flex", flexDirection: "column"}}>
    <label style={{margin: "5px"}} htmlFor={name}>{title}</label>
    <input placeholder={placeHolder} style={{borderColor: errorMessage ? "red" : "black", borderWidth: "1px", height:"4vh"}} name={name} id={name} required type={type} onChange={event => handleChange(event)} />
    { errorMessage ? <p style={{color: errorMessage ? "red" : "black"}}>{errorMessage}</p> : "" }
  </div>
}