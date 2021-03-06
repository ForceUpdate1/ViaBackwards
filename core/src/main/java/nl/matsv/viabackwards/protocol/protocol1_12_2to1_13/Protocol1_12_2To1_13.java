/*
 * Copyright (c) 2016 Matsv
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package nl.matsv.viabackwards.protocol.protocol1_12_2to1_13;

import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.data.BackwardsMappings;
import nl.matsv.viabackwards.protocol.protocol1_12_2to1_13.packets.BlockItemPackets1_13;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class Protocol1_12_2To1_13 extends BackwardsProtocol {
    @Override
    protected void registerPackets() {
        new BlockItemPackets1_13().register(this);

        // Thanks to  https://wiki.vg/index.php?title=Pre-release_protocol&oldid=14150


        out(State.PLAY, 0x00, 0x00, cancel());
        out(State.PLAY, 0x04, 0x04, cancel());// Spawn Painting TODO MODIFIED
        out(State.PLAY, 0x07, 0x07, cancel()); // Statistics TODO MODIFIED
        out(State.PLAY, 0x09, 0x09, cancel()); // Update Block Entity TODO MODIFIED
        out(State.PLAY, 0x0B, 0x0B, cancel()); // Block Change TODO MODIFIED
        out(State.PLAY, 0x0E, 0x0F); // Chat Message (clientbound)
        out(State.PLAY, 0x0F, 0x10, cancel()); // Multi Block Change TODO MODIFIED
        out(State.PLAY, 0x10, 0x0E, cancel()); // Tab-Complete (clientbound) TODO MODIFIED
        out(State.PLAY, 0x11, -1, cancel()); // Declare Commands TODO NEW
        out(State.PLAY, 0x12, 0x11, cancel()); // Confirm Transaction (clientbound)
        out(State.PLAY, 0x13, 0x12, cancel()); // Close Window (clientbound)
        out(State.PLAY, 0x14, 0x13, cancel()); // Open Window
        out(State.PLAY, 0x15, 0x14, cancel()); // Window Items
        out(State.PLAY, 0x16, 0x15, cancel()); // Window Property
        out(State.PLAY, 0x17, 0x16, cancel()); // Set Slot
        out(State.PLAY, 0x18, 0x17); // Set Cooldown 
        out(State.PLAY, 0x19, 0x18); // Plugin Message (clientbound) TODO MODIFIED
        out(State.PLAY, 0x1A, 0x19, cancel()); // Named Sound Effect TODO MODIFIED
        out(State.PLAY, 0x1B, 0x1A); // Disconnect (play)
        out(State.PLAY, 0x1C, 0x1B); // Entity Status
        out(State.PLAY, 0x1D, -1, cancel()); // NBT Query Response TODO NEW
        out(State.PLAY, 0x1E, 0x1C); // Explosion
        out(State.PLAY, 0x1F, 0x1D); // Unload Chunk
        out(State.PLAY, 0x20, 0x1E); // Change Game State
        out(State.PLAY, 0x21, 0x1F); // Keep Alive (clientbound)

        // Chunk Data -> moved to BlockItemPackets



        out(State.PLAY, 0x23, 0x21, cancel()); // Effect TODO MODIFIED
        out(State.PLAY, 0x24, 0x22, cancel()); // Spawn Particle TODO MODIFIED
        out(State.PLAY, 0x25, 0x23, new PacketRemapper() {
            @Override
            public void registerMap() {
                map(Type.INT); // 0 - Entity ID
                map(Type.UNSIGNED_BYTE); // 1 - Gamemode
                map(Type.INT); // 2 - Dimension

                handler(new PacketHandler() {
                    @Override
                    public void handle(PacketWrapper wrapper) throws Exception {
                        ClientWorld clientChunks = wrapper.user().get(ClientWorld.class);
                        int dimensionId = wrapper.get(Type.INT, 1);
                        clientChunks.setEnvironment(dimensionId);
                    }
                });
            }
        }); // Join Game
        out(State.PLAY, 0x26, 0x24, cancel()); // Map TODO MODIFIED
        out(State.PLAY, 0x27, 0x25); // Entity
        out(State.PLAY, 0x28, 0x26); // Entity Relative Move
        out(State.PLAY, 0x29, 0x27); // Entity Look And Relative Move
        out(State.PLAY, 0x2A, 0x28); // Entity Look
        out(State.PLAY, 0x2B, 0x29); // Vehicle Move (clientbound)
        out(State.PLAY, 0x2C, 0x2A); //	Open Sign Editor
        out(State.PLAY, 0x2D, 0x2B, cancel()); // Craft Recipe Response TODO MODIFIED
        out(State.PLAY, 0x2E, 0x2C); // Player Abilities (clientbound)
        out(State.PLAY, 0x2F, 0x2D); // Combat Event
        out(State.PLAY, 0x30, 0x2E); // Player List Item
        out(State.PLAY, 0x31, -1, cancel()); // Face Player TODO NEW
        out(State.PLAY, 0x32, 0x2F); // Player Position And Look (clientbound)
        out(State.PLAY, 0x33, 0x30); // Use Bed
        out(State.PLAY, 0x34, 0x31, cancel()); // Unlock Recipes TODO MODIFIED
        out(State.PLAY, 0x35, 0x32); // Destroy Entities
        out(State.PLAY, 0x36, 0x33); // Remove Entity Effect
        out(State.PLAY, 0x37, 0x34); // Resource Pack Send
        out(State.PLAY, 0x38, 0x35); // Respawn
        out(State.PLAY, 0x39, 0x36); // Entity Head Look
        out(State.PLAY, 0x3A, 0x37); // Select Advancement Tab
        out(State.PLAY, 0x3B, 0x38); // World Border
        out(State.PLAY, 0x3C, 0x39); // Camera
        out(State.PLAY, 0x3D, 0x3A, cancel()); // Held Item Change (clientbound)
        out(State.PLAY, 0x3E, 0x3B); // Display Scoreboard
        out(State.PLAY, 0x3F, 0x3C, cancel()); // Entity Metadata
        out(State.PLAY, 0x40, 0x3D); // Attach Entity
        out(State.PLAY, 0x41, 0x3E); // Entity Velocity
        out(State.PLAY, 0x42, 0x3F, cancel()); // Entity Equipment
        out(State.PLAY, 0x43, 0x40); // Set Experience
        out(State.PLAY, 0x44, 0x41); // Update Health
        out(State.PLAY, 0x45, 0x42, cancel()); // Scoreboard Objective TODO MODIFIED
        out(State.PLAY, 0x46, 0x43); //	Set Passengers
        out(State.PLAY, 0x47, 0x44, cancel()); // Teams TODO MODIFIED
        out(State.PLAY, 0x48, 0x45); // Update Score
        out(State.PLAY, 0x49, 0x46); // Spawn Position
        out(State.PLAY, 0x4A, 0x47); // Time Update
        out(State.PLAY, 0x4B, 0x48); // Title
        out(State.PLAY, 0x4C, -1, cancel()); // Stop Sound TODO NEW
        out(State.PLAY, 0x4D, 0x49); // Sound Effect
        out(State.PLAY, 0x4E, 0x4A); // Player List Header And Footer
        out(State.PLAY, 0x4F, 0x4B, cancel()); // Collect Item
        out(State.PLAY, 0x50, 0x4C); // Entity Teleport
        out(State.PLAY, 0x51, 0x4D, cancel()); // Advancements
        out(State.PLAY, 0x52, 0x4E); // Entity Properties
        out(State.PLAY, 0x53, 0x4F); // Entity Effect
        out(State.PLAY, 0x54, -1, cancel()); // Declare Recipes TODO NEW
        out(State.PLAY, 0x55, -1, cancel()); // Tags TODO NEW


        in(State.PLAY, 0x01, -1, cancel()); // Query Block NBT TODO NEW
        in(State.PLAY, 0x05, 0x01); // Tab-Complete (serverbound) TODO MODIFIED
        in(State.PLAY, 0x06, 0x05); //Confirm Transaction (serverbound)
        in(State.PLAY, 0x07, 0x06); // Enchant Item
        in(State.PLAY, 0x08, 0x07); // Click Window
        in(State.PLAY, 0x09, 0x08); // Close Window (serverbound)
        in(State.PLAY, 0x0A, 0x09, cancel()); // Plugin message (serverbound) TODO MODIFIED
        in(State.PLAY, 0x0B, -1, cancel()); // Edit Book TODO NEW
        in(State.PLAY, 0x0C, -1, cancel()); // Query Entity NBT TODO NEW
        in(State.PLAY, 0x0D, 0x0A); // Use Entity
        in(State.PLAY, 0x0E, 0x0B); // Keep Alive (serverbound)
        in(State.PLAY, 0x0F, 0x0C); // Player
        in(State.PLAY, 0x10, 0x0D); // Player Position
        in(State.PLAY, 0x11, 0x0E); // Player Position And Look (serverbound)
        in(State.PLAY, 0x12, 0x0F); // Player Look
        in(State.PLAY, 0x13, 0x10); // Vehicle Move (serverbound)
        in(State.PLAY, 0x14, 0x11); // Steer Boat
        in(State.PLAY, 0x15, -1, cancel()); // Pick Item TODO NEW
        in(State.PLAY, 0x16, 0x12, cancel()); // Craft Recipe Request TODO MODIFIED
        in(State.PLAY, 0x17, 0x13); // Player Abilities (serverbound)
        in(State.PLAY, 0x18, 0x14); // Player Digging
        in(State.PLAY, 0x19, 0x15); // Entity Action
        in(State.PLAY, 0x1A, 0x16); // Steer Vehicle
        in(State.PLAY, 0x1B, 0x17); // Recipe Book Data
        in(State.PLAY, 0x1C, -1, cancel()); // Name Item TODO NEW
        in(State.PLAY, 0x1D, 0x18); // Resource Pack Status
        in(State.PLAY, 0x1E, 0x19); // Advancement Tab
        in(State.PLAY, 0x1F, -1); // Select Trade
        in(State.PLAY, 0x20, -1); // Set Beacon Effect
        in(State.PLAY, 0x21, 0x1A); // Held Item Change (serverbound)
        in(State.PLAY, 0x22, -1, cancel()); // Update Command Block TODO NEW
        in(State.PLAY, 0x23, -1, cancel()); // Update Command Block Minecart TODO NEW
        in(State.PLAY, 0x24, 0x1B); // Creative Inventory Action
        in(State.PLAY, 0x25, -1, cancel()); // Update Structure Block TODO NEW
        in(State.PLAY, 0x26, 0x1C); // Update Sign
        in(State.PLAY, 0x27, 0x1D); // Animation (serverbound)
        in(State.PLAY, 0x28, 0x1E); // Spectate
        in(State.PLAY, 0x29, 0x1F); // Player Block Placement
        in(State.PLAY, 0x2A, 0x20); // Use Item

    }

    @Override
    public void init(UserConnection user) {
        // Register ClientWorld
        if (!user.has(ClientWorld.class))
            user.put(new ClientWorld(user));

    }

    public PacketRemapper cancel() {
        return new PacketRemapper() {
            @Override
            public void registerMap() {
                handler(new PacketHandler() {
                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                    }
                });
            }
        };
    }

    static {
        BackwardsMappings.init();
    }

}
