function getLoctaionDetailsByIP(){
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','http://ip-api.com/json');

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);
        renderHTML(ourData );
    };
    ourRequest.send();

 }

function renderHTML(data){

    var ipDivContainer = document.getElementById('animal-info');

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


    ipDivContainer.insertAdjacentHTML('beforeend',htmlString);
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


function getReadingListByUserId(){
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET','http://localhost:8080/readinglist/63');

    ourRequest.onload = function(){
        var ourData = JSON.parse(ourRequest.responseText);
        console.log(ourData);

    };
    ourRequest.send();

 }


function goBack() {
    window.history.back();
}
