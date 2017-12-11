var successStatusCode = 200;

signup = function(){
	var name = document.getElementById("name").value;
    var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var url = "http://localhost:3001/processsignup?name="+name+"&password="+password+"&email="+email;
    sendAjaxRequest(url,signupCallback);
  }

signupCallback = function(statusCode){
if(statusCode == "201"){
    alert("succesfully registerd. Please check your email to activate your account");
}
else{
    alert("registration rest api down");
}
}

login = function(){
	var password = document.getElementById("password").value;
    var email = document.getElementById("email").value;
    var url = "http://localhost:3001/processlogin?password="+password+"&email="+email;
    sendAjaxRequest(url,loginCallBack);
}

loginCallBack = function(statusCode){
if(statusCode == successStatusCode){
    window.location.href = "http://localhost:3001/search";
}
else{
    alert("Invalid Credentials");
}  
}

sendAjaxRequest = function(url,callback){
	var xmlhttp = new XMLHttpRequest();
                    xmlhttp.onreadystatechange = function() {
                        if (this.readyState == 4 && this.status == 200) {
                            //console.log(this.responseText);
                            callback(this.responseText);
                        }
                    };
    xmlhttp.open("GET", url, true);
    xmlhttp.send();
}