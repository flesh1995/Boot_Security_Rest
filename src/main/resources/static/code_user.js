const url = "http://localhost:8080/user/loginUser";
const table = document.getElementById('userPanelTable');
const headerEmailJS = document.getElementById('headerEmail');
const headerRoleJS = document.getElementById('headerRole');
function userPageTable() {
    fetch(url)
        .then(response => response.json())
        .then((userPage) => {
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
        .catch(function(error) {
            console.log(error);
        });
}
userPageTable();