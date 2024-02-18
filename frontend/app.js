let map;

async function initMap() {
  const { Map } = await google.maps.importLibrary("maps");

  map = new Map(document.getElementById("map"), {
    center: { lat: 43.6532, lng: -79.3832 },
    zoom: 10,
  });
  setMarkers(map);
}

const shelters = [
  ["Cornerstone Place", 43.69185, -79.43987, 1],
  ["Christie Ossington Neighbourhood Centre", 43.66619, -79.44591, 2],
  ["Sagatay", 43.68184, -79.41887, 3],
];

function setMarkers(map) {
  for (let i = 0; i < shelters.length; i++) {
    const shelter = shelters[i];
    new google.maps.Marker({
      position: { lat: shelter[1], lng: shelter[2] },
      map: map,
    });
  }
}

initMap();
