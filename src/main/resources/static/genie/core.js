$(document).ready(function() {
	{
		var jsonKey = $("#genie_json");
		if (!jsonKey.length) {
			return;
		}
		var json = JSON.parse($(jsonKey).html());
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
	{

	}
}

function hideRoleDisplay(json) {
	$("#settings_box").html("<h4>Select one of the roles on the left or create one if none exist!</h4>");
}