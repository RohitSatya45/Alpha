document.addEventListener("load", function () {
  const dropdown = document.querySelector("#citySelect");
  const weatherContainer = document.querySelector("#detailsContainer");
  let selectedCity = "";
  let intervalId;

  if (!dropdown || !weatherContainer) {
    console.error("Required element not found");
    return;
  }

  // 🔹 Fetch city list
  fetch("/bin/getGeoCities")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Failed to fetch city list");
      }
      return response.json();
    })
    .then((cities) => {
      dropdown.innerHTML = "";
      const defaultOption = document.createElement("option");
      defaultOption.textContent = "...Select City...";
      defaultOption.selected = true;
      defaultOption.disabled = true;
      dropdown.appendChild(defaultOption);

      cities.forEach((city) => {
        const option = document.createElement("option");
        option.textContent = city;
        option.value = city;
        dropdown.appendChild(option);
      });
    })
    .catch((error) => {
      console.error("Error loading cities:", error);
      dropdown.innerHTML = `<option>Error Loading Cities</option>`;
    });

  // 🔹 On city change
  dropdown.addEventListener("change", function () {
    selectedCity = dropdown.value;
    fetchAndRenderWeather();

    // prevent stacking intervals
    if (intervalId) clearInterval(intervalId);
    intervalId = setInterval(fetchAndRenderWeather, 60000);
  });

  // 🔹 Fetch weather data
  function fetchAndRenderWeather() {
    if (!selectedCity) return;

    fetch(`/bin/getCityDetaials?city=${encodeURIComponent(selectedCity)}`)
      .then((response) => {
        if (!response.ok) {
          throw new Error("Weather API failed.");
        }
        return response.json();
      })
      .then((data) => {
        const temp = data.main.temp;
        const description = data.weather[0].description;

        weatherContainer.innerHTML = `
          <p><strong>City:</strong> ${data.name}</p>
          <p><strong>Temperature:</strong> ${temp}°C</p>
          <p><strong>Condition:</strong> ${description}</p>
          <p style="font-size:12px; color:gray;">Last updated: ${new Date().toLocaleTimeString()}</p>
        `;
      })
      .catch((error) => {
        console.error("Weather error:", error);
        weatherContainer.innerHTML = `<p style="color:red;">Unable to load weather data.</p>`;
      });
  }
});
