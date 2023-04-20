function serializeForm(formNode) {
    console.log(formNode.elements)
}

function handleFormSubmit(event) {
    event.preventDefault()
    serializeForm(applicantForm)
}

const applicantForm = document.getElementById('mars-once')
applicantForm.addEventListener('submit', handleFormSubmit)
