/*
 * Copyright 2016, 2017, 2018, 2019 FabricMC
 * Copyright 2022 QuiltMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.fabric.api.entity.event.v1;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ServerPlayerEvents {
	/**
	 * An event that is called when the data from an old player is copied to a new player.
	 *
	 * <p>This event is typically called before a player is completely respawned.
	 * Mods may use this event to copy old player data to a new player.
	 */
	public static final Event<ServerPlayerEvents.CopyFrom> COPY_FROM = EventFactory.createArrayBacked(ServerPlayerEvents.CopyFrom.class, callbacks -> (oldPlayer, newPlayer, alive) -> {
		for (CopyFrom callback : callbacks) {
			callback.copyFromPlayer(oldPlayer, newPlayer, alive);
		}
	});

	/**
	 * An event that is called after a player has been respawned.
	 *
	 * <p>Mods may use this event for reference clean up on the old player.
	 */
	public static final Event<ServerPlayerEvents.AfterRespawn> AFTER_RESPAWN = EventFactory.createArrayBacked(ServerPlayerEvents.AfterRespawn.class, callbacks -> (oldPlayer, newPlayer, alive) -> {
		for (AfterRespawn callback : callbacks) {
			callback.afterRespawn(oldPlayer, newPlayer, alive);
		}
	});

	/**
	 * An event that is called when a player takes fatal damage.
	 *
	 * <p>Mods can cancel this to keep the player alive.
	 *
	 * <p>Vanilla checks for player health {@code <= 0} each tick (with {@link LivingEntity#isDead()}), and kills if true -
	 * so the player will still die next tick if this event is cancelled. It's assumed that the listener will do
	 * something to prevent this, for example:
	 *
	 * <ul>
	 *     <li>a minigame mod teleporting the player into a 'respawn room' and setting their health to 20.0</li>
	 *     <li>a mod that changes death mechanics switching the player over to the mod's play-mode, where death doesn't
	 *     apply</li>
	 * </ul>
	 */
	public static final Event<AllowDeath> ALLOW_DEATH = EventFactory.createArrayBacked(AllowDeath.class, callbacks -> (player, damageSource, damageAmount) -> {
		for (AllowDeath callback : callbacks) {
			if (!callback.allowDeath(player, damageSource, damageAmount)) {
				return false;
			}
		}

		return true;
	});

	@FunctionalInterface
	public interface CopyFrom {
		/**
		 * Called when player data is copied to a new player.
		 *
		 * @param oldPlayer the old player
		 * @param newPlayer the new player
		 * @param alive whether the old player is still alive
		 */
		void copyFromPlayer(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive);
	}

	@FunctionalInterface
	public interface AfterRespawn {
		/**
		 * Called after player a has been respawned.
		 *
		 * @param oldPlayer the old player
		 * @param newPlayer the new player
		 * @param alive whether the old player is still alive
		 */
		void afterRespawn(ServerPlayerEntity oldPlayer, ServerPlayerEntity newPlayer, boolean alive);
	}

	@FunctionalInterface
	public interface AllowDeath {
		/**
		 * Called when a player takes fatal damage (before totems of undying can take effect).
		 *
		 * @param player the player
		 * @param damageSource the fatal damage damageSource
		 * @param damageAmount the damageAmount of damage that has killed the player
		 * @return true if the death should go ahead, false otherwise.
		 */
		boolean allowDeath(ServerPlayerEntity player, DamageSource damageSource, float damageAmount);
	}

	private ServerPlayerEvents() {
	}
}
