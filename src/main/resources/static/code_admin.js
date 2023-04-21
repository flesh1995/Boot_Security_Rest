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
            
                        <td> <button class="btn-edit-user" name="edit" style="background-color: dodgerblue; 
                        color: white" data-toggle="modal" data-target="#edit" id="${user.id}"> Edit </button></td>
                        <td> <button class="btn-delete-user" name= "delete" style="background-color: red;
                        color: white" data-toggle="modal" data-target="#delete" id="${user.id}"> Delete </button></td>
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


// Функция для поиска пользователя по id.
// async function getUser(id) {
//     let urlSeach = url + id;
//     let response = await fetch(urlSeach);
//     return await response.json();
// }


// Создание нового пользователя. Тут самое главное понять, для меня это было не очевидно (пока не почитал),
// что querySelector ищет по тегу class, а не по id
const createUserForm = document.querySelector('.newUser');
const selectRoles = document.getElementById('roleNew');
const buttonUserTable = document.getElementById('nav-show-tab')
createUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    let addUser = {
        userName: document.getElementById("userNameNew").value,
        lastname: document.getElementById("lastnameNew").value,
        age: document.getElementById("ageNew").value,
        password: document.getElementById("passwordNew").value,
        email: document.getElementById("emailNew").value,
        roles: controlRoleUser(Array.from(selectRoles.options).filter(option => option.selected).map(option => option.value))
    }
    fetch(url + "/create", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(addUser)
    })
        .then(updateUserTable => {
            const userAdd = [];
            userAdd.push(updateUserTable);
            getAllUsers(userAdd)
        })
        .then(() => buttonUserTable.click());

})

// Функция для вставки в JSON информации о выбранных ролях (если хочешь написать свою версию, то просто посмотри
// в консоли на вывод пользователей. Там все очень понятно и легко). Затем почитай про методы над массивами в JS.
// indexOf и includes, можно еще использовать find.
function controlRoleUser(roleUser) {
    let roles = [];
    if (roleUser.includes("1")) {
        roles.push({
            "id": 1,
            "name": "ROLE_ADMIN",
            "users": null,
            "authority": "ROLE_ADMIN"
        });
    }
    if (roleUser.includes("2")) {
        roles.push({
            "id": 2,
            "name": "ROLE_USER",
            "users": null,
            "authority": "ROLE_USER"
        });
    }
    return roles;
}


const tableUse = document.querySelector('.tab-content');
const delete_id = document.getElementById('idDelete');
const delete_username = document.getElementById('userNameDelete');
const delete_lastName = document.getElementById('lastnameDelete');
const delete_age = document.getElementById('ageDelete');
const delete_email = document.getElementById('emailDelete');
const delete_password = document.getElementById('passwordDelete');
const delete_role = document.getElementById('roleDelete');
const edit_id = document.getElementById('idEdit');
const edit_username = document.getElementById('userNameEdit');
const edit_lastName = document.getElementById('lastnameEdit');
const edit_age = document.getElementById('ageEdit');
const edit_email = document.getElementById('emailEdit');
const edit_password = document.getElementById('passwordEdit');
const edit_role = document.getElementById('roleEdit');

//Заполнение модальных окон редактирования и удаления. Тут самое главное узнать id пользователя.
tableUse.addEventListener('click', (event) => {
    event.preventDefault();
    let userID = event.target.id;
    let nameButton = event.target.name;
    if (nameButton === "delete") {
        fetch(url + "/" + userID, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=UTF-8'
            },
        })
            .then(responce => responce.json())
            .then(user => {
                delete_id.value = user.id;
                delete_username.value = user.userName;
                delete_lastName.value = user.lastname;
                delete_age.value = user.age;
                delete_email.value = user.email;
                delete_password.value = user.password;
                delete_role.value = user.roles.map(role => " " + role.name.substring(5));
            })
    } if (nameButton === "edit") {
        fetch(url + "/" + userID, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=UTF-8'
            },
        })
            .then(responce => responce.json())
            .then(user => {
                edit_id.value = user.id;
                edit_username.value = user.userName;
                edit_lastName.value = user.lastname;
                edit_age.value = user.age;
                edit_email.value = user.email;
                edit_password.value = user.password;
                edit_role.value = user.roles.map(role => " " + role.name.substring(5));
            })
    }
})


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
const buttonDelete = document.querySelector('.deleteUser');
const buttonDeleteClose = document.getElementById('deleteModalCloseButton');

buttonDelete.addEventListener('click', (event) => {
    event.preventDefault();
    let userID = event.target.id;
    if (userID === "deleteModalButton"){
        deleteUser();
    }
})
async function deleteUser() {
    await fetch(url + "/delete/" + delete_id.value, {
        method: "DELETE",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(delete_id.value)
    });
    getAllUsers()
    buttonDeleteClose.click();
}