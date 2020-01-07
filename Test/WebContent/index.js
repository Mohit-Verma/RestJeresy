//{"firstName":"fname","lastName":"lname","email":"email","address":"add","city":"city","state":"state","country":"country","pin":12}
var scope = this;
var customerData = [];
var customerDataHeader = {
  "index": "Customer Id",
  "firstName": 'First Name',
  "lastName": 'Last Name',
  "email": 'Email',
  "address": 'Address',
  "city": 'City',
  "state": 'State',
  "country": 'Country',
  "pin": 'Pin'
};

function submitData(){
  var data = {
    "firstName": document.getElementById('firstName').value,
    "lastName": document.getElementById('lastName').value,
    "email": document.getElementById('email').value,
    "address": document.getElementById('address').value,
    "city": document.getElementById('city').value,
    "state": document.getElementById('state').value,
    "country": document.getElementById('country').value,
    "pin": document.getElementById('pin').value
  };


  // alert(JSON.stringify(data));

  var url = 'http://localhost:8080/Test/rest/customer/add';
  var portal = new XMLHttpRequest();
  portal.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = this.responseText.split(':');
      if(response[0] === '0')
        scope.fetchCustomers();
      else
        document.getElementById("message").innerText = response[1];
    }
  };
  portal.open("POST", url, true);
  portal.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  portal.send(JSON.stringify(data));
}

//{"dbUrl":"dbUrl","userName":"dbUserName","password":"password","driver":"com.mysql.jdbc.Driver"}
function testDbConnection(){
  //jdbc:mysql://localhost:3306/egain:root@localhost 
  var data = {
    "dbUrl": document.getElementById("dbUrl").value,
    "userName": document.getElementById("dbUserName").value,
    "password": document.getElementById("userPassword").value,
    "driver": "com.mysql.jdbc.Driver"
  }
  // alert(JSON.stringify(data));

  var url = 'http://localhost:8080/Test/rest/db/config';
  var portal = new XMLHttpRequest();
  portal.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      var response = this.responseText.split(':');//0:success,1:error in communicating with db,-1:no input recieved
      if(response[0] === '0'){
        scope.fetchCustomers();
        document.getElementById("database_config_view").style.display="none";
        document.getElementById("customer_data_view").style.display="block";
        document.getElementById("add_customer_instance").style.display="block";
      } else{
        document.getElementById("database_config_view").style.display="block";
        document.getElementById("customer_data_view").style.display="none";
        document.getElementById("add_customer_instance").style.display="none";
      }
      document.getElementById("message").innerText = response[1];
    }
  };
  portal.open("POST", url, true);
  portal.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
  portal.send(JSON.stringify(data));
  return false;
}

function fetchCustomers(){
  var url = 'http://localhost:8080/Test/rest/customer/fetchall';
  var portal = new XMLHttpRequest();
  var data = [];
  portal.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      data = JSON.parse(this.responseText);
      scope.updateCustomerTableView(data);
      console.log(this.responseText);
    }
  };
  portal.open("GET", url, true);
  portal.send();
}

function updateCustomerTableView(custData){
  //setting header
  scope.customerData = custData;
  var header = '<tr>';
  for(var prop in customerDataHeader){
    header += '<th>' + customerDataHeader[prop] + '</th>'
  }
  header += '</tr>'

  var data = '';
  var row = '';
  custData.forEach((elem, index) =>{
    row = '<tr><td>' + index + '</td>';
    for(var prop in elem){
      row += '<td>' + elem[prop] + '</td>';
    }
    row += '</tr>';
    data += row;
  });

  document.getElementById('customer_data_table').innerHTML = header + data;
  if(custData.length == 0){
    document.getElementById('message').innerText = 'No record found in the customer table';
    document.getElementById('remove_customer').style.display = 'none';
  } else{
    document.getElementById('message').innerText = 'Record count: ' + custData.length;
    document.getElementById('remove_customer').style.display = 'block';
  }
}

function removeCustomer(){
    var index = document.getElementById('customerIndex').value;
    if(scope.customerData[index] == undefined || scope.customerData[index] == null){
      document.getElementById('message').innerText = 'Invalid Ckudtomer Row Number'
    } else{
      var data = scope.customerData[index];
      var url = 'http://localhost:8080/Test/rest/customer/remove';
      var portal = new XMLHttpRequest();
      portal.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
          var response = this.responseText.split(':');
          if(response[0] === '0')
            scope.fetchCustomers();
          else
            document.getElementById("message").innerText = response[1];
        }
      };
      portal.open("POST", url, true);
      portal.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
      portal.send(JSON.stringify(data));
    }    
}