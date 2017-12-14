var express = require('express');
var app     = express();
var bodyParser = require('body-parser');
var path = require('path');
var Client = require('node-rest-client').Client;
var client = new Client();
var cookieParser = require('cookie-parser');
var session = require('express-session');
var successStatusCode = 200

var registerPostUrl = "http://cartapi-6e52c8c7.6a24f60d.svc.dockerapp.io:3000/orders";
var loginPostUrl = "http://cartapi-6e52c8c7.6a24f60d.svc.dockerapp.io:3000/orders";
var searchUrl = "http://cartapi-6e52c8c7.6a24f60d.svc.dockerapp.io:3000/orders";
var defaultPostArgs = {
          headers: { "Content-Type": "application/json" }
  };


app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.urlencoded({ extended: true })); 

app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});

app.use(cookieParser());
app.use(session({
    secret: "fd34s@!@dfa453f3DF#$D&W",
    resave: false,
    saveUninitialized: true,
    cookie: { secure: !true }
}));

//Routers
/* GET login page. */
app.get('/', function(req, res) {
  res.sendFile('signup.html',{"root":"public"});
});

/* GET Signup page. */
app.get('/login', function(req, res) {
  res.sendFile('login.html',{"root":"public"});
});


app.get('/search', function(req, res) {
  if(req.session.isLoggedIn){
      res.sendFile('search.html',{"root":"public"});
    }
  else{
      res.send("You are not authorized to view this page");
  }
});


/*Form Data processing*/
/* GET signup form data. */
app.get('/processsignup', function(req, res) {
  var name  = req.query.name;
  var password  = req.query.password;
  var email = req.query.email;
  console.log(registerPostUrl+"?email="+email+"&password="+password+"&name="+name);
  //sendRestPostRequest(registerPostUrl,defaultPostArgs,registerPostCallback,req,res);
  registerPostCallback("data",successStatusCode,req,res);
});

app.get('/processlogin', function(req, res) {
  var password  = req.query.password;
  var email = req.query.email;
  console.log(loginPostUrl+"?email="+email+"&password="+password);
  //sendRestPostRequest(loginPostUrl,defaultPostArgs,loginPostCallback,req,res);
  loginPostCallback("data",successStatusCode,req,res);
});

app.get('/processsearch', function(req, res) {
  var from  = req.query.from;
  var to = req.query.to;
  var type  = req.query.type;
  var numberofpassengers = req.query.numberofpassengers;
  var date  = req.query.date;
  var time = req.query.time;
  var exacttime  = req.query.exacttime;
  var roundTrip = req.query.roundTrip;
  var url = searchUrl+"?From="+from+"&To="+to+"&trainType="+type+"&noOfPassengers="+numberofpassengers+"&Date="+date+"&trainDeptTime="+time+"&exactTime="+exacttime+"&roundTrip="+roundTrip;
  console.log(url);
  var data = [{
        "trainId": "SB0645",
        "deptTime": "07:09",
        "arrTime": "08:02",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0715",
        "deptTime": "07:39",
        "arrTime": "08:32",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0730",
        "deptTime": "07:54",
        "arrTime": "08:47",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0745",
        "deptTime": "08:09",
        "arrTime": "09:02",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0815",
        "deptTime": "08:39",
        "arrTime": "09:32",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0830",
        "deptTime": "08:54",
        "arrTime": "09:47",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0845",
        "deptTime": "09:09",
        "arrTime": "10:02",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0915",
        "deptTime": "09:39",
        "arrTime": "10:32",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0930",
        "deptTime": "09:54",
        "arrTime": "10:47",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    },
    {
        "trainId": "SB0945",
        "deptTime": "10:09",
        "arrTime": "11:02",
        "date": null,
        "from": "D",
        "to": "K",
        "noOfPassengers": null,
        "rate": 8,
        "trainType": "Regular",
        "totalDuration": 53
    }];
  //sendRestGetRequest(url,searchCallback,res);
  searchCallback(data,res);
});



app.listen(3001, function() {
  console.log('Server running at http://127.0.0.1:3001/');
});


/*rest Clients*/
sendRestPostRequest = function(url,args,callback,req,res){
//console.log(url);
//console.log(args);
client.post(url,args, function (data, response) {
    callback(data,response.statusCode,req,res);
});
};

function sendRestGetRequest(url,callback,req,res){
// direct way 

client.get(url, function (data, response) {
    // parsed response body as js object 
    console.log(data);
    callback(data,res);
    // raw response 
    //console.log(response);
});

};

/*Callbacks*/
registerPostCallback = function(data,statusCode,req,res){
//console.log(data);
console.log(statusCode);
//req.session.isLoggedIn = true;
res.send(statusCode.toString());
};

loginPostCallback = function(data,statusCode,req,res){
    //console.log(data);
    console.log(statusCode);

    if(statusCode == successStatusCode){
      req.session.isLoggedIn = true;
    }
    else{
     req.session.isLoggedIn = false;
    }
    res.send(statusCode.toString()); 
};

searchCallback = function(data,res){
res.setHeader('Content-Type', 'application/json');
res.send(JSON.stringify(data));

}
