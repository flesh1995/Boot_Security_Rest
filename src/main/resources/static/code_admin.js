const url = 'http://localhost:8080/api/admin';
const updateDataBase = document.getElementById("tableUsers");
const createUser = document.getElementById("newUser");

const allDifferentUser = (users) => {
    let stringInfoUser = "";
    users.forEach((user) => {
        stringInfoUser +=
        `<tr>
            <td>${user.id}</td>
            <td id=${'userName'+user.id}>${user.userName}</td>
            <td id=${'lastname'+user.id}>${user.lastname}</td>
            <td id=${'age'+user.id}>${user.age}</td>
            <td id=${'email'+user.id}>${user.email}</td>
            <td id=${'password'+user.id}>${user.password}</td>
            <td id=${'role'+user.id}>${user.roles.map(p => p.name).join('')}</td>

            <td> <button data-toggle="modal" data-target="#edit" onClick="edit(${user.id})"> Edit </button></td>
            <td> <button data-toggle="modal" data-target="#delete" onClick="delete(${user.id})"> Delete </button></td>
        </tr>`
    })

    updateDataBase.innerHTML = stringInfoUser;
}

function getAllUsers() { fetch(url).then(respon => respon.json()).then(tableAllUsers => updateDataBase(tableAllUsers)) }
getAllUsers();

createUser.addEventListener('submit', event => {
    event.preventDefault();
    let userNameJS = document.getElementById("userNameNew").value;
    let lastnameJS = document.getElementById("lastnameNew").value;
    let ageJS = document.getElementById("ageNew").value;
    let emailJS = document.getElementById("emailNew").value;
    let passwordJS = document.getElementById("passwordNew").value;
    let roleJS = controlRoleUser(Array.from(document.getElementById("roleNew").selectedOptions).map(role => role.value));
    let addUser = {
        userName: userNameJS,
        lastname: lastnameJS,
        age: ageJS,
        password: passwordJS,
        email: emailJS,
        roles: roleJS
    }
    fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(addUser)
    }).then(updateDB => {
        const list = [];
        list.push(updateDB);
        getAllUsers(updateDB);
    }).then(() => {
        document.getElementById('newFormUser').click();
    })
})

function controlRoleUser(roleUser) {
    let roles = [];
    if (roleUser === "ADMIN") {
        roles.push({
            "id": 1,
            "name": "ROLE_ADMIN",
            "users": null,
            "authority": "ROLE_ADMIN"
        });
    }
    if (roleUser === "USER") {
        roles.push({
            "id": 2,
            "name": "ROLE_USER",
            "users": null,
            "authority": "ROLE_USER"
        });
    }
    return roles;
}

function editUser(id) {
    fetch(url + '/' + id, {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        }
    }).then(respon => {
        respon.json().then(user => {
            document.getElementById('idDelete').value = user.id
        })
    });
}
async function editUserNow() {
    let idDif = document.getElementById("idEdit").value;
    let lastnameDif = document.getElementById("lastnameEdit").value;
    let ageDif = document.getElementById("ageEdit").value;
    let emailDif = document.getElementById("emailEdit").value;
    let passwordDif = document.getElementById("passwordEdit").value;
    let roleDif = controlRoleUser(Array.from(document.getElementById("roleEdit").selectedOptions).map(role => role.value));
    let userEditDB = {
        id: idDif,
        userName: userNameDif,
        lastname: lastnameDif,
        age: ageDif,
        password: passwordDif,
        email: emailDif,
        roles: roleDif
    }
    await fetch(url, {
        method: "PUTCH",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(userEditDB)
    });
    getAllUsers()
    document.getElementById("editModalCloseButton").click();
}

// Удаление пользователя
const deleteModalJS = document.getElementById('deleteModal');
const buttonClose = document.getElementById('deleteModalCloseButton');
const deleteFormJS = document.getElementById('DeleteUser');
function deleteUser(id) {
    deleteFormJS.addEventListener("submit", event => {
        event.preventDefault();
        fetch(url + deleteFormJS.id.value, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json; charset=UTF-8'
            }
        }).then(response => response.json()
            .then(user => {
                document.getElementById('idEdit').value = user.id
            }));
    });
}
async function deleteUserNow() {
    await fetch(url + document.getElementById('idDelete').value, {
        method: 'DELETE',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(document.getElementById('idDelete').value)
    })
    getAllUsers();
    buttonClose.click();
}



// Отображение информации о пользователе
const urlUser = "http://localhost:8080/user/loginUser";
const table = document.getElementById('userPanelTable');
const headerEmailJS = document.getElementById('headerEmail');
const headerRoleJS = document.getElementById('headerRole');
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
        })
}
userPageTable();
