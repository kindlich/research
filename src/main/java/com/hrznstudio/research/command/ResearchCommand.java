package com.hrznstudio.research.command;

import com.hrznstudio.research.api.research.APIMethods;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collections;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ResearchCommand implements ICommand {
    
    @Override
    public String getName() {
        return "research";
    }
    
    @Override
    public String getUsage(ICommandSender sender) {
        return "";
    }
    
    @Override
    public List<String> getAliases() {
        return Collections.emptyList();
    }
    
    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(!(sender instanceof EntityPlayer))
            return;
        if(args.length == 1 && "clearAllProgress".equalsIgnoreCase(args[0])) {
            APIMethods.getProgressForPlayer((EntityPlayer) sender).clearAllProgress();
        }
    }
    
    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }
    
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.singletonList("clearAllProgress");
    }
    
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
    
    @Override
    public int compareTo(@NotNull ICommand o) {
        return o instanceof ResearchCommand ? 0 : -1;
    }
}
