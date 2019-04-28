package com.hrznstudio.research.api.research;

import com.hrznstudio.research.api.player.PlayerProgress;
import org.jetbrains.annotations.Contract;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;

@FunctionalInterface
@ParametersAreNonnullByDefault
public interface IPrerequisite extends Predicate<PlayerProgress> {

    @Override
    @Contract(pure = true, value = "_->new")
    default IPrerequisite and(Predicate<? super PlayerProgress> other) {
        return playerProgress -> this.test(playerProgress) && other.test(playerProgress);
    }

    @Contract(pure = true, value = "_->new")
    default IPrerequisite or(Predicate<? super PlayerProgress> other) {
        return playerProgress -> this.test(playerProgress) || other.test(playerProgress);
    }

    @Contract(pure = true, value = "_->new")
    default IPrerequisite nand(Predicate<? super PlayerProgress> other) {
        return playerProgress -> !this.test(playerProgress) || !other.test(playerProgress);
    }

    @Contract(pure = true, value = "_->new")
    default IPrerequisite nor(Predicate<? super PlayerProgress> other) {
        return playerProgress -> !this.test(playerProgress) && !other.test(playerProgress);
    }

    @Contract(pure = true, value = "_->new")
    default IPrerequisite xor(Predicate<? super PlayerProgress> other) {
        return playerProgress -> this.test(playerProgress) ^ other.test(playerProgress);
    }

    @Override
    @Contract(pure = true, value = "->new")
    default IPrerequisite negate() {
        return playerProgress -> !this.test(playerProgress);
    }
}
