document.addEventListener("DOMContentLoaded",function(){
    const  cityContainer= document.querySelector("[data-city]");
    if(!cityContainer){
        console.log("data-city attribute not found");
        return;
    }
    const city =cityContainer.getAttribute("data-city");
    function fetchWeatherData(){
        fetch(`/bin/getCityDetaials?city=${encodeURIComponent(city)}`).then((response)=>{
        if(!response.ok){
            throw new Error("Network response was not ok..");
    }
        return response.json();
        
        
    }).then((data)=>{
        const temp=data.main.temp;
        const description=data.weather[0].description;
        cityContainer.innerHTML=`
            <p><strong>City:</strong>${data.name}</p>
            <p><strong>Temperature:</strong>${temp}</p>
            <p><strong>Condition:</strong>${description}</p>
            <p style="font-size:12px; color:gray;">Last updated: ${new Date().toLocaleTimeString()}</p>`;
    }).catch((error)=>{
        console.error("error fetching datta:",error);
        cityContainer.innerHTML=`<p style="color:red" >unable to load weather data..</p>`
        
    })
    }
    fetchWeatherData();
    setInterval(fetchWeatherData,60000);
    
})
