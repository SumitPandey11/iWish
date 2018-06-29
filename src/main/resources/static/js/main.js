var animalContainer = document.getElementById("animal-info");
var btn = document.getElementById("btn");

var myinfobtn = document.getElementById("myinfobtn");
var personalInfoContainer = document.getElementById("my-personal-info");

var bookbtn = document.getElementById("bookimg");
var bookInfoContainer = document.getElementById("divbook1");

btn.addEventListener("click", function(){
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','http://ip-api.com/json');

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);
        renderHTML(ourData);
    };
    ourRequest.send();
    btn.classList.add('hide-me');

});

function renderHTML(data){
    var htmlString = "<p> IP Address: " +  data.query +

                     "<p> Country: " + data.country +
                     "<p> countryCode: " + data.countryCode +
                     "<p> city: " +  data.city +
                     "<p> zip: " + data.zip  +
                     "</p> region: " + data.region +
                     "</p> regionName: " + data.regionName +
                     "</p> Time Zone: " + data.timezone +
                     "<p> Host As: " + data.as  +
                     "<p> isp: " + data.isp  +
                     "<p> org: " + data.org +
                     "<p> lat: " + data.lat +
                     "</p> lon: " + data.lon;


    animalContainer.insertAdjacentHTML('beforeend',htmlString);
}

myinfobtn.addEventListener("click", function(){
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','/json/sumit.json',true);

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);
        renderPersonalHTML(ourData);
    };
    ourRequest.send();

});

function renderPersonalHTML(data){
    var htmlString = "<p> Firstname: " +  data.firstname +
                     "<p> lastname: " + data.lastname;

    personalInfoContainer.insertAdjacentHTML('beforeend',htmlString);
}

function getBookDetails(id){
   var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','/json/book'+id+'.json',true);

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);
        renderbookHTML(ourData , id);
    };
    ourRequest.send();
}



function renderbookHTML(data , id){
    var bookInfoDivContainer = document.getElementById("divbook"+id);
    var htmlString = "<p> Title: " +  data.title +
                     "<p> Autor: " + data.autor +
                     "<p> Publisher: " + data.Publisher +
                     "<p> Rating: " + data.rating +  "  Review's : " + data.numerofreview +
                     "<p> price: " + data.price + "<p> Available format: ";

      for(var i = 0 ; i < data.format.length ; i++){
            htmlString += data.format[i] + ", ";
      }

        bookInfoDivContainer.insertAdjacentHTML('beforeend',htmlString);
}

/*

bookbtn.addEventListener("click", function(){
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','/json/book1.json',true);

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);
        renderbookHTML(ourData);
    };
    ourRequest.send();

});

function renderbookHTML(data){
    var htmlString = "<p> Title: " +  data.title +
                     "<p> Autor: " + data.autor +
                     "<p> Publisher: " + data.Publisher +
                     "<p> Rating: " + data.rating +  "  Review's : " + data.numerofreview +
                     "<p> price: " + data.price + "<p> Available format: ";

      for(var i = 0 ; i < data.format.length ; i++){
            htmlString += data.format[i] + ", ";
      }

        bookInfoContainer.insertAdjacentHTML('beforeend',htmlString);
}




*/
