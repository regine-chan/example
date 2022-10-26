import React, {FormEvent, SyntheticEvent, useEffect, useState} from 'react';
import logo from './logo.svg';
import './App.css';
import {Input} from "./components/Input";
import validateCarRegistrationNumber from "./validation/validateCarRegistrationNumber";
import validateBonus from "./validation/validateBonus";
import validateNationalIdentityNumber from "./validation/validateNationalIdentityNumber";
import validateEmail from "./validation/validateEmail";

type FormSubmisson = {
  carRegistrationNumber: {value: string},
  bonus: {value: number},
  nationalIdentityNumber: {value: number}
  firstName: {value: string},
  lastName: {value: string},
  email: {value: string}
}

function App() {

  const [carRegistrationNumber, setCarRegistrationNumber] = useState<string>()
  const [isValidCarRegistrationNumber, setIsValidCarRegistrationNumber] = useState(true)
  const [bonus, setBonus] = useState<number>()
  const [isValidBonus, setIsValidBonus] = useState(true)
  const [nationalIdentityNumber, setNationalIdentityNumber] = useState<number>()
  const [isValidNationalIdentityNumber, setIsValidNationalIdentityNumber] = useState(true)
  const [firstName, setFirstName] = useState<string>()
  const [lastName, setLastName] = useState<string>()
  const [email, setEmail] = useState<string>()
  const [isValidEmail, setIsValidEmail] = useState(true)
  const [hasSubmitted, setHasSubmitted] = useState(false)

  const handleSubmitIsValidCarRegistrationNumber = (value: string) => {
    setIsValidCarRegistrationNumber(_ => validateCarRegistrationNumber(value))
  }

  const handleSubmitIsValidBonus = (value: number) => {
    setIsValidBonus(_ => validateBonus(value))
  }

  const handleSubmitIsValidNationalIdentityNumber = (value: number) => {
    setIsValidNationalIdentityNumber(_ => validateNationalIdentityNumber(value))
  }

  const handleSubmitIsValidEmail = (value: string) => {
    setIsValidEmail(_ => validateEmail(value))
  }

  const onCancel = () => {
    window.location.reload()
  }

  const onSubmit = (event: SyntheticEvent) => {
    event.preventDefault()
    const target = event.target as typeof event.target & FormSubmisson
    handleSubmitIsValidCarRegistrationNumber(target.carRegistrationNumber.value)
    handleSubmitIsValidBonus(target.bonus.value)
    handleSubmitIsValidNationalIdentityNumber(target.nationalIdentityNumber.value)
    handleSubmitIsValidEmail(target.email.value)
    setFirstName(target.firstName.value)
    setLastName(target.lastName.value)

    if (isValidCarRegistrationNumber
    && isValidBonus
    && isValidNationalIdentityNumber
    && isValidEmail) {
      setHasSubmitted(_ => true)
    } else {
      setHasSubmitted(_ => false)
    }
  }

  useEffect(() => {}, [hasSubmitted])

  return (
    <div style={{textAlign: "left", padding: "2vh 5vw 2vh 5vw"}} className="App">
        <h1>Kjøp Bilforsikring</h1>
        <p>Det er fire forskjellige forsikringer å velge mellom.
          Ansvarsforsikring er lovpålagt om kjøretøyet er registrert og skal bruker på veien.
          I tilleg kan du utvide forsikringen avhengig av hvor gammel bilen din er og hvordan du bruker den.
        </p>

      <form onSubmit={onSubmit}>
        <Input
          name={"carRegistrationNumber"}
          title="Bilens registreringsnummer"
          type="text"
          onChange={setCarRegistrationNumber}
          errorMessage={isValidCarRegistrationNumber ? null : "Feil registreringsnummer"}
          placeHolder="E.g. AB 12345"
        />
        <Input
          name={"bonus"}
          title="Din bonus"
          type="number"
          onChange={setBonus}
          errorMessage={isValidBonus ? null : "Feil bonus"}
          placeHolder="Placeholder"
        />
        <Input
          name={"nationalIdentityNumber"}
          title="Fødselsnummer"
          type="number"
          onChange={setNationalIdentityNumber}
          errorMessage={isValidNationalIdentityNumber ? null : "Feil fødselsnummer"}
          placeHolder="11 siffer"
        />
        <div id="nameInput">
          <Input
            name={"firstName"}
            title="Fornavn"
            type="text"
            onChange={setFirstName}
            errorMessage={null}
            placeHolder="Fornavn"
          />
          <Input
            name={"lastName"}
            title="Etternavn"
            type="text"
            onChange={setLastName}
            errorMessage={null}
            placeHolder="Etternavn"
          />
        </div>
        <Input
          name={"email"}
          title="E-post"
          type="email"
          onChange={setEmail}
          errorMessage={isValidEmail ? null : "Feil e-post"}
          placeHolder="E-post addresse"
        />
        <button style={{margin: "10px"}} type="submit">Kjøp</button>
        <button onClick={onCancel}>Avbryt</button>
        {hasSubmitted ? <p>Ordren er registrert</p> : ""}
      </form>
    </div>
  );
}

export default App;
