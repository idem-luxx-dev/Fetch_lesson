
const userAddFormId = $('#addForm');

function loadAddForm(){
    $('#NewUser').addClass('show').addClass('active');
}


function insertUser() {

    let headers = new Headers();
    headers.append('Content-Type', 'application/json; charset=utf-8');
    let role = [
                {
                    'id' : userAddFormId.find('#rroles').children(":selected").attr("id"),
                    'name' : userAddFormId.find('#rroles').children(":selected").attr("value")
                }
                ]


    let user = {
        'firstName': userAddFormId.find('#firstName').val(),
        'lastName': userAddFormId.find('#lastName').val(),
        'username': userAddFormId.find('#username').val(),
        'email': userAddFormId.find('#email').val(),
        'password': userAddFormId.find('#password').val(),
        'roles': role
        }

    console.log(role)
    console.log(user);
    let request = new Request('/api/add', {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(user)
    });

    fetch(request)
        .then(function (response) {
            response.json().then(function (userData) {
                console.log(userData);
                console.info("User with id = " + userData.id + " was inserted");
            });
        });
}

document.querySelector('#newUserLink').addEventListener('click',loadAddForm);
document.querySelector('#addButton').addEventListener('click', insertUser);

$('#newUserLink').click(() => {

    fetch('/api/roles')
        .then((responce) => {
            return responce.json();
        } )
        .then((data) => {
        data.forEach( r => {
            console.log(r);
            $('#rroles').append('<option value=' + r.name + ' id='+ r.id +'>' + r.name + '</option>');
        } )
    });
});
