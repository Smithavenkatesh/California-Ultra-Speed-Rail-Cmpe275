search = function(){
	var from = document.getElementById("from").value;
    var to = document.getElementById("to").value;
    var type = document.getElementById("type").value;
    var numberofpassengers = document.getElementById("numberofpassengers").value;
    var date = document.getElementById("date").value;
    var time = document.getElementById("time").value;
    var exacttime = document.getElementById("exacttime").checked;
    var roundTrip = "no";

    var url = "http://localhost:3001/processsearch?from="+from+"&to="+to+"&type="+type+"&numberofpassengers="+numberofpassengers+"&date="+date+"&time="+time+"&exacttime="+exacttime+"&roundTrip="+roundTrip;
    sendAjaxRequest(url,searchCallback);
    console.log(url);
  }


var app = angular.module("searchresults", []); 
app.controller("myCtrl", function($scope,$http) {
    $scope.quantity = 1;
    $scope.totalPrice = "$0";
    $scope.results = [];  
    $scope.SearchResultsHeading = "";

    searchCallback = function(data){
    	$scope.SearchResultsHeading = "Search Results";
    	$scope.results = [];
    	data = JSON.parse(data);
    	for(i=0;i<data.length;i++){
    		$scope.results.push({"trainId":data[i].trainId,"deptTime":data[i].deptTime,"arrTime":data[i].arrTime,"date":data[i].date,"from":data[i].from,"to":data[i].to,"noOfPassengers":data[i].noOfPassengers,"rate":data[i].rate+"$","trainType":data[i].trainType,"totalDuration":data[i].totalDuration+" minutes"});
    	}
		$scope.$apply();
  	};

});
