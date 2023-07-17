function submitLogin(username, password) {
    var user = document.getElementById("username").value;
    var pass = document.getElementById("password").value;
    return fetch = ("http://localhost:8080/user/loginAuth/${username}&${password}", {
        method: 'GET'
    }).then(res => res.text()).then(res => console.log(res)).then(res=>location.reload());
    if(user == username & pass == password){
        alert("đăng nhập thành côg");
        console.log(user + pass);
    }
    else{
        alert("Đăng nhập thất bại!");
    }
   }


