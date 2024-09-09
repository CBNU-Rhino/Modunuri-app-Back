// Kakao Map Initialization
var mapContainer = document.getElementById('map'), // 지도 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 기본 중심 좌표
        level: 8 // 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도 생성

// 저장된 관광지 마커 표시
var places = [
    { title: "관광지1", lat: 33.450701, lng: 126.570667, contentId: "126486" },
    { title: "관광지2", lat: 33.450933, lng: 126.572345, contentId: "126525" }
];

places.forEach(function(place) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.lat, place.lng),
        title: place.title
    });

    // 마커 클릭 이벤트
    kakao.maps.event.addListener(marker, 'click', function() {
        showSearchResultModal(place.contentId);
    });
});

// 저장된 관광지 목록 불러오기
function loadSavedPlaces() {
    fetch('/users/getFavoriteContents')
        .then(response => response.json())
        .then(data => {
            data.forEach(contentId => {
                // 각 장소에 대한 마커나 UI 처리를 여기서 수행
                console.log("Saved place: " + contentId);
            });
        })
        .catch(error => {
            console.error('Error loading saved places:', error);
        });
}

// 페이지 로드 시 저장된 관광지 목록 불러오기
document.addEventListener('DOMContentLoaded', function () {
    loadSavedPlaces();
});

// 지도에 추가한 관광지 정보를 팝업으로 보여줌
function showSearchResultModal(contentId) {
    var modal = document.getElementById('search-result-modal');
    modal.style.display = "block";

    // 관광지 정보를 가져와서 보여줌 (API 호출)
    fetch(`/touristSpot/Json/tourist-information?contentId=${contentId}`)
        .then(response => response.json())
        .then(data => {
            document.getElementById('place-info').innerText = data.touristDetail.title;
            document.getElementById('saveButton').onclick = function() {
                savePlace(contentId);
            };
        });
}

// 관광지를 저장하는 함수
function savePlace(contentId) {
    fetch('/users/save', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ contentId: contentId })
    })
        .then(response => response.json())
        .then(data => {
            alert('저장되었습니다!');
            closeModal();
        });
}

// 모달 닫기
function closeModal() {
    document.getElementById('search-result-modal').style.display = "none";
}

// 드래그 앤 드롭 기능
function allowDrop(ev) {
    ev.preventDefault();
}

function drag(ev) {
    ev.dataTransfer.setData("text", ev.target.id);
}

function drop(ev) {
    ev.preventDefault();
    var data = ev.dataTransfer.getData("text");
    ev.target.appendChild(document.getElementById(data));
}

// 일차 추가 기능
var dayCount = 1;
function addDay() {
    dayCount++;
    var dayDiv = document.createElement("div");
    dayDiv.id = "day" + dayCount;
    dayDiv.className = "day";
    dayDiv.innerHTML = `<h3>${dayCount}일차</h3><div class="drop-area" ondrop="drop(event)" ondragover="allowDrop(event)"></div>`;
    document.querySelector('.days').appendChild(dayDiv);
}

// 경로 그리기 (각 일차별 다른 색상)
var dayRoutes = [
    { coords: [{ lat: 33.450701, lng: 126.570667 }, { lat: 33.450933, lng: 126.572345 }], color: '#FF0000' }
];

dayRoutes.forEach(function(route) {
    var linePath = route.coords.map(coord => new kakao.maps.LatLng(coord.lat, coord.lng));
    var polyline = new kakao.maps.Polyline({
        path: linePath,
        strokeWeight: 5,
        strokeColor: route.color,
        strokeOpacity: 0.7,
        strokeStyle: 'solid'
    });
    polyline.setMap(map);
});
