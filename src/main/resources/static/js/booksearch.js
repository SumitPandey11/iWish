//this Javascript is using the jquery, you have already downloaded in minified version of it and placed it in the js folder.
//If you dont want to store the js file locally then use the CDN lin to jquer file
//JSON-AJAX code to fetch all the users
//'data' returned from the "http://localhost:8080/user" is passed to call back function
 $('#searchthis').keyup(function(){
    //create a variable to store the text that  user in typing
    var searchfield = $('#searchthis').val();
    //For case insensitive search, create regular expression,
    //"i" indicated ignore case
    var myExp = new RegExp(searchfield,"i")
    $.getJSON("http://localhost:8080/user", function(data){
     var output = '<ul cass="searchresults">';
     $.each(data, function(key, val){
         //if the name or email matches the text that usre key in then include that in optput
         // NOT EQUAL to -1 means it found the "text" that user typed in.
         if( ( val.name.search(myExp) != -1 ) || val.email.search(myExp) != -1 ){
             output += '<li>';
             output +=  val.name + ' - ' +  val.email ;
             output += '</li>';
         }
     });
     output += '<ul>';
     $('#update').html(output);
  });
 });

