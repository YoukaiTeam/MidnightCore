# MidnightCore
Data, games and various foreground/background mechanics engine for Minecraft Network.

### Content

-  **API** - HTTP client & request builder. JSON serialization and Model building. This includes the GamePlayer UserStorage. This API is obligatory to any other MioCore module.
	- **bukkit** - Bukkit API module
	- **shared** - Shared module for bukkit/bungee functionalities.

- **Commons** - Shared functionalities between all network servers.
	- **bukkit** - Bukkit Commons module
	- **shared** - Shared classes between bukkit/bungee plugins and **i18n** locale language managing.

API plugin is an obligatory dependency for every server plugin that interacts with any data model.  This plugin interacts with a Midnight Backend instance, allowing minimal funcionalities to any independent Minecraft server instance. 
