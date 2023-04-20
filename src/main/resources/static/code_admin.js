const url = 'http://localhost:8080/api';
const updateDataBase = document.getElementById("usersAllTable");
const headerEmailJS = document.getElementById('headerEmail');
const headerRoleJS = document.getElementById('headerRole');

// Отображение информации о пользователях
function getAllUsers() {
    fetch(url)
        .then(response => response.json())
        .then(tableAllUsers => {
            let stringInfoUser = "";
            tableAllUsers.forEach(user => {
                stringInfoUser +=
                    `<tr>
                        <td>${user.id}</td>
                        <td>${user.userName}</td>
                        <td>${user.lastname}</td>
                        <td>${user.age}</td>
                        <td>${user.email}</td>
                        <td>${user.password}</td>
                        <td>${user.roles.map(p => p.name.substring(5)).join(',')}</td>
            
                        <td> <button class="btn-edit-user" style="background-color: dodgerblue; color: white" 
                        data-toggle="modal" data-target="#edit" data-id="${user.id}"> Edit </button></td>
                        <td> <button class="btn-delete-user" style="background-color: red; color: white" 
                        data-toggle="modal" data-target="#delete" data-id="${user.id}"> Delete </button></td>
                    </tr>`
            })
            updateDataBase.innerHTML = stringInfoUser;
            }
        ).catch(function(error) {
            console.log(error);
        });
}
getAllUsers();


// Отображение информации о пользователе
const urlUser = "http://localhost:8080/user/loginUser";
const table = document.getElementById('userPanelTable');
function userPageTable() {
    fetch(urlUser)
        .then(response => response.json())
        .then(userPage => {
            headerEmailJS.append(userPage.email);
            let roles = userPage.roles.map(role => " " + role.name.substring(5));
            headerRoleJS.append(roles);
            let user = '';
            user +=
                `<tr>
                    <td>${userPage.id}</td>
                    <td>${userPage.userName}</td>
                    <td>${userPage.lastname}</td>
                    <td>${userPage.age}</td>
                    <td>${userPage.email}</td> 
                    <td>${userPage.password}</td> 
                    <td>${roles}</td>
                </tr>`;
            table.innerHTML = user;
        });
}
userPageTable();

// Создание нового пользователя
// const createUser = document.getElementById("newUser");
// const createUserForm = document.getElementById('newFormUser')
// createUserForm.addEventListener('submit', event => {
//     event.preventDefault();
//     let addUser = {
//         userName: document.getElementById("userNameNew").value,
//         lastname: document.getElementById("lastnameNew").value,
//         age: document.getElementById("ageNew").value,
//         password: document.getElementById("passwordNew").value,
//         email: document.getElementById("emailNew").value,
//         roles: controlRoleUser(Array.from(document.getElementById("roleNew").selectedOptions).map(role => role.value))
//     }
//     fetch(url, {
//         method: 'POST',
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json; charset=UTF-8'
//         },
//         body: JSON.stringify(addUser)
//     }).then(response => response.json())
//         .then(updateDB => {
//         const list = [];
//         list.push(updateDB);
//         getAllUsers(updateDB);
//     }).then(() => createUserForm.click())
// })
//
// function controlRoleUser(roleUser) {
//     let roles = [];
//     if ("ADMIN" in roleUser) {
//         roles.push({
//             "id": 1,
//             "name": "ROLE_ADMIN",
//             "users": null,
//             "authority": "ROLE_ADMIN"
//         });
//     }
//     if ("USER" in roleUser) {
//         roles.push({
//             "id": 2,
//             "name": "ROLE_USER",
//             "users": null,
//             "authority": "ROLE_USER"
//         });
//     }
//     return roles;
// }
//
// // Редактирование пользователя
// const EditId = document.getElementById('idEdit');
// const buttonCloseEdit = document.getElementById('editModalCloseButton');
// const editFormJS = document.getElementById('EditUser');
// function editUser(id) {
//     fetch(url + '/' + id, {
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json; charset=UTF-8'
//         }
//     }).then(respon => {
//         respon.json().then(user => {
//             document.getElementById('idDelete').value = user.id
//         })
//     });
// }
// async function editUserNow() {
//     let userEditDB = {
//         id: document.getElementById("idEdit").value,
//         userName: document.getElementById("userNameEdit").value,
//         lastname: document.getElementById("lastnameEdit").value,
//         age: document.getElementById("ageEdit").value,
//         password: document.getElementById("passwordEdit").value,
//         email: document.getElementById("emailEdit").value,
//         roles: controlRoleUser(Array.from(document.getElementById("roleEdit").selectedOptions).map(role => role.value))
//     }
//     await fetch(url, {
//         method: "PATCH",
//         headers: {
//             'Accept': 'application/json',
//             'Content-Type': 'application/json; charset=UTF-8'
//         },
//         body: JSON.stringify(userEditDB)
//     });
//     getAllUsers()
//     document.getElementById("editModalCloseButton").click();
// }


// Удаление пользователя
const deleteId = document.getElementById('idDelete');
const buttonCloseDelete = document.getElementById('deleteModalCloseButton');
const form = document.getElementById('DeleteUser');
const deleteFormJS = document.forms["DeleteUser"];
const farmat = document.getElementById('delete')

const deleteForm = document.getElementById('delete');
const delete_id = document.getElementById('idDelete');
const delete_username = document.getElementById('userNameDelete');
const delete_lastName = document.getElementById('lastNameDelete');
const delete_age = document.getElementById('ageDelete');
const delete_email = document.getElementById('emailDelete');
const delete_password = document.getElementById('passwordDelete');

let formDelete = document.forms["DeleteUser"];



// // Отображение информации о пользователях
// async function getUser(id) {
//     let url = "http://localhost:8080/api/" + id;
//     let response = await fetch(url);
//     return await response.json();
// }


