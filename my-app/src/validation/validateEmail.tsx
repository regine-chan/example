
const regex = RegExp("^[\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")

const validateEmail = (email: string) => regex.test(email)

export default validateEmail