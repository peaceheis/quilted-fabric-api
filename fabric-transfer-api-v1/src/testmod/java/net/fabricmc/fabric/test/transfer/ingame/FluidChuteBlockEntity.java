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

package net.fabricmc.fabric.test.transfer.ingame;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;

public class FluidChuteBlockEntity extends BlockEntity {
	private int tickCounter = 0;

	public FluidChuteBlockEntity(BlockPos pos, BlockState state) {
		super(TransferTestInitializer.FLUID_CHUTE_TYPE, pos, state);
	}

	@SuppressWarnings("ConstantConditions")
	public void tick() {
		if (!world.isClient() && tickCounter++ % 20 == 0) {
			Storage<FluidVariant> top = FluidStorage.SIDED.find(world, pos.offset(Direction.UP), Direction.DOWN);
			Storage<FluidVariant> bottom = FluidStorage.SIDED.find(world, pos.offset(Direction.DOWN), Direction.UP);

			if (top != null && bottom != null) {
				StorageUtil.move(top, bottom, fluid -> true, FluidConstants.BUCKET, null);
			}
		}
	}
}
