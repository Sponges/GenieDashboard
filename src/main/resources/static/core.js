$(document).ready(function() {
	var selectionElement = $("#network-selection");
	if (selectionElement.length) {
		var clients = ["skype"];
		for (client of clients) {
			var elementId = "#" + client + "_networks";
			var collectionId = "#" + client + "-collection";
			if ($(elementId).length) { // checks if the element is actually there
				selectionElement.append('<div class="col s4"><div class="card"><div class="card-content"><span class="card-title">' + client + '</span><p>Here are all the ' + client + ' chats you can manage.</p></div></div><ul id="' + client + '-collection" class="collection"></ul></div>');
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
    var array = json.networks;
    for (network of array) {
        var networkId = network.id;
        var networkTitle = "My Network";
        $(collectionId).append('<a class="collection-item avatar" href="/dashboard?network=' + networkId + '"><img class="circle" src="http://placehold.it/150x150" /><div class="select-items"><span class="title">' + networkTitle + '</span><p>Some descriptive line.</p></div></a>');
    }
}