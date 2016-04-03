$(document).ready(function(){
    $('#postTypeSelector').on('change',function(){
        document.getElementById("container-info").innerHTML = "";
        if($(this).val() === 'Car'){    //Get just car posts
            displayElements("Car");
        }
        else{                           //Get just taxi posts
            displayElements("Taxi");
        }
    });
});

function displayElements(postType){
    $.get("../ListPostServlet?type=" + postType,function(data){
        //$('.main-container').html(data);
        if(data.posts.length > 0){
            var container = document.getElementById("container-info");
        }
        else{
            var container = document.getElementById("container-info");
            var label = document.createElement("h4");
            var text = document.createTextNode("No records found of " + postType);
            label.appendChild(text);
            container.appendChild(label);
        }
        for(var i = 0; i < data.posts.length; i++){
            //console.log("postType: " + data.posts[i].postType);
            //Adding username
            var label = document.createElement("p");
            label.setAttribute("id","username"+i);
            var text = document.createTextNode("Username: " + data.posts[i].username);
            label.appendChild(text);
            container.appendChild(label);

            //Adding date
            var label = document.createElement("p");
            label.setAttribute("id","date"+i);
            var text = document.createTextNode("Date: " + data.posts[i].date);
            label.appendChild(text);
            container.appendChild(label);

            //Adding departure
            var label = document.createElement("p");
            label.setAttribute("id","departure"+i);
            var text;
            if(postType === "taxi"){
                text = document.createTextNode("City: " + data.posts[i].departure);
            }
            else{
                var text = document.createTextNode("Departure: " + data.posts[i].departure);
            }
            label.appendChild(text);
            container.appendChild(label);

            //Check if postType is a car to add additional fields
            if(data.posts[i].postType === "car"){
                //Adding destination
                var label = document.createElement("p");
                label.setAttribute("id","destination"+i);
                var text = document.createTextNode("Destination: " + data.posts[i].destination);
                label.appendChild(text);
                container.appendChild(label);

                //Adding carType
                var label = document.createElement("p");
                label.setAttribute("id","carType"+i);
                var text = document.createTextNode("Car type: " + data.posts[i].carType);
                label.appendChild(text);
                container.appendChild(label);

                //Adding carYear
                var label = document.createElement("p");
                label.setAttribute("id","carYear"+i);
                var text = document.createTextNode("Car year: " + data.posts[i].carYear);
                label.appendChild(text);
                container.appendChild(label);

            }
            var label = document.createElement("hr");
            container.appendChild(label);
      }
    });
}

$(document).ready(function(){
     displayElements("car");
});


