

const register = () => {
    let userEmail = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    fetch("http://localhost:8080/api/investors", {
        method: 'POST',
        credentials: "include",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:"userEmail=" + email + "&password=" + password
    }).then(function (response) {
        if (response.ok){
        console.log("jojoj");
            logIn();
        }

    }).catch(function(error) {
        alert('Investor not saved: ' + error.message);
    });

};

const logIn = () => {
    let userEmail = document.getElementById("email").value;
    let password = document.getElementById("password").value;

    fetch("http://localhost:8080/api/login", {
        method: 'POST',
        credentials: "include",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body:"userEmail=" + userEmail + "&password=" + password
    }).then(function (response) {
        if (response.ok){
            location.reload();
    }
        }).catch(function(error) {
        alert('Not logged in: ' + error.message);
    });

}

/*const logOut = () => {

    fetch("http://localhost:8080/api/logout", {
        method: 'POST',
    }).then(function (response) {
        if (response.ok){
            location.reload();
        }
    }).catch(function(error) {
        alert('Not logged out: ' + error.message);
    });

}*/