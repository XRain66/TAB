package me.neznamy.tab.platforms.bungeecord.injection;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import me.neznamy.tab.platforms.bungeecord.BungeeTabPlayer;
import me.neznamy.tab.shared.features.injection.NettyPipelineInjector;
import me.neznamy.tab.shared.platform.TabPlayer;
import net.md_5.bungee.UserConnection;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * Pipeline injection to secure proper functionality
 * of some features by preventing other plugins
 * from overriding it.
 */
public class BungeePipelineInjector extends NettyPipelineInjector {

    /** Whether ByteBuf deserialization should be enabled or not */
    private final boolean byteBufDeserialization;

    /**
     * Constructs new instance of the feature
     */
    public BungeePipelineInjector() {
        super("inbound-boss");
        boolean antiOverrideTeams = config().getBoolean("scoreboard-teams.enabled", true) &&
                config().getBoolean("scoreboard-teams.anti-override", true);
        byteBufDeserialization = antiOverrideTeams || config().getBoolean("scoreboard.enabled", false);
    }

    @Override
    @NotNull
    public Function<TabPlayer, ChannelDuplexHandler> getChannelFunction() {
        return byteBufDeserialization ? DeserializableBungeeChannelDuplexHandler::new : BungeeChannelDuplexHandler::new;
    }

    @Override
    @NotNull
    protected Channel getChannel(@NotNull TabPlayer player) {
        return ((UserConnection) ((BungeeTabPlayer) player).getPlayer()).getCh().getHandle();
    }
}