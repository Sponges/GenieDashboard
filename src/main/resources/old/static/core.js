$(document).ready(function() {
	var selectionElement = $("#network-selection");
	if (selectionElement.length) {
		var clients = JSON.parse($("#clients_list").html()).clients;
		for (client of clients) {
			var clientId = client.id;
			var elementId = "#" + clientId + "_networks";
			var collectionId = "#" + clientId + "-collection";
			if ($(elementId).length) { // checks if the element is actually there
				selectionElement.append('<div class="col s4"><div class="card"><div class="card-content"><span class="card-title">' + clientId + '</span><p>Here are all the ' + clientId + ' chats you can manage.</p></div></div><ul id="' + clientId + '-collection" class="collection"></ul></div>');
				loadNetworks(elementId, collectionId);
			}
		}
	}
});

function loadNetworks(elementId, collectionId) {
	var networks = $(elementId).html();
    if (networks.length < 1) {
        return;
    }
    var json = JSON.parse(networks);
    if (json.hasOwnProperty("error")) {
        console.log("JSON networks error: " + json.error + " - " + json);
        return;
    }
    var array = json.networks;
    for (network of array) {
        var networkId = network.id;
        var networkTitle = "My Network";
        $(collectionId).append('<a class="collection-item avatar" href="/dashboard?network=' + networkId + '"><img class="circle" src="http://placehold.it/150x150" /><div class="select-items"><span class="title">' + networkTitle + '</span><p>Some descriptive line.</p></div></a>');
    }
}