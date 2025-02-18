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

package net.fabricmc.fabric.api.networking.v1;

import java.util.Set;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import net.fabricmc.fabric.impl.networking.QuiltPacketSender;

/**
 * Offers access to play stage server-side networking functionalities.
 *
 * <p>Server-side networking functionalities include receiving serverbound packets, sending clientbound packets, and events related to server-side network handlers.
 *
 * <p>This class should be only used for the logical server.
 *
 * @see ServerLoginNetworking
 * @see net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking
 */
@Deprecated
public final class ServerPlayNetworking {
	/**
	 * Registers a handler to a channel.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>If a handler is already registered to the {@code channel}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterReceiver(ServerPlayNetworkHandler, Identifier)} to unregister the existing handler.
	 *
	 * @param channelName    the id of the channel
	 * @param channelHandler the handler
	 * @return false if a handler is already registered to the channel
	 * @see ServerPlayNetworking#unregisterGlobalReceiver(Identifier)
	 * @see ServerPlayNetworking#registerReceiver(ServerPlayNetworkHandler, Identifier, PlayChannelHandler)
	 */
	public static boolean registerGlobalReceiver(Identifier channelName, PlayChannelHandler channelHandler) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.registerGlobalReceiver(channelName, channelHandler);
	}

	/**
	 * Removes the handler of a channel.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * <p>The {@code channel} is guaranteed not to have a handler after this call.
	 *
	 * @param channelName the id of the channel
	 * @return the previous handler, or {@code null} if no handler was bound to the channel
	 * @see ServerPlayNetworking#registerGlobalReceiver(Identifier, PlayChannelHandler)
	 * @see ServerPlayNetworking#unregisterReceiver(ServerPlayNetworkHandler, Identifier)
	 */
	@Nullable
	public static PlayChannelHandler unregisterGlobalReceiver(Identifier channelName) {
		var old = org.quiltmc.qsl.networking.api.ServerPlayNetworking.unregisterGlobalReceiver(channelName);

		if (old instanceof PlayChannelHandler fabric) {
			return fabric;
		} else if (old != null) {
			return old::receive;
		} else {
			return null;
		}
	}

	/**
	 * Gets all channel names which global receivers are registered for.
	 * A global receiver is registered to all connections, in the present and future.
	 *
	 * @return all channel names which global receivers are registered for.
	 */
	public static Set<Identifier> getGlobalReceivers() {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getGlobalReceivers();
	}

	/**
	 * Registers a handler to a channel.
	 * This method differs from {@link ServerPlayNetworking#registerGlobalReceiver(Identifier, PlayChannelHandler)} since
	 * the channel handler will only be applied to the player represented by the {@link ServerPlayNetworkHandler}.
	 *
	 * <p>For example, if you only register a receiver using this method when a {@linkplain ServerLoginNetworking#registerGlobalReceiver(Identifier, ServerLoginNetworking.LoginQueryResponseHandler)}
	 * login response has been received, you should use {@link ServerPlayConnectionEvents#INIT} to register the channel handler.
	 *
	 * <p>If a handler is already registered to the {@code channelName}, this method will return {@code false}, and no change will be made.
	 * Use {@link #unregisterReceiver(ServerPlayNetworkHandler, Identifier)} to unregister the existing handler.
	 *
	 * @param networkHandler the handler
	 * @param channelName    the id of the channel
	 * @param channelHandler the handler
	 * @return false if a handler is already registered to the channel name
	 * @see ServerPlayConnectionEvents#INIT
	 */
	public static boolean registerReceiver(ServerPlayNetworkHandler networkHandler, Identifier channelName, PlayChannelHandler channelHandler) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.registerReceiver(networkHandler, channelName, channelHandler);
	}

	/**
	 * Removes the handler of a channel.
	 *
	 * <p>The {@code channelName} is guaranteed not to have a handler after this call.
	 *
	 * @param channelName the id of the channel
	 * @return the previous handler, or {@code null} if no handler was bound to the channel name
	 */
	@Nullable
	public static PlayChannelHandler unregisterReceiver(ServerPlayNetworkHandler networkHandler, Identifier channelName) {
		var old = org.quiltmc.qsl.networking.api.ServerPlayNetworking.unregisterReceiver(networkHandler, channelName);

		if (old instanceof PlayChannelHandler fabric) {
			return fabric;
		} else if (old != null) {
			return old::receive;
		} else {
			return null;
		}
	}

	/**
	 * Gets all the channel names that the server can receive packets on.
	 *
	 * @param player the player
	 * @return All the channel names that the server can receive packets on
	 */
	public static Set<Identifier> getReceived(ServerPlayerEntity player) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getReceived(player);
	}

	/**
	 * Gets all the channel names that the server can receive packets on.
	 *
	 * @param handler the network handler
	 * @return All the channel names that the server can receive packets on
	 */
	public static Set<Identifier> getReceived(ServerPlayNetworkHandler handler) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getReceived(handler);
	}

	/**
	 * Gets all channel names that the connected client declared the ability to receive a packets on.
	 *
	 * @param player the player
	 * @return All the channel names the connected client declared the ability to receive a packets on
	 */
	public static Set<Identifier> getSendable(ServerPlayerEntity player) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getSendable(player);
	}

	/**
	 * Gets all channel names that a the connected client declared the ability to receive a packets on.
	 *
	 * @param handler the network handler
	 * @return True if the connected client has declared the ability to receive a packet on the specified channel
	 */
	public static Set<Identifier> getSendable(ServerPlayNetworkHandler handler) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getSendable(handler);
	}

	/**
	 * Checks if the connected client declared the ability to receive a packet on a specified channel name.
	 *
	 * @param player      the player
	 * @param channelName the channel name
	 * @return True if the connected client has declared the ability to receive a packet on the specified channel
	 */
	public static boolean canSend(ServerPlayerEntity player, Identifier channelName) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.canSend(player, channelName);
	}

	/**
	 * Checks if the connected client declared the ability to receive a packet on a specified channel name.
	 *
	 * @param handler     the network handler
	 * @param channelName the channel name
	 * @return True if the connected client has declared the ability to receive a packet on the specified channel
	 */
	public static boolean canSend(ServerPlayNetworkHandler handler, Identifier channelName) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.canSend(handler, channelName);
	}

	/**
	 * Creates a packet which may be sent to a the connected client.
	 *
	 * @param channelName the channel name
	 * @param buf         the packet byte buf which represents the payload of the packet
	 * @return a new packet
	 */
	public static Packet<?> createS2CPacket(Identifier channelName, PacketByteBuf buf) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.createS2CPacket(channelName, buf);
	}

	/**
	 * Gets the packet sender which sends packets to the connected client.
	 *
	 * @param player the player
	 * @return the packet sender
	 */
	public static PacketSender getSender(ServerPlayerEntity player) {
		return new QuiltPacketSender(org.quiltmc.qsl.networking.api.ServerPlayNetworking.getSender(player));
	}

	/**
	 * Gets the packet sender which sends packets to the connected client.
	 *
	 * @param handler the network handler, representing the connection to the player/client
	 * @return the packet sender
	 */
	public static PacketSender getSender(ServerPlayNetworkHandler handler) {
		return new QuiltPacketSender(org.quiltmc.qsl.networking.api.ServerPlayNetworking.getSender(handler));
	}

	/**
	 * Sends a packet to a player.
	 *
	 * @param player      the player to send the packet to
	 * @param channelName the channel of the packet
	 * @param buf         the payload of the packet.
	 */
	public static void send(ServerPlayerEntity player, Identifier channelName, PacketByteBuf buf) {
		org.quiltmc.qsl.networking.api.ServerPlayNetworking.send(player, channelName, buf);
	}

	// Helper methods

	/**
	 * Returns the <i>Minecraft</i> Server of a server play network handler.
	 *
	 * @param handler the server play network handler
	 */
	public static MinecraftServer getServer(ServerPlayNetworkHandler handler) {
		return org.quiltmc.qsl.networking.api.ServerPlayNetworking.getServer(handler);
	}

	private ServerPlayNetworking() {
	}

	@Deprecated
	@FunctionalInterface
	public interface PlayChannelHandler extends org.quiltmc.qsl.networking.api.ServerPlayNetworking.ChannelReceiver {
		@Override
		default void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, org.quiltmc.qsl.networking.api.PacketSender responseSender) {
			this.receive(server, player, handler, buf, new QuiltPacketSender(responseSender));
		}

		/**
		 * Handles an incoming packet.
		 *
		 * <p>This method is executed on {@linkplain io.netty.channel.EventLoop netty's event loops}.
		 * Modification to the game should be {@linkplain net.minecraft.util.thread.ThreadExecutor#submit(Runnable) scheduled} using the provided Minecraft server instance.
		 *
		 * <p>An example usage of this is to create an explosion where the player is looking:
		 * <pre>{@code
		 * ServerPlayNetworking.registerReceiver(new Identifier("mymod", "boom"), (server, player, handler, buf, responseSender) -&rt; {
		 * 	boolean fire = buf.readBoolean();
		 *
		 * 	// All operations on the server or world must be executed on the server thread
		 * 	server.execute(() -&rt; {
		 * 		ModPacketHandler.createExplosion(player, fire);
		 *    });
		 * });
		 * }</pre>
		 *
		 * @param server         the server
		 * @param player         the player
		 * @param handler        the network handler that received this packet, representing the player/client who sent the packet
		 * @param buf            the payload of the packet
		 * @param responseSender the packet sender
		 */
		void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender);
	}
}
