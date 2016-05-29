$(document).ready(function() {
	{
		var jsonKey = $("#genie_json");
		if (!jsonKey.length) {
			return;
		}
		var json = JSON.parse($(jsonKey).html());
		var showAds = json.show_ads;
		if (showAds) {
			$("#advertisement-section").show();
		}

		loadNetworks(json);

		var currentPage = json.page_name;

		// roles page content loading
		if (currentPage == "roles") {
			loadRoles(json);
			if (json.hasOwnProperty("role_info")) {
				displayRole(json);
			} else {
				hideRoleDisplay(json);
			}
		}

		// active sidebar tab shit - move to bottom so elements effected by js can be activated
		activeTabs(json);
	}
});

String.prototype.capitalizeFirstLetter = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}

function activeTabs(json) {
	if (json.hasOwnProperty("active_tabs")) {
        var tabsArray = json.active_tabs;
        for (var item in tabsArray) {
            var id = tabsArray[item];
            if (id.indexOf("_module_") >= 0) {
                $("#" + id).children().css("color", "white");
            } else {
                $("#" + id).addClass("active");
            }
        }
    }
}

function loadRoles(json) {
	var rolesTable = $("#roles_table");
	var networkId = json.network_id;
	var roles = json.roles_list;
    var array = roles.roles;
    for (var id in array) {
        var roleId = array[id];
        var roleName = roleId.capitalizeFirstLetter();
        rolesTable.append('<tr id="' + roleId + '_role_tab"><td><a href="/roles?network=' + networkId + '&role=' + roleId + '">' + roleName + '</a></td></tr>');
    }
}

function displayRole(json) {
	var role = json.role_info.role;
	var roleId = role.id;
	var roleName = roleId.capitalizeFirstLetter();

	$(".role-label").append(roleName);
	$("#roleName").attr("placeholder", roleName);

	// populating permissions table
	{
		var permissions = role.permissions;
		var table = $("#permissions_table");
		for (nodeIndex in permissions) {
			var node = permissions[nodeIndex];
			table.append('<tr><td>' + node + '</td><td style="text-align: center;"><a href="#"><i class="fa fa-trash text-red"></i></a></td></tr>');
		}
	}

	// populating users table
	if (role.hasOwnProperty("users")) {
		var users = role.users;
		var table = $("#users_table");
		for (userIndex in users) {
			var user = users[userIndex];
			table.append('<tr><td>' + user.username + '</td><td>' + user.id + '</td><td style="text-align: center;"><a href="#"><i class="fa fa-trash text-red"></i></a></td></tr>');
		}
	}
}

function hideRoleDisplay(json) {
	$("#settings_box").html("<h4>Select one of the roles on the left or create one if none exist!</h4>");
}

function loadNetworks(json) {
	var networks = json.networks.networks;
	var element = $(".sidebar-menu");
	for (networkIndex in networks) {
		var network = networks[networkIndex];
		var networkId = network.id;
		element.append('<li id="' + networkId + '_item"> <a href="#"><i class="fa fa-th-list"></i> <span>My Guild</span> <i class="fa fa-angle-left pull-right"></i> </a> <ul class="treeview-menu"> <li id="' + networkId + '_overview_item"><a href="/overview?network=' + networkId + '"><i class="fa fa-laptop"></i> Overview</a></li><li id="' + networkId + '_chat_item"><a href="/chat?network=' + networkId + '"><i class="fa fa-comments"></i> Chat</a></li><li id="' + networkId + '_settings_item"><a href="/settings?network=' + networkId + '"><i class="fa fa-cog"></i> Settings</a></li><li id="' + networkId + '_roles_item"><a href="/roles?network=' + networkId + '"><i class="fa fa-users"></i> Manage Roles</a></li><li id="' + networkId + '_modules_item"> <a href="#"><i class="fa fa-plug"></i> Modules <i class="fa fa-angle-left pull-right"></i></a> <ul class="treeview-menu"> <li id="' + networkId + '_manage_module_item"><a href="/modules?network=' + networkId + '&module=manage"><i class="fa fa-cogs"></i> Manage Modules</a></li><li id="' + networkId + '_moderation_module_item"><a href="/modules?network=' + networkId + '&module=moderation"><i class="fa fa-check text-green"></i> Moderation</a></li><li id="' + networkId + '_music_module_item"><a href="/modules?network=' + networkId + '&module=music"><i class="fa fa-check text-green"></i> Music Bot</a></li><li id="' + networkId + '_bridges_module_item"><a href="/modules?network=' + networkId + '&module=bridges"><i class="fa fa-check text-green"></i> Chat Bridges</a></li><li id="' + networkId + '_shitposting_module_item"><a href="/modules?network=' + networkId + '&module=shitposting"><i class="fa fa-times text-red"></i> Shitposting</a></li><li id="' + networkId + '_chatterbot_module_item"><a href="/modules?network=' + networkId + '&module=chatterbot"><i class="fa fa-check text-green"></i> Chatterbot</a></li><li id="' + networkId + '_searchers_module_item"><a href="/modules?network=' + networkId + '&module=searchers"><i class="fa fa-check text-green"></i> Searchers</a></li></ul> </li></ul> </li>');
	}
}