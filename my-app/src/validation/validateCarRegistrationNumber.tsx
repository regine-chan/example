
const regex = new RegExp("([A-Z]{1,3}[1-9]{3,5})")

const validateCarRegistrationNumber = (regNumber: string) => regex.test(regNumber)

export default validateCarRegistrationNumber