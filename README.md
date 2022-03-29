# mMobs

Description

## API

Add Jitpack repository to pom.xml:
```xml
<repository>
	<id>jitpack.io</id>
	<url>https://jitpack.io</url>
</repository>
```
Add dependency to pom.xml:
```xml
<dependency>
    <groupId>com.github.bottleofench</groupId>
    <artifactId>mMobs</artifactId>
    <version>-SNAPSHOT</version>
</dependency>
```
An example of using the API:

```java
import api.bottleofench.mmobs.MMobsAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class YourPlugin extends JavaPlugin implements CommandExecutor {
    private static JavaPlugin instance;

    public static Plugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        new File(getDataFolder().getPath() + File.separator + "data").mkdirs();

        getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            MMobsAPI.spawnMob(p.getLocation(), new File(
                    getDataFolder().getPath() + File.separator + "data" + File.separator + "test.yml")
            );
        }
        return false;
    }
}

```