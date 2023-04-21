const registrationUserForm = document.querySelector('.registrationUser');
const selectRolesRegistration = document.getElementById('role1');
const urlReg = 'http://localhost:8080/loginreg';

registrationUserForm.addEventListener('click', (event) => {
    event.preventDefault();
    let nameButtonReg = event.target.name;
    if (nameButtonReg === "registration"){
        registrationUser();
    }
})
async function registrationUser() {
    let registrationUser = {
        userName: document.getElementById("userName1").value,
        lastname: document.getElementById("lastname1").value,
        age: document.getElementById("age1").value,
        password: document.getElementById("password1").value,
        email: document.getElementById("email1").value,
        roles: controlRoleUserRegistration(Array.from(selectRolesRegistration.options).filter(option => option.selected).map(option => option.value))
    }
    await fetch(urlReg + "/registration", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(registrationUser)
    })
}
// Функция для вставки в JSON информации о выбранных ролях (если хочешь написать свою версию, то просто посмотри
// в консоли на вывод пользователей. Там все очень понятно и легко). Затем почитай про методы над массивами в JS.
// indexOf и includes, можно еще использовать find.
function controlRoleUserRegistration(roleUser) {
    let roles = [];
    if (roleUser.includes("1")) {
        roles.push({
            "id": 2,
            "name": "ROLE_USER",
            "users": null,
            "authority": "ROLE_USER"
        });
    }
    return roles;
}